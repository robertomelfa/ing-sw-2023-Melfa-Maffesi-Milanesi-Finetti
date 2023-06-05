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

        p1.getLibrary().setCard(1, 0, PURPLE2);
        p1.getLibrary().setCard(2, 0, WHITE3);
        p1.getLibrary().setCard(3, 0, LIGHTBLUE1);
        p1.getLibrary().setCard(4, 0, LIGHTBLUE3);
        p1.getLibrary().setCard(5, 0, LIGHTBLUE2);

        p1.getLibrary().setCard(0, 1, PURPLE2);
        p1.getLibrary().setCard(1, 1, PURPLE3);
        p1.getLibrary().setCard(2, 1, WHITE3);
        p1.getLibrary().setCard(3, 1, YELLOW1);
        p1.getLibrary().setCard(4, 1, LIGHTBLUE1);
        p1.getLibrary().setCard(5, 1, LIGHTBLUE3);

        p1.getLibrary().setCard(0, 2, PURPLE3);
        p1.getLibrary().setCard(1, 2, PURPLE1);
        p1.getLibrary().setCard(2, 2, BLUE3);
        p1.getLibrary().setCard(3, 2, LIGHTBLUE2);
        p1.getLibrary().setCard(4, 2, GREEN2);
        p1.getLibrary().setCard(5, 2, LIGHTBLUE1);

        p1.getLibrary().setCard(1, 3, GREEN1);
        p1.getLibrary().setCard(2, 3, WHITE2);
        p1.getLibrary().setCard(3, 3, YELLOW2);
        p1.getLibrary().setCard(4, 3, GREEN3);
        p1.getLibrary().setCard(5, 3, GREEN1);

        p1.getLibrary().setCard(4, 4, GREEN2);
        p1.getLibrary().setCard(5, 4, GREEN3);

        p1.getLibrary().viewGrid();
        Assert.assertFalse(obj.checkObj(p1.getLibrary()));
        p1.getLibrary().reset_lib();

        p1.getLibrary().setCard(1, 0, PURPLE1);
        p1.getLibrary().setCard(2, 0, WHITE1);
        p1.getLibrary().setCard(3, 0, LIGHTBLUE1);
        p1.getLibrary().setCard(4, 0, LIGHTBLUE3);
        p1.getLibrary().setCard(5, 0, LIGHTBLUE2);

        p1.getLibrary().setCard(0, 1, PURPLE2);
        p1.getLibrary().setCard(1, 1, PURPLE1);
        p1.getLibrary().setCard(2, 1, WHITE1);
        p1.getLibrary().setCard(3, 1, YELLOW1);
        p1.getLibrary().setCard(4, 1, LIGHTBLUE1);
        p1.getLibrary().setCard(5, 1, LIGHTBLUE3);

        p1.getLibrary().setCard(0, 2, PURPLE2);
        p1.getLibrary().setCard(1, 2, PURPLE3);
        p1.getLibrary().setCard(2, 2, BLUE2);
        p1.getLibrary().setCard(3, 2, LIGHTBLUE2);
        p1.getLibrary().setCard(4, 2, GREEN1);
        p1.getLibrary().setCard(5, 2, LIGHTBLUE1);

        p1.getLibrary().setCard(0, 3, PURPLE2);
        p1.getLibrary().setCard(1, 3, GREEN1);
        p1.getLibrary().setCard(2, 3, WHITE2);
        p1.getLibrary().setCard(3, 3, YELLOW1);
        p1.getLibrary().setCard(4, 3, GREEN3);
        p1.getLibrary().setCard(5, 3, GREEN2);

        p1.getLibrary().setCard(0, 4, GREEN1);
        p1.getLibrary().setCard(1, 4, GREEN1);
        p1.getLibrary().setCard(2, 4, WHITE3);
        p1.getLibrary().setCard(3, 4, YELLOW2);
        p1.getLibrary().setCard(4, 4, GREEN3);
        p1.getLibrary().setCard(5, 4, GREEN1);

        p1.getLibrary().viewGrid();
        Assert.assertTrue(obj.checkObj(p1.getLibrary()));
        p1.getLibrary().reset_lib();
    }

    @Test
    public void test_check2() throws Exception{

        CommonObj obj = new CommonObj(3, 2);

        // there is no diagonal

        p1.getLibrary().setCard(1, 0, PURPLE1);
        p1.getLibrary().setCard(2, 0, WHITE1);
        p1.getLibrary().setCard(3, 0, LIGHTBLUE1);
        p1.getLibrary().setCard(4, 0, LIGHTBLUE3);
        p1.getLibrary().setCard(5, 0, LIGHTBLUE2);

        p1.getLibrary().setCard(0, 1, PURPLE3);
        p1.getLibrary().setCard(1, 1, PURPLE1);
        p1.getLibrary().setCard(2, 1, WHITE1);
        p1.getLibrary().setCard(3, 1, YELLOW3);
        p1.getLibrary().setCard(4, 1, LIGHTBLUE1);
        p1.getLibrary().setCard(5, 1, LIGHTBLUE3);

        p1.getLibrary().setCard(0, 2, PURPLE2);
        p1.getLibrary().setCard(1, 2, PURPLE3);
        p1.getLibrary().setCard(2, 2, BLUE1);
        p1.getLibrary().setCard(3, 2, LIGHTBLUE1);
        p1.getLibrary().setCard(4, 2, GREEN3);
        p1.getLibrary().setCard(5, 2, LIGHTBLUE2);

        p1.getLibrary().setCard(1, 3, GREEN2);
        p1.getLibrary().setCard(2, 3, WHITE1);
        p1.getLibrary().setCard(3, 3, YELLOW1);
        p1.getLibrary().setCard(4, 3, GREEN1);
        p1.getLibrary().setCard(5, 3, GREEN2);

        p1.getLibrary().setCard(4, 4, GREEN3);
        p1.getLibrary().setCard(5, 4, GREEN1);

        Assert.assertFalse(obj.checkObj(p1.getLibrary()));
        p1.getLibrary().reset_lib();

        // first diagonal

        p1.getLibrary().setCard(5, 0, PURPLE1);
        p1.getLibrary().setCard(4, 1, PURPLE1);
        p1.getLibrary().setCard(3, 2, PURPLE2);
        p1.getLibrary().setCard(2, 3, PURPLE1);
        p1.getLibrary().setCard(1, 4, PURPLE2);

        Assert.assertTrue(obj.checkObj(p1.getLibrary()));
        p1.getLibrary().reset_lib();

        // second diagonal

        p1.getLibrary().setCard(4, 0, WHITE2);
        p1.getLibrary().setCard(3, 1, WHITE1);
        p1.getLibrary().setCard(2, 2, WHITE1);
        p1.getLibrary().setCard(1, 3, WHITE3);
        p1.getLibrary().setCard(0, 4, WHITE2);

        Assert.assertTrue(obj.checkObj(p1.getLibrary()));
        p1.getLibrary().reset_lib();

        // third diagonal

        p1.getLibrary().setCard(4, 4, WHITE1);
        p1.getLibrary().setCard(3, 3, WHITE1);
        p1.getLibrary().setCard(2, 2, WHITE1);
        p1.getLibrary().setCard(1, 1, WHITE2);
        p1.getLibrary().setCard(0, 0, WHITE1);

        Assert.assertTrue(obj.checkObj(p1.getLibrary()));
        p1.getLibrary().reset_lib();

        // fourth case

        p1.getLibrary().setCard(5, 4, GREEN1);
        p1.getLibrary().setCard(4, 3, GREEN1);
        p1.getLibrary().setCard(3, 2, GREEN2);
        p1.getLibrary().setCard(2, 1, GREEN3);
        p1.getLibrary().setCard(1, 0, GREEN1);

        Assert.assertTrue(obj.checkObj(p1.getLibrary()));
        p1.getLibrary().reset_lib();
    }

    @Test
    public void test_check3() throws Exception{

        CommonObj obj = new CommonObj(2, 3);

        p1.getLibrary().setCard(0, 0, GREEN3);
        p1.getLibrary().setCard(5, 0, GREEN2);
        p1.getLibrary().setCard(5, 4, GREEN1);

        Assert.assertFalse(obj.checkObj(p1.getLibrary()));
        p1.getLibrary().reset_lib();

        p1.getLibrary().setCard(0, 0, GREEN1);
        p1.getLibrary().setCard(5, 0, GREEN1);
        p1.getLibrary().setCard(5, 4, GREEN2);
        p1.getLibrary().setCard(0, 4, WHITE3);

        Assert.assertFalse(obj.checkObj(p1.getLibrary()));
        p1.getLibrary().reset_lib();

        p1.getLibrary().setCard(0, 0, GREEN3);
        p1.getLibrary().setCard(5, 0, GREEN1);
        p1.getLibrary().setCard(5, 4, GREEN3);
        p1.getLibrary().setCard(0, 4, GREEN2);

        Assert.assertTrue(obj.checkObj(p1.getLibrary()));
        p1.getLibrary().reset_lib();

        p1.getLibrary().setCard(0, 0, GREEN1);
        p1.getLibrary().setCard(5, 0, WHITE2);
        p1.getLibrary().setCard(5, 4, GREEN1);
        p1.getLibrary().setCard(0, 4, GREEN1);

        Assert.assertFalse(obj.checkObj(p1.getLibrary()));
        p1.getLibrary().reset_lib();

        p1.getLibrary().setCard(0, 0, GREEN3);
        p1.getLibrary().setCard(5, 0, GREEN2);
        p1.getLibrary().setCard(5, 4, WHITE1);
        p1.getLibrary().setCard(0, 4, GREEN1);

        Assert.assertFalse(obj.checkObj(p1.getLibrary()));
        p1.getLibrary().reset_lib();

        p1.getLibrary().setCard(0, 0, WHITE1);
        p1.getLibrary().setCard(5, 0, GREEN3);
        p1.getLibrary().setCard(5, 4, GREEN1);
        p1.getLibrary().setCard(0, 4, GREEN2);

        Assert.assertFalse(obj.checkObj(p1.getLibrary()));
        p1.getLibrary().reset_lib();

        p1.getLibrary().setCard(0, 0, NONE);
        p1.getLibrary().setCard(5, 0, GREEN3);
        p1.getLibrary().setCard(5, 4, GREEN1);
        p1.getLibrary().setCard(0, 4, GREEN2);

        Assert.assertFalse(obj.checkObj(p1.getLibrary()));
        p1.getLibrary().reset_lib();
    }

    @Test
    public void test_check4() throws Exception{

        CommonObj obj = new CommonObj(2, 4);

        p1.getLibrary().setCard(0, 0, PURPLE1);
        p1.getLibrary().setCard(0, 1, WHITE1);
        p1.getLibrary().setCard(0, 2, LIGHTBLUE3);
        p1.getLibrary().setCard(0, 3, PURPLE2);
        p1.getLibrary().setCard(0, 4, WHITE2);

        p1.getLibrary().setCard(2, 0, WHITE1);
        p1.getLibrary().setCard(2, 1, WHITE3);
        p1.getLibrary().setCard(2, 2, LIGHTBLUE1);
        p1.getLibrary().setCard(2, 3, GREEN3);
        p1.getLibrary().setCard(2, 4, WHITE1);

        p1.getLibrary().setCard(3, 0, PURPLE3);
        p1.getLibrary().setCard(3, 1, WHITE2);
        p1.getLibrary().setCard(3, 2, LIGHTBLUE1);
        p1.getLibrary().setCard(3, 3, PURPLE2);
        p1.getLibrary().setCard(3, 4, LIGHTBLUE2);

        p1.getLibrary().setCard(4, 0, WHITE1);
        p1.getLibrary().setCard(4, 1, WHITE1);
        p1.getLibrary().setCard(4, 2, YELLOW3);
        p1.getLibrary().setCard(4, 3, YELLOW2);
        p1.getLibrary().setCard(4, 4, WHITE1);


        Assert.assertTrue(obj.checkObj(p1.getLibrary()));
        p1.getLibrary().reset_lib();

        // test if when card is NONE (rig 167)

        p1.getLibrary().setCard(0, 0, PURPLE1);
        p1.getLibrary().setCard(0, 1, WHITE2);
        p1.getLibrary().setCard(0, 2, LIGHTBLUE1);
        p1.getLibrary().setCard(0, 3, PURPLE2);
        p1.getLibrary().setCard(0, 4, WHITE3);

        p1.getLibrary().setCard(2, 0, WHITE1);
        p1.getLibrary().setCard(2, 1, WHITE1);
        p1.getLibrary().setCard(2, 2, LIGHTBLUE3);
        p1.getLibrary().setCard(2, 3, GREEN2);
        p1.getLibrary().setCard(2, 4, NONE);

        p1.getLibrary().setCard(3, 0, NONE);
        p1.getLibrary().setCard(3, 1, WHITE1);
        p1.getLibrary().setCard(3, 2, LIGHTBLUE1);
        p1.getLibrary().setCard(3, 3, PURPLE3);
        p1.getLibrary().setCard(3, 4, YELLOW1);

        p1.getLibrary().setCard(4, 0, WHITE1);
        p1.getLibrary().setCard(4, 1, WHITE2);
        p1.getLibrary().setCard(4, 2, YELLOW1);
        p1.getLibrary().setCard(4, 3, YELLOW2);
        p1.getLibrary().setCard(4, 4, WHITE1);


        Assert.assertFalse(obj.checkObj(p1.getLibrary()));
        p1.getLibrary().reset_lib();

        p1.getLibrary().setCard(0, 0, PURPLE1);
        p1.getLibrary().setCard(0, 1, WHITE3);
        p1.getLibrary().setCard(0, 2, LIGHTBLUE2);
        p1.getLibrary().setCard(0, 3, PURPLE3);
        p1.getLibrary().setCard(0, 4, WHITE1);

        p1.getLibrary().setCard(2, 0, WHITE2);
        p1.getLibrary().setCard(2, 1, WHITE1);
        p1.getLibrary().setCard(2, 2, LIGHTBLUE3);
        p1.getLibrary().setCard(2, 3, GREEN1);
        p1.getLibrary().setCard(2, 4, NONE);

        p1.getLibrary().setCard(3, 0, WHITE2);
        p1.getLibrary().setCard(3, 1, WHITE1);
        p1.getLibrary().setCard(3, 2, LIGHTBLUE2);
        p1.getLibrary().setCard(3, 3, PURPLE2);
        p1.getLibrary().setCard(3, 4, YELLOW1);

        p1.getLibrary().setCard(4, 0, WHITE2);
        p1.getLibrary().setCard(4, 1, WHITE1);
        p1.getLibrary().setCard(4, 2, NONE);
        p1.getLibrary().setCard(4, 3, YELLOW3);
        p1.getLibrary().setCard(4, 4, WHITE1);


        Assert.assertFalse(obj.checkObj(p1.getLibrary()));
        p1.getLibrary().reset_lib();

        // test when i have row having 4, 5 different cards (if row 173)

        p1.getLibrary().setCard(0, 0, WHITE1);
        p1.getLibrary().setCard(0, 1, WHITE3);
        p1.getLibrary().setCard(0, 2, YELLOW2);
        p1.getLibrary().setCard(0, 3, YELLOW1);
        p1.getLibrary().setCard(0, 4, WHITE1);


        p1.getLibrary().setCard(2, 0, WHITE2);
        p1.getLibrary().setCard(2, 1, YELLOW3);
        p1.getLibrary().setCard(2, 2, LIGHTBLUE1);
        p1.getLibrary().setCard(2, 3, GREEN2);
        p1.getLibrary().setCard(2, 4, BLUE2);

        p1.getLibrary().setCard(3, 0, GREEN1);
        p1.getLibrary().setCard(3, 1, WHITE1);
        p1.getLibrary().setCard(3, 2, LIGHTBLUE1);
        p1.getLibrary().setCard(3, 3, PURPLE1);
        p1.getLibrary().setCard(3, 4, YELLOW1);

        p1.getLibrary().setCard(4, 0, WHITE1);
        p1.getLibrary().setCard(4, 1, WHITE3);
        p1.getLibrary().setCard(4, 2, YELLOW2);
        p1.getLibrary().setCard(4, 3, YELLOW1);
        p1.getLibrary().setCard(4, 4, WHITE1);


        Assert.assertFalse(obj.checkObj(p1.getLibrary()));
        p1.getLibrary().reset_lib();


    }

    @Test
    public void test_check5() throws Exception{

        CommonObj obj = new CommonObj(2, 5);

        // one group of 16 cards (1 group of 4)

        p1.getLibrary().setCard(1, 0, PURPLE2);
        p1.getLibrary().setCard(2, 0, PURPLE1);
        p1.getLibrary().setCard(3, 0, PURPLE2);
        p1.getLibrary().setCard(4, 0, PURPLE3);
        p1.getLibrary().setCard(5, 0, PURPLE2);

        p1.getLibrary().setCard(0, 1, PURPLE1);
        p1.getLibrary().setCard(1, 1, PURPLE3);
        p1.getLibrary().setCard(2, 1, PURPLE1);
        p1.getLibrary().setCard(3, 1, PURPLE2);
        p1.getLibrary().setCard(4, 1, PURPLE1);
        p1.getLibrary().setCard(5, 1, PURPLE2);

        p1.getLibrary().setCard(0, 2, PURPLE1);
        p1.getLibrary().setCard(1, 2, PURPLE2);
        p1.getLibrary().setCard(2, 2, PURPLE1);
        p1.getLibrary().setCard(3, 2, PURPLE3);
        p1.getLibrary().setCard(4, 2, PURPLE1);
        p1.getLibrary().setCard(5, 2, PURPLE2);

        p1.getLibrary().setCard(1, 3, PURPLE3);


        Assert.assertFalse(obj.checkObj(p1.getLibrary()));
        p1.getLibrary().reset_lib();

        // 4 distinct groups of 4 cards (or more)

        p1.getLibrary().setCard(2, 0, PURPLE1);
        p1.getLibrary().setCard(3, 0, PURPLE3);
        p1.getLibrary().setCard(4, 0, PURPLE1);
        p1.getLibrary().setCard(5, 0, PURPLE1);

        p1.getLibrary().setCard(2, 1, WHITE2);
        p1.getLibrary().setCard(3, 1, WHITE1);
        p1.getLibrary().setCard(4, 1, WHITE1);
        p1.getLibrary().setCard(5, 1, WHITE1);

        p1.getLibrary().setCard(2, 2, PURPLE2);
        p1.getLibrary().setCard(3, 2, PURPLE2);
        p1.getLibrary().setCard(4, 2, PURPLE1);
        p1.getLibrary().setCard(5, 2, PURPLE3);

        p1.getLibrary().setCard(2, 3, WHITE3);
        p1.getLibrary().setCard(3, 3, WHITE1);
        p1.getLibrary().setCard(4, 3, WHITE2);
        p1.getLibrary().setCard(5, 3, WHITE1);


        Assert.assertTrue(obj.checkObj(p1.getLibrary()));
        p1.getLibrary().reset_lib();

        //test 1 group of 16 cards (horizontal)

        p1.getLibrary().setCard(0, 0, PURPLE2);
        p1.getLibrary().setCard(0, 1, PURPLE1);
        p1.getLibrary().setCard(0, 2, PURPLE1);
        p1.getLibrary().setCard(0, 3, PURPLE1);

        p1.getLibrary().setCard(1, 0, PURPLE2);
        p1.getLibrary().setCard(1, 1, PURPLE3);
        p1.getLibrary().setCard(1, 2, PURPLE2);
        p1.getLibrary().setCard(1, 3, PURPLE1);

        p1.getLibrary().setCard(2, 0, PURPLE1);
        p1.getLibrary().setCard(2, 1, PURPLE1);
        p1.getLibrary().setCard(2, 2, PURPLE2);
        p1.getLibrary().setCard(2, 3, PURPLE1);

        p1.getLibrary().setCard(3, 0, PURPLE3);
        p1.getLibrary().setCard(3, 1, PURPLE1);
        p1.getLibrary().setCard(3, 2, PURPLE1);
        p1.getLibrary().setCard(3, 3, PURPLE2);


        Assert.assertFalse(obj.checkObj(p1.getLibrary()));
        p1.getLibrary().reset_lib();

        //4 distinct groups of horizontal cards

        p1.getLibrary().setCard(0, 0, PURPLE1);
        p1.getLibrary().setCard(0, 1, PURPLE3);
        p1.getLibrary().setCard(0, 2, PURPLE2);
        p1.getLibrary().setCard(0, 3, PURPLE1);

        p1.getLibrary().setCard(1, 0, BLUE3);
        p1.getLibrary().setCard(1, 1, BLUE2);
        p1.getLibrary().setCard(1, 2, BLUE1);
        p1.getLibrary().setCard(1, 3, BLUE1);

        p1.getLibrary().setCard(2, 0, YELLOW3);
        p1.getLibrary().setCard(2, 1, YELLOW2);
        p1.getLibrary().setCard(2, 2, YELLOW1);
        p1.getLibrary().setCard(2, 3, YELLOW1);

        p1.getLibrary().setCard(3, 0, LIGHTBLUE2);
        p1.getLibrary().setCard(3, 1, LIGHTBLUE3);
        p1.getLibrary().setCard(3, 2, LIGHTBLUE1);
        p1.getLibrary().setCard(3, 3, LIGHTBLUE3);


        Assert.assertTrue(obj.checkObj(p1.getLibrary()));
        p1.getLibrary().reset_lib();

        //4 distinct groups (mixed vertical and horizontal adjacency)

        p1.getLibrary().setCard(0, 0, LIGHTBLUE1);
        p1.getLibrary().setCard(0, 1, LIGHTBLUE2);
        p1.getLibrary().setCard(0, 2, LIGHTBLUE1);
        p1.getLibrary().setCard(1, 2, LIGHTBLUE2);

        p1.getLibrary().setCard(0, 3, BLUE2);
        p1.getLibrary().setCard(0, 4, BLUE1);
        p1.getLibrary().setCard(1, 3, BLUE2);
        p1.getLibrary().setCard(1, 4, BLUE3);

        p1.getLibrary().setCard(1, 0, YELLOW2);
        p1.getLibrary().setCard(1, 1, YELLOW3);
        p1.getLibrary().setCard(2, 0, YELLOW1);
        p1.getLibrary().setCard(2, 1, YELLOW2);

        p1.getLibrary().setCard(3, 0, PURPLE1);
        p1.getLibrary().setCard(3, 1, PURPLE1);
        p1.getLibrary().setCard(4, 1, PURPLE2);
        p1.getLibrary().setCard(4, 2, PURPLE3);

        p1.getLibrary().setCard(2, 2, WHITE1);
        p1.getLibrary().setCard(2, 3, WHITE1);
        p1.getLibrary().setCard(2, 4, WHITE3);
        p1.getLibrary().setCard(3, 2, WHITE1);
        p1.getLibrary().setCard(3, 3, WHITE2);
        p1.getLibrary().setCard(3, 4, WHITE1);
        p1.getLibrary().setCard(4, 3, WHITE3);
        p1.getLibrary().setCard(4, 4, WHITE1);
        p1.getLibrary().setCard(4, 0, WHITE3);
        p1.getLibrary().setCard(5, 0, WHITE1);
        p1.getLibrary().setCard(5, 1, WHITE2);
        p1.getLibrary().setCard(5, 2, WHITE1);
        p1.getLibrary().setCard(5, 3, WHITE2);
        p1.getLibrary().setCard(5, 4, WHITE3);



        Assert.assertTrue(obj.checkObj(p1.getLibrary()));
        p1.getLibrary().reset_lib();

        //4 distinct groups mixed colors (mixed vertical and horizontal adjacency)

        p1.getLibrary().setCard(0, 0, LIGHTBLUE1);
        p1.getLibrary().setCard(0, 1, LIGHTBLUE3);
        p1.getLibrary().setCard(0, 2, LIGHTBLUE2);
        p1.getLibrary().setCard(1, 2, LIGHTBLUE1);

        p1.getLibrary().setCard(0, 3, BLUE1);
        p1.getLibrary().setCard(0, 4, BLUE1);
        p1.getLibrary().setCard(1, 3, BLUE2);
        p1.getLibrary().setCard(1, 4, BLUE1);

        p1.getLibrary().setCard(1, 0, YELLOW1);
        p1.getLibrary().setCard(1, 1, YELLOW3);
        p1.getLibrary().setCard(2, 0, YELLOW2);
        p1.getLibrary().setCard(2, 1, YELLOW1);

        p1.getLibrary().setCard(3, 0, BLUE2);
        p1.getLibrary().setCard(3, 1, BLUE3);
        p1.getLibrary().setCard(4, 1, BLUE1);
        p1.getLibrary().setCard(4, 2, BLUE1);

        p1.getLibrary().setCard(2, 2, WHITE2);
        p1.getLibrary().setCard(2, 3, WHITE1);
        p1.getLibrary().setCard(2, 4, WHITE2);
        p1.getLibrary().setCard(3, 2, WHITE1);
        p1.getLibrary().setCard(3, 3, WHITE2);
        p1.getLibrary().setCard(3, 4, WHITE3);
        p1.getLibrary().setCard(4, 3, WHITE2);
        p1.getLibrary().setCard(4, 4, WHITE1);
        p1.getLibrary().setCard(4, 0, WHITE1);
        p1.getLibrary().setCard(5, 0, WHITE1);
        p1.getLibrary().setCard(5, 1, WHITE3);
        p1.getLibrary().setCard(5, 2, WHITE2);
        p1.getLibrary().setCard(5, 3, WHITE1);
        p1.getLibrary().setCard(5, 4, WHITE1);



        Assert.assertTrue(obj.checkObj(p1.getLibrary()));
        p1.getLibrary().reset_lib();

    }

    @Test
    public void test_check6() throws Exception{

        CommonObj obj = new CommonObj(2, 6);

        // case true

        p1.getLibrary().setCard(0, 0, PURPLE1);
        p1.getLibrary().setCard(1, 0, GREEN3);
        p1.getLibrary().setCard(2, 0, LIGHTBLUE3);
        p1.getLibrary().setCard(3, 0, WHITE1);
        p1.getLibrary().setCard(4, 0, BLUE2);
        p1.getLibrary().setCard(5, 0, YELLOW1);

        p1.getLibrary().setCard(0, 3, GREEN1);
        p1.getLibrary().setCard(1, 3, PURPLE1);
        p1.getLibrary().setCard(2, 3, LIGHTBLUE1);
        p1.getLibrary().setCard(3, 3, WHITE2);
        p1.getLibrary().setCard(4, 3, YELLOW3);
        p1.getLibrary().setCard(5, 3, BLUE1);

        Assert.assertTrue(obj.checkObj(p1.getLibrary()));
        p1.getLibrary().reset_lib();

        // case false (cover if cases)

        p1.getLibrary().setCard(0, 0, NONE);
        p1.getLibrary().setCard(1, 0, GREEN2);
        p1.getLibrary().setCard(2, 0, LIGHTBLUE2);
        p1.getLibrary().setCard(3, 0, WHITE3);
        p1.getLibrary().setCard(4, 0, BLUE2);
        p1.getLibrary().setCard(5, 0, YELLOW2);

        p1.getLibrary().setCard(0, 3, GREEN1);
        p1.getLibrary().setCard(1, 3, PURPLE2);
        p1.getLibrary().setCard(2, 3, PURPLE1);
        p1.getLibrary().setCard(3, 3, WHITE1);
        p1.getLibrary().setCard(4, 3, YELLOW1);
        p1.getLibrary().setCard(5, 3, BLUE3);

        Assert.assertFalse(obj.checkObj(p1.getLibrary()));
        p1.getLibrary().reset_lib();
    }


    @Test
    public void test_check7() throws Exception{

        CommonObj obj = new CommonObj(2, 7);

        // case two adjacent square (false)

        p1.getLibrary().setCard(0, 0, BLUE1);
        p1.getLibrary().setCard(1, 0, BLUE3);
        p1.getLibrary().setCard(0, 1, BLUE2);
        p1.getLibrary().setCard(1, 1, BLUE1);

        p1.getLibrary().setCard(2, 0, BLUE1);
        p1.getLibrary().setCard(2, 1, BLUE1);
        p1.getLibrary().setCard(3, 0, BLUE2);
        p1.getLibrary().setCard(3, 1, BLUE3);

        Assert.assertFalse(obj.checkObj(p1.getLibrary()));
        p1.getLibrary().reset_lib();

        // case true (two squares not adjacent)

        p1.getLibrary().setCard(0, 0, BLUE1);
        p1.getLibrary().setCard(1, 0, BLUE2);
        p1.getLibrary().setCard(0, 1, BLUE1);
        p1.getLibrary().setCard(1, 1, BLUE3);

        p1.getLibrary().setCard(3, 2, BLUE1);
        p1.getLibrary().setCard(3, 3, BLUE2);
        p1.getLibrary().setCard(4, 2, BLUE1);
        p1.getLibrary().setCard(4, 3, BLUE1);

        Assert.assertTrue(obj.checkObj(p1.getLibrary()));
        p1.getLibrary().reset_lib();


        //case true (two squares of different colors)

        p1.getLibrary().setCard(0,0,PURPLE3);
        p1.getLibrary().setCard(1,0,PURPLE1);
        p1.getLibrary().setCard(0,1,PURPLE2);
        p1.getLibrary().setCard(1,1,PURPLE1);

        p1.getLibrary().setCard(3,2,BLUE3);
        p1.getLibrary().setCard(3,3,BLUE1);
        p1.getLibrary().setCard(4,2,BLUE2);
        p1.getLibrary().setCard(4,3,BLUE1);

        Assert.assertTrue(obj.checkObj(p1.getLibrary()));
        p1.getLibrary().reset_lib();

        //case false(one perfect square and one square not perfect of different colors)

        p1.getLibrary().setCard(0,0,PURPLE3);
        p1.getLibrary().setCard(1,0,PURPLE1);
        p1.getLibrary().setCard(0,1,PURPLE2);
        p1.getLibrary().setCard(1,1,PURPLE3);

        p1.getLibrary().setCard(3,2,BLUE2);
        p1.getLibrary().setCard(3,3,BLUE1);
        p1.getLibrary().setCard(4,2,BLUE2);
        p1.getLibrary().setCard(4,3,BLUE1);
        p1.getLibrary().setCard(2,2,BLUE3);

        Assert.assertTrue(!obj.checkObj(p1.getLibrary()));
        p1.getLibrary().reset_lib();

    }

    @Test
    public void test_check8() throws Exception {

        CommonObj obj = new CommonObj(2, 8);

        // case true

        p1.getLibrary().setCard(0, 0, BLUE1);
        p1.getLibrary().setCard(0, 1, LIGHTBLUE1);
        p1.getLibrary().setCard(0, 2, YELLOW2);
        p1.getLibrary().setCard(0, 3, WHITE2);
        p1.getLibrary().setCard(0, 4, GREEN2);

        p1.getLibrary().setCard(1, 0, GREEN3);
        p1.getLibrary().setCard(1, 1, LIGHTBLUE2);
        p1.getLibrary().setCard(1, 2, YELLOW3);
        p1.getLibrary().setCard(1, 3, WHITE3);
        p1.getLibrary().setCard(1, 4, PURPLE1);


        Assert.assertTrue(obj.checkObj(p1.getLibrary()));
        p1.getLibrary().reset_lib();

        // case one row not completed and one row with 2 different type (coverage of if cases, result: false)

        p1.getLibrary().setCard(0, 0, BLUE2);
        p1.getLibrary().setCard(0, 1, LIGHTBLUE3);
        p1.getLibrary().setCard(0, 2, YELLOW1);
        p1.getLibrary().setCard(0, 3, WHITE1);
        p1.getLibrary().setCard(0, 4, NOT);

        p1.getLibrary().setCard(1, 0, GREEN1);
        p1.getLibrary().setCard(1, 1, LIGHTBLUE1);
        p1.getLibrary().setCard(1, 2, YELLOW2);
        p1.getLibrary().setCard(1, 3, WHITE3);
        p1.getLibrary().setCard(1, 4, GREEN3);


        Assert.assertFalse(obj.checkObj(p1.getLibrary()));
        p1.getLibrary().reset_lib();

        //case one row not completed

        p1.getLibrary().setCard(0, 0, BLUE3);
        p1.getLibrary().setCard(0, 1, LIGHTBLUE3);
        p1.getLibrary().setCard(0, 2, YELLOW1);
        p1.getLibrary().setCard(0, 3, WHITE1);

        p1.getLibrary().setCard(1, 0, GREEN2);
        p1.getLibrary().setCard(1, 1, LIGHTBLUE2);
        p1.getLibrary().setCard(1, 2, YELLOW1);
        p1.getLibrary().setCard(1, 3, WHITE2);
        p1.getLibrary().setCard(1, 4, PURPLE3);


        Assert.assertFalse(obj.checkObj(p1.getLibrary()));
        p1.getLibrary().reset_lib();

        //case row with 2 different types

        p1.getLibrary().setCard(0, 0, BLUE2);
        p1.getLibrary().setCard(0, 1, LIGHTBLUE1);
        p1.getLibrary().setCard(0, 2, YELLOW2);
        p1.getLibrary().setCard(0, 3, WHITE2);
        p1.getLibrary().setCard(0, 4, GREEN1);

        p1.getLibrary().setCard(1, 0, GREEN2);
        p1.getLibrary().setCard(1, 1, LIGHTBLUE2);
        p1.getLibrary().setCard(1, 2, YELLOW3);
        p1.getLibrary().setCard(1, 3, WHITE3);
        p1.getLibrary().setCard(1, 4, GREEN1);


        Assert.assertFalse(obj.checkObj(p1.getLibrary()));
        p1.getLibrary().reset_lib();

    }

    @Test
    public void test_check9() throws Exception {

        CommonObj obj = new CommonObj(2, 9);

        // case true

        p1.getLibrary().setCard(0, 0, WHITE1);
        p1.getLibrary().setCard(1, 0, WHITE1);
        p1.getLibrary().setCard(2, 0, LIGHTBLUE1);
        p1.getLibrary().setCard(3, 0, YELLOW2);
        p1.getLibrary().setCard(4, 0, WHITE2);
        p1.getLibrary().setCard(5, 0, WHITE1);

        p1.getLibrary().setCard(0, 3, GREEN3);
        p1.getLibrary().setCard(1, 3, GREEN2);
        p1.getLibrary().setCard(2, 3, LIGHTBLUE3);
        p1.getLibrary().setCard(3, 3, GREEN1);
        p1.getLibrary().setCard(4, 3, GREEN2);
        p1.getLibrary().setCard(5, 3, LIGHTBLUE1);

        p1.getLibrary().setCard(0, 4, GREEN1);
        p1.getLibrary().setCard(1, 4, GREEN1);
        p1.getLibrary().setCard(2, 4, GREEN3);
        p1.getLibrary().setCard(3, 4, GREEN2);
        p1.getLibrary().setCard(4, 4, GREEN1);
        p1.getLibrary().setCard(5, 4, GREEN2);

        Assert.assertTrue(obj.checkObj(p1.getLibrary()));
        p1.getLibrary().reset_lib();

        // case false (one column with NONE, one column with more than 3 different cards: if cases coverage)

        p1.getLibrary().setCard(0, 0, NONE);
        p1.getLibrary().setCard(1, 0, WHITE3);
        p1.getLibrary().setCard(2, 0, LIGHTBLUE2);
        p1.getLibrary().setCard(3, 0, YELLOW1);
        p1.getLibrary().setCard(4, 0, WHITE2);
        p1.getLibrary().setCard(5, 0, BLUE1);

        p1.getLibrary().setCard(0, 3, GREEN3);
        p1.getLibrary().setCard(1, 3, WHITE1);
        p1.getLibrary().setCard(2, 3, LIGHTBLUE3);
        p1.getLibrary().setCard(3, 3, YELLOW2);
        p1.getLibrary().setCard(4, 3, GREEN1);
        p1.getLibrary().setCard(5, 3, LIGHTBLUE1);

        p1.getLibrary().setCard(0, 4, GREEN3);
        p1.getLibrary().setCard(1, 4, GREEN2);
        p1.getLibrary().setCard(2, 4, GREEN1);
        p1.getLibrary().setCard(3, 4, GREEN1);
        p1.getLibrary().setCard(4, 4, GREEN3);
        p1.getLibrary().setCard(5, 4, GREEN2);

        Assert.assertFalse(obj.checkObj(p1.getLibrary()));
        p1.getLibrary().reset_lib();

        // case false, one column not completed

        p1.getLibrary().setCard(0, 0, NONE);
        p1.getLibrary().setCard(1, 0, WHITE3);
        p1.getLibrary().setCard(2, 0, LIGHTBLUE1);
        p1.getLibrary().setCard(3, 0, YELLOW3);
        p1.getLibrary().setCard(4, 0, WHITE2);
        p1.getLibrary().setCard(5, 0, BLUE1);

        p1.getLibrary().setCard(0, 3, GREEN1);
        p1.getLibrary().setCard(1, 3, GREEN3);
        p1.getLibrary().setCard(2, 3, LIGHTBLUE2);
        p1.getLibrary().setCard(3, 3, GREEN1);
        p1.getLibrary().setCard(4, 3, GREEN1);
        p1.getLibrary().setCard(5, 3, LIGHTBLUE3);

        p1.getLibrary().setCard(0, 4, GREEN2);
        p1.getLibrary().setCard(1, 4, GREEN1);
        p1.getLibrary().setCard(2, 4, GREEN3);
        p1.getLibrary().setCard(3, 4, GREEN1);
        p1.getLibrary().setCard(4, 4, GREEN2);
        p1.getLibrary().setCard(5, 4, GREEN1);

        Assert.assertFalse(obj.checkObj(p1.getLibrary()));
        p1.getLibrary().reset_lib();

        //case false, one column with more than 3 different types of cards

        p1.getLibrary().setCard(0, 0, WHITE1);
        p1.getLibrary().setCard(1, 0, WHITE2);
        p1.getLibrary().setCard(2, 0, LIGHTBLUE3);
        p1.getLibrary().setCard(3, 0, YELLOW3);
        p1.getLibrary().setCard(4, 0, WHITE3);
        p1.getLibrary().setCard(5, 0, WHITE2);

        p1.getLibrary().setCard(0, 3, GREEN1);
        p1.getLibrary().setCard(1, 3, WHITE1);
        p1.getLibrary().setCard(2, 3, LIGHTBLUE2);
        p1.getLibrary().setCard(3, 3, YELLOW2);
        p1.getLibrary().setCard(4, 3, GREEN1);
        p1.getLibrary().setCard(5, 3, LIGHTBLUE3);

        p1.getLibrary().setCard(0, 4, GREEN3);
        p1.getLibrary().setCard(1, 4, GREEN1);
        p1.getLibrary().setCard(2, 4, GREEN2);
        p1.getLibrary().setCard(3, 4, GREEN1);
        p1.getLibrary().setCard(4, 4, GREEN1);
        p1.getLibrary().setCard(5, 4, GREEN2);

        Assert.assertFalse(obj.checkObj(p1.getLibrary()));
        p1.getLibrary().reset_lib();


    }

    @Test
    public void test_check10() throws Exception {

        CommonObj obj = new CommonObj(2, 10);

        // case true

        p1.getLibrary().setCard(2, 0, PURPLE2);
        p1.getLibrary().setCard(3, 1, PURPLE1);
        p1.getLibrary().setCard(2, 2, PURPLE1);
        p1.getLibrary().setCard(4, 0, PURPLE1);
        p1.getLibrary().setCard(4, 2, PURPLE2);

        Assert.assertTrue(obj.checkObj(p1.getLibrary()));
        p1.getLibrary().reset_lib();

        // case false

        p1.getLibrary().setCard(2, 0, PURPLE3);
        p1.getLibrary().setCard(3, 1, PURPLE1);
        p1.getLibrary().setCard(2, 2, PURPLE2);
        p1.getLibrary().setCard(4, 0, PURPLE1);
        p1.getLibrary().setCard(4, 2, WHITE3);

        Assert.assertFalse(obj.checkObj(p1.getLibrary()));
        p1.getLibrary().reset_lib();

        p1.getLibrary().setCard(3, 1, PURPLE2);
        p1.getLibrary().setCard(2, 2, PURPLE1);
        p1.getLibrary().setCard(4, 0, GREEN3);
        p1.getLibrary().setCard(4, 2, PURPLE2);

        Assert.assertFalse(obj.checkObj(p1.getLibrary()));
        p1.getLibrary().reset_lib();

        p1.getLibrary().setCard(3, 1, PURPLE3);
        p1.getLibrary().setCard(2, 2, GREEN2);
        p1.getLibrary().setCard(4, 0, PURPLE2);
        p1.getLibrary().setCard(4, 2, PURPLE1);

        Assert.assertFalse(obj.checkObj(p1.getLibrary()));
        p1.getLibrary().reset_lib();

        p1.getLibrary().setCard(3, 1, GREEN1);
        p1.getLibrary().setCard(2, 2, PURPLE2);
        p1.getLibrary().setCard(4, 0, PURPLE2);
        p1.getLibrary().setCard(4, 2, PURPLE1);

        Assert.assertFalse(obj.checkObj(p1.getLibrary()));
        p1.getLibrary().reset_lib();
    }

    @Test
    public void test_check11() throws Exception {

        CommonObj obj = new CommonObj(2, 11);

        // case true

        p1.getLibrary().setCard(3, 1, PURPLE2);
        p1.getLibrary().setCard(4, 2, PURPLE1);
        p1.getLibrary().setCard(4, 0, PURPLE3);
        p1.getLibrary().setCard(1, 2, PURPLE1);
        p1.getLibrary().setCard(5, 1, PURPLE1);
        p1.getLibrary().setCard(1, 1, PURPLE2);
        p1.getLibrary().setCard(5, 0, PURPLE1);
        p1.getLibrary().setCard(2, 2, PURPLE3);

        Assert.assertTrue(obj.checkObj(p1.getLibrary()));
        p1.getLibrary().reset_lib();



        // case false

        p1.getLibrary().setCard(3, 1, PURPLE1);
        p1.getLibrary().setCard(4, 2, PURPLE1);
        p1.getLibrary().setCard(4, 0, PURPLE2);
        p1.getLibrary().setCard(1, 2, PURPLE1);
        p1.getLibrary().setCard(5, 1, PURPLE3);
        p1.getLibrary().setCard(1, 1, PURPLE1);
        p1.getLibrary().setCard(5, 0, PURPLE1);
        p1.getLibrary().setCard(2, 2, WHITE2);

        Assert.assertFalse(obj.checkObj(p1.getLibrary()));
        p1.getLibrary().reset_lib();
    }

    @Test
    public void test_check12() throws Exception {

        CommonObj obj = new CommonObj(2, 12);

        // two cases true:

        for(int j = 0; j < 5; j++){
            for(int i = 5; i > j; i--){
                p1.getLibrary().setCard(i, j, GREEN3);
            }
        }

        Assert.assertTrue(obj.checkObj(p1.getLibrary()));
        p1.getLibrary().reset_lib();

        for(int j = 0; j < 5; j++){
            for(int i = 5; i > 4 - j; i--){
                p1.getLibrary().setCard(i, j, WHITE1);
            }
        }

        Assert.assertTrue(obj.checkObj(p1.getLibrary()));
        p1.getLibrary().reset_lib();

        // the other cases are false:
        for(int j = 0; j < 6; j++){
            for(int i = 0; i < 5; i++){
                p1.getLibrary().setCard(j, i, WHITE2);
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
