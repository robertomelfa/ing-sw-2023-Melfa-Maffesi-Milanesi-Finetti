package it.polimi.ingsw.Model;

import java.util.*;
import java.io.Serializable;

public class Game implements Serializable{
    private GameTable gameTable;
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
            this.cardbox = new CardBox();
            setBothCommonObj(numOfPlayers);
        }
    }

    public void setGameTable(GameTable gameTable){
        this.gameTable = gameTable;
    }

    /**
     *
     * @param
     * @throws Exception
     * The method adds a new player into the players list called players.
     * It throws Exception if the size of the list equals the number of players in the game, meaning that the game is full
     */
    public Player getCurrentPlayer(){
        return currentPlayer;
    }

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

    public void setCurrentPlayer(Player player){
        this.currentPlayer = player;
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