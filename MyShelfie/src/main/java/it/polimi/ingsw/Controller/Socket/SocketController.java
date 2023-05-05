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


    public void checkObjectives() throws Exception{
        if(!current_client.getPlayer().getCommonObj1Completed()){
            if(gameLogic.getGame().getCommonObj1().checkObj(current_client.getPlayer().getLibrary())){
                for (int i=0;i<players.size();i++){
                    Message message=new Message(MessageType.objectiveCompleted,current_client.getPlayer().getNickname() + " successfully completed the first common goal\nnow has the " +gameLogic.getGame().getCommonObj1().getPointCount() +" card");
                    server.sendMessage(message,players.get(i).getSocket());
                }
                current_client.getPlayer().addPoints(gameLogic.getGame().getCommonObj1().getPointCount());
                current_client.getPlayer().setCommonObj2Completed();

            }
        }

        if(!current_client.getPlayer().getCommonObj2Completed()){
            if(gameLogic.getGame().getCommonObj2().checkObj(current_client.getPlayer().getLibrary())){
                for (int i=0;i<players.size();i++){
                    Message message=new Message(MessageType.objectiveCompleted,current_client.getPlayer().getNickname() + " successfully completed the second common goal\nnow has the " + gameLogic.getGame().getCommonObj2().getPointCount() + " card");
                    server.sendMessage(message,players.get(i).getSocket());
                }
                current_client.getPlayer().addPoints(gameLogic.getGame().getCommonObj2().getPointCount());
                current_client.getPlayer().setCommonObj2Completed();
            }
        }


        if(current_client.getPlayer().getLibrary().checkFull()){
            gameLogic.getGame().setEndGame();
            gameLogic.getGame().getCurrentPlayer().addPoints(1);
            endGame = true;
        }

        // check gameTable: in case refill

    }

    public void takeTurn() throws IOException, Exception {
        shufflePlayers();

        while(!endGame){ // test

            server.sendGameTable(current_client.getSocket(),gameLogic.getGameTable());
            server.sendLibrary(current_client.getSocket(),current_client.getPlayer().getLibrary());
            int i=0;
            while(i==0){
                server.sendMessage(new Message(MessageType.notifyBeginTurn,"\nInsert 1 if you want to see your objectives or insert 2 if you want to pick the cards"),current_client.getSocket());
                switch (server.receiveMessage(current_client.getSocket()).getMessage()){

                    case "1":
                        server.sendMessage(new Message(MessageType.printMessage,"Player Object:"),current_client.getSocket());
                        server.sendPlayerObj(current_client.getSocket(),current_client.getPlayer().getPlayerObj());
                        server.sendMessage(new Message(MessageType.printMessage,"Common Object 1:"),current_client.getSocket());
                        server.sendMessage(new Message(MessageType.printMessage,gameLogic.getGame().getCommonObj1().getDescrizione()),current_client.getSocket());
                        server.sendMessage(new Message(MessageType.printMessage,"Common Object 2:"),current_client.getSocket());
                        server.sendMessage(new Message(MessageType.printMessage,gameLogic.getGame().getCommonObj2().getDescrizione()),current_client.getSocket());
                        break;
                    case "2":
                        gameLogic = server.sendGameLogic(current_client, gameLogic);
                        i=1;
                        break;
                    default:
                        server.sendMessage(new Message(MessageType.printMessage,"The input is not valid, please insert 1 or 2\n"),current_client.getSocket());

                }
            }
            //server.gameTableToAll(gameLogic.getGameTable());

            //server.sendLibrary(current_client.getSocket(), current_client.getPlayer().getLibrary());

            //gameLogic = server.sendGameLogic(current_client, gameLogic);

            checkObjectives();

            //inserire checkFull sulla libreria del player che fa terminare il game
            gameLogic.getGameTable().checkStatus();

            updateCurrentPlayer();
        }
        //server.notifyEnd
    }
}
