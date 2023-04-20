package it.polimi.ingsw.Model;

import java.util.*;

public class Game {
    private GameTable gameTable;
    private List<Player> players;
    private int numOfPlayers;
    private int chair;
    private Player currentPlayer;
    private CardBox cardbox;
    private CommonObj commonObj1;
    private CommonObj commonObj2;
    private boolean endGame;

    private int listIteration = 0;

    /**
     *
     * Constructor for the Game class: it sets the gametable, it initializes the players list, sets the cardbox and it calls
     * setBothCommonObj
     *
     */
    public Game(int numOfPlayers) throws Exception{
        if(numOfPlayers < 2 || numOfPlayers > 4){
            System.out.println("the number of players must be between 2 and 4, try again!");
            throw new Exception();
        }else {
            this.numOfPlayers = numOfPlayers;
            this.gameTable = new GameTable(numOfPlayers);
            players = new LinkedList<>();
            this.cardbox = new CardBox();
            setBothCommonObj(numOfPlayers);
        }
    }

    /**
     *
     * @param player
     * @throws Exception
     * The method adds a new player into the players list called players.
     * It throws Exception if the size of the list equals the number of players in the game, meaning that the game is full
     */
    public void addNewPlayer(Player player) throws Exception{
        if(players.size()==numOfPlayers){
            throw new Exception();
        }
        else
        {
           players.add(player);
        }
    }

    /**
     * Method that prints the player list
     */
    public void printPlayers(){
        StringBuilder str=new StringBuilder();
        for (int i=0; i<players.size();i++){
            str.append(players.get(i).getNickname());
            str.append("    ");
        }
        System.out.println(str);
    }

    /**
     * Method that shuffles randomly the players list, it sets chair to 0 and sets currentPlayer on the head of the list
     */
    public void setChairOrder(){
        java.util.Collections.shuffle(players);
        chair=0;
        currentPlayer=players.get(0);

    }


    /**
     *
     * @return the player that has the chair (the player index equals chair)
     */
    public Player getChair(){
        return players.get(chair);
    }

    /**
     *
     * @return currentPlayer
     */
    public Player getCurrentPlayer(){
        return currentPlayer;
    };

    private void setBothCommonObj(int numOfPlayers){
        Random rn = new Random();
        int rand = rn.nextInt(12)+1;
        int rand1 = rn.nextInt(12)+1;
        commonObj1 = new CommonObj(numOfPlayers, rand);
        while (rand1 == rand){ rand1 = rn.nextInt(12)+1;}
        commonObj2 = new CommonObj(numOfPlayers, rand1);
    }

    /**
     * This method prints the leaderboard, that is the list of players and their points scored sorted by score
     * This method will be in the view
     */
    public void printLeaderboard(){
        List<Player> temp = new LinkedList<>();
        temp.addAll(players);
        ScoreComparator comparator = new ScoreComparator();
        Collections.sort(temp, comparator);
        StringBuilder str = new StringBuilder();
        for (int i=0; i < temp.size(); i++){
            str.append(temp.get(i).getNickname() + "  " + temp.get(i).getScore() + " points\n");
        }
        System.out.println(str);
    }

    /**
     *
     * @return the game board
     */
    public GameTable getGameTable(){
        return  this.gameTable;
    }

    /**
     *
     * @return the cardbox linked to game
     */
    public CardBox getCardbox() {
        return cardbox;
    }

    /**
     *
     * @return commonObj1
     */
    public CommonObj getCommonObj1(){
        return commonObj1;
    }

    /**
     *
     * @return NumOfPlayers
     */
    public int getNumOfPlayers() {
        return numOfPlayers;
    }

    /**
     *
     * @return commonObj2
     */
    public CommonObj getCommonObj2(){
        return commonObj2;
    }

    /**
     * This method is used to update the current player
     */
    public void updateCurrentPlayer() throws Exception{
        if(!endGame){
            listIteration++;
            if(players.size() == listIteration){
                listIteration = 0;
                currentPlayer = players.get(listIteration);
            }else {
                currentPlayer = players.get(listIteration);
            }
        }else {
            listIteration++;
            if(players.size() == listIteration){
                checkEnd();
                throw new Exception("GAME IS ENDED");   // probably this will be in the view; metterei più un messaggio che un'eccezione che da meno problemi @simone
            }
            else {
                currentPlayer = players.get(listIteration);
            }
        }
    }

    /**
     * Method that sets endGame=true
     */
    public void setEndGame() {
        this.endGame = true;
    }

    /**
     *
     * @return endGame
     */
    public boolean getEndGame(){
        return endGame;
    }

    /**
     * This method adds points for each group of cards, for each player
     */
    public void checkEnd(){
        for(int i = 0; i < players.size(); i++){
            try{
                players.get(i).addPoints(players.get(i).getPlayerObj().checkObj(players.get(i), players.get(i).getLibrary()));
                players.get(i).addPoints(players.get(i).getLibrary().checkFinal());
          //      System.out.println("Player " + players.get(i).getNickname() + " hai totalizzato " + players.get(i).getScore());  // view
            }catch (Exception e){}
        }
    }
}

/**
 * Class used for sorting the players list by score
 */
class ScoreComparator implements Comparator<Player>{

    @Override
    public int compare(Player p1, Player p2){
        if(p1.getScore() >= p2.getScore()){
            return -1;
        }
        else
        {
            return 1;
        }
    }
}