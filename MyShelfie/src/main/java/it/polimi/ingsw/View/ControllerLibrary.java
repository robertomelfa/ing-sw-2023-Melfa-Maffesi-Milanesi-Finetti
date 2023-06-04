package it.polimi.ingsw.View;

import it.polimi.ingsw.View.PathImageCards;
import it.polimi.ingsw.Model.Card;
import it.polimi.ingsw.Model.Library;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;

import java.io.Serializable;
import java.util.Random;

public class ControllerLibrary implements Serializable {

    @FXML
    private GridPane grid;

    @FXML
    private ImageView[][] libraryImageView = new ImageView[5][6];

    private boolean first = true;

// TODO una volta verificato che funziona eliminare metodo e bottone prova

    // test per verificare che funzioni, da rimpiazzare con click su librerie giocatori
    public void setLibrary(){
        /*
        Library library= new Library();

        library.getGrid() [0][0] = Card.WHITE;   library.getGrid()[0][1]=Card.WHITE;   library.getGrid()[0][2]=Card.LIGHTBLUE;   library.getGrid()[0][3]=Card.PURPLE;   library.getGrid()[0][4]=Card.YELLOW;

        library.getGrid()[1][0]=Card.BLUE;   library.getGrid()[1][1]=Card.WHITE;   library.getGrid()[1][2]=Card.WHITE;   library.getGrid()[1][3]=Card.WHITE;   library.getGrid()[1][4]=Card.LIGHTBLUE;

        library.getGrid()[2][0]=Card.YELLOW;   library.getGrid()[2][1]=Card.BLUE;   library.getGrid()[2][2]=Card.YELLOW;   library.getGrid()[2][3]=Card.PURPLE;   library.getGrid()[2][4]=Card.YELLOW;

        library.getGrid()[3][0]=Card.LIGHTBLUE;   library.getGrid()[3][1]=Card.BLUE;   library.getGrid()[3][2]=Card.PURPLE;   library.getGrid()[3][3]=Card.BLUE;   library.getGrid()[3][4]=Card.LIGHTBLUE;

        library.getGrid()[4][0]=Card.WHITE;   library.getGrid()[4][1]=Card.WHITE;   library.getGrid()[4][2]=Card.LIGHTBLUE;   library.getGrid()[4][3]=Card.GREEN;   library.getGrid()[4][4]=Card.GREEN;

        library.getGrid()[5][0]=Card.LIGHTBLUE;   library.getGrid()[5][1]=Card.WHITE;   library.getGrid()[5][2]=Card.BLUE;   library.getGrid()[5][3]=Card.GREEN;   library.getGrid()[5][4]=Card.GREEN;

        updateLibrary(library);
        */
    }

    public void updateLibrary(Library library) {

        if(first){
            for (int j = 0; j < 5; j++) {
                for (int i = 0; i < 6; i++) {
                    ImageView card = new ImageView();
                    card.setFitHeight(26);
                    card.setFitWidth(24);
                    libraryImageView[j][i] = card;
                    grid.add(card, j, i);
                }
            }
            first = false;
        }

        for (int j=0; j<5; j++){
            for (int i=0; i<6; i++){

                String url = urlCard(library.getPos(i,j));

                if(libraryImageView[j][i].getImage()==null && url != null){
                    Image image = new Image(url);
                    libraryImageView[j][i].setImage(image);
                }
            }
        }
    }

    private String urlCard(Card card) {
        Random rand = new Random();
        int r = rand.nextInt(3)+1;
        String url = null;
        switch (card){
            case LIGHTBLUE1 -> {
                url = PathImageCards.TROFEI1;

            }
            case LIGHTBLUE2 -> {
                url = PathImageCards.TROFEI2;

            }
            case LIGHTBLUE3 -> {
                url = PathImageCards.TROFEI3;

            }
            case BLUE1 ->{
                url = PathImageCards.CORNICI1;
            }
            case BLUE2 ->{
                url = PathImageCards.CORNICI2;
            }
            case BLUE3 ->{
                url = PathImageCards.CORNICI3;
            }
            case YELLOW1 ->{
                url = PathImageCards.GIOCHI1;
            }
            case YELLOW2 ->{
                url = PathImageCards.GIOCHI2;
            }
            case YELLOW3 ->{
                url = PathImageCards.GIOCHI3;
            }
            case GREEN1 ->{
                url = PathImageCards.GATTI1;
            }
            case GREEN2 ->{
                url = PathImageCards.GATTI2;
            }
            case GREEN3 ->{
                url = PathImageCards.GATTI3;
            }
            case WHITE1 ->{
                url = PathImageCards.LIBRI1;
            }
            case WHITE2 ->{
                url = PathImageCards.LIBRI2;
            }
            case WHITE3 ->{
                url = PathImageCards.LIBRI3;
            }
            case PURPLE1 ->{
                url = PathImageCards.PIANTE1;
            }
            case PURPLE2 ->{
                url = PathImageCards.PIANTE2;
            }
            case PURPLE3 ->{
                url = PathImageCards.PIANTE3;
            }
        }
        return url;
    }


}
