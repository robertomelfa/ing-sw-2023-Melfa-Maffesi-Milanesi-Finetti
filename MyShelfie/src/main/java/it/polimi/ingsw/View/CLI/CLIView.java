package it.polimi.ingsw.View.CLI;

import it.polimi.ingsw.Model.*;
import it.polimi.ingsw.View.GUI.ControllerGui;
import it.polimi.ingsw.View.ViewClient_Interface;

import java.io.IOException;
import java.io.Serializable;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

import static it.polimi.ingsw.Model.Card.NONE;
import static it.polimi.ingsw.Model.Card.NOT;

public class CLIView implements ViewClient_Interface, Serializable {

    String username;
    GameLogic gameLogic;

    /**
     * @return the ip chosen by the user
     */
    public String requestIP(){
        String port;
        Scanner input = new Scanner(System.in);
        do{
            System.out.println("Insert server IP:");
            port = input.nextLine();
        }while (!port.matches("\\d+.\\d+.\\d+.\\d+") && !port.matches("localhost") && !port.matches(""));

        if (port.isBlank()){
            port = "localhost";
        }
        return port;
    }

    /**
     * print the received library
     * @param library : library we want to print
     */
    @Override
    public void viewLibrary(Library library) {
        System.out.print("   ");
        for (int i = 1; i < 6; i++) {
            System.out.printf("     %d     ", i);
        }

        System.out.print("\n");
        System.out.print("\n");

        for (int i = 0; i < 6; i++) {
            System.out.printf(" %d ", i + 1);
            for (int j = 0; j < 5; j++) {
                if (library.getPos(i,j)!= NOT && library.getPos(i,j)!= NONE) {
                    System.out.printf("%-22s", library.getPos(i,j));
                } else {
                    System.out.print("           ");
                }
            }
            System.out.print("\n");
        }
        System.out.print("\n");
    }

    /**
     * print the received game table
     * @param gameTable : game table we want to print
     */
    @Override
    public void viewGameTable(GameTable gameTable) {
        System.out.print("   ");
        for(int i = 1; i < 10; i++){
            System.out.printf("     %d     ", i);
        }

        System.out.print("\n");
        System.out.print("\n");

        for(int i = 1; i < 10; i++){
            System.out.printf(" %d ", i);
            for(int j = 1; j < 11; j++){
                if(gameTable.getCardfromBoard(i,j) != NOT && gameTable.getCardfromBoard(i,j) != NONE){ // remove this if to view NONE and NOT on the gameTable
                    System.out.printf("%-22s", gameTable.getCardfromBoard(i,j));
                }else{
                    System.out.print("           ");     // if there is no card, read space

                }
            }
            System.out.print("\n");
        }
        System.out.print("\n");
    }

    /**
     * print the received message
     * @param message : the message we want to view
     */
    @Override
    public void viewString(String message) {
        System.out.println(message);
    }

    /**
     * prints leaderboard
     * @param msg : string containing the leaderboard
     * @throws IOException
     */
    public void viewLeaderboard(String msg) throws IOException{
        System.out.println(msg);
    }

