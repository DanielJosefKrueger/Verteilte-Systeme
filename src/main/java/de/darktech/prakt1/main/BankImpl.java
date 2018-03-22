package de.darktech.prakt1.main;

import com.sun.javaws.exceptions.InvalidArgumentException;

import java.rmi.RemoteException;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.List;


public class BankImpl extends UnicastRemoteObject implements Bank {


    private final HashMap<Integer, Account> accounts;
    private int nextFreeNumber ;


    public BankImpl(String name, Registry reg) throws RemoteException {

        try {
            reg.rebind(name, this);
        } catch (Exception e) {
            System.out.println("Exception: " + e.getMessage());
        }
        accounts = new HashMap<Integer, Account>();
        nextFreeNumber=0;
    }

    public int createNewAccount(String name, String pw) {
        int number = nextFreeNumber;
        nextFreeNumber++;
        accounts.put(number, new Account(name, pw , 0));
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

    public Balance getBalance(int accNo, String name, String pw) {
        return null;
    }

    public double getCumulatedBalanceForName(String name) {
        return 0;
    }

    public List<Integer> getAccountNoForName(String name) {
        return null;
    }


    private Account findAccount(int accNo) throws NoSuchAccountException {
        Account account = accounts.get(accNo);
        if(account==null){
            throw new NoSuchAccountException();
        }
        return account;
    }



}


