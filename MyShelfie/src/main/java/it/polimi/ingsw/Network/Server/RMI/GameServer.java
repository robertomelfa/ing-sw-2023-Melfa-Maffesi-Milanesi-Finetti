package it.polimi.ingsw.Network.Server.RMI;

import it.polimi.ingsw.Model.*;
import it.polimi.ingsw.Network.Client.RMI.Client;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class GameServer extends UnicastRemoteObject implements GameInterface{

    private List<Client> client;

    private Game game;
    public GameServer(Game game) throws RemoteException{
        super();
        try{
            this.game = game;
            client = new LinkedList<>();
        }catch(Exception e){}
    }


    public void setClient(Client c) throws RemoteException{
        client.add(c);
        try{
            game.addNewPlayer(c.getPlayer());
        }catch(Exception e){}
    }

    public  Client getClient(int i) throws RemoteException{
        try{
            return client.get(i);
        }catch(Exception e){
            return null;
        }

    }

    public Game getGame() throws RemoteException{
        return this.game;
    }

}
