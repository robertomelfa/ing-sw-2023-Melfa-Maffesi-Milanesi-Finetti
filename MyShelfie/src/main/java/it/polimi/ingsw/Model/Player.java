package it.polimi.ingsw.Model;

import java.io.Serializable;

public class Player implements Serializable{
    private boolean yourTurn;
    private int points;
    private String  nickname;
    private Library library;
    private PlayerObj playerObj;
    private boolean commonObj1Completed;
    private boolean commonObj2Completed;

    /**
     *
     * @param nickname
     * The method is the constructor for the Player class
     * It sets the nickname, the library and initializes the playerObject.
     * It also initializes yourTurn and points
     */
    public Player(String nickname) throws Exception{
        yourTurn = false;
        points = 0;
        library = new Library();
        this.nickname = nickname;
        this.playerObj = new PlayerObj();
        this.commonObj1Completed=false;
        this.commonObj2Completed=false;
    }

    /**
     *
     * @param points
     * @throws Exception
     * addPoints is used for adding points to a player, it trows Exception if points is <=0
     */
    public void addPoints(int points) throws Exception {
        if(points < 0){
            throw new Exception();
        }
        else {
            this.points = this.points+points;
        }
    }

    /**
     *
     * @return playerObj
     */
    public PlayerObj getPlayerObj(){ return playerObj; }

    /**
     *
     * @return the player's nickname
     */
    public String getNickname() {
        return nickname;
    }

    /**
     *
     * @return the player's library
     */
    public Library getLibrary(){
        return library;
    }

    /**
     *
     * @return the player's score
     */
    public int getScore(){
        return points;
    }

    public void setCommonObj1Completed() {
        this.commonObj1Completed = true;
    }

    public void setCommonObj2Completed() {
        this.commonObj2Completed = true;
    }


    /**
     *
     * @return commonObj1Completed
     */
    public boolean getCommonObj1Completed() {
        return commonObj1Completed;
    }

    /**
     *
     * @return commonObj2Completed
     */
    public boolean getCommonObj2Completed() {
        return commonObj2Completed;
    }

    public void endGame(){
        System.out.printf("[Server]: the game has come to the end! Please wait for the leaderboard...");
    }
}
