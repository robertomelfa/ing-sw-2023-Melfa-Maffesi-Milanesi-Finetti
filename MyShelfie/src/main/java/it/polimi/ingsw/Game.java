package it.polimi.ingsw;

import java.util.List;

public class Game {
    private GameTable gameTable;
    private List<Player> players;
    private int numOfPlayers;
    private int chair;
    private Player currentPlayer;
    private Cardbox cardbox;
    private CommonObj commonObj1;
    private CommonObj commonObj2;

    public Game(){}

    public void addNewPlayer(){}

    private void setChairOrder(){}

    public Player getChair(){}

    public Player getCurrentPlayer(){};

    private void setBothCommonObj(int numOfPlayers){}

    public void turn(Player player){}

    public void getLeaderboard(){}
}
