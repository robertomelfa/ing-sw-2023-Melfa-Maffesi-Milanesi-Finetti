package it.polimi.ingsw.Controller.Socket;


import it.polimi.ingsw.Model.GameLogic;
import it.polimi.ingsw.Network.Messages.Message;
import it.polimi.ingsw.Network.Messages.MessageType;
import it.polimi.ingsw.Network.Server.Socket.*;
import it.polimi.ingsw.Network.Client.Socket.ClientClass;
import it.polimi.ingsw.View.CLIView;

import java.io.IOException;
import java.io.Serializable;

// TODO sistemare la gestione del turno (per ora è una bozza)
// TODO gestione obiettivi
// TODO scambio messaggi server-client per inizio/fine turno ed eventualmente per la gestione delle carte
// TODO ping al client per disconnessioni

public class SocketController implements Serializable {

    private Server_Socket server;
    private GameLogic gameLogic;
    private ClientClass current_client;

    /**
     * constructor for the controller
     * @param server
     * @throws Exception
     */

    public SocketController(Server_Socket server, ClientClass current_client, GameLogic gameLogic) throws Exception{
        this.server = server;
        this.current_client = current_client;
        this.gameLogic = gameLogic;
    }

    /**
     * This method handler the turn of a socket client
     * @return  gameLogic updated
     * @throws IOException
     * @throws Exception
     */
    public GameLogic takeTurn() throws IOException, Exception {

            // send gametable to client
            server.sendGameTable(current_client.getSocket(), gameLogic.getGameTable());

            // send library to client
            server.sendLibrary(current_client.getSocket(),current_client.getPlayer().getLibrary());

            int i=0;
            while(i==0){
                server.sendMessage(new Message(MessageType.notifyBeginTurn,"\nInsert 1 if you want to see your objectives or insert 2 if you want to pick the cards"),current_client.getSocket());
                switch (server.receiveMessage(current_client.getSocket()).getMessage()){

                    case "1":
                        // print objects
                        server.sendMessage(new Message(MessageType.printMessage,"Player Object:"),current_client.getSocket());
                        server.sendPlayerObj(current_client.getSocket(),current_client.getPlayer().getPlayerObj());
                        server.sendMessage(new Message(MessageType.printMessage,"Common Object 1:"),current_client.getSocket());
                        server.sendMessage(new Message(MessageType.printMessage,gameLogic.getGame().getCommonObj1().getDescrizione()),current_client.getSocket());
                        server.sendMessage(new Message(MessageType.printMessage,"Common Object 2:"),current_client.getSocket());
                        server.sendMessage(new Message(MessageType.printMessage,gameLogic.getGame().getCommonObj2().getDescrizione()),current_client.getSocket());
                        break;
                    case "2":
                        // pick card from table

                        gameLogic = server.sendGameLogic(current_client, gameLogic);
                        i=1;
                        break;
                    default:
                        server.sendMessage(new Message(MessageType.printMessage,"The input is not valid, please insert 1 or 2\n"),current_client.getSocket());

                }
            }
            // return the updated gamelogic
            return gameLogic;
        }
}
