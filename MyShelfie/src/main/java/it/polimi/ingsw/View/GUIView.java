package it.polimi.ingsw.View;

import it.polimi.ingsw.Model.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Objects;

public class GUIView extends Application implements ViewClient{

private ControllerGui controllerGui;

    @Override
    public void viewLibrary(Library library) {    }

    @Override
    public void viewGameTable(GameTable gameTable) {    }

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

    }

    @Override
    public ArrayList<Card> getCardFromTable(GameLogic gameLogic) throws RemoteException {

        // trovare modo di fare arrivare le posizioni delle carte
        // controllo
        // se errore ciclo
        // pesco le carte e le metto in un array: gameLogic.getGameTable().getCardfromBoard()
        // setto a NONE le medesime carte: gameLogic.getGameTable().setCardfromBoard()
        // update gameTable
        // return array delle carte pescate


        return null;
    }


    @Override
    public GameLogic getTurn(GameLogic gameLogic) {

        controllerGui.updateGameTable(gameLogic.getGameTable());

        controllerGui.setLabelMessage("Is your turn!  Choose from 1 to three Cards");
        controllerGui.enableGameTable();

//        list = getCardFromTable(gameLogic);
//        insert(list, this.gameLogic);
//        return this.gameLogic;

        return gameLogic;
    }

    @Override
    public void start(Stage stage) throws Exception {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/MyShelfieGui.fxml"));
        Parent root = loader.load();
        setControllerGui(loader.getController());
//        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/MyShelfieGui.fxml")));
        stage.setTitle("hellow");
        stage.setScene(new Scene(root));
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    public void setControllerGui(ControllerGui controllerGui) {
        this.controllerGui = controllerGui;
    }
}
