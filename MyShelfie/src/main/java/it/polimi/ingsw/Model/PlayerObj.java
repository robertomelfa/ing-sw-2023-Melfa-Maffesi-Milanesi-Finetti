package it.polimi.ingsw.Model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import static it.polimi.ingsw.Model.Card.NONE;
import static it.polimi.ingsw.Model.Card.NOT;


public class PlayerObj {
    private final ArrayList<SingleObj> playerObjs ;
    private static ArrayList <Integer> available = new ArrayList<>(Arrays.asList(1,2,3,4,5,6,7,8,9,10,11,12));
    private final int[] POINT = {1, 2, 4, 6, 9, 12};
  //  private int i;

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
            if (library.getPos(singleObj.getXPosition(), singleObj.getYPosition()) == singleObj.getType()) count++;

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
//        for(int i=0; i < 6; i++){
//            assert false;                               //lo ha messo java per completezza
//            playerObjs.add(new SingleObj(i));
//        }

        Random rn = new Random();
        int rand = rn.nextInt(available.size());
    //    this.i = 0;
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
        if (available.size() <= 8) resetAvailable();
        available.remove(rand);
    }

    private ArrayList <SingleObj>  obj1 (){
        ArrayList<SingleObj> temp=new ArrayList<>();
        temp.add(new SingleObj(1,1,Card.PURPLE));
        temp.add(new SingleObj(2,0,Card.GREEN));
        temp.add(new SingleObj(2,2,Card.YELLOW));
        temp.add(new SingleObj(3,4,Card.WHITE));
        temp.add(new SingleObj(4,3,Card.LIGHTBLUE));
        temp.add(new SingleObj(5,4,Card.BLUE));
        return temp;
    }

    private ArrayList <SingleObj> obj2 (){
        ArrayList<SingleObj> temp = new ArrayList<>();
        temp.add(new SingleObj(0,0,Card.PURPLE));
        temp.add(new SingleObj(0,2,Card.BLUE));
        temp.add(new SingleObj(1,4,Card.GREEN));
        temp.add(new SingleObj(2,3,Card.WHITE));
        temp.add(new SingleObj(3,1,Card.YELLOW));
        temp.add(new SingleObj(5,2,Card.LIGHTBLUE));
        return temp;
    }

    private ArrayList <SingleObj> obj3 (){
        ArrayList<SingleObj> temp = new ArrayList<>();
        temp.add(new SingleObj(1,0,Card.BLUE));
        temp.add(new SingleObj(1,3,Card.YELLOW));
        temp.add(new SingleObj(2,2,Card.PURPLE));
        temp.add(new SingleObj(3,1,Card.GREEN));
        temp.add(new SingleObj(3,4,Card.LIGHTBLUE));
        temp.add(new SingleObj(5,0,Card.WHITE));
        return temp;
    }

    private ArrayList <SingleObj> obj4 (){
        ArrayList<SingleObj> temp = new ArrayList<>();
        temp.add(new SingleObj(0,4,Card.YELLOW));
        temp.add(new SingleObj(2,0,Card.LIGHTBLUE));
        temp.add(new SingleObj(2,2,Card.BLUE));
        temp.add(new SingleObj(3,3,Card.PURPLE));
        temp.add(new SingleObj(4,1,Card.WHITE));
        temp.add(new SingleObj(4,2,Card.GREEN));
        return temp;
    }

    private ArrayList <SingleObj> obj5 (){
        ArrayList<SingleObj> temp = new ArrayList<>();
        temp.add(new SingleObj(1,1,Card.LIGHTBLUE));
        temp.add(new SingleObj(3,1,Card.BLUE));
        temp.add(new SingleObj(3,2,Card.WHITE));
        temp.add(new SingleObj(4,4,Card.PURPLE));
        temp.add(new SingleObj(5,0,Card.YELLOW));
        temp.add(new SingleObj(5,3,Card.GREEN));
        return temp;
    }

    private ArrayList <SingleObj> obj6 (){
        ArrayList<SingleObj> temp = new ArrayList<>();
        temp.add(new SingleObj(0,2,Card.LIGHTBLUE));
        temp.add(new SingleObj(0,4,Card.GREEN));
        temp.add(new SingleObj(2,3,Card.WHITE));
        temp.add(new SingleObj(4,1,Card.YELLOW));
        temp.add(new SingleObj(4,3,Card.BLUE));
        temp.add(new SingleObj(5,0,Card.PURPLE));
        return temp;
    }

    private ArrayList <SingleObj> obj7 (){
        ArrayList<SingleObj> temp = new ArrayList<>();
        temp.add(new SingleObj(0,0,Card.GREEN));
        temp.add(new SingleObj(1,3,Card.BLUE));
        temp.add(new SingleObj(2,1,Card.PURPLE));
        temp.add(new SingleObj(3,0,Card.LIGHTBLUE));
        temp.add(new SingleObj(4,4,Card.YELLOW));
        temp.add(new SingleObj(5,2,Card.WHITE));
        return temp;
    }

    private ArrayList <SingleObj> obj8 (){
        ArrayList<SingleObj> temp = new ArrayList<>();
        temp.add(new SingleObj(0,4,Card.BLUE));
        temp.add(new SingleObj(1,1,Card.GREEN));
        temp.add(new SingleObj(2,2,Card.LIGHTBLUE));
        temp.add(new SingleObj(3,0,Card.PURPLE));
        temp.add(new SingleObj(4,3,Card.WHITE));
        temp.add(new SingleObj(5,3,Card.YELLOW));
        return temp;
    }

    private ArrayList <SingleObj> obj9 (){
        ArrayList<SingleObj> temp = new ArrayList<>();
        temp.add(new SingleObj(0,2,Card.YELLOW));
        temp.add(new SingleObj(2,2,Card.GREEN));
        temp.add(new SingleObj(3,4,Card.WHITE));
        temp.add(new SingleObj(4,1,Card.LIGHTBLUE));
        temp.add(new SingleObj(4,4,Card.PURPLE));
        temp.add(new SingleObj(5,0,Card.BLUE));
        return temp;
    }

    private ArrayList <SingleObj> obj10 (){
        ArrayList<SingleObj> temp = new ArrayList<>();
        temp.add(new SingleObj(0,4,Card.LIGHTBLUE));
        temp.add(new SingleObj(1,1,Card.YELLOW));
        temp.add(new SingleObj(2,0,Card.WHITE));
        temp.add(new SingleObj(3,3,Card.GREEN));
        temp.add(new SingleObj(4,1,Card.BLUE));
        temp.add(new SingleObj(5,3,Card.PURPLE));
        return temp;
    }

    private ArrayList <SingleObj> obj11 (){
        ArrayList<SingleObj> temp = new ArrayList<>();
        temp.add(new SingleObj(0,2,Card.PURPLE));
        temp.add(new SingleObj(1,1,Card.WHITE));
        temp.add(new SingleObj(2,0,Card.YELLOW));
        temp.add(new SingleObj(3,2,Card.BLUE));
        temp.add(new SingleObj(4,4,Card.GREEN));
        temp.add(new SingleObj(5,3,Card.LIGHTBLUE));
        return temp;
    }

    private ArrayList <SingleObj> obj12 (){
        ArrayList<SingleObj> temp = new ArrayList<>();
        temp.add(new SingleObj(0,2,Card.WHITE));
        temp.add(new SingleObj(1,1,Card.PURPLE));
        temp.add(new SingleObj(2,2,Card.BLUE));
        temp.add(new SingleObj(3,3,Card.LIGHTBLUE));
        temp.add(new SingleObj(4,4,Card.YELLOW));
        temp.add(new SingleObj(5,0,Card.GREEN));
        return temp;
    }


    /**
     * Method that prints playerObj
     */
    public void print(){
        Card[][] temp= new Card[6][5];
        for (int k=0;k<6;k++){
            for (int j=0;j<5;j++){
                temp[k][j]=Card.NONE;
            }
        }
        for (int i=0; i<playerObjs.size();i++){
            temp[playerObjs.get(i).getXPosition()][playerObjs.get(i).getYPosition()]=playerObjs.get(i).getType();
        }
        for(int i = 1; i < 6; i++){
            System.out.printf("     %d     ", i);
        }

        System.out.print("\n");
        System.out.print("\n");

        for(int i = 0; i < 6; i++){
            System.out.printf(" %d ", i+1);
            for(int j = 0; j < 5; j++){
                if(temp[i][j] != NOT && temp[i][j] != NONE){
                    System.out.printf("%-11s", temp[i][j]);
                }else{
                    System.out.print("           ");
                }
            }
            System.out.print("\n");
        }
        System.out.print("\n");
    }

  /*  public int getI() {
        return 0;
    }   */

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

    public void resetAvailable(){
        available = new ArrayList<>(Arrays.asList(1,2,3,4,5,6,7,8,9,10,11,12));
    }
}
