package it.polimi.ingsw.Network.Client.RMI;

import it.polimi.ingsw.Model.*;
import it.polimi.ingsw.Network.Client.RMI.Client;
import it.polimi.ingsw.Network.Server.RMI.GameInterface;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

public interface GameClientInterface extends Remote{
    public Player getPlayer() throws RemoteException;

    public void receiveGameTable(GameTable board) throws RemoteException;

    public void receiveLibrary(Library library) throws RemoteException;

    public void receiveGetCard(GameLogic gameLogic, GameInterface server) throws RemoteException, Exception;


    public void connection(GameInterface server, GameClientInterface client) throws RemoteException, Exception;

}
