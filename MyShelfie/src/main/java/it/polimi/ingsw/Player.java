package it.polimi.ingsw;

public class Player {
    private boolean yourTurn;
    private int points;
    private String  nickname;
    private Library library;
    private PlayerObj playerObj;

    public Player(String nickname, Library library, PlayerObj playerObj){
        yourTurn=false;
        points=0;
        this.nickname=nickname;
        this.library=library;
        this.playerObj=playerObj;

    }

    public void addPoints(int points) throws Exception {
        if(points<=0){
            throw new Exception();
        }
        else {
            this.points=this.points+points;
        }

    }

    public Library getLibrary(){
        return library;
    }

    public int getScore(){
        return points;
    }

}
