package it.polimi.ingsw.Network.Client.Socket;

import it.polimi.ingsw.Model.*;
import it.polimi.ingsw.Network.Messages.Message;
import it.polimi.ingsw.Network.Messages.MessageType;
import it.polimi.ingsw.Network.Server.RMI.GameInterface;
import it.polimi.ingsw.View.*;
import javafx.application.Platform;

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

//TODO sistemare le varie funzioni (troppo lunghe)
public class Client_Socket implements Serializable {


    private Socket socket;

    private GameInterface serverRMI;

    private ViewClient view;

    private ObjectInputStream ois;

    private ObjectOutputStream oos;

    private Timer timer;

    private boolean gui = false;

    /**
     * The client choose the game he wants to connect to choosing the corresponding port
     *
     * @throws Exception Exception
     */

    public  void start(GameInterface server, String port) throws Exception{
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
     * @param host server we want to connect
     * @param port port of the server
     */
    public  void connect(String host,int port, GameInterface server) throws ClassNotFoundException, Exception{
        try {
            this.socket = new Socket(host, port);
            serverRMI = server;
            view.viewString("Client is running...");
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
     * handles the turn from the client side. The client recive a message from the server and perform a different
     * action based on the typer of the message received. The action the client can perform are receiving the game table
     * receiving the library, picking the cards from the table.
     * @throws Exception Exception
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
     *
     * @return the Message received from the socket input
     * @throws IOException Exception
     * @throws ClassNotFoundException Exception
     * @throws Exception Exception
     */
    public Message receiveMessage() throws IOException, ClassNotFoundException, Exception {
        ois = new ObjectInputStream(socket.getInputStream());
        return (Message) ois.readObject();
    }

    /**
     * send a message to the server
     *
     * @param msg the message we want to send
     * @throws IOException Exception
     * @throws ClassNotFoundException Exception
     * @throws Exception Exception
     */
    public void sendMessage(Message msg) throws IOException, ClassNotFoundException, Exception {
        oos = new ObjectOutputStream(socket.getOutputStream());
        oos.writeObject(msg);
    }

    /**
     * @return the library we received in socket input
     * @throws IOException Exception
     * @throws ClassNotFoundException Exception
     */
    public Library receiveLibrary() throws IOException, ClassNotFoundException {
        ois = new ObjectInputStream(socket.getInputStream());
        return (Library) ois.readObject();
    }
    /**
     *
     * @return the gameLogic received in socket input
     * @throws IOException Exception
     * @throws ClassNotFoundException Exception
     */
    public GameLogic receiveGameLogic() throws IOException, ClassNotFoundException {
        ois = new ObjectInputStream(socket.getInputStream());
        return (GameLogic) ois.readObject();
    }

    /**
     *
     * @return the player's object
     * @throws IOException Exception
     * @throws ClassNotFoundException Exception
     */
    public PlayerObj receivePlayerObj() throws IOException, ClassNotFoundException {
        ois = new ObjectInputStream(socket.getInputStream());
        return (PlayerObj) ois.readObject();
    }

    public ArrayList<Player> receivePlayers() throws IOException, ClassNotFoundException {
        ois = new ObjectInputStream(socket.getInputStream());
        return (ArrayList<Player>) ois.readObject();
    }

    /**
     *
     * @param num number we want to send
     * @throws IOException Exception
     * @throws ClassNotFoundException Exception
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
     * @throws IOException Exception
     * @throws ClassNotFoundException Exception
     */
    public void sendGameLogic(GameLogic gameLogic) throws IOException, ClassNotFoundException {
        // dovro aggiungere tipologia messaggio

        oos = new ObjectOutputStream(socket.getOutputStream());
        oos.writeObject(gameLogic);
    }
    /**
     * method that sets the controller of the gui to the view
     * @param controllerGui controller of the gui
     */
    public void setControllerGui(ControllerGui controllerGui){
        view = new GUIView();
        view.setController(controllerGui);
    }

    /**
     * method that start the client socket with gui
     *
     * @param server RMI server where the client will connect
     * @param guiIp ip of the gui
     * @param num num of players in the game
     * @param username name of the player
     * @throws Exception Exception
     */
    public void startGUI(GameInterface server,String guiIp,int num, String username) throws Exception {
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
     * method that connect the player to the gui view
     *
     * @param host name of the host
     * @param port port of the host
     * @param server RMI server where the client will connect
     * @param num num of players
     * @param username name of the player
     * @throws IOException Exception
     */
    public void connectGUI(String host, int port, GameInterface server, int num, String username) throws IOException {
        try {
            this.socket = new Socket(host, port);
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
            server.release();
        } catch (IOException e) {
            view.viewString("client fatal error");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * handles the turn from the client side. The client recive a message from the server and perform a different
     * action based on the typer of the message received. The action the client can perform are receiving the game table
     * receiving the library, picking the cards from the table. (gui)
     * @throws Exception Exception
     */
    public void clientLogicGui(GameInterface server) throws Exception {
        System.out.println("started gui logic");

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
                            } catch (IOException | RuntimeException | ClassNotFoundException e) {
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
                            } catch (IOException | ClassNotFoundException e) {
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
                            } catch (IOException | ClassNotFoundException e) {
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
     * timer
     * @throws RemoteException Exception
     */
    private synchronized void scheduleTimer() throws RemoteException {
        timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                try {
                    serverRMI.release();
                } catch (RemoteException | InterruptedException e) {
                    throw new RuntimeException(e);
                }
                System.exit(0);
            }
        };
        timer.schedule(task, 30000);
    }
}
