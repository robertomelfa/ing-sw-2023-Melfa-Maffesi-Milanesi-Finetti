package it.polimi.ingsw.View;

import it.polimi.ingsw.Model.*;

import java.rmi.RemoteException;
import java.util.ArrayList;

public class GUIView implements ViewClient{


    @Override
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

    public GameLogic insert(ArrayList<Card> list, GameLogic gameLogic){
        return null;
    }

    public GameLogic getCardFromTable(GameLogic gameLogic) throws RemoteException{
        return null;
    }

}