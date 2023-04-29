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
//        cardLeft = 132;
//        leftWhite = 22;
//        leftBlue= 22;
//        leftLightBlue= 22;
//        leftYellow = 22;
//        leftPurple = 22;
//        leftGreen = 22;

        for (int i=0; i<22; i++){
            cardBox.add(Card.WHITE);
            cardBox.add(Card.BLUE);
            cardBox.add(Card.LIGHTBLUE);
            cardBox.add(Card.YELLOW);
            cardBox.add(Card.PURPLE);
            cardBox.add(Card.GREEN);
        }
        Collections.shuffle(cardBox);

    }

    /**
     * Method that picks a card from the cardbox list
     * @return the card picked from the cardbox
     */
    public Card getCard() {
//      simulation of the cardbox
//
//        int rand = rn.nextInt(1, cardLeft + 1);
//
//
//        if (rand > 0 && rand <= leftWhite) {
//            cardLeft--;
//            leftWhite--;
//            return Card.WHITE;
//        } else if (rand > leftWhite && rand <= leftWhite + leftBlue) {
//            cardLeft--;
//            leftBlue--;
//            return Card.BLUE;
//        } else if (rand > leftWhite + leftBlue && rand <= leftWhite + leftBlue + leftLightBlue) {
//            cardLeft--;
//            leftLightBlue--;
//            return Card.LIGHTBLUE;
//        } else if (rand > leftWhite + leftBlue + leftLightBlue && rand <= leftWhite + leftBlue + leftLightBlue + leftYellow) {
//            cardLeft--;
//            leftYellow--;
//            return Card.YELLOW;
//        } else if (rand > leftWhite + leftBlue + leftLightBlue + leftYellow && rand <= leftWhite + leftBlue + leftLightBlue + leftYellow + leftPurple) {
//            cardLeft--;
//            leftPurple--;
//            return Card.PURPLE;
//        } else {
//            cardLeft--;
//            leftGreen--;
//            return Card.GREEN;
//        }
//

        Card temp;

        if (cardBox.isEmpty()) return Card.NONE;
        else{
            temp = cardBox.get(cardBox.size()-1);
            cardBox.remove(cardBox.size()-1);
            return temp;
        }
    }

    public List<Card> getCardBox() {
        return cardBox;
    }
}
