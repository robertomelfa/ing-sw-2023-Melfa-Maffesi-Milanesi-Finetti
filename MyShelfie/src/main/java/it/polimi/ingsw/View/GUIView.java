package it.polimi.ingsw.View;

import it.polimi.ingsw.Model.*;
import javafx.application.Platform;

import java.io.Serializable;
import java.util.ArrayList;

public class GUIView implements ViewClient, Serializable {

private ControllerGui controllerGui = new ControllerGui();
private GameLogic gameLogic;
private boolean first = true;
private boolean firstName = true;
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
            controllerGui.setGameLogic(gameLogic);
            controllerGui.updateCommonObjPoints();
            controllerGui.updateGameTable(gameLogic.getGameTable());
            controllerGui.clearListCard();
            controllerGui.clearPosCard();
            controllerGui.setLabelMessage("Is your turn!  Choose from 1 to 3 Cards");
            controllerGui.enableGameTable();
        });

        ArrayList<Card> list;
        list = getCardFromTable(this.gameLogic);
        insert(list, this.gameLogic);

        gameLogic.getGameTable().checkStatus();

        return gameLogic;
    }

    public void setController(ControllerGui controllerGui) { this.controllerGui = controllerGui; }

    @Override
    public void updatePoints(String msg) {
        if (firstName){
            String message = msg;
            Platform.runLater(()->controllerGui.setNamePlayers(message));
            firstName=false;
        }

        msg = msg.replaceAll("\\D+","");
        for (int i=0; i<msg.length(); i++){
            int point = msg.charAt(i) -48;
            int temp = i+1;
            Platform.runLater(()-> controllerGui.updatePoits(point, temp));
        }
    }

}
