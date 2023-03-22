package it.polimi.ingsw;

public class Cardbox {
    int cardLeft, leftWhite, leftBlue, leftLightBlue, leftYellow, leftPurple, leftGreen;

    private Cardbox(){
        cardLeft = 132;
        leftWhite = 22;
        leftBlue= 22;
        leftLightBlue= 22;
        leftYellow = 22;
        leftPurple = 22;
        leftGreen = 22;
    }
    public Card getCard(){
        Random rn = new Random();
        int rand = rn.nextInt(1,cardLeft + 1);
        if (rand > 0 && rand <= leftWhite) {
            cardLeft--;
            leftWhite--;
            return Card.WHITE;
        } else if(rand > leftWhite && rand <= leftWhite + leftBlue ) {
            cardLeft--;
            leftBlue--;
            return Card.BLUE;
        } else if(rand > leftWhite + leftBlue && rand <= leftWhite + leftBlue + leftLightBlue) {
            cardLeft--;
            leftLightBlue--;
            return Card.LIGHTBLUE;
        } else if(rand > leftWhite + leftBlue + leftLightBlue && rand <= leftWhite + leftBlue + leftLightBlue + leftYellow) {
            cardLeft--;
            leftYellow--;
            return Card.YELLOW;
        } else if(rand > leftWhite + leftBlue + leftLightBlue + leftYellow && rand <= leftWhite + leftBlue + leftLightBlue + leftYellow + leftPurple) {
            cardLeft--;
            leftPurple--;
            return Card.PURPLE;
        } else {
            cardLeft--;
            leftGreen--;
            return Card.GREEN;
        }



}
