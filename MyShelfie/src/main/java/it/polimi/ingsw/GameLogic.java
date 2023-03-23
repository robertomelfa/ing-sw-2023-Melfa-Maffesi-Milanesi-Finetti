package it.polimi.ingsw;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static it.polimi.ingsw.Card.*;

public class GameLogic {
    private GameTable gameTable;

    private boolean endGame;

    public GameLogic(Game game){
        gameTable = game.getGameTable();
        endGame = false;
    }

    public List<Card> getCardFromTable(){
        int size = 0;
        Scanner in = new Scanner(System.in);
        ArrayList<Card> list = new ArrayList<Card>();
        do{
            System.out.println("How many cards?");         // number of card to pick (1 - 3)
            size = in.nextInt();

            if(size < 1 || size >3){
                System.out.println("You can pick 1, 2 or 3 cards. Try again!");
            }
        }while(size < 1 || size >3);
        if(size == 1){  // case 1 card
            int x1, y1;
            // ask coordinates until a correct input
            do{
                System.out.println("Coordinate x Card 1");
                x1 = in.nextInt();
                System.out.println("Coordinate y Card 1");
                y1 = in.nextInt();
                if(!checkNear(x1, y1)){
                    System.out.println("Impossible to draw the card");
                }
            }while(!checkNear(x1, y1));
            if(checkNear(x1, y1)){
                list.add(gameTable.board[x1][y1]);
                System.out.println("Card " + gameTable.board[x1][y1] + " drawn!");
                gameTable.board[x1][y1] = NONE;
            }
            return list;
        }else if(size == 2){        // case 2 cards
            int x1, y1, x2, y2;
            // requires coordinates
            do{
                System.out.println("Coordinate x Card 1");
                x1 = in.nextInt();
                System.out.println("Coordinate y Card 1");
                y1 = in.nextInt();
                System.out.println("Coordinate x Card 2");
                x2 = in.nextInt();
                System.out.println("Coordinate y Card 2");
                y2 = in.nextInt();

                if(!checkNear(x1, y1, x2, y2)){
                    System.out.println("Invalid coordinates, try again!");
                }
            }while(!checkNear(x1, y1, x2, y2));
            if(checkNear(x1, y1, x2, y2)){
                list.add(gameTable.board[x1][y1]);
                list.add(gameTable.board[x2][y2]);
                System.out.print("Card " + gameTable.board[x1][y1] + " " + gameTable.board[x2][y2] + " drawn");
                gameTable.board[x1][y1] = NONE;
                gameTable.board[x2][y2] = NONE;
            }
        }else{  // case 3 cards
            int x1, y1, x2, y2, x3, y3;
            // requires coordinates
            do{
                System.out.println("Coordinate x Card 1");
                x1 = in.nextInt();
                System.out.println("Coordinate y Card 1");
                y1 = in.nextInt();
                System.out.println("Coordinate x Card 2");
                x2 = in.nextInt();
                System.out.println("Coordinate y Card 2");
                y2 = in.nextInt();
                System.out.println("Coordinate x Card 3");
                x3 = in.nextInt();
                System.out.println("Coordinate y Card 3");
                y3 = in.nextInt();

                if(!checkNear(x1, y1, x2, y2, x3, y3)){
                    System.out.println("Invalid coordinates, try again!");
                }
            }while(!checkNear(x1, y1, x2, y2, x3, y3));

            if(checkNear(x1, y1, x2, y2, x3, y3)){
                // add cards on the list
                list.add(gameTable.board[x1][y1]);
                list.add(gameTable.board[x2][y2]);
                list.add(gameTable.board[x3][y3]);

                System.out.print("Cards " + gameTable.board[x1][y1] + " " + gameTable.board[x2][y2] + " " + gameTable.board[x3][y3] + " drawn");
                // set NONE on the table
                gameTable.board[x1][y1] = NONE;
                gameTable.board[x2][y2] = NONE;
                gameTable.board[x3][y3] = NONE;
            }
        }
        return list;
    }

    // verify if card has free side
    private boolean checkNear(int x1, int y1){
        if(gameTable.board[x1][y1] == NONE || gameTable.board[x1][y1] == NOT){
            System.out.println("Card not exists");
            return false;
        }else if((gameTable.board[x1+1][y1] == NONE || gameTable.board[x1+1][y1] == NOT) || (gameTable.board[x1-1][y1] == NONE || gameTable.board[x1-1][y1] == NOT) || (gameTable.board[x1][y1+1] == NONE || gameTable.board[x1+1][y1+1] == NOT) || (gameTable.board[x1][y1-1] == NONE || gameTable.board[x1][y1-1] == NOT)){
            System.out.println("Card has not free side");
            return true;
        }
        return false;
    }

    private boolean checkNear(int x1, int y1, int x2, int y2){
        if(checkNear(x1, y1) && checkNear(x2, y2)){
            if((x1 == x2+1 && y1 == y2) || (x1 == x2-1 && y1 == y2) || (x1 == x2 && y1 == y2+1) || (x1 == x2 && y1 == y2-1)){
                System.out.println("Card 2 has different x and y beside Card 1 (x or y must be the same)");
                return true;
            }
        }
        return false;
    }

    private boolean checkNear(int x1, int y1, int x2, int y2, int x3, int y3){
        if(checkNear(x1, y1) && checkNear(x2, y2) && checkNear(x3, y3)){
            if(((checkNear(x1, y1, x2, y2) && checkNear(x2, y2, x3, y3)) || ((checkNear(x1, y1, x3, y3) && checkNear(x2, y2, x3, y3)))) && ((x1 == x2 && x2 == x3) || (y1 == y2 && y2 == y3))){
                System.out.println("Cards must be in the same row or column");
                return true;
            }
        }
        return false;
    }

    public void changeCurrentPlayer(){}

    public void gameIsEnded(){}

    public void setEnd(){}

    public void checkCommObj(Player player){}

}
