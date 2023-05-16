package it.polimi.ingsw.View;

import it.polimi.ingsw.Model.Card;
import it.polimi.ingsw.Model.GameLogic;
import it.polimi.ingsw.Model.GameTable;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.awt.event.ActionEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

import static it.polimi.ingsw.Model.Card.NONE;
import static it.polimi.ingsw.Model.Card.NOT;

public class ControllerGui {

    @FXML
    private ImageView CommonObj1;

    @FXML
    private ImageView CommonObj2;

    @FXML
    private ImageView LibraryPlayer1;

    @FXML
    private ImageView LibraryPlayer2;

    @FXML
    private ImageView LibraryPlayer3;

    @FXML
    private ImageView LibraryPlayer4;

    @FXML
    private ImageView background;

    @FXML
    private GridPane gridLibrary;

    @FXML
    private GridPane gridTable;
    @FXML
    private Label labelMessage;
    @FXML
    private ToggleButton button;

    @FXML
    private Button confirm;

    private ArrayList <Integer[]> posCard = new ArrayList<>();
    private int countCard = 0;
    private GameLogic gameLogic;


    @FXML
    void openDescription1(MouseEvent event) throws IOException {
        openDescription(1);
    }

    @FXML
    void openDescription2(MouseEvent event) throws IOException {
        openDescription(2);
    }


    @FXML
    void openDescription(int num) throws IOException {

        Stage stage = new Stage();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/comObjDesc.fxml"));
        Parent root = loader.load();
        stage.setTitle("description");
        stage.setScene(new Scene(root));
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(CommonObj1.getScene().getWindow());


        int numObj=0;
        final String prefix = "file:/C:/Users/mylan/Desktop/Progetto%20Software%20Engeniring/ing-sw-2023-Melfa-Maffesi-Milanesi-Finetti/MyShelfie/target/classes/assets/common goal cards/";

        // ho aggiunto metodo in model/CommonObj

        String url;
        if (num == 1) url = CommonObj1.getImage().getUrl();
        else if (num == 2) url = CommonObj2.getImage().getUrl();
        else url = null;

        switch (url){
            case prefix + "4.jpg" ->numObj=1;
            case prefix + "11.jpg" ->numObj=2;
            case prefix + "3.jpg" ->numObj=3;
            case prefix + "7.jpg" ->numObj=4;
            case prefix + "8.jpg" ->numObj=5;
            case prefix + "2.jpg" ->numObj=6;
            case prefix + "1.jpg" ->numObj=7;
            case prefix + "6.jpg" ->numObj=8;
            case prefix + "5.jpg" ->numObj=9;
            case prefix + "10.jpg" ->numObj=10;
            case prefix + "9.jpg" ->numObj=11;
            case prefix + "12.jpg" ->numObj=12;
        }

        // 4 - 11 - 3 - 7 - 8 - 2 - 1 - 6 - 5 - 10 - 9 - 12

        ControllerComObjDesc controller = loader.getController();
        controller.setLabelText(numObj);
        stage.show();
    }

    @FXML
    void openLibrary1(MouseEvent event) throws IOException {
        openLibrary(1);
    }

    @FXML
    void openLibrary2(MouseEvent event) throws IOException {
        openLibrary(2);
    }

    @FXML
    void openLibrary3(MouseEvent event) throws IOException {
        openLibrary(3);
    }

    @FXML
    void openLibrary4(MouseEvent event) throws IOException {
        openLibrary(4);
    }


        @FXML
    void openLibrary(int numPlayer) throws IOException {

        Stage stage = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Library.fxml"));
        Parent root = loader.load();
        stage.setTitle("Library");
        stage.setScene(new Scene(root));
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(CommonObj1.getScene().getWindow());

        //trovare modo di ottenere lista player
        // passo la Player.getLibrary
//        ControllerLibrary controller = loader.getController();
//        controller.updateLibrary(library);


        stage.show();
    }

    @FXML
    void openRules(MouseEvent event) throws IOException {

        //crea una nuova scena o apri un'immagine con il libretto regole

        Stage stage = new Stage();
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/RuleBook.fxml")));
        stage.setTitle("RuleBook");
        stage.setScene(new Scene(root));
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(CommonObj1.getScene().getWindow());
        stage.show();


    }


    @FXML
    void selecColumn(MouseEvent event) {

        ImageView[] gridLibrary;
//        gridLibrary =event.getSource();
    }

    @FXML
    void updateGameTable(GameTable gameTable){

        int k =0;
        for(int i = 1; i < 10; i++){
            for(int j = 1; j < 10; j++) {

                if (gameTable.getCardfromBoard(i,j)!= NOT) {
                    if (gameTable.getCardfromBoard(i,j)!= NONE) {
                        button = (ToggleButton) gridTable.getChildren().get(k);
                        button.setDisable(false);
                        button.selectedProperty().set(false);
                        ImageView image = (ImageView) button.getGraphic();
                        image.setImage(new Image("assets/boards/bookshelf.png"));
                    }else{
                        button = (ToggleButton) gridTable.getChildren().get(k);
                        button.setDisable(true);
                        button.selectedProperty().set(false);
                        ImageView image = (ImageView) button.getGraphic();
                        image.setImage(null);
                    }
                    k++;
                }
            }
        }
    }


