package it.polimi.ingsw.Network.Server.RMI;

import it.polimi.ingsw.Model.Game;
import it.polimi.ingsw.Model.GameTable;
import it.polimi.ingsw.Network.Client.RMI.Client;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface GameInterface extends Remote {

    public void setClient(Client c) throws RemoteException;
    public  Client getClient(int i) throws RemoteException;

    public Game getGame() throws RemoteException;
}
