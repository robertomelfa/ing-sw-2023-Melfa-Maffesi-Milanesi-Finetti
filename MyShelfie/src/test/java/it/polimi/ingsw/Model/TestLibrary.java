package it.polimi.ingsw.Model;

import it.polimi.ingsw.View.CLI.CLIView;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;

import static it.polimi.ingsw.Model.Card.*;


/**
 * Unit test for Library.
 */
public class TestLibrary {
    Library lib=null;
    @Before
    public void setUp(){
        lib = new Library();
    }

    @Test
    public void testConstructor_allgridNONE(){
        for (int i=0;i<6;i++){
            for (int j=0;j<5;j++){
                try{
                    Assert.assertSame(lib.getPos(i, j), Card.NONE);
                }catch (Exception e){

                }
            }
        }
    }

    @Test
    public void testgetPosException_00_NONE() throws Exception{
        Assert.assertSame(lib.getPos(0, 0), Card.NONE);
    }

    @Test
    public void testgetPosException_76_exception(){
        Assert.assertSame(lib.getPos(7, 6), Card.NOT);

    }

    @Test
    public void testcheckFull_notfull_false(){
        Assert.assertFalse(lib.checkFull());
    }

    @Test
    public void testcheckFull_fullBLUE1_true(){
        for(int i=1; i<=5;i++){
            StringBuilder builder = new StringBuilder();
            builder.append(i+"\n1\n1\n1\n");
            for (int k=0;k<2;k++){
                ArrayList<Card> cards=new ArrayList<>();
                cards.add(Card.BLUE1);
                cards.add(Card.BLUE2);
                cards.add(Card.BLUE3);
                InputStream input=new ByteArrayInputStream(builder.toString().getBytes());
                System.setIn(input);
                lib.insert(cards);
            }


        }
        Assert.assertTrue(lib.checkFull());
    }

    @Test
    public void testcheckFull_fullmixedcolours_true(){
        for(int i=1; i<=5;i++){
            StringBuilder builder = new StringBuilder();
            builder.append(i+"\n1\n1\n1\n");
            for (int k=0;k<2;k++){
                ArrayList<Card> cards=new ArrayList<>();
                cards.add(Card.LIGHTBLUE1);
                cards.add(Card.PURPLE2);
                cards.add(Card.WHITE3);
                Collections.shuffle(cards);
                InputStream input=new ByteArrayInputStream(builder.toString().getBytes());
                System.setIn(input);
                lib.insert(cards);
            }


        }
        Assert.assertTrue(lib.checkFull());
    }

    @Test
    public void testinsert_firstcolumn() throws Exception{
        ArrayList<Card> cards=new ArrayList<>();
        cards.add(Card.YELLOW3);
        cards.add(Card.PURPLE2);
        cards.add(Card.WHITE2);
        StringBuilder builder = new StringBuilder();
        builder.append("1\n1\n1\n1\n");
        InputStream input=new ByteArrayInputStream(builder.toString().getBytes());
        System.setIn(input);
        lib.insert(cards);
        Assert.assertTrue(lib.getPos(5, 0) == Card.YELLOW3 && lib.getPos(4, 0) == Card.PURPLE2 && lib.getPos(3, 0) == Card.WHITE2);
    }

    @Test
    public void testinsert_lastcolumn() throws Exception{
        ArrayList<Card> cards=new ArrayList<>();
        cards.add(Card.YELLOW1);
        cards.add(Card.PURPLE1);
        cards.add(Card.WHITE1);
        StringBuilder builder = new StringBuilder();
        builder.append("5\n1\n1\n1\n");
        InputStream input=new ByteArrayInputStream(builder.toString().getBytes());
        System.setIn(input);
        lib.insert(cards);
        Assert.assertTrue(lib.getPos(5, 4) == Card.YELLOW1 && lib.getPos(4, 4) == Card.PURPLE1 && lib.getPos(3, 4) == Card.WHITE1);
    }

    @Test
    public void test_column_full() throws Exception{
        for(int i = 0; i < 6; i++){
            lib.setCard(i, 0, Card.GREEN1);
        }
        Assert.assertTrue(lib.numberOfCards(3));

        // can't pick 1 card
        for(int i = 0; i < 6; i++){
            for(int j = 0; j < 5; j++){
                lib.setCard(i, j, Card.GREEN1);
            }
        }
        Assert.assertFalse(lib.numberOfCards(1));
    }

    @Test
    public void testCheckFinal (){

        testcheckFull_fullBLUE1_true();
        Assert.assertEquals(8, lib.checkFinal());

        testConstructor_allgridNONE();
        Assert.assertEquals(0, lib.checkFinal());



    }
    @Test
    public void testCheckNumCardsRemain() throws Exception{
        Player p1 = new Player("bob");
        p1.getLibrary().setCard(1, 0, PURPLE2);
        p1.getLibrary().setCard(2, 0, WHITE3);
        p1.getLibrary().setCard(3, 0, LIGHTBLUE1);
        p1.getLibrary().setCard(4, 0, LIGHTBLUE3);
        p1.getLibrary().setCard(5, 0, LIGHTBLUE2);

        p1.getLibrary().setCard(0, 1, PURPLE2);
        p1.getLibrary().setCard(1, 1, PURPLE3);
        p1.getLibrary().setCard(2, 1, WHITE2);
        p1.getLibrary().setCard(3, 1, YELLOW3);
        p1.getLibrary().setCard(4, 1, LIGHTBLUE1);
        p1.getLibrary().setCard(5, 1, LIGHTBLUE2);

        p1.getLibrary().setCard(0, 2, PURPLE3);
        p1.getLibrary().setCard(1, 2, PURPLE2);
        p1.getLibrary().setCard(2, 2, BLUE3);
        p1.getLibrary().setCard(3, 2, LIGHTBLUE1);
        p1.getLibrary().setCard(4, 2, GREEN1);
        p1.getLibrary().setCard(5, 2, LIGHTBLUE1);

        p1.getLibrary().setCard(1, 3, GREEN1);
        p1.getLibrary().setCard(2, 3, WHITE2);
        p1.getLibrary().setCard(3, 3, YELLOW1);
        p1.getLibrary().setCard(4, 3, GREEN3);
        p1.getLibrary().setCard(5, 3, GREEN1);

        p1.getLibrary().setCard(4, 4, GREEN2);
        p1.getLibrary().setCard(5, 4, GREEN1);

        Assert.assertTrue(p1.getLibrary().checkNumCardsRemain(3));

        p1.getLibrary().setCard(3, 4, GREEN1);
        p1.getLibrary().setCard(2, 4, GREEN3);

        Assert.assertFalse(p1.getLibrary().checkNumCardsRemain(3));
        Assert.assertTrue(p1.getLibrary().checkNumCardsRemain(2));

        p1.getLibrary().setCard(1, 4, GREEN1);

        Assert.assertFalse(p1.getLibrary().checkNumCardsRemain(2));
        Assert.assertFalse(p1.getLibrary().checkNumCardsRemain(3));
        Assert.assertTrue(p1.getLibrary().checkNumCardsRemain(1));

    }

    /**
     * Check library with 18 points of final groups. It is a particular case for the six group of purple and yellow cards
     * @throws Exception
     */
    @Test
    public void testCheckFinal2() throws Exception{
        Player p1 = new Player("bob");

        p1.getLibrary().setCard(0, 0, GREEN2);
        p1.getLibrary().setCard(0, 1, YELLOW1);
        p1.getLibrary().setCard(0, 2, YELLOW3);
        p1.getLibrary().setCard(0, 3, PURPLE1);
        p1.getLibrary().setCard(0, 4, LIGHTBLUE1);

        p1.getLibrary().setCard(1, 0, YELLOW2);
        p1.getLibrary().setCard(1, 1, YELLOW1);
        p1.getLibrary().setCard(1, 2, YELLOW3);
        p1.getLibrary().setCard(1, 3, BLUE1);
        p1.getLibrary().setCard(1, 4, WHITE2);

        p1.getLibrary().setCard(2, 0, YELLOW2);
        p1.getLibrary().setCard(2, 1, PURPLE1);
        p1.getLibrary().setCard(2, 2, BLUE2);
        p1.getLibrary().setCard(2, 3, PURPLE1);
        p1.getLibrary().setCard(2, 4, YELLOW2);

        p1.getLibrary().setCard(3, 0, LIGHTBLUE1);
        p1.getLibrary().setCard(3, 1, GREEN2);
        p1.getLibrary().setCard(3, 2, PURPLE3);
        p1.getLibrary().setCard(3, 3, GREEN3);
        p1.getLibrary().setCard(3, 4, PURPLE3);

        p1.getLibrary().setCard(4, 0, YELLOW2);
        p1.getLibrary().setCard(4, 1, BLUE3);
        p1.getLibrary().setCard(4, 2, PURPLE3);
        p1.getLibrary().setCard(4, 3, PURPLE1);
        p1.getLibrary().setCard(4, 4, PURPLE2);

        p1.getLibrary().setCard(5, 0, YELLOW3);
        p1.getLibrary().setCard(5, 1, YELLOW1);
        p1.getLibrary().setCard(5, 2, PURPLE3);
        p1.getLibrary().setCard(5, 3, WHITE1);
        p1.getLibrary().setCard(5, 4, YELLOW3);

        CLIView view = new CLIView();
        view.viewLibrary(p1.getLibrary());

        Assert.assertEquals(p1.getLibrary().checkFinal() , 18);
    }
    @After
    public void teardown(){
        lib=null;
    }

}