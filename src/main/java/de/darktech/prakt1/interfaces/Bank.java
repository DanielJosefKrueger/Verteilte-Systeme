package de.darktech.prakt1.interfaces;

import com.sun.javaws.exceptions.InvalidArgumentException;
import de.darktech.prakt1.server.NoSuchAccountException;
import de.darktech.prakt1.server.WrongNameException;
import de.darktech.prakt1.server.WrongPasswordException;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface Bank extends Remote {


    int createNewAccount(String name, String pw) throws RemoteException;

    void deposit(int accNo, String name, double ammount) throws NoSuchAccountException, WrongNameException, InvalidArgumentException, RemoteException;

    void withDraw(int accNo, String name, String pw, double amount) throws NoSuchAccountException, WrongPasswordException, WrongNameException, InvalidArgumentException, RemoteException;

    Balance getBalance(int accNo, String name, String pw) throws NoSuchAccountException, WrongNameException, WrongPasswordException, RemoteException;

    double getCumulatedBalanceForName(String name) throws RemoteException, NoSuchAccountException;

    List<Integer> getAccountNoForName(String name) throws RemoteException;




}
