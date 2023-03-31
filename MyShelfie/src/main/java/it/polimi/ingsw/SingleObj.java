package it.polimi.ingsw;


public class SingleObj {

    int x;

    int y;

    Card type;

    /**
     *
     * @param x, the line of the single goal
     * Constructor for the single personal goal: makes a random goal in a random position in the line selected
     */

    public SingleObj (int x, int y, Card type){
        this.x=x;
        this.y=y;
        this.type=type;
    }

    /**
     *
     * @return x, the x-position of the goal
     */
    public int getXPosition(){
        return x;
    }

    /**
     *
     * @return y, the y-position of the goal
     */
    public int getYPosition(){
        return y;
    }

    /**
     *
     * @return Card, the type of the goal
     */
    public Card getType(){
        return type;
    }
}
