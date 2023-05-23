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

import java.util.Random;

public class ControllerLibrary {

    @FXML
    private GridPane grid;

// TODO una volta verificato che funziona eliminare metodo e bottone prova

    // test per verificare che funzioni, da rimpiazzare con click su librerie giocatori
    public void setLibrary(){
        Library library= new Library();

        library.getGrid() [0][0] = Card.WHITE;   library.getGrid()[0][1]=Card.WHITE;   library.getGrid()[0][2]=Card.LIGHTBLUE;   library.getGrid()[0][3]=Card.PURPLE;   library.getGrid()[0][4]=Card.YELLOW;

        library.getGrid()[1][0]=Card.BLUE;   library.getGrid()[1][1]=Card.WHITE;   library.getGrid()[1][2]=Card.WHITE;   library.getGrid()[1][3]=Card.WHITE;   library.getGrid()[1][4]=Card.LIGHTBLUE;

        library.getGrid()[2][0]=Card.YELLOW;   library.getGrid()[2][1]=Card.BLUE;   library.getGrid()[2][2]=Card.YELLOW;   library.getGrid()[2][3]=Card.PURPLE;   library.getGrid()[2][4]=Card.YELLOW;

        library.getGrid()[3][0]=Card.LIGHTBLUE;   library.getGrid()[3][1]=Card.BLUE;   library.getGrid()[3][2]=Card.PURPLE;   library.getGrid()[3][3]=Card.BLUE;   library.getGrid()[3][4]=Card.LIGHTBLUE;

        library.getGrid()[4][0]=Card.WHITE;   library.getGrid()[4][1]=Card.WHITE;   library.getGrid()[4][2]=Card.LIGHTBLUE;   library.getGrid()[4][3]=Card.GREEN;   library.getGrid()[4][4]=Card.GREEN;

        library.getGrid()[5][0]=Card.LIGHTBLUE;   library.getGrid()[5][1]=Card.WHITE;   library.getGrid()[5][2]=Card.BLUE;   library.getGrid()[5][3]=Card.GREEN;   library.getGrid()[5][4]=Card.GREEN;

        updateLibrary(library);

    }

    public void updateLibrary(Library library) {

        if (library != null) {
            Image image;
            ImageView card;
            grid.setPadding(new Insets(4, 4, 4, 4));
            grid.setHgap(10);

            ControllerGui c = new ControllerGui();

            for (int i = 0; i < 6; i++) {
                for (int j = 0; j < 5; j++) {

                    String url = c.urlCard(library.getPos(i, j));
                    image = new Image(url);

                    if (image != null) {
                        card = new ImageView(image);
                        card.setFitHeight(35);
                        card.setFitWidth(36);
                        grid.add(card, j, i);
                    }
                }
            }
        }
    }


}
