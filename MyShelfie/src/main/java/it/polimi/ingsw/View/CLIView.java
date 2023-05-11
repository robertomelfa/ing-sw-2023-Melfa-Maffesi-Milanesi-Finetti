package it.polimi.ingsw.View;

import it.polimi.ingsw.Model.*;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Scanner;

import static it.polimi.ingsw.Model.Card.NONE;
import static it.polimi.ingsw.Model.Card.NOT;

public class CLIView implements ViewClient, Serializable {

    Scanner scanner = new Scanner(System.in);
    String username;


    @Override
    public String askUserName() {

        String temp;
        viewString("Enter your username : ");
        do {
            username = scanner.nextLine();
//            if (checkUsername(username)) {
//                username = "";
//                System.out.println("Username already used \n Enter another username");
//            }
        }while(username == null);
        return username;
    }

    @Override
    public void viewLibrary(Library library) {
        System.out.print("   ");
        for (int i = 1; i < 6; i++) {
            System.out.printf("     %d     ", i);
        }

        System.out.print("\n");
        System.out.print("\n");

        for (int i = 0; i < 6; i++) {
            System.out.printf(" %d ", i + 1);
            for (int j = 0; j < 5; j++) {
                if (library.getPos(i,j)!= NOT && library.getPos(i,j)!= NONE) {
                    System.out.printf("%-22s", library.getPos(i,j));
                } else {
                    System.out.print("           ");
                }
            }
            System.out.print("\n");
        }
        System.out.print("\n");
    }

    @Override
    public void viewGameTable(GameTable gameTable) {
        System.out.print("   ");
        for(int i = 1; i < 10; i++){
            System.out.printf("     %d     ", i);
        }

        System.out.print("\n");
        System.out.print("\n");

        for(int i = 1; i < 10; i++){
            System.out.printf(" %d ", i);
            for(int j = 1; j < 11; j++){
                if(gameTable.getCardfromBoard(i,j) != NOT && gameTable.getCardfromBoard(i,j) != NONE){ // remove this if to view NONE and NOT on the gameTable
                    System.out.printf("%-22s", gameTable.getCardfromBoard(i,j));
                }else{
                    System.out.print("           ");     // if there is no card, read space

                }
            }
            System.out.print("\n");
        }
        System.out.print("\n");
    }

    @Override
    public void viewString(String message) {
        System.out.println(message);
    }

    @Override
    public void viewPlayerObj(PlayerObj playerObj) {
        Card[][] temp= new Card[6][5];
        for (int k=0;k<6;k++){
            for (int j=0;j<5;j++){
                temp[k][j]=Card.NONE;
            }
        }
        for (int i=0; i<playerObj.getPlayerObjs().size();i++){
            temp[playerObj.getPlayerObjs().get(i).getXPosition()][playerObj.getPlayerObjs().get(i).getYPosition()]=playerObj.getPlayerObjs().get(i).getType();
        }
        for(int i = 1; i < 6; i++){
            System.out.printf("     %d     ", i);
        }

        System.out.print("\n");
        System.out.print("\n");

        for(int i = 0; i < 6; i++){
            System.out.printf(" %d ", i+1);
            for(int j = 0; j < 5; j++){
                if(temp[i][j] != NOT && temp[i][j] != NONE){
                    System.out.printf("%-22s", temp[i][j]);
                }else{
                    System.out.print("           ");
                }
            }
            System.out.print("\n");
        }
        System.out.print("\n");
    }

    @Override
    public void viewCommonObj(CommonObj obj1, CommonObj obj2) {
        viewString("Common Object 1 : ");
        viewString(obj1.getDescrizione());
        viewString("Common Object 2 : ");
        viewString(obj2.getDescrizione());
    }


