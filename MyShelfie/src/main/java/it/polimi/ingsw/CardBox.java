package it.polimi.ingsw;

import java.util.*;

public class CardBox {
//    int cardLeft, leftWhite, leftBlue, leftLightBlue, leftYellow, leftPurple, leftGreen;

    List<Card> cardBox = new LinkedList<>();
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
    public Card getCard() {
        Random rn = new Random();


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
}