    public void disableGameTable(){
        for(int i=0; i<gridTable.getChildren().size(); i++){
            button = (ToggleButton) gridTable.getChildren().get(i);
            button.setDisable(true);
        }
    }

    public void enableGameTable(){
        for(int i=0; i<gridTable.getChildren().size(); i++) {
            button = (ToggleButton) gridTable.getChildren().get(i);
            if (button.getGraphic()!=null) {
                button.setDisable(false);
            }
        }
    }

    public void unselectedGameTable(){
        for(int i=0; i<gridTable.getChildren().size(); i++){
            button = (ToggleButton) gridTable.getChildren().get(i);
            button.setSelected(false);
        }

    }

    @FXML
    void selectedCard(MouseEvent event) {

        this.button = (ToggleButton) event.getSource();

        //controllare se invertiti x & y
        String pos = button.getText();
        int x = (int) pos.charAt(0)-48;
        int y = (int) pos.charAt(1)-48;

        if (button.isSelected()) {
            System.out.println("clicked " +x+ " , " +y);
            addCountCard();
            posCard.add(new Integer[]{x,y});
        }
        else {
            System.out.println("unclicked " +x+ ", " +y);
            subCountCard();
            posCard.remove(new Integer[]{x, y});
        }

        confirm.setDisable(countCard > 3);

        System.out.println("you have selected : " +countCard+ " cards");
    }


    @FXML
    void controlCard(ActionEvent event) {

        switch (countCard){
            case 1 -> {
                if (gameLogic.checkNear(posCard.get(0)[0],posCard.get(0)[1])){
                    // manda arrayList a view
                    gameLogic.getGameTable().setCardfromBoard(posCard.get(0)[0], posCard.get(0)[1], NONE);
                    posCard.remove(0);
                }else{
                    String message = "Cards selected are not drawable. Choose other cards";
                    setLabelMessage(message);
                }
            }
            case 2 ->{
                gameLogic.checkNear(posCard.get(0)[0],posCard.get(0)[1],posCard.get(1)[0],posCard.get(1)[1]);
                gameLogic.getGameTable().setCardfromBoard(posCard.get(1)[0], posCard.get(1)[1], NONE);
                gameLogic.getGameTable().setCardfromBoard(posCard.get(0)[0], posCard.get(0)[1], NONE);
                posCard.remove(1);
                posCard.remove(0);
            }

            case 3 ->{
                gameLogic.checkNear(posCard.get(0)[0],posCard.get(0)[1],posCard.get(1)[0],posCard.get(1)[1],posCard.get(2)[0],posCard.get(2)[1]);
                gameLogic.getGameTable().setCardfromBoard(posCard.get(2)[0], posCard.get(2)[1], NONE);
                gameLogic.getGameTable().setCardfromBoard(posCard.get(1)[0], posCard.get(1)[1], NONE);
                gameLogic.getGameTable().setCardfromBoard(posCard.get(0)[0], posCard.get(0)[1], NONE);
                posCard.remove(2);
                posCard.remove(1);
                posCard.remove(0);
            }

        }

        unselectedGameTable();
        disableGameTable();

    }

    public void addCountCard(){
        countCard++;
    }

    public void subCountCard(){
        countCard--;
    }

    public int getCountCard() {
        return countCard;
    }


    //    @FXML
//    void selectedCard(MouseEvent event) {
//
//        Node node = new ImageView();
//        int gridSize = 40;
//        int row = 9;
//        int column = 9;
//        gridTable.setOnMouseClicked(mouseEvent -> {
//            double mouseX = mouseEvent.getX();
//            double mouseY = mouseEvent.getY();
//
////            int x = (int) (mouseX/gridSize % column ) * gridSize;
////            int y = (int) (mouseY/gridSize % row ) * gridSize;
//
//            int x = (int) (mouseX/gridSize % column );
//            int y = (int) (mouseY/gridSize % row );
//
//            System.out.println("mouse x "+ mouseX + " mouse y "+ mouseY);
//            System.out.println("mouse / grid size  " +mouseX/gridSize + " , " + mouseY/gridSize);
//            System.out.println("clicked " +x+ " , " +y);
////            gridTable.getCellBounds(x,y)
//        });
//    }


    public void setLabelMessage(String message){
        labelMessage.setText(message);
//        labelMessage.setFont();
    }

    public void updateGameLogic(GameLogic gameLogic){
        this.gameLogic = gameLogic;
    }

    public void controlCard(javafx.event.ActionEvent actionEvent) {
        
    }
}
