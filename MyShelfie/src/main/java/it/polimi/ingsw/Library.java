package it.polimi.ingsw;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static it.polimi.ingsw.Card.*;

public class Library {
    private Card[][] grid = new Card[6][5];
    private boolean full;

    /**
     * Constructor for the Library class.
     * It sets all elements in grid to NONE
     */
    public Library(){
        for (int i=0;i<6;i++){
            for (int j=0;j<5;j++){
                grid[i][j] = NONE;
            }
        }
        full=false;
    }

    /**
     *
     * @param x
     * @param y
     * @return the Card in position [x][y] in grid
     */
    public Card getPos(int x, int y)throws Exception{
        if(x>6 || x<0 || y>5 || y<0){
            throw new Exception();
        }else {
            return grid[x][y];
        }
    }

    /**
     * This method prints the player's library
     */
    public void viewGrid(){
        System.out.print("   ");
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

        int card = 0;
        int listSize = list.size();
        for(int i = 0; i < listSize; i++){
            System.out.println("Which card do you want to insert?" + list.toString());
            if(in.hasNextInt()) {
                card = in.nextInt() - 1;
                grid[lastRowFree(column)][column] = list.get(card);
                list.remove(card);
            } else {
                String type = in.next().toUpperCase();
                if(list.contains(Card.valueOf(type))){
                  list.remove(Card.valueOf(type));
                  grid[lastRowFree(column)][column] = Card.valueOf(type);
                } else {
                    System.out.print("The input is not valid\n");
                    i--;
                }

            }
        }
        System.out.println("Now the grid is: \n");
        viewGrid();
    }
}


