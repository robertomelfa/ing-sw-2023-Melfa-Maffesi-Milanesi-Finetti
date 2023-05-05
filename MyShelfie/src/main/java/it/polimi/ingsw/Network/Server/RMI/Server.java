package it.polimi.ingsw.Network.Server.RMI;

import it.polimi.ingsw.Controller.RMI.RMIController;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.RemoteException;
import java.rmi.server.RMIClassLoader;


public class Server {

    public static void main(String[] argv) throws RemoteException, Exception{
        try {


        }catch (Exception e){
            System.out.println("[System] Server failed: " + e);
        }
    }

}
