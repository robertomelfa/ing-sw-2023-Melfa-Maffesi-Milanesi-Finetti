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
     * Constructor for the Game class: it sets the gametable, it initializes the players list, sets the cardbox and calls
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
     * @return the current player
     * @throws Exception
     */
    public Player getCurrentPlayer(){
        return currentPlayer;
    }

    /**
     * set both the commonObj using two different random numbers
     * @param numOfPlayers number of players
     */
    private void setBothCommonObj(int numOfPlayers){
        Random rn = new Random();
        int rand = rn.nextInt(12)+1;
        int rand1 = rn.nextInt(12)+1;
        commonObj1 = new CommonObj(numOfPlayers, rand);
        while (rand1 == rand){ rand1 = rn.nextInt(12)+1;}
        commonObj2 = new CommonObj(numOfPlayers, rand1);
    }

    /**
     * @return the gameTable
     */
    public GameTable getGameTable(){
        return  this.gameTable;
    }

    /**
     * @return commonObj1
     */
    public CommonObj getCommonObj1(){
        return commonObj1;
    }

    /**
     * @return the number of players
     */
    public int getNumOfPlayers() {
        return numOfPlayers;
    }

    /**
     * @return commonObj2
     */
    public CommonObj getCommonObj2(){
        return commonObj2;
    }

    /**
     * set a player as the current player
     * @param player player we want to set as the current player
     */
    public void setCurrentPlayer(Player player){
        this.currentPlayer = player;
    }
}