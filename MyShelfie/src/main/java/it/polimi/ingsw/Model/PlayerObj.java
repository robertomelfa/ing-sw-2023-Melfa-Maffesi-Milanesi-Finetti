package it.polimi.ingsw.Model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import static it.polimi.ingsw.Model.Card.NONE;
import static it.polimi.ingsw.Model.Card.NOT;
import java.io.Serializable;


public class PlayerObj implements Serializable{
    private final ArrayList<SingleObj> playerObjs ;
    private static ArrayList <Integer> available = new ArrayList<>(Arrays.asList(1,2,3,4,5,6,7,8,9,10,11,12));
    private final int[] POINT = {1, 2, 4, 6, 9, 12};
    private int num;

    /**
     * @param player, player to check
     * @param library, library to check
     * @return POINT, difference between the points reached and the points already taken
     * Method that check the status of the personal goal and return the points
     */
    public int checkObj(Player player, Library library){

        //contatore delle caselle obiettivo completate
        int count = 0;
    //    int temp = i;

        //per ogni casella obiettivo controllo che nella stessa posizione della library ci sia la stessa carta e aggiorno il contatore
        for (SingleObj singleObj : playerObjs) {
            if (library.getPos(singleObj.getXPosition(), singleObj.getYPosition()).isEqualTo(singleObj.getType())) count++;

        }
    /*    if(count == 6){     // if count == 6, does not verify the object during the next rounds
            player.setPlyObjCompleted();
        }
        if ((count > i) && (i>0) ){

            this.i = count-1;
            return POINT[count-1] - POINT[temp];

        } else if ((count > i) && (i==0)){

            this.i = count-1;
            return POINT[count-1];
        } else return 0;    */

        if(count > 0){
            return POINT[count-1];
        }
        return 0;
    }

    /**
     * Constructor for the PlayerObj class: choose randomly one of the personal goal of the game
     */
    public PlayerObj() throws Exception{
        Random rn = new Random();
        int rand = rn.nextInt(available.size());

        switch (available.get(rand)){
            case 1 -> this.playerObjs = obj1();
            case 2 -> this.playerObjs = obj2();
            case 3 -> this.playerObjs = obj3();
            case 4 -> this.playerObjs = obj4();
            case 5 -> this.playerObjs = obj5();
            case 6 -> this.playerObjs = obj6();
            case 7 -> this.playerObjs = obj7();
            case 8 -> this.playerObjs = obj8();
            case 9 -> this.playerObjs = obj9();
            case 10 -> this.playerObjs = obj10();
            case 11 -> this.playerObjs = obj11();
            case 12 -> this.playerObjs = obj12();
            default -> throw new Exception("error");
        }
        num=available.get(rand);
        if (available.size() <= 8) resetAvailable();
        available.remove(rand);
    }

    private ArrayList <SingleObj>  obj1 (){
        ArrayList<SingleObj> temp=new ArrayList<>();
        temp.add(new SingleObj(1,1,Card.PURPLE1));
        temp.add(new SingleObj(2,0,Card.GREEN1));
        temp.add(new SingleObj(2,2,Card.YELLOW1));
        temp.add(new SingleObj(3,4,Card.WHITE1));
        temp.add(new SingleObj(4,3,Card.LIGHTBLUE1));
        temp.add(new SingleObj(5,4,Card.BLUE1));
        return temp;
    }

    private ArrayList <SingleObj> obj2 (){
        ArrayList<SingleObj> temp = new ArrayList<>();
        temp.add(new SingleObj(0,0,Card.PURPLE1));
        temp.add(new SingleObj(0,2,Card.BLUE1));
        temp.add(new SingleObj(1,4,Card.GREEN1));
        temp.add(new SingleObj(2,3,Card.WHITE1));
        temp.add(new SingleObj(3,1,Card.YELLOW1));
        temp.add(new SingleObj(5,2,Card.LIGHTBLUE1));
        return temp;
    }

    private ArrayList <SingleObj> obj3 (){
        ArrayList<SingleObj> temp = new ArrayList<>();
        temp.add(new SingleObj(1,0,Card.BLUE1));
        temp.add(new SingleObj(1,3,Card.YELLOW1));
        temp.add(new SingleObj(2,2,Card.PURPLE1));
        temp.add(new SingleObj(3,1,Card.GREEN1));
        temp.add(new SingleObj(3,4,Card.LIGHTBLUE1));
        temp.add(new SingleObj(5,0,Card.WHITE1));
        return temp;
    }

    private ArrayList <SingleObj> obj4 (){
        ArrayList<SingleObj> temp = new ArrayList<>();
        temp.add(new SingleObj(0,4,Card.YELLOW1));
        temp.add(new SingleObj(2,0,Card.LIGHTBLUE1));
        temp.add(new SingleObj(2,2,Card.BLUE1));
        temp.add(new SingleObj(3,3,Card.PURPLE1));
        temp.add(new SingleObj(4,1,Card.WHITE1));
        temp.add(new SingleObj(4,2,Card.GREEN1));
        return temp;
    }

    private ArrayList <SingleObj> obj5 (){
        ArrayList<SingleObj> temp = new ArrayList<>();
        temp.add(new SingleObj(1,1,Card.LIGHTBLUE1));
        temp.add(new SingleObj(3,1,Card.BLUE1));
        temp.add(new SingleObj(3,2,Card.WHITE1));
        temp.add(new SingleObj(4,4,Card.PURPLE1));
        temp.add(new SingleObj(5,0,Card.YELLOW1));
        temp.add(new SingleObj(5,3,Card.GREEN1));
        return temp;
    }

