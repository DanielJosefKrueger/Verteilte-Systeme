package de.darktech.prakt1.client;

import de.darktech.prakt1.interfaces.Balance;
import de.darktech.prakt1.interfaces.Bank;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Client {


    public static void main(String args[]) {
        try {
            Registry reg = LocateRegistry.getRegistry(1099);


            Bank bank = (Bank) reg.lookup("myBank");
            //ComplexAdder ad = (ComplexAdder) reg.lookup ("rmi://" + args[0] + ":1099/" + "myComplexAdder");
            //ComplexAdder ad = (ComplexAdder) Naming.lookup ("rmi://" + args[0] + "/" + "myAdder");

            int accNo = bank.createNewAccount("Daniel Krüger", "sicheresPassword");
            System.out.println("Neues Konto wurde eröffnet");


            bank.deposit(accNo, "Daniel Krüger", 100000000);
            System.out.println("Geld wurde auf Konto überwiesen");

            Balance balance = bank.getBalance(accNo, "Daniel Krüger", "sicheresPassword");
            System.out.println("Geld auf dem Konto: " + balance);


            //  bank.withDraw(accNo, "The Evil one", "sicheresPassword", 1000000);
            bank.withDraw(accNo, "Daniel Krüger", "wrongPassword", 1000000);


        } catch (Exception e) {
            System.out.println("ClientException: " + e.getClass());
        }
    }

}
