package it.polimi.ingsw.Model;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Random;

public class TestSingleObj {

    PlayerObj obj;


    @Before
    public void setUp() throws Exception {
        obj = new PlayerObj();
    }


    @Test
    public void testConstructor() throws Exception {

        Random random = new Random();
        int x = random.nextInt(5)+1;
        int y = random.nextInt(6)+1;

        SingleObj tail = new SingleObj(x,y, Card.GREEN1);
        SingleObj copy = tail;
        Assert.assertEquals(x, tail.getXPosition());
        Assert.assertEquals(y, tail.getYPosition());
        Assert.assertEquals(Card.GREEN1, tail.getType());
        Assert.assertEquals(copy,tail);
    }
}
