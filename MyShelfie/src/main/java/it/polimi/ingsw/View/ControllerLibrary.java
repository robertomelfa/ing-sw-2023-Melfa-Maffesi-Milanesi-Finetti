package it.polimi.ingsw.View;

import it.polimi.ingsw.Model.Card;
import it.polimi.ingsw.Model.Library;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;

public class ControllerLibrary {

    private static final String CORNICI1 = "assets/item tiles/Cornici1.1.png";
    private static final String CORNICI2 = "assets/item tiles/Cornici1.2.png";
    private static final String CORNICI3 = "assets/item tiles/Cornici1.3.png";
    private static final String GATTI1 = "assets/item tiles/Gatti1.1.png";
    private static final String GATTI2 = "assets/item tiles/Gatti1.2.png";
    private static final String GATTI3 = "assets/item tiles/Gatti1.3.png";
    private static final String GIOCHI1 = "assets/item tiles/Giochi1.1.png";
    private static final String GIOCHI2 = "assets/item tiles/Giochi1.2.png";
    private static final String GIOCHI3 = "assets/item tiles/Giochi1.3.png";
    private static final String LIBRI1 = "assets/item tiles/Libri1.1.png";
    private static final String LIBRI2 = "assets/item tiles/Libri1.2.png";
    private static final String LIBRI3 = "assets/item tiles/Libri1.3.png";
    private static final String PIANTE1 = "assets/item tiles/Piante1.1.png";
    private static final String PIANTE2 = "assets/item tiles/Piante1.2.png";
    private static final String PIANTE3 = "assets/item tiles/Piante1.3.png";
    private static final String TROFEI1 = "assets/item tiles/Trofei1.1.png";
    private static final String TROFEI2 = "assets/item tiles/Trofei1.2.png";
    private static final String TROFEI3 = "assets/item tiles/Trofei1.3.png";





    @FXML
    private GridPane grid;



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

    public void updateLibrary(Library library){
        Image image ;
        ImageView card ;
        grid.setPadding(new Insets(4, 4, 4, 4) );
        grid.setHgap(10);

        for (int i=0; i<6; i++){
            for (int j=0; j<5; j++){
                image = null;
                switch (library.getPos(i,j)){
                    case PURPLE -> image = new Image(CORNICI1);         // ( cornici1 + rand + ".png" );
                    case WHITE -> image = new Image(GATTI1);
                    case GREEN -> image = new Image(GIOCHI1);
                    case LIGHTBLUE -> image = new Image(LIBRI1);
                    case BLUE -> image = new Image(PIANTE1);
                    case YELLOW -> image = new Image(TROFEI1);
                }
                if (image!=null) {
                    card = new ImageView(image);
                    card.setFitHeight(35);
                    card.setFitWidth(36);
                    grid.add(card,j,i);
                }
            }
        }
    }


}
