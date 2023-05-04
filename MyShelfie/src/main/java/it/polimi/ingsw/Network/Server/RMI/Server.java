package it.polimi.ingsw.Network.Server.RMI;

import it.polimi.ingsw.Controller.RMI.RMIController;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.RemoteException;
import java.rmi.server.RMIClassLoader;


public class Server {

    public static void main(String[] argv) throws RemoteException, Exception{
        try {
            int i = 0;

            GameInterface server = new GameServer();

            Registry registry = LocateRegistry.createRegistry(1099);
            registry.rebind("GameInterface", server);

            //    System.out.println("[Server] is running, and is waiting players. " + num + " players remains");

            System.out.println("[Server] is running");

            while (true) {
                if (server.getClient(i) != null) {
                    System.out.println("Si e' connesso " + server.getClient(i).getPlayer().getNickname());
                    if(i != 0){
                        server.getClient(i).receiveMessage("wait for the game to start!");
                    }
                    i++;
                    if (server.getGame().getNumOfPlayers() == server.getGame().numActualPlayers()) {
                        RMIController controller = new RMIController();
                        controller.takeTurn();
                    }
                }
                /*
                if (server.getGame().getNumOfPlayers() - server.getGame().numActualPlayers() > 0) {
                    System.out.println("Waiting players, " + (server.getGame().getNumOfPlayers() - server.getGame().numActualPlayers()) + " remaining");
                } else {
                    System.out.println("All players are online, game is ready to start!");
                }
                //  server.getGame().getGameTable().viewTable();
                i++; */
            }
        }catch (Exception e){
            System.out.println("[System] Server failed: " + e);
        }
    }

}
