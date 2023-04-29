package it.polimi.ingsw.Network.Client.RMI;

import it.polimi.ingsw.Model.*;
import it.polimi.ingsw.Network.Server.RMI.GameInterface;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Scanner;

import static it.polimi.ingsw.Model.Card.NONE;

public class Client extends UnicastRemoteObject implements GameClientInterface {
    private Player player;

    Client(String name) throws Exception{
        this.player = new Player(name);
    }

    public Player getPlayer() throws RemoteException {
        return this.player;
    }

    public void receiveGameTable(GameTable board) throws RemoteException{
        board.viewTable();
    }

    public void receiveLibrary(Library library) throws RemoteException{
        library.viewGrid();
    }

}
