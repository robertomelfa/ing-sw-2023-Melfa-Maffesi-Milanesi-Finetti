package it.polimi.ingsw.Controller.Socket;


import it.polimi.ingsw.Model.GameLogic;
import it.polimi.ingsw.Network.Messages.Message;
import it.polimi.ingsw.Network.Messages.MessageType;
import it.polimi.ingsw.Network.Server.Socket.*;
import it.polimi.ingsw.Network.Client.Socket.ClientClass;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;

// TODO sistemare la gestione del turno (per ora è una bozza)
// TODO gestione obiettivi
// TODO scambio messaggi server-client per inizio/fine turno ed eventualmente per la gestione delle carte
// TODO ping al client per disconnessioni

public class SocketController implements Serializable {

    private Server_Socket server;
    private GameLogic gameLogic;
    private ClientClass current_client;


    private boolean gui;

    /**
     * constructor for the controller
     * @param server
     * @throws Exception
     */

    public SocketController(Server_Socket server, ClientClass current_client, GameLogic gameLogic, boolean gui) throws Exception{
        this.server = server;
        this.current_client = current_client;
        this.gameLogic = gameLogic;
        this.gui = gui;
    }

    /**
     * This method handler the turn of a socket client
     * @return  gameLogic updated
     * @throws IOException
     * @throws Exception
     */
    public GameLogic takeTurn() throws IOException, Exception {

            // send library to client
            server.sendLibrary(current_client.getSocket(),current_client.getPlayer().getLibrary());

            int i=0;
            while(i==0) {
                if (!gui) {
                    server.sendMessage(new Message(MessageType.notifyBeginTurn, "\nInsert 1 if you want to see your objectives, insert 2 if you want to pick the cards or insert 3 to view all the libraries"), current_client.getSocket());
                    switch (server.receiveMessage(current_client.getSocket()).getMessage()) {
                        case "1" -> {
                            // print objects
                            server.sendMessage(new Message(MessageType.printMessage, "Player Object:"), current_client.getSocket());
                            server.sendPlayerObj(current_client.getSocket(), current_client.getPlayer().getPlayerObj());
                            server.sendMessage(new Message(MessageType.printMessage, "Common Object 1:"), current_client.getSocket());
                            server.sendMessage(new Message(MessageType.printMessage, gameLogic.getGame().getCommonObj1().getDescription()), current_client.getSocket());
                            server.sendMessage(new Message(MessageType.printMessage, "Common Object 2:"), current_client.getSocket());
                            server.sendMessage(new Message(MessageType.printMessage, gameLogic.getGame().getCommonObj2().getDescription()), current_client.getSocket());
                        }
                        case "2" -> {
                            // pick card from table
                            gameLogic = server.sendGameLogic(current_client, gameLogic);
                            i = 1;
                        }
                        case "3" -> {
                            for(int j = 0; j < gameLogic.getPlayers().size(); j++){
                                if(!gameLogic.getPlayers().get(j).getNickname().equals(gameLogic.getGame().getCurrentPlayer().getNickname())){
                                    Message msg = new Message(MessageType.printMessage, "\n" + gameLogic.getPlayers().get(j).getNickname() + "'s library");
                                    server.sendMessage(msg, current_client.getSocket());
                                    server.sendLibrary(current_client.getSocket(), gameLogic.getPlayers().get(j).getLibrary());
                                }
                            }
                        }
                        default ->
                                server.sendMessage(new Message(MessageType.printMessage, "The input is not valid, please insert 1 or 2\n"), current_client.getSocket());
                    }
                }else {
                    System.out.println("Socket controller");
                    gameLogic = server.sendGameLogic(current_client, gameLogic);
                    i = 1;
                }
            }
            // return the updated gamelogic
            return gameLogic;
    }
}
