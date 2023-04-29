package it.polimi.ingsw.Network.Client.Socket;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

public class Client_Socket {


    public  void start() {
        String ins;
        Scanner scanner=new Scanner(System.in);
        System.out.print("Make a choice:\nN: start a new game\nJ: join a game\n");
        ins=scanner.next();
        switch (ins.toUpperCase()){
            case "N":
                System.out.println("You chose N");
                //creare automaticamente una nuova istanza di server?

            break;
            case "J":
                System.out.println("You chose J");
                //ritornare la lista dei game attualmente attivi e permettere al client di scegliere una partita
                int port;
                System.out.println("Insert server port");
                port=scanner.nextInt();
                connect("127.0.0.1",port);
            break;
            default:
                System.out.println("Invalid choice");
            break;
        }




    }

    public static void connect(String host,int port){
        try {
            Socket socket = new Socket(host,port);
            System.out.println("Client is running...");



        }catch(IOException e){
            System.out.println("Client fatal error");
        }
    }


}


