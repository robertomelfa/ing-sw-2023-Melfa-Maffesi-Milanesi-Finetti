package it.polimi.ingsw.Model;

import java.util.*;
import java.io.Serializable;

public class Game implements Serializable{
    private GameTable gameTable;
    private int numOfPlayers;
    private Player currentPlayer;
    private CardBox cardbox;
    private CommonObj commonObj1;
    private CommonObj commonObj2;

    /**
     *
     * Constructor for the Game class: it sets the gametable, it initializes the players list, sets the cardbox and it calls
     * setBothCommonObj
     *
     */
    public Game(int numOfPlayers){
        this.numOfPlayers = numOfPlayers;
        this.gameTable = new GameTable(numOfPlayers);
        this.cardbox = new CardBox();
        setBothCommonObj(numOfPlayers);
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
     *
     * @param player the current player in the game
     */
    public void setCurrentPlayer(Player player){
        this.currentPlayer = player;
    }
}