package it.polimi.ingsw.Controller.Socket;

import it.polimi.ingsw.Controller.ControllerMain;
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

public class SocketController implements Serializable {

    private Server_Socket server;
    private  boolean endGame=false;
    private GameLogic gameLogic;
    private ClientClass current_client;

    /**
     * constructor for the controller
     * @param server
     * @throws Exception
     */

    public SocketController(Server_Socket server, ClientClass current_client, GameLogic gameLogic) throws Exception{
        this.server = server;
        this.endGame=false;
        this.current_client = current_client;
        this.gameLogic = gameLogic;
    }



    /*public void checkObjectives() throws Exception{
        if(!current_client.getPlayer().getCommonObj1Completed()){
            if(gameLogic.getGame().getCommonObj1().checkObj(current_client.getPlayer().getLibrary())){
                Message message=new Message(MessageType.objectiveCompleted,current_client.getPlayer().getNickname() + " successfully completed the first common goal\nnow has the " +gameLogic.getGame().getCommonObj1().getPointCount() +" card");

                current_client.getPlayer().addPoints(gameLogic.getGame().getCommonObj1().getPointCount());
                current_client.getPlayer().setCommonObj2Completed();

            }
        }

        if(!current_client.getPlayer().getCommonObj2Completed()){
            if(gameLogic.getGame().getCommonObj2().checkObj(current_client.getPlayer().getLibrary())){
                for (int i=0;i<server.getClientList().size();i++){
                    Message message=new Message(MessageType.objectiveCompleted,current_client.getPlayer().getNickname() + " successfully completed the second common goal\nnow has the " + gameLogic.getGame().getCommonObj2().getPointCount() + " card");
                    server.sendMessage(message,server.getClientList().get(i).getSocket());
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



    }*/

    public GameLogic takeTurn() throws IOException, Exception {


            server.sendGameTable(current_client.getSocket(), gameLogic.getGameTable());
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

            //checkObjectives();
            return gameLogic;
        }
}
