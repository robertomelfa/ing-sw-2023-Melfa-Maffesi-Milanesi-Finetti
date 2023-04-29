package it.polimi.ingsw.Network.Client;



import it.polimi.ingsw.Network.Client.Socket.Client_Socket;

import java.util.Scanner;

public class Client {

    public static void main(String[] args) {
        System.out.println("[CLIENT] is running");
        Scanner scanner=new Scanner(System.in);
        String input;
        System.out.println("Choose A to start a Socket server\nChoose B to start a RMI server");
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
        }
    }
}


class startSocketClient implements Runnable{


    @Override
    public void run() {
        Client_Socket client=new Client_Socket();
        client.start();
    }
}