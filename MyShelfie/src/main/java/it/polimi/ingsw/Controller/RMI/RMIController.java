package it.polimi.ingsw.Controller.RMI;

import it.polimi.ingsw.Model.Card;
import it.polimi.ingsw.Model.Game;
import it.polimi.ingsw.Model.GameLogic;
import it.polimi.ingsw.Model.Player;
import it.polimi.ingsw.Network.Client.RMI.GameClientInterface;
import it.polimi.ingsw.Network.Server.RMI.GameInterface;
import it.polimi.ingsw.Network.Server.RMI.GameServer;

import java.io.Serializable;
import java.rmi.AccessException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static it.polimi.ingsw.Model.Card.NONE;

// TODO implementare comunicazione client-server attraverso controller per la gestione del turno

public class RMIController implements Serializable {
    private GameInterface server;

    private boolean endGame = false;

    private GameLogic gameLogic;

    private GameClientInterface current_client;

    /**
     * constructor of the RMIController
     * call shufflePLayers to shuffle the arraylist stored in the controller to perform the turn in random order
     * @throws AccessException
     * @throws NotBoundException
     * @throws Exception
     */
    public RMIController(GameLogic gameLogic, GameClientInterface current_client, GameInterface server) {
        this.server = server;
        this.gameLogic = gameLogic;
        this.current_client = current_client;
    }
    public GameLogic takeTurn() throws RemoteException, Exception{


            // notify all players about the turn


            // send the gameTable to all players
            server.gameTableToClient(gameLogic.getGameTable(), current_client);


            int i = 0;
            while(i == 0){

                switch (current_client.getIntFromClient("\nInsert 1 if you want to see your objectives or insert 2 if you want to pick the cards")){
                    case 1:
                        current_client.receiveMessage("Player Object:");
                        current_client.printPlayerObj();
                        current_client.receiveMessage("Common Object 1:");
                        current_client.receiveMessage(gameLogic.getGame().getCommonObj1().getDescrizione());
                        current_client.receiveMessage("Common Object 2:");
                        current_client.receiveMessage(gameLogic.getGame().getCommonObj2().getDescrizione());
                        break;
                    case 2:
                        i = 1;
                        break;
                    default:
                        current_client.receiveMessage("The input is not valid, please insert 1 or 2\n");
                }

            }

            // send the library to the current player
            current_client.receiveLibrary(current_client.getPlayer().getLibrary());

            // get cards from table
            this.gameLogic = current_client.receiveGetCard(gameLogic, server);

            // send the gameTable to all players
            server.gameTableToAll(gameLogic.getGameTable());

            // update gameLogic table
         //   gameLogic.setGameTable(server.getGame().getGameTable());

            // check the commonObj
            if(!current_client.getPlayer().getCommonObj1Completed()){
                if(gameLogic.getGame().getCommonObj1().checkObj(current_client.getPlayer().getLibrary())){
                    server.messageToAll(current_client.getPlayer().getNickname() + " successfully completed the first common goal");
                    server.messageToAll(current_client.getPlayer().getNickname() + " now has the " + gameLogic.getGame().getCommonObj1().getPointCount() + " card");
                    current_client.getPlayer().addPoints(gameLogic.getGame().getCommonObj1().getPointCount());
                    current_client.getPlayer().setCommonObj2Completed();
                }
            }

            if(!current_client.getPlayer().getCommonObj2Completed()){
                if(gameLogic.getGame().getCommonObj2().checkObj(current_client.getPlayer().getLibrary())){
                    server.messageToAll(current_client.getPlayer().getNickname() + " successfully completed the first common goal");
                    server.messageToAll(current_client.getPlayer().getNickname() + " now has the " + gameLogic.getGame().getCommonObj1().getPointCount() + " card");
                    current_client.getPlayer().addPoints(gameLogic.getGame().getCommonObj2().getPointCount());
                    current_client.getPlayer().setCommonObj2Completed();
                }
            }

            if(current_client.getPlayer().getLibrary().checkFull()){
                gameLogic.getGame().setEndGame();
                gameLogic.getGame().getCurrentPlayer().addPoints(1);
                endGame = true;
            }
        return gameLogic;
        }

}
