package it.polimi.ingsw.Model;

import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import static it.polimi.ingsw.Model.Card.*;


public class TestCommonObj extends TestCase{

    Player p1=null;

    @Before
    public void setUp() throws Exception{
        p1 = new Player("player1");
    }

    @Test
    public void test_check1() throws Exception{

        CommonObj obj = new CommonObj(2, 1);

        p1.getLibrary().setCard(1, 0, PURPLE);
        p1.getLibrary().setCard(2, 0, WHITE);
        p1.getLibrary().setCard(3, 0, LIGHTBLUE);
        p1.getLibrary().setCard(4, 0, LIGHTBLUE);
        p1.getLibrary().setCard(5, 0, LIGHTBLUE);

        p1.getLibrary().setCard(0, 1, PURPLE);
        p1.getLibrary().setCard(1, 1, PURPLE);
        p1.getLibrary().setCard(2, 1, WHITE);
        p1.getLibrary().setCard(3, 1, YELLOW);
        p1.getLibrary().setCard(4, 1, LIGHTBLUE);
        p1.getLibrary().setCard(5, 1, LIGHTBLUE);

        p1.getLibrary().setCard(0, 2, PURPLE);
        p1.getLibrary().setCard(1, 2, PURPLE);
        p1.getLibrary().setCard(2, 2, BLUE);
        p1.getLibrary().setCard(3, 2, LIGHTBLUE);
        p1.getLibrary().setCard(4, 2, GREEN);
        p1.getLibrary().setCard(5, 2, LIGHTBLUE);

        p1.getLibrary().setCard(1, 3, GREEN);
        p1.getLibrary().setCard(2, 3, WHITE);
        p1.getLibrary().setCard(3, 3, YELLOW);
        p1.getLibrary().setCard(4, 3, GREEN);
        p1.getLibrary().setCard(5, 3, GREEN);

        p1.getLibrary().setCard(4, 4, GREEN);
        p1.getLibrary().setCard(5, 4, GREEN);

        p1.getLibrary().viewGrid();
        Assert.assertFalse(obj.checkObj(p1.getLibrary()));
        p1.getLibrary().reset_lib();

        p1.getLibrary().setCard(1, 0, PURPLE);
        p1.getLibrary().setCard(2, 0, WHITE);
        p1.getLibrary().setCard(3, 0, LIGHTBLUE);
        p1.getLibrary().setCard(4, 0, LIGHTBLUE);
        p1.getLibrary().setCard(5, 0, LIGHTBLUE);

        p1.getLibrary().setCard(0, 1, PURPLE);
        p1.getLibrary().setCard(1, 1, PURPLE);
        p1.getLibrary().setCard(2, 1, WHITE);
        p1.getLibrary().setCard(3, 1, YELLOW);
        p1.getLibrary().setCard(4, 1, LIGHTBLUE);
        p1.getLibrary().setCard(5, 1, LIGHTBLUE);

        p1.getLibrary().setCard(0, 2, PURPLE);
        p1.getLibrary().setCard(1, 2, PURPLE);
        p1.getLibrary().setCard(2, 2, BLUE);
        p1.getLibrary().setCard(3, 2, LIGHTBLUE);
        p1.getLibrary().setCard(4, 2, GREEN);
        p1.getLibrary().setCard(5, 2, LIGHTBLUE);

        p1.getLibrary().setCard(0, 3, PURPLE);
        p1.getLibrary().setCard(1, 3, GREEN);
        p1.getLibrary().setCard(2, 3, WHITE);
        p1.getLibrary().setCard(3, 3, YELLOW);
        p1.getLibrary().setCard(4, 3, GREEN);
        p1.getLibrary().setCard(5, 3, GREEN);

        p1.getLibrary().setCard(0, 4, GREEN);
        p1.getLibrary().setCard(1, 4, GREEN);
        p1.getLibrary().setCard(2, 4, WHITE);
        p1.getLibrary().setCard(3, 4, YELLOW);
        p1.getLibrary().setCard(4, 4, GREEN);
        p1.getLibrary().setCard(5, 4, GREEN);

        p1.getLibrary().viewGrid();
        Assert.assertTrue(obj.checkObj(p1.getLibrary()));
        p1.getLibrary().reset_lib();
    }

    @Test
    public void test_check2() throws Exception{

        CommonObj obj = new CommonObj(3, 2);

        // there is no diagonal

        p1.getLibrary().setCard(1, 0, PURPLE);
        p1.getLibrary().setCard(2, 0, WHITE);
        p1.getLibrary().setCard(3, 0, LIGHTBLUE);
        p1.getLibrary().setCard(4, 0, LIGHTBLUE);
        p1.getLibrary().setCard(5, 0, LIGHTBLUE);

        p1.getLibrary().setCard(0, 1, PURPLE);
        p1.getLibrary().setCard(1, 1, PURPLE);
        p1.getLibrary().setCard(2, 1, WHITE);
        p1.getLibrary().setCard(3, 1, YELLOW);
        p1.getLibrary().setCard(4, 1, LIGHTBLUE);
        p1.getLibrary().setCard(5, 1, LIGHTBLUE);

        p1.getLibrary().setCard(0, 2, PURPLE);
        p1.getLibrary().setCard(1, 2, PURPLE);
        p1.getLibrary().setCard(2, 2, BLUE);
        p1.getLibrary().setCard(3, 2, LIGHTBLUE);
        p1.getLibrary().setCard(4, 2, GREEN);
        p1.getLibrary().setCard(5, 2, LIGHTBLUE);

        p1.getLibrary().setCard(1, 3, GREEN);
        p1.getLibrary().setCard(2, 3, WHITE);
        p1.getLibrary().setCard(3, 3, YELLOW);
        p1.getLibrary().setCard(4, 3, GREEN);
        p1.getLibrary().setCard(5, 3, GREEN);

        p1.getLibrary().setCard(4, 4, GREEN);
        p1.getLibrary().setCard(5, 4, GREEN);

        Assert.assertFalse(obj.checkObj(p1.getLibrary()));
        p1.getLibrary().reset_lib();

        // first diagonal

        p1.getLibrary().setCard(5, 0, PURPLE);
        p1.getLibrary().setCard(4, 1, PURPLE);
        p1.getLibrary().setCard(3, 2, PURPLE);
        p1.getLibrary().setCard(2, 3, PURPLE);
        p1.getLibrary().setCard(1, 4, PURPLE);

        Assert.assertTrue(obj.checkObj(p1.getLibrary()));
        p1.getLibrary().reset_lib();

        // second diagonal

        p1.getLibrary().setCard(4, 0, WHITE);
        p1.getLibrary().setCard(3, 1, WHITE);
        p1.getLibrary().setCard(2, 2, WHITE);
        p1.getLibrary().setCard(1, 3, WHITE);
        p1.getLibrary().setCard(0, 4, WHITE);

        Assert.assertTrue(obj.checkObj(p1.getLibrary()));
        p1.getLibrary().reset_lib();

        // third diagonal

        p1.getLibrary().setCard(4, 4, WHITE);
        p1.getLibrary().setCard(3, 3, WHITE);
        p1.getLibrary().setCard(2, 2, WHITE);
        p1.getLibrary().setCard(1, 1, WHITE);
        p1.getLibrary().setCard(0, 0, WHITE);

        Assert.assertTrue(obj.checkObj(p1.getLibrary()));
        p1.getLibrary().reset_lib();

        // fourth case

        p1.getLibrary().setCard(5, 4, GREEN);
        p1.getLibrary().setCard(4, 3, GREEN);
        p1.getLibrary().setCard(3, 2, GREEN);
        p1.getLibrary().setCard(2, 1, GREEN);
        p1.getLibrary().setCard(1, 0, GREEN);

        Assert.assertTrue(obj.checkObj(p1.getLibrary()));
        p1.getLibrary().reset_lib();
    }

    @Test
    public void test_check3() throws Exception{

        CommonObj obj = new CommonObj(2, 3);

        p1.getLibrary().setCard(0, 0, GREEN);
        p1.getLibrary().setCard(5, 0, GREEN);
        p1.getLibrary().setCard(5, 4, GREEN);

        Assert.assertFalse(obj.checkObj(p1.getLibrary()));
        p1.getLibrary().reset_lib();

        p1.getLibrary().setCard(0, 0, GREEN);
        p1.getLibrary().setCard(5, 0, GREEN);
        p1.getLibrary().setCard(5, 4, GREEN);
        p1.getLibrary().setCard(0, 4, WHITE);

        Assert.assertFalse(obj.checkObj(p1.getLibrary()));
        p1.getLibrary().reset_lib();

        p1.getLibrary().setCard(0, 0, GREEN);
        p1.getLibrary().setCard(5, 0, GREEN);
        p1.getLibrary().setCard(5, 4, GREEN);
        p1.getLibrary().setCard(0, 4, GREEN);

        Assert.assertTrue(obj.checkObj(p1.getLibrary()));
        p1.getLibrary().reset_lib();

        p1.getLibrary().setCard(0, 0, GREEN);
        p1.getLibrary().setCard(5, 0, WHITE);
        p1.getLibrary().setCard(5, 4, GREEN);
        p1.getLibrary().setCard(0, 4, GREEN);

        Assert.assertFalse(obj.checkObj(p1.getLibrary()));
        p1.getLibrary().reset_lib();

        p1.getLibrary().setCard(0, 0, GREEN);
        p1.getLibrary().setCard(5, 0, GREEN);
        p1.getLibrary().setCard(5, 4, WHITE);
        p1.getLibrary().setCard(0, 4, GREEN);

        Assert.assertFalse(obj.checkObj(p1.getLibrary()));
        p1.getLibrary().reset_lib();

        p1.getLibrary().setCard(0, 0, WHITE);
        p1.getLibrary().setCard(5, 0, GREEN);
        p1.getLibrary().setCard(5, 4, GREEN);
        p1.getLibrary().setCard(0, 4, GREEN);

        Assert.assertFalse(obj.checkObj(p1.getLibrary()));
        p1.getLibrary().reset_lib();

        p1.getLibrary().setCard(0, 0, NONE);
        p1.getLibrary().setCard(5, 0, GREEN);
        p1.getLibrary().setCard(5, 4, GREEN);
        p1.getLibrary().setCard(0, 4, GREEN);

        Assert.assertFalse(obj.checkObj(p1.getLibrary()));
        p1.getLibrary().reset_lib();
    }

    @Test
    public void test_check4() throws Exception{

        CommonObj obj = new CommonObj(2, 4);

        p1.getLibrary().setCard(0, 0, PURPLE);
        p1.getLibrary().setCard(0, 1, WHITE);
        p1.getLibrary().setCard(0, 2, LIGHTBLUE);
        p1.getLibrary().setCard(0, 3, PURPLE);
        p1.getLibrary().setCard(0, 4, WHITE);

        p1.getLibrary().setCard(2, 0, WHITE);
        p1.getLibrary().setCard(2, 1, WHITE);
        p1.getLibrary().setCard(2, 2, LIGHTBLUE);
        p1.getLibrary().setCard(2, 3, GREEN);
        p1.getLibrary().setCard(2, 4, WHITE);

        p1.getLibrary().setCard(3, 0, PURPLE);
        p1.getLibrary().setCard(3, 1, WHITE);
        p1.getLibrary().setCard(3, 2, LIGHTBLUE);
        p1.getLibrary().setCard(3, 3, PURPLE);
        p1.getLibrary().setCard(3, 4, LIGHTBLUE);

        p1.getLibrary().setCard(4, 0, WHITE);
        p1.getLibrary().setCard(4, 1, WHITE);
        p1.getLibrary().setCard(4, 2, YELLOW);
        p1.getLibrary().setCard(4, 3, YELLOW);
        p1.getLibrary().setCard(4, 4, WHITE);


        Assert.assertTrue(obj.checkObj(p1.getLibrary()));
        p1.getLibrary().reset_lib();

        // test if when card is NONE (rig 167)

        p1.getLibrary().setCard(0, 0, PURPLE);
        p1.getLibrary().setCard(0, 1, WHITE);
        p1.getLibrary().setCard(0, 2, LIGHTBLUE);
        p1.getLibrary().setCard(0, 3, PURPLE);
        p1.getLibrary().setCard(0, 4, WHITE);

        p1.getLibrary().setCard(2, 0, WHITE);
        p1.getLibrary().setCard(2, 1, WHITE);
        p1.getLibrary().setCard(2, 2, LIGHTBLUE);
        p1.getLibrary().setCard(2, 3, GREEN);
        p1.getLibrary().setCard(2, 4, NONE);

        p1.getLibrary().setCard(3, 0, NONE);
        p1.getLibrary().setCard(3, 1, WHITE);
        p1.getLibrary().setCard(3, 2, LIGHTBLUE);
        p1.getLibrary().setCard(3, 3, PURPLE);
        p1.getLibrary().setCard(3, 4, YELLOW);

        p1.getLibrary().setCard(4, 0, WHITE);
        p1.getLibrary().setCard(4, 1, WHITE);
        p1.getLibrary().setCard(4, 2, YELLOW);
        p1.getLibrary().setCard(4, 3, YELLOW);
        p1.getLibrary().setCard(4, 4, WHITE);


        Assert.assertFalse(obj.checkObj(p1.getLibrary()));
        p1.getLibrary().reset_lib();

        p1.getLibrary().setCard(0, 0, PURPLE);
        p1.getLibrary().setCard(0, 1, WHITE);
        p1.getLibrary().setCard(0, 2, LIGHTBLUE);
        p1.getLibrary().setCard(0, 3, PURPLE);
        p1.getLibrary().setCard(0, 4, WHITE);

        p1.getLibrary().setCard(2, 0, WHITE);
        p1.getLibrary().setCard(2, 1, WHITE);
        p1.getLibrary().setCard(2, 2, LIGHTBLUE);
        p1.getLibrary().setCard(2, 3, GREEN);
        p1.getLibrary().setCard(2, 4, NONE);

        p1.getLibrary().setCard(3, 0, WHITE);
        p1.getLibrary().setCard(3, 1, WHITE);
        p1.getLibrary().setCard(3, 2, LIGHTBLUE);
        p1.getLibrary().setCard(3, 3, PURPLE);
        p1.getLibrary().setCard(3, 4, YELLOW);

        p1.getLibrary().setCard(4, 0, WHITE);
        p1.getLibrary().setCard(4, 1, WHITE);
        p1.getLibrary().setCard(4, 2, NONE);
        p1.getLibrary().setCard(4, 3, YELLOW);
        p1.getLibrary().setCard(4, 4, WHITE);


        Assert.assertFalse(obj.checkObj(p1.getLibrary()));
        p1.getLibrary().reset_lib();

        // test when i have row having 4, 5 different cards (if row 173)

        p1.getLibrary().setCard(0, 0, WHITE);
        p1.getLibrary().setCard(0, 1, WHITE);
        p1.getLibrary().setCard(0, 2, YELLOW);
        p1.getLibrary().setCard(0, 3, YELLOW);
        p1.getLibrary().setCard(0, 4, WHITE);


        p1.getLibrary().setCard(2, 0, WHITE);
        p1.getLibrary().setCard(2, 1, YELLOW);
        p1.getLibrary().setCard(2, 2, LIGHTBLUE);
        p1.getLibrary().setCard(2, 3, GREEN);
        p1.getLibrary().setCard(2, 4, BLUE);

        p1.getLibrary().setCard(3, 0, GREEN);
        p1.getLibrary().setCard(3, 1, WHITE);
        p1.getLibrary().setCard(3, 2, LIGHTBLUE);
        p1.getLibrary().setCard(3, 3, PURPLE);
        p1.getLibrary().setCard(3, 4, YELLOW);

        p1.getLibrary().setCard(4, 0, WHITE);
        p1.getLibrary().setCard(4, 1, WHITE);
        p1.getLibrary().setCard(4, 2, YELLOW);
        p1.getLibrary().setCard(4, 3, YELLOW);
        p1.getLibrary().setCard(4, 4, WHITE);


        Assert.assertFalse(obj.checkObj(p1.getLibrary()));
        p1.getLibrary().reset_lib();


    }

    @Test
    public void test_check5() throws Exception{

        CommonObj obj = new CommonObj(2, 5);

        // one group of 16 cards (1 group of 4)

        p1.getLibrary().setCard(1, 0, PURPLE);
        p1.getLibrary().setCard(2, 0, PURPLE);
        p1.getLibrary().setCard(3, 0, PURPLE);
        p1.getLibrary().setCard(4, 0, PURPLE);
        p1.getLibrary().setCard(5, 0, PURPLE);

        p1.getLibrary().setCard(0, 1, PURPLE);
        p1.getLibrary().setCard(1, 1, PURPLE);
        p1.getLibrary().setCard(2, 1, PURPLE);
        p1.getLibrary().setCard(3, 1, PURPLE);
        p1.getLibrary().setCard(4, 1, PURPLE);
        p1.getLibrary().setCard(5, 1, PURPLE);

        p1.getLibrary().setCard(0, 2, PURPLE);
        p1.getLibrary().setCard(1, 2, PURPLE);
        p1.getLibrary().setCard(2, 2, PURPLE);
        p1.getLibrary().setCard(3, 2, PURPLE);
        p1.getLibrary().setCard(4, 2, PURPLE);
        p1.getLibrary().setCard(5, 2, PURPLE);

        p1.getLibrary().setCard(1, 3, PURPLE);


        Assert.assertFalse(obj.checkObj(p1.getLibrary()));
        p1.getLibrary().reset_lib();

        // 4 distinct groups of 4 cards (or more)

        p1.getLibrary().setCard(2, 0, PURPLE);
        p1.getLibrary().setCard(3, 0, PURPLE);
        p1.getLibrary().setCard(4, 0, PURPLE);
        p1.getLibrary().setCard(5, 0, PURPLE);

        p1.getLibrary().setCard(2, 1, WHITE);
        p1.getLibrary().setCard(3, 1, WHITE);
        p1.getLibrary().setCard(4, 1, WHITE);
        p1.getLibrary().setCard(5, 1, WHITE);

        p1.getLibrary().setCard(2, 2, PURPLE);
        p1.getLibrary().setCard(3, 2, PURPLE);
        p1.getLibrary().setCard(4, 2, PURPLE);
        p1.getLibrary().setCard(5, 2, PURPLE);

        p1.getLibrary().setCard(2, 3, WHITE);
        p1.getLibrary().setCard(3, 3, WHITE);
        p1.getLibrary().setCard(4, 3, WHITE);
        p1.getLibrary().setCard(5, 3, WHITE);


        Assert.assertTrue(obj.checkObj(p1.getLibrary()));
        p1.getLibrary().reset_lib();

        //test 1 group of 16 cards (horizontal)

        p1.getLibrary().setCard(0, 0, PURPLE);
        p1.getLibrary().setCard(0, 1, PURPLE);
        p1.getLibrary().setCard(0, 2, PURPLE);
        p1.getLibrary().setCard(0, 3, PURPLE);

        p1.getLibrary().setCard(1, 0, PURPLE);
        p1.getLibrary().setCard(1, 1, PURPLE);
        p1.getLibrary().setCard(1, 2, PURPLE);
        p1.getLibrary().setCard(1, 3, PURPLE);

        p1.getLibrary().setCard(2, 0, PURPLE);
        p1.getLibrary().setCard(2, 1, PURPLE);
        p1.getLibrary().setCard(2, 2, PURPLE);
        p1.getLibrary().setCard(2, 3, PURPLE);

        p1.getLibrary().setCard(3, 0, PURPLE);
        p1.getLibrary().setCard(3, 1, PURPLE);
        p1.getLibrary().setCard(3, 2, PURPLE);
        p1.getLibrary().setCard(3, 3, PURPLE);


        Assert.assertFalse(obj.checkObj(p1.getLibrary()));
        p1.getLibrary().reset_lib();

        //4 distinct groups of horizontal cards

        p1.getLibrary().setCard(0, 0, PURPLE);
        p1.getLibrary().setCard(0, 1, PURPLE);
        p1.getLibrary().setCard(0, 2, PURPLE);
        p1.getLibrary().setCard(0, 3, PURPLE);

        p1.getLibrary().setCard(1, 0, BLUE);
        p1.getLibrary().setCard(1, 1, BLUE);
        p1.getLibrary().setCard(1, 2, BLUE);
        p1.getLibrary().setCard(1, 3, BLUE);

        p1.getLibrary().setCard(2, 0, YELLOW);
        p1.getLibrary().setCard(2, 1, YELLOW);
        p1.getLibrary().setCard(2, 2, YELLOW);
        p1.getLibrary().setCard(2, 3, YELLOW);

        p1.getLibrary().setCard(3, 0, LIGHTBLUE);
        p1.getLibrary().setCard(3, 1, LIGHTBLUE);
        p1.getLibrary().setCard(3, 2, LIGHTBLUE);
        p1.getLibrary().setCard(3, 3, LIGHTBLUE);


        Assert.assertTrue(obj.checkObj(p1.getLibrary()));
        p1.getLibrary().reset_lib();

        //4 distinct groups (mixed vertical and horizontal adjacency)

        p1.getLibrary().setCard(0, 0, LIGHTBLUE);
        p1.getLibrary().setCard(0, 1, LIGHTBLUE);
        p1.getLibrary().setCard(0, 2, LIGHTBLUE);
        p1.getLibrary().setCard(1, 2, LIGHTBLUE);

        p1.getLibrary().setCard(0, 3, BLUE);
        p1.getLibrary().setCard(0, 4, BLUE);
        p1.getLibrary().setCard(1, 3, BLUE);
        p1.getLibrary().setCard(1, 4, BLUE);

        p1.getLibrary().setCard(1, 0, YELLOW);
        p1.getLibrary().setCard(1, 1, YELLOW);
        p1.getLibrary().setCard(2, 0, YELLOW);
        p1.getLibrary().setCard(2, 1, YELLOW);

        p1.getLibrary().setCard(3, 0, PURPLE);
        p1.getLibrary().setCard(3, 1, PURPLE);
        p1.getLibrary().setCard(4, 1, PURPLE);
        p1.getLibrary().setCard(4, 2, PURPLE);

        p1.getLibrary().setCard(2, 2, WHITE);
        p1.getLibrary().setCard(2, 3, WHITE);
        p1.getLibrary().setCard(2, 4, WHITE);
        p1.getLibrary().setCard(3, 2, WHITE);
        p1.getLibrary().setCard(3, 3, WHITE);
        p1.getLibrary().setCard(3, 4, WHITE);
        p1.getLibrary().setCard(4, 3, WHITE);
        p1.getLibrary().setCard(4, 4, WHITE);
        p1.getLibrary().setCard(4, 0, WHITE);
        p1.getLibrary().setCard(5, 0, WHITE);
        p1.getLibrary().setCard(5, 1, WHITE);
        p1.getLibrary().setCard(5, 2, WHITE);
        p1.getLibrary().setCard(5, 3, WHITE);
        p1.getLibrary().setCard(5, 4, WHITE);



        Assert.assertTrue(obj.checkObj(p1.getLibrary()));
        p1.getLibrary().reset_lib();

        //4 distinct groups mixed colors (mixed vertical and horizontal adjacency)

        p1.getLibrary().setCard(0, 0, LIGHTBLUE);
        p1.getLibrary().setCard(0, 1, LIGHTBLUE);
        p1.getLibrary().setCard(0, 2, LIGHTBLUE);
        p1.getLibrary().setCard(1, 2, LIGHTBLUE);

        p1.getLibrary().setCard(0, 3, BLUE);
        p1.getLibrary().setCard(0, 4, BLUE);
        p1.getLibrary().setCard(1, 3, BLUE);
        p1.getLibrary().setCard(1, 4, BLUE);

        p1.getLibrary().setCard(1, 0, YELLOW);
        p1.getLibrary().setCard(1, 1, YELLOW);
        p1.getLibrary().setCard(2, 0, YELLOW);
        p1.getLibrary().setCard(2, 1, YELLOW);

        p1.getLibrary().setCard(3, 0, BLUE);
        p1.getLibrary().setCard(3, 1, BLUE);
        p1.getLibrary().setCard(4, 1, BLUE);
        p1.getLibrary().setCard(4, 2, BLUE);

        p1.getLibrary().setCard(2, 2, WHITE);
        p1.getLibrary().setCard(2, 3, WHITE);
        p1.getLibrary().setCard(2, 4, WHITE);
        p1.getLibrary().setCard(3, 2, WHITE);
        p1.getLibrary().setCard(3, 3, WHITE);
        p1.getLibrary().setCard(3, 4, WHITE);
        p1.getLibrary().setCard(4, 3, WHITE);
        p1.getLibrary().setCard(4, 4, WHITE);
        p1.getLibrary().setCard(4, 0, WHITE);
        p1.getLibrary().setCard(5, 0, WHITE);
        p1.getLibrary().setCard(5, 1, WHITE);
        p1.getLibrary().setCard(5, 2, WHITE);
        p1.getLibrary().setCard(5, 3, WHITE);
        p1.getLibrary().setCard(5, 4, WHITE);



        Assert.assertTrue(obj.checkObj(p1.getLibrary()));
        p1.getLibrary().reset_lib();

    }

    @Test
    public void test_check6() throws Exception{

        CommonObj obj = new CommonObj(2, 6);

        // case true

        p1.getLibrary().setCard(0, 0, PURPLE);
        p1.getLibrary().setCard(1, 0, GREEN);
        p1.getLibrary().setCard(2, 0, LIGHTBLUE);
        p1.getLibrary().setCard(3, 0, WHITE);
        p1.getLibrary().setCard(4, 0, BLUE);
        p1.getLibrary().setCard(5, 0, YELLOW);

        p1.getLibrary().setCard(0, 3, GREEN);
        p1.getLibrary().setCard(1, 3, PURPLE);
        p1.getLibrary().setCard(2, 3, LIGHTBLUE);
        p1.getLibrary().setCard(3, 3, WHITE);
        p1.getLibrary().setCard(4, 3, YELLOW);
        p1.getLibrary().setCard(5, 3, BLUE);

        Assert.assertTrue(obj.checkObj(p1.getLibrary()));
        p1.getLibrary().reset_lib();

        // case false (cover if cases)

        p1.getLibrary().setCard(0, 0, NONE);
        p1.getLibrary().setCard(1, 0, GREEN);
        p1.getLibrary().setCard(2, 0, LIGHTBLUE);
        p1.getLibrary().setCard(3, 0, WHITE);
        p1.getLibrary().setCard(4, 0, BLUE);
        p1.getLibrary().setCard(5, 0, YELLOW);

        p1.getLibrary().setCard(0, 3, GREEN);
        p1.getLibrary().setCard(1, 3, PURPLE);
        p1.getLibrary().setCard(2, 3, PURPLE);
        p1.getLibrary().setCard(3, 3, WHITE);
        p1.getLibrary().setCard(4, 3, YELLOW);
        p1.getLibrary().setCard(5, 3, BLUE);

        Assert.assertFalse(obj.checkObj(p1.getLibrary()));
        p1.getLibrary().reset_lib();
    }


    @Test
    public void test_check7() throws Exception{

        CommonObj obj = new CommonObj(2, 7);

        // case two adjacent square (false)

        p1.getLibrary().setCard(0, 0, BLUE);
        p1.getLibrary().setCard(1, 0, BLUE);
        p1.getLibrary().setCard(0, 1, BLUE);
        p1.getLibrary().setCard(1, 1, BLUE);

        p1.getLibrary().setCard(2, 0, BLUE);
        p1.getLibrary().setCard(2, 1, BLUE);
        p1.getLibrary().setCard(3, 0, BLUE);
        p1.getLibrary().setCard(3, 1, BLUE);

        Assert.assertFalse(obj.checkObj(p1.getLibrary()));
        p1.getLibrary().reset_lib();

        // case true (two squares not adjacent)

        p1.getLibrary().setCard(0, 0, BLUE);
        p1.getLibrary().setCard(1, 0, BLUE);
        p1.getLibrary().setCard(0, 1, BLUE);
        p1.getLibrary().setCard(1, 1, BLUE);

        p1.getLibrary().setCard(3, 2, BLUE);
        p1.getLibrary().setCard(3, 3, BLUE);
        p1.getLibrary().setCard(4, 2, BLUE);
        p1.getLibrary().setCard(4, 3, BLUE);

        Assert.assertTrue(obj.checkObj(p1.getLibrary()));
        p1.getLibrary().reset_lib();


        //case true (two squares of different colors)

        p1.getLibrary().setCard(0,0,PURPLE);
        p1.getLibrary().setCard(1,0,PURPLE);
        p1.getLibrary().setCard(0,1,PURPLE);
        p1.getLibrary().setCard(1,1,PURPLE);

        p1.getLibrary().setCard(3,2,BLUE);
        p1.getLibrary().setCard(3,3,BLUE);
        p1.getLibrary().setCard(4,2,BLUE);
        p1.getLibrary().setCard(4,3,BLUE);

        Assert.assertTrue(obj.checkObj(p1.getLibrary()));
        p1.getLibrary().reset_lib();

        //case false(one perfect square and one square not perfect of different colors)

        p1.getLibrary().setCard(0,0,PURPLE);
        p1.getLibrary().setCard(1,0,PURPLE);
        p1.getLibrary().setCard(0,1,PURPLE);
        p1.getLibrary().setCard(1,1,PURPLE);

        p1.getLibrary().setCard(3,2,BLUE);
        p1.getLibrary().setCard(3,3,BLUE);
        p1.getLibrary().setCard(4,2,BLUE);
        p1.getLibrary().setCard(4,3,BLUE);
        p1.getLibrary().setCard(2,2,BLUE);

        Assert.assertTrue(!obj.checkObj(p1.getLibrary()));
        p1.getLibrary().reset_lib();

    }

    @Test
    public void test_check8() throws Exception {

        CommonObj obj = new CommonObj(2, 8);

        // case true

        p1.getLibrary().setCard(0, 0, BLUE);
        p1.getLibrary().setCard(0, 1, LIGHTBLUE);
        p1.getLibrary().setCard(0, 2, YELLOW);
        p1.getLibrary().setCard(0, 3, WHITE);
        p1.getLibrary().setCard(0, 4, GREEN);

        p1.getLibrary().setCard(1, 0, GREEN);
        p1.getLibrary().setCard(1, 1, LIGHTBLUE);
        p1.getLibrary().setCard(1, 2, YELLOW);
        p1.getLibrary().setCard(1, 3, WHITE);
        p1.getLibrary().setCard(1, 4, PURPLE);


        Assert.assertTrue(obj.checkObj(p1.getLibrary()));
        p1.getLibrary().reset_lib();

        // case one row not completed and one row with 2 different type (coverage of if cases, result: false)

        p1.getLibrary().setCard(0, 0, BLUE);
        p1.getLibrary().setCard(0, 1, LIGHTBLUE);
        p1.getLibrary().setCard(0, 2, YELLOW);
        p1.getLibrary().setCard(0, 3, WHITE);
        p1.getLibrary().setCard(0, 4, NOT);

        p1.getLibrary().setCard(1, 0, GREEN);
        p1.getLibrary().setCard(1, 1, LIGHTBLUE);
        p1.getLibrary().setCard(1, 2, YELLOW);
        p1.getLibrary().setCard(1, 3, WHITE);
        p1.getLibrary().setCard(1, 4, GREEN);


        Assert.assertFalse(obj.checkObj(p1.getLibrary()));
        p1.getLibrary().reset_lib();

        //case one row not completed

        p1.getLibrary().setCard(0, 0, BLUE);
        p1.getLibrary().setCard(0, 1, LIGHTBLUE);
        p1.getLibrary().setCard(0, 2, YELLOW);
        p1.getLibrary().setCard(0, 3, WHITE);

        p1.getLibrary().setCard(1, 0, GREEN);
        p1.getLibrary().setCard(1, 1, LIGHTBLUE);
        p1.getLibrary().setCard(1, 2, YELLOW);
        p1.getLibrary().setCard(1, 3, WHITE);
        p1.getLibrary().setCard(1, 4, PURPLE);


        Assert.assertFalse(obj.checkObj(p1.getLibrary()));
        p1.getLibrary().reset_lib();

        //case row with 2 different types

        p1.getLibrary().setCard(0, 0, BLUE);
        p1.getLibrary().setCard(0, 1, LIGHTBLUE);
        p1.getLibrary().setCard(0, 2, YELLOW);
        p1.getLibrary().setCard(0, 3, WHITE);
        p1.getLibrary().setCard(0, 4, GREEN);

        p1.getLibrary().setCard(1, 0, GREEN);
        p1.getLibrary().setCard(1, 1, LIGHTBLUE);
        p1.getLibrary().setCard(1, 2, YELLOW);
        p1.getLibrary().setCard(1, 3, WHITE);
        p1.getLibrary().setCard(1, 4, GREEN);


        Assert.assertFalse(obj.checkObj(p1.getLibrary()));
        p1.getLibrary().reset_lib();

    }

    @Test
    public void test_check9() throws Exception {

        CommonObj obj = new CommonObj(2, 9);

        // case true

        p1.getLibrary().setCard(0, 0, WHITE);
        p1.getLibrary().setCard(1, 0, WHITE);
        p1.getLibrary().setCard(2, 0, LIGHTBLUE);
        p1.getLibrary().setCard(3, 0, YELLOW);
        p1.getLibrary().setCard(4, 0, WHITE);
        p1.getLibrary().setCard(5, 0, WHITE);

        p1.getLibrary().setCard(0, 3, GREEN);
        p1.getLibrary().setCard(1, 3, GREEN);
        p1.getLibrary().setCard(2, 3, LIGHTBLUE);
        p1.getLibrary().setCard(3, 3, GREEN);
        p1.getLibrary().setCard(4, 3, GREEN);
        p1.getLibrary().setCard(5, 3, LIGHTBLUE);

        p1.getLibrary().setCard(0, 4, GREEN);
        p1.getLibrary().setCard(1, 4, GREEN);
        p1.getLibrary().setCard(2, 4, GREEN);
        p1.getLibrary().setCard(3, 4, GREEN);
        p1.getLibrary().setCard(4, 4, GREEN);
        p1.getLibrary().setCard(5, 4, GREEN);

        Assert.assertTrue(obj.checkObj(p1.getLibrary()));
        p1.getLibrary().reset_lib();

        // case false (one column with NONE, one column with more than 3 different cards: if cases coverage)

        p1.getLibrary().setCard(0, 0, NONE);
        p1.getLibrary().setCard(1, 0, WHITE);
        p1.getLibrary().setCard(2, 0, LIGHTBLUE);
        p1.getLibrary().setCard(3, 0, YELLOW);
        p1.getLibrary().setCard(4, 0, WHITE);
        p1.getLibrary().setCard(5, 0, BLUE);

        p1.getLibrary().setCard(0, 3, GREEN);
        p1.getLibrary().setCard(1, 3, WHITE);
        p1.getLibrary().setCard(2, 3, LIGHTBLUE);
        p1.getLibrary().setCard(3, 3, YELLOW);
        p1.getLibrary().setCard(4, 3, GREEN);
        p1.getLibrary().setCard(5, 3, LIGHTBLUE);

        p1.getLibrary().setCard(0, 4, GREEN);
        p1.getLibrary().setCard(1, 4, GREEN);
        p1.getLibrary().setCard(2, 4, GREEN);
        p1.getLibrary().setCard(3, 4, GREEN);
        p1.getLibrary().setCard(4, 4, GREEN);
        p1.getLibrary().setCard(5, 4, GREEN);

        Assert.assertFalse(obj.checkObj(p1.getLibrary()));
        p1.getLibrary().reset_lib();

        // case false, one column not completed

        p1.getLibrary().setCard(0, 0, NONE);
        p1.getLibrary().setCard(1, 0, WHITE);
        p1.getLibrary().setCard(2, 0, LIGHTBLUE);
        p1.getLibrary().setCard(3, 0, YELLOW);
        p1.getLibrary().setCard(4, 0, WHITE);
        p1.getLibrary().setCard(5, 0, BLUE);

        p1.getLibrary().setCard(0, 3, GREEN);
        p1.getLibrary().setCard(1, 3, GREEN);
        p1.getLibrary().setCard(2, 3, LIGHTBLUE);
        p1.getLibrary().setCard(3, 3, GREEN);
        p1.getLibrary().setCard(4, 3, GREEN);
        p1.getLibrary().setCard(5, 3, LIGHTBLUE);

        p1.getLibrary().setCard(0, 4, GREEN);
        p1.getLibrary().setCard(1, 4, GREEN);
        p1.getLibrary().setCard(2, 4, GREEN);
        p1.getLibrary().setCard(3, 4, GREEN);
        p1.getLibrary().setCard(4, 4, GREEN);
        p1.getLibrary().setCard(5, 4, GREEN);

        Assert.assertFalse(obj.checkObj(p1.getLibrary()));
        p1.getLibrary().reset_lib();

        //case false, one column with more than 3 different types of cards

        p1.getLibrary().setCard(0, 0, WHITE);
        p1.getLibrary().setCard(1, 0, WHITE);
        p1.getLibrary().setCard(2, 0, LIGHTBLUE);
        p1.getLibrary().setCard(3, 0, YELLOW);
        p1.getLibrary().setCard(4, 0, WHITE);
        p1.getLibrary().setCard(5, 0, WHITE);

        p1.getLibrary().setCard(0, 3, GREEN);
        p1.getLibrary().setCard(1, 3, WHITE);
        p1.getLibrary().setCard(2, 3, LIGHTBLUE);
        p1.getLibrary().setCard(3, 3, YELLOW);
        p1.getLibrary().setCard(4, 3, GREEN);
        p1.getLibrary().setCard(5, 3, LIGHTBLUE);

        p1.getLibrary().setCard(0, 4, GREEN);
        p1.getLibrary().setCard(1, 4, GREEN);
        p1.getLibrary().setCard(2, 4, GREEN);
        p1.getLibrary().setCard(3, 4, GREEN);
        p1.getLibrary().setCard(4, 4, GREEN);
        p1.getLibrary().setCard(5, 4, GREEN);

        Assert.assertFalse(obj.checkObj(p1.getLibrary()));
        p1.getLibrary().reset_lib();


    }

    @Test
    public void test_check10() throws Exception {

        CommonObj obj = new CommonObj(2, 10);

        // case true

        p1.getLibrary().setCard(2, 0, PURPLE);
        p1.getLibrary().setCard(3, 1, PURPLE);
        p1.getLibrary().setCard(2, 2, PURPLE);
        p1.getLibrary().setCard(4, 0, PURPLE);
        p1.getLibrary().setCard(4, 2, PURPLE);

        Assert.assertTrue(obj.checkObj(p1.getLibrary()));
        p1.getLibrary().reset_lib();

        // case false

        p1.getLibrary().setCard(2, 0, PURPLE);
        p1.getLibrary().setCard(3, 1, PURPLE);
        p1.getLibrary().setCard(2, 2, PURPLE);
        p1.getLibrary().setCard(4, 0, PURPLE);
        p1.getLibrary().setCard(4, 2, WHITE);

        Assert.assertFalse(obj.checkObj(p1.getLibrary()));
        p1.getLibrary().reset_lib();

        p1.getLibrary().setCard(3, 1, PURPLE);
        p1.getLibrary().setCard(2, 2, PURPLE);
        p1.getLibrary().setCard(4, 0, GREEN);
        p1.getLibrary().setCard(4, 2, PURPLE);

        Assert.assertFalse(obj.checkObj(p1.getLibrary()));
        p1.getLibrary().reset_lib();

        p1.getLibrary().setCard(3, 1, PURPLE);
        p1.getLibrary().setCard(2, 2, GREEN);
        p1.getLibrary().setCard(4, 0, PURPLE);
        p1.getLibrary().setCard(4, 2, PURPLE);

        Assert.assertFalse(obj.checkObj(p1.getLibrary()));
        p1.getLibrary().reset_lib();

        p1.getLibrary().setCard(3, 1, GREEN);
        p1.getLibrary().setCard(2, 2, PURPLE);
        p1.getLibrary().setCard(4, 0, PURPLE);
        p1.getLibrary().setCard(4, 2, PURPLE);

        Assert.assertFalse(obj.checkObj(p1.getLibrary()));
        p1.getLibrary().reset_lib();
    }

    @Test
    public void test_check11() throws Exception {

        CommonObj obj = new CommonObj(2, 11);

        // case true

        p1.getLibrary().setCard(3, 1, PURPLE);
        p1.getLibrary().setCard(4, 2, PURPLE);
        p1.getLibrary().setCard(4, 0, PURPLE);
        p1.getLibrary().setCard(1, 2, PURPLE);
        p1.getLibrary().setCard(5, 1, PURPLE);
        p1.getLibrary().setCard(1, 1, PURPLE);
        p1.getLibrary().setCard(5, 0, PURPLE);
        p1.getLibrary().setCard(2, 2, PURPLE);

        Assert.assertTrue(obj.checkObj(p1.getLibrary()));
        p1.getLibrary().reset_lib();



        // case false

        p1.getLibrary().setCard(3, 1, PURPLE);
        p1.getLibrary().setCard(4, 2, PURPLE);
        p1.getLibrary().setCard(4, 0, PURPLE);
        p1.getLibrary().setCard(1, 2, PURPLE);
        p1.getLibrary().setCard(5, 1, PURPLE);
        p1.getLibrary().setCard(1, 1, PURPLE);
        p1.getLibrary().setCard(5, 0, PURPLE);
        p1.getLibrary().setCard(2, 2, WHITE);

        Assert.assertFalse(obj.checkObj(p1.getLibrary()));
        p1.getLibrary().reset_lib();
    }

    @Test
    public void test_check12() throws Exception {

        CommonObj obj = new CommonObj(2, 12);

        // two cases true:

        for(int j = 0; j < 5; j++){
            for(int i = 5; i > j; i--){
                p1.getLibrary().setCard(i, j, GREEN);
            }
        }

        Assert.assertTrue(obj.checkObj(p1.getLibrary()));
        p1.getLibrary().reset_lib();

        for(int j = 0; j < 5; j++){
            for(int i = 5; i > 4 - j; i--){
                p1.getLibrary().setCard(i, j, WHITE);
            }
        }

        Assert.assertTrue(obj.checkObj(p1.getLibrary()));
        p1.getLibrary().reset_lib();

        // the other cases are false:
        for(int j = 0; j < 6; j++){
            for(int i = 0; i < 5; i++){
                p1.getLibrary().setCard(j, i, WHITE);
                Assert.assertFalse(obj.checkObj(p1.getLibrary()));
            }
        }
        Assert.assertFalse(obj.checkObj(p1.getLibrary()));
        p1.getLibrary().reset_lib();
    }

    @Test
    public void testgetPointCount_2players_returncorrectpoints(){
        CommonObj obj= new CommonObj(2,1);
        int points;
        points=obj.getPointCount();
        assertTrue(points==8);
        points=obj.getPointCount();
        assertTrue(points==4);
        points=obj.getPointCount();
        assertTrue(points==0);
        points=obj.getPointCount();
        assertTrue(points==0);
    }

    @Test
    public void testgetPointCount_3players_returncorrectpoints(){
        CommonObj obj= new CommonObj(3,1);
        int points;
        points=obj.getPointCount();
        assertTrue(points==8);
        points=obj.getPointCount();
        assertTrue(points==6);
        points=obj.getPointCount();
        assertTrue(points==4);
        points=obj.getPointCount();
        assertTrue(points==0);
    }

    @Test
    public void testgetPointCount_4players_returncorrectpoints(){
        CommonObj obj= new CommonObj(4,1);
        int points;
        points=obj.getPointCount();
        assertTrue(points==8);
        points=obj.getPointCount();
        assertTrue(points==6);
        points=obj.getPointCount();
        assertTrue(points==4);
        points=obj.getPointCount();
        assertTrue(points==2);
    }
}
