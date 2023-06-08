package it.polimi.ingsw.View;

import it.polimi.ingsw.Model.*;

import java.io.IOException;
import java.rmi.RemoteException;
import java.util.ArrayList;


public interface ViewClient {
    public void viewLibrary(Library library);
    public void viewGameTable(GameTable gameTable);
    public void viewString(String message);
    public void viewLeaderboard(String msg) throws IOException;
    public void viewPlayerObj(PlayerObj playerObj);
    public void viewCommonObj(CommonObj obj1, CommonObj obj2);
    public void insert(ArrayList<Card> list, GameLogic gameLogic);
    public ArrayList<Card> getCardFromTable(GameLogic gameLogic) throws RemoteException;

    public GameLogic getTurn(GameLogic gameLogic);

    void setController(ControllerGui controllerGui);


    public void viewPoints(ArrayList<Player> playerList);
}
