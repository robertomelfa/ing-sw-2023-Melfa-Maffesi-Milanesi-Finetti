package it.polimi.ingsw;

import junit.framework.TestCase;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.Assert.*;

import javax.sound.sampled.AudioInputStream;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Unit test for Library.
 */
public class testLibrary extends TestCase
{
    Library lib=null;
    @Before
    public void setUp(){
        lib =new Library();
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
        assertTrue(lib.getPos(0,0)==Card.NONE);
    }

    @Test
    public void testgetPosException_76_exception(){
        try{
            lib.getPos(7,6);
            fail("Expected Exception");
        }catch (Exception e){

        }

    }

    @Test
    public void testcheckFull_notfull_false(){
        assertTrue(lib.checkFull()==false);
    }

    @Test
    public void testcheckFull_full_true(){
        ArrayList<Card> cards=new ArrayList<>();
        cards.add(Card.BLUE);
        cards.add(Card.BLUE);
        cards.add(Card.BLUE);
        StringBuilder builder = new StringBuilder();
        for(int i=1; i<=5;i++){
            builder.append(i);
            InputStream input=new ByteArrayInputStream(builder.toString().getBytes());
            String pos= "1";
            InputStream input2 = new ByteArrayInputStream(pos.getBytes());
            System.setIn(input);
            lib.insert(cards);
            System.setIn(input2);
            System.setIn(input2);
            System.setIn(input2);
            System.setIn(input);
            lib.insert(cards);
            System.setIn(input2);
            System.setIn(input2);
            System.setIn(input2);
            builder.deleteCharAt(0);
        }
        assertTrue(lib.checkFull()==true);
    }



    @After
    public void teardown(){
        lib=null;
    }

}