    /**
     *
     * @param playerObj : the player object we want to show
     */
    @Override
    public void viewPlayerObj(PlayerObj playerObj) {
        Card[][] temp= new Card[6][5];
        for (int k=0;k<6;k++){
            for (int j=0;j<5;j++){
                temp[k][j]=Card.NONE;
            }
        }
        for (int i=0; i<playerObj.getPlayerObjs().size();i++){
            temp[playerObj.getPlayerObjs().get(i).getXPosition()][playerObj.getPlayerObjs().get(i).getYPosition()]=playerObj.getPlayerObjs().get(i).getType();
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
                    System.out.printf("%-22s", temp[i][j]);
                }else{
                    System.out.print("           ");
                }
            }
            System.out.print("\n");
        }
        System.out.print("\n");
    }

    /**
     *  prints each common object description
     * @param obj1 : first common object
     * @param obj2 : second common object
     */
    @Override
    public void viewCommonObj(CommonObj obj1, CommonObj obj2) {
        viewString("Common Object 1 : ");
        viewString(obj1.getDescription());
        viewString("Common Object 2 : ");
        viewString(obj2.getDescription());
    }

    /**
     * insert the received list of cards into a column of the library chosen by the player.
     * The player also chooses the order of the cards
     * @param list : list of cards that needs to be inserted in the library
     * @param gameLogic : the game logic of the game
     */
    public void insert(ArrayList<Card> list, GameLogic gameLogic) {
        int column = 0;
        String tempColumn;
        boolean validChoice = false;
        Scanner in = new Scanner(System.in);
        viewLibrary(gameLogic.getGame().getCurrentPlayer().getLibrary());
        do {
            System.out.println("Choose the column:");
            tempColumn = in.nextLine();
            if (tempColumn.equals("1") || tempColumn.equals("2") || tempColumn.equals("3") || tempColumn.equals("4") || tempColumn.equals("5")){
                column = Integer.parseInt(tempColumn) - 1;
                if (gameLogic.getGame().getCurrentPlayer().getLibrary().checkFreeSpaces(column, list.size())) {
                    validChoice = true;

                }else {
                    System.out.println("There is no enough space in this column");
                }
            }else {
                System.out.println("Invalid input");
            }

        } while (!validChoice);

        int flag = 0;
        String type = new String();
        int card = 0;
        int listSize = list.size();
        for (int i = 0; i < listSize; i++) {
            Card cardtoRemove = null;
            System.out.println("Which card do you want to insert?" + list.toString());
            do {
                flag = 0;
                if (in.hasNextInt()) {
                    card = Integer.parseInt(in.nextLine()) - 1;
                    if (card >= 0 && card < list.size()) {
                        flag = 1;
                    } else {
                        System.out.println("This card does not exist, try again!");
                    }
                } else {
                    type = in.nextLine().toUpperCase();
                    if (type.equals("YELLOW") || type.equals("PURPLE") || type.equals("WHITE") || type.equals("BLUE") || type.equals("LIGHTBLUE") || type.equals("GREEN")) {
                        for(int j=0; j<list.size(); j++){
                            if (list.get(j).compare(type)){
                                cardtoRemove = list.get(j);
                                flag = 2;
                                break;
                            }
                        }
                        if (cardtoRemove == null){
                            System.out.println("This card does not exist, try again!");
                        }

                    } else {
                        System.out.println("This card does not exist, try again!");
                    }

                }
            } while (flag == 0);
            if (flag == 1) {
                gameLogic.getGame().getCurrentPlayer().getLibrary().getGrid()[gameLogic.getGame().getCurrentPlayer().getLibrary().lastRowFree(column)][column] = list.get(card);
                list.remove(card);
            } else if (flag == 2) {
                list.remove(cardtoRemove);
                gameLogic.getGame().getCurrentPlayer().getLibrary().getGrid()[gameLogic.getGame().getCurrentPlayer().getLibrary().lastRowFree(column)][column] = cardtoRemove;
            } else {
                System.out.println("This card does not exist, try again!");notify();
            }
        }
        System.out.println("Now the grid is: \n");
        gameLogic.getGame().getCurrentPlayer().setLibrary(gameLogic.getGame().getCurrentPlayer().getLibrary());
        viewLibrary(gameLogic.getGame().getCurrentPlayer().getLibrary());
        this.gameLogic = gameLogic;
    }

    /**
     * handles all the process of selecting a card from the game table.
     * The player chooses the number of cards he wants to pick and then insert their coordinates.
     * A check is performed to see if the selection is valid, if so the selected cards are returned in a list.
     * If the selection is not valid the player can correct the coordinates.
     * @param gameLogic : the game logic of the game
     * @return the cards selected from the game table by the player
     */
    public ArrayList<Card> getCardFromTable(GameLogic gameLogic){
        int size = 0;
        boolean back;
        Scanner in = new Scanner(System.in);
        ArrayList<Card> list = new ArrayList<Card>();
        do{
            back = false;
            do {
                String tempSize;
                do {
                    System.out.println("How many cards? At any moment of the selection you can write back to return to this point");
                    tempSize = in.nextLine();
                } while (!tempSize.matches("\\d"));
                size = Integer.parseInt(tempSize);

                if(size < 1 || size >3){
                    System.out.println("You can pick 1, 2 or 3 cards. Try again!");
                }
                if(!gameLogic.checkCardsPickable(size)){
                    System.out.println("There isn't " + size + " cards avaible");
                }
                if(!gameLogic.getGame().getCurrentPlayer().getLibrary().checkNumCardsRemain(size)){
                    System.out.println("There isn't column with " + size + " free spaces");
                }
            }while (size < 1 || size >3 || !gameLogic.checkCardsPickable(size) || !gameLogic.getGame().getCurrentPlayer().getLibrary().checkNumCardsRemain(size));

            if(size == 1) {  // case 1 card
                int x1 = 0, y1 = 0;
                // ask coordinates until a correct input
                do {
                    String tempX, tempY;
                    do {
                        System.out.println("Row Card 1");
                        tempX = in.nextLine();
                    } while (!tempX.matches("\\d") && !tempX.toLowerCase().equals("back"));
                    if (!tempX.toLowerCase().equals("back")) {
                        x1 = Integer.parseInt(tempX);
                        do {
                            System.out.println("Column y Card 1");
                            tempY = in.nextLine();
                        } while (!tempY.matches("\\d") && !tempY.toLowerCase().equals("back"));
                        if (!tempY.toLowerCase().equals("back")) {
                            y1 = Integer.parseInt(tempY);
                            if (!gameLogic.checkNear(x1, y1)) {
                                System.out.println("Invalid coordinates, try again!");
                            }
                        } else {
                            back = true;
                        }
                    } else {
                        back = true;
                    }
                } while (!gameLogic.checkNear(x1, y1) && !back);
                if (gameLogic.checkNear(x1, y1) && !back) {
                    list.add(gameLogic.getGameTable().getCardfromBoard(x1, y1));
                    System.out.println("Card " + gameLogic.getGameTable().getCardfromBoard(x1, y1) + " drawn!");
                    gameLogic.getGameTable().setCardfromBoard(x1, y1, NONE);
                }
            }else if(size == 2) {        // case 2 cards
                int x1 = 0, y1 = 0, x2 = 0, y2 = 0;
                // requires coordinates
                do {
                    String tempX, tempY;
                    do {
                        System.out.println("Row Card 1");
                        tempX = in.nextLine();
                    } while (!tempX.matches("\\d") && !tempX.toLowerCase().equals("back"));
                    if (!tempX.toLowerCase().equals("back")) {
                        x1 = Integer.parseInt(tempX);

                        do {
                            System.out.println("Column Card 1");
                            tempY = in.nextLine();
                        } while (!tempY.matches("\\d") && !tempY.toLowerCase().equals("back"));
                        if (!tempY.toLowerCase().equals("back")) {
                            y1 = Integer.parseInt(tempY);

                            do {
                                System.out.println("Row Card 2");
                                tempX = in.nextLine();
                            } while (!tempX.matches("\\d") && !tempX.toLowerCase().equals("back"));
                            if (!tempX.toLowerCase().equals("back")) {
                                x2 = Integer.parseInt(tempX);

                                do {
                                    System.out.println("Column Card 2");
                                    tempY = in.nextLine();
                                } while (!tempY.matches("\\d") && !tempY.toLowerCase().equals("back"));
                                if (!tempY.toLowerCase().equals("back")) {
                                    y2 = Integer.parseInt(tempY);

                                    if (!gameLogic.checkNear(x1, y1, x2, y2)) {
                                        System.out.println("Invalid coordinates, try again!");
                                    }
                                } else {
                                    back = true;
                                }
                                ;
                            } else {
                                back = true;
                            }
                        } else {
                            back = true;
                        }
                    } else {
                        back = true;
                    }

                } while (!gameLogic.checkNear(x1, y1, x2, y2) && !back);
                if (gameLogic.checkNear(x1, y1, x2, y2) && !back) {
                    list.add(gameLogic.getGameTable().getCardfromBoard(x1, y1));
                    list.add(gameLogic.getGameTable().getCardfromBoard(x2, y2));
                    System.out.println("Card " + gameLogic.getGameTable().getCardfromBoard(x1, y1) + " " + gameLogic.getGameTable().getCardfromBoard(x2, y2) + " drawn");
                    gameLogic.getGameTable().setCardfromBoard(x1, y1, NONE);
                    gameLogic.getGameTable().setCardfromBoard(x2, y2, NONE);
                }
            }else{  // case 3 cards
                int x1 = 0, y1 = 0, x2 = 0, y2 = 0, x3 = 0, y3 = 0;
                // requires coordinates
                do{
                    String tempX,tempY;
                    do {
                        System.out.println("Row Card 1");
                        tempX = in.nextLine();
                    }while (!tempX.matches("\\d") && !tempX.toLowerCase().equals("back"));
                    if (!tempX.toLowerCase().equals("back")) {
                        x1 = Integer.parseInt(tempX);

                        do {
                            System.out.println("Column Card 1");
                            tempY = in.nextLine();
                        } while (!tempY.matches("\\d") && !tempY.toLowerCase().equals("back"));
                        if (!tempY.toLowerCase().equals("back")) {
                            y1 = Integer.parseInt(tempY);

                            do {
                                System.out.println("Row Card 2");
                                tempX = in.nextLine();
                            } while (!tempX.matches("\\d") && !tempX.toLowerCase().equals("back"));
                            if(!tempX.toLowerCase().equals("back")) {
                                x2 = Integer.parseInt(tempX);

                                do {
                                    System.out.println("Column Card 2");
                                    tempY = in.nextLine();
                                } while (!tempY.matches("\\d") && !tempY.toLowerCase().equals("back"));
                                if(!tempY.toLowerCase().equals("back")) {
                                    y2 = Integer.parseInt(tempY);

                                    do {
                                        System.out.println("Row Card 3");
                                        tempX = in.nextLine();
                                    } while (!tempX.matches("\\d") && !tempX.toLowerCase().equals("back"));
                                    if(!tempX.toLowerCase().equals("back")) {
                                        x3 = Integer.parseInt(tempX);

                                        do {
                                            System.out.println("Column Card 3");
                                            tempY = in.nextLine();
                                        } while (!tempY.matches("\\d") && !tempY.toLowerCase().equals("back"));
                                        if(!tempY.toLowerCase().equals("back")) {
                                            y3 = Integer.parseInt(tempY);

                                            if (!gameLogic.checkNear(x1, y1, x2, y2, x3, y3)) {
                                                System.out.println("Invalid coordinates, try again!");
                                            }
                                        } else { back = true; }
                                    } else { back = true; }
                                } else { back = true; }
                            } else { back = true; }
                        } else { back = true; }
                    } else { back = true; }
                }while(!gameLogic.checkNear(x1, y1, x2, y2, x3, y3) && !back);

                if(gameLogic.checkNear(x1, y1, x2, y2, x3, y3) && !back){
                    // add cards on the list
                    list.add(gameLogic.getGameTable().getCardfromBoard(x1,y1));
                    list.add(gameLogic.getGameTable().getCardfromBoard(x2,y2));
                    list.add(gameLogic.getGameTable().getCardfromBoard(x3,y3));

                    System.out.println("Cards " + gameLogic.getGameTable().getCardfromBoard(x1,y1) + " " + gameLogic.getGameTable().getCardfromBoard(x2,y2) + " " + gameLogic.getGameTable().getCardfromBoard(x3,y3) + " drawn");
                    // set NONE on the table
                    gameLogic.getGameTable().setCardfromBoard(x1,y1,NONE);
                    gameLogic.getGameTable().setCardfromBoard(x2,y2,NONE);
                    gameLogic.getGameTable().setCardfromBoard(x3,y3,NONE);
                }
            }
        }while (back);
        gameLogic.getGameTable().checkStatus();
        this.gameLogic = gameLogic;
        // return gameLogic
        return list;
    }


    /**
     * handles all the process of selecting a card from the game table.
     * The player chooses the number of cards he wants to pick and then insert their coordinates.
     * A check is performed to see if the selection is valid, if so the selected cards are returned in a list.
     * If the selection is not valid the player can correct the coordinates.
     * @param gameLogic : the game logic of the game
     * @return the cards selected from the game table by the player
     */
    /*
    public ArrayList<Card> getCardFromTable(GameLogic gameLogic){
        int size = 0;
        Scanner in = new Scanner(System.in);
        ArrayList<Card> list = new ArrayList<Card>();
        do{
            String tempSize;
            do {
                System.out.println("How many cards?");         // number of card to pick (1 - 3)
                tempSize = in.nextLine();
            }while (!tempSize.matches("\\d"));           // "\\d" represents number from 0-9
            size = Integer.parseInt(tempSize);

            if(size < 1 || size >3){
                System.out.println("You can pick 1, 2 or 3 cards. Try again!");
            }
            if(!gameLogic.checkCardsPickable(size)){
                System.out.println("There isn't " + size + " cards avaible");
            }
            if(!gameLogic.getGame().getCurrentPlayer().getLibrary().checkNumCardsRemain(size)){
                System.out.println("There isn't column with " + size + " free spaces");
            }

        }while(size < 1 || size >3 || !gameLogic.checkCardsPickable(size) || !gameLogic.getGame().getCurrentPlayer().getLibrary().checkNumCardsRemain(size));
        if(size == 1){  // case 1 card
            int x1, y1;
            // ask coordinates until a correct input
            do{
                String tempX,tempY;
                do {
                    System.out.println("Row Card 1");
                    tempX = in.nextLine();
                }while (!tempX.matches("\\d"));
                x1 = Integer.parseInt(tempX);
                do {
                    System.out.println("Column y Card 1");
                    tempY = in.nextLine();
                }while (!tempY.matches("\\d"));
                y1 = Integer.parseInt(tempY);
                if(!gameLogic.checkNear(x1, y1)){
                    System.out.println("Invalid coordinates, try again!");
                }
            }while(!gameLogic.checkNear(x1, y1));
            if(gameLogic.checkNear(x1, y1)){
                list.add(gameLogic.getGameTable().getCardfromBoard(x1,y1));
                System.out.println("Card " + gameLogic.getGameTable().getCardfromBoard(x1,y1) + " drawn!");
                gameLogic.getGameTable().setCardfromBoard(x1,y1, NONE);
            }
        }else if(size == 2){        // case 2 cards
            int x1, y1, x2, y2;
            // requires coordinates
            do{
                String tempX,tempY;
                do {
                    System.out.println("Row Card 1");
                    tempX = in.nextLine();
                }while (!tempX.matches("\\d"));
                x1 = Integer.parseInt(tempX);

                do {
                    System.out.println("Column Card 1");
                    tempY = in.nextLine();
                }while (!tempY.matches("\\d"));
                y1 = Integer.parseInt(tempY);

                do {
                    System.out.println("Row Card 2");
                    tempX = in.nextLine();
                }while (!tempX.matches("\\d"));
                x2 = Integer.parseInt(tempX);

                do {
                    System.out.println("Column Card 2");
                    tempY = in.nextLine();
                }while (!tempY.matches("\\d"));
                y2 = Integer.parseInt(tempY);

                if(!gameLogic.checkNear(x1, y1, x2, y2)){
                    System.out.println("Invalid coordinates, try again!");
                }
            }while(!gameLogic.checkNear(x1, y1, x2, y2));
            if(gameLogic.checkNear(x1, y1, x2, y2)){
                list.add(gameLogic.getGameTable().getCardfromBoard(x1,y1));
                list.add(gameLogic.getGameTable().getCardfromBoard(x2,y2));
                System.out.println("Card " + gameLogic.getGameTable().getCardfromBoard(x1,y1) + " " + gameLogic.getGameTable().getCardfromBoard(x2,y2) + " drawn");
                gameLogic.getGameTable().setCardfromBoard(x1,y1,NONE);
                gameLogic.getGameTable().setCardfromBoard(x2,y2,NONE);
            }
        }else{  // case 3 cards
            int x1, y1, x2, y2, x3, y3;
            // requires coordinates
            do{
                String tempX,tempY;
                do {
                    System.out.println("Row Card 1");
                    tempX = in.nextLine();
                }while (!tempX.matches("\\d"));
                x1 = Integer.parseInt(tempX);

                do {
                    System.out.println("Column Card 1");
                    tempY = in.nextLine();
                }while (!tempY.matches("\\d"));
                y1 = Integer.parseInt(tempY);

                do {
                    System.out.println("Row Card 2");
                    tempX = in.nextLine();
                }while (!tempX.matches("\\d"));
                x2 = Integer.parseInt(tempX);

                do {
                    System.out.println("Column Card 2");
                    tempY = in.nextLine();
                }while (!tempY.matches("\\d"));
                y2 = Integer.parseInt(tempY);

                do {
                    System.out.println("Row Card 3");
                    tempX = in.nextLine();
                }while (!tempX.matches("\\d"));
                x3 = Integer.parseInt(tempX);

                do {
                    System.out.println("Column Card 3");
                    tempY = in.nextLine();
                }while (!tempY.matches("\\d"));
                y3 = Integer.parseInt(tempY);

                if(!gameLogic.checkNear(x1, y1, x2, y2, x3, y3)){
                    System.out.println("Invalid coordinates, try again!");
                }
            }while(!gameLogic.checkNear(x1, y1, x2, y2, x3, y3));

            if(gameLogic.checkNear(x1, y1, x2, y2, x3, y3)){
                // add cards on the list
                list.add(gameLogic.getGameTable().getCardfromBoard(x1,y1));
                list.add(gameLogic.getGameTable().getCardfromBoard(x2,y2));
                list.add(gameLogic.getGameTable().getCardfromBoard(x3,y3));

                System.out.println("Cards " + gameLogic.getGameTable().getCardfromBoard(x1,y1) + " " + gameLogic.getGameTable().getCardfromBoard(x2,y2) + " " + gameLogic.getGameTable().getCardfromBoard(x3,y3) + " drawn");
                // set NONE on the table
                gameLogic.getGameTable().setCardfromBoard(x1,y1,NONE);
                gameLogic.getGameTable().setCardfromBoard(x2,y2,NONE);
                gameLogic.getGameTable().setCardfromBoard(x3,y3,NONE);

            }
        }

        // check the status of the table
        gameLogic.getGameTable().checkStatus();

        this.gameLogic = gameLogic;
        // return gameLogic
        return list;
    }
    */

    /**
     * perform the turn using getCardFromTable to draw cards from the table and then insert
     * to place them in the library
     * @param gameLogic : the game logic of the game
     * @return the updated gamelogic
     */
    public GameLogic getTurn(GameLogic gameLogic) {
        ArrayList<Card> list = new ArrayList<>();
        // get the cards from the table
        list = getCardFromTable(gameLogic);
        // insert the picked cards into the library
        insert(list, this.gameLogic);
        return this.gameLogic;
    }

    /**
     *
     * @param controllerGui : controller that needs to be set
     */
    @Override
    public void setController(ControllerGui controllerGui) {    }

    /**
     * prints the point of each player
     * @param playerList : list of all the player
     */
    public void viewPoints(ArrayList<Player> playerList){
        String string = "POINTS\n";
        for(int i = 0; i < playerList.size(); i++){
            string = string + playerList.get(i).getNickname() + " : " + playerList.get(i).getPoints() + " | ";
        }
        System.out.println(string);
    }


}
