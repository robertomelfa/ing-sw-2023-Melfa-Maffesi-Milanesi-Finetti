package it.polimi.ingsw.Network.Server.RMI;

import it.polimi.ingsw.Controller.ControllerMain;
import it.polimi.ingsw.Model.*;
import it.polimi.ingsw.Network.Client.RMI.ClientRMI_Interface;
import it.polimi.ingsw.Network.Client.ClientHandler;
import it.polimi.ingsw.Network.Lock;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class Server_RMI extends UnicastRemoteObject implements ServerRMI_Interface, Serializable {

    private ControllerMain controller;
    private boolean firstPlayer = true;

    private static Lock lock;

    private boolean isConnecting = false; // the client is trying to connect

    private boolean temp = false;

    public Server_RMI() throws RemoteException{
        super();
    }

    /**
     * link the controllerMain to the server and then initialize the lock
     * @param controller controllerMain of the game
     * @throws RemoteException
     */
    public void start(ControllerMain controller) throws RemoteException{
        this.controller = controller;
        lock = new Lock();
    }


    /**
     * send the game table only to a specific client
     * @param board the game table we want to send to the client

     * @throws RemoteException Exception
     * @throws Exception Exception
     */
    public void gameTableToClient(GameTable board, ClientRMI_Interface client) throws RemoteException,Exception{
        client.receiveGameTable(board);
    }


    /**
     * @return the value of the firstPlayer variable, used to understand who's the player who need to perform
     * the action of choosing the number of players allowed in the game
     */
    public boolean isFirstPlayer(){
        return this.firstPlayer;
    }

    /**
     * set the boolean firstPlayer when the first player connects to the server
     */
    public void setFirstPlayer(){
        this.firstPlayer = false;
    }

    /**
     * send a message only to a specific client
     * @param msg the message we want to send to the client
     * @param client the client that will receive the message
     * @throws RemoteException
     */
    public void messageToClient(String msg, ClientRMI_Interface client) throws RemoteException{
        client.receiveMessage(msg);
    }

    /**
     *
     * @return the controllerMain linked to the server
     * @throws RemoteException
     */
    public ControllerMain getController() throws RemoteException{
        return this.controller;
    }

    /**
     * updates the number of players of the game
     * @param num the number of players
     * @throws RemoteException
     */
    public void updateNumPlayers(int num) throws RemoteException{
        this.controller.setNumPlayers(num);
    }

    /**
     * adds the client to the game
     * @param client the client that want to join the game
     * @throws RemoteException
     */
    public void updatePlayers(ClientHandler client) throws RemoteException{
        this.controller.addClient(client);
    }

    /**
     *
     * @return the value of the lock
     * @throws RemoteException
     */
    public synchronized Lock getLock() throws RemoteException{
        return lock;
    }

    /**
     * set the lock
     * @throws RemoteException
     * @throws InterruptedException
     */
    public void block() throws RemoteException, InterruptedException{
        lock.acquire();
    }

    /**
     * releases the lock
     * @throws RemoteException
     * @throws InterruptedException
     */
    public void release() throws RemoteException, InterruptedException{
        lock.release();
    }

    /**
     *
     * @return the boolean value of the lock
     * @throws RemoteException
     * @throws InterruptedException
     */
    public boolean isLocked() throws RemoteException, InterruptedException{ return lock.getLock(); }

    public void setTemp(boolean set) throws RemoteException{
        temp = set;
    }

    public boolean getTemp() throws RemoteException{
        if(temp){
            temp = false;
            return true;
        }
        return false;
    }


}
