package it.polimi.ingsw.Controller;

import it.polimi.ingsw.Controller.RMI.RMIController;
import it.polimi.ingsw.Controller.Socket.SocketController;
import it.polimi.ingsw.Model.Game;
import it.polimi.ingsw.Model.GameLogic;
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



    public ControllerMain(Server_Socket serverSocket, GameInterface serverRMI){
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
                checkEnd();
                sendGeneralMessage(new Message(MessageType.endGame,"Game is ended"));

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
        sendGeneralMessage(new Message(MessageType.printMessage, "Game is starting..."));
        shufflePlayers();
        //requestNickname();        da terminare e bisogna eliminare tutti i riferimenti e le chiamate per i nickname nelle classi
        while(true){
            if(current_client.getClient() == null){
                SocketController controllerS = new SocketController(serverSocket, current_client, gameLogic);
                gameLogic = controllerS.takeTurn();
            }else{
                RMIController controllerR = new RMIController(gameLogic, current_client.getClient(), serverRMI);
                gameLogic = controllerR.takeTurn();
            }
            checkObjectives();
            updateCurrentPlayer();
        }
        // TODO sistemare il turno con tutte le verifiche del caso
    }

    public void checkObjectives() throws Exception{
        if(!current_client.getPlayer().getCommonObj1Completed()){
            if(gameLogic.getGame().getCommonObj1().checkObj(current_client.getPlayer().getLibrary())){
                Message message=new Message(MessageType.objectiveCompleted,current_client.getPlayer().getNickname() + " successfully completed the first common goal\nnow has the " +gameLogic.getGame().getCommonObj1().getPointCount() +" card");
                sendGeneralMessage(message);
                current_client.getPlayer().addPoints(gameLogic.getGame().getCommonObj1().getPointCount());
                current_client.getPlayer().setCommonObj2Completed();

            }
        }

        if(!current_client.getPlayer().getCommonObj2Completed()){
            if(gameLogic.getGame().getCommonObj2().checkObj(current_client.getPlayer().getLibrary())){
                Message message=new Message(MessageType.objectiveCompleted,current_client.getPlayer().getNickname() + " successfully completed the second common goal\nnow has the " + gameLogic.getGame().getCommonObj2().getPointCount() + " card");
                sendGeneralMessage(message);
                current_client.getPlayer().addPoints(gameLogic.getGame().getCommonObj2().getPointCount());
                current_client.getPlayer().setCommonObj2Completed();
            }
        }


        if(current_client.getPlayer().getLibrary().checkFull()){
            //gameLogic.getGame().setEndGame();
            gameLogic.getGame().getCurrentPlayer().addPoints(1);
            endGame = true;
        }



    }


    public void sendGeneralMessage(Message msg) throws IOException, ClassNotFoundException {
        for (int i=0; i<clientList.size();i++){
            if(clientList.get(i).getClient() == null){
                serverSocket.sendMessage(msg,clientList.get(i).getSocket());
            }else{
                serverRMI.messageToClient(msg.getMessage(),clientList.get(i).getClient());
            }
        }
    }

    public void checkEnd() throws Exception{
        for(int i = 0; i < clientList.size(); i++){
                // check the player object
                clientList.get(i).getPlayer().addPoints(clientList.get(i).getPlayer().getPlayerObj().checkObj(clientList.get(i).getPlayer(), clientList.get(i).getPlayer().getLibrary()));

                // check the final groups of cards
                clientList.get(i).getPlayer().addPoints(clientList.get(i).getPlayer().getLibrary().checkFinal());

        }
    }

    public void requestNickname() throws IOException, ClassNotFoundException, Exception{
        ArrayList<String> nicknames=new ArrayList<>();
        for (int i=0;i<clientList.size();i++){
            boolean askagain=true;
            Message message=null;
            while (askagain){
                if(clientList.get(i).getClient()==null){
                    serverSocket.sendMessage(new Message(MessageType.requestNickname,null),clientList.get(i).getSocket());
                    message=serverSocket.receiveMessage(clientList.get(i).getSocket());
                    if (message.getType()!=MessageType.sendNickname){
                        throw new Exception("Invalid message type");
                    }
                    if (!nicknames.contains(message.getMessage())){
                        askagain=false;
                    }else {
                        serverSocket.sendMessage(new Message(MessageType.printMessage,message.getMessage()+" is already taken, choose another nickname"),clientList.get(i).getSocket());
                    }
                }else {
                    //inserire controllo per RMI
                }
            }
            if (clientList.get(i).getClient()==null){
                nicknames.add(message.getMessage());
            }else {
                //aggiunta nickname a RMI
            }

        }
        for (int j=0; j<nicknames.size();j++){
            clientList.get(j).setPlayer(nicknames.get(j));
        }
    }

}
