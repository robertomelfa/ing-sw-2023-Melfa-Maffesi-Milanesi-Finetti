package it.polimi.ingsw.Network.Client.RMI;

import it.polimi.ingsw.Controller.ControllerMain;
import it.polimi.ingsw.Model.GameLogic;
import it.polimi.ingsw.Model.GameTable;
import it.polimi.ingsw.Model.Library;
import it.polimi.ingsw.Model.PlayerObj;
import it.polimi.ingsw.Network.Client.Socket.ClientClass;
import it.polimi.ingsw.Network.Server.RMI.GameInterface;
import it.polimi.ingsw.View.CLIView;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Scanner;


public class Client extends UnicastRemoteObject implements GameClientInterface, Serializable{

    private CLIView view=new CLIView(); // view

    public Client() throws RemoteException{
        super();
    }


    /**
     * call the viewTable() method to print the game table on the client command line
     * @param board the game table we want to display
     * @throws RemoteException
     */
    public void receiveGameTable(GameTable board) throws RemoteException{
        view.viewGameTable(board);
    }

    /**
     * call the viewGrid() method to print the library on the client command line
     * @param library the library we want to display
     * @throws RemoteException
     */
    public void receiveLibrary(Library library) throws RemoteException{
        view.viewLibrary(library);
    }

    /**
     *
     * @param obj the player object
     * @throws RemoteException
     */
    public void receivePlayerObj(PlayerObj obj) throws RemoteException{
        view.viewPlayerObj(obj);
    }

    /**
     * used to perform the action of taking cards from the game table and store them in the player's library
     * use the getCardFromTable() and insert() methods defined in the model
     * @param gameLogic to perform the getCardFromTable method
     * @param server who the client is connected to
     * @throws RemoteException
     * @throws Exception
     */
    public GameLogic receiveGetCard(GameLogic gameLogic, GameInterface server) throws RemoteException, Exception{
        gameLogic = view.getCardFromTable(gameLogic);

        return gameLogic;
    }

    /**
     * print a string message
     * @param msg string we want to print
     * @throws RemoteException
     */
    public void receiveMessage(String msg) throws RemoteException{
        view.viewString(msg);
    }

    /**
     *
     * @param server RMI server where the client will connect
     * @param client client wants to connect to the server
     * @param controller controller of the game
     * @throws RemoteException
     * @throws Exception
     */
    public void connection(GameInterface server, GameClientInterface client, ControllerMain controller) throws RemoteException, Exception{
        Scanner in = new Scanner(System.in);
        ClientClass client1;
        if(controller.getNumPlayers() == 0) {
            view.viewString("Insert players number");
            int num = in.nextInt();
            server.updateNumPlayers(num);
            view.viewString("Enter the player's name");
            String name;
            name = in.next();
            client1 = new ClientClass(client);
            client1.setPlayer(name);
            server.updatePlayers(client1);
        }else{
            if(controller.getClientList().size() < controller.getNumPlayers()){
                String name;
                do{
                    view.viewString("Enter the player's name");
                    name = in.nextLine();
                    if(controller.checkExistingName(name)){
                        System.out.println("This name is used. Try again");
                    }
                }while(controller.checkExistingName(name));
                client1 = new ClientClass(client);
                client1.setPlayer(name);
                server.updatePlayers(client1);
            }else{
                throw new Exception("too many connected clients, you can't log in");
            }
        }
        view.viewString("[System] connected!");
    }



    /**
     * print a string and return an int that correspond to the answer of a question present in the sting
     * we printed
     * @param msg string we want to print
     * @return an int taken in input
     * @throws RemoteException
     */
    public int getIntFromClient(String msg) throws RemoteException{
        Scanner in = new Scanner(System.in);
        view.viewString(msg);
        return in.nextInt();
    }

    public void connectionGUI(GameInterface server, GameClientInterface client, ControllerMain controller, int num, String username) throws RemoteException, Exception{
        ClientClass client1;
        server.updateNumPlayers(num);
        client1 = new ClientClass(client);
        client1.setPlayer(username);
        server.updatePlayers(client1);
    }

    public void connectionGUI(GameInterface server, GameClientInterface client, ControllerMain controller, String username) throws RemoteException, Exception {
        ClientClass client1;
        client1 = new ClientClass(client);
        client1.setPlayer(username);
        server.updatePlayers(client1);

    }
}