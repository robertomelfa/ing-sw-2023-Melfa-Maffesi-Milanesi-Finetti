package it.polimi.ingsw.View;

import it.polimi.ingsw.Model.CommonObj;
import it.polimi.ingsw.Model.GameTable;
import it.polimi.ingsw.Model.Library;
import it.polimi.ingsw.Model.PlayerObj;
import it.polimi.ingsw.Network.Messages.Message;
import it.polimi.ingsw.Network.Messages.MessageType;

import java.util.Scanner;

public class CLIView implements ViewClient {

    Scanner scanner = new Scanner(System.in);
    String username;


    @Override
    public String askUserName() {

        String temp;
        displayMessages("Enter your username : ");
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
    public void displayLibrary(Library library) {
        library.viewGrid();
    }

    @Override
    public void displayGameTable(GameTable gameTable) {
        gameTable.viewTable();
    }

    @Override
    public void displayMessages(String message) {
        System.out.println(message);
    }

    @Override
    public void displayPlayerObj(PlayerObj playerObj) { playerObj.print(); }

    @Override
    public void displayCommonObj(CommonObj obj1, CommonObj obj2) {
        displayMessages("Common Object 1 : ");
        displayMessages(obj1.getDescrizione());
        displayMessages("Common Object 2 : ");
        displayMessages(obj2.getDescrizione());
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


}
