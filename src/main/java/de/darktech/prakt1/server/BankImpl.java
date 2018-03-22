package de.darktech.prakt1.server;

import com.sun.javaws.exceptions.InvalidArgumentException;
import de.darktech.prakt1.interfaces.Balance;
import de.darktech.prakt1.interfaces.Bank;

import java.rmi.RemoteException;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class BankImpl extends UnicastRemoteObject implements Bank {


    private final HashMap<Integer, Account> accounts;
    private int nextFreeNumber ;


    public BankImpl(String name, Registry reg) throws RemoteException {


        accounts = new HashMap<Integer, Account>();
        nextFreeNumber = 0;
        try {
            reg.rebind(name, this);
        } catch (Exception e) {
            System.out.println("Exception: " + e.getMessage());
        }
    }

    public int createNewAccount(String name, String pw) {
        int number = nextFreeNumber;
        nextFreeNumber++;
        accounts.put(number, new Account(name, pw, number));
        return number;
    }

    public void deposit(int accNo, String name, double amount) throws NoSuchAccountException, WrongNameException, InvalidArgumentException {
        Account account = findAccount(accNo);
        account.deposit(name, amount);
    }

    public void withDraw(int accNo, String name, String pw, double amount) throws NoSuchAccountException, WrongPasswordException, WrongNameException, InvalidArgumentException {
        Account account = findAccount(accNo);
        account.withDraw(name,pw,  amount);
    }

    public Balance getBalance(int accNo, String name, String pw) throws NoSuchAccountException, WrongNameException, WrongPasswordException {
        Account account = findAccount(accNo);
        return account.getBelance(accNo, name, pw);
    }

    public double getCumulatedBalanceForName(String name) {
        double sum = 0;
        for (Account account : findAccounts(name)) {
            sum += account.getBalance();
        }
        return sum;
    }

    public List<Integer> getAccountNoForName(String name) {
        final List<Integer> accNos = new ArrayList<>();
        for (Account account : findAccounts(name)) {
            accNos.add(account.getAccNo());
        }
        return accNos;
    }


    private Account findAccount(int accNo) throws NoSuchAccountException {
        Account account = accounts.get(accNo);
        if(account==null){
            throw new NoSuchAccountException();
        }
        return account;
    }

    private List<Account> findAccounts(String name) {
        final ArrayList<Account> list = new ArrayList<>();
        accounts.entrySet()
                .stream()
                .filter(e -> e.getValue().getName().equals(name))
                .map(e -> e.getValue())
                .forEach(list::add);
        return list;
    }

}


