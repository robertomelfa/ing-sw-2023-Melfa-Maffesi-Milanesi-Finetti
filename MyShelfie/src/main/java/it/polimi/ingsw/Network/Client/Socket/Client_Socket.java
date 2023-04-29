package it.polimi.ingsw.Network.Client.Socket;

import it.polimi.ingsw.Network.Message;
import it.polimi.ingsw.Network.MessageType;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

public class Client_Socket implements Serializable {

    private Socket serversocket;

    public  void start(){
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
                try {
                    clientlogic();
                }catch (Exception e){
                    try{
                        serversocket.close();
                    }catch (IOException i){
                        System.out.println("Impossible to close socket");
                    }

                }

            break;
            default:
                System.out.println("Invalid choice");
            break;
        }




    }

    public  void connect(String host,int port){
        try {
            Socket socket = new Socket(host,port);
            this.serversocket=socket;
            System.out.println("Client is running...");



        }catch(IOException e){
            System.out.println("Client fatal error");
        }
    }


    public void clientlogic() throws Exception{
        try {
            ObjectInputStream ois=new ObjectInputStream(serversocket.getInputStream());
            Message nickresponse= (Message) ois.readObject();
            if (nickresponse.getType()!= MessageType.requestNickname){
                throw new Exception("Message type not correct");
            }
            System.out.println("message type is correct");


            ois.close();
        }catch (IOException i){
            System.out.println("IOException");
        }catch (ClassNotFoundException c){
            System.out.println("ClassNotFoundException");
        }
    }


}


