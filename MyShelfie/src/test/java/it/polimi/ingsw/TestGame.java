package it.polimi.ingsw;

import it.polimi.ingsw.Model.Game;
import it.polimi.ingsw.Model.Player;
import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.Test;

public class TestGame {

    @Test
    public void testConstructor_1_exception(){
        try {
            Game g1 = new Game(1);
            Assert.fail("Expected exception");
            Assert.assertTrue(g1.getGameTable() != null && g1.getCardbox() != null);
        }catch (Exception e){

        }
    }

    @Test
    public void testConstructor_5_exception(){
        try {
            Game g1=new Game(5);
            Assert.fail("Expected exception");
            Assert.assertTrue(g1.getGameTable() != null && g1.getCardbox() != null);
        }catch (Exception e){

        }
    }

    @Test
    public void testaddNewPlayer_3playersnumofPlayers2_exception() throws Exception{
        Game g1=new Game(2);
        Player p1=new Player("player");
        Player p2=new Player("player");
        Player p3=new Player("player");
        try {
            g1.addNewPlayer(p1);
            g1.addNewPlayer(p2);
            g1.addNewPlayer(p3);
            Assert.fail("Expected exception");
        }catch (Exception e){

        }
    }

    @Test
    public void testaddNewPlayer_5playersnumofPlayers4_exception() throws Exception{
        Game g1=new Game(4);
        Player p1=new Player("player");
        Player p2=new Player("player");
        Player p3=new Player("player");
        Player p4=new Player("player");
        Player p5=new Player("player");
        try {
            g1.addNewPlayer(p1);
            g1.addNewPlayer(p2);
            g1.addNewPlayer(p3);
            g1.addNewPlayer(p4);
            g1.addNewPlayer(p5);
            Assert.fail("Expected exception");
        }catch (Exception e){

        }
    }

    @Test
    public void testaddNewPlayer_2playersnumofPlayers4(){
        try {
            Game g1=new Game(4);
            Player p1=new Player("player");
            Player p2=new Player("player");
            g1.addNewPlayer(p1);
            g1.addNewPlayer(p2);
        }catch (Exception e){

        }
    }

    @Test
    public void testGetter () throws Exception {

        Game game = new Game(2);
        Player p1=new Player("player1");
        Player p2=new Player("player2");
        game.addNewPlayer(p1);
        game.addNewPlayer(p2);
        game.setChairOrder();
        Assert.assertNotEquals(null, game.getCurrentPlayer());
        Assert.assertNotEquals(null, game.getChair());
        Assert.assertNotEquals(null, game.getCommonObj1());
        Assert.assertNotEquals(null, game.getCommonObj2());
        Assert.assertNotEquals(null, game.getGameTable());
        Assert.assertNotEquals(null, game.getEndGame());
        Assert.assertNotEquals(null, game.getCardbox());
        Assert.assertNotEquals(null, game.getNumOfPlayers());

    }

    @Test
    public void testUpdateCurrentPlayer() throws Exception {

        Game game = new Game(3);
        Player p1=new Player("player1");
        Player p2=new Player("player2");
        Player p3=new Player("player3");
        game.addNewPlayer(p1);
        game.addNewPlayer(p2);
        game.addNewPlayer(p3);
        game.setChairOrder();

        Player temp1 = game.getCurrentPlayer();
        game.updateCurrentPlayer();
        Assert.assertNotEquals(temp1,game.getCurrentPlayer());

        Player temp2 = game.getCurrentPlayer();
        game.updateCurrentPlayer();
        Assert.assertNotEquals(temp1,game.getCurrentPlayer());
        Assert.assertNotEquals(temp2,game.getCurrentPlayer());

        game.updateCurrentPlayer();
        Assert.assertEquals(temp1,game.getCurrentPlayer());

        game.setEndGame();

        temp1 = game.getCurrentPlayer();
        game.updateCurrentPlayer();
        Assert.assertNotEquals(temp1,game.getCurrentPlayer());

        temp2 = game.getCurrentPlayer();
        game.updateCurrentPlayer();
        Assert.assertNotEquals(temp1,game.getCurrentPlayer());
        Assert.assertNotEquals(temp2,game.getCurrentPlayer());

        try {
            game.updateCurrentPlayer();
        }catch (Exception ignored){
// se viene tolta o modificata l'eccezione, bisogna rivedere questa parte
        }
        Assert.assertNotEquals(temp1,game.getCurrentPlayer());
    }
}
