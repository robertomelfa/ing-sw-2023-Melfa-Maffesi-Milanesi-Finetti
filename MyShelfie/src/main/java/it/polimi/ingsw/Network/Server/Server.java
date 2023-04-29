package it.polimi.ingsw.Network.Server;

import it.polimi.ingsw.Network.Server.Socket.Server_Socket;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.Scanner;

public class Server {
    public static void main(String[] args) {
        System.out.println("[SERVER] is running");
        Scanner scanner=new Scanner(System.in);
        String input;
        System.out.println("Choose A to start a Socket server\nChoose B to start a RMI server");
        input=scanner.next();
        switch (input.toUpperCase()){
            case "A":
                System.out.println("Starting Socket");
                startSocketServer thread=new startSocketServer();
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
        server.start();
    }
}
