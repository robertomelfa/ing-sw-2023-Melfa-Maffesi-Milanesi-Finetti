package it.polimi.ingsw.Network.Client;



import it.polimi.ingsw.Network.Client.RMI.Client;
import it.polimi.ingsw.Network.Client.RMI.GameClientInterface;
import it.polimi.ingsw.Network.Client.Socket.Client_Socket;
import it.polimi.ingsw.Network.Server.RMI.GameInterface;

import java.io.Serializable;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;

public class ClientMain implements Serializable {

    public static void main(String[] args) throws Exception{
        System.out.println("[CLIENT] is running");
        Scanner scanner=new Scanner(System.in);
        String input;
        System.out.println("Choose A to start a Socket client\nChoose B to start a RMI client");
        input=scanner.next();
        switch(input.toUpperCase()){
            case "A":
                System.out.println("Starting Socket");
                startSocketClient thread=new startSocketClient();
                thread.run();
                break;
            case "B":
                try{
                    Registry registry = LocateRegistry.getRegistry("localhost", 1099);
                    GameInterface server = (GameInterface) registry.lookup("GameInterface");

                    Scanner in = new Scanner(System.in);
                    String name;
                    System.out.println("Enter the player's name");
                    name = in.nextLine();
                    GameClientInterface client = new Client(name);
                    client.connection2(server, client, server.getController());

                }catch(Exception e){
                    System.out.println("[System] Server failed: " + e);
                }
                break;
            default:
                System.out.println("Invalid choice");
        }
    }
}


class startSocketClient implements Runnable{

    @Override
    public void run() {
        Client_Socket client=new Client_Socket();
        try{
            client.start();
        }catch(Exception e){}

    }
}