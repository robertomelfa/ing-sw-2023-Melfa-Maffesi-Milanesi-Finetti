package it.polimi.ingsw;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;


public class PlayerObj {
    private final ArrayList<SingleObj> playerObjs ;
    private static final ArrayList <Integer> avaliable = new ArrayList<Integer>(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12));
    private final int[] POINT = {1, 2, 4, 6, 9, 12};
    private int i;

    /**
     *
     * @param library, library to check
     * @return POINT, differenze between the points reached and the points already taken
     * Method that check the status of the personal goal and return the points
     */
    public int checkObj(Library library) throws Exception{

        //contatore delle caselle obiettivo completate
        int count = 0;

        //per ogni casella obiettivo controllo che nella stessa posizione della library ci sia la stessa carta e aggiorno il contatore
        for (SingleObj singleObj : playerObjs) {
            if (library.getPos(singleObj.x, singleObj.y) == singleObj.type) count++;

        }

        if (count > i) {

            this.i = count;

            return POINT[count-1] - POINT[i];

        }else return 0;
    }

    /**
     * Constructor for the PlayerObj class: choose randomly one of the personal goal of the game
     */
    public PlayerObj() throws Exception{
//        for(int i=0; i < 6; i++){
//            assert false;                               //lo ha messo java per completezza
//            playerObjs.add(new SingleObj(i));
//        }

        Random rn = new Random();
        int k=avaliable.size()-1;
        int rand = rn.nextInt(k)+1;
        switch (avaliable.get(rand)){
            case 1 -> {this.playerObjs = obj1();}
            case 2 -> {this.playerObjs = obj2();}
            case 3 -> {this.playerObjs = obj3();}
            case 4 -> {this.playerObjs = obj4();}
            case 5 -> {this.playerObjs = obj5();}
            case 6 -> {this.playerObjs = obj6();}
            case 7 -> {this.playerObjs = obj7();}
            case 8 -> {this.playerObjs = obj8();}
            case 9 -> {this.playerObjs = obj9();}
            case 10 -> {this.playerObjs = obj10();}
            case 11 -> {this.playerObjs = obj11();}
            case 12 -> {this.playerObjs = obj12();}
            default -> throw new Exception("error");
        }
        avaliable.remove(rand);
    }

    private ArrayList <SingleObj>  obj1 (){
        this.playerObjs.add(new SingleObj(1,1,Card.PURPLE));
        this.playerObjs.add(new SingleObj(2,0,Card.GREEN));
        this.playerObjs.add(new SingleObj(2,2,Card.YELLOW));
        this.playerObjs.add(new SingleObj(3,4,Card.WHITE));
        this.playerObjs.add(new SingleObj(4,3,Card.LIGHTBLUE));
        this.playerObjs.add(new SingleObj(5,4,Card.BLUE));
        return playerObjs;
    }

    private ArrayList <SingleObj> obj2 (){
        this.playerObjs.add(new SingleObj(0,0,Card.PURPLE));
        this.playerObjs.add(new SingleObj(0,2,Card.BLUE));
        this.playerObjs.add(new SingleObj(1,4,Card.GREEN));
        this.playerObjs.add(new SingleObj(2,3,Card.WHITE));
        this.playerObjs.add(new SingleObj(3,1,Card.YELLOW));
        this.playerObjs.add(new SingleObj(5,2,Card.LIGHTBLUE));
        return playerObjs;
    }

    private ArrayList <SingleObj> obj3 (){
        this.playerObjs.add(new SingleObj(1,0,Card.BLUE));
        this.playerObjs.add(new SingleObj(1,3,Card.YELLOW));
        this.playerObjs.add(new SingleObj(2,2,Card.PURPLE));
        this.playerObjs.add(new SingleObj(3,1,Card.GREEN));
        this.playerObjs.add(new SingleObj(3,4,Card.LIGHTBLUE));
        this.playerObjs.add(new SingleObj(5,0,Card.WHITE));
        return playerObjs;
    }

    private ArrayList <SingleObj> obj4 (){
        this.playerObjs.add(new SingleObj(0,4,Card.YELLOW));
        this.playerObjs.add(new SingleObj(2,0,Card.LIGHTBLUE));
        this.playerObjs.add(new SingleObj(2,2,Card.BLUE));
        this.playerObjs.add(new SingleObj(3,3,Card.PURPLE));
        this.playerObjs.add(new SingleObj(4,1,Card.WHITE));
        this.playerObjs.add(new SingleObj(4,2,Card.GREEN));
        return playerObjs;
    }

    private ArrayList <SingleObj> obj5 (){
        this.playerObjs.add(new SingleObj(1,1,Card.LIGHTBLUE));
        this.playerObjs.add(new SingleObj(3,1,Card.BLUE));
        this.playerObjs.add(new SingleObj(3,2,Card.WHITE));
        this.playerObjs.add(new SingleObj(4,4,Card.PURPLE));
        this.playerObjs.add(new SingleObj(5,0,Card.YELLOW));
        this.playerObjs.add(new SingleObj(5,3,Card.GREEN));
        return playerObjs;
    }

    private ArrayList <SingleObj> obj6 (){
        this.playerObjs.add(new SingleObj(0,2,Card.LIGHTBLUE));
        this.playerObjs.add(new SingleObj(0,4,Card.GREEN));
        this.playerObjs.add(new SingleObj(2,3,Card.WHITE));
        this.playerObjs.add(new SingleObj(4,1,Card.YELLOW));
        this.playerObjs.add(new SingleObj(4,3,Card.BLUE));
        this.playerObjs.add(new SingleObj(5,0,Card.PURPLE));
        return playerObjs;
    }

    private ArrayList <SingleObj> obj7 (){
        this.playerObjs.add(new SingleObj(0,0,Card.GREEN));
        this.playerObjs.add(new SingleObj(1,3,Card.BLUE));
        this.playerObjs.add(new SingleObj(2,1,Card.PURPLE));
        this.playerObjs.add(new SingleObj(3,0,Card.LIGHTBLUE));
        this.playerObjs.add(new SingleObj(4,4,Card.YELLOW));
        this.playerObjs.add(new SingleObj(5,2,Card.WHITE));
        return playerObjs;
    }

    private ArrayList <SingleObj> obj8 (){
        this.playerObjs.add(new SingleObj(0,4,Card.BLUE));
        this.playerObjs.add(new SingleObj(1,1,Card.GREEN));
        this.playerObjs.add(new SingleObj(2,2,Card.LIGHTBLUE));
        this.playerObjs.add(new SingleObj(3,0,Card.PURPLE));
        this.playerObjs.add(new SingleObj(4,3,Card.WHITE));
        this.playerObjs.add(new SingleObj(5,3,Card.YELLOW));
        return playerObjs;
    }

    private ArrayList <SingleObj> obj9 (){
        this.playerObjs.add(new SingleObj(0,2,Card.YELLOW));
        this.playerObjs.add(new SingleObj(2,2,Card.GREEN));
        this.playerObjs.add(new SingleObj(3,4,Card.WHITE));
        this.playerObjs.add(new SingleObj(4,1,Card.LIGHTBLUE));
        this.playerObjs.add(new SingleObj(4,4,Card.PURPLE));
        this.playerObjs.add(new SingleObj(5,0,Card.BLUE));
        return playerObjs;
    }

    private ArrayList <SingleObj> obj10 (){
        this.playerObjs.add(new SingleObj(0,4,Card.LIGHTBLUE));
        this.playerObjs.add(new SingleObj(1,1,Card.YELLOW));
        this.playerObjs.add(new SingleObj(2,0,Card.WHITE));
        this.playerObjs.add(new SingleObj(3,3,Card.GREEN));
        this.playerObjs.add(new SingleObj(4,1,Card.BLUE));
        this.playerObjs.add(new SingleObj(5,3,Card.PURPLE));
        return playerObjs;
    }

    private ArrayList <SingleObj> obj11 (){
        this.playerObjs.add(new SingleObj(0,2,Card.PURPLE));
        this.playerObjs.add(new SingleObj(1,1,Card.WHITE));
        this.playerObjs.add(new SingleObj(2,0,Card.YELLOW));
        this.playerObjs.add(new SingleObj(3,2,Card.BLUE));
        this.playerObjs.add(new SingleObj(4,4,Card.GREEN));
        this.playerObjs.add(new SingleObj(5,3,Card.LIGHTBLUE));
        return playerObjs;
    }

    private ArrayList <SingleObj> obj12 (){
        this.playerObjs.add(new SingleObj(0,2,Card.WHITE));
        this.playerObjs.add(new SingleObj(1,1,Card.PURPLE));
        this.playerObjs.add(new SingleObj(2,2,Card.BLUE));
        this.playerObjs.add(new SingleObj(3,3,Card.LIGHTBLUE));
        this.playerObjs.add(new SingleObj(4,4,Card.YELLOW));
        this.playerObjs.add(new SingleObj(5,0,Card.GREEN));
        return playerObjs;
    }
}

