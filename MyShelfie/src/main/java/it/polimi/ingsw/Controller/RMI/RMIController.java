package it.polimi.ingsw.Controller.RMI;

import it.polimi.ingsw.Model.Card;
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

    private int chair;

    private List<GameClientInterface> players;

    private int listIterator = 0;

    private GameClientInterface current_client;

    public RMIController() {
        try {
            Registry registry = LocateRegistry.getRegistry("localhost", 1099);
            GameInterface server = (GameInterface) registry.lookup("GameInterface");

            for(int i = 0; i < server.getClientList().size(); i++){
                players = new ArrayList<>();
                players.add(server.getClient(i));
            }

            shufflePlayers(players);

        } catch (AccessException e) {
            throw new RuntimeException(e);
        } catch (NotBoundException e) {
            throw new RuntimeException(e);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    public void shufflePlayers(List<GameClientInterface> players) throws RemoteException{
        try {
            java.util.Collections.shuffle(players);
            chair = 0;
        } catch (Exception e){
            System.out.printf("Error setting up order...");
        }
    }

    public void takeTurn() throws RemoteException{
        try {

            current_client = players.get(listIterator);

            server.messageToAll(server.getGame().getGameTable());

            current_client.receiveLibrary(current_client.getPlayer().getLibrary());



        } catch (Exception e) {

        }
    }



}

