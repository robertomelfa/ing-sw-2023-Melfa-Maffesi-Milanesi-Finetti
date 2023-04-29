package it.polimi.ingsw.Network.Client.RMI;

import it.polimi.ingsw.Model.Game;
import it.polimi.ingsw.Model.GameTable;
import it.polimi.ingsw.Model.Player;
import it.polimi.ingsw.Network.Client.RMI.Client;
import it.polimi.ingsw.Network.Server.RMI.GameInterface;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface GameClientInterface extends Remote{
    public Player getPlayer() throws RemoteException;

    public void receiveGameTable(GameTable board) throws RemoteException;

}
