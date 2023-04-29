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

    public void setGame(Game game) throws RemoteException{
        this.game = game;
        for(int i = 0; i < client.size(); i++){
            client.remove(i);
            this.game.removePlayer(i);
        }
    }

    public Game getGame() throws RemoteException{
        return this.game;
    }

    public void GameTableToAll(GameTable board) throws RemoteException{
        for(int i = 0; i < client.size(); i++){
            client.get(i).receiveGameTable(board);
        }
    }

    public void GameTableToClient(GameTable board, int i) throws RemoteException{
        client.get(i).receiveGameTable(board);
    }

}
