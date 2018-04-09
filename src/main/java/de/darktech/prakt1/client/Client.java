package de.darktech.prakt1.client;

import de.darktech.prakt1.interfaces.Balance;
import de.darktech.prakt1.interfaces.Bank;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.List;
import java.util.Scanner;

public class Client {


    private final Scanner in = new Scanner(System.in);
    private Bank bank = null;

    private Client() {
        try {
            Registry reg = LocateRegistry.getRegistry(1099);
            bank = (Bank) reg.lookup("myBank");
        } catch (Exception e) {
            System.out.println("ClientException: " + e.getClass());
        }
    }

    public static void main(String args[]) {
        Client client = new Client();
        client.start();
    }

    private void start() {
        try {

            while (true) {
                selectDialog();
                System.out.println("++++++++++ Neue Operation ++++++++");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void selectDialog() {
        System.out.println("Im folgenden können verschiedene Funktionalitäten per Zahleingabe ausgewählt werden\n" +
                "0: Konto eröffnen\n" +
                "1: Geld auf Konto laden\n" +
                "2: Kontobetrag anzeigenen\n" +
                "3: Geld abhebenen\n" +
                "4: Kummulierte Kontobeiträge anzeigen\n" +
                "5: Kontonummern einer Person anzeigen\n" +
                "6: Ende\n");

        int i = in.nextInt();
        switch (i) {
            case 0:
                createAccountDialog();
                break;
            case 1:
                createDepositDialog();
                break;
            case 2:
                createShowBalanceDialog();
                break;
            case 3:
                createWithDrawDialog();
                break;
            case 4:
                createShowCommulatedBalanceDialog();
                break;
            case 5:
                createShowAccountNumbersDialog();
                break;
            case 6:
                System.out.println("Client wird beendet");
                System.exit(0);
            default:
                System.out.println("Eingabe konnte nicht verarbietet werden. Bitte Wiederholen:\n");
        }
    }


    private void createAccountDialog() {
        try {
            System.out.println("Bitte Namen des Kontoinhabers eingeben");
            String name = in.next();
            System.out.println("Bitte Passwort des Kontoinhabers eingeben");
            String pw = in.next();
            int newAccount = bank.createNewAccount(name, pw);
            System.out.println("Konto für " + name + " wurde eröffnet. Kontonummer: " + newAccount);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void createDepositDialog() {
        System.out.println("Geld wird auf Konto überwiesen\n Bitte Kontonummer eingeben:");
        int accNo = in.nextInt();
        System.out.println("Bitte Name des Inhabers eingeben");
        String name = in.next();
        System.out.println("Bitte Betrag als Ganzzahl eingeben");
        int amount = in.nextInt();

        try {
            bank.deposit(accNo, name, amount);
            System.out.println("Geld wurde auf Konto überwiesen");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void createShowBalanceDialog() {
        System.out.println("Geldbetrag auf Konto anzeigen.\n Bitte Kontonummer eingeben:");
        int accNo = in.nextInt();
        System.out.println("Bitte Name des Inhabers eingeben");
        String name = in.next();
        System.out.println("Bitte Passwort eiongeben");
        String pw = in.next();
        try {
            Balance balance = bank.getBalance(accNo, name, pw);
            System.out.println("Auf Konto mit Kontonummer " + balance.getNumber() + "befinden sich " + balance.getBalance() + "€");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void createWithDrawDialog() {
        System.out.println("Geld vom Konto abheben.\n Name des Kontoinhabers eingeben: ");
        String name = in.next();
        System.out.println("Password eingeben:");
        String pw = in.next();
        System.out.println("Bitte Acount Nummer eingeben:");
        int accNo = in.nextInt();
        System.out.println("Bitte den gewünschten Betrag eingeben, der abgehoben wird:");
        int amount = in.nextInt();
        try {
            bank.withDraw(accNo, name, pw, amount);
            System.out.println("Es wurden " + amount + "€ vom Konto mit Nummer:" + accNo + "abgehoben.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void createShowCommulatedBalanceDialog() {
        System.out.println("Kummulierte Kontobeträhge für Person anzeigen\nName eingeben:");
        String name = in.next();
        try {
            double cumulatedBalanceForName = bank.getCumulatedBalanceForName(name);
            System.out.println("Die Person " + name + " hat insgesamt " + cumulatedBalanceForName + "€ auf den Konten");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void createShowAccountNumbersDialog() {
        System.out.println("Kontonummers für Person anzeigen.\nName eingeben:");
        String name = in.next();
        try {
            List<Integer> accountNoForName = bank.getAccountNoForName(name);
            System.out.println("Die Person " + name + " hat folgende Kontonummers:\n");
            for (Integer number : accountNoForName) {
                System.out.println(number);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

        /*
        int accNo = bank.createNewAccount("Daniel Krüger", "sicheresPassword");
        System.out.println("Neues Konto wurde eröffnet");


        bank.deposit(accNo, "Daniel Krüger", 100000000);
        System.out.println("Geld wurde auf Konto überwiesen");

        Balance balance = bank.getBalance(accNo, "Daniel Krüger", "sicheresPassword");
        System.out.println("Geld auf dem Konto: " + balance);


        System.out.println("Versuch: Falscher Benutzername");
        try {
            bank.withDraw(accNo, "The Evil one", "sicheresPassword", 1000000);
        } catch (Exception e) {
            System.out.println("ClientException: " + e.getClass());
        }


        System.out.println("Versuch: Falsches Password");
        try {
            bank.withDraw(accNo, "Daniel Krüger", "wrongPassword", 1000000);
        } catch (Exception e) {
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

    */
}
