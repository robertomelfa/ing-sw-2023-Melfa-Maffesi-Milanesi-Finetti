package it.polimi.ingsw;

public class Player {
    private boolean yourTurn;
    private int points;
    private String  nickname;
    private Library library;
    private PlayerObj playerObj;

    /**
     *
     * @param nickname
     * @param playerObj, the object linked to the player
     * The method is the constructor for the Player class
     * It sets the nickname, the library and the playerObject.
     * It also initializes yourTurn and points
     */
    public Player(String nickname, PlayerObj playerObj){
        yourTurn = false;
        points = 0;
        library = new Library();
        this.nickname = nickname;
        this.playerObj = playerObj;

    }

    /**
     *
     * @param points
     * @throws Exception
     * addPoints is used for adding points to a player, it trows Exception if points is <=0
     */
    public void addPoints(int points) throws Exception {
        if(points <= 0){
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

}
