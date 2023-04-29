package it.polimi.ingsw.Network.Server.RMI;

import it.polimi.ingsw.Model.Card;
import it.polimi.ingsw.Model.Game;
import it.polimi.ingsw.Model.GameTable;
import it.polimi.ingsw.Model.Player;
import it.polimi.ingsw.Network.Client.RMI.Client;
import it.polimi.ingsw.Network.Client.RMI.GameClient;
import it.polimi.ingsw.Network.Client.RMI.GameClientInterface;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

public interface GameInterface extends Remote {

    public void setClient(GameClientInterface c) throws RemoteException, Exception;
    public GameClientInterface getClient(int i) throws RemoteException;

    public List<GameClientInterface> getClientList() throws RemoteException ;

    public void setGame(Game game) throws RemoteException;
    public Game getGame() throws RemoteException;

    public void gameTableToAll(GameTable board) throws RemoteException;

    public void gameTableToClient(GameTable board, int i) throws RemoteException,Exception;

}
