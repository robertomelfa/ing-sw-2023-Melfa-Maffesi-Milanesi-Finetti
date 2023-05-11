package it.polimi.ingsw.Model;

import it.polimi.ingsw.Model.Card;
import it.polimi.ingsw.Model.PlayerObj;
import it.polimi.ingsw.Model.SingleObj;
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

        SingleObj tail = new SingleObj(x,y, Card.GREEN);
        SingleObj copy = tail;
        Assert.assertEquals(x, tail.getXPosition());
        Assert.assertEquals(y, tail.getYPosition());
        Assert.assertEquals(Card.GREEN, tail.getType());
        Assert.assertEquals(copy,tail);
    }
}
