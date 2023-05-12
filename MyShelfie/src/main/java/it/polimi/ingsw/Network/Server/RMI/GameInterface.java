package it.polimi.ingsw.Network.Server.RMI;

import it.polimi.ingsw.Controller.ControllerMain;
import it.polimi.ingsw.Model.*;
import it.polimi.ingsw.Network.Client.RMI.GameClientInterface;
import it.polimi.ingsw.Network.Client.Socket.ClientClass;
import it.polimi.ingsw.Network.Lock;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface GameInterface extends Remote{

    public void start(ControllerMain controller) throws RemoteException ;

    public void setClient(GameClientInterface c) throws RemoteException, Exception;
    public GameClientInterface getClient(int i) throws RemoteException;

    public List<GameClientInterface> getClientList() throws RemoteException ;

    public void gameTableToAll(GameTable board) throws RemoteException;

    public void gameTableToClient(GameTable board, GameClientInterface client) throws RemoteException,Exception;


    public boolean isFirstPlayer() throws RemoteException;

    public void setFirstPlayer() throws RemoteException; //useful while we manage only one game at time to avoid
                                                        //overlapping games

    public void messageToClient(String msg, GameClientInterface client) throws RemoteException;

    public void messageToAll(String msg) throws RemoteException;


    public ControllerMain getController() throws RemoteException;
    public void updateNumPlayers(int num) throws RemoteException;

    public void updatePlayers(ClientClass client) throws RemoteException;

    public Lock getLock() throws RemoteException;

    public void block() throws RemoteException, InterruptedException;

    public void release() throws RemoteException, InterruptedException;
    public boolean isLocked() throws RemoteException, InterruptedException;
}
