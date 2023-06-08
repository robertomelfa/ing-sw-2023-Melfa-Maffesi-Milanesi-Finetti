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

    public void updateLibrary(Library library) {

        if(first){
            grid.setPadding(new Insets(4, 4, 4, 4));
            grid.setVgap(4);
            grid.setHgap(10);
            for (int j = 0; j < 5; j++) {
                for (int i = 0; i < 6; i++) {
                    ImageView card = new ImageView();
                    card.setFitHeight(35);
                    card.setFitWidth(36);
                    libraryImageView[j][i] = card;
                    grid.add(card, j, i);
                }
            }
            first = false;
        }

        for (int j=0; j<5; j++){
            for (int i=0; i<6; i++){

                ControllerGui c = new ControllerGui();
                String url = c.urlCard(library.getPos(i,j));

                if(libraryImageView[j][i].getImage()==null && url != null){
                    Image image = new Image(url);
                    libraryImageView[j][i].setImage(image);
                }
            }
        }
    }

}
