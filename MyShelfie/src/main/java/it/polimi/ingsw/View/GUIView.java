package it.polimi.ingsw.View;

import it.polimi.ingsw.Model.*;

import java.util.ArrayList;

public class GUIView implements ViewClient {

private ControllerGui controllerGui;
private GameLogic gameLogic;

    public GUIView(){
        controllerGui = new ControllerGui();
    }
    @Override
    public void viewLibrary(Library library) {    }

    @Override
    public void viewGameTable(GameTable gameTable) {
        controllerGui.updateGameTable(gameTable);
    }

    @Override
    public void viewString(String message) {

        controllerGui.setLabelMessage(message);

    }

    @Override
    public void viewPlayerObj(PlayerObj playerObj) {    }

    @Override
    public void viewCommonObj(CommonObj obj1, CommonObj obj2) {    }

    @Override
    public void insert(ArrayList<Card> list, GameLogic gameLogic) {

        controllerGui.showArrayCards(list);
        controllerGui.setLabelMessage("Choose the column");
        controllerGui.enableColumnButton();

        while (!controllerGui.getAllCardsInsert()){
            // wait until all cards are insert
        }
        controllerGui.setAllCardsInsert(false);
        this.gameLogic = controllerGui.getGameLogic();
    }

    @Override
    public ArrayList<Card> getCardFromTable(GameLogic gameLogic){


        while(!controllerGui.getConfirm()){
            // wait until cards are selected and have pass controls
        }
        controllerGui.setConfirmCards(false);
        this.gameLogic = controllerGui.getGameLogic();

        return controllerGui.getListCard();
    }


    @Override
    public GameLogic getTurn(GameLogic gameLogic) {

        controllerGui.updateGameTable(gameLogic.getGameTable());
        controllerGui.setGameLogic(gameLogic);
        controllerGui.clearListCard();
        controllerGui.clearPosCard();
        controllerGui.setLabelMessage("Is your turn!  Choose from 1 to 3 Cards");
        controllerGui.enableGameTable();

        ArrayList<Card> list;
        list = getCardFromTable(this.gameLogic);
        controllerGui.disableGameTable();
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

    public void setControllerGui(ControllerGui controllerGui) { this.controllerGui = controllerGui;
    }
}
