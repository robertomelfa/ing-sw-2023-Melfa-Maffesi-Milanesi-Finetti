package it.polimi.ingsw.View;

import it.polimi.ingsw.Model.*;

import java.rmi.RemoteException;
import java.util.ArrayList;


public interface ViewClient {
    public String askUserName();
    public void viewLibrary(Library library);
    public void viewGameTable(GameTable gameTable);
    public void viewString(String message);
    public void viewPlayerObj(PlayerObj playerObj);
    public void viewCommonObj(CommonObj obj1, CommonObj obj2);
    public GameLogic insert(ArrayList<Card> list, GameLogic gameLogic);
    public GameLogic getCardFromTable(GameLogic gameLogic) throws RemoteException;
}
