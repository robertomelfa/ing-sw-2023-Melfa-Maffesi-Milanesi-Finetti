package it.polimi.ingsw.Network.Server.RMI;

import it.polimi.ingsw.Model.*;
import it.polimi.ingsw.Network.Client.RMI.Client;
import it.polimi.ingsw.Network.Client.RMI.GameClientInterface;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class GameServer extends UnicastRemoteObject implements GameInterface{

    private List<GameClientInterface> client;

    private GameInterface server;

    private Game game;
    public GameServer() throws RemoteException{
        super();
        try{
            client = new LinkedList<>();
        }catch(Exception e){}
    }


    public void setClient(GameClientInterface c) throws RemoteException{
        client.add(c);
        try{
            game.addNewPlayer(c.getPlayer());
        }catch(Exception e){}
    }

    public  GameClientInterface getClient(int i) throws RemoteException{
        try{
            return client.get(i);
        }catch(Exception e){
            return null;
        }

    }

    public List<GameClientInterface> getClientList() throws RemoteException {
        return client;
    }

    public void setGame(Game game, GameClientInterface c) throws RemoteException{
        this.game = game;
        for(int i = 0; i < client.size(); i++){
            client.remove(i);
            this.game.removePlayer(i);
            UnicastRemoteObject.unexportObject(client.get(i), false);
        }
        client.add(c);
        try{
            this.game.addNewPlayer(c.getPlayer());
        }catch(Exception e){}
    }

    public Game getGame() throws RemoteException{
        return this.game;
    }

    public void messageToAll(GameTable board) throws RemoteException{
        for(int i = 0; i < client.size(); i++){
            client.get(i).receiveGameTable(board);
        }
    }
}
