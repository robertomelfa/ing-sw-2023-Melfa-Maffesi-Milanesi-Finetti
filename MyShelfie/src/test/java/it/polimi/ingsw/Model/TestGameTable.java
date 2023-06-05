package it.polimi.ingsw.Model;

import it.polimi.ingsw.Model.Card;
import it.polimi.ingsw.Model.Game;
import it.polimi.ingsw.Model.GameLogic;
import it.polimi.ingsw.Model.Player;
import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;

import static it.polimi.ingsw.Model.Card.*;

public class TestGameTable extends TestCase {

    /*
        i'm testing the refill method using the constructor of GameTable because it firstly sets all the positions on the table to NONE
        and then calls refill.
        Also because refill is private
     */
    @Test
    public void testrefill_usingcontructor2player() throws Exception{
        Game g1=new Game(2);
        GameLogic logic=new GameLogic(g1);
        Player p1=new Player("Player 1");
        g1.setCurrentPlayer(p1);
        ArrayList<Card> result = new ArrayList<>();
        StringBuilder builder=new StringBuilder();
        builder.append("1\n2\n4\n");
        InputStream input = new ByteArrayInputStream(builder.toString().getBytes());
        System.setIn(input);
        result=logic.getCardFromTable();
        assertTrue(result.size()!=0);
    }

    @Test
    public void testrefill_usingcontructor3player() throws Exception{
        Game g1=new Game(3);
        GameLogic logic=new GameLogic(g1);
        Player p1=new Player("Player 1");
        g1.setCurrentPlayer(p1);
        ArrayList<Card> result = new ArrayList<>();
        StringBuilder builder=new StringBuilder();
        builder.append("1\n1\n4\n");
        InputStream input = new ByteArrayInputStream(builder.toString().getBytes());
        System.setIn(input);
        result=logic.getCardFromTable();
        assertTrue(result.size()!=0);
    }

    @Test
    public void testrefill_usingcontructor4player() throws Exception{
        Game g1=new Game(4);
        GameLogic logic=new GameLogic(g1);
        Player p1=new Player("Player 1");
        g1.setCurrentPlayer(p1);
        ArrayList<Card> result = new ArrayList<>();
        StringBuilder builder=new StringBuilder();
        builder.append("1\n1\n5\n");
        InputStream input = new ByteArrayInputStream(builder.toString().getBytes());
        System.setIn(input);
        result=logic.getCardFromTable();
        assertTrue(result.size()!=0);
    }

    @Test
    public void testCheckStatus_emptyboard_refill() throws Exception{
        GameTable gameTable = new GameTable(2);
        for(int i = 0; i < 11; i++){
            for(int j = 0; j < 11; j++){
                if(gameTable.getCardfromBoard(i, j)!= NOT){
                    gameTable.setCardfromBoard(i, j, NONE);
                }
            }
        }

        gameTable.setCardfromBoard(2, 3, YELLOW1);
        gameTable.setCardfromBoard(2, 5, YELLOW1);
        gameTable.setCardfromBoard(3, 6, YELLOW1);
        gameTable.setCardfromBoard(4, 5, YELLOW1);

        gameTable.checkStatus();

        Assert.assertTrue(gameTable.getCardfromBoard(8, 5) != NONE);
        Assert.assertTrue(gameTable.getCardfromBoard(8, 6) != NONE);
        Assert.assertTrue(gameTable.getCardfromBoard(2, 4) != NONE);
        Assert.assertTrue(gameTable.getCardfromBoard(8, 4) == NOT);
        Assert.assertTrue(gameTable.getCardfromBoard(1, 1) == NOT);
    }



}
