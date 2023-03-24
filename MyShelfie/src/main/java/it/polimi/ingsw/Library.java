package it.polimi.ingsw;

import java.util.ArrayList;
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
        for (int i=0;i<5;i++){
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
    public Card getPos(int x, int y){
        return grid[x][y];
    }

    /**
     * This method prints the player's library
     */
    public void viewGrid(){
        for (int i=0;i<6;i++){
            for (int j=0;j<5;j++){
                System.out.print(getPos(i,j).toString()+"   ");
            }
            System.out.print("\n");
        }
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
        for(int i = 0; i < 6; i++){
            if(grid[i][column] == NONE){
                freeSpaces = freeSpaces + 1;
            }
        }
        if(freeSpaces >= size){
            return true;
        }else{
            return false;
        }
    }

    /**
     *
     * @param column: verify last free row of this column
     * @return the last free row of the column
     */
    private int lastRowFree(int column){
        for(int i = 5; i > 0; i--){
            if(grid[i][column] == NONE){
                return i;
            }
        }
        return -1;  // there is no row free
    }

    /**
     *
     * @param List: list of the cards picked from the game table
     * This method insert into the player's library the cards picked from the table
     */
    public void insert(ArrayList<Card> List){
        Scanner in = new Scanner(System.in);
        int column = 0;
        viewGrid();
        System.out.println("Choose the column:");
        do{
            column = in.nextInt();
            if(!checkFreeSpaces(column, List.size())){
                System.out.println("There is not enough space. Try again");
            }
        }while(!checkFreeSpaces(column, List.size()));

        int card = 0;
        for(int i = 0; i < List.size(); i++){
            System.out.println("Which card do you want to insert?" + List.toString());
            card = in.nextInt() - 1;
            grid[lastRowFree(column)][column] = List.get(card);
        }
        System.out.println("Now the grid is: ");
        viewGrid();
    }
}
