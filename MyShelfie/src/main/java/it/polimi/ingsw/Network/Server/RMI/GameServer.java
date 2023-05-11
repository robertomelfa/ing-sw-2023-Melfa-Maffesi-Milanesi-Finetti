package it.polimi.ingsw.Network.Server.RMI;

import it.polimi.ingsw.Controller.ControllerMain;
import it.polimi.ingsw.Model.*;
import it.polimi.ingsw.Network.Client.RMI.GameClientInterface;
import it.polimi.ingsw.Network.Client.Socket.ClientClass;
import it.polimi.ingsw.Network.Lock;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.LinkedList;
import java.util.List;

public class GameServer extends UnicastRemoteObject implements GameInterface, Serializable {

    private List<GameClientInterface> client;

    private ControllerMain controller;
    private int numPlayers;
    private boolean firstPlayer = true;

    private static Lock lock;

    /**
     * constructor for the game server: create a LinkedList to save all the client connected
     *
     * @throws RemoteException
     */
    public GameServer() throws RemoteException{
        super();
        try{
            client = new LinkedList<>();
        }catch(Exception e){}
    }

    public void start(ControllerMain controller) throws RemoteException, Exception{
        this.controller = controller;
        lock = new Lock();
        // wait clients
        while(controller.getClientList().size() < controller.getNumPlayers()){ }
    }

    /**
     * When a client connect to the server add it to the LinkedList and to the game
     * @param c , the client that connected
     * @throws RemoteException
     * @throws Exception
     */
    public void setClient(GameClientInterface c) throws RemoteException,Exception{
        client.add(c);
    }

    /**
     *
     * @param i int corresponding to the position of the client in the LinkedList
     * @return the client corresponding to the i position in the LinkedList
     * @throws RemoteException
     */
    public  GameClientInterface getClient(int i) throws RemoteException{
        try{
            return client.get(i);
        }catch(Exception e){
            return null;
        }

    }

    /**
     * @return the LinkedList of the clients saved in the server
     * @throws RemoteException
     */
    public List<GameClientInterface> getClientList() throws RemoteException {
        return client;
    }

    /**
     * sent the game table to all the client connected to the game
     * @param board the game table we want to send to all the clients
     * @throws RemoteException
     */
    public void gameTableToAll(GameTable board) throws RemoteException{
        for(int i = 0; i < client.size(); i++){
            client.get(i).receiveGameTable(board);
        }
    }

    /**
     * send the game table only to a specific client
     * @param board the game table we want to send to the client

     * @throws RemoteException
     * @throws Exception
     */
    public void gameTableToClient(GameTable board, GameClientInterface client) throws RemoteException,Exception{
        client.receiveGameTable(board);
    }


    /**
     * return the value of the firstPlayer variable, used to understand who's the player who need to perform
     * the action of choosing the number of players allowed in the game
     * @return
     */
    public boolean isFirstPlayer(){
        return this.firstPlayer;
    }

    public void setFirstPlayer(){
        this.firstPlayer = false;
    }

    /**
     * used to send string message that will be displayed on all the clients terminal
     * @param msg string that we want to send to all the clients
     * @throws RemoteException
     */

    public void messageToClient(String msg, GameClientInterface client) throws RemoteException{
        client.receiveMessage(msg);
    }

    public void messageToAll(String msg) throws RemoteException{
        for(int i = 0; i < client.size(); i++){
            client.get(i).receiveMessage(msg);
        }
    }

    public int getNumPlayers() throws RemoteException{
        return this.numPlayers;
    }

    public ControllerMain getController() throws RemoteException{
        return this.controller;
    }

    public void updateNumPlayers(int num) throws RemoteException{
        this.controller.setNumPlayers(num);
    }

    public void updatePlayers(ClientClass client) throws RemoteException{
        this.controller.addClient(client);
    }

    public Lock getLock() throws RemoteException{
        return lock;
    }

    public void block() throws RemoteException, InterruptedException{
        lock.acquire();
    }

    public void release() throws RemoteException, InterruptedException{
        lock.release();
    }

    public boolean isLocked() throws RemoteException, InterruptedException{ return lock.getLock(); }

}
