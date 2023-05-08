package it.polimi.ingsw.Network.Client;



import it.polimi.ingsw.Network.Client.RMI.Client;
import it.polimi.ingsw.Network.Client.RMI.GameClientInterface;
import it.polimi.ingsw.Network.Client.Socket.Client_Socket;
import it.polimi.ingsw.Network.Server.RMI.GameInterface;
import it.polimi.ingsw.Network.Server.Server;

import java.io.Serializable;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;

public class ClientMain implements Serializable {

    private Server serverMain;
    public static void main(String[] args) throws Exception{
        Registry registry = LocateRegistry.getRegistry("localhost", 1099);
        GameInterface server = (GameInterface) registry.lookup("GameInterface");
        server.block();
        System.out.println("[CLIENT] is running");
        Scanner scanner=new Scanner(System.in);
        String input;
        System.out.println("Choose A to start a Socket client\nChoose B to start a RMI client");
        input=scanner.next();
        switch(input.toUpperCase()){
            case "A":
                System.out.println("Starting Socket");
                Client_Socket clientS = new Client_Socket();
                clientS.start(server);
                break;
            case "B":
                 try{
                     Scanner in = new Scanner(System.in);
                     String name;
                     System.out.println("Enter the player's name");
                     name = in.nextLine();
                     GameClientInterface clientR = new Client(name);
                     clientR.connection2(server, clientR, server.getController());
                     server.release();

                 }catch(Exception e){
                     System.out.println("[System] Server failed: " + e);
                 }
                 break;
            default:
                System.out.println("Invalid choice");
            }
    }
}