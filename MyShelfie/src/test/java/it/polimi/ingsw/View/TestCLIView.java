package it.polimi.ingsw.View;

import it.polimi.ingsw.Model.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

import static it.polimi.ingsw.Model.Card.*;

public class TestCLIView {

    private CLIView view;

    @Before
    public void set(){
        view = new CLIView();
    }

    @Test
    public void viewLibraryTest(){
        Library library = new Library();
        library.setCard(2,2, Card.YELLOW);
        library.setCard(3,2, Card.YELLOW);
        library.setCard(2,4, Card.YELLOW);

        view.viewLibrary(library);
    }

    @Test
    public void viewTableTest(){
        GameTable gameTable = new GameTable(2);
        gameTable.setCardfromBoard(3,3,Card.YELLOW);
        gameTable.setCardfromBoard(5,4,Card.YELLOW);
        gameTable.setCardfromBoard(5,3,Card.YELLOW);
        gameTable.setCardfromBoard(6,3,Card.YELLOW);

        view.viewGameTable(gameTable);
    }

    @Test
    public void viewStringTest(){
        view.viewString("ciao");
    }

    @Test
    public void viewPlayerObjTest() throws Exception{
        PlayerObj obj = new PlayerObj(2);
        view.viewPlayerObj(obj);
    }

    @Test
    public void viewCommObjTest() throws Exception{
        CommonObj obj1 = new CommonObj(2, 1);
        CommonObj obj2 = new CommonObj(2, 6);
        view.viewCommonObj(obj1, obj2);
    }

    @Test
    public void viewGetOneCardTest() throws Exception{
        Game game = new Game(2);
        GameLogic gameLogic = new GameLogic(game);
        GameTable gameTable1 = gameLogic.getGameTable();
        Card card = gameTable1.getCardfromBoard(2,4);
        Player player = new Player("bob");
        player.getLibrary().setCard(5, 0, BLUE);
        player.getLibrary().setCard(4, 0, BLUE);
        gameLogic.getGame().setCurrentPlayer(player);
        // one card

        String input = "5\n1\n5\n1\n2\n4\n";
        ByteArrayInputStream inputStream = new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8));
        System.setIn(inputStream);


        ArrayList<Card> list = view.getCardFromTable(gameLogic);

        Assert.assertEquals(card, list.get(0));
    }

    @Test
    public void getTwoCardTest() throws Exception{
        // two cards
        Game game = new Game(2);
        GameLogic gameLogic = new GameLogic(game);
        GameTable gameTable1 = gameLogic.getGameTable();
        Player player = new Player("bob");
        gameLogic.getGame().setCurrentPlayer(player);

        view.viewGameTable(gameTable1);

        String input = "2\n8\n5\n8\n6\n";
        ByteArrayInputStream inputStream = new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8));
        System.setIn(inputStream);

        Card card1 = gameTable1.getCardfromBoard(8,5);
        Card card2 = gameTable1.getCardfromBoard(8,6);


        ArrayList<Card> list1 = view.getCardFromTable(gameLogic);

        Assert.assertEquals(card1, list1.get(0));
        Assert.assertEquals(card2, list1.get(1));
    }

    @Test
    public void getThreeCardTest() throws Exception{
        // three cards

        Game game = new Game(2);
        GameLogic gameLogic = new GameLogic(game);
        GameTable gameTable1 = gameLogic.getGameTable();
        Player player = new Player("bob");
        gameLogic.getGame().setCurrentPlayer(player);

        view.viewGameTable(gameTable1);

        String input = "3\n2\n8\n4\n8\n5\n8\n6\n8\n5";
        ByteArrayInputStream inputStream = new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8));
        System.setIn(inputStream);

        Card card2 = gameTable1.getCardfromBoard(8,5);
        Card card3 = gameTable1.getCardfromBoard(8,6);


        ArrayList<Card> list1 = view.getCardFromTable(gameLogic);

        Assert.assertEquals(card2, list1.get(1));
        Assert.assertEquals(card3, list1.get(0));

    }

    @Test
    public void threeCard4PlayersTest() throws Exception {
        Game game1 = new Game(4);
        GameLogic gameLogic1 = new GameLogic(game1);
        GameTable gameTable2 = gameLogic1.getGameTable();
        view.viewGameTable(gameTable2);
        Player player = new Player("bob");
        gameLogic1.getGame().setCurrentPlayer(player);

        String input = "3\n1\n2\n4";
        ByteArrayInputStream inputStream = new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8));
        System.setIn(inputStream);


        Card card4 = gameTable2.getCardfromBoard(2,4);

        ArrayList<Card> list2 = view.getCardFromTable(gameLogic1);

        Assert.assertEquals(card4, list2.get(0));
    }

    @Test
    public void insertOneCardTest() throws Exception{
        ArrayList<Card> list = new ArrayList<>();
        list.add(YELLOW);

        Game game = new Game(2);
        GameLogic gameLogic = new GameLogic(game);
        Player player = new Player("bob");
        gameLogic.getGame().setCurrentPlayer(player);

        String input = "1\n1\n";
        ByteArrayInputStream inputStream = new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8));
        System.setIn(inputStream);

        view.insert(list, gameLogic);

        Assert.assertEquals(gameLogic.getGame().getCurrentPlayer().getLibrary().getPos(5,0), YELLOW);
    }

    @Test
    public void insertTwoCardTest() throws Exception{
        ArrayList<Card> list = new ArrayList<>();
        list.add(YELLOW);
        list.add(BLUE);

        Game game = new Game(2);
        GameLogic gameLogic = new GameLogic(game);
        Player player = new Player("bob");
        gameLogic.getGame().setCurrentPlayer(player);

        String input = "2\n2\n1\n";
        ByteArrayInputStream inputStream = new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8));
        System.setIn(inputStream);

        view.insert(list, gameLogic);

        Assert.assertEquals(gameLogic.getGame().getCurrentPlayer().getLibrary().getPos(5,1), BLUE);
        Assert.assertEquals(gameLogic.getGame().getCurrentPlayer().getLibrary().getPos(4,1), YELLOW);
    }

    @Test
    public void insertThreeCardTest() throws Exception{
        ArrayList<Card> list = new ArrayList<>();
        list.add(YELLOW);
        list.add(BLUE);
        list.add(LIGHTBLUE);

        Game game = new Game(2);
        GameLogic gameLogic = new GameLogic(game);
        Player player = new Player("bob");
        gameLogic.getGame().setCurrentPlayer(player);

        String input = "5\n3\n1\n1\n";
        ByteArrayInputStream inputStream = new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8));
        System.setIn(inputStream);

        view.insert(list, gameLogic);

        Assert.assertEquals(gameLogic.getGame().getCurrentPlayer().getLibrary().getPos(5,4), LIGHTBLUE);
        Assert.assertEquals(gameLogic.getGame().getCurrentPlayer().getLibrary().getPos(4,4), YELLOW);
        Assert.assertEquals(gameLogic.getGame().getCurrentPlayer().getLibrary().getPos(3,4), BLUE);
    }
}
