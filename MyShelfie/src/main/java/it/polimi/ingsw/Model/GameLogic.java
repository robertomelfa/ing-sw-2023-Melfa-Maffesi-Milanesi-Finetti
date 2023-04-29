package it.polimi.ingsw.Model;


import java.util.ArrayList;
import java.util.Scanner;

import static it.polimi.ingsw.Model.Card.*;

public class GameLogic {
    private GameTable gameTable;


    private Game game1;


    /**
     *
     * @param game: the current game
     * The method is the constructor for GameLogic class
     * It takes the gameTable of the game
     */
    public GameLogic(Game game){
        Game game1;
        this.game1 = game;
        gameTable = game.getGameTable();
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
                System.out.println("Card " + gameTable.board[x1][y1] + " " + gameTable.board[x2][y2] + " drawn");
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

    /**
     *
     * @param x1: x coordinate of Card 1
     * @param y1: y coordinate of Card 2
     * @return true if card has at least one free side
     */
    // verify if card has free side
    private boolean checkNear(int x1, int y1){
        if(gameTable.board[x1][y1] == NONE || gameTable.board[x1][y1] == NOT){
            System.out.println("Card not exists");
            return false;
        }else if((gameTable.board[x1+1][y1] == NONE || gameTable.board[x1+1][y1] == NOT) || (gameTable.board[x1-1][y1] == NONE || gameTable.board[x1-1][y1] == NOT) || (gameTable.board[x1][y1+1] == NONE || gameTable.board[x1+1][y1+1] == NOT) || (gameTable.board[x1][y1-1] == NONE || gameTable.board[x1][y1-1] == NOT)){
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
    private boolean checkNear(int x1, int y1, int x2, int y2){
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
    private boolean checkNear(int x1, int y1, int x2, int y2, int x3, int y3){
        if(checkNear(x1, y1) && checkNear(x2, y2) && checkNear(x3, y3)){
            if(((checkNear(x1, y1, x2, y2) && checkNear(x2, y2, x3, y3)) || ((checkNear(x1, y1, x3, y3) && checkNear(x2, y2, x3, y3)))) && ((x1 == x2 && x2 == x3) || (y1 == y2 && y2 == y3))){
                return true;
            }
        }
        System.out.println("Cards must be in the same row or column");
        return false;
    }


    /**
     * This method is used to handle every turn of the game
     * @throws Exception
     */

    public void startTurn() throws Exception {

        if(game1.getCurrentPlayer()!=null){
            Player currentPlayer = game1.getCurrentPlayer();

            Scanner in = new Scanner(System.in);

            System.out.print(currentPlayer.getNickname()+" is your turn to play! Choose the cards you want form the game table\n");


            game1.getCurrentPlayer().getLibrary().viewGrid();
            System.out.print("\n");
            gameTable.viewTable();


            System.out.print("Insert 1 if you want to see your objectives or insert 2 if you want to pick the cards\n");

            int i = 0;

            while(i == 0){

                switch (in.nextInt()){
                    case 1:
                        System.out.println("Player Object:");
                        currentPlayer.getPlayerObj().print();
                        System.out.println("Common Object 1:");
                        System.out.println(game1.getCommonObj1().getDescrizione());
                        System.out.println("Common Object 2:");
                        System.out.println(game1.getCommonObj2().getDescrizione());
                        System.out.print("\n");
                        System.out.print("Insert 1 if you want to see your objectives or insert 2 if you want to pick the cards\n");
                        break;
                    case 2:
                        i = 1;
                        break;
                    default:
                        System.out.print("The input is not valid, please insert 1 or 2\n");
                }

            }


            ArrayList<Card> cards = new ArrayList<>();

            cards = getCardFromTable();

            System.out.print("Now you can insert the cards in your library\n");

            currentPlayer.getLibrary().insert(cards);

            // check dei commonObj

            if(!game1.getCurrentPlayer().getCommonObj1Completed()){
                if(game1.getCommonObj1().checkObj(currentPlayer.getLibrary())){
                    currentPlayer.addPoints(game1.getCommonObj1().getPointCount());
                    System.out.print("Congratulations, you successfully completed a common goal\n");
                    game1.getCurrentPlayer().setCommonObj1Completed();
                }
            }

            if(!game1.getCurrentPlayer().getCommonObj2Completed()){
                if(game1.getCommonObj2().checkObj(currentPlayer.getLibrary())){
                    currentPlayer.addPoints(game1.getCommonObj2().getPointCount());
                    System.out.print("Congratulations, you successfully completed a common goal\n");
                    game1.getCurrentPlayer().setCommonObj2Completed();
                }
            }

      /*      if(!game1.getCurrentPlayer().getPlyObjCompleted()){
                int points=game1.getCurrentPlayer().getPlayerObj().checkObj(game1.getCurrentPlayer(),game1.getCurrentPlayer().getLibrary());
                if (points>0){
                    game1.getCurrentPlayer().addPoints(points);
                    System.out.print("\"Congratulations, you successfully completed your personal goal\\n\"");
//                    avevo pensato di metterlo qui, ma poi lo abbiamo messo direttamente in checkPlayerObj (@simone)
//                    if (game1.getCurrentPlayer().getPlayerObj().getI() >= 6) game1.getCurrentPlayer().setPlyObjCompleted();
                }

            }   */

            if(game1.getCurrentPlayer().getLibrary().checkFull()){
                game1.setEndGame();
                game1.getCurrentPlayer().addPoints(1);
                System.out.println(game1.getCurrentPlayer().getNickname()+"has finished the game");
            }

            System.out.println(currentPlayer.getNickname()+"your turn is ended!\n Preparing for the next turn...");


            /* update the current player before ending*/
            game1.updateCurrentPlayer();

            gameTable.checkStatus();
        }else {
            System.out.println("GAME IS ENDED ");
        }
    }



}
