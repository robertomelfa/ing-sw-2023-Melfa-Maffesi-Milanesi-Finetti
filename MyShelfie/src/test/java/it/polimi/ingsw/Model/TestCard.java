package it.polimi.ingsw.Model;

import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.Test;

public class TestCard extends TestCase {
    @Test
    public void testcompare_yellow_true(){
        Card card = Card.YELLOW1;
        Assert.assertTrue(card.compare("YELLOW"));
    }

    public void testcompare_yellow_false(){
        Card card = Card.YELLOW1;
        Assert.assertTrue(!card.compare("GREEN"));
    }

    public void testcompare_blue_true(){
        Card card = Card.BLUE3;
        Assert.assertTrue(card.compare("BLUE"));
    }

    public void testcompare_blue_false(){
        Card card = Card.BLUE3;
        Assert.assertTrue(!card.compare("WHITE"));
    }

    public void testisEqualTo_purple_true(){
        Card card1 = Card.PURPLE2;
        Card card2 = Card.PURPLE1;
        Assert.assertTrue(card1.isEqualTo(card2));
    }

    public void testisEqualTo_purple_false(){
        Card card1 = Card.YELLOW1;
        Card card2 = Card.PURPLE1;
        Assert.assertTrue(!card1.isEqualTo(card2));
    }

}
