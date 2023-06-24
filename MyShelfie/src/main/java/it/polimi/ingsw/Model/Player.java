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
     * The method is the constructor for the Player class
     * It sets the nickname, the library and initializes the playerObject.
     * It also initializes yourTurn and points
     * @param nickname
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
     * addPoints is used to add points to a player, it throws Exception if points is <=0
     * @param points
     * @throws Exception
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
     * set the player's library
     * @param library library we want to set as player's library
     */
    public void setLibrary(Library library){
        this.library = library;
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
     * @return true if commonObj1 is completed
     */
    public boolean getCommonObj1Completed() {
        return commonObj1Completed;
    }

    /**
     *
     * @return true if commonObj2 is completed
     */
    public boolean getCommonObj2Completed() {
        return commonObj2Completed;
    }

    /**
     *
     * @return points of the player
     */
    public int getPoints(){
        return this.points;
    }

    /**
     * sets the points of the player
     * @param points we want to set to the player
     */
    public void setPoints(int points){
        this.points = points;
    }

}
