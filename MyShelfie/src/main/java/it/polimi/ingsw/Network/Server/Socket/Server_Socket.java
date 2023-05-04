package it.polimi.ingsw.Network.Server.Socket;

import it.polimi.ingsw.Model.Library;
import it.polimi.ingsw.Network.Client.Socket.ClientClass;
import it.polimi.ingsw.Controller.Socket.*;
import it.polimi.ingsw.Network.Messages.Message;
import it.polimi.ingsw.Network.Messages.MessageType;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

public class Server_Socket implements Serializable {

    private  ArrayList<ClientClass> clientlist = new ArrayList<>();
    private SocketController controller;
    public  void start() throws Exception{
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
            while (i < numplayers){
                int remaining=numplayers-i;
                System.out.println("Waiting for a client...\n"+remaining+" Clients remaining");
                Socket socket = serversocket.accept();  // questo è il client
                ClientClass client = new ClientClass(socket);   // associo il client ad un player
                clientlist.add(client);
                i++;
            }
            for (int k=0;k<clientlist.size();k++){
                System.out.println(clientlist.get(k).getSocket());
            }

            // chiedo il nome a tutti i client (potrei fare una funzione unica)
            for(int j = 0; j < clientlist.size(); j++){
                Message msg=new Message(MessageType.requestNickname,null);
                sendMessage(msg, j);
                clientlist.get(j).setPlayer(receiveMessage(j).getMessage());  // nomino il player
                System.out.println("Player " + clientlist.get(j).getPlayer().getNickname() + " added");
            }

            for (int j=0;j<clientlist.size();j++){
                System.out.println(clientlist.get(j).getSocket()+" nickname: "+clientlist.get(j).getPlayer().getNickname());
            }

            System.out.println("Game is starting");

            try {
                controller = new SocketController(serversocket,clientlist,numplayers);
                controller.shufflePlayers();
                gameTableToAll();
                for (int j=0;j<clientlist.size();j++){
                    sendLibrary(j,clientlist.get(j).getPlayer().getLibrary());
                }

            }catch (Exception e){
                System.out.println("Exception in game");
            }

        }catch (IOException e){
            System.out.println("[SERVER] fatal error");
        }
    }

    public void gameTableToAll() throws IOException{
        for (int i=0; i < clientlist.size(); i++){
            ObjectOutputStream oos = new ObjectOutputStream(clientlist.get(i).getSocket().getOutputStream());
            oos.writeObject(controller.getGameLogic().getGame().getGameTable());
            System.out.println("GameTable sent to " + clientlist.get(i).getPlayer().getNickname());
        }
    }

    public Message receiveMessage(int i) throws IOException, ClassNotFoundException, Exception {
        ObjectInputStream ois = new ObjectInputStream(clientlist.get(i).getSocket().getInputStream());
        return (Message) ois.readObject();
    }

    public void sendMessage(Message msg, int i) throws IOException, ClassNotFoundException{
        ObjectOutputStream oos = new ObjectOutputStream(clientlist.get(i).getSocket().getOutputStream());
        oos.writeObject(msg);
    }

    public void sendLibrary(int i, Library lib) throws IOException{
        ObjectOutputStream oos = new ObjectOutputStream(clientlist.get(i).getSocket().getOutputStream());
        oos.writeObject(lib);
    }

}

