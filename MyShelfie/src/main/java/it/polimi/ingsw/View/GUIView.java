package it.polimi.ingsw.View;

import it.polimi.ingsw.Model.*;
import javafx.application.Platform;

import java.util.ArrayList;

public class GUIView implements ViewClient {

private ControllerGui controllerGui;
private GameLogic gameLogic;
private byte initLibrary = 0;

    public GUIView(){    }
    @Override
    public void viewLibrary(Library library) {    }

    @Override
    public void viewGameTable(GameTable gameTable) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                controllerGui.updateGameTable(gameTable);
            }
        });
//        controllerGui.updateGameTable(gameTable);
    }

    @Override
    public void viewString(String message) {

        Platform.runLater(() -> controllerGui.setLabelMessage(message));

//        controllerGui.setLabelMessage(message);
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
                Thread.sleep(50);
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
                Thread.sleep(50);
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

    //@Override
    /*public void start(Stage stage) throws Exception {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/MyShelfieGui.fxml"));
        Parent root = loader.load();
        setControllerGui(loader.getController());
        stage.setTitle("My Shelfie");
        stage.setScene(new Scene(root));
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    */

    public void setController(ControllerGui controllerGui) { this.controllerGui = controllerGui; }

}
