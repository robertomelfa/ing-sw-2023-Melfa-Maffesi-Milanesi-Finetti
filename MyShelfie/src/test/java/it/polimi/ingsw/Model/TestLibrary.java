package it.polimi.ingsw.Model;

import it.polimi.ingsw.Model.Card;
import it.polimi.ingsw.Model.Library;
import junit.framework.TestCase;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;

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
    public void testcheckFull_fullblue_true(){
        for(int i=1; i<=5;i++){
            StringBuilder builder = new StringBuilder();
            builder.append(i+"\n1\n1\n1\n");
            for (int k=0;k<2;k++){
                ArrayList<Card> cards=new ArrayList<>();
                cards.add(Card.BLUE);
                cards.add(Card.BLUE);
                cards.add(Card.BLUE);
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
                cards.add(Card.LIGHTBLUE);
                cards.add(Card.PURPLE);
                cards.add(Card.WHITE);
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
        cards.add(Card.YELLOW);
        cards.add(Card.PURPLE);
        cards.add(Card.WHITE);
        StringBuilder builder = new StringBuilder();
        builder.append("1\n1\n1\n1\n");
        InputStream input=new ByteArrayInputStream(builder.toString().getBytes());
        System.setIn(input);
        lib.insert(cards);
        Assert.assertTrue(lib.getPos(5, 0) == Card.YELLOW && lib.getPos(4, 0) == Card.PURPLE && lib.getPos(3, 0) == Card.WHITE);
    }

    @Test
    public void testinsert_lastcolumn() throws Exception{
        ArrayList<Card> cards=new ArrayList<>();
        cards.add(Card.YELLOW);
        cards.add(Card.PURPLE);
        cards.add(Card.WHITE);
        StringBuilder builder = new StringBuilder();
        builder.append("5\n1\n1\n1\n");
        InputStream input=new ByteArrayInputStream(builder.toString().getBytes());
        System.setIn(input);
        lib.insert(cards);
        Assert.assertTrue(lib.getPos(5, 4) == Card.YELLOW && lib.getPos(4, 4) == Card.PURPLE && lib.getPos(3, 4) == Card.WHITE);
    }

    @Test
    public void test_column_full() throws Exception{
        for(int i = 0; i < 6; i++){
            lib.setCard(i, 0, Card.GREEN);
        }
        Assert.assertTrue(lib.numberOfCards(3));

        // can't pick 1 card
        for(int i = 0; i < 6; i++){
            for(int j = 0; j < 5; j++){
                lib.setCard(i, j, Card.GREEN);
            }
        }
        Assert.assertFalse(lib.numberOfCards(1));
    }

    @Test
    public void testCheckFinal (){

        testcheckFull_fullblue_true();
        Assert.assertEquals(8, lib.checkFinal());

        testConstructor_allgridNONE();
        Assert.assertEquals(0, lib.checkFinal());



    }
    @After
    public void teardown(){
        lib=null;
    }

}