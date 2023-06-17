package it.polimi.ingsw.View;

import it.polimi.ingsw.Model.*;
import javafx.application.Platform;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;

public class GUIView implements ViewClient, Serializable {

private ControllerGui controllerGui = new ControllerGui();
private GameLogic gameLogic;
private boolean first = true;
private boolean firstName = true;
private byte initLibrary = 0;

    public GUIView(){    }

    /**
     * call the updateLibrary method sending the updated library as a parameter
     * @param library : updated library
     */
    @Override
    public void viewLibrary(Library library) {
        Platform.runLater(()-> controllerGui.updateLibrary(library));
    }

    /**
     * call the updateGameTable method sending the updated gameTable as a parameter and then disables the
     * gameTable in the GUI
     * @param gameTable : updated gameTable
     */
    @Override
    public void viewGameTable(GameTable gameTable) {
        Platform.runLater(() -> {
            controllerGui.updateGameTable(gameTable);
            controllerGui.disableGameTable();
        });
    }

    /**
     * sets the message as a label in the window
     * @param message : the message we need to show
     */
    @Override
    public void viewString(String message) {
        Platform.runLater(() -> controllerGui.setLabelMessage(message));
    }

    /**
     * opens the leaderboard in a new window
     * @param msg
     * @throws IOException
     */
    @Override
    public void viewLeaderboard(String msg) throws IOException {
        controllerGui.openLeaderboard();
    }

    @Override
    public void viewPlayerObj(PlayerObj playerObj) {    }

    @Override
    public void viewCommonObj(CommonObj obj1, CommonObj obj2) {    }

    /**
     * shows the card list and enables the column button to insert them in the library then updates the game logic
     * @param list : card got from the table
     * @param gameLogic : the game logic of the game
     */
    @Override
    public void insert(ArrayList<Card> list, GameLogic gameLogic) {

        System.out.println("in insert, GuiView");
        Platform.runLater(() -> {
            controllerGui.showArrayCards(list);
            controllerGui.setLabelMessage("Choose the column");
            controllerGui.enableColumnButton();
        });
        System.out.println("refresh gui done, showCards");

        while (!controllerGui.getAllCardsInsert()){
            // wait until all cards are insert
            try{
                Thread.sleep(500);
            } catch (InterruptedException ignore) {
            }
        }
        controllerGui.setAllCardsInsert(false);
        this.gameLogic = controllerGui.getGameLogic();
    }

    /**
     * when the player confirm his selection it updates the game logic and then returns the selected cards
     * @param gameLogic : the game logic of the game
     * @return the card we got from the game table
     */
    @Override
    public ArrayList<Card> getCardFromTable(GameLogic gameLogic){

        System.out.println("in getCardFromTable, GuiView ...");

        while(!controllerGui.getConfirmCards()){
            //wait until get confirm is true
            try{
                Thread.sleep(100);
            } catch (InterruptedException ignore) {}
        }

        this.gameLogic = controllerGui.getGameLogic();
        controllerGui.setConfirmCards(false);

        return controllerGui.getListCard();
    }

    /**
     * performs a turn using the method defined in this class and in the controllerGUI
     * @param gameLogic : the game logic of the game
     * @return the updated game logic
     */
    @Override
    public GameLogic getTurn(GameLogic gameLogic) {


//        da capire come fare a inizializzare con gli altri player
//        controllerGui.updateCurrPlayer();
//        if (initLibrary < gameLogic.getGame().getNumOfPlayers()){
//            controllerGui.setLibraries(gameLogic.getGame().getCurrentPlayer().getLibrary());
//            initLibrary++;
//        }

        if (first){
            Platform.runLater(()-> {
                controllerGui.setPlayerObj(gameLogic);
                controllerGui.setCommonObj(gameLogic);
                controllerGui.setChair();
            });
            first = false;
        }

        System.out.println("turn started ");

        Platform.runLater(() -> {
            controllerGui.setGameLogic(gameLogic); // updates the game logic in the controllerGUI
            controllerGui.updateCommonObjPoints();
            controllerGui.updateGameTable(gameLogic.getGameTable());
            controllerGui.clearListCard(); // clears the selected cards list
            controllerGui.clearPosCard();
            controllerGui.setLabelMessage("Is your turn!  Choose from 1 to 3 Cards");
            controllerGui.enableGameTable(); // enables the game table so the player can choose his cards
        });

        ArrayList<Card> list;
        list = getCardFromTable(this.gameLogic);
        insert(list, this.gameLogic); // insert the card in the library

        gameLogic.getGameTable().checkStatus(); // checks if the game table needs to be refilled and in that case performs the
                                                // refill

        return gameLogic;
    }

    /**
     *
     * @param controllerGui : the controller gui we want to set
     */
    public void setController(ControllerGui controllerGui) { this.controllerGui = controllerGui; }


    /**
     * Updates the points of every player, if it's the first of the current player it also sets all the players names
     * @param playerList
     */
    public void viewPoints(ArrayList<Player> playerList){
        if (firstName){
            Platform.runLater(()->controllerGui.setNamePlayers(playerList));
        }
        Platform.runLater(()-> controllerGui.updatePoints(playerList));
        firstName=false;
    }

}
