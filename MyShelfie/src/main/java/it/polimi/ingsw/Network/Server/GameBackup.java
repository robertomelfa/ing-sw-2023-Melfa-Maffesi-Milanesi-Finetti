package it.polimi.ingsw.Network.Server;


import it.polimi.ingsw.Model.Game;
import it.polimi.ingsw.Model.GameLogic;
import it.polimi.ingsw.Model.Player;
import it.polimi.ingsw.Network.Client.Socket.ClientClass;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

public class GameBackup implements Serializable {
    String name;
    private ArrayList<Player> players;
    private int listIterator;
    private int chair;
    private ArrayList<String> nicknames;
    private Date date;
    private GameLogic gamelogic;
    private Player current_client;

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

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public Date getDate() {
        return date;
    }

    public GameLogic getGamelogic() {
        return gamelogic;
    }

    public Player getCurrent_client() {
        return current_client;
    }

    public void setCurrent_client(Player current_client) {
        this.current_client = current_client;
    }

    public void setGamelogic(GameLogic gamelogic) {
        this.gamelogic = gamelogic;
    }

    public void setPlayers(ArrayList<Player> players) {
        this.players = players;
    }

    public ArrayList<String> getNicknames() {
        return nicknames;
    }

    public void setListIterator(int listIterator) {
        this.listIterator = listIterator;
    }

    public int getListIterator() {
        return listIterator;
    }

    public void setChair(int chair) {
        this.chair = chair;
    }

    public int getChair() {
        return chair;
    }
}
