package de.darktech.prakt1.main;

import com.sun.javaws.exceptions.InvalidArgumentException;

import java.io.Serializable;
import java.util.concurrent.TimeUnit;

public class Account implements Serializable {


    private static final Double  START_BALANCE =0.0;
    private static final int PASSWORD_CONTROL_TIME_MS = 3000;

    private final String name;
    private String password;
    private double money;

    public Account(String name, String password, double money) {
        this.name = name;
        this.password = password;
        this.money = money;
    }

    public Account(String name, String password) {
        this.name = name;
        this.password = password;
        this.money = START_BALANCE;
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

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }





    public void deposit(String name, double ammount) throws WrongNameException, InvalidArgumentException {
        if (name == null || !this.name.equals(name)) {
            throw new WrongNameException();
        }
        if (ammount <= 0) {
            throw new InvalidArgumentException(new String[]{"Ammount must be positive!"});
        }
        this.changeMoney(ammount);
    }

    private void changeMoney(double delta) {
        this.money = money + delta;
    }


    /**
     * Prohibit password finding by measuring testing time by taking always 3 seconds
     * @return
     */
    public boolean testPasswordABitMoreSecurely(String input){
        boolean correct =false;
        long start = System.currentTimeMillis();

        correct = input.equals(password);
        Long mustWait = PASSWORD_CONTROL_TIME_MS - (System.currentTimeMillis()-start);
        try {
            TimeUnit.MILLISECONDS.wait(mustWait);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return correct;
    }


    public void withDraw(String name, String pw, double amount) throws WrongNameException, InvalidArgumentException, WrongPasswordException {
        if (name == null || !this.name.equals(name)) {
            throw new WrongNameException();
        }
        if (amount >= 0) {
            throw new InvalidArgumentException(new String[]{"Ammount must be negative!"});
        }
        if(!testPasswordABitMoreSecurely(pw)){
            throw new WrongPasswordException();
        }
        this.changeMoney(amount);
    }
}
