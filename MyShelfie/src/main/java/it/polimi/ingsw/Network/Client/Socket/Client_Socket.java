package it.polimi.ingsw.Network.Client.Socket;

import it.polimi.ingsw.Model.GameLogic;
import it.polimi.ingsw.Model.GameTable;
import it.polimi.ingsw.Model.Library;
import it.polimi.ingsw.Model.PlayerObj;
import it.polimi.ingsw.Network.Messages.Message;
import it.polimi.ingsw.Network.Messages.MessageType;
import it.polimi.ingsw.Network.Server.RMI.GameInterface;
import it.polimi.ingsw.View.*;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;
import java.util.Objects;
import java.util.Scanner;

//TODO sistemare le varie funzioni (troppo lunghe)
public class Client_Socket implements Serializable {


    private Socket socket;

    private ViewClient view;

    private ObjectInputStream ois;

    private ObjectOutputStream oos;

    private boolean gui = false;

    /**
     * The client choose the game he want to connect to choosing the corresponding port
     * @throws Exception
     */
    public  void start(GameInterface server) throws Exception{
        view = new CLIView();
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
                    String tempNum;
                    do{
                        view.viewString("Insert players number:");
                        tempNum=in.nextLine();

                        if(!tempNum.equals("2") && !tempNum.equals("4") && !tempNum.equals("3")){
                            view.viewString("Players number must be between 2 and 4. Retry");
                        }
                    }while(!tempNum.equals("2") && !tempNum.equals("4") && !tempNum.equals("3"));
                    numPlayers = Integer.parseInt(tempNum);
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
                msg = new Message(MessageType.sendBoolean, "false");
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
        ois = new ObjectInputStream(socket.getInputStream());
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
        // get message from server
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
            }else if(msg.getType() == MessageType.closeGame){
                view.viewString(msg.getMessage());
                socket.close();
                break;
            }else if(msg.getType() == MessageType.ping){

            }else if (msg.getType()==MessageType.endGame){
                view.viewString("Game is ended");
                break;
            } else {
                view.viewString("Comunication error");
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
        ois = new ObjectInputStream(socket.getInputStream());
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
        oos = new ObjectOutputStream(socket.getOutputStream());
        oos.writeObject(msg);
    }

    /**
     * @return the library we received in socket input
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public Library receiveLibrary() throws IOException, ClassNotFoundException {
        ois = new ObjectInputStream(socket.getInputStream());
        return (Library) ois.readObject();
    }
    /**
     *
     * @return the gameLogic received in socket input
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public GameLogic receiveGameLogic() throws IOException, ClassNotFoundException {
        ois = new ObjectInputStream(socket.getInputStream());
        return (GameLogic) ois.readObject();
    }

    /**
     *
     * @return the player's object
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public PlayerObj receivePlayerObj() throws IOException, ClassNotFoundException {
        ois = new ObjectInputStream(socket.getInputStream());
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

        oos = new ObjectOutputStream(socket.getOutputStream());
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

        oos = new ObjectOutputStream(socket.getOutputStream());
        oos.writeObject(gameLogic);
    }

    public void setControllerGui(ControllerGui controllerGui){
        gui = true;
        view.setController(controllerGui);
    }

    public void startGUI(GameInterface server) throws Exception {
        System.out.println("socket");
        connectGUI("127.0.0.1", 8080, server);
        view = new GUIView();
        try {
            while (!gui){
                // wait until the set of the controller
            }
            //start the logic of the client
            clientLogicGui(server);
        } catch (Exception e) {
            try {
                socket.close();
            } catch (IOException i) {
                view.viewString("Impossible to close socket");
            }
        }
    }

    public void connectGUI(String host,int port, GameInterface server) {
        try {
            Socket socket = new Socket(host, port);
            this.socket = socket;
        }catch(IOException e){
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    public void clientLogicGui(GameInterface server) throws Exception {
        Message msg;
        System.out.println("started gui logic");

        loop: while(true) {
            msg = receiveMessage();

            switch (msg.getType()) {
                case receiveGameTable, receiveLibrary, receivePlayerObj -> {
                }
                case notifyBeginTurn -> {
                    sendMessage(new Message(MessageType.printMessage, "2"));
                }
                case getCard -> {
                    GameLogic gameLogic = receiveGameLogic();
                    gameLogic = view.getTurn(gameLogic);
                    sendGameLogic(gameLogic);
                }
                case objectiveCompleted, printMessage, receivePoint -> {
                    view.viewString(msg.getMessage());
                }
                case requestNickname, requestNumPlayer -> { }
                case endGame -> {
                    view.viewString("Game is ended");
                    break loop;
                }
                default -> {
                    view.viewString("Communication error");
                    break loop;
                }
            }
        }
    }

    //@Test
    public void setView(){
        view = new CLIView();
    }
}
