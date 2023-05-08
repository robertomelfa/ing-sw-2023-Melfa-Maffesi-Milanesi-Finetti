package it.polimi.ingsw.View;

import it.polimi.ingsw.Model.CommonObj;
import it.polimi.ingsw.Model.GameTable;
import it.polimi.ingsw.Model.Library;
import it.polimi.ingsw.Model.PlayerObj;


public interface ViewClient {
    public String askUserName();
    public void displayLibrary(Library library);
    public void displayGameTable(GameTable gameTable);
    public void displayMessages(String message);
    public void displayPlayerObj(PlayerObj playerObj);
    public void displayCommonObj(CommonObj obj1, CommonObj obj2);
}
