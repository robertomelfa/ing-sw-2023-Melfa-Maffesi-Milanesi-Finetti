package it.polimi.ingsw.Network.Client.RMI;

import it.polimi.ingsw.Controller.ControllerMain;
import it.polimi.ingsw.Model.*;
import it.polimi.ingsw.Network.Client.Socket.ClientClass;
import it.polimi.ingsw.Network.Server.RMI.GameInterface;
import it.polimi.ingsw.View.CLIView;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface GameClientInterface extends Remote{

    public void receiveGameTable(GameTable board) throws RemoteException;

    public void receiveLibrary(Library library) throws RemoteException;

    public GameLogic receiveGetCard(GameLogic gameLogic, GameInterface server) throws RemoteException, Exception;

    public void receiveMessage(String msg) throws RemoteException;



    public int getIntFromClient(String msg) throws RemoteException;


    public void connection(GameInterface server, GameClientInterface client, ControllerMain controller) throws RemoteException, Exception;

}
