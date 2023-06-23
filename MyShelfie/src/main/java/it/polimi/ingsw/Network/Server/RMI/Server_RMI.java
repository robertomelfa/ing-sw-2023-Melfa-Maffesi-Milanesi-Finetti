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


    public void start(ControllerMain controller) throws RemoteException{
        this.controller = controller;
        lock = new Lock();
    }

    public void stopConnecting() throws RemoteException{
        this.isConnecting = false;
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

    public void setFirstPlayer(){
        this.firstPlayer = false;
    }

    /**
     * used to send string message that will be displayed on all the clients terminal
     * @param msg string that we want to send to all the clients
     * @throws RemoteException Exception
     */

    public void messageToClient(String msg, ClientRMI_Interface client) throws RemoteException{
        client.receiveMessage(msg);
    }


    public ControllerMain getController() throws RemoteException{
        return this.controller;
    }

    public void updateNumPlayers(int num) throws RemoteException{
        this.controller.setNumPlayers(num);
    }

    public void updatePlayers(ClientHandler client) throws RemoteException{
        this.controller.addClient(client);
    }

    public synchronized Lock getLock() throws RemoteException{
        return lock;
    }

    public synchronized void block() throws RemoteException, InterruptedException{
        lock.acquire();
    }

    public synchronized void release() throws RemoteException, InterruptedException{
        lock.release();
    }

    public synchronized boolean isLocked() throws RemoteException, InterruptedException{ return lock.getLock(); }

    public void newClient(ClientRMI_Interface client) throws RemoteException{
        isConnecting = true;
        Thread thread = new Thread(()->{
            while(isConnecting){
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                try {
                    client.ping();
                } catch (Exception e) {
                    try {
                        release();
                    } catch (RemoteException | InterruptedException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            }

        });
        thread.start();
    }

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
