package it.polimi.ingsw.Network.Server.RMI;

import it.polimi.ingsw.Controller.ControllerMain;
import it.polimi.ingsw.Model.*;
import it.polimi.ingsw.Network.Client.RMI.Client;
import it.polimi.ingsw.Network.Client.RMI.GameClientInterface;
import it.polimi.ingsw.Network.Client.Socket.ClientClass;
import it.polimi.ingsw.Network.Lock;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface GameInterface extends Remote{

    public void start(ControllerMain controller) throws RemoteException ;



    public void gameTableToClient(GameTable board, GameClientInterface client) throws RemoteException,Exception;


    public boolean isFirstPlayer() throws RemoteException;

    public void setFirstPlayer() throws RemoteException; //useful while we manage only one game at time to avoid
                                                        //overlapping games

    public void messageToClient(String msg, GameClientInterface client) throws RemoteException;


    public void stopConnecting() throws RemoteException;


    public ControllerMain getController() throws RemoteException;
    public void updateNumPlayers(int num) throws RemoteException;

    public void updatePlayers(ClientClass client) throws RemoteException;

    public Lock getLock() throws RemoteException;

    public void block() throws RemoteException, InterruptedException;

    public void release() throws RemoteException, InterruptedException;
    public boolean isLocked() throws RemoteException, InterruptedException;

    public void newClient(GameClientInterface client) throws RemoteException;
}
