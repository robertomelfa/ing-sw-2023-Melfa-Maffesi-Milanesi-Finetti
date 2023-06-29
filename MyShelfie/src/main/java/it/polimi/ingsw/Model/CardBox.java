package it.polimi.ingsw.Model;

import java.util.*;
import java.io.Serializable;

public class CardBox implements Serializable{
//    int cardLeft, leftWhite, leftBlue, leftLightBlue, leftYellow, leftPurple, leftGreen;

    private List<Card> cardBox = new LinkedList<>();

    /**
     * Constructor for the CardBox class.
     * It initializes a list of cards of different colors (22 cards for each color) and it shuffles the list
     */

    public CardBox(){           // public to private

        for (int i=0; i < 8; i++){
            cardBox.add(Card.WHITE1);
            cardBox.add(Card.BLUE1);
            cardBox.add(Card.LIGHTBLUE1);
            cardBox.add(Card.YELLOW1);
            cardBox.add(Card.PURPLE1);
            cardBox.add(Card.GREEN1);
            cardBox.add(Card.WHITE2);
            cardBox.add(Card.BLUE2);
            cardBox.add(Card.LIGHTBLUE2);
            cardBox.add(Card.YELLOW2);
            cardBox.add(Card.PURPLE2);
            cardBox.add(Card.GREEN2);
            cardBox.add(Card.WHITE3);
            cardBox.add(Card.BLUE3);
            cardBox.add(Card.LIGHTBLUE3);
            cardBox.add(Card.YELLOW3);
            cardBox.add(Card.PURPLE3);
            cardBox.add(Card.GREEN3);
        }
        cardBox.add(Card.WHITE3);
        cardBox.add(Card.BLUE3);
        cardBox.add(Card.LIGHTBLUE3);
        cardBox.add(Card.YELLOW3);
        cardBox.add(Card.PURPLE3);
        cardBox.add(Card.GREEN3);

        Collections.shuffle(cardBox);

    }

    /**
     * Method that picks a card from the cardbox list
     * @return the card picked from the cardbox
     */
    public Card getCard() {

        Card temp;

        if (cardBox.isEmpty()) return Card.NONE;
        else{
            temp = cardBox.get(cardBox.size()-1);
            cardBox.remove(cardBox.size()-1);
            return temp;
        }
    }
}
