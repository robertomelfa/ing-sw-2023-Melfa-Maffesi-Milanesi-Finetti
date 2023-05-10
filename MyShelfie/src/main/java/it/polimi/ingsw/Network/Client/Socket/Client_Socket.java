package it.polimi.ingsw.Network.Client.Socket;

import it.polimi.ingsw.Model.*;
import it.polimi.ingsw.Network.Messages.Message;
import it.polimi.ingsw.Network.Messages.MessageType;
import it.polimi.ingsw.Network.Server.RMI.GameInterface;
import it.polimi.ingsw.View.CLIView;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

//TODO sistemare le varie funzioni (troppo lunghe)
public class Client_Socket implements Serializable {

    private Socket socket;  // è il server


    /**
     * The client choose the game he want to connect to choosing the corresponding port
     * @throws Exception
     */
    public  void start(GameInterface server) throws Exception{
        Scanner scanner=new Scanner(System.in);

        int port;
        System.out.println("Insert server port");
        port=scanner.nextInt();
        connect("127.0.0.1",port);
        try {
            // start the logic of the client
            clientlogic(server);
        }catch (Exception e){
            try{
                socket.close();
            }catch (IOException i){
                System.out.println("Impossible to close socket");
            }
        }
    }

    /**
     *
     * @param host server we want to connect
     * @param port port of the server
     */
    public  void connect(String host,int port){
        try {
            Socket socket = new Socket(host,port);
            this.socket=socket;
            System.out.println("Client is running...");
        }catch(IOException e){
            System.out.println("Client fatal error");
        }
    }

    /**
     * the client start listening waiting for the server to send him the game table
     */
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

    /**
     * handles the turn from the client side. The client recive a message from the server and perform a different
     * action based on the typer of the message received. The action the client can perform are receiving the game table
     * receiving the library, picking the cards from the table.
     * @throws Exception
     */
    public void clientlogic(GameInterface server) throws Exception{
        // ricevo richiesta del nome e invio nome
        while(true){
            Message msg;
            msg=receiveMessage();
            if(msg.getType()==MessageType.requestNumPlayer){
                int numPlayers = 0;
                Scanner in = new Scanner(System.in);
                do{
                    System.out.println("Insert players number:");
                    numPlayers = in.nextInt();
                    if(numPlayers < 2 || numPlayers > 4){
                        System.out.println("Players number must be between 2 and 4. Retry");
                    }
                }while(numPlayers < 2 || numPlayers > 4);
                sendInt(numPlayers);
            }else if (msg.getType()==MessageType.requestNickname){
                System.out.println("Insert name: ");
                Scanner in = new Scanner(System.in);
                String name = in.nextLine();
                msg=new Message(MessageType.sendNickname,name);
                sendMessage(msg);
                server.release();   // tolgo il lock
            }else if(msg.getType()==MessageType.receiveGameTable){
                receiveGameTable();
            }else if(msg.getType()==MessageType.receiveLibrary){
                Library lib=receiveLibrary();
                lib.viewGrid();
            }else if(msg.getType()==MessageType.getCard){
                CLIView view = new CLIView();
                GameLogic gameLogic = receiveGameLogic();
                gameLogic = view.getCardFromTable(gameLogic);
                sendGameLogic(gameLogic);

            }else if (msg.getType()==MessageType.objectiveCompleted){
                System.out.println(msg.getMessage());

            }else if (msg.getType()==MessageType.printMessage){
                System.out.println(msg.getMessage());

            }else if (msg.getType()==MessageType.receivePlayerObj){
                PlayerObj obj=receivePlayerObj();
                obj.print();

            }else if (msg.getType()==MessageType.notifyBeginTurn){
                System.out.println(msg.getMessage());
                Scanner in =new Scanner(System.in);
                String choice=in.nextLine();
                sendMessage(new Message(MessageType.printMessage,choice));
            }else if (msg.getType()==MessageType.endGame){
                System.out.println("Game is ended");
                break;
            }else {
                System.out.println("Comunication error");
                break;
            }
        }
    }

    /**
     *
     * @return the Message received from the socket input
     * @throws IOException
     * @throws ClassNotFoundException
     * @throws Exception
     */
    public Message receiveMessage() throws IOException, ClassNotFoundException, Exception {
        ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
        return (Message) ois.readObject();
    }

    /**
     * send a message to the server
     * @param msg the message we want to send
     * @throws IOException
     * @throws ClassNotFoundException
     * @throws Exception
     */
    public void sendMessage(Message msg) throws IOException, ClassNotFoundException, Exception{
        ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
        oos.writeObject(msg);
    }

    /**
     * @return the library we received in socket input
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public Library receiveLibrary()throws IOException,ClassNotFoundException{
        ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
        return (Library) ois.readObject();
    }

    /**
     *
     * @return the gameLogic received in socket input
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public GameLogic receiveGameLogic() throws IOException, ClassNotFoundException{
        ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
        return (GameLogic) ois.readObject();
    }

    /**
     *
     * @return the player's object
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public PlayerObj receivePlayerObj() throws IOException, ClassNotFoundException{
        ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
        return (PlayerObj) ois.readObject();
    }

    /**
     * send the library to the server
     * @param library the library we want to send
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public void sendLibrary(Library library) throws IOException, ClassNotFoundException{
        // dovro aggiungere tipologia messaggio

        ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
        oos.writeObject(library);
    }

    /**
     *
     * @param num number we want to send
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public void sendInt(int num) throws IOException, ClassNotFoundException{
        // dovro aggiungere tipologia messaggio

        ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
        oos.writeObject(num);
    }

    /**
     * send the gamelogic to the server
     * @param gameLogic the gamelogic we want to send
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public void sendGameLogic(GameLogic gameLogic) throws IOException, ClassNotFoundException{
        // dovro aggiungere tipologia messaggio

        ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
        oos.writeObject(gameLogic);
    }

}