    private ArrayList <SingleObj> obj6 (){
        ArrayList<SingleObj> temp = new ArrayList<>();
        temp.add(new SingleObj(0,2,Card.LIGHTBLUE1));
        temp.add(new SingleObj(0,4,Card.GREEN1));
        temp.add(new SingleObj(2,3,Card.WHITE1));
        temp.add(new SingleObj(4,1,Card.YELLOW1));
        temp.add(new SingleObj(4,3,Card.BLUE1));
        temp.add(new SingleObj(5,0,Card.PURPLE1));
        return temp;
    }

    private ArrayList <SingleObj> obj7 (){
        ArrayList<SingleObj> temp = new ArrayList<>();
        temp.add(new SingleObj(0,0,Card.GREEN1));
        temp.add(new SingleObj(1,3,Card.BLUE1));
        temp.add(new SingleObj(2,1,Card.PURPLE1));
        temp.add(new SingleObj(3,0,Card.LIGHTBLUE1));
        temp.add(new SingleObj(4,4,Card.YELLOW1));
        temp.add(new SingleObj(5,2,Card.WHITE1));
        return temp;
    }

    private ArrayList <SingleObj> obj8 (){
        ArrayList<SingleObj> temp = new ArrayList<>();
        temp.add(new SingleObj(0,4,Card.BLUE1));
        temp.add(new SingleObj(1,1,Card.GREEN1));
        temp.add(new SingleObj(2,2,Card.LIGHTBLUE1));
        temp.add(new SingleObj(3,0,Card.PURPLE1));
        temp.add(new SingleObj(4,3,Card.WHITE1));
        temp.add(new SingleObj(5,3,Card.YELLOW1));
        return temp;
    }

    private ArrayList <SingleObj> obj9 (){
        ArrayList<SingleObj> temp = new ArrayList<>();
        temp.add(new SingleObj(0,2,Card.YELLOW1));
        temp.add(new SingleObj(2,2,Card.GREEN1));
        temp.add(new SingleObj(3,4,Card.WHITE1));
        temp.add(new SingleObj(4,1,Card.LIGHTBLUE1));
        temp.add(new SingleObj(4,4,Card.PURPLE1));
        temp.add(new SingleObj(5,0,Card.BLUE1));
        return temp;
    }

    private ArrayList <SingleObj> obj10 (){
        ArrayList<SingleObj> temp = new ArrayList<>();
        temp.add(new SingleObj(0,4,Card.LIGHTBLUE1));
        temp.add(new SingleObj(1,1,Card.YELLOW1));
        temp.add(new SingleObj(2,0,Card.WHITE1));
        temp.add(new SingleObj(3,3,Card.GREEN1));
        temp.add(new SingleObj(4,1,Card.BLUE1));
        temp.add(new SingleObj(5,3,Card.PURPLE1));
        return temp;
    }

    private ArrayList <SingleObj> obj11 (){
        ArrayList<SingleObj> temp = new ArrayList<>();
        temp.add(new SingleObj(0,2,Card.PURPLE1));
        temp.add(new SingleObj(1,1,Card.WHITE1));
        temp.add(new SingleObj(2,0,Card.YELLOW1));
        temp.add(new SingleObj(3,2,Card.BLUE1));
        temp.add(new SingleObj(4,4,Card.GREEN1));
        temp.add(new SingleObj(5,3,Card.LIGHTBLUE1));
        return temp;
    }

    private ArrayList <SingleObj> obj12 (){
        ArrayList<SingleObj> temp = new ArrayList<>();
        temp.add(new SingleObj(0,2,Card.WHITE1));
        temp.add(new SingleObj(1,1,Card.PURPLE1));
        temp.add(new SingleObj(2,2,Card.BLUE1));
        temp.add(new SingleObj(3,3,Card.LIGHTBLUE1));
        temp.add(new SingleObj(4,4,Card.YELLOW1));
        temp.add(new SingleObj(5,0,Card.GREEN1));
        return temp;
    }

    //@test
    public int[] getPOINT() {
        return POINT;
    }

    //@test
    public static ArrayList<Integer> getAvailable() {
        return available;
    }

    //@test
    public ArrayList<SingleObj> getPlayerObjs() {
        return playerObjs;
    }

    //@test
    public PlayerObj(int numObj) throws Exception {
        num = numObj;
        switch (numObj){
            case 1 -> this.playerObjs = obj1();
            case 2 -> this.playerObjs = obj2();
            case 3 -> this.playerObjs = obj3();
            case 4 -> this.playerObjs = obj4();
            case 5 -> this.playerObjs = obj5();
            case 6 -> this.playerObjs = obj6();
            case 7 -> this.playerObjs = obj7();
            case 8 -> this.playerObjs = obj8();
            case 9 -> this.playerObjs = obj9();
            case 10 -> this.playerObjs = obj10();
            case 11 -> this.playerObjs = obj11();
            case 12 -> this.playerObjs = obj12();
            default -> throw new Exception("error");
        }
    }

    //@test
    public int getNum(){
        return num;
    }

    public void resetAvailable(){
        available = new ArrayList<>(Arrays.asList(1,2,3,4,5,6,7,8,9,10,11,12));
    }
}

