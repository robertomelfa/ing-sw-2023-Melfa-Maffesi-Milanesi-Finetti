package it.polimi.ingsw.Controller.Socket;

import it.polimi.ingsw.Model.Game;
import it.polimi.ingsw.Model.GameLogic;
import it.polimi.ingsw.Model.Player;
import it.polimi.ingsw.Network.Client.RMI.GameClientInterface;

import java.io.Serializable;
import java.net.ServerSocket;
import java.net.Socket;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SocketController implements Serializable {

    private ServerSocket server;
    private  boolean endGame=false;
    private int chair;
    private ArrayList<Socket> players= new ArrayList<>();
    private int playersIterator;
    private GameLogic gameLogic;
    private Socket current_client;

    public SocketController(ServerSocket server, ArrayList<Socket> players, int numOfPlayers) throws Exception{
        this.server=server;
        this.players=players;
        this.endGame=false;
        Game game=new Game(numOfPlayers);
        gameLogic=new GameLogic(game);
    }

    public ArrayList<Socket> getPlayers(){
        return players;
    }

    public Socket getChair(){
        return players.get(chair);
    }

    public Socket getCurrentPlayer(){
        return current_client;
    }

    public GameLogic getGameLogic() {
        return gameLogic;
    }

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

    public void addPlayerToGame(Player player) throws Exception{
        gameLogic.getGame().addNewPlayer(player);
    }

    public void takeTurn(){
        /*shufflePlayers();

        while(true){ // test
            server.gameTableToAll();

            current_client.receiveLibrary(current_client.getPlayer().getLibrary());

            current_client.receiveGetCard(gameLogic, server);

            updateCurrentPlayer();
        }*/

    }


}
