package it.polimi.ingsw.Network.Server;


import it.polimi.ingsw.Model.GameLogic;
import it.polimi.ingsw.Model.Player;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

public class GameBackup implements Serializable {
    String name;
    private ArrayList<Player> players;
    private int listIterator;
    private int chair;
    private ArrayList<String> nicknames;
    private Date date;
    private GameLogic gamelogic;
    private Player current_client;

    /**
     * constructor of the GameBackup class
     * @param players player's list
     * @param gamelogic the game logic of the game
     * @param current_client the current player who needs to perform the turn
     * @param nicknames list of the player's nicknames
     * @param listIterator value of the list iterator
     * @param chair int that represents the player who has the chair
     */
    public GameBackup(ArrayList<Player> players, GameLogic gamelogic, Player current_client, ArrayList<String> nicknames, int listIterator, int chair){
        this.players=players;
        date=new Date();
        date.getTime();
        this.gamelogic=gamelogic;
        this.current_client=current_client;
        this.nicknames=nicknames;
        this.chair=chair;
        this.listIterator=listIterator;
    }

    /**
     *
     * @param name name we want to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     *
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     *
     * @return players list
     */
    public ArrayList<Player> getPlayers() {
        return players;
    }

    /**
     *
     * @return date
     */
    public Date getDate() {
        return date;
    }

    /**
     *
     * @return the gamelogic
     */
    public GameLogic getGamelogic() {
        return gamelogic;
    }

    /**
     *
     * @return the current client
     */
    public Player getCurrent_client() {
        return current_client;
    }

    /**
     *
     * @param current_client client we want to set
     */
    public void setCurrent_client(Player current_client) {
        this.current_client = current_client;
    }

    /**
     *
     * @param gamelogic gamelogic we want to set
     */
    public void setGamelogic(GameLogic gamelogic) {
        this.gamelogic = gamelogic;
    }

    /**
     *
     * @param players list of players we want to set
     */
    public void setPlayers(ArrayList<Player> players) {
        this.players = players;
    }

    /**
     *
     * @return the nicknames of all the players in the game
     */
    public ArrayList<String> getNicknames() {
        return nicknames;
    }

    /**
     *
     * @param listIterator value of the list iterator
     */
    public void setListIterator(int listIterator) {
        this.listIterator = listIterator;
    }

    /**
     *
     * @return the value of the list iterator
     */
    public int getListIterator() {
        return listIterator;
    }

    /**
     *
     * @param chair int that shows the position of the chair int the player's list
     */
    public void setChair(int chair) {
        this.chair = chair;
    }

    /**
     *
     * @return the position of the chair
     */
    public int getChair() {
        return chair;
    }
}
