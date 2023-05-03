package it.polimi.ingsw.Network.Client.Socket;

import it.polimi.ingsw.Model.GameTable;
import it.polimi.ingsw.Network.Messages.Message;
import it.polimi.ingsw.Network.Messages.MessageType;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Client_Socket implements Serializable {

    private Socket socket;  // è il server

    public  void start() throws Exception{
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
                        socket.close();
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
            this.socket=socket;
            System.out.println("Client is running...");
        }catch(IOException e){
            System.out.println("Client fatal error");
        }
    }

    public void receiveGameTable(){
        try{
            ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
            GameTable gametable = (GameTable) ois.readObject();
            gametable.viewTable();
        }catch (IOException i){
            System.out.println("IOException");
        }catch (ClassNotFoundException c){
            System.out.println("ClassNotFoundException");
        }
    }

    public void clientlogic() throws Exception{
        // ricevo richiesta del nome e invio nome
        Message msg;
        msg=receiveMessage();
        if (msg.getType()!=MessageType.requestNickname){
            throw new Exception("Error in message type");
        }
        else {
            System.out.println("Insert Player name");
        }
        Scanner in = new Scanner(System.in);
        String name = in.nextLine();
        msg=new Message(MessageType.sendNickname,name);
        sendMessage(msg);
        receiveGameTable();
    }
    public Message receiveMessage() throws IOException, ClassNotFoundException, Exception {
        ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
        return (Message) ois.readObject();
    }

    public void sendMessage(Message msg) throws IOException, ClassNotFoundException, Exception{
        ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
        oos.writeObject(msg);
    }
}


