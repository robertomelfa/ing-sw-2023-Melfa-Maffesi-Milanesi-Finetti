package it.polimi.ingsw.Model;


import static it.polimi.ingsw.Model.Card.*;
import java.io.Serializable;

public class GameTable implements Serializable{
    private Card[][] board = new Card[11][11];
    private CardBox cardbox = new CardBox();

    /**
     *
     * @param playerNumber: players in the game
     * Constructor for GameTable: set the board for 2, 3 or 4 players
     */
    public GameTable(int playerNumber) {
        /** all the game table is NOT (impossible to insert a card)
         *
         */

        for(int i = 0; i < 11; i++){
            for(int j = 0; j < 11; j++){
                board[i][j] = NOT;
            }
        }

        // set the common grid of the table (now i have the 2 players' grid)

        board[2][4] = NONE;
        board[2][5] = NONE;
        board[3][3] = NONE;
        board[3][4] = NONE;
        board[3][5] = NONE;
        board[3][6] = NONE;
        board[4][2] = NONE;
        board[4][3] = NONE;
        board[4][4] = NONE;
        board[4][5] = NONE;
        board[4][6] = NONE;
        board[4][7] = NONE;
        board[4][8] = NONE;
        board[5][1] = NONE;
        board[5][2] = NONE;
        board[5][3] = NONE;
        board[5][4] = NONE;
        board[5][5] = NONE;
        board[5][6] = NONE;
        board[5][7] = NONE;
        board[5][8] = NONE;
        board[6][2] = NONE;
        board[6][3] = NONE;
        board[6][4] = NONE;
        board[6][5] = NONE;
        board[6][6] = NONE;
        board[6][7] = NONE;
        board[7][4] = NONE;
        board[7][5] = NONE;
        board[7][6] = NONE;
        board[8][4] = NONE;
        board[8][5] = NONE;
        board[8][6] = NONE;

        if(playerNumber != 2) {
            // these cases are good for 3 or 4 players
            board[1][4] = NONE;
            board[3][7] = NONE;
            board[4][9] = NONE;
            board[6][1] = NONE;
            board[7][3] = NONE;
            board[7][7] = NONE;
            board[9][6] = NONE;
            // 4 players
            if (playerNumber == 4) {
                board[1][5] = NONE;
                board[2][6] = NONE;
                board[5][9] = NONE;
                board[6][8] = NONE;
                board[9][5] = NONE;
            }
        }

        refill();
    }

    /**
     * This method inserts random cards into the board (refill the board)
     */
    private void refill() {
        for(int i = 1; i < 10; i++){
            for(int j = 1; j < 10; j++){
                if(board[i][j] == NONE){
                    board[i][j] = cardbox.getCard();   // get random card from cardbox
                }
            }
        }
    }

    /**
     *
     * @return true if it is necessary to refill the board
     * In this method i and j start from 1 to 10 because it is not necessary to verify the frame
     */
    public void checkStatus() {
        boolean check = false;
        for(int i = 1; i < 10; i++){
            for(int j = 1; j < 10; j++){
                if((board[i][j] != NONE && board[i][j] != NOT) && ((board[i][j+1] != NONE && board[i][j+1] != NOT) || (board[i+1][j] != NONE && board[i+1][j] != NOT) || (board[i-1][j] != NONE && board[i-1][j] != NOT) || (board[i][j-1] != NONE && board[i][j-1] != NOT))){
                    check = true;    // refill not necessary
                }
            }
        }
        if(check == false){
            refill(); // refill is necessary
        }
    }

    /**
     * This method prints the board. If there is no card (NOT or NONE) it prints space
     * This method will be in the view
     */
    public void viewTable() {
        System.out.print("   ");
        for(int i = 1; i < 10; i++){
            System.out.printf("     %d     ", i);
        }

        System.out.print("\n");
        System.out.print("\n");

        for(int i = 1; i < 10; i++){
            System.out.printf(" %d ", i);
            for(int j = 1; j < 11; j++){
                if(this.board[i][j] != NOT && this.board[i][j] != NONE){ // remove this if to view NONE and NOT on the gameTable
                    System.out.printf("%-22s", board[i][j]);
                }else{
                    System.out.print("           ");     // if there is no card, read space

                }
            }
            System.out.print("\n");
        }
        System.out.print("\n");
    }

    public void updateTable(GameTable gameTable){
        for(int i = 0; i < 10; i++){
            for(int j = 0; j < 10; j++){
                board[i][j] = gameTable.getCardfromBoard(i ,j);
            }
        }
    }

    public void setCardfromBoard(int x, int y, Card card){
        board[x][y]=card;
    }


    public Card getCardfromBoard(int x,int y) {
        return board[x][y];
    }
}
