package it.polimi.ingsw.Network.Server.Socket;

import it.polimi.ingsw.Controller.ControllerMain;
import it.polimi.ingsw.Model.*;
import it.polimi.ingsw.Network.Client.Socket.ClientClass;
import it.polimi.ingsw.Network.Lock;
import it.polimi.ingsw.Network.Messages.Message;
import it.polimi.ingsw.Network.Messages.MessageType;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

//TODO sistemare le funzioni, specie sendGameLogic dove viene gestito il pescaggio carte
public  class Server_Socket implements Serializable {

    private static ServerSocket serversocket;
    /**
     *
     * @param controller the controller who handle the game
     * @throws Exception
     */
    public  void start(ControllerMain controller) throws Exception{
        try {
            int port=8080;
            serversocket = new ServerSocket(port); // create the server
            System.out.println("[SERVER] started on port: "+port);

            while (!controller.getStart()){
                Socket socket = serversocket.accept();
                if(controller.getClientList().size()==0){   // first player?
                    controller = firstClient(serversocket, socket, controller);
                }else{
                    ClientClass client = new ClientClass(socket);   // associo il client ad un player
                    Message msg = new Message(MessageType.requestNickname, null);
                    sendMessage(msg, socket);
                    String name = receiveMessage(socket).getMessage();
                    client.setPlayer(name);
                    controller.addClient(client);
                }
            }
        }catch (IOException e){
            System.out.println("[SERVER] fatal error");
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

    /**
     *
     * @param socket the client
     * @return int from client (we use that for the number of the players)
     * @throws IOException
     * @throws ClassNotFoundException
     * @throws Exception
     */
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

    /**
     *
     * @param socket the server
     * @param obj player's object
     * @throws IOException
     * @throws ClassNotFoundException
     */
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
        // send Gamelogic
        ObjectOutputStream oos = new ObjectOutputStream(client.getSocket().getOutputStream());
        oos.writeObject(gameLogic);


        // receive the updated gameLogic
        ObjectInputStream ois = new ObjectInputStream(client.getSocket().getInputStream());
        return (GameLogic) ois.readObject();
    }

    /**
     *
     * @param serversocket server
     * @param socket client
     * @param controller controller of the game
     * @return number of players
     * @throws IOException
     * @throws ClassNotFoundException
     * @throws Exception
     */
    public ControllerMain firstClient(ServerSocket serversocket, Socket socket, ControllerMain controller) throws IOException, ClassNotFoundException, Exception{
          // questo è il client
        ClientClass client = new ClientClass(socket);   // associo il client ad un player
        // ask num of players
        Message msg=new Message(MessageType.requestNumPlayer,null);
        sendMessage(msg, socket);
        // receive num of players
        int num = receiveInt(socket);
        controller.setNumPlayers(num);
        System.out.println("Players: " + num);
        // ask name of player
        msg=new Message(MessageType.requestNickname,null);
        sendMessage(msg, socket);
        client.setPlayer(receiveMessage(socket).getMessage());
        controller.addClient(client);
        return controller;
    }

    public void close() throws IOException{
        serversocket.close();
    }

}

