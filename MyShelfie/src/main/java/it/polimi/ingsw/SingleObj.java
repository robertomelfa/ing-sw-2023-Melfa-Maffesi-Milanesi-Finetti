package it.polimi.ingsw;

import java.util.Random;

public class SingleObj {

    int x;

    int y;

    Card type;

    /**
     *
     * @param x, the line of the single goal
     * Constructor for the single personal goal: makes a random goal in a random position in the line selected
     */
    public SingleObj (int x){

        Random random = new Random();
        int num = random.nextInt(6)+1;

        this.x = x;
        this.y = random.nextInt(6);

        switch (num) {
            case 1 -> this.type = Card.WHITE;
            case 2 -> this.type = Card.BLUE;
            case 3 -> this.type = Card.LIGHTBLUE;
            case 4 -> this.type = Card.YELLOW;
            case 5 -> this.type = Card.GREEN;
            case 6 -> this.type = Card.PURPLE;
        }

    }

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
