package it.polimi.ingsw.Network.Client.RMI;

import it.polimi.ingsw.Model.*;
import it.polimi.ingsw.Network.Server.RMI.*;

import java.rmi.Naming;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Scanner;
import java.rmi.registry.LocateRegistry;
import java.rmi.RemoteException;

public class GameClient {


    public static void main(String[] argv) throws Exception{
        try{
            Registry registry = LocateRegistry.getRegistry("localhost", 1099);
            GameInterface server = (GameInterface) registry.lookup("GameInterface");
            Scanner in = new Scanner(System.in);
            String name;
            String answer;

            if(server.getGame() == null){
                System.out.println("Welcome in a new game");

                System.out.println("How many players?");
                int num = in.nextInt();
                System.out.println("Enter the player's name");
                Scanner ou = new Scanner(System.in);
                name = ou.nextLine();
                GameClientInterface client = new Client(name);
                Game game = new Game(num);
                server.setGame(game, client);
                client.receiveGameTable(server.getGame().getGameTable());
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
                            System.out.println("Enter the player's name");
                            name = in.nextLine();
                            GameClientInterface client = new Client(name);
                            server.setClient(client);
                        }else{
                            throw new Exception("troppi dispositivi connessi" + server.getGame().numActualPlayers() +", non puoi accedere");
                        }
                        break;
                    default: System.out.println("Invalid choice");
                }
            }




        }catch(Exception e){
            System.out.println("[System] Server failed: " + e);
        }
    }
}
