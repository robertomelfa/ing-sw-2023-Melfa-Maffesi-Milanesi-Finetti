package it.polimi.ingsw.Network.Client.RMI;

import it.polimi.ingsw.Model.*;
import it.polimi.ingsw.Network.Server.RMI.GameInterface;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Scanner;


public class Client extends UnicastRemoteObject implements GameClientInterface{
    private Player player;

    Client(String name) throws Exception{
        this.player = new Player(name);
    }

    public Player getPlayer() throws RemoteException {
        return this.player;
    }

    public void receiveGameTable(GameTable board) throws RemoteException{
        board.viewTable();
    }

    public void receiveLibrary(Library library) throws RemoteException{
        library.viewGrid();
    }

    public void receiveGetCard(GameLogic gameLogic, GameInterface server) throws RemoteException, Exception{
        ArrayList<Card> cards = new ArrayList<>();

        cards = gameLogic.getCardFromTable();

        server.receiveTable(gameLogic.getGameTable());  // do al server il tabellone aggiornato

        // TODO bisogna migliorare l'aggiornamento del tabellone, non so perchè funziona correttamente solo all'inizio

        player.getLibrary().insert(cards);
        // TODO forse bisognerebbe aggiornare la libreria del client sul server (non so se necessario)
    }

    // potrebbe servire
    public void receiveMessage(String msg) throws RemoteException{
        System.out.println(msg);
    }

    public void connection(GameInterface server, GameClientInterface client) throws RemoteException, Exception{
        if(server.getGame() == null){
            Scanner in = new Scanner(System.in);
            System.out.println("How many players?");
            int num = in.nextInt();
            Game game = new Game(num);
            server.setGame(game);
            server.setClient(client);

        }else{
            String answer = "J"; // per ora teniamo che puoi solo joinare

            if(server.getGame().getNumOfPlayers() > server.getGame().numActualPlayers()){
                switch (answer){
                    case "N":
                        // TODO implementare il caso di un nuovo game: veramente necessario?
                        break;
                    case "J":
                        System.out.println("You choose J");
                        server.setClient(client);
                        break;
                    default: System.out.println("Invalid choice");
                }
            }else{
                throw new Exception("troppi dispositivi connessi, non puoi accedere");
            }

        }
        System.out.println("[System] connected!");
    }
}
