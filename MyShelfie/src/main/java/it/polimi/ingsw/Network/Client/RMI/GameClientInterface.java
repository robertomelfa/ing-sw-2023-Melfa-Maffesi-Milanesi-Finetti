package it.polimi.ingsw.Network.Client.RMI;

import it.polimi.ingsw.Controller.ControllerMain;
import it.polimi.ingsw.Model.*;
import it.polimi.ingsw.Network.Server.RMI.GameInterface;
import it.polimi.ingsw.View.ControllerGui;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

public interface GameClientInterface extends Remote{

    public void receiveGameTable(GameTable board) throws RemoteException;

    public void receiveLibrary(Library library) throws RemoteException;

    public void receivePlayerObj(PlayerObj obj) throws RemoteException;

    public GameLogic receiveGetCard(GameLogic gameLogic, GameInterface server) throws RemoteException, Exception;

    public void receiveMessage(String msg) throws RemoteException;

    public void connection(GameInterface server, GameClientInterface client, ControllerMain controller) throws RemoteException, Exception;

    public String getStringFromClient(String viewMessage) throws RemoteException;

    public void connectionGUI(GameInterface server, GameClientInterface client, ControllerMain controller, int num, String username) throws RemoteException, Exception;

    public void connectionGUI(GameInterface server, GameClientInterface client, ControllerMain controller, String username) throws RemoteException, Exception;

    public void ping() throws RemoteException;

    void receivePoint(ArrayList<Player> playerList) throws RemoteException;

}
