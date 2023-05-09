package it.polimi.ingsw.Network.Client;



import it.polimi.ingsw.Network.Client.RMI.Client;
import it.polimi.ingsw.Network.Client.RMI.GameClientInterface;
import it.polimi.ingsw.Network.Client.Socket.Client_Socket;
import it.polimi.ingsw.Network.Server.RMI.GameInterface;
import it.polimi.ingsw.Network.Server.Server;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

public class ClientMain implements Serializable {

    private Server serverMain;

    public static void main(String[] args) throws Exception {
        Registry registry = LocateRegistry.getRegistry("localhost", 1099);
        GameInterface server = (GameInterface) registry.lookup("GameInterface");
        System.out.println("[CLIENT] is running");
        Scanner scanner = new Scanner(System.in);
        String input;
        if (server.isLocked()) {
            System.out.println("Another client is connecting");
        }
        while (server.isLocked()) {
        }
        System.out.println("Choose A to start a Socket client\nChoose B to start a RMI client");
        server.block();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                System.out.println("Input not received within 15 seconds. Disconnecting from server ..-");
                try {
                    server.release();
                } catch (RemoteException e) {
                    throw new RuntimeException(e);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                System.exit(0);
            }
        };

        Timer timer = new Timer(true);
        timer.schedule(task, 15000);
        if (scanner.hasNext()) {
            input = scanner.next();
            switch (input.toUpperCase()) {
                case "A":
                    timer.cancel();
                    System.out.println("Starting Socket");
                    Client_Socket clientS = new Client_Socket();
                    clientS.start(server);
                    server.release();
                    break;
                case "B":
                    try {
                        timer.cancel();
                        Scanner in = new Scanner(System.in);
                        String name;
                        System.out.println("Enter the player's name");
                        name = in.nextLine();
                        GameClientInterface clientR = new Client(name);
                        clientR.connection2(server, clientR, server.getController());
                        server.release();


                    } catch (Exception e) {
                        System.out.println("[System] Server failed: " + e);
                    }
                    break;
                default:
                    System.out.println("Invalid choice");
            }
        }
    }
}