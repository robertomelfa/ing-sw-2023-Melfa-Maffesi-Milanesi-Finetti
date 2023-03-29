package it.polimi.ingsw;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static it.polimi.ingsw.Card.*;

public class Library {
    private Card[][] grid = new Card[6][5];

    private int i;

    /**
     * Constructor for the Library class.
     * It sets all elements in grid to NONE
     */
    public Library(){
        i = 0;
        for (int i=0;i<6;i++){
            for (int j=0;j<5;j++){
                grid[i][j] = NONE;
            }
        }
    }

    /**
     *
     * @param x
     * @param y
     * @return the Card in position [x][y] in grid
     */
    public Card getPos(int x, int y) throws ArrayIndexOutOfBoundsException{
        try{
            return grid[x][y];
        }catch(ArrayIndexOutOfBoundsException exception){
            return NOT;
        }
    }

    /**
     * This method prints the player's library
     */
    public void viewGrid(){
        System.out.print("\n   ");
        for(int i = 1; i < 6; i++){
            System.out.printf("     %d     ", i);
        }

        System.out.print("\n");
        System.out.print("\n");

        for(int i = 0; i < 6; i++){
            System.out.printf(" %d ", i+1);
            for(int j = 0; j < 5; j++){
                if(this.grid[i][j] != NOT && this.grid[i][j] != NONE){
                    System.out.printf("%-11s", grid[i][j]);
                }else{
                    System.out.print("           ");
                }
            }
            System.out.print("\n");
        }
        System.out.print("\n");

    }

    /**
     *
     * @return true if the player's library is full (end of the game)
     */
    public boolean checkFull(){
        for(int i = 0; i < 6; i ++){
            for(int j = 0; j < 5; j++){
                if(grid[i][j] == NONE){
                    return false;
                }
            }
        }
        return true;
    }

    /**
     *
     * @param column: column to check
     * @param size: number of cards to insert
     * @return true if the column has enough space to insert cards
     */
    private boolean checkFreeSpaces(int column, int size){
        int freeSpaces = 0;
        if(column < 0 || column > 4){
            System.out.println("This column does not exist, try again");
            return false;
        }
        for(int i = 0; i < 6; i++){
            if(grid[i][column] == NONE){
                freeSpaces = freeSpaces + 1;
            }
        }
        if(freeSpaces >= size){
            return true;
        }else{
            System.out.println("In this column there is not enough space, try again");
            return false;
        }
    }

    /**
     *
     * @param column: verify last free row of this column
     * @return the last free row of the column
     */
    private int lastRowFree(int column){
        for(int i = 5; i >= 0; i--){
            if(grid[i][column] == NONE){
                return i;
            }
        }
        return -1;  // there is no row free
    }

    /**
     *
     * @param list: list of the cards picked from the game table
     * This method insert into the player's library the cards picked from the table
     */
    public void insert(ArrayList<Card> list){
        Scanner in = new Scanner(System.in);
        int column = 0;
        viewGrid();
        System.out.println("Choose the column:");
        do{
            column = in.nextInt()-1;
        }while(!checkFreeSpaces(column, list.size()));

        int flag = 0;
        String type = new String();
        int card = 0;
        int listSize = list.size();
        for(int i = 0; i < listSize; i++){
            System.out.println("Which card do you want to insert?" + list.toString());
            do{
                flag = 0;
                if(in.hasNextInt()){
                    card = in.nextInt() - 1;
                    if (card >= 0 && card < list.size()) {
                        flag = 1;
                    }else {
                        System.out.println("This card does not exist, try again!");
                    }
                }else{
                    type = in.next().toUpperCase();
                    if(type.equals("YELLOW") || type.equals("PURPLE") || type.equals("WHITE") || type.equals("BLUE") || type.equals("LIGHTBLUE") || type.equals("GREEN")) {
                        if(list.contains(Card.valueOf(type))) {
                            flag = 2;
                        }else{
                            System.out.println("This card does not exist, try again!");
                        }
                    }else {
                        System.out.println("This card does not exist, try again!");
                    }

                }
            }while(flag == 0);
            if(flag == 1){
                grid[lastRowFree(column)][column] = list.get(card);
                list.remove(card);
            }else if(flag == 2){
                list.remove(Card.valueOf(type));
                grid[lastRowFree(column)][column] = Card.valueOf(type);
            }else{
                System.out.println("This card does not exist, try again!");
                i--;
            }
        }
        System.out.println("Now the grid is: \n");
        viewGrid();
    }

    /**
     *
     * @param x: x position of the Card
     * @param y: y position of the Card
     * @param color: color of the group of cards
     * This recursive method search the groups of same cards
     */
    private void group(int x, int y, Card color){
        if(getPos(x, y) != NONE && getPos(x, y) != NOT && getPos(x, y) == color){
            if(getPos(x, y) == getPos(x+1, y)){
                group(x+1, y, color);
            }
            if(getPos(x, y) == getPos(x, y+1)){
                group(x, y+1, color);
            }
            if(getPos(x, y) == color){
                grid[x][y] = NONE;
                this.i ++;
            }
        }
    }

    /**
     *
     * @return total points get from the groups of same cards
     */
    public int checkFinal(){
        int points = 0;
        for(int i = 0; i < 6; i++){
            for(int j = 0; j < 5; j++){
                if(getPos(i, j) != NONE){
                    this.i = 0;
                    group(i, j, getPos(i, j));
                    if(this.i >= 3){
                        if(this.i >= 6){    // if i have more than 6 cards, 8 points
                            points = points + 8;
                        }else{
                            points = points + this.i;
                        }
                    }
                }
            }
        }
        return points;
    }
}


