package de.darktech.prakt1.client;

import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;
import de.darktech.prakt1.interfaces.Balance;
import de.darktech.prakt1.interfaces.Bank;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.List;

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


            System.out.println("Versuch: Falscher Benutzername");
            try{
                bank.withDraw(accNo, "The Evil one", "sicheresPassword", 1000000);
            }catch(Exception e){
                System.out.println("ClientException: " + e.getClass());
            }


            System.out.println("Versuch: Falsches Password");
            try{
                bank.withDraw(accNo, "Daniel Krüger", "wrongPassword", 1000000);
            }catch(Exception e){
                System.out.println("ClientException: " + e.getClass());
            }
            int accNo3 = bank.createNewAccount("Mister A", "sicheresPassword2");
            System.out.println("Anderes Konto wurde eröffnet");

            int accNo2 = bank.createNewAccount("Daniel Krüger", "sicheresPassword2");
            System.out.println("Zweites Konto wurde eröffnet");

            bank.deposit(accNo, "Daniel Krüger", 1);
            System.out.println("Geld wurde auf Konto2 überwiesen");


            int accNo4 = bank.createNewAccount("Mister A", "sicheresPassword2");
            System.out.println("Anderes Konto wurde eröffnet");




            List<Integer> numbers = bank.getAccountNoForName("Daniel Krüger");
            System.out.println("Daniel Krüger hat folgende Kontonummern: " + numbers);

            double money = bank.getCumulatedBalanceForName("Daniel Krüger");
            System.out.println("Daniel Krüger hat insgesamt " + money + " Euro auf allen Konten");


        } catch (Exception e) {
            System.out.println("ClientException: " + e.getClass());
        }
    }

}
