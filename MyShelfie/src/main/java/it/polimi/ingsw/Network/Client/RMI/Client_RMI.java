package it.polimi.ingsw.Network.Client.RMI;

import it.polimi.ingsw.Controller.ControllerMain;
import it.polimi.ingsw.Model.*;
import it.polimi.ingsw.Network.Client.ClientHandler;
import it.polimi.ingsw.Network.Server.RMI.ServerRMI_Interface;
import it.polimi.ingsw.View.CLI.CLIView;
import it.polimi.ingsw.View.GUI.ControllerGui;
import it.polimi.ingsw.View.GUI.GUIView;
import it.polimi.ingsw.View.ViewClient_Interface;

import java.io.IOException;
import java.io.Serializable;
import java.rmi.NoSuchObjectException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;


public class Client_RMI extends UnicastRemoteObject implements ClientRMI_Interface, Serializable{

    private ViewClient_Interface view;

    private ServerRMI_Interface serverRMI;

    private Timer timer;
    public Client_RMI() throws RemoteException{
        super();
    }


    /**
     * call the viewTable() method to show the game table on the view
     * @param board the game table we want to display
     * @throws RemoteException Exception
     */
    public void receiveGameTable(GameTable board) throws RemoteException{
        view.viewGameTable(board);
    }

    /**
     * call the viewGrid() method to show the library on the client view
     * @param library the library we want to display
     * @throws RemoteException Exception
     */
    public void receiveLibrary(Library library) throws RemoteException{
        view.viewLibrary(library);
    }

    /**
     * call the viewPlayerObj() method to show the library on the client view
     * @param obj the player object
     * @throws RemoteException Exception
     */
    public void receivePlayerObj(PlayerObj obj) throws RemoteException{
        view.viewPlayerObj(obj);
    }

    /**
     * used to perform the action of taking cards from the game table and store them in the player's library
     * use the getCardFromTable() and insert() methods defined in the model
     * @param gameLogic to perform the getCardFromTable method
     * @param server who the client is connected to
     * @throws RemoteException Exception
     * @throws Exception Exception
     */
    public GameLogic receiveGetCard(GameLogic gameLogic, ServerRMI_Interface server) throws RemoteException, Exception{
        gameLogic = view.getTurn(gameLogic);

        return gameLogic;
    }

    /**
     * shows a string message in the client's view
     * @param msg string we want to print
     * @throws RemoteException Exception
     */
    public void receiveMessage(String msg) throws RemoteException{
        if (msg.contains("Leaderboard")){
            try{
                view.viewLeaderboard(msg);
                return;
            }catch (IOException e){
                System.out.println("IOException in client RMI");
            }
        }
        view.viewString(msg);
        if(msg.equals("Stop game")){
            kill();
        }
    }

    /**
     * Establish the connection between the client and the server creating a ClientClass with
     * the username selected by the player. Implements a timer to handle inactive players
     * @param server RMI server where the client will connect
     * @param client client that wants to connect to the server
     * @param controller controller of the game
     * @throws RemoteException Exception
     * @throws Exception Exception
     */
    public void connection(ServerRMI_Interface server, ClientRMI_Interface client, ControllerMain controller) throws RemoteException, Exception{
        view = new CLIView();
        serverRMI = server;
        Scanner in = new Scanner(System.in);
        ClientHandler client1;
        int num;
        server.newClient(client);
        scheduleTimer();
        if(controller.getClientList().size() == 0) {
            try{
                String tempNum;
                do{
                    view.viewString("Insert players number");
                    tempNum = in.nextLine();
                    if(!tempNum.equals("2") && !tempNum.equals("4") && !tempNum.equals("3")){
                        view.viewString("Players number must be between 2 and 4. Retry");
                    }
                }while(!tempNum.equals("2") && !tempNum.equals("4") && !tempNum.equals("3"));
                num = Integer.parseInt(tempNum);
                server.updateNumPlayers(num);
                view.viewString("Enter the player's name");
                String name;
                name = in.next();
                client1 = new ClientHandler(client);
                client1.setPlayer(name);
                server.updatePlayers(client1);
            }catch(Exception ignored){
            }
        }else{
            if(controller.getClientList().size() < controller.getNumPlayers()){
                String name;
                try{
                    do{
                        view.viewString("Enter the player's name");
                        name = in.nextLine();
                        if(controller.checkExistingName(name)){
                            System.out.println("This name is used. Try again");
                        }
                    }while(controller.checkExistingName(name));
                    client1 = new ClientHandler(client);
                    client1.setPlayer(name);
                    server.updatePlayers(client1);
                }catch(Exception ignored){
                }
            }
        }
        timer.cancel();
        server.stopConnecting();
        view.viewString("Waiting other players...\n");
    }



    /**
     * shows a string on the client's view and then return a string taken from input
     * @param viewMessage string we want to print
     * @return the string taken from input
     * @throws RemoteException Exception
     */
    public String getStringFromClient(String viewMessage) throws RemoteException{
        Scanner in = new Scanner(System.in);
        view.viewString(viewMessage);
        return in.nextLine();
    }

    /**
     * creates a ClientHandler with the selected username for the client and add it has a player to the server.
     * Updates the number of players for the game and sets the GUI for the client.
     * This method is used only by the first player that connects to every game, since he is the one who select
     * the player's number
     * @param server : server the client needs to be connected to
     * @param client : the client
     * @param controller : the main controller
     * @param num : the player's number of the game
     * @param username : the username selected by this client
     * @throws RemoteException Exception
     * @throws Exception Exception
     */
    public void connectionGUI(ServerRMI_Interface server, ClientRMI_Interface client, ControllerMain controller, int num, String username) throws RemoteException, Exception{
        ClientHandler client1;
        server.updateNumPlayers(num);
        client1 = new ClientHandler(client);
        client1.setGui();
        client1.setPlayer(username);
        server.updatePlayers(client1);
    }

    /**
     * Creates a ClientHandler for the client and set the selected username then adds the ClientClass
     * has a player to the server.
     * @param server : server the client needs to be connected to
     * @param client : the client
     * @param controller : the main controller
     * @param username : the username selected by this client
     * @throws RemoteException Exception
     * @throws Exception Exception
     */
    public void connectionGUI(ServerRMI_Interface server, ClientRMI_Interface client, ControllerMain controller, String username) throws RemoteException, Exception {
        ClientHandler client1;
        client1 = new ClientHandler(client);
        client1.setPlayer(username);
        client1.setGui();
        server.updatePlayers(client1);
    }

    /**
     * method that sets the controller of the gui to the view
     * @param controllerGui controller of the gui
     */
    public void setControllerView(ControllerGui controllerGui){
        view = new GUIView();
        view.setController(controllerGui);
    }

    /**
     * ping message to check if the client is connected to the server
     * @throws RemoteException
     */
    public void ping() throws RemoteException{}

    /**
     * kill the program
     * @throws NoSuchObjectException Exception
     */
    public void kill() throws NoSuchObjectException {
        try{
            UnicastRemoteObject.unexportObject(this, true);
        }catch(NoSuchObjectException e){
            System.out.println("Exit");
        }

    }

    /**
     * display the points of the players
     * @param playerList list of player in the game
     * @throws RemoteException Exception
     */
    public void receivePoint(ArrayList<Player> playerList) throws RemoteException{
        view.viewPoints(playerList);
    }

    /**
     * Timer that is called when we have to wait for a player's input.
     * After 30 seconds the TimerTask runs disconnecting the player.
     * It's used to handle inactive player and avoid a possible deadlock.
     * @throws RemoteException
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