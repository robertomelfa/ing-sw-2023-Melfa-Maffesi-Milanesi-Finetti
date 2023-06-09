package it.polimi.ingsw.View;

import it.polimi.ingsw.Model.*;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
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
import javafx.scene.text.TextAlignment;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.Serializable;
import java.net.URL;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;
import java.util.ResourceBundle;

import static it.polimi.ingsw.Model.Card.*;

public class ControllerGui implements Initializable, Serializable {

    @FXML
    private ImageView PlayerObj;
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
    @FXML
    private ToggleButton button = new ToggleButton();
    @FXML
    private Button confirm;
    @FXML
    private Label namePlayer1;
    @FXML
    private Label namePlayer2;
    @FXML
    private Label namePlayer3;
    @FXML
    private Label namePlayer4;
    @FXML
    private Label points1;
    @FXML
    private Label points2;
    @FXML
    private Label points3;
    @FXML
    private Label points4;
    @FXML
    private HBox CardPlayer3;
    @FXML
    private HBox CardPlayer4;
    @FXML
    private ImageView scoreObj1;
    @FXML
    private ImageView scoreObj2;

    @FXML
    private ImageView[][] libraryImageView = new ImageView[5][6];


    private int column = -1;
    private ArrayList <Integer[]> posCard = new ArrayList<>();
    private ArrayList <Card> listCard = new ArrayList<>();
    private int countCard = 0;
    private GameLogic gameLogic;
    private boolean confirmCards = false;
    private boolean allCardsInsert = false;
    private ArrayList<Library> libraries = new ArrayList<>();
    private int indexCurrPlayer = -1;

    private boolean first = true;


    @FXML
    void openDescription1(MouseEvent event) throws IOException {
        openDescription(1);
    }

    @FXML
    void openDescription2(MouseEvent event) throws IOException {
        openDescription(2);
    }

    /**
     * method that open a new window and show the description of the Common Object selected
     * @param num CommonObj selected
     * @throws IOException exception for the loader
     */
    @FXML
    void openDescription(int num) throws IOException {

        Stage stage = new Stage();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/comObjDesc.fxml"));
        Parent root = loader.load();
        stage.setTitle("Description");
        stage.setResizable(false);
        stage.setScene(new Scene(root));
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(CommonObj1.getScene().getWindow());


        int numObj=0;
        final String prefix = "cards/";

        String url;
        if (num == 1) url = CommonObj1.getImage().getUrl();
        else if (num == 2) url = CommonObj2.getImage().getUrl();
        else url = PathImageCards.COMMONOBJBACK;
        url = url.substring(url.lastIndexOf("cards"));
        switch (url){
            case prefix + "4.jpg" ->numObj=1;
            case prefix + "11.jpg" ->numObj=2;
            case prefix + "7.jpg" ->numObj=3;
            case prefix + "3.jpg" ->numObj=4;
            case prefix + "8.jpg" ->numObj=5;
            case prefix + "2.jpg" ->numObj=6;
            case prefix + "1.jpg" ->numObj=7;
            case prefix + "6.jpg" ->numObj=8;
            case prefix + "5.jpg" ->numObj=9;
            case prefix + "10.jpg" ->numObj=10;
            case prefix + "9.jpg" ->numObj=11;
            case prefix + "12.jpg" ->numObj=12;
        }
        // se da problemi con le exception mettere un default
        // 4 - 11 - 7 - 3 - 8 - 2 - 1 - 6 - 5 - 10 - 9 - 12

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


    /**
     * method that open a new window that show the library of the player selected
     * @param numPlayer player selected
     * @throws IOException exception for the loader
     */
        @FXML
    void openLibrary(int numPlayer) throws IOException {

        Stage stage = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Library.fxml"));
        stage.setResizable(false);
        Parent root = loader.load();
        Image icon = new Image("assets/Publisher material/icon 50x50px.png");
        stage.getIcons().add(icon);
        stage.setTitle("Library");
        stage.setScene(new Scene(root));
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(CommonObj1.getScene().getWindow());
        ControllerLibrary controller = loader.getController();
        try {
            controller.updateLibrary(libraries.get(numPlayer-1));
        }catch (NullPointerException e ){
            controller.updateLibrary(null);
        }
        stage.show();
    }

    /**
     * method that open a window with the rule of the game
     * @param event the click on the button "rules"
     * @throws IOException exception for the loader
     */
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


    /**
     * update the main library
     * @param library the new library to show
     */
    void updateLibrary(Library library){

        if(first){
            for (int j = 0; j < 5; j++) {
                for (int i = 0; i < 6; i++) {
                    ImageView card = new ImageView();
                    card.setFitHeight(25);
                    card.setFitWidth(26);
                    libraryImageView[j][i] = card;
                    gridLibrary.add(card, j, i);
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

    /**
     * update gameTable
     * @param gameTable the new gameTable
     */
    void updateGameTable(GameTable gameTable){

        int k = 0;
        while(k<45) {
            button = (ToggleButton) gridTable.getChildren().get(k);
            String pos = button.getText();
            int x = (int) pos.charAt(0) - 48 +1;
            int y = (int) pos.charAt(1) - 48 +1;

            if((gameTable.getCardfromBoard(x,y)!= NONE) && (gameTable.getCardfromBoard(x,y) != NOT)){
                button.setDisable(false);
                button.selectedProperty().set(false);

                ImageView imageView = (ImageView) button.getGraphic();
                String url = urlCard(gameTable.getCardfromBoard(x,y));
                if(imageView.getImage()==null){
                    Image image = new Image(url);
                    imageView.setImage(image);
                    imageView.setFitWidth(35);
                    imageView.setFitHeight(40);
                }

                String temp = imageView.getImage().getUrl().substring(imageView.getImage().getUrl().lastIndexOf("assets"));
                temp = temp. replace("%20"," ");
                if (!Objects.equals(temp,url)){
                    imageView.setImage(null);
                    Image image = new Image(url);
                    imageView.setImage(image);
                    image.cancel();
                }
            }else{
                button.setDisable(true);
                button.selectedProperty().set(false);
                ImageView image = (ImageView) button.getGraphic();
                if(image.getImage()!=null){
                    image.setImage(null);
                }
            }

            k++;
        }
    }

    /**
     * response to the column choose
     * @param event click of the button
     */
    @FXML
    void selectColumn(javafx.event.ActionEvent event) {

        Button b = (Button) event.getSource();
        column = (int) b.getText().charAt(0) -48;
        System.out.println("hai scelto la colonna : " + column);
        controlColumn();

    }

    /**
     * controls that the column chosen have enough space for the cars selected
     */
    public void controlColumn(){
        if (column == -1) return;

        if (!gameLogic.getGame().getCurrentPlayer().getLibrary().checkFreeSpaces(column-1,countCard)){
            setLabelMessage("There is no enough space in this column. Choose another one");
            column = -1;
        }else{
            enableArrayCards();
            disableColumnButton();
            setLabelMessage("Which card do you want to insert? Click the cards in the order that you want to insert");
        }

    }

    /**
     * insert the card selected in the library
     * @param event card selected
     */
    @FXML
    void insertCard(MouseEvent event) {

        ImageView image = (ImageView) event.getSource();
        Card card = cardUrl(image.getImage().getUrl());
        gameLogic.getGame().getCurrentPlayer().getLibrary().getGrid()[gameLogic.getGame().getCurrentPlayer().getLibrary().lastRowFree(column-1)][column-1] = card;
        Platform.runLater(()->updateLibrary(gameLogic.getGame().getCurrentPlayer().getLibrary()));

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

    /**
     * enable the buttons of column choose
     */
    public void enableColumnButton(){
        for (int i=0; i<5; i++){
            Button b = (Button) columnButtonBox.getChildren().get(i);
            b.setVisible(true);
            b.setDisable(false);
        }
    }

    /**
     * disable the buttons of column choose
     */
    public void disableColumnButton(){
        for (int i=0; i<5; i++){
            Button b = (Button) columnButtonBox.getChildren().get(i);
            b.setVisible(false);
            b.setDisable(true);
        }
    }

    /**
     * show the cards selected in the gameTable over the main library
     * @param list cards selected
     */
    public void showArrayCards(ArrayList<Card> list){
        String url;
        System.out.println("in showCards...");
        for (int i=0; i<list.size(); i++){
            System.out.println("Card :  " +list.get(i));
            ImageView image = (ImageView) arrayCards.getChildren().get(i);
            url = urlCard(list.get(i));
            image.setImage(new Image(url));
            image.setVisible(true);
//            image.setDisable(false);
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

    /**
     * disable the gameTable
     */
    public void disableGameTable(){
        confirm.setVisible(false);
        confirm.setDisable(true);
        for(int i=0; i<gridTable.getChildren().size(); i++){
            button = (ToggleButton) gridTable.getChildren().get(i);
            button.setDisable(true);
        }
    }

    /**
     * enable the gameTable where there are cards
     */
    public void enableGameTable(){
        confirm.setVisible(true);
        confirm.setDisable(true);
        for(int i=0; i<gridTable.getChildren().size(); i++) {
            button = (ToggleButton) gridTable.getChildren().get(i);
            ImageView imageView = (ImageView) button.getGraphic();
            if (imageView.getImage() !=null) {
                button.setDisable(false);
            }else{
                button.setDisable(true);
            }
        }
    }

    /**
     * unselect all the button on gameTable
     */
    public void unselectedGameTable(){
        for(int i=0; i<gridTable.getChildren().size(); i++){
            button = (ToggleButton) gridTable.getChildren().get(i);
            button.setSelected(false);
        }

    }

    /**
     * register the card selected / unselected an the number of cards selected
     * @param event card selected / unselected
     */
    @FXML
    void selectedCard(javafx.event.ActionEvent event) {

        this.button = (ToggleButton) event.getSource();

        //controllare se invertiti x & y
        String pos = button.getText();
        int x = (int) pos.charAt(0)-48+1;
        int y = (int) pos.charAt(1)-48+1;

        if (button.isSelected()) {
            System.out.println("clicked " +(x-1)+ " , " +(y-1));
            addCountCard();
            posCard.add(new Integer[]{x,y});
        }
        else {
            System.out.println("unclicked " +(x-1)+ ", " +(y-1));
            subCountCard();

            for (Integer[] pair : posCard){
                if ((pair[0] == x) && (pair[1] == y)) {
                    posCard.remove(pair);
                    break;
                }
            }
//            posCard.remove(new Integer[]{x,y});
        }


        confirm.setDisable((countCard > 3) || (countCard<1) ||
                           (!gameLogic.getGame().getCurrentPlayer().getLibrary().checkNumCardsRemain(countCard)));

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

                    System.out.println("add Card at list");

                    gameLogic.getGameTable().setCardfromBoard(posCard.get(0)[0], posCard.get(0)[1], NONE);

                    System.out.println("set NONE in gameTable");

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
                    gameLogic.getGameTable().setCardfromBoard(posCard.get(0)[0], posCard.get(0)[1], NONE);
                    gameLogic.getGameTable().setCardfromBoard(posCard.get(1)[0], posCard.get(1)[1], NONE);

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

        Platform.runLater(() -> {
            updateGameTable(gameLogic.getGameTable());
            unselectedGameTable();
            disableGameTable();
        });
        setConfirmCards(true);
    }

    public void addCountCard(){
        countCard++;
    }

    public void subCountCard(){
        countCard--;
    }

    public boolean getConfirmCards(){
        return confirmCards;
    }
    public void setConfirmCards(boolean confirmCards) {
        this.confirmCards = confirmCards;
    }

    /**
     * clear the variables that register the cards selected
     */
    public void clearPosCard(){
        try {
            while(posCard != null){
                System.out.println("clear pos : " + posCard.get(posCard.size()-1)[0] + " - " +posCard.get(posCard.size()-1)[1] );
                posCard.remove(posCard.size()-1);
                subCountCard();
            }
        }catch (IndexOutOfBoundsException ignored){

        }
    }

    public ArrayList<Card> getListCard(){
        return listCard;
    }

    /**
     * clear the list of cards chosen
     */
    public void clearListCard(){
        try {
            while (listCard != null) {
                listCard.remove(listCard.size() - 1);
            }
        }catch (IndexOutOfBoundsException ignored ){

        }
    }

    public void setNamePlayers(ArrayList<Player> players){

        int numPlayers = players.size();
        CardPlayer3.setVisible(false);
        CardPlayer4.setVisible(false);
        namePlayer1.setText(players.get(0).getNickname());
        libraries.add(players.get(0).getLibrary());
        namePlayer2.setText(players.get(1).getNickname());
        libraries.add(players.get(1).getLibrary());
        if(numPlayers > 2){
            namePlayer3.setText(players.get(2).getNickname());
            libraries.add(players.get(2).getLibrary());
            CardPlayer3.setVisible(true);
            if(numPlayers == 4){
                namePlayer4.setText(players.get(3).getNickname());
                libraries.add(players.get(3).getLibrary());
                CardPlayer3.setVisible(true);
            }
        }

    }

    public void updatePoints(ArrayList<Player> players){

        int numPlayers = players.size();
        for(int i = 0; i < players.size(); i++){
            if(gameLogic!=null){
                gameLogic.setPlayers(players.get(i));
            }
        }
        points1.setText("Points : " + players.get(0).getPoints());
        libraries.set(0, players.get(0).getLibrary());
        points2.setText("Points : " + players.get(1).getPoints());
        libraries.set(1, players.get(1).getLibrary());
        if(numPlayers > 2){
            points3.setText("Points : " + players.get(2).getPoints());
            libraries.set(2, players.get(2).getLibrary());
            if(numPlayers == 4){
                points4.setText("Points : " + players.get(3).getPoints());
                libraries.set(3, players.get(3).getLibrary());
            }
        }
    }

    public void updateCommonObjPoints(){
        int point = gameLogic.getGame().getCommonObj1().getPointCount(true);
        String url = "";
        switch (point){
            case 0 -> url = PathImageCards.SCORE_EMPTY;
            case 2 -> url = PathImageCards.SCORE2;
            case 4 -> url = PathImageCards.SCORE4;
            case 6 -> url = PathImageCards.SCORE6;
            case 8 -> url = PathImageCards.SCORE8;
        }
        scoreObj1.setImage(new Image(url));
        scoreObj1.setFitWidth(32);
        scoreObj1.setFitHeight(32);

        point = gameLogic.getGame().getCommonObj2().getPointCount(true);

        switch (point){
            case 0 -> url = PathImageCards.SCORE_EMPTY;
            case 2 -> url = PathImageCards.SCORE2;
            case 4 -> url = PathImageCards.SCORE4;
            case 6 -> url = PathImageCards.SCORE6;
            case 8 -> url = PathImageCards.SCORE8;
        }
        scoreObj2.setImage(new Image(url));
        scoreObj2.setFitWidth(32);
        scoreObj2.setFitHeight(32);

    }

    @FXML
    public void setLabelMessage(String message) {
        labelMessage.setAlignment(Pos.CENTER);
        labelMessage.setText(message);
    }

    public void setGameLogic(GameLogic gameLogic){
        this.gameLogic = gameLogic;
    }

    public GameLogic getGameLogic() {
        return gameLogic;
    }

    public void updateCurrPlayer(){
        indexCurrPlayer++;
        if (indexCurrPlayer >= gameLogic.getGame().getNumOfPlayers()) indexCurrPlayer = 0;
    }
    public void setLibraries(Library library){
        if (libraries == null) libraries = new ArrayList<>(gameLogic.getGame().getNumOfPlayers());
        libraries.add(library);
    }

    /**
     * convert the card in a string path where can find the right image
     * @param card type of card
     * @return url of image
     */
    public String urlCard (Card card){
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

    /**
     * convert the url in the right type of card
     * @param url string path where find image
     * @return type of card
     */
    public Card cardUrl (String url){
        url = url.substring(url.lastIndexOf("assets"));
        url = url.replace("%20", " ");
        Card card = null;
        switch (url){
            case PathImageCards.TROFEI1 -> card = LIGHTBLUE1;
            case PathImageCards.TROFEI3 -> card = LIGHTBLUE3;
            case PathImageCards.TROFEI2 -> card = LIGHTBLUE2;
            case PathImageCards.CORNICI1 -> card = BLUE1;
            case PathImageCards.CORNICI2 -> card = BLUE2;
            case PathImageCards.CORNICI3 -> card = BLUE3;
            case PathImageCards.GIOCHI1 -> card = YELLOW1;
            case PathImageCards.GIOCHI2 -> card = YELLOW2;
            case PathImageCards.GIOCHI3 -> card = YELLOW3;
            case PathImageCards.GATTI1 -> card = GREEN1;
            case PathImageCards.GATTI2 -> card = GREEN2;
            case PathImageCards.GATTI3 -> card = GREEN3;
            case PathImageCards.LIBRI1 -> card = WHITE1;
            case PathImageCards.LIBRI2 -> card = WHITE2;
            case PathImageCards.LIBRI3 -> card = WHITE3;
            case PathImageCards.PIANTE1 -> card = PURPLE1;
            case PathImageCards.PIANTE2 -> card = PURPLE2;
            case PathImageCards.PIANTE3 -> card = PURPLE3;
        }
        return card;
    }

    public void setPlayerObj(GameLogic gameLogic){

        String url = PathImageCards.PLAYEROBJEMPTY;

        switch (gameLogic.getGame().getCurrentPlayer().getPlayerObj().getNum()){
            case 1 -> url = PathImageCards.PLAYEROBJ2;
            case 2 -> url = PathImageCards.PLAYEROBJ1;
            case 3 -> url = PathImageCards.PLAYEROBJ3;
            case 4 -> url = PathImageCards.PLAYEROBJ4;
            case 5 -> url = PathImageCards.PLAYEROBJ5;
            case 6 -> url = PathImageCards.PLAYEROBJ6;
            case 7 -> url = PathImageCards.PLAYEROBJ7;
            case 8 -> url = PathImageCards.PLAYEROBJ8;
            case 9 -> url = PathImageCards.PLAYEROBJ9;
            case 10 -> url = PathImageCards.PLAYEROBJ10;
            case 11 -> url = PathImageCards.PLAYEROBJ11;
            case 12 -> url = PathImageCards.PLAYEROBJ12;
        }
        // 2 - 1 - 3 - 4 - 5 - 6 - 7 - 8 - 9 - 10 - 11 - 12

        PlayerObj.setImage(new Image(url));
        PlayerObj.setFitWidth(200);
        PlayerObj.setFitHeight(200);
    }

    /**
     * initialize the common object in the window
     * @param gameLogic game logic of the game
     */
    public void setCommonObj(GameLogic gameLogic){

        String url = PathImageCards.COMMONOBJBACK;
        switch (gameLogic.getGame().getCommonObj1().getObjNum()){

            case 1 -> url = PathImageCards.COMMONOBJ4;
            case 2 -> url = PathImageCards.COMMONOBJ11;
            case 3 -> url = PathImageCards.COMMONOBJ8;
            case 4 -> url = PathImageCards.COMMONOBJ7;
            case 5 -> url = PathImageCards.COMMONOBJ3;
            case 6 -> url = PathImageCards.COMMONOBJ2;
            case 7 -> url = PathImageCards.COMMONOBJ1;
            case 8 -> url = PathImageCards.COMMONOBJ6;
            case 9 -> url = PathImageCards.COMMONOBJ5;
            case 10 -> url = PathImageCards.COMMONOBJ10;
            case 11 -> url = PathImageCards.COMMONOBJ9;
            case 12 -> url = PathImageCards.COMMONOBJ12;
        }

        CommonObj1.setImage(new Image(url));
        CommonObj1.setFitWidth(200);
        CommonObj1.setFitHeight(150);

        switch (gameLogic.getGame().getCommonObj2().getObjNum()){

            case 1 -> url = PathImageCards.COMMONOBJ4;
            case 2 -> url = PathImageCards.COMMONOBJ11;
            case 3 -> url = PathImageCards.COMMONOBJ8;
            case 4 -> url = PathImageCards.COMMONOBJ7;
            case 5 -> url = PathImageCards.COMMONOBJ3;
            case 6 -> url = PathImageCards.COMMONOBJ2;
            case 7 -> url = PathImageCards.COMMONOBJ1;
            case 8 -> url = PathImageCards.COMMONOBJ6;
            case 9 -> url = PathImageCards.COMMONOBJ5;
            case 10 -> url = PathImageCards.COMMONOBJ10;
            case 11 -> url = PathImageCards.COMMONOBJ9;
            case 12 -> url = PathImageCards.COMMONOBJ12;
        }

        CommonObj2.setImage(new Image(url));
        CommonObj2.setFitWidth(200);
        CommonObj2.setFitHeight(150);
    }

    /**
     * initialize the message label before the start of the game
     * @param url url
     * @param resourceBundle resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        labelMessage.setText(" Welcome !!! \n waiting other players... ");
    }

    @FXML
    public void openLeaderboard(){
        Platform.runLater(() -> {
            try {
                System.out.println("Opening Leaderboard");
                Stage stage = new Stage();
                stage.setResizable(false);
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/leaderboard.fxml"));
                Parent root = loader.load();
                Image icon = new Image("assets/Publisher material/icon 50x50px.png");
                stage.getIcons().add(icon);
                stage.setTitle("Leaderboard");
                stage.setScene(new Scene(root));
                stage.initModality(Modality.WINDOW_MODAL);
                stage.initOwner(CommonObj1.getScene().getWindow());
                ControllerLeaderboard controller = loader.getController();
                controller.updateLeaderboard(gameLogic.getPlayers());
//                loader.setController(controller);
                stage.show();
            }catch (IOException e){
                System.out.println("Exception in openLeaderboard");
            }

        });

    }
}
