package it.polimi.ingsw;

import it.polimi.ingsw.Model.Card;
import it.polimi.ingsw.Model.Game;
import it.polimi.ingsw.Model.GameLogic;
import it.polimi.ingsw.Model.Player;
import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.ArrayList;

public class TestGameLogic {


    @Test
    public void testgetCardFromTable_1card_validcards() throws Exception{
        Game g1=new Game(2);
        GameLogic gameLogic=new GameLogic(g1);
        Player p1=new Player("Player 1");
        g1.setCurrentPlayer(p1);
        //setchairorder
        StringBuilder builder=new StringBuilder();
        builder.append("1\n2\n4\n");
        InputStream input = new ByteArrayInputStream(builder.toString().getBytes());
        //reading from console output
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        PrintStream print = new PrintStream(output);
        //saving old output
        PrintStream old = System.out;
        System.setOut(print);

        System.setIn(input);

        ArrayList<Card> result=new ArrayList<>();
        result=gameLogic.getCardFromTable();
        System.out.flush();
        System.setOut(old);
        System.out.println("Out from console"+output.toString());
        Assert.assertTrue(result.size() == 1 && output.toString().contains(result.get(0).toString()));
    }


    @Test
    public void testgetCardFromTable_2card_validcards() throws Exception{
        Game g1=new Game(2);
        GameLogic gameLogic=new GameLogic(g1);
        Player p1=new Player("Player 1");
        g1.setCurrentPlayer(p1);
        StringBuilder builder=new StringBuilder();
        builder.append("2\n2\n4\n2\n5");
        InputStream input = new ByteArrayInputStream(builder.toString().getBytes());
        //reading from console output
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        PrintStream print = new PrintStream(output);
        //saving old output
        PrintStream old = System.out;
        System.setOut(print);

        System.setIn(input);

        ArrayList<Card> result=new ArrayList<>();
        result=gameLogic.getCardFromTable();
        System.out.flush();
        System.setOut(old);
        System.out.println("Out from console"+output.toString());
        boolean containsColor=true;
        for (int i=0; i<result.size();i++){
            if(!output.toString().contains(result.get(i).toString())){
                containsColor=false;
            }
        }

        Assert.assertTrue(result.size() == 2 && containsColor);
    }


    @Test
    public void testgetCardFromTable_3card_validcards() throws Exception{
        Game g1=new Game(2);
        GameLogic gameLogic=new GameLogic(g1);
        Player p1=new Player("Player 1");
        g1.setCurrentPlayer(p1);
        StringBuilder builder=new StringBuilder();
        builder.append("3\n8\n4\n8\n5\n8\n6");
        InputStream input = new ByteArrayInputStream(builder.toString().getBytes());
        //reading from console output
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        PrintStream print = new PrintStream(output);
        //saving old output
        PrintStream old = System.out;
        System.setOut(print);

        System.setIn(input);

        ArrayList<Card> result=new ArrayList<>();
        result=gameLogic.getCardFromTable();
        System.out.flush();
        System.setOut(old);
        System.out.println("Out from console"+output.toString());
        boolean containsColor=true;
        for (int i=0; i<result.size();i++){
            if(!output.toString().contains(result.get(i).toString())){
                containsColor=false;
            }
        }

        Assert.assertTrue(result.size() == 3 && containsColor);
    }


    @Test
    public void testgetCardFromTable_1card_notvalidcards() throws Exception{
        Game g1=new Game(2);
        GameLogic gameLogic=new GameLogic(g1);
        Player p1=new Player("Player 1");
        g1.setCurrentPlayer(p1);
        StringBuilder builder=new StringBuilder();
        builder.append("1\n3\n4\n3\n3");
        InputStream input = new ByteArrayInputStream(builder.toString().getBytes());
        //reading from console output
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        PrintStream print = new PrintStream(output);
        //saving old output
        PrintStream old = System.out;
        System.setOut(print);

        System.setIn(input);

        ArrayList<Card> result=new ArrayList<>();
        result=gameLogic.getCardFromTable();
        System.out.flush();
        System.setOut(old);
        System.out.println("Out from console"+output.toString());
        Assert.assertTrue(output.toString().contains("Impossible to draw the card"));
    }


    @Test
    public void testgetCardFromTable_2card_notvalidcards() throws Exception{
        Game g1=new Game(2);
        GameLogic gameLogic=new GameLogic(g1);
        Player p1=new Player("Player 1");
        g1.setCurrentPlayer(p1);
        StringBuilder builder=new StringBuilder();
        builder.append("2\n4\n4\n4\n5\n2\n4\n2\n5");
        InputStream input = new ByteArrayInputStream(builder.toString().getBytes());
        //reading from console output
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        PrintStream print = new PrintStream(output);
        //saving old output
        PrintStream old = System.out;
        System.setOut(print);

        System.setIn(input);

        ArrayList<Card> result=new ArrayList<>();
        result=gameLogic.getCardFromTable();
        System.out.flush();
        System.setOut(old);
        System.out.println("Out from console"+output.toString());
        Assert.assertTrue(output.toString().contains("Invalid coordinates, try again!"));
    }


    @Test
    public void testgetCardFromTable_3card_notvalidcards() throws Exception{
        Game g1=new Game(2);
        GameLogic gameLogic=new GameLogic(g1);
        Player p1=new Player("Player 1");
        g1.setCurrentPlayer(p1);
        StringBuilder builder=new StringBuilder();
        builder.append("3\n4\n3\n4\n4\n4\n5\n8\n4\n8\n5\n8\n6");
        InputStream input = new ByteArrayInputStream(builder.toString().getBytes());
        //reading from console output
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        PrintStream print = new PrintStream(output);
        //saving old output
        PrintStream old = System.out;
        System.setOut(print);

        System.setIn(input);

        ArrayList<Card> result=new ArrayList<>();
        result=gameLogic.getCardFromTable();
        System.out.flush();
        System.setOut(old);
        System.out.println("Out from console"+output.toString());
        Assert.assertTrue(output.toString().contains("Invalid coordinates, try again!"));
    }

}
