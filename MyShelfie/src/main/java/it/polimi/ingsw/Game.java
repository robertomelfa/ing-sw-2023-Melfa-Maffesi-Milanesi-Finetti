package it.polimi.ingsw;

import java.util.*;

public class Game {
    private GameTable gameTable;
    private List<Player> players;
    private int numOfPlayers;
    private int chair;
    private Player currentPlayer;
    private Cardbox cardbox;
    private CommonObj commonObj1;
    private CommonObj commonObj2;

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
            this.gameTable = new GameTable(numOfPlayers);
            players = new LinkedList<>();
            this.cardbox = new Cardbox();
            setBothCommonObj(numOfPlayers);
        }


    }

    /**
     *
     * @param player
     * @throws Exception
     * The method adds a new player into the players list called players.
     * It throws Exception if the size of the list equals the number of players in the game that means that the game is full
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
     * This method prints the player list
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
     * The method shuffles randomly the players list, it sets chair to 0 and sets currentPlayer on the head of the list
     */
    private void setChairOrder(){
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
        commonObj1=new CommonObj();
        commonObj2=new CommonObj();
    }

    public void turn(Player player){

    }

    /**
     * This method prints the leaderboard, that is the list of players and their points scored sorted by score
     */
    public void printLeaderboard(){
        List<Player> temp= new LinkedList<>();
        temp.addAll(players);
        ScoreComparator comparator=new ScoreComparator();
        Collections.sort(temp,comparator);
        StringBuilder str = new StringBuilder();
        for (int i=0; i<temp.size();i++){
            str.append(temp.get(i).getNickname()+"  "+temp.get(i).getScore()+" points\n");
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
    public Cardbox getCardbox() {
        return cardbox;
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