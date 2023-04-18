package it.polimi.ingsw;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class TestPlayer {
    Player p1=null;

    @Before
    public void setUp() throws Exception{
        p1 = new Player("player1");
    }

    @Test
    public void testConstructor_checkattributes(){
        Assert.assertTrue(p1.getNickname().equals("player1") && p1.getScore() == 0 && !p1.getLibrary().checkFull()); //library all NONE is checked in testLibrary class
    }

    @Test
    public void testaddPoints_10_10() throws Exception{
        p1.addPoints(10);
        Assert.assertTrue(p1.getScore() == 10);

    }

    @Test
    public void testaddPoints_multiplevalues_10() throws Exception{
        p1.addPoints(10);
        p1.addPoints(5);
        p1.addPoints(40);
        p1.addPoints(5);
        Assert.assertTrue(p1.getScore() == 60);

    }

    @Test
    public void testaddPoints_0_added0points() throws Exception{
        p1.addPoints(0);
        Assert.assertTrue(p1.getScore()==0);

    }

    @Test
    public void testaddPoints_negativevalue_exception(){
        try{
            p1.addPoints(-10);
            Assert.fail("Expected exception");
        }catch (Exception e){

        }
    }

    @Test
    public void testgetNickname(){
        Assert.assertTrue(p1.getNickname() == "player1");
    }

    @Test
    public void testgetScore() throws Exception{
        p1.addPoints(20);
        p1.addPoints(10);
        Assert.assertTrue(p1.getScore() == 30);
    }

    @After
    public void teardown(){
        p1=null;
    }
}
