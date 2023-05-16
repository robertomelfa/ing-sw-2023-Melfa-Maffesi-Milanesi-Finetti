package it.polimi.ingsw.Controller;

import it.polimi.ingsw.Controller.RMI.RMIController;
import it.polimi.ingsw.Controller.Socket.SocketController;
import it.polimi.ingsw.Model.Game;
import it.polimi.ingsw.Model.GameLogic;
import it.polimi.ingsw.Model.GameTable;
import it.polimi.ingsw.Network.Client.Socket.ClientClass;
import it.polimi.ingsw.Network.Messages.Message;
import it.polimi.ingsw.Network.Messages.MessageType;
import it.polimi.ingsw.Network.Server.RMI.GameInterface;
import it.polimi.ingsw.Network.Server.Socket.Server_Socket;

import java.io.IOException;
import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.ArrayList;

public class ControllerMain implements Serializable {
    private final ArrayList<ClientClass> clientList = new ArrayList<>();

    private int numPlayers = 0;

    private Server_Socket serverSocket;

    private GameInterface serverRMI;

    private boolean endGame = false;

    private int chair;

    private int listIterator = 0;

    private GameLogic gameLogic;

    private ClientClass current_client;

    public ControllerMain(){}


    /**
     * constructor of the class: the controller is connected to both servers
     * @param serverSocket Socket Server
     * @param serverRMI RMI Server
     */
    public ControllerMain(Server_Socket serverSocket, GameInterface serverRMI){
        this.serverSocket = serverSocket;
        this.serverRMI = serverRMI;
    }

    /**
     *
     * @param name name e want to check
     * @return true if the name is already used in the game, false if not
     */
    public boolean checkExistingName(String name){
        if(clientList.size() != 0){
            for(int i = 0; i < clientList.size(); i++){
                if(name.equals(clientList.get(i).getPlayer().getNickname())){
                    return true;
                }
            }
        }
        return false;
    }

    /**
     *
     * Thread waiting the start of the game
     * @throws InterruptedException
     */
    public synchronized void waitStart() throws InterruptedException{
        while(!getStart()){
            wait();
        }
    }

    /**
     *
     * @return true if all the players are connected and the game will start
     */
    public synchronized boolean getStart(){
        if(numPlayers == clientList.size() && numPlayers > 1 && numPlayers < 5){
            return true;
        }
        return false;
    }

    public ClientClass getChair(){
        return clientList.get(chair);
    }

    public ClientClass getCurrentPlayer(){
        return current_client;
    }

