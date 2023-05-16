package it.polimi.ingsw.View;

import it.polimi.ingsw.Model.*;
import javafx.application.Application;
import javafx.stage.Stage;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Scanner;

public class GUIView extends Application implements ViewClient {


    public String askUserName() {
        return null;
    }

    @Override
    public void viewLibrary(Library library) {

    }

    @Override
    public void viewGameTable(GameTable gameTable) {

    }

    @Override
    public void viewString(String message) {

    }

    @Override
    public void viewPlayerObj(PlayerObj playerObj) {

    }

    @Override
    public void viewCommonObj(CommonObj obj1, CommonObj obj2) {

    }

    public void insert(ArrayList<Card> list, GameLogic gameLogic){
    }

    public ArrayList<Card> getCardFromTable(GameLogic gameLogic) throws RemoteException{
        return null;
    }

    public GameLogic getTurn(GameLogic gameLogic){
        return null;
    }

    @Override
    public void start(Stage stage) throws Exception {

    }
}
