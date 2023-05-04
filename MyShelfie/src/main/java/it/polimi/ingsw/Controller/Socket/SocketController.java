package it.polimi.ingsw.Controller.Socket;

import it.polimi.ingsw.Model.Game;
import it.polimi.ingsw.Model.GameLogic;
import it.polimi.ingsw.Model.Player;
import it.polimi.ingsw.Network.Client.Socket.*;
import it.polimi.ingsw.Network.Messages.Message;
import it.polimi.ingsw.Network.Messages.MessageType;
import it.polimi.ingsw.Network.Server.Socket.*;
import it.polimi.ingsw.Network.Client.Socket.ClientClass;

import java.io.IOException;
import java.io.Serializable;
import java.net.ServerSocket;
import java.net.Socket;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

// TODO sistemare la gestione del turno (per ora è una bozza)
// TODO gestione obiettivi
// TODO scambio messaggi server-client per inizio/fine turno ed eventualmente per la gestione delle carte
// TODO ping al client per disconnessioni

public class SocketController  implements Serializable {

    private Server_Socket server;
    private  boolean endGame=false;
    private int chair;
    private ArrayList<ClientClass> players;
    private int playersIterator;
    private GameLogic gameLogic;
    private ClientClass current_client;

    /**
     * constructor for the controller
     * @param server
     * @throws Exception
     */

    public SocketController(Server_Socket server) throws Exception{
        this.server = server;
        this.players = server.getClientlist();
        this.endGame=false;
        Game game=new Game(server.getClientlist().size());
        gameLogic=new GameLogic(game);
    }

    /**
     * @return the list of all the Client connected
     */
    public ArrayList<ClientClass> getPlayers(){
        return players;
    }

    /**
     * @return the client who has the chair
     */
    public ClientClass getChair(){
        return players.get(chair);
    }

    /**
     * @return client corresponding to the current player
     */
    public ClientClass getCurrentPlayer(){
        return current_client;
    }


    public GameLogic getGameLogic() {
        return gameLogic;
    }

    /**
     * update the current player and handles part of the end game procedures
     * @throws Exception
     */
    public void updateCurrentPlayer() throws Exception{
        if(!endGame){
            playersIterator++;
            if(players.size() == playersIterator){
                playersIterator = 0;
                current_client = players.get(playersIterator);
            }else {
                current_client = players.get(playersIterator);
            }
        }else {
            playersIterator++;
            if(players.size() == playersIterator){
                gameLogic.getGame().checkEnd();
                throw new Exception("GAME IS ENDED");   // probably this will be in the view; metterei più un messaggio che un'eccezione che da meno problemi @simone
            }
            else {
                current_client = players.get(playersIterator);
            }
        }

    }

    /**
     * shuffle the player list to create a random order. Then set the chair to the player in the first position of
     * of the list
     */
    public void shufflePlayers(){
        try {
            Collections.shuffle(players);
            chair = 0;
            playersIterator = 0;
            current_client=players.get(0);
        } catch (Exception e){
            System.out.println("Error setting up order...");
        }
    }

    /**
     * add a new player to the game
     * @param player the player we want to add to the game
     * @throws Exception
     */
    public void addPlayerToGame(Player player) throws Exception{
        gameLogic.getGame().addNewPlayer(player);
    }

    public void takeTurn() throws IOException, Exception {
        shufflePlayers();

        while(true){ // test

            server.gameTableToAll(gameLogic.getGameTable());

            server.sendLibrary(current_client.getSocket(), current_client.getPlayer().getLibrary());

            gameLogic = server.sendGameLogic(current_client, gameLogic);

            updateCurrentPlayer();
        }
    }
}
