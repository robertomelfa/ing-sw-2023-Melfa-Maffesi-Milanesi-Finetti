package it.polimi.ingsw.Controller;

import it.polimi.ingsw.Controller.RMI.RMIController;
import it.polimi.ingsw.Controller.Socket.SocketController;
import it.polimi.ingsw.Model.Game;
import it.polimi.ingsw.Model.GameLogic;
import it.polimi.ingsw.Model.GameTable;
import it.polimi.ingsw.Network.Client.RMI.GameClientInterface;
import it.polimi.ingsw.Network.Client.Socket.ClientClass;
import it.polimi.ingsw.Network.Client.Socket.Client_Socket;
import it.polimi.ingsw.Network.Messages.Message;
import it.polimi.ingsw.Network.Server.RMI.GameInterface;
import it.polimi.ingsw.Network.Server.RMI.GameServer;
import it.polimi.ingsw.Network.Server.Socket.Server_Socket;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

public class controllerMain implements Serializable {
    private final ArrayList<ClientClass> clientList = new ArrayList<>();

    private int numPlayers = 0;

    private Server_Socket serverSocket;

    private GameInterface serverRMI;

    private boolean endGame = false;

    private int chair;

    private int listIterator = 0;

    private GameLogic gameLogic;

    private ClientClass current_client;



    public controllerMain(Server_Socket serverSocket, GameInterface serverRMI){
        this.serverSocket = serverSocket;
        this.serverRMI = serverRMI;
    }

    public synchronized boolean getStart(){
        if(numPlayers == clientList.size() && numPlayers > 1 && numPlayers < 5){
            return true;
        }
        return false;
    }

    public ClientClass getChair() throws RemoteException {
        return clientList.get(chair);
    }

    public ClientClass getCurrentPlayer() throws RemoteException{
        return current_client;
    }

    public void updateCurrentPlayer() throws RemoteException, Exception{
        if(!endGame){
            listIterator++;
            if(clientList.size() == listIterator){
                listIterator = 0;
                current_client = clientList.get(listIterator);
            }else {
                current_client = clientList.get(listIterator);
            }
        }else {
            listIterator++;
            if(chair == listIterator){
                gameLogic.getGame().checkEnd();

                // TODO aggiungere che si informano i client della fine del gioco
            }
            else {
                current_client = clientList.get(listIterator);
            }
        }
    }

    public void shufflePlayers(){
        try {
            java.util.Collections.shuffle(clientList);
            chair = 0;
            listIterator = 0;
            current_client=clientList.get(0);
        } catch (Exception e){
            System.out.println("Error setting up order...");
        }
    }

     public synchronized void addClient(ClientClass clientList){
        this.clientList.add(clientList);
    }

     public synchronized ArrayList<ClientClass> getClientList(){
        return this.clientList;
    }

    public synchronized void setNumPlayers(int numPlayers){
        this.numPlayers = numPlayers;
    }

    public synchronized int getNumPlayers(){
        return this.numPlayers;
    }

    public void startGame() throws Exception, ArrayIndexOutOfBoundsException{
        // creo game
        Game game = new Game(numPlayers);
        gameLogic = new GameLogic(game);
        shufflePlayers();
        while(true){
            if(current_client.getClient() == null){
                SocketController controllerS = new SocketController(serverSocket, current_client, gameLogic);
                gameLogic = controllerS.takeTurn();
            }else{
                RMIController controllerR = new RMIController(gameLogic, current_client.getClient(), serverRMI);
                gameLogic = controllerR.takeTurn();
            }
            updateCurrentPlayer();
        }
        // TODO sistemare il turno con tutte le verifiche del caso
    }

}
