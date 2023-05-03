package it.polimi.ingsw.Network.Server;

import it.polimi.ingsw.Controller.Socket.SocketController;
import it.polimi.ingsw.Network.Server.RMI.GameInterface;
import it.polimi.ingsw.Network.Server.Socket.Server_Socket;

import javax.naming.ldap.Control;
import java.io.IOException;
import java.net.ServerSocket;
import java.rmi.server.UnicastRemoteObject;
import java.util.Scanner;

//TODO gametable to single socket
//TODO library to single socket
//TODO take cards from library


public class Server{
    public static void main(String[] args) {
        System.out.println("[SERVER] is running");
        Scanner scanner=new Scanner(System.in);
        String input;
        System.out.println("Choose A to start a Socket server\nChoose B to start a RMI server");
        input=scanner.next();
        switch (input.toUpperCase()){
            case "A":
                System.out.println("Starting Socket");
                startSocketServer thread = new startSocketServer();
                thread.run();

               break;
            case "B":



                break;
            default:
                System.out.println("Invalid choice");

        }
    }
}

class startSocketServer implements Runnable{

    @Override
    public void run() {
        System.out.println("New Thread created");
        Server_Socket server=new Server_Socket();
        try{
            server.start();
            SocketController controller = new SocketController(server);
            while(true){
                controller.takeTurn();
            }
        }catch(Exception e){}

    }
}
