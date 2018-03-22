package de.darktech.prakt1.interfaces;

import java.io.Serializable;

public class Balance implements Serializable {

    private final int number;
    private final double balance;


    public Balance(int number, double balance) {
        this.number = number;
        this.balance = balance;
    }


    public int getNumber() {
        return number;
    }

    public double getBalance() {
        return balance;
    }


    @Override
    public String toString() {
        return "Kontostand: " + (float) balance + "/Konto Nr.:" + number;
    }
}
