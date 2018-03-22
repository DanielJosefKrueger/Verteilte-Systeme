package de.darktech.prakt1.main;

import com.sun.javaws.exceptions.InvalidArgumentException;

import java.util.List;

public interface Bank {



    int createNewAccount(String name, String pw);
    void deposit(int accNo, String name, double ammount) throws NoSuchAccountException, WrongNameException, InvalidArgumentException;
    void withDraw(int accNo, String name, String pw, double amount) throws NoSuchAccountException, WrongPasswordException, WrongNameException, InvalidArgumentException;
    Balance getBalance(int accNo, String name, String pw);
    double getCumulatedBalanceForName(String name);
    List<Integer> getAccountNoForName(String name);




}
