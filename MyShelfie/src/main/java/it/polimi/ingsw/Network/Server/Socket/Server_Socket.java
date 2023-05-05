package it.polimi.ingsw.Network.Server.Socket;

import it.polimi.ingsw.Model.*;
import it.polimi.ingsw.Network.Client.Socket.ClientClass;
import it.polimi.ingsw.Controller.Socket.*;
import it.polimi.ingsw.Network.Messages.Message;
import it.polimi.ingsw.Network.Messages.MessageType;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

//TODO sistemare le funzioni, specie sendGameLogic dove viene gestito il pescaggio carte
public  class Server_Socket implements Serializable {

    private  ArrayList<ClientClass> clientlist = new ArrayList<>();
    public  void start() throws Exception{
        try {
            int port=8080;
            ServerSocket serversocket = new ServerSocket(port);
            int numplayers;
            int i=1;
            numplayers = firstClient(serversocket);
            while (i < numplayers){
                int remaining=numplayers-i;
                System.out.println("Waiting for a client...\n"+remaining+" Clients remaining");
                Socket socket = serversocket.accept();  // questo è il client
                ClientClass client = new ClientClass(socket);   // associo il client ad un player
                clientlist.add(client);

                Message msg=new Message(MessageType.requestNickname,null);
                sendMessage(msg, socket);
                clientlist.get(i).setPlayer(receiveMessage(socket).getMessage());  // nomino il player
                System.out.println("Player " + clientlist.get(i).getPlayer().getNickname() + " added");
                i++;
            }
            for (int k=0;k<clientlist.size();k++){
                System.out.println(clientlist.get(k).getSocket());
            }

            for (int j=0;j<clientlist.size();j++){
                System.out.println(clientlist.get(j).getSocket()+" nickname: "+clientlist.get(j).getPlayer().getNickname());
            }

        }catch (IOException e){
            System.out.println("[SERVER] fatal error");
        }
    }

    /**
     *  send the gametable to all the clients connected
     * @param gameTable that we want to send to all the plauers
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public void gameTableToAll(GameTable gameTable) throws IOException, ClassNotFoundException{
        for (int i=0; i < clientlist.size(); i++){
            sendGameTable(clientlist.get(i).getSocket(), gameTable);
        }
    }

    /**
     * Received a message in input from the socket passed as a parameter
     * @param socket the socket we wanto to recive the Message from
     * @return Message
     * @throws IOException
     * @throws ClassNotFoundException
     * @throws Exception
     */
    public Message receiveMessage(Socket socket) throws IOException, ClassNotFoundException, Exception {
        ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
        return (Message) ois.readObject();
    }

    public int receiveInt(Socket socket) throws IOException, ClassNotFoundException, Exception {
        ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
        return (int) ois.readObject();
    }

    /**
     * send a specific message to the socket. Both the message and the socket are specified when the method is called
     * @param msg the Message we want to send to the socket passed to the method as a parameter
     * @param socket
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public void sendMessage(Message msg, Socket socket) throws IOException, ClassNotFoundException{
        ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
        oos.writeObject(msg);
    }

    public void sendPlayerObj(Socket socket, PlayerObj obj) throws IOException, ClassNotFoundException{
        Message msg = new Message(MessageType.receivePlayerObj, null);
        sendMessage(msg, socket);
        ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
        oos.writeObject(obj);
    }

    /**
     * send the library to the socket. Both the client and the library are specified when the method is called
     * @param socket the socket we want to send the library to
     * @param lib the library we want to send to the client
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public void sendLibrary(Socket socket, Library lib) throws IOException, ClassNotFoundException{
        Message msg = new Message(MessageType.receiveLibrary, null);
        sendMessage(msg, socket);
        ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
        oos.writeObject(lib);
    }

    /**
     * send the gametable to the socket
     * @param socket the socket we want to send the gametable to
     * @param gameTable the gametable we want to send to the socket
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public void sendGameTable(Socket socket, GameTable gameTable) throws IOException, ClassNotFoundException{
        Message msg = new Message(MessageType.receiveGameTable, null);
        sendMessage(msg, socket);
        ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
        oos.writeObject(gameTable);
    }

    /**
     * Handles part of the process of picking cards from the table. Sever send to the client the gamelogic, his library
     * and a message of the "getCard" type, then receives the updated gamelogic and library from the client
     * @param client we want to pick cards from the game table
     * @param gameLogic the gamelogic we want to send to the client
     * @return the updated gamelogic after the client picked its cards from the game table
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public GameLogic sendGameLogic(ClientClass client, GameLogic gameLogic) throws IOException, ClassNotFoundException{
        Message msg = new Message(MessageType.getCard, null);
        sendMessage(msg, client.getSocket());
        // invio il gamelogic
        ObjectOutputStream oos = new ObjectOutputStream(client.getSocket().getOutputStream());
        oos.writeObject(gameLogic);

        // invio libreria
        oos = new ObjectOutputStream(client.getSocket().getOutputStream());
        oos.writeObject(client.getPlayer().getLibrary());

        // ricevo libreria current_client aggiornata
        ObjectInputStream ois = new ObjectInputStream(client.getSocket().getInputStream());
        client.getPlayer().setLibrary((Library) ois.readObject());


        // ricevo il game logic aggiornato
        ois = new ObjectInputStream(client.getSocket().getInputStream());
        return (GameLogic) ois.readObject();
    }

    /**
     *
     * @return the list of all the client connected
     */
    public ArrayList<ClientClass> getClientlist(){
        return clientlist;
    }

    public int firstClient(ServerSocket serversocket) throws IOException, ClassNotFoundException, Exception{
        Socket socket = serversocket.accept();  // questo è il client
        ClientClass client = new ClientClass(socket);   // associo il client ad un player
        clientlist.add(client);
        // ask num of players
        Message msg=new Message(MessageType.requestNumPlayer,null);
        sendMessage(msg, socket);
        // receive num of players
        int num = receiveInt(socket);
        System.out.println("num giocatori: " + num);
        // ask name of player
        msg=new Message(MessageType.requestNickname,null);
        sendMessage(msg, socket);
        clientlist.get(0).setPlayer(receiveMessage(clientlist.get(0).getSocket()).getMessage());
        return num;
    }
}

