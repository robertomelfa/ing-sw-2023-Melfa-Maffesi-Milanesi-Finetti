package it.polimi.ingsw.Network.Client;



import it.polimi.ingsw.Model.*;
import it.polimi.ingsw.Network.Client.Socket.Client_Socket;

import java.util.Scanner;

public class Client {

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