package it.polimi.ingsw.Network.Server.RMI;

import it.polimi.ingsw.Controller.ControllerMain;
import it.polimi.ingsw.Model.*;
import it.polimi.ingsw.Network.Client.RMI.ClientRMI_Interface;
import it.polimi.ingsw.Network.Client.ClientHandler;
import it.polimi.ingsw.Network.Lock;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ServerRMI_Interface extends Remote{

    public void start(ControllerMain controller) throws RemoteException ;

    public void gameTableToClient(GameTable board, ClientRMI_Interface client) throws RemoteException,Exception;


    public boolean isFirstPlayer() throws RemoteException;

    public void setFirstPlayer() throws RemoteException; //useful while we manage only one game at time to avoid
                                                        //overlapping games

    public void messageToClient(String msg, ClientRMI_Interface client) throws RemoteException;


    public void stopConnecting() throws RemoteException;


    public ControllerMain getController() throws RemoteException;
    public void updateNumPlayers(int num) throws RemoteException;

    public void updatePlayers(ClientHandler client) throws RemoteException;

    public Lock getLock() throws RemoteException;

    public void block() throws RemoteException, InterruptedException;

    public void release() throws RemoteException, InterruptedException;
    public boolean isLocked() throws RemoteException, InterruptedException;

    public void newClient(ClientRMI_Interface client) throws RemoteException;

    public void setTemp(boolean set) throws RemoteException;

    public boolean getTemp() throws RemoteException;
}
