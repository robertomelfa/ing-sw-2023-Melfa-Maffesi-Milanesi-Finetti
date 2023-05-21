package it.polimi.ingsw.Network.Server.RMI;

import it.polimi.ingsw.Controller.ControllerMain;
import it.polimi.ingsw.Model.*;
import it.polimi.ingsw.Network.Client.RMI.GameClientInterface;
import it.polimi.ingsw.Network.Client.Socket.ClientClass;
import it.polimi.ingsw.Network.Lock;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class GameServer extends UnicastRemoteObject implements GameInterface, Serializable {

    private ControllerMain controller;
    private boolean firstPlayer = true;

    private static Lock lock;

    private static Lock messageLock = new Lock();

    public GameServer() throws RemoteException{
        super();
    }


    public void start(ControllerMain controller) throws RemoteException{
        this.controller = controller;
        lock = new Lock();
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