    /**
     * this method update the current player and check if the game is ended
     * @throws RemoteException
     * @throws Exception
     */
    public void updateCurrentPlayer() throws Exception{
        if(!endGame){
            // update the current player
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
                // the game is ended
                checkEnd();
                sendGeneralMessage(new Message(MessageType.endGame,"Game is ended"));
            }
            else {
                current_client = clientList.get(listIterator);
            }
        }
    }

    /**
     * this method is used to mix the list with the players, before starting the game
     */
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

    /**
     *
     * @param clientList the client we want to add to the client list
     */
     public synchronized void addClient(ClientClass clientList){
        this.clientList.add(clientList);
        // notify the client list change
        notifyAll();
    }

    /**
     *
     * @return the client list
     */
     public synchronized ArrayList<ClientClass> getClientList(){
        return this.clientList;
    }

    /**
     *
     * @param numPlayers num of players of the game (asked to the first client)
     */
    public synchronized void setNumPlayers(int numPlayers){
        this.numPlayers = numPlayers;
    }

    /**
     *
     * @return num of game players
     */
    public synchronized int getNumPlayers(){
        return this.numPlayers;
    }

    public void gameTableToALL(GameTable gameTable) throws ClassNotFoundException, IOException, Exception{
        for(int i = 0; i < clientList.size(); i++){
            if(clientList.get(i).getClient() == null){
                serverSocket.sendGameTable(clientList.get(i).getSocket(), gameTable);
            }else{
                serverRMI.gameTableToClient(gameTable, clientList.get(i).getClient());
            }
        }
    }

    /**
     * this method control the game
     * @throws Exception
     * @throws ArrayIndexOutOfBoundsException
     */
    public void startGame() throws Exception, ArrayIndexOutOfBoundsException{
        // create game
        Game game = new Game(numPlayers);
        gameLogic = new GameLogic(game);
        sendGeneralMessage(new Message(MessageType.printMessage, "Game is starting..."));
        shufflePlayers();
        while(true){
            sendGeneralMessage(new Message(MessageType.printMessage, current_client.getPlayer().getNickname() + " is your turn!"));
            gameLogic.getGame().setCurrentPlayer(current_client.getPlayer());
            gameTableToALL(gameLogic.getGameTable());
            if(current_client.getClient() == null){
                SocketController controllerS = new SocketController(serverSocket, current_client, gameLogic);
                gameLogic = controllerS.takeTurn();
                current_client.getPlayer().setLibrary(gameLogic.getGame().getCurrentPlayer().getLibrary());
            }else{
                RMIController controllerR = new RMIController(gameLogic, current_client, serverRMI);
                gameLogic = controllerR.takeTurn();
                current_client.getPlayer().setLibrary(gameLogic.getGame().getCurrentPlayer().getLibrary());
            }
            checkObjectives();
            updateCurrentPlayer();
        }
    }

    /**
     * This method checks all the objects
     * @throws Exception
     */
    public void checkObjectives() throws Exception{
        // check common object 1
        if(!current_client.getPlayer().getCommonObj1Completed()){
            if(gameLogic.getGame().getCommonObj1().checkObj(current_client.getPlayer().getLibrary())){
                Message message=new Message(MessageType.objectiveCompleted,current_client.getPlayer().getNickname() + " successfully completed the first common goal\nnow has the " +gameLogic.getGame().getCommonObj1().getPointCount() +" card");
                sendGeneralMessage(message);
                current_client.getPlayer().addPoints(gameLogic.getGame().getCommonObj1().getPointCount());
                current_client.getPlayer().setCommonObj2Completed();

            }
        }

        // check common object 2
        if(!current_client.getPlayer().getCommonObj2Completed()){
            if(gameLogic.getGame().getCommonObj2().checkObj(current_client.getPlayer().getLibrary())){
                Message message=new Message(MessageType.objectiveCompleted,current_client.getPlayer().getNickname() + " successfully completed the second common goal\nnow has the " + gameLogic.getGame().getCommonObj2().getPointCount() + " card");
                sendGeneralMessage(message);
                current_client.getPlayer().addPoints(gameLogic.getGame().getCommonObj2().getPointCount());
                current_client.getPlayer().setCommonObj2Completed();
            }
        }

        // check player object
        if(current_client.getPlayer().getLibrary().checkFull()){
            gameLogic.getGame().getCurrentPlayer().addPoints(1);
            endGame = true;
        }
    }

    /**
     *
     * @param msg is the message controller wants to send to the client
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public void sendGeneralMessage(Message msg) throws IOException, ClassNotFoundException {
        for (int i=0; i<clientList.size();i++){
            if(clientList.get(i).getClient() == null){
                serverSocket.sendMessage(msg,clientList.get(i).getSocket());
            }else{
                serverRMI.messageToClient(msg.getMessage(),clientList.get(i).getClient());
            }
        }
    }

    /**
     * this method check the final groups of card of the same colore
     * @throws Exception
     */
    public void checkEnd() throws Exception{
        for(int i = 0; i < clientList.size(); i++){
                // check the player object
                clientList.get(i).getPlayer().addPoints(clientList.get(i).getPlayer().getPlayerObj().checkObj(clientList.get(i).getPlayer(), clientList.get(i).getPlayer().getLibrary()));

                // check the final groups of cards
                clientList.get(i).getPlayer().addPoints(clientList.get(i).getPlayer().getLibrary().checkFinal());

        }
    }
}
