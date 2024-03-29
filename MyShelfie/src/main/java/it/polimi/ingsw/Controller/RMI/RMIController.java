package it.polimi.ingsw.Controller.RMI;

import it.polimi.ingsw.Model.GameLogic;
import it.polimi.ingsw.Network.Client.RMI.ClientRMI_Interface;
import it.polimi.ingsw.Network.Client.ClientHandler;
import it.polimi.ingsw.Network.Server.RMI.ServerRMI_Interface;

import java.io.IOException;
import java.io.Serializable;
import java.rmi.AccessException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;


public class RMIController implements Serializable {
    private ServerRMI_Interface server;   // RMIServer

    private GameLogic gameLogic;

    private ClientRMI_Interface current_client; // RMI Client

    private boolean gui;



    /**
     * constructor of the RMIController
     * call shufflePLayers to shuffle the arraylist stored in the controller to perform the turn in random order
     * @throws AccessException
     * @throws NotBoundException
     * @throws Exception
     */
    public RMIController(GameLogic gameLogic, ClientHandler current_client, ServerRMI_Interface server, boolean gui) {
        this.server = server;
        this.gameLogic = gameLogic;
        this.current_client = current_client.getClient();
        this.gui = gui;
    }

    /**
     * Used to perform the turn by a client connected with RMI
     * @return the updated gameLogic
     * @throws IOException
     * @throws Exception
     */
    public GameLogic takeTurn() throws RemoteException, Exception{

            // send library to client
            current_client.receiveLibrary(gameLogic.getGame().getCurrentPlayer().getLibrary());

            int i = 0;
            while(i == 0){
                if(!gui) {
                    switch (current_client.getStringFromClient("\nInsert 1 if you want to see your objectives, insert 2 if you want to pick the cards or insert 3 to view all the libraries")) {
                        case "1" -> {
                            // print object
                            current_client.receiveMessage("Player Object:");
                            current_client.receivePlayerObj(gameLogic.getGame().getCurrentPlayer().getPlayerObj());
                            current_client.receiveMessage("Common Object 1:");
                            current_client.receiveMessage(gameLogic.getGame().getCommonObj1().getDescription());
                            current_client.receiveMessage("Common Object 2:");
                            current_client.receiveMessage(gameLogic.getGame().getCommonObj2().getDescription());
                        }
                        case "2" -> {
                            i = 1;
                        }
                        case "3"->{
                            for(int j = 0; j < gameLogic.getPlayers().size(); j++){
                                if(!gameLogic.getPlayers().get(j).getNickname().equals(gameLogic.getGame().getCurrentPlayer().getNickname())){
                                    current_client.receiveMessage("\n" + gameLogic.getPlayers().get(j).getNickname() + "'s library");
                                    current_client.receiveLibrary(gameLogic.getPlayers().get(j).getLibrary());
                                }
                            }
                        }
                        default -> current_client.receiveMessage("The input is not valid, please insert 1, 2 or 3\n");
                    }
                }else {
                    i = 1;
                }
            }

            // send the library to the current player
            //current_client.receiveLibrary(gameLogic.getGame().getCurrentPlayer().getLibrary());

            // get cards from table
            this.gameLogic = current_client.receiveGetCard(gameLogic, server);

        return gameLogic;
    }
}
