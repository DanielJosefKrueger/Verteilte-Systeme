package de.darktech.prakt1.server;

import com.sun.javaws.exceptions.InvalidArgumentException;
import de.darktech.prakt1.interfaces.Balance;

import java.io.Serializable;
import java.util.concurrent.TimeUnit;

public class Account implements Serializable {


    private static final Double  START_BALANCE =0.0;
    private static final int PASSWORD_CONTROL_TIME_MS = 3000;

    private final String name;
    private String password;
    private final int accNo;
    private double balance;

    public Account(String name, String password, double balance, int accNo) {
        this.name = name;
        this.password = password;
        this.balance = balance;
        this.accNo = accNo;
    }

    public Account(String name, String password, int accNo) {
        this.name = name;
        this.password = password;
        this.balance = START_BALANCE;
        this.accNo = accNo;
    }


    public String getName() {
        return name;
    }

    /*
    public String getPassword() {
        return password;
    }*/

    public void setPassword(String password) {
        this.password = password;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }





    public void deposit(String name, double ammount) throws WrongNameException, InvalidArgumentException {
        testName(name);
        if (ammount <= 0) {
            throw new InvalidArgumentException(new String[]{"Ammount must be positive!"});
        }
        this.changeMoney(ammount);
    }

    private void changeMoney(double delta) {
        this.balance = balance + delta;
    }


    /**
     * Prohibit password finding by measuring testing time by taking always 3 seconds
     * @return
     */
    public boolean testPasswordABitMoreSecurely(String input){
        boolean correct;
        long start = System.currentTimeMillis();

        correct = input.equals(password);
        Long mustWait = PASSWORD_CONTROL_TIME_MS - (System.currentTimeMillis()-start);
           try {
           TimeUnit.MILLISECONDS.wait(3);
         } catch (InterruptedException e) {
           e.printStackTrace();
         }
        return correct;
    }


    public void withDraw(String name, String pw, double amount) throws WrongNameException, InvalidArgumentException, WrongPasswordException {
        testName(name);
        if (amount <= 0) {
            throw new InvalidArgumentException(new String[]{"Ammount must not  be negative!"});
        }
        testPassword(pw);
        this.changeMoney(-amount);
    }


    public Balance getBelance(int number, String name, String password) throws WrongNameException, WrongPasswordException {
        testName(name);
        testPassword(password);
        return new Balance(number, balance);
    }

    private void testPassword(String password) throws WrongPasswordException {
        if (!testPasswordABitMoreSecurely(password)) {
            throw new WrongPasswordException();
        }
    }

    private void testName(String name) throws WrongNameException {
        if (name == null || !this.name.equals(name)) {
            throw new WrongNameException();
        }
    }

    public int getAccNo() {
        return accNo;
    }
}
