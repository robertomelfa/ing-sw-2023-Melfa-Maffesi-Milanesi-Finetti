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

    private boolean firstPlayer = true;

    /**
     * constructor for the game server: create a LinkedList to save all the client connected
     *
     * @throws RemoteException
     */
    public GameServer() throws RemoteException{
        super();
        try{
            client = new LinkedList<>();
        }catch(Exception e){}
    }

    /**
     * When a client connect to the server add it to the LinkedList and to the game
     * @param c , the client that connected
     * @throws RemoteException
     * @throws Exception
     */
    public void setClient(GameClientInterface c) throws RemoteException,Exception{
        client.add(c);
        game.addNewPlayer(c.getPlayer());
    }

    /**
     *
     * @param i int corresponding to the position of the client in the LinkedList
     * @return the client corresponding to the i position in the LinkedList
     * @throws RemoteException
     */
    public  GameClientInterface getClient(int i) throws RemoteException{
        try{
            return client.get(i);
        }catch(Exception e){
            return null;
        }

    }

    /**
     * @return the LinkedList of the clients saved in the server
     * @throws RemoteException
     */
    public List<GameClientInterface> getClientList() throws RemoteException {
        return client;
    }

    /**
     * used to set the game in the server
     * @param game that we want to set in the sever
     * @throws RemoteException
     */
    public void setGame(Game game) throws RemoteException{
        this.game = game;
    }

    /**
     *
     * @return the game stored in the server
     * @throws RemoteException
     */
    public Game getGame() throws RemoteException{
        return this.game;
    }

    /**
     * sent the game table to all the client connected to the game
     * @param board the game table we want to send to all the clients
     * @throws RemoteException
     */
    public void gameTableToAll(GameTable board) throws RemoteException{
        for(int i = 0; i < client.size(); i++){
            client.get(i).receiveGameTable(board);
        }
    }

    /**
     * send the game table only to a specific client
     * @param board the game table we want to send to the client
     * @param i int corresponding to the position in the LinkedList of the client we want to send the board to
     * @throws RemoteException
     * @throws Exception
     */
    public void gameTableToClient(GameTable board, int i) throws RemoteException,Exception{
        client.get(i).receiveGameTable(board);
    }

    /**
     * update the game table of the game stored in the server
     * @param board the updated version of the board
     * @throws RemoteException
     * @throws Exception
     */
    public void receiveTable(GameTable board) throws RemoteException, Exception{
        game.setGameTable(board);
    }

    /**
     * used to notify all that the game has come to its end
     * @throws RemoteException
     */
    public void notifyEnd() throws RemoteException{
        for(int i = 0; i < client.size(); i++){
            client.get(i).endMessage();
        }
    }

    /**
     * notify the turn player that it's its turn and all the other clients that they need to wait
     * until the end of the current player turn
     * @param current_client the client who needs to perform its turn
     * @throws RemoteException
     */
    public void notifyTurnPlayer(GameClientInterface current_client) throws RemoteException{
        for(int i = 0; i < client.size(); i++){
            if(client.get(i).equals(current_client)){
                client.get(i).receiveMessage("It's your turn");
            }else{
                client.get(i).receiveMessage("Wait, it's " + current_client.getPlayer().getNickname() +"'s turn");
            }

        }
    }

    /**
     * return the value of the firstPlayer variable, used to understand who's the player who need to perform
     * the action of choosing the number of players allowed in the game
     * @return
     */
    public boolean isFirstPlayer(){
        return this.firstPlayer;
    }

    public void setFirstPlayer(){
        this.firstPlayer = false;
    }

    /**
     * used to send string message that will be displayed on all the clients terminal
     * @param msg string that we want to send to all the clients
     * @throws RemoteException
     */
    public void messageToAll(String msg) throws RemoteException{
        for(int i = 0; i < client.size(); i++){
            client.get(i).receiveMessage(msg);
        }
    }

}
