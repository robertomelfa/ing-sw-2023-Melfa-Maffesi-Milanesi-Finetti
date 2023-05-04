package it.polimi.ingsw.Network.Client.Socket;

import it.polimi.ingsw.Model.*;
import it.polimi.ingsw.Network.Messages.Message;
import it.polimi.ingsw.Network.Messages.MessageType;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

//TODO sistemare le varie funzioni (troppo lunghe)
public class Client_Socket implements Serializable {

    private Socket socket;  // è il server

    public  void start() throws Exception{
        Scanner scanner=new Scanner(System.in);

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
            GameTable gameTable = (GameTable) ois.readObject();
            gameTable.viewTable();
        }catch (IOException i){
            System.out.println("IOException");
        }catch (ClassNotFoundException c){
            System.out.println("ClassNotFoundException");
        }
    }

    public void clientlogic() throws Exception{
        // ricevo richiesta del nome e invio nome
        int i = 0;
        while(i < 10){
            Message msg;
            msg=receiveMessage();
            if (msg.getType()==MessageType.requestNickname){
                System.out.println("Inserisci nome: ");
                Scanner in = new Scanner(System.in);
                String name = in.nextLine();
                msg=new Message(MessageType.sendNickname,name);
                sendMessage(msg);
            }else if(msg.getType()==MessageType.receiveGameTable){
                receiveGameTable();
            }else if(msg.getType()==MessageType.receiveLibrary){
                Library lib=receiveLibrary();
                lib.viewGrid();
            }else if(msg.getType()==MessageType.getCard){
                System.out.println("devo pescare una carta");
                ArrayList<Card> cards;
                GameLogic gameLogic = receiveGameLogic();
                Library library = receiveLibrary();
                cards = gameLogic.getCardFromTable();
                library.insert(cards);
                sendLibrary(library);
                sendGameLogic(gameLogic);
            }else{

                System.out.println("errore comunicazione");
                break;
            }
            i++;        // per ora ho messo un while che si ripete per 6 volte
        }
    }
    public Message receiveMessage() throws IOException, ClassNotFoundException, Exception {
        ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
        return (Message) ois.readObject();
    }

    public void sendMessage(Message msg) throws IOException, ClassNotFoundException, Exception{
        ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
        oos.writeObject(msg);
    }

    public Library receiveLibrary()throws IOException,ClassNotFoundException{
        ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
        return (Library) ois.readObject();
    }

    public GameLogic receiveGameLogic() throws IOException, ClassNotFoundException{
        ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
        return (GameLogic) ois.readObject();
    }

    public void sendLibrary(Library library) throws IOException, ClassNotFoundException{
        // dovro aggiungere tipologia messaggio

        ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
        oos.writeObject(library);
    }

    public void sendGameLogic(GameLogic gameLogic) throws IOException, ClassNotFoundException{
        // dovro aggiungere tipologia messaggio

        ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
        oos.writeObject(gameLogic);
    }

}


