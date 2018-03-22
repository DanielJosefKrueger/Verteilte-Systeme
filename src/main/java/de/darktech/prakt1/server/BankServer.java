package de.darktech.prakt1.server;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class BankServer {


    public static void main(String args[]) {
        try {

            Registry reg = LocateRegistry.createRegistry(1099);

            BankImpl bank = new BankImpl("myBank", reg);
            //AdderImpl ad = new AdderImpl ("rmi://localhost:1099/myComplexAdder");

            System.out.println("BankImpl Server ready.");


        } catch (Exception e) {
            System.out.println("ServerException: " + e.getMessage());
        }
    }


}
