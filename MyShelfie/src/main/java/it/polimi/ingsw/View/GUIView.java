package it.polimi.ingsw.View;

import it.polimi.ingsw.Model.*;
import javafx.application.Platform;

import java.io.Serializable;
import java.util.ArrayList;

public class GUIView implements ViewClient, Serializable {

private ControllerGui controllerGui = new ControllerGui();
private GameLogic gameLogic;
private boolean first = true;
private byte initLibrary = 0;

    public GUIView(){    }
    @Override
    public void viewLibrary(Library library) {
        Platform.runLater(()-> controllerGui.updateLibrary(library));
    }

    @Override
    public void viewGameTable(GameTable gameTable) {
        Platform.runLater(() -> {
            controllerGui.updateGameTable(gameTable);
            controllerGui.disableGameTable();
        });
    }

    @Override
    public void viewString(String message) {
        Platform.runLater(() -> controllerGui.setLabelMessage(message));
    }

    @Override
    public void viewPlayerObj(PlayerObj playerObj) {    }

    @Override
    public void viewCommonObj(CommonObj obj1, CommonObj obj2) {    }

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

    @Override
    public ArrayList<Card> getCardFromTable(GameLogic gameLogic){

        System.out.println("in getCardFromTable, GuiView ...");

        while(!controllerGui.getConfirmCards()){
            //wait until get confirm is true
            try{
                Thread.sleep(100);
            } catch (InterruptedException ignore) {}
        }

        System.out.println("get the confirmation and go on...");
        this.gameLogic = controllerGui.getGameLogic();
        controllerGui.setConfirmCards(false);

        return controllerGui.getListCard();
    }


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
            });
            first = false;
        }

        System.out.println("turn started ");

        Platform.runLater(() -> {
            controllerGui.updateGameTable(gameLogic.getGameTable());
            controllerGui.setGameLogic(gameLogic);
            controllerGui.clearListCard();
            controllerGui.clearPosCard();
            controllerGui.setLabelMessage("Is your turn!  Choose from 1 to 3 Cards");
            controllerGui.enableGameTable();
        });

        ArrayList<Card> list;
        System.out.println("created list ");
        list = getCardFromTable(this.gameLogic);
        System.out.println("taken list from getCardFromTable, GuiView");
        insert(list, this.gameLogic);

        return gameLogic;
    }

    public void setController(ControllerGui controllerGui) { this.controllerGui = controllerGui; }

}
