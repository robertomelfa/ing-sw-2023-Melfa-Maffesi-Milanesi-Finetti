package it.polimi.ingsw;

import it.polimi.ingsw.Model.Card;
import it.polimi.ingsw.Model.Game;
import it.polimi.ingsw.Model.GameLogic;
import it.polimi.ingsw.Model.Player;
import junit.framework.TestCase;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;

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
        Player p2=new Player("Player 2");
        g1.addNewPlayer(p1);
        g1.addNewPlayer(p2);
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
        Player p2=new Player("Player 2");
        Player p3=new Player("Player 3");
        g1.addNewPlayer(p1);
        g1.addNewPlayer(p2);
        g1.addNewPlayer(p3);
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
        Player p2=new Player("Player 2");
        Player p3=new Player("Player 3");
        Player p4=new Player("Player 4");
        g1.addNewPlayer(p1);
        g1.addNewPlayer(p2);
        g1.addNewPlayer(p3);
        g1.addNewPlayer(p4);
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
        Game g1=new Game(2);
        GameLogic logic=new GameLogic(g1);
        Player p1=new Player("Player 1");
        Player p2=new Player("Player 2");
        g1.addNewPlayer(p1);
        g1.addNewPlayer(p2);

        StringBuilder builder=new StringBuilder();
        builder.append("2\n2\n4\n2\n5\n");
        InputStream input = new ByteArrayInputStream(builder.toString().getBytes());
        ArrayList<Card> result = new ArrayList<>();
        System.setIn(input);
        result=logic.getCardFromTable();

        builder=new StringBuilder();
        builder.append("3\n3\n3\n3\n4\n3\n5\n");
        input = new ByteArrayInputStream(builder.toString().getBytes());
        result = new ArrayList<>();
        System.setIn(input);
        result=logic.getCardFromTable();

        builder=new StringBuilder();
        builder.append("1\n3\n6\n");
        input = new ByteArrayInputStream(builder.toString().getBytes());
        result = new ArrayList<>();
        System.setIn(input);
        result=logic.getCardFromTable();

        builder=new StringBuilder();
        builder.append("3\n4\n2\n4\n3\n4\n4\n");
        input = new ByteArrayInputStream(builder.toString().getBytes());
        result = new ArrayList<>();
        System.setIn(input);
        result=logic.getCardFromTable();

        builder=new StringBuilder();
        builder.append("3\n4\n5\n4\n6\n4\n7\n");
        input = new ByteArrayInputStream(builder.toString().getBytes());
        result = new ArrayList<>();
        System.setIn(input);
        result=logic.getCardFromTable();

        builder=new StringBuilder();
        builder.append("1\n4\n8\n");
        input = new ByteArrayInputStream(builder.toString().getBytes());
        result = new ArrayList<>();
        System.setIn(input);
        result=logic.getCardFromTable();

        builder=new StringBuilder();
        builder.append("3\n5\n1\n5\n2\n5\n3\n");
        input = new ByteArrayInputStream(builder.toString().getBytes());
        result = new ArrayList<>();
        System.setIn(input);
        result=logic.getCardFromTable();

        builder=new StringBuilder();
        builder.append("3\n5\n4\n5\n5\n5\n6\n");
        input = new ByteArrayInputStream(builder.toString().getBytes());
        result = new ArrayList<>();
        System.setIn(input);
        result=logic.getCardFromTable();

        builder=new StringBuilder();
        builder.append("2\n5\n7\n5\n8\n");
        input = new ByteArrayInputStream(builder.toString().getBytes());
        result = new ArrayList<>();
        System.setIn(input);
        result=logic.getCardFromTable();

        builder=new StringBuilder();
        builder.append("3\n6\n2\n6\n3\n6\n4\n");
        input = new ByteArrayInputStream(builder.toString().getBytes());
        result = new ArrayList<>();
        System.setIn(input);
        result=logic.getCardFromTable();

        builder=new StringBuilder();
        builder.append("3\n6\n5\n6\n6\n6\n7\n");
        input = new ByteArrayInputStream(builder.toString().getBytes());
        result = new ArrayList<>();
        System.setIn(input);
        result=logic.getCardFromTable();

        builder=new StringBuilder();
        builder.append("3\n7\n4\n7\n5\n7\n6\n");
        input = new ByteArrayInputStream(builder.toString().getBytes());
        result = new ArrayList<>();
        System.setIn(input);
        result=logic.getCardFromTable();

        builder=new StringBuilder();
        builder.append("3\n8\n4\n8\n5\n8\n6\n");
        input = new ByteArrayInputStream(builder.toString().getBytes());
        result = new ArrayList<>();
        System.setIn(input);
        result=logic.getCardFromTable();



        g1.getGameTable().checkStatus();


        builder=new StringBuilder();
        builder.append("2\n2\n4\n2\n5\n");
        input = new ByteArrayInputStream(builder.toString().getBytes());
        result = new ArrayList<>();
        System.setIn(input);
        result=logic.getCardFromTable();
        boolean condition=true;
        for (int i=0; i<result.size();i++){
            if(result.get(i)==Card.NONE){
                condition=false;
            }
        }
        assertTrue(condition);

    }



}
