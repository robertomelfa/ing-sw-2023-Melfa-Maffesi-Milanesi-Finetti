package it.polimi.ingsw.Network.Client.RMI;

import it.polimi.ingsw.Model.*;
import it.polimi.ingsw.Network.Server.RMI.GameInterface;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Scanner;

public class Client extends UnicastRemoteObject implements GameClientInterface {
    private Player player;

    Client(String name) throws Exception{
        this.player = new Player(name);
    }

    public Player getPlayer() throws RemoteException {
        return this.player;
    }

    public void receiveGameTable(GameTable board) throws RemoteException{
        board.viewTable();
    }

    public void Connection(GameInterface server, GameClientInterface client) throws RemoteException, Exception {
        Scanner in = new Scanner(System.in);
        String answer;

        if(server.getGame() == null){
            System.out.println("Welcome in a new game");

            System.out.println("How many players?");
            int num = in.nextInt();
            Game game = new Game(num);
            server.setGame(game);
            server.setClient(client);


            //    client.receiveGameTable(server.getGame().getGameTable());
        }else{
            //   System.out.println("Press J to join a game\nPress N to create a new game");
            //   answer = in.nextLine();

            answer = "J"; // per ora teniamo che puoi solo joinare

            switch (answer){
                case "N":
                    // TODO implementare il caso di un nuovo game: veramente necessario?
                    break;
                case "J":
                    System.out.println("You choose J");
                    if(server.getGame().getNumOfPlayers() > server.getGame().numActualPlayers()){
                        server.setClient(client);
                    }else{
                        throw new Exception("troppi dispositivi connessi" + server.getGame().numActualPlayers() +", non puoi accedere");
                    }
                    break;
                default: System.out.println("Invalid choice");
            }
        }
    }

}
