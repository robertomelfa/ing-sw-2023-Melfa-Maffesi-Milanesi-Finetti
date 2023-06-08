package it.polimi.ingsw.Controller;

import com.google.gson.Gson;
import it.polimi.ingsw.Controller.RMI.RMIController;
import it.polimi.ingsw.Controller.Socket.SocketController;
import it.polimi.ingsw.Model.*;
import it.polimi.ingsw.Network.Client.Socket.ClientClass;
import it.polimi.ingsw.Network.Messages.Message;
import it.polimi.ingsw.Network.Messages.MessageType;
import it.polimi.ingsw.Network.Server.GameBackup;
import it.polimi.ingsw.Network.Server.RMI.GameInterface;
import it.polimi.ingsw.Network.Server.Socket.Server_Socket;
import it.polimi.ingsw.View.CLIView;

import java.io.*;
import java.rmi.RemoteException;
import java.util.*;
import java.util.concurrent.TimeUnit;

import static it.polimi.ingsw.Model.Card.*;


public class ControllerMain implements Serializable {
    private ArrayList<ClientClass> clientList = new ArrayList<>();
    private GameBackup backup;
    private boolean backupCreated = false;
    private boolean isResumedGame = false;

    private int numPlayers = 0;

    private Server_Socket serverSocket;

    private GameInterface serverRMI;

    private boolean endGame = false;

    private boolean finish = false;

    private transient Thread thread;

    private int chair = 0;

    private int listIterator = 0;

    private GameLogic gameLogic;

    private ClientClass current_client;


    /**
     * constructor of the class: the controller is connected to both servers
     * @param serverSocket Socket Server
     * @param serverRMI RMI Server
     */
    public ControllerMain(Server_Socket serverSocket, GameInterface serverRMI){
        this.serverSocket = serverSocket;
        this.serverRMI = serverRMI;
        // starting thread check clients disconnection
        this.thread = new Thread(() ->{
            while(!finish){
                try {
                    Thread.sleep(10000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                try {
                    if(!clientList.isEmpty()){
                        checkConnection();
                    }
                } catch (IOException e) {
                    throw new RuntimeException(e);
                } catch (ClassNotFoundException e) {
                    throw new RuntimeException(e);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        thread.start();
    }

    /**
     * This method reset the clientList of the controller when a client crash
     */
    public void resetController(){
        clientList.clear();
    }

    /**
     *
     * @param controller controller we want to copy (it pass the list of the clients connected)
     */
    public void copy(ControllerMain controller){
        this.clientList.addAll(controller.getClientList());
        this.numPlayers = clientList.size();
    }

    public synchronized void updateBackup() throws IOException, StackOverflowError {
        if(!backupCreated){
            System.out.println("Starting backup");
            ArrayList<String> nick = new ArrayList<>();
            ArrayList<Player> ply = new ArrayList<>();
            for (int i=0; i<clientList.size();i++){
                nick.add(clientList.get(i).getPlayer().getNickname());
            }
            for (int i=0; i<clientList.size();i++){
                ply.add(clientList.get(i).getPlayer());
            }
            backup=new GameBackup(ply,gameLogic,current_client.getPlayer(),nick,listIterator,chair);
            Gson gson = new Gson();
            backup.setName(String.valueOf(backup.getDate().hashCode()));
            FileWriter writer = new FileWriter("MyShelfie/saves/"+backup.getName()+".json");
            gson.toJson(backup,writer);
            writer.flush();
            writer.close();
            System.out.println("Backup created");
            setBackupCreated(true);

        }else {
            System.out.println("Updating backup");
            backup.setCurrent_client(current_client.getPlayer());
            backup.setGamelogic(gameLogic);
            ArrayList<Player> ply = new ArrayList<>();
            for (int i=0; i<clientList.size();i++){
                ply.add(clientList.get(i).getPlayer());
            }
            backup.setPlayers(ply);
            backup.setChair(chair);
            backup.setListIterator(listIterator);
            Gson gson = new Gson();
            FileWriter writer = new FileWriter("MyShelfie/saves/"+backup.getName()+".json");
            gson.toJson(backup,writer);
            writer.flush();
            writer.close();
            System.out.println("Backup saved");
        }
    }


    public synchronized void deleteObsoleteJson(){
        File folder = new File("MyShelfie/saves/");
        Date date=new Date();
        date.getTime();
        long daysmillies = 7*24*60*60*1000;
        Gson gson = new Gson();
        for (File file : folder.listFiles()){
            if(!file.isDirectory() && file.isFile() && file.getName().endsWith(".json")){
                GameBackup temp;
                try{
                    FileReader reader = new FileReader(file);
                    temp=gson.fromJson(reader,GameBackup.class);
                    reader.close();
                    long diff = date.getTime() - temp.getDate().getTime();
                    if (diff >= daysmillies){
                        String tempName=file.getName();
                       boolean res = file.delete();
                       if (res){
                           System.out.println(tempName+" deleted");
                       }
                    }

                }catch (IOException e){
                    System.out.println("IO EXCEPTION");
                }
            }
        }

    }

    public synchronized void deleteBackup(){
        File folder = new File("MyShelfie/saves/");
        String currName = backup.getName()+".json";
        for (File file : folder.listFiles()){
            if (!file.isDirectory() && file.isFile() && file.getName().endsWith(".json")){
                if (file.getName().equals(currName)){
                    boolean res = file.delete();
                    if (res){
                        System.out.println("Deleting current backup");
                    }
                }
            }
        }
    }

    public synchronized void resumeBackup() throws Exception{
        System.out.println("Checking backups to resume");
        File folder = new File("MyShelfie/saves/");
        GameBackup found=null;
        Gson gson = new Gson();
        for (File file : folder.listFiles()){
            if(!file.isDirectory() && file.isFile() && file.getName().endsWith(".json")){
                GameBackup temp;
                try{
                    FileReader reader = new FileReader(file);
                    temp=gson.fromJson(reader,GameBackup.class);
                    System.out.println("Checking "+file.getName());
                    reader.close();
                    boolean resetGame=true;
                    for (int i=0; i<clientList.size(); i++){
                        if(!temp.getNicknames().contains(clientList.get(i).getPlayer().getNickname())){
                            resetGame=false;
                        }
                    }
                    if(resetGame){
                        found=temp;
                        try {
                            sendGeneralMessage(new Message(MessageType.printMessage,"Loading  game saved on "+found.getDate()));
                            System.out.println("Loading "+file.getName()+" date: "+found.getDate());
                        }catch (IOException e){
                            System.out.println("IO EXCEPTION");
                        }catch (ClassNotFoundException c){
                            System.out.println("CLASS NOT FOUND");
                        }
                        break;
                    }


                }catch (IOException e){
                    System.out.println("IO EXCEPTION");
                }

            }
        }
        if(found!=null){
            for (int i=0; i<clientList.size();i++){
                for (int j=0; j<found.getPlayers().size(); j++){
                    if(clientList.get(i).getPlayer().getNickname().equals(found.getPlayers().get(j).getNickname())){
                        try{

                            clientList.get(i).setPlayer(found.getPlayers().get(j));
                        }catch (Exception e){
                            System.out.println("EXCEPTION");
                        }
                    }

                }
                if (clientList.get(i).getPlayer().getNickname().equals(found.getCurrent_client().getNickname())){
                    current_client=clientList.get(i);
                }
            }
            for(int i=0; i<found.getPlayers().size(); i++){
                for (int j=0;j<clientList.size();j++){
                    if (found.getPlayers().get(i).getNickname().equals(clientList.get(j).getPlayer().getNickname())){
                        ClientClass temp = clientList.set(i,clientList.get(j));
                        clientList.set(j,temp);
                    }
                }
            }
            this.gameLogic=found.getGamelogic();
            for (int i=0; i<gameLogic.getPlayers().size(); i++){
                for (int j=0; j<found.getGamelogic().getPlayers().size();j++){
                    if (found.getGamelogic().getPlayers().get(j).getNickname().equals(gameLogic.getPlayers().get(i).getNickname())){
                        gameLogic.setPlayers(found.getGamelogic().getPlayers().get(j));
                    }
                }
            }
            this.chair=found.getChair();
            this.listIterator=found.getListIterator();
            this.backup=found;
            this.backup.setName(found.getName());
            setBackupCreated(true);
            setResumedGame(true);
        }

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
            if(clientList.size() == listIterator){
                listIterator = 0;
            }
            if(listIterator == 0){
                // the game is ended
                finish = true;
                // check the adiacent cards
                checkEnd();
            }
            else {
                current_client = clientList.get(listIterator);
            }
        }
    }

    /**
     *
     * @return the string which the points of each player
     */
    public void updatePlayers(){
        for(int i = 0; i < clientList.size(); i++){
            gameLogic.setPlayers(clientList.get(i).getPlayer());
        }
    }

    public void setGamePlayers(){
        for(int i = 0; i < clientList.size(); i++){
            gameLogic.addPlayer(clientList.get(i).getPlayer());
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

    /**
     * This method send the gameTable to all clients (socket and RMI)
     *
     * @param gameTable the gametable associate with the game
     * @throws ClassNotFoundException
     * @throws IOException
     * @throws Exception
     */
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
     *
     * Send points to all the clients
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public void pointsToAll() throws IOException, ClassNotFoundException{
        for(int i = 0; i < clientList.size(); i++){
            if(clientList.get(i).getClient() == null){
                serverSocket.sendPlayers(clientList.get(i).getSocket(), gameLogic.getPlayers());
            }else{
                clientList.get(i).getClient().receivePoint(gameLogic.getPlayers());
            }
        }
    }

    /**
     * this method control the game
     * @throws Exception
     * @throws ArrayIndexOutOfBoundsException
     */
    public void startGame() throws Exception, ArrayIndexOutOfBoundsException, IOException, ClassNotFoundException{
        // create game
        Game game = new Game(numPlayers);
        gameLogic = new GameLogic(game);
        setGamePlayers();
        deleteObsoleteJson();
        resumeBackup();
        if(!isResumedGame){
            shufflePlayers();
        }

        sendGeneralMessage(new Message(MessageType.printMessage, "Game is starting..."));
        while(!finish){
            // update the players in gamelogic
            updatePlayers();
            //send points to all the players
            pointsToAll();
            // advise the current player
            sendGeneralMessage(new Message(MessageType.printMessage, current_client.getPlayer().getNickname() + " is your turn!"));
            // set the current player in gameLogic
            gameLogic.getGame().setCurrentPlayer(current_client.getPlayer());
            // print the game table to all the clients
            gameTableToALL(gameLogic.getGameTable());
            // handle the turn in base of RMI or Socket clients
            if(current_client.getClient() == null){
                SocketController controllerS = new SocketController(serverSocket, current_client, gameLogic, current_client.isGui());
                gameLogic = controllerS.takeTurn();
                current_client.getPlayer().setLibrary(gameLogic.getGame().getCurrentPlayer().getLibrary());
            }else{
                RMIController controllerR = new RMIController(gameLogic, current_client, serverRMI, current_client.isGui());
                gameLogic = controllerR.takeTurn();
                current_client.getPlayer().setLibrary(gameLogic.getGame().getCurrentPlayer().getLibrary());
            }
            // check the objectives of the current player at the end of the turn
            checkObjectives();
            //update the current player
            updateCurrentPlayer();
            // update backup
            updateBackup();
        }
        //updating points in endgame
        updatePlayers();
        //send final gametable to all
        gameTableToALL(gameLogic.getGameTable());
        //updating points to all
        pointsToAll();
        // print classification
        sendGeneralMessage(new Message(MessageType.showLeaderboard,getClassification()));
        //delete current backup
        deleteBackup();
        // notify the game is ended
        sendGeneralMessage(new Message(MessageType.endGame,"Game is ended"));
        TimeUnit.MINUTES.sleep(5);

    }

    /**
     * This method checks all the objects
     * @throws Exception
     */
    public void checkObjectives() throws Exception{

        // check common object 1
        if(!current_client.getPlayer().getCommonObj1Completed()){
            if(gameLogic.getGame().getCommonObj1().checkObj(current_client.getPlayer().getLibrary())){
                int points1 = 0;
                points1 = gameLogic.getGame().getCommonObj1().getPointCount();
                Message message=new Message(MessageType.objectiveCompleted,current_client.getPlayer().getNickname() + " successfully completed the first common goal\nnow has the " + points1 +" card");
                sendGeneralMessage(message);
                current_client.getPlayer().addPoints(points1);
                current_client.getPlayer().setCommonObj1Completed();

            }
        }

        // check common object 2
        if(!current_client.getPlayer().getCommonObj2Completed()){
            if(gameLogic.getGame().getCommonObj2().checkObj(current_client.getPlayer().getLibrary())){
                int points2 = 0;
                points2 = gameLogic.getGame().getCommonObj2().getPointCount();
                Message message=new Message(MessageType.objectiveCompleted,current_client.getPlayer().getNickname() + " successfully completed the second common goal\nnow has the " + points2 + " card");
                sendGeneralMessage(message);
                current_client.getPlayer().addPoints(points2);
                current_client.getPlayer().setCommonObj2Completed();
            }
        }

        // end game
        if(current_client.getPlayer().getLibrary().checkFull() && !endGame){
            current_client.getPlayer().addPoints(1);
            Message message=new Message(MessageType.objectiveCompleted,current_client.getPlayer().getNickname() + " finish the game. Now he has the bonus point");
            sendGeneralMessage(message);
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

    public void setBackupCreated(boolean backupCreated) {
        this.backupCreated = backupCreated;
    }

    public void setResumedGame(boolean resumedGame) {
        isResumedGame = resumedGame;
    }

    public GameBackup getGameBackup() {
        return backup;
    }

    /**
     *
     * @return the String with the classification of the game
     */
    public String getClassification(){
        List<Player> playersList = new ArrayList<>();
        String string ="\nLeaderboard:\n";
        for(int i = 0; i < clientList.size(); i++){
            playersList.add(clientList.get(i).getPlayer());
        }
        playersList.sort(Comparator.comparingInt(Player::getScore).reversed());
        for(int i = 0; i < playersList.size(); i++){
            string = string + (i+1) + "° : " + playersList.get(i).getNickname() + " with " + playersList.get(i).getScore() + "\n";
        }
        return string;
    }

    /**
     * This method checks if any client is disconnected, if yes the game will stop
     *
     * @throws IOException if the socket client is disconnected
     * @throws ClassNotFoundException
     * @throws InterruptedException
     */
    public void checkConnection() throws IOException, ClassNotFoundException, InterruptedException {
        Message msg1 = new Message(MessageType.closeGame, "Stop game");
        for(int i = 0; i < clientList.size(); i++){
            if(clientList.get(i).getSocket() == null){
                try {
                    clientList.get(i).getClient().ping();
                }catch (Exception e){
                    finish = true;
                    clientList.remove(i);
                    sendGeneralMessage(msg1);
                    resetController();
                }
            }else{
                Message msg = new Message(MessageType.ping, null);
                try{
                    serverSocket.sendMessage(msg, clientList.get(i).getSocket());
                }catch (IOException e){
                    finish = true;
                    clientList.remove(i);
                    sendGeneralMessage(msg1);
                    resetController();
                }
            }
        }
    }

}

/**
 * Class to compare the players' scores
 */
class ScoreComparator implements Comparator<Player>{

    @Override
    public int compare(Player p1, Player p2){
        return Integer.compare(p1.getScore(), p2.getScore());
    }
}

