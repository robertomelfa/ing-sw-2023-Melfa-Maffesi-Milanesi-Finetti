package it.polimi.ingsw.Model;

import junit.framework.TestCase;
import org.junit.Test;

public class TestCardBox extends TestCase {

    @Test
    public void testgetCard__validcard(){
        CardBox cardbox = new CardBox();
        Card card;
        card=cardbox.getCard();
        assertTrue(card!=Card.NONE && card!=Card.NOT);
    }

    public void testgetCard_cardboxempty_NONE(){
        CardBox cardbox = new CardBox();
        Card card;
        for (int i=0;i<132;i++){
            card=cardbox.getCard();
        }
        card=cardbox.getCard();
        assertTrue(card==Card.NONE);
    }

}
