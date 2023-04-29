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

        } catch (AccessException e) {
            throw new RuntimeException(e);
        } catch (NotBoundException e) {
            throw new RuntimeException(e);
        } catch (RemoteException e) {
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
            System.out.println("ciao");
            listIterator++;
            if(players.size() == listIterator){
                System.out.println("si");
                listIterator = 0;
                current_client = players.get(listIterator);
            }else {
                System.out.println("ciao");
                current_client = players.get(listIterator);
            }
        }else {
            listIterator++;
            if(players.size() == listIterator){
                gameLogic.getGame().checkEnd();
                throw new Exception("GAME IS ENDED");   // probably this will be in the view; metterei più un messaggio che un'eccezione che da meno problemi @simone
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
            System.out.printf("Error setting up order...");
        }
    }

    public void takeTurn() throws RemoteException, Exception{
        shufflePlayers(players);
        while(true){ // test
            gameLogic = new GameLogic(server.getGame());

            server.gameTableToAll(server.getGame().getGameTable());

            current_client.receiveLibrary(current_client.getPlayer().getLibrary());

            current_client.receiveGetCard(gameLogic);

            current_client.receiveLibrary(current_client.getPlayer().getLibrary());

            updateCurrentPlayer();
        }

    }



}