    public void clearDisplay(){
        //per farlo bisognerebbe creare una finestra e quindi una GUI
    }



/*

    SocketController socketController;
    RMIController rmiController;
    Library library;
    PlayerObj playerObj;
    GameTable gameTable;
    CommonObj commonObj1;
    CommonObj commonObj2;


    public CLIView(){
        scanner = new Scanner(System.in);
    }

*/

/*
    public void begin() throws Exception {

        //istruzioni
        System.out.println("""
                Benvenuto !!!\s
                Hai scelto CLI view.\s
                La prima cosa che devi fare adesso è scegliere che tipo di tecnologia vuoi utilizzare per comunicare con il server \s
                Socket o RMI ???
                """);

        String typeConnection = scanner.nextLine();

        while (!typeConnection.equalsIgnoreCase("socket") && !typeConnection.equalsIgnoreCase("rmi")) {
            System.out.println("Socket o RMI?");
            typeConnection = scanner.nextLine();
        }

        if (typeConnection.equalsIgnoreCase("rmi")) {
            startRmi();
        } else {
            startSocket();
        }
    }

 */

/*
    public void startRmi() throws Exception {
        GameClient.main(new String[]{"start"});
        rmiController = RMIServer.getController;
    }

*/

/*
    public void startSocket() throws Exception {

        Scanner scanner = new Scanner(System.in);
        int port;
        int ip;
        System.out.println("Inserisci il tuo ip");
        ip = scanner.nextInt();
        System.out.println("Inserisci la porta del server");
        boolean okPort = false;

        while (!okPort) {
            try {
                port = Integer.parseInt(scanner.nextLine());
                okPort = true;
            } catch (NumberFormatException e) {
                System.out.println("port socket: ");
            }

        }

        Client_Socket clientSocket = new Client_Socket();
        clientSocket.start();
        socketController = Server_Socket.getController;
        gameTable = socketController.getGameLogic().getGameTable();
        commonObj1 = socketController.getGameLogic().getGame().getCommonObj1();
        commonObj2 = socketController.getGameLogic().getGame().getCommonObj2();

    }
*/
/* public void chooseGame() throws RemoteException {
        do {
            clientController.giveGamesStatus();
            clientController.getLobbiesStatus();

            String [] possibleAnswers = {"new", "join"};
            String answer = loopStringAsk("Create a new game or join one? [new | join]: ", Arrays.asList(possibleAnswers));
            if(answer.equalsIgnoreCase("new")) {
                int map = loopIntegerAsk("Which map? [1 to " + ClientContext.get().getNumberOfMaps() + "]: ", ClientContext.get().getNumberOfMaps());
                clientController.getMapInfo(-1, map);
                MapInfoView selectedMap = ClientContext.get().getMap();
                String endMode = loopStringAsk("End mode? " + selectedMap.getAllowedEndModes() + ": ", selectedMap.getAllowedEndModes());
                clientController.createGame(map, endMode.toLowerCase());
                chooseUsername(ClientContext.get().getValidGame());
            }
            else {
                if(ClientContext.get().isValidJoin()) {
                    chooseUsername(-1);
                }
            }
        } while (ClientContext.get().getCurrentPlayer() == null);
        playing();


    }

*/

/*
    //Method to ask user a question for which e can respond only with an integer going from 0 to max
    private int loopIntegerAsk(String question, int max) {
        int answer = -1;
        boolean okAnswer = false;
        do {
            System.out.println(question);
            try {
                answer = Integer.parseInt(scanner.nextLine());
                for (int i = 1; i <= max; i++) {
                    if (answer == i) {
                        okAnswer = true;
                        break;
                    }
                }
            } catch (NumberFormatException e) {

            }
        } while (!okAnswer);

        return answer;
    }

 */
/*
    //Method to ask user a question for which he can respond only with an already established string
    private String loopStringAsk(String question, List<String> values) {
        String answer = "";
        boolean okAnswer = false;
        do {
            System.out.println(question);
            answer = scanner.nextLine();
            for (String s : values) {
                if (answer.equalsIgnoreCase(s)) {
                    okAnswer = true;
                    break;
                }
            }
        } while (!okAnswer);

        return answer.toUpperCase();
    }

*/

    public GameLogic insert(ArrayList<Card> list, GameLogic gameLogic) {
        Scanner in = new Scanner(System.in);
        int column = 0;
        viewLibrary(gameLogic.getGame().getCurrentPlayer().getLibrary());
        //gameLogic.getGame().getCurrentPlayer().getLibrary().viewGrid();
        System.out.println("Choose the column:");
        do {
            column = in.nextInt() - 1;
            if (!gameLogic.getGame().getCurrentPlayer().getLibrary().checkFreeSpaces(column, list.size())) {
                System.out.println("There is no enough space in this column");
            }
        } while (!gameLogic.getGame().getCurrentPlayer().getLibrary().checkFreeSpaces(column, list.size()));

        int flag = 0;
        String type = new String();
        int card = 0;
        int listSize = list.size();
        for (int i = 0; i < listSize; i++) {
            System.out.println("Which card do you want to insert?" + list.toString());
            do {
                flag = 0;
                if (in.hasNextInt()) {
                    card = in.nextInt() - 1;
                    if (card >= 0 && card < list.size()) {
                        flag = 1;
                    } else {
                        System.out.println("This card does not exist, try again!");
                    }
                } else {
                    type = in.next().toUpperCase();
                    if (type.equals("YELLOW") || type.equals("PURPLE") || type.equals("WHITE") || type.equals("BLUE") || type.equals("LIGHTBLUE") || type.equals("GREEN")) {
                        if (list.contains(Card.valueOf(type))) {
                            flag = 2;
                        } else {
                            System.out.println("This card does not exist, try again!");
                        }
                    } else {
                        System.out.println("This card does not exist, try again!");
                    }

                }
            } while (flag == 0);
            if (flag == 1) {
                gameLogic.getGame().getCurrentPlayer().getLibrary().getGrid()[gameLogic.getGame().getCurrentPlayer().getLibrary().lastRowFree(column)][column] = list.get(card);
                list.remove(card);
            } else if (flag == 2) {
                list.remove(Card.valueOf(type));
                gameLogic.getGame().getCurrentPlayer().getLibrary().getGrid()[gameLogic.getGame().getCurrentPlayer().getLibrary().lastRowFree(column)][column] = Card.valueOf(type);
            } else {
                System.out.println("This card does not exist, try again!");
                i--;
            }
        }
        System.out.println("Now the grid is: \n");
        gameLogic.getGame().getCurrentPlayer().setLibrary(gameLogic.getGame().getCurrentPlayer().getLibrary());
        //gameLogic.getGame().getCurrentPlayer().getLibrary().viewGrid();
        viewLibrary(gameLogic.getGame().getCurrentPlayer().getLibrary());
        return gameLogic;
    }


