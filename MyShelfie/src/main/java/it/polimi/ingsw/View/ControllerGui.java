package it.polimi.ingsw.View;

import it.polimi.ingsw.Model.Card;
import it.polimi.ingsw.Model.GameLogic;
import it.polimi.ingsw.Model.GameTable;
import it.polimi.ingsw.Model.Library;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;
import java.util.ResourceBundle;

import static it.polimi.ingsw.Model.Card.*;

public class ControllerGui implements Initializable {

    @FXML
    private ImageView CommonObj1;

    @FXML
    private ImageView CommonObj2;
    @FXML
    private HBox arrayCards;
    @FXML
    private HBox columnButtonBox;
    @FXML
    private GridPane gridLibrary;
    @FXML
    private GridPane gridTable;
    @FXML
    private Label labelMessage;
    private ToggleButton button = new ToggleButton();
    @FXML
    private Button confirm;


    private int column = -1;
    private ArrayList <Integer[]> posCard = new ArrayList<>();
    private ArrayList <Card> listCard = new ArrayList<>();
    private int countCard = 0;
    private GameLogic gameLogic;
    private boolean confirmCards = false;
    private boolean allCardsInsert = false;


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
        Image icon = new Image("assets/Publisher material/icon 50x50px.png");
        stage.getIcons().add(icon);
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
        Image icon = new Image("assets/Publisher material/icon 50x50px.png");
        stage.getIcons().add(icon);
        stage.setTitle("Library");
        stage.setScene(new Scene(root));
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(CommonObj1.getScene().getWindow());

        // controllerMain.getClientList.get(i);
        // trovare modo di ottenere lista player **
        // passo la Player.getLibrary
        // ControllerLibrary controller = loader.getController();
        // controller.updateLibrary(library);


