package it.polimi.ingsw.Controller.RMI;

import it.polimi.ingsw.Model.Card;
import it.polimi.ingsw.Model.GameLogic;
import it.polimi.ingsw.Model.Player;
import it.polimi.ingsw.Network.Client.RMI.GameClientInterface;
import it.polimi.ingsw.Network.Server.RMI.GameInterface;
import it.polimi.ingsw.Network.Server.RMI.GameServer;

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

public class RMIController {
    private GameInterface server;

    private boolean endGame = false;

    private int chair;

    private List<GameClientInterface> players = new ArrayList<>();

    private int listIterator = 0;

    private GameLogic gameLogic;

    private GameClientInterface current_client;

    public RMIController() {
        try {
             Registry registry = LocateRegistry.getRegistry("localhost", 1099);
             server = (GameInterface) registry.lookup("GameInterface");


            for(int i = 0; i < server.getClientList().size(); i++){
                players.add(server.getClient(i));
            }

            gameLogic = new GameLogic(server.getGame());

            shufflePlayers(players);

        } catch (AccessException e) {
            throw new RuntimeException(e);
        } catch (NotBoundException e) {
            throw new RuntimeException(e);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public GameClientInterface getChair() throws RemoteException{
        return players.get(chair);
    }

    public GameClientInterface getCurrentPlayer() throws RemoteException{
        return current_client;
    }

    public void updateCurrentPlayer() throws RemoteException, Exception{
        if(!endGame){
            listIterator++;
            if(players.size() == listIterator){
                listIterator = 0;
                current_client = players.get(listIterator);
            }else {
                current_client = players.get(listIterator);
            }
        }else {
            listIterator++;
            if(chair == listIterator){
                gameLogic.getGame().checkEnd();
                server.notifyEnd();
            }
            else {
                current_client = players.get(listIterator);
            }
        }
    }

    public void shufflePlayers(List<GameClientInterface> players) throws RemoteException, Exception{
        try {
            java.util.Collections.shuffle(players);
            chair = 0;
            listIterator = 0;
            current_client=players.get(0);
        } catch (Exception e){
            System.out.println("Error setting up order...");
        }
    }

    // TODO inserisce scelta per vedere obiettivi o pescare carte
    public void takeTurn() throws RemoteException, Exception{

        while(!endGame){ // test

            // notify all players about the turn
            server.notifyTurnPlayer(current_client);


            int i = 0;
            while(i == 0){

                switch (current_client.getIntFromClient("\nInsert 1 if you want to see your objectives or insert 2 if you want to pick the cards")){
                    case 1:
                        current_client.receiveMessage("Player Object:");
                        current_client.printPlayerObj();
                        current_client.receiveMessage("Common Object 1:");
                        current_client.receiveMessage(server.getGame().getCommonObj1().getDescrizione());
                        current_client.receiveMessage("Common Object 2:");
                        current_client.receiveMessage(server.getGame().getCommonObj2().getDescrizione());
                        break;
                    case 2:
                        i = 1;
                        break;
                    default:
                        current_client.receiveMessage("The input is not valid, please insert 1 or 2\n");
                }

            }

            // send the gameTable to all players
            server.gameTableToAll(server.getGame().getGameTable());

            // send the library to the current player
            current_client.receiveLibrary(current_client.getPlayer().getLibrary());

            // get cards from table
            current_client.receiveGetCard(gameLogic, server);

            // update gameLogic table
            gameLogic.setGameTable(server.getGame().getGameTable());

            // check the commonObj
            if(!current_client.getPlayer().getCommonObj1Completed()){
                if(server.getGame().getCommonObj1().checkObj(current_client.getPlayer().getLibrary())){
                    server.messageToAll(current_client.getPlayer().getNickname() + " successfully completed the first common goal");
                    server.messageToAll(current_client.getPlayer().getNickname() + " now has the " + server.getGame().getCommonObj1().getPointCount() + " card");
                    current_client.getPlayer().addPoints(server.getGame().getCommonObj1().getPointCount());
                    current_client.getPlayer().setCommonObj2Completed();
                }
            }

            if(!current_client.getPlayer().getCommonObj2Completed()){
                if(server.getGame().getCommonObj2().checkObj(current_client.getPlayer().getLibrary())){
                    server.messageToAll(current_client.getPlayer().getNickname() + " successfully completed the first common goal");
                    server.messageToAll(current_client.getPlayer().getNickname() + " now has the " + server.getGame().getCommonObj1().getPointCount() + " card");
                    current_client.getPlayer().addPoints(server.getGame().getCommonObj2().getPointCount());
                    current_client.getPlayer().setCommonObj2Completed();
                }
            }

            if(current_client.getPlayer().getLibrary().checkFull()){
                server.getGame().setEndGame();
                server.getGame().getCurrentPlayer().addPoints(1);
                endGame = true;
            }

            // check gameTable: in case refill
            gameLogic.getGameTable().checkStatus();

            // update the current_player
            updateCurrentPlayer();
        }
        server.notifyEnd();
    }
}

