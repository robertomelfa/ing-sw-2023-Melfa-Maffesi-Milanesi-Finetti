package it.polimi.ingsw.Network.Client.RMI;

import it.polimi.ingsw.Model.*;
import it.polimi.ingsw.Network.Server.RMI.*;

import java.rmi.Naming;
import java.rmi.registry.Registry;
import java.util.Scanner;
import java.rmi.registry.LocateRegistry;
import java.rmi.RemoteException;

public class GameClient {
    private static String name = new String();

    public static void main(String[] argv) throws Exception{
        try{
            Registry registry = LocateRegistry.getRegistry("localhost", 1099);
            GameInterface server = (GameInterface) registry.lookup("GameInterface");

          //  server.setClient(client);
            if(server.getGame().getNumOfPlayers() > server.getGame().numActualPlayers()){
                Scanner in = new Scanner(System.in);
                System.out.println("Enter the player's name");
                name = in.nextLine();
                Client client = new Client(name);
                server.setClient(client);
            }else{
                throw new Exception("troppi dispositivi connessi, non puoi accedere");
            }


            Game game = server.getGame();
            GameLogic turn = new GameLogic(game);
            turn.getCardFromTable();
            game.printPlayers();
            GameTable table = game.getGameTable();
            table.viewTable();

        }catch(Exception e){
            System.out.println("[System] Server failed: " + e);
        }
    }
}