        stage.show();
    }

    @FXML
    void openRules(MouseEvent event) throws IOException {

        Stage stage = new Stage();
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/RuleBook.fxml")));
        Image icon = new Image("assets/Publisher material/icon 50x50px.png");
        stage.getIcons().add(icon);
        stage.setTitle("RuleBook");
        stage.setScene(new Scene(root));
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(CommonObj1.getScene().getWindow());
        stage.show();

    }

    void updateLibrary(Library library){
        Image image ;
        ImageView card ;
        for (int i=0; i<6; i++){
            for (int j=0; j<5; j++){

                String url = urlCard(library.getPos(i,j));

                if (url != null){
                    image = new Image(url);
                    card = new ImageView(image);
                    gridLibrary.add(card,i,j);
//                }else{
//                    card = new ImageView(null);
//                    gridLibrary.add(card,i,j);
                }
            }
        }
    }

    void updateGameTable(GameTable gameTable){

        int k = 0;
        for(int i = 1; i < 10; i++){
            for(int j = 1; j < 10; j++) {

                if (gameTable.getCardfromBoard(i,j) != NOT) {
                    if (gameTable.getCardfromBoard(i,j)!= NONE) {
                        button = (ToggleButton) gridTable.getChildren().get(k);
                        button.setDisable(false);
                        button.selectedProperty().set(false);
                        //ImageView image = (ImageView) button.getGraphic();
                        String url = urlCard(gameTable.getCardfromBoard(i,j));
                        //image.setImage(new Image(url));
                        Image image = new Image(url);
                        button.setGraphic(new ImageView(image));

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

    @FXML
    void selectColumn(javafx.event.ActionEvent event) {

        Button b = (Button) event.getSource();
        column = (int) b.getText().charAt(0);   // -48
        System.out.println("hai scelto la colonna : " + column);
        controlColumn();

    }

    public void controlColumn(){
        if (column == -1) return;

        if (!gameLogic.getGame().getCurrentPlayer().getLibrary().checkFreeSpaces(column,countCard)){
            setLabelMessage("There is no enough space in this column. Choose another one");
            column = -1;
            return;
        }else{
            enableArrayCards();
            disableColumnButton();
            setLabelMessage("Which card do you want to insert? Click the cards in the order that you want to insert");
        }

    }

    @FXML
    void insertCard(MouseEvent event) {

        ImageView image = (ImageView) event.getSource();
        Card card = cardUrl(image.getImage().getUrl());
        gameLogic.getGame().getCurrentPlayer().getLibrary().getGrid()[gameLogic.getGame().getCurrentPlayer().getLibrary().lastRowFree(column)][column] = card;
        updateLibrary(gameLogic.getGame().getCurrentPlayer().getLibrary());
        image.setImage(null);
        image.setVisible(false);
        image.setDisable(true);

        int count=0;
        for (int i=0; i<3; i++){
            image = (ImageView) arrayCards.getChildren().get(i);
            if (image.isDisable()) count++;
        }
        if (count==3) setAllCardsInsert(true);
    }

    public boolean getAllCardsInsert(){
        return allCardsInsert;
    }
    public void setAllCardsInsert(boolean allCardsInsert){
         this.allCardsInsert = allCardsInsert;
    }

    public void enableColumnButton(){
        for (int i=0; i<5; i++){
            Button b = (Button) columnButtonBox.getChildren().get(i);
            b.setVisible(true);
            b.setDisable(false);
        }
    }

    public void disableColumnButton(){
        for (int i=0; i<5; i++){
            Button b = (Button) columnButtonBox.getChildren().get(i);
            b.setVisible(false);
            b.setDisable(true);
        }
    }


    public void showArrayCards(ArrayList<Card> list){
        String url;
        for (int i=0; i<list.size(); i++){
            ImageView image = (ImageView) arrayCards.getChildren().get(i);
            url = urlCard(list.get(i));
            image.setImage(new Image(url));
            image.setVisible(true);
//            image.setDisable(true);
        }
        for (int i=list.size(); i<3; i++){
            ImageView image = (ImageView) arrayCards.getChildren().get(i);
            image.setImage(null);
            image.setDisable(true);
            image.setVisible(false);
        }
    }

    public void enableArrayCards(){
        for (int i=0; i<3; i++) {
            ImageView image = (ImageView) arrayCards.getChildren().get(i);
            if (image.isVisible()) image.setDisable(false);
        }
    }

    public void disableGameTable(){
        confirm.setVisible(false);
        confirm.setDisable(true);
        for(int i=0; i<gridTable.getChildren().size(); i++){
            button = (ToggleButton) gridTable.getChildren().get(i);
            button.setDisable(true);
        }
    }

    public void enableGameTable(){
        confirm.setVisible(true);
        confirm.setDisable(false);
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


    /**
     * method that controls if the cards choose are drawable
     * if not, change the message and return
     * if yes, create the list of cards and replace those in the gameTable,
     * and set true the confirmation to send the list to GuiView
     *
     * @param event when confirm button is clicked
     */
    @FXML
    void controlCard(javafx.event.ActionEvent event) {

        switch (countCard){
            case 1 -> {
                if (gameLogic.checkNear(posCard.get(0)[0],posCard.get(0)[1])){

                    listCard.add(gameLogic.getGameTable().getCardfromBoard(posCard.get(0)[0],posCard.get(0)[1]));
                    setConfirmCards(true);
                    gameLogic.getGameTable().setCardfromBoard(posCard.get(0)[0], posCard.get(0)[1], NONE);

                }else{
                    String message = "Cards selected are not drawable. Choose other cards";
                    setLabelMessage(message);
                    return;
                }
            }
            case 2 ->{
                if (gameLogic.checkNear(posCard.get(0)[0],posCard.get(0)[1],posCard.get(1)[0],posCard.get(1)[1])) {

                    listCard.add(gameLogic.getGameTable().getCardfromBoard(posCard.get(0)[0],posCard.get(0)[1]));
                    listCard.add(gameLogic.getGameTable().getCardfromBoard(posCard.get(1)[0],posCard.get(1)[1]));
                    setConfirmCards(true);
                    gameLogic.getGameTable().setCardfromBoard(posCard.get(1)[0], posCard.get(1)[1], NONE);
                    gameLogic.getGameTable().setCardfromBoard(posCard.get(0)[0], posCard.get(0)[1], NONE);

                }else{
                    String message = "Cards selected are not drawable. Choose other cards";
                    setLabelMessage(message);
                    return;
                }
            }

            case 3 ->{
                if (gameLogic.checkNear(posCard.get(0)[0],posCard.get(0)[1],posCard.get(1)[0],posCard.get(1)[1],posCard.get(2)[0],posCard.get(2)[1])) {

                    listCard.add(gameLogic.getGameTable().getCardfromBoard(posCard.get(0)[0],posCard.get(0)[1]));
                    listCard.add(gameLogic.getGameTable().getCardfromBoard(posCard.get(1)[0],posCard.get(1)[1]));
                    listCard.add(gameLogic.getGameTable().getCardfromBoard(posCard.get(2)[0],posCard.get(2)[1]));
                    setConfirmCards(true);
                    gameLogic.getGameTable().setCardfromBoard(posCard.get(2)[0], posCard.get(2)[1], NONE);
                    gameLogic.getGameTable().setCardfromBoard(posCard.get(1)[0], posCard.get(1)[1], NONE);
                    gameLogic.getGameTable().setCardfromBoard(posCard.get(0)[0], posCard.get(0)[1], NONE);

                }else{
                    String message = "Cards selected are not drawable. Choose other cards";
                    setLabelMessage(message);
                    return;
                }
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

    public boolean getConfirm(){
        return confirmCards;
    }
    public void setConfirmCards(boolean confirmCards) {
        this.confirmCards = confirmCards;
    }
    public void clearPosCard(){
        while(posCard.get(0)!=null){
            posCard.remove(posCard.size()-1);
        }
    }

    public ArrayList<Card> getListCard(){
        return listCard;
    }
    public void clearListCard(){
        while(listCard.get(0)!=null){
            listCard.remove(listCard.size()-1);
        }
    }

    @FXML
    public void setLabelMessage(String message) {
        labelMessage.setText("message");

    }

    public void setGameLogic(GameLogic gameLogic){
        this.gameLogic = gameLogic;
    }

    public GameLogic getGameLogic() {
        return gameLogic;
    }

    public String urlCard (Card card){
        Random rand = new Random();
        int r = rand.nextInt(3)+1;
        String url = null;
        switch (card){
            case LIGHTBLUE -> {
                switch (r){
                    case 1 -> url = PathImageCards.TROFEI1;
                    case 2 -> url = PathImageCards.TROFEI2;
                    case 3 -> url = PathImageCards.TROFEI3;
                }
            }
            case BLUE ->{
                switch (r){
                    case 1 -> url = PathImageCards.CORNICI1;
                    case 2 -> url = PathImageCards.CORNICI2;
                    case 3 -> url = PathImageCards.CORNICI3;
                }
            }
            case YELLOW ->{
                switch (r){
                    case 1 -> url = PathImageCards.GIOCHI1;
                    case 2 -> url = PathImageCards.GIOCHI2;
                    case 3 -> url = PathImageCards.GIOCHI3;
                }
            }
            case GREEN ->{
                switch (r){
                    case 1 -> url = PathImageCards.GATTI1;
                    case 2 -> url = PathImageCards.GATTI2;
                    case 3 -> url = PathImageCards.GATTI3;
                }
            }
            case WHITE ->{
                switch (r){
                    case 1 -> url = PathImageCards.LIBRI1;
                    case 2 -> url = PathImageCards.LIBRI2;
                    case 3 -> url = PathImageCards.LIBRI3;
                }
            }
            case PURPLE ->{
                switch (r){
                    case 1 -> url = PathImageCards.PIANTE1;
                    case 2 -> url = PathImageCards.PIANTE2;
                    case 3 -> url = PathImageCards.PIANTE3;
                }
            }
        }
        return url;
    }

    public Card cardUrl (String url){

        Card card = null;
        switch (url){
            case PathImageCards.TROFEI1, PathImageCards.TROFEI3, PathImageCards.TROFEI2 -> card = LIGHTBLUE;
            case PathImageCards.CORNICI1, PathImageCards.CORNICI3, PathImageCards.CORNICI2 -> card = BLUE;
            case PathImageCards.GIOCHI1, PathImageCards.GIOCHI3, PathImageCards.GIOCHI2 -> card = YELLOW;
            case PathImageCards.GATTI1, PathImageCards.GATTI2, PathImageCards.GATTI3 -> card = GREEN;
            case PathImageCards.LIBRI1, PathImageCards.LIBRI3, PathImageCards.LIBRI2 -> card = WHITE;
            case PathImageCards.PIANTE1, PathImageCards.PIANTE2, PathImageCards.PIANTE3 -> card = PURPLE;
        }
        return card;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        labelMessage.setText("hello");
    }


}