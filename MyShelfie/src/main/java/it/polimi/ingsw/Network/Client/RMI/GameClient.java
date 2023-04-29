package it.polimi.ingsw.Network.Client.RMI;

import it.polimi.ingsw.Controller.RMI.RMIController;
import it.polimi.ingsw.Model.*;
import it.polimi.ingsw.Network.Server.RMI.*;

import java.rmi.Naming;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Scanner;
import java.rmi.registry.LocateRegistry;
import java.rmi.RemoteException;

public class GameClient {


    public static void main(String[] argv) throws Exception{
        try{
            Registry registry = LocateRegistry.getRegistry("localhost", 1099);
            GameInterface server = (GameInterface) registry.lookup("GameInterface");

            Scanner in = new Scanner(System.in);
            String name;

            System.out.println("Enter the player's name");
            name = in.nextLine();
            GameClientInterface client = new Client(name);
            client.connection(server, client);


        }catch(Exception e){
            System.out.println("[System] Server failed: " + e);
        }
    }
}
