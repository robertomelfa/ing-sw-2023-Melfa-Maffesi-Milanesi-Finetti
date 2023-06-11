package it.polimi.ingsw.Network.Client.RMI;

import it.polimi.ingsw.Controller.ControllerMain;
import it.polimi.ingsw.Model.*;
import it.polimi.ingsw.Network.Client.Socket.ClientClass;
import it.polimi.ingsw.Network.Server.RMI.GameInterface;
import it.polimi.ingsw.View.CLIView;
import it.polimi.ingsw.View.ControllerGui;
import it.polimi.ingsw.View.GUIView;
import it.polimi.ingsw.View.ViewClient;

import java.io.IOException;
import java.io.Serializable;
import java.rmi.NoSuchObjectException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;


public class Client extends UnicastRemoteObject implements GameClientInterface, Serializable{

//    private CLIView view=new CLIView(); // view
    private ViewClient view;

    private GameInterface serverRMI;

    private Timer timer;
    public Client() throws RemoteException{
        super();
    }


    /**
     * call the viewTable() method to print the game table on the client command line
     * @param board the game table we want to display
     * @throws RemoteException
     */
    public void receiveGameTable(GameTable board) throws RemoteException{
        view.viewGameTable(board);
    }

    /**
     * call the viewGrid() method to print the library on the client command line
     * @param library the library we want to display
     * @throws RemoteException
     */
    public void receiveLibrary(Library library) throws RemoteException{
        view.viewLibrary(library);
    }

    /**
     *
     * @param obj the player object
     * @throws RemoteException
     */
    public void receivePlayerObj(PlayerObj obj) throws RemoteException{
        view.viewPlayerObj(obj);
    }

    /**
     * used to perform the action of taking cards from the game table and store them in the player's library
     * use the getCardFromTable() and insert() methods defined in the model
     * @param gameLogic to perform the getCardFromTable method
     * @param server who the client is connected to
     * @throws RemoteException
     * @throws Exception
     */
    public GameLogic receiveGetCard(GameLogic gameLogic, GameInterface server) throws RemoteException, Exception{
        gameLogic = view.getTurn(gameLogic);

        return gameLogic;
    }

    /**
     * print a string message
     * @param msg string we want to print
     * @throws RemoteException
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
     *
     * @param server RMI server where the client will connect
     * @param client client wants to connect to the server
     * @param controller controller of the game
     * @throws RemoteException
     * @throws Exception
     */
    public void connection(GameInterface server, GameClientInterface client, ControllerMain controller) throws RemoteException, Exception{
        view = new CLIView();
        serverRMI = server;
        Scanner in = new Scanner(System.in);
        ClientClass client1;
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
                client1 = new ClientClass(client);
                client1.setPlayer(name);
                server.updatePlayers(client1);
            }catch(Exception e){
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
                    client1 = new ClientClass(client);
                    client1.setPlayer(name);
                    server.updatePlayers(client1);
                }catch(Exception e){
                }
            }
        }
        timer.cancel();
        server.stopConnecting();
        view.viewString("[System] connected!");
    }



    /**
     * print a string to CLI and return a string taken from input
     * @param viewMessage string we want to print
     * @return the string taken from input
     * @throws RemoteException
     */

    public String getStringFromClient(String viewMessage) throws RemoteException{
        Scanner in = new Scanner(System.in);
        view.viewString(viewMessage);
        return in.nextLine();
    }

    public void connectionGUI(GameInterface server, GameClientInterface client, ControllerMain controller, int num, String username) throws RemoteException, Exception{
        ClientClass client1;
        server.updateNumPlayers(num);
        client1 = new ClientClass(client);
        client1.setGui();
        client1.setPlayer(username);
        server.updatePlayers(client1);
    }


    public void connectionGUI(GameInterface server, GameClientInterface client, ControllerMain controller, String username) throws RemoteException, Exception {
        ClientClass client1;
        client1 = new ClientClass(client);
        client1.setPlayer(username);
        client1.setGui();
        server.updatePlayers(client1);
    }

    public void setControllerView(ControllerGui controllerGui){
        view = new GUIView();
        view.setController(controllerGui);
    }

    public void ping() throws RemoteException{}

    public void kill() throws NoSuchObjectException {
        try{
            UnicastRemoteObject.unexportObject(this, true);
        }catch(NoSuchObjectException e){
            System.out.println("Exit");
        }

    }

    public void viewPoints() throws RemoteException{
        viewPoints();
    }

    public void receivePoint(ArrayList<Player> playerList) throws RemoteException{
        view.viewPoints(playerList);
    }

    private synchronized void scheduleTimer() throws RemoteException {
        timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                try {
                    serverRMI.release();
                } catch (RemoteException e) {
                    throw new RuntimeException(e);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                System.exit(0);
            }
        };
        timer.schedule(task, 30000);
    }

}