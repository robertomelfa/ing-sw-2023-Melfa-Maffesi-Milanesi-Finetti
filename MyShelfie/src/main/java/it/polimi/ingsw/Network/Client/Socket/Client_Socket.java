package it.polimi.ingsw.Network.Client.Socket;

import it.polimi.ingsw.Model.*;
import it.polimi.ingsw.Network.Messages.Message;
import it.polimi.ingsw.Network.Messages.MessageType;
import it.polimi.ingsw.Network.Server.RMI.ServerRMI_Interface;
import it.polimi.ingsw.View.CLI.CLIView;
import it.polimi.ingsw.View.GUI.ControllerGui;
import it.polimi.ingsw.View.GUI.GUIView;
import it.polimi.ingsw.View.ViewClient_Interface;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

import static it.polimi.ingsw.Network.Messages.MessageType.*;


public class Client_Socket implements Serializable {


    private Socket socket;

    private ServerRMI_Interface serverRMI;

    private ViewClient_Interface view;

    private ObjectInputStream ois;

    private ObjectOutputStream oos;

    private Timer timer;

    private boolean gui = false;

    /**
     * The client choose the game he wants to connect to choosing the corresponding port
     *
     * @throws Exception
     */

    public  void start(ServerRMI_Interface server, String port) throws Exception{
        view = new CLIView();
        connect(port,8080, server);
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
     * creates a socket with host and port and uses it to connect the player to the game.
     * Requests the player's number only if the client is the first to connect to the game, then asks the nickname
     * also if the player is not the first connected.
     * @param host server the client wants to connect to
     * @param port port of the server
     */
    public  void connect(String host,int port, ServerRMI_Interface server) throws ClassNotFoundException, Exception{
        try {
            Socket socket = new Socket(host, port);
            this.socket = socket;
            serverRMI = server;
            Message msg;
            Scanner in = new Scanner(System.in);
            msg=receiveMessage();
            scheduleTimer();
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
                view.viewString("Waiting other players...\n");
                msg = new Message(MessageType.sendNickname, name);
                sendMessage(msg);
                msg = new Message(MessageType.sendBoolean, "false");
                sendMessage(msg);
                server.release();
            }
            timer.cancel();
        }catch(IOException e){
            view.viewString("Client fatal error");
        }
    }

    /**
     * the client start listening waiting for the server to send him the game table
     */
    public GameTable receiveGameTable() throws IOException,ClassNotFoundException{
        ois = new ObjectInputStream(socket.getInputStream());
        return (GameTable) ois.readObject();
    }

    /**
     * handles the turn from the client side. The client receive a message from the server and perform a different
     * action based on the type of the message received. The action the client can perform are: receiving the game table
     * receiving the library, picking the cards from the table. Then there are other message used to communicated game actions
     * to the client such as if a goal is achieved or notifying the client on the current turn player.
     * There are also message to handle the end of the game and to show the final leaderboard.
     * @throws Exception
     */
    public void clientlogic(ServerRMI_Interface server) throws Exception{
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
                view.viewPoints(receivePlayers());
            }else if(msg.getType() == MessageType.closeGame){
                view.viewString(msg.getMessage());
                socket.close();
                break;
            }else if(msg.getType() == MessageType.ping){

            }else if (msg.getType()==MessageType.endGame){
                view.viewString("Game is ended");
                break;
            } else if (msg.getType() == showLeaderboard) {
                view.viewLeaderboard(msg.getMessage());
            }else {
                view.viewString("Comunication error");
                break;
            }
        }
        socket.close();
        ois.close();
        oos.close();
    }

    /**
     * the client start listening and wait for the server to send him a message
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
     * the client start listening and waits for the server to send him a library
     * @return the library we received in socket input
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public Library receiveLibrary() throws IOException, ClassNotFoundException {
        ois = new ObjectInputStream(socket.getInputStream());
        return (Library) ois.readObject();
    }

    /**
     * the client start listening and waits for the server to send him the game logic
     * @return the gameLogic received in socket input
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public GameLogic receiveGameLogic() throws IOException, ClassNotFoundException {
        ois = new ObjectInputStream(socket.getInputStream());
        return (GameLogic) ois.readObject();
    }

    /**
     * the client start listening and waits for the server to send him the player object
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
     * the client start listening and waits for the server to send him the player's list
     * @return the player's list
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public ArrayList<Player> receivePlayers() throws IOException, ClassNotFoundException {
        ois = new ObjectInputStream(socket.getInputStream());
        return (ArrayList<Player>) ois.readObject();
    }

    /**
     * send an int to the server
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

    /**
     * set the controller for the GUIView
     * @param controllerGui the controller we want to set for the GUIView
     */
    public void setControllerGui(ControllerGui controllerGui){
        view = new GUIView();
        view.setController(controllerGui);
    }

    /**
     * calls connect gui to connect the client to the server and then starts the client logic to handle
     * all the interactions with the server
     * @param server server used to establish the connection
     * @param guiIp IP address we need to connect to
     * @param num player's number for the game
     * @param username username of the player
     * @throws Exception
     */
    public void startGUI(ServerRMI_Interface server, String guiIp, int num, String username) throws Exception {
        connectGUI(guiIp, 8080, server, num, username);
        try {
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

    /**
     * creates a socket with host and port to communicate with the server, then sends to the server the player's number
     * and the username of the player. The requestNumPlayer message is sent from the server only if the player is the
     * first connected to the game.
     * @param host server the client wants to connect to
     * @param port port of the server
     * @param server server used to establish the connection
     * @param num player's number
     * @param username username selected by the player
     * @throws IOException
     */
    public void connectGUI(String host, int port, ServerRMI_Interface server, int num, String username) throws IOException {
        try {
            Socket socket = new Socket(host, port);
            this.socket = socket;
            Message msg;
            msg = receiveMessage();
            if (msg.getType() == requestNumPlayer) {
                sendInt(num);
                msg = receiveMessage();
            }
            if (msg.getType() == requestNickname) {
                msg = new Message(MessageType.sendNickname, username);
                sendMessage(msg);
                msg = new Message(MessageType.sendBoolean, "true");
                sendMessage(msg);
            }
        } catch (IOException e) {
            view.viewString("client fatal error");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * creates a new thread to handle all the communication between client and server.
     * The clients listen to the server messages and performs a different action based
     * on the server message.
     * @param server //non è più usato
     * @throws Exception
     */
    public void clientLogicGui(ServerRMI_Interface server) throws Exception {

        new Thread(()->{
            int i = 0;
            while (i == 0) {
                    Message msg = null;
                    try {
                        msg = receiveMessage();
                    } catch (Exception ignored) {}
                    if(msg != null){
                        if (msg.getType() == MessageType.receiveGameTable) {
                            try {
                                GameTable table = receiveGameTable();

                                view.viewGameTable(table);
                            } catch (IOException e) {
                                System.out.println("error receiving gameTable");
                            } catch (RuntimeException | ClassNotFoundException e) {
                                System.out.println("error receiving gameTable");
                            }
                        } else if (msg.getType() == MessageType.receiveLibrary) {
                            try {
                                Library lib = receiveLibrary();

                                view.viewLibrary(lib);

                            } catch (Exception e) {
                                System.out.println("error");
                            }
                        } else if (msg.getType() == MessageType.getCard) {
                            try {
                                GameLogic gameLogic = receiveGameLogic();
                                gameLogic = view.getTurn(gameLogic);
                                sendGameLogic(gameLogic);
                            } catch (Exception e) {
                                System.out.println("Error");
                            }
                        } else if (msg.getType() == MessageType.objectiveCompleted) {
                            view.viewString(msg.getMessage());
                        } else if (msg.getType() == MessageType.printMessage) {
                            String message = msg.getMessage();
                            view.viewString(message);
                        } else if (msg.getType() == MessageType.receivePlayerObj) {
                            PlayerObj obj = null;
                            try {
                                obj = receivePlayerObj();
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            } catch (ClassNotFoundException e) {
                                throw new RuntimeException(e);
                            }
                            view.viewPlayerObj(obj);

                        } else if (msg.getType() == MessageType.notifyBeginTurn) {
                            try {
                                sendMessage(new Message(MessageType.printMessage, "2"));
                            } catch (Exception e) {
                                throw new RuntimeException(e);
                            }
                        } else if (msg.getType() == MessageType.receivePoint) {
                            try {
                                view.viewPoints(receivePlayers());
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            } catch (ClassNotFoundException e) {
                                throw new RuntimeException(e);
                            }
                        } else if (msg.getType() == MessageType.closeGame) {
                            view.viewString(msg.getMessage());
                            try {
                                socket.close();
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                            i = 1;
                        } else if (msg.getType() == MessageType.ping) {

                        } else if (msg.getType() == endGame) {
                            view.viewString("Game is ended");
                            i = 1;
                        } else if (msg.getType() == showLeaderboard) {
                            try{
                                view.viewLeaderboard(msg.getMessage());
                            }catch (IOException e){
                                System.out.println("IOException in clientlogicGui");
                            }
                        }else {
                            view.viewString("Comunication error");
                            i = 1;
                        }
                    }

            }
            try {
                socket.close();
                ois.close();
                oos.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }).start();
    }

    //@Test
    public void setView(){
        view = new CLIView();
    }

    /**
     * Timer that is called when we have to wait for a player's input.
     * After 30 seconds the TimerTask runs disconnecting the player.
     * It's used to handle inactive player and avoid a possible deadlock
     * @throws RemoteException
     */
    private void scheduleTimer() throws RemoteException {
        timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                System.exit(0);
            }
        };
        timer.schedule(task, 30000);
    }
}
