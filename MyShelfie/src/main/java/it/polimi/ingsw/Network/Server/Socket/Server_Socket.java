package it.polimi.ingsw.Network.Server.Socket;

import it.polimi.ingsw.Controller.controllerMain;
import it.polimi.ingsw.Model.*;
import it.polimi.ingsw.Network.Client.Socket.ClientClass;
import it.polimi.ingsw.Network.Messages.Message;
import it.polimi.ingsw.Network.Messages.MessageType;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

//TODO sistemare le funzioni, specie sendGameLogic dove viene gestito il pescaggio carte
public  class Server_Socket implements Serializable {

    private  ArrayList<ClientClass> clientlist = new ArrayList<>();
    public  void start(controllerMain controller) throws Exception{
        try {
            int port=8080;
            ServerSocket serversocket = new ServerSocket(port);
            System.out.println("[SERVER] started on port: "+port);
            int numplayers;

            Socket socket = serversocket.accept(); // trovo client
            if(controller.getClientList().size()==0){
                numplayers = firstClient(serversocket, socket, controller);
                controller.setNumPlayers(numplayers);
            }else{
                ClientClass client = new ClientClass(socket);   // associo il client ad un player
                Message msg = new Message(MessageType.requestNickname, null);
                sendMessage(msg, socket);
                String name = receiveMessage(socket).getMessage();
                client.setPlayer(name);
                clientlist.add(client);
                controller.addClient(client);
                System.out.println(controller.getClientList().size());
            }

            while (controller.getClientList().size() < controller.getNumPlayers()){
            //    System.out.println("Waiting for a client...\n"+remaining+" Clients remaining");
                Socket socket1 = serversocket.accept();  // questo è il client
                Message msg = new Message(MessageType.requestNickname, null);
                sendMessage(msg, socket1);
                ClientClass client = new ClientClass(socket1);   // associo il client ad un player
                String name = receiveMessage(socket1).getMessage();
                client.setPlayer(name);
                clientlist.add(client);
                controller.addClient(client);
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

    public int firstClient(ServerSocket serversocket, Socket socket, controllerMain controller) throws IOException, ClassNotFoundException, Exception{
          // questo è il client
        ClientClass client = new ClientClass(socket);   // associo il client ad un player
        // ask num of players
        Message msg=new Message(MessageType.requestNumPlayer,null);
        sendMessage(msg, socket);
        // receive num of players
        int num = receiveInt(socket);
        System.out.println("Players: " + num);
        // ask name of player
        msg=new Message(MessageType.requestNickname,null);
        sendMessage(msg, socket);
        String name = receiveMessage(socket).getMessage();
        client.setPlayer(name);
        controller.addClient(client);
        clientlist.add(client);
        return num;
    }


    public void requestNicknameClients() throws IOException, ClassNotFoundException, Exception{
        ArrayList<String> nicknames=new ArrayList<>();
        for (int i=0;i<clientlist.size();i++){
            boolean askagain=true;
            Message message=null;
            while (askagain){
                sendMessage(new Message(MessageType.requestNickname,null),clientlist.get(i).getSocket());
                message=receiveMessage(clientlist.get(i).getSocket());
                if (message.getType()!=MessageType.sendNickname){
                    throw new Exception("Invalid message type");
                }
                if (!nicknames.contains(message.getMessage())){
                    askagain=false;
                }else {
                    sendMessage(new Message(MessageType.printMessage,message.getMessage()+" is already taken, choose another nickname"),clientlist.get(i).getSocket());
                }
            }
            nicknames.add(message.getMessage());
        }
        for (int j=0; j<nicknames.size();j++){
            clientlist.get(j).setPlayer(nicknames.get(j));
        }
    }

    public void notifyEnd() throws IOException, ClassNotFoundException{
        for (int i=0;i<clientlist.size();i++){
            sendMessage(new Message(MessageType.endGame,null),clientlist.get(i).getSocket());
        }
    }

}

