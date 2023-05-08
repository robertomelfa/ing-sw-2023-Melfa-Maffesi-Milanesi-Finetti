package it.polimi.ingsw.Network.Client.RMI;

import it.polimi.ingsw.Controller.ControllerMain;
import it.polimi.ingsw.Model.*;
import it.polimi.ingsw.Network.Client.Socket.ClientClass;
import it.polimi.ingsw.Network.Server.RMI.GameInterface;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Scanner;


public class Client extends UnicastRemoteObject implements GameClientInterface, Serializable{
    private Player player;

    /**
     * constructor of the client
     * @param name username of the player
     * @throws Exception
     */
    public Client(String name) throws Exception{
        this.player = new Player(name);
    }

    /**
     *
     * @return the player corresponding to the client
     * @throws RemoteException
     */
    public Player getPlayer() throws RemoteException {
        return this.player;
    }

    /**
     * call the viewTable() method to print the game table on the client command line
     * @param board the game table we want to display
     * @throws RemoteException
     */
    public void receiveGameTable(GameTable board) throws RemoteException{
        board.viewTable();
    }

    /**
     * call the viewGrid() method to print the library on the client command line
     * @param library the library we want to display
     * @throws RemoteException
     */
    public void receiveLibrary(Library library) throws RemoteException{
        library.viewGrid();
    }

    /**
     * used to perform the action of taking cards from the game table and store them in the player's library
     * use the getCardFromTable() and insert() methods defined in the model
     * @param gameLogic to perform the getCardFromTable method
     * @param server who the client is connected to
     * @throws RemoteException
     * @throws Exception
     */
    public GameLogic receiveGetCard(GameLogic gameLogic, GameInterface server) throws RemoteException, Exception{
        ArrayList<Card> cards = new ArrayList<>();

        cards = gameLogic.getCardFromTable();

        System.out.print("\n");

        player.getLibrary().insert(cards);

        return gameLogic;
    }

    /**
     * print a string message
     * @param msg string we want to print
     * @throws RemoteException
     */
    public void receiveMessage(String msg) throws RemoteException{
        System.out.println(msg);
    }

    /**
     * establish connection between client and server, if it is the first client to connect to the server
     * creates a new game and choose how many players are allowed to play in that game.
     * If it isn't the first player to connect it'll wait until the game is created by the first player
     * @param server server we want to connect the client to
     * @param client client that we want to connect to the server
     * @throws RemoteException
     * @throws Exception
     */
    public void connection(GameInterface server, GameClientInterface client) throws RemoteException, Exception{
        if(server.isFirstPlayer()) {
            server.setFirstPlayer();
            Scanner in = new Scanner(System.in);
            server.setClient(client);
        }else{

            if(server.getNumPlayers() > server.getClientList().size()){
                server.setClient(client);
            }else{
                throw new Exception("troppi dispositivi connessi, non puoi accedere");
            }
        }
        System.out.println("[System] connected!");
    }

    public void connection2(GameInterface server, GameClientInterface client, ControllerMain controller) throws RemoteException, Exception{
        Scanner in = new Scanner(System.in);
            if(controller.getNumPlayers() == 0) {
                System.out.println("Insert players number");
                int num = in.nextInt();
                server.updateNumPlayers(num);
                ClientClass client1 = new ClientClass(client);
                client1.setPlayer(this.player);
                server.updatePlayers(client1);
            }else{
                if(controller.getClientList().size() < controller.getNumPlayers()){
                    ClientClass client1 = new ClientClass(client);
                    client1.setPlayer(this.player);
                    server.updatePlayers(client1);
                }else{
                    throw new Exception("too many connected clients, you can't log in");
                }
            }
            System.out.println("[System] connected!");
    }

    /**
     * set the end of the game and it's called by the server to print the end of the game to the client
     * @throws RemoteException
     */
    public void endMessage() throws RemoteException{
        player.endGame();
    }

    /**
     * print a string and return an int that correspond to the answer of a question present in the sting
     * we printed
     * @param msg string we want to print
     * @return an int taken in input
     * @throws RemoteException
     */
    public int getIntFromClient(String msg) throws RemoteException{
        Scanner in = new Scanner(System.in);
        System.out.println(msg);
        return in.nextInt();
    }

    /**
     * print the player objective using the corresponding method defined in the model
     * @throws RemoteException
     */
    public void printPlayerObj() throws RemoteException{
        this.player.getPlayerObj().print();
    }
}
