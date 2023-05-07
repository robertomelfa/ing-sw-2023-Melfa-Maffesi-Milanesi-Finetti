package it.polimi.ingsw.Network.Server.RMI;

import it.polimi.ingsw.Controller.controllerMain;
import it.polimi.ingsw.Model.*;
import it.polimi.ingsw.Network.Client.RMI.GameClientInterface;
import it.polimi.ingsw.Network.Client.Socket.ClientClass;

import java.io.Serializable;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface GameInterface extends Remote{

    public void start(controllerMain controller) throws RemoteException, Exception;

    public void setClient(GameClientInterface c) throws RemoteException, Exception;
    public GameClientInterface getClient(int i) throws RemoteException;

    public List<GameClientInterface> getClientList() throws RemoteException ;

    public void gameTableToAll(GameTable board) throws RemoteException;

    public void gameTableToClient(GameTable board, GameClientInterface client) throws RemoteException,Exception;


    public void notifyEnd() throws RemoteException;

    public void notifyTurnPlayer(GameClientInterface current_client) throws RemoteException;

    public boolean isFirstPlayer() throws RemoteException;

    public void setFirstPlayer() throws RemoteException; //useful while we manage only one game at time to avoid
                                                         //overlapping games

    public void messageToAll(String msg) throws RemoteException;

    public int getNumPlayers() throws RemoteException;

    public controllerMain getController() throws RemoteException;
    public void updateNumPlayers(int num) throws RemoteException;

    public void updatePlayers(ClientClass client) throws RemoteException;
}
