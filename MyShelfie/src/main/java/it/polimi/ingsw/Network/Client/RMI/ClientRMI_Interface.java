package it.polimi.ingsw.Network.Client.RMI;

import it.polimi.ingsw.Controller.ControllerMain;
import it.polimi.ingsw.Model.*;
import it.polimi.ingsw.Network.Server.RMI.ServerRMI_Interface;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

public interface ClientRMI_Interface extends Remote{

    public void receiveGameTable(GameTable board) throws RemoteException;

    public void receiveLibrary(Library library) throws RemoteException;

    public void receivePlayerObj(PlayerObj obj) throws RemoteException;

    public GameLogic receiveGetCard(GameLogic gameLogic, ServerRMI_Interface server) throws RemoteException, Exception;

    public void receiveMessage(String msg) throws RemoteException;

    public void connection(ServerRMI_Interface server, ClientRMI_Interface client, ControllerMain controller) throws RemoteException, Exception;

    public String getStringFromClient(String viewMessage) throws RemoteException;

    public void connectionGUI(ServerRMI_Interface server, ClientRMI_Interface client, ControllerMain controller, int num, String username) throws RemoteException, Exception;

    public void connectionGUI(ServerRMI_Interface server, ClientRMI_Interface client, ControllerMain controller, String username) throws RemoteException, Exception;

    public void ping() throws RemoteException;

    void receivePoint(ArrayList<Player> playerList) throws RemoteException;

}
