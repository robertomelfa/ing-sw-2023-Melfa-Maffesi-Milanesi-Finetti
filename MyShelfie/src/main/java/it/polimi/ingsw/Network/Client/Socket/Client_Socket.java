package it.polimi.ingsw.Network.Client.Socket;

import it.polimi.ingsw.Model.*;
import it.polimi.ingsw.Network.Messages.Message;
import it.polimi.ingsw.Network.Messages.MessageType;
import it.polimi.ingsw.Network.Server.RMI.GameInterface;
import it.polimi.ingsw.View.CLIView;
import javafx.stage.Stage;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

//TODO sistemare le varie funzioni (troppo lunghe)
public class Client_Socket implements Serializable {

    private Socket socket;  // è il server
    private CLIView view = new CLIView();

    private boolean gui = false;

    /**
     * The client choose the game he want to connect to choosing the corresponding port
     * @throws Exception
     */
    public  void start(GameInterface server) throws Exception{

        connect("127.0.0.1",8080, server);
        try {
            // start the logic of the client
            clientlogic(server);
        } catch (Exception e) {
            try {
                socket.close();
            } catch (IOException i) {
                view.viewString("Impossible to close socket");
            }
        }
    }

    /**
     * @param host server we want to connect
     * @param port port of the server
     */
    public  void connect(String host,int port, GameInterface server) throws ClassNotFoundException, Exception{
        try {
            Socket socket = new Socket(host, port);
            this.socket = socket;
            view.viewString("Client is running...");
            Message msg;
            Scanner in = new Scanner(System.in);
            msg=receiveMessage();

            if(server.getController().getClientList().size()==0){
                if(msg.getType()==MessageType.requestNumPlayer){
                    int numPlayers = 0;
                    do{
                        view.viewString("Insert players number:");
                        numPlayers = in.nextInt();

                        if(numPlayers < 2 || numPlayers > 4){
                            view.viewString("Players number must be between 2 and 4. Retry");
                        }
                    }while(numPlayers < 2 || numPlayers > 4);
                    sendInt(numPlayers);
                }
                msg=receiveMessage();
            }

            if (msg.getType()==MessageType.requestNickname){
                String name;
                do {
                    view.viewString("Insert name: ");
                    name = in.next();

                    if (server.getController().checkExistingName(name)) {
                        view.viewString("This name is used, try again");
                    }
                } while (server.getController().checkExistingName(name));
                msg = new Message(MessageType.sendNickname, name);
                sendMessage(msg);
                server.release();
            }
        }catch(IOException e){
            view.viewString("Client fatal error");
        }
    }

    /**
     * the client start listening waiting for the server to send him the game table
     */
    public GameTable receiveGameTable() throws IOException,ClassNotFoundException{
        ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
        GameTable gameTable = (GameTable) ois.readObject();
        return gameTable;
    }

    /**
     * handles the turn from the client side. The client recive a message from the server and perform a different
     * action based on the typer of the message received. The action the client can perform are receiving the game table
     * receiving the library, picking the cards from the table.
     * @throws Exception
     */
    public void clientlogic(GameInterface server) throws Exception{
        // ricevo richiesta del nome e invio nome
        Message msg;
        Scanner in = new Scanner(System.in);
        while(true){
            msg=receiveMessage();
            if(msg.getType()==MessageType.receiveGameTable){
                GameTable table=receiveGameTable();
                view.viewGameTable(table);
            } else if (msg.getType() == MessageType.receiveLibrary) {
                Library lib = receiveLibrary();
                view.viewLibrary(lib);
            } else if (msg.getType() == MessageType.getCard) {
                GameLogic gameLogic = receiveGameLogic();
                gameLogic = view.getTurn(gameLogic);
                sendGameLogic(gameLogic);

            } else if (msg.getType() == MessageType.objectiveCompleted) {
                view.viewString(msg.getMessage());

            } else if (msg.getType() == MessageType.printMessage) {
                view.viewString(msg.getMessage());

            } else if (msg.getType() == MessageType.receivePlayerObj) {
                PlayerObj obj = receivePlayerObj();
                view.viewPlayerObj(obj);

            } else if (msg.getType() == MessageType.notifyBeginTurn) {
                view.viewString(msg.getMessage());
                String choice=in.nextLine();
                sendMessage(new Message(MessageType.printMessage,choice));
            }else if(msg.getType() == MessageType.receivePoint){
                view.viewString(msg.getMessage());
            }else if (msg.getType()==MessageType.endGame){
                view.viewString("Game is ended");
                break;
            } else {
                view.viewString("Comunication error");
                break;
            }
        }
        System.exit(0);
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
     *
     * @param msg the message we want to send
     * @throws IOException
     * @throws ClassNotFoundException
     * @throws Exception
     */
    public void sendMessage(Message msg) throws IOException, ClassNotFoundException, Exception {
        ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
        oos.writeObject(msg);
    }

    /**
     * @return the library we received in socket input
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public Library receiveLibrary() throws IOException, ClassNotFoundException {
        ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
        return (Library) ois.readObject();
    }

    /**
     *
     * @return the gameLogic received in socket input
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public GameLogic receiveGameLogic() throws IOException, ClassNotFoundException {
        ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
        return (GameLogic) ois.readObject();
    }

    /**
     *
     * @return the player's object
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public PlayerObj receivePlayerObj() throws IOException, ClassNotFoundException {
        ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
        return (PlayerObj) ois.readObject();
    }

    /**
     *
     * @param num number we want to send
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public void sendInt(int num) throws IOException, ClassNotFoundException {
        // dovro aggiungere tipologia messaggio

        ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
        oos.writeObject(num);
    }

    /**
     * send the gamelogic to the server
     *
     * @param gameLogic the gamelogic we want to send
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public void sendGameLogic(GameLogic gameLogic) throws IOException, ClassNotFoundException {
        // dovro aggiungere tipologia messaggio

        ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
        oos.writeObject(gameLogic);
    }


    public void startGUI(GameInterface server, Stage stage) throws Exception {
        connectGUI("127.0.0.1", 8080);
        try {
            // start the logic of the client
            //clientlogicGUI(server, stage);
        } catch (Exception e) {
            try {
                socket.close();
            } catch (IOException i) {
                view.viewString("Impossible to close socket");
            }
        }
    }

    public void connectGUI(String host, int port){
        try {
            Socket socket = new Socket(host, port);
            this.socket = socket;
        } catch (IOException e) {
        }
    }
}
