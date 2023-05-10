package it.polimi.ingsw.Network.Client.RMI;

import it.polimi.ingsw.Controller.ControllerMain;
import it.polimi.ingsw.Model.*;
import it.polimi.ingsw.Network.Client.Socket.ClientClass;
import it.polimi.ingsw.Network.Server.RMI.GameInterface;
import it.polimi.ingsw.View.CLIView;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Scanner;


public class Client extends UnicastRemoteObject implements GameClientInterface, Serializable{

    public Client() throws RemoteException{
        super();
    }


    /**
     * call the viewTable() method to print the game table on the client command line
     * @param board the game table we want to display
     * @throws RemoteException
     */
    public void receiveGameTable(GameTable board) throws RemoteException{
        board.viewTable();
    }

    /**
     * call the viewGrid() method to print the library on the client command line
     * @param library the library we want to display
     * @throws RemoteException
     */
    public void receiveLibrary(Library library) throws RemoteException{
        library.viewGrid();
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
        CLIView view = new CLIView();

        gameLogic = view.getCardFromTable(gameLogic);

        return gameLogic;
    }

    /**
     * print a string message
     * @param msg string we want to print
     * @throws RemoteException
     */
    public void receiveMessage(String msg) throws RemoteException{
        System.out.println(msg);
    }

    public void connection(GameInterface server, GameClientInterface client, ControllerMain controller) throws RemoteException, Exception{
        Scanner in = new Scanner(System.in);
        ClientClass client1;
        if(controller.getNumPlayers() == 0) {
            System.out.println("Insert players number");
            int num = in.nextInt();
            server.updateNumPlayers(num);

            System.out.println("Enter the player's name");
            String name;
            name = in.next();
            client1 = new ClientClass(client);
            client1.setPlayer(name);
            server.updatePlayers(client1);
        }else{
            if(controller.getClientList().size() < controller.getNumPlayers()){
                System.out.println("Enter the player's name");
                String name = in.nextLine();
                client1 = new ClientClass(client);
                client1.setPlayer(name);
                server.updatePlayers(client1);
            }else{
                throw new Exception("too many connected clients, you can't log in");
            }
        }
        System.out.println("[System] connected!");
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
        System.out.println(msg);
        return in.nextInt();
    }

}