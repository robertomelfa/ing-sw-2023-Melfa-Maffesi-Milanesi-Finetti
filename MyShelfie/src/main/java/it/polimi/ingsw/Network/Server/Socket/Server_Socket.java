package it.polimi.ingsw.Network.Server.Socket;

import it.polimi.ingsw.Model.*;
import it.polimi.ingsw.Network.Client.Socket.Client_Socket;
import it.polimi.ingsw.Network.Message;
import it.polimi.ingsw.Network.MessageType;
import it.polimi.ingsw.Controller.Socket.*;

import java.awt.*;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

public class Server_Socket implements Serializable {

    private  ArrayList<Socket> clientlist = new ArrayList<>();
    private SocketController controller;
    public  void start() {
        try {
            int port=8080;
            ServerSocket serversocket = new ServerSocket(port);
            System.out.println("[SERVER] is running on port "+port);
            Scanner input= new Scanner(System.in);
            int numplayers;
            do {
                System.out.println("Insert player number");
                numplayers=input.nextInt();
            }while (numplayers<2 || numplayers>4);
            int i=0;
            while (i<numplayers){
                int remaining=numplayers-i;
                System.out.println("Waiting for a client...\n"+remaining+" Clients remaining");
                Socket socket =serversocket.accept();
                clientlist.add(socket);
                i++;
            }
            System.out.println("Game is starting");
            try {
                controller=new SocketController(serversocket,clientlist,numplayers);
                System.out.println("Users:");
                for (int k=0; k<clientlist.size();k++){
                    System.out.println(clientlist.get(k));
                }
                /*System.out.println("Requesting nicknames");
                for (int k=0; k<clientlist.size();k++){
                    //requesting nickname
                    ObjectOutputStream oos =new ObjectOutputStream(clientlist.get(k).getOutputStream());
                    Message nickmessage=new Message(MessageType.requestNickname,null);
                    oos.writeObject(nickmessage);
                    System.out.println("Message sent to "+clientlist.get(k));
                    oos.close();

                    //receiving nickname
                    /*ObjectInputStream ois=new ObjectInputStream(clientlist.get(k).getInputStream());
                    Message nickresponse= (Message) ois.readObject();
                    System.out.println(nickresponse.toString());

                }*/
                controller.shufflePlayers();
                requestNicknames();
                gameTableToAll();
                sendLibrary();


                //richiesta e creazione nickname ai client
                //aggiunta players al game
                //Start turn

            }catch (Exception e){
                System.out.println("Exception in game");
            }




        }catch (IOException e){
            System.out.println("[SERVER] fatal error");
        }
    }


    public void sendLibrary() throws IOException{
        ObjectOutputStream oos =new ObjectOutputStream(controller.getCurrentPlayer().getOutputStream());
        oos.writeObject(controller.getGameLogic().getGame().getCurrentPlayer().getLibrary());
        System.out.println("Library sent to the current payer "+controller.getCurrentPlayer());
        oos.close();
    }

    public void requestNicknames() throws IOException,ClassNotFoundException,Exception{
        for (int i=0; i<clientlist.size(); i++){
            ObjectOutputStream oos =new ObjectOutputStream(clientlist.get(i).getOutputStream());
            Message message=new Message(MessageType.requestNickname,null);
            oos.writeObject(message);
            System.out.println("Message request sent to "+clientlist.get(i));
            oos.close();
            ObjectInputStream ois=new ObjectInputStream(clientlist.get(i).getInputStream());
            Message response= (Message) ois.readObject();
            if(response.getType()!=MessageType.sendNickname || response.getMessage()==null){
                throw new Exception("Exception in messages");
            }
            System.out.println("Nickname: "+response.getMessage()+" sent from "+clientlist.get(i));
            Player player=new Player(response.getMessage());
            controller.getGameLogic().getGame().addNewPlayer(player);
            ois.close();
        }
    }

    public void gameTableToAll() throws IOException{
        for (int i=0; i<clientlist.size(); i++){
            ObjectOutputStream oos =new ObjectOutputStream(clientlist.get(i).getOutputStream());
            oos.writeObject(controller.getGameLogic().getGame().getGameTable());
            System.out.println("GameTable sent to "+clientlist.get(i));
            oos.close();
        }
    }



}

