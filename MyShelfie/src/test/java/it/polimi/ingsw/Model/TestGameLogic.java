package it.polimi.ingsw.Model;

import it.polimi.ingsw.View.CLI.CLIView;
import org.junit.Assert;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.ArrayList;

import static it.polimi.ingsw.Model.Card.NONE;

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
        gameLogic.getGame().getGameTable().setCardfromBoard(8,5,NONE);
        builder.append("3\n7\n4\n7\n5\n7\n6");
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
        builder.append("1\n5\n1\n2\n4");
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
        gameLogic.getGame().getGameTable().setCardfromBoard(8,5,NONE);
        builder.append("3\n4\n3\n4\n4\n4\n5\n7\n4\n7\n5\n7\n6");
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
    public void checkPickableCardTest(){
        Game g1=new Game(2);
        GameLogic gameLogic=new GameLogic(g1);
        CLIView view = new CLIView();
        for(int i = 0; i < 11; i++){
            for(int j = 0; j < 11; j++){
                gameLogic.getGameTable().setCardfromBoard(i,j, NONE);
            }
        }
        Assert.assertFalse(gameLogic.checkCardsPickable(3));

        gameLogic.getGameTable().setCardfromBoard(5,5,Card.YELLOW1);
        gameLogic.getGameTable().setCardfromBoard(5,6,Card.YELLOW1);
        gameLogic.getGameTable().setCardfromBoard(4,5,Card.YELLOW1);
        view.viewGameTable(gameLogic.getGameTable());

        Assert.assertFalse(gameLogic.checkCardsPickable(3));

        for(int i = 0; i < 11; i++){
            for(int j = 0; j < 11; j++){
                gameLogic.getGameTable().setCardfromBoard(i,j, NONE);
            }
        }
        gameLogic.getGameTable().setCardfromBoard(1,7,Card.YELLOW1);
        gameLogic.getGameTable().setCardfromBoard(1,8,Card.YELLOW1);
        gameLogic.getGameTable().setCardfromBoard(1,9,Card.YELLOW1);
        view.viewGameTable(gameLogic.getGameTable());

        Assert.assertTrue(gameLogic.checkCardsPickable(3));

        for(int i = 0; i < 11; i++){
            for(int j = 0; j < 11; j++){
                gameLogic.getGameTable().setCardfromBoard(i,j, NONE);
            }
        }
        gameLogic.getGameTable().setCardfromBoard(6,3,Card.YELLOW1);
        gameLogic.getGameTable().setCardfromBoard(5,3,Card.YELLOW1);
        gameLogic.getGameTable().setCardfromBoard(4,3,Card.YELLOW1);
        view.viewGameTable(gameLogic.getGameTable());

        Assert.assertTrue(gameLogic.checkCardsPickable(3));

        Game g2=new Game(3);
        GameLogic gameLogic1=new GameLogic(g2);
        view.viewGameTable(gameLogic1.getGameTable());
        Assert.assertFalse(gameLogic1.checkCardsPickable(3));
        gameLogic1.getGameTable().setCardfromBoard(8,7,Card.YELLOW1);
        gameLogic1.getGameTable().setCardfromBoard(6,7, NONE);
        view.viewGameTable(gameLogic1.getGameTable());
        Assert.assertFalse(gameLogic1.checkCardsPickable(3));

        Game g3=new Game(4);
        GameLogic gameLogic2=new GameLogic(g3);
        view.viewGameTable(gameLogic2.getGameTable());
        Assert.assertFalse(gameLogic2.checkCardsPickable(3));

        Game g4=new Game(2);
        GameLogic gameLogic3=new GameLogic(g4);
        view.viewGameTable(gameLogic3.getGameTable());
        Assert.assertTrue(gameLogic3.checkCardsPickable(2));

        Assert.assertTrue(gameLogic3.checkCardsPickable(1));

    }

}