    public GameLogic getCardFromTable(GameLogic gameLogic) throws RemoteException {
        int size = 0;
        Scanner in = new Scanner(System.in);
        ArrayList<Card> list = new ArrayList<Card>();
        do{
            System.out.println("How many cards?");         // number of card to pick (1 - 3)
            size = in.nextInt();

            if(size < 1 || size >3){
                System.out.println("You can pick 1, 2 or 3 cards. Try again!");
            }
        }while(size < 1 || size >3);
        if(size == 1){  // case 1 card
            int x1, y1;
            // ask coordinates until a correct input
            do{
                System.out.println("Coordinate x Card 1");
                x1 = in.nextInt();
                System.out.println("Coordinate y Card 1");
                y1 = in.nextInt();
                if(!gameLogic.checkNear(x1, y1)){
                    System.out.println("Impossible to draw the card");
                }
            }while(!gameLogic.checkNear(x1, y1));
            if(gameLogic.checkNear(x1, y1)){
                list.add(gameLogic.getGameTable().getCardfromBoard(x1,y1));
                System.out.println("Card " + gameLogic.getGameTable().getCardfromBoard(x1,y1) + " drawn!");
                gameLogic.getGameTable().setCardfromBoard(x1,y1, NONE);
            }
        }else if(size == 2){        // case 2 cards
            int x1, y1, x2, y2;
            // requires coordinates
            do{
                System.out.println("Coordinate x Card 1");
                x1 = in.nextInt();
                System.out.println("Coordinate y Card 1");
                y1 = in.nextInt();
                System.out.println("Coordinate x Card 2");
                x2 = in.nextInt();
                System.out.println("Coordinate y Card 2");
                y2 = in.nextInt();

                if(!gameLogic.checkNear(x1, y1, x2, y2)){
                    System.out.println("Invalid coordinates, try again!");
                }
            }while(!gameLogic.checkNear(x1, y1, x2, y2));
            if(gameLogic.checkNear(x1, y1, x2, y2)){
                list.add(gameLogic.getGameTable().getCardfromBoard(x1,y1));
                list.add(gameLogic.getGameTable().getCardfromBoard(x2,y2));
                System.out.println("Card " + gameLogic.getGameTable().getCardfromBoard(x1,y1) + " " + gameLogic.getGameTable().getCardfromBoard(x2,y2) + " drawn");
                gameLogic.getGameTable().setCardfromBoard(x1,y1,NONE);
                gameLogic.getGameTable().setCardfromBoard(x2,y2,NONE);
            }
        }else{  // case 3 cards
            int x1, y1, x2, y2, x3, y3;
            // requires coordinates
            do{
                System.out.println("Coordinate x Card 1");
                x1 = in.nextInt();
                System.out.println("Coordinate y Card 1");
                y1 = in.nextInt();
                System.out.println("Coordinate x Card 2");
                x2 = in.nextInt();
                System.out.println("Coordinate y Card 2");
                y2 = in.nextInt();
                System.out.println("Coordinate x Card 3");
                x3 = in.nextInt();
                System.out.println("Coordinate y Card 3");
                y3 = in.nextInt();

                if(!gameLogic.checkNear(x1, y1, x2, y2, x3, y3)){
                    System.out.println("Invalid coordinates, try again!");
                }
            }while(!gameLogic.checkNear(x1, y1, x2, y2, x3, y3));

            if(gameLogic.checkNear(x1, y1, x2, y2, x3, y3)){
                // add cards on the list
                list.add(gameLogic.getGameTable().getCardfromBoard(x1,y1));
                list.add(gameLogic.getGameTable().getCardfromBoard(x2,y2));
                list.add(gameLogic.getGameTable().getCardfromBoard(x3,y3));

                System.out.println("Cards " + gameLogic.getGameTable().getCardfromBoard(x1,y1) + " " + gameLogic.getGameTable().getCardfromBoard(x2,y2) + " " + gameLogic.getGameTable().getCardfromBoard(x3,y3) + " drawn");
                // set NONE on the table
                gameLogic.getGameTable().setCardfromBoard(x1,y1,NONE);
                gameLogic.getGameTable().setCardfromBoard(x2,y2,NONE);
                gameLogic.getGameTable().setCardfromBoard(x3,y3,NONE);

            }
        }


        // implementa l'inserimento del giocatore
        gameLogic = insert(list, gameLogic);

        // return gameLogic
        return gameLogic;
    }
}
