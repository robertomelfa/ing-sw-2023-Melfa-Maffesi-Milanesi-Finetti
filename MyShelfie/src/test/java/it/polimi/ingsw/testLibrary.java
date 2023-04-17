package it.polimi.ingsw;

import junit.framework.TestCase;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.Assert.*;

import javax.sound.sampled.AudioInputStream;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Unit test for Library.
 */
public class testLibrary extends TestCase
{
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
                    assertTrue(lib.getPos(i,j)==Card.NONE);
                }catch (Exception e){

                }
            }
        }
    }

    @Test
    public void testgetPosException_00_NONE() throws Exception{
        assertTrue(lib.getPos(0,0) == Card.NONE);
    }

    @Test
    public void testgetPosException_76_exception(){
        Assert.assertTrue(lib.getPos(7,6)==Card.NOT);

    }

    @Test
    public void testcheckFull_notfull_false(){
        assertTrue(lib.checkFull()==false);
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
        assertTrue(lib.checkFull()==true);
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
        assertTrue(lib.checkFull()==true);
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
        assertTrue(lib.getPos(5,0)==Card.YELLOW && lib.getPos(4,0)==Card.PURPLE && lib.getPos(3,0)==Card.WHITE);
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
        assertTrue(lib.getPos(5,4)==Card.YELLOW && lib.getPos(4,4)==Card.PURPLE && lib.getPos(3,4)==Card.WHITE);
    }

    @After
    public void teardown(){
        lib=null;
    }

}
