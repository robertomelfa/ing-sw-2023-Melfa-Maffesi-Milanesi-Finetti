package it.polimi.ingsw.Model;


import java.util.ArrayList;
import java.util.Scanner;

import static it.polimi.ingsw.Model.Card.*;
import java.io.Serializable;

public class GameLogic implements Serializable{
    private GameTable gameTable;


    private Game game1;


    /**
     *
     * @param game: the current game
     * The method is the constructor for GameLogic class
     * It takes the gameTable of the game
     */
    public GameLogic(Game game){
        this.game1 = game;
        this.gameTable = game.getGameTable();
    }

    public Game getGame(){
        return this.game1;
    }

    public void setGame(Game game){
        this.game1 = game;
    }

    public void setGameTable(GameTable gameTable){
        this.gameTable = gameTable;
    }

    public GameTable getGameTable(){
        return this.gameTable;
    }

    /**
     *
     * @return the List of cards picked by player
     * The method is used to draw from one to three cards from the board
     * the method continues to request cards from the player until they are actually drawable
     */
    public ArrayList<Card> getCardFromTable(){
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
                list.add(gameTable.getCardfromBoard(x1,y1));
                System.out.println("Card " + gameTable.getCardfromBoard(x1,y1) + " drawn!");
                gameTable.setCardfromBoard(x1,y1, NONE);
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
                list.add(gameTable.getCardfromBoard(x1,y1));
                list.add(gameTable.getCardfromBoard(x2,y2));
                System.out.println("Card " + gameTable.getCardfromBoard(x1,y1) + " " + gameTable.getCardfromBoard(x2,y2) + " drawn");
                gameTable.setCardfromBoard(x1,y1,NONE);
                gameTable.setCardfromBoard(x2,y2,NONE);
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
                list.add(gameTable.getCardfromBoard(x1,y1));
                list.add(gameTable.getCardfromBoard(x2,y2));
                list.add(gameTable.getCardfromBoard(x3,y3));

                System.out.println("Cards " + gameTable.getCardfromBoard(x1,y1) + " " + gameTable.getCardfromBoard(x2,y2) + " " + gameTable.getCardfromBoard(x3,y3) + " drawn");
                // set NONE on the table
                gameTable.setCardfromBoard(x1,y1,NONE);
                gameTable.setCardfromBoard(x2,y2,NONE);
                gameTable.setCardfromBoard(x3,y3,NONE);

            }
        }
        return list;
    }

    /**
     *
     * @param x1: x coordinate of Card 1
     * @param y1: y coordinate of Card 2
     * @return true if card has at least one free side
     */
    // verify if card has free side
    public boolean checkNear(int x1, int y1){
        if(gameTable.getCardfromBoard(x1,y1) == NONE || gameTable.getCardfromBoard(x1,y1) == NOT){
            System.out.println("Card not exists");
            return false;
        }else if((gameTable.getCardfromBoard(x1+1,y1) == NONE || gameTable.getCardfromBoard(x1+1,y1) == NOT) || (gameTable.getCardfromBoard(x1-1,y1) == NONE || gameTable.getCardfromBoard(x1-1,y1) == NOT) || (gameTable.getCardfromBoard(x1,y1+1) == NONE || gameTable.getCardfromBoard(x1+1,y1+1) == NOT) || (gameTable.getCardfromBoard(x1,y1-1) == NONE || gameTable.getCardfromBoard(x1,y1-1) == NOT)){
            return true;
        }
        System.out.println("Card has not free side");
        return false;
    }

    /**
     *
     * @param x1: x coordinate of Card 1
     * @param y1: y coordinate of Card 1
     * @param x2: x coordinate of Card 2
     * @param y2: y coordinate of Card 2
     * @return true if the two selected cards are drawable
     * the two cards must have a free side and must be adjacent
     */
    public boolean checkNear(int x1, int y1, int x2, int y2){
        if(checkNear(x1, y1) && checkNear(x2, y2)){
            if((x1 == x2+1 && y1 == y2) || (x1 == x2-1 && y1 == y2) || (x1 == x2 && y1 == y2+1) || (x1 == x2 && y1 == y2-1)){
                return true;
            }
        }
        System.out.println("Card 2 has different x and y beside Card 1 (x or y must be the same)");
        return false;
    }

    /**
     *
     * @param x1: x coordinate of Card 1
     * @param y1: y coordinate of Card 1
     * @param x2: x coordinate of Card 2
     * @param y2: y coordinate of Card 2
     * @param x3: x coordinate of Card 3
     * @param y3: y coordinate of Card 3
     * @return true if the three selected cards are drawable
     * the three cards must have a free side and must be adjacent and must be on the same row or column
     */
    public boolean checkNear(int x1, int y1, int x2, int y2, int x3, int y3){
        if(checkNear(x1, y1) && checkNear(x2, y2) && checkNear(x3, y3)){
            if(((checkNear(x1, y1, x2, y2) && checkNear(x2, y2, x3, y3)) || ((checkNear(x1, y1, x3, y3) && checkNear(x2, y2, x3, y3)))) && ((x1 == x2 && x2 == x3) || (y1 == y2 && y2 == y3))){
                return true;
            }
        }
        System.out.println("Cards must be in the same row or column");
        return false;
    }

}
