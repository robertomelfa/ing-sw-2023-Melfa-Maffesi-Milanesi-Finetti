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


    public void setClient(GameClientInterface c) throws RemoteException,Exception{
        client.add(c);
        game.addNewPlayer(c.getPlayer());
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

    public void setGame(Game game) throws RemoteException{
        this.game = game;
    }

    public Game getGame() throws RemoteException{
        return this.game;
    }

    public void gameTableToAll(GameTable board) throws RemoteException{
        for(int i = 0; i < client.size(); i++){
            client.get(i).receiveGameTable(board);
        }
    }

    public void gameTableToClient(GameTable board, int i) throws RemoteException,Exception{
        client.get(i).receiveGameTable(board);
    }

    public void receiveTable(GameTable board) throws RemoteException, Exception{
        game.setGameTable(board);
    }
}
