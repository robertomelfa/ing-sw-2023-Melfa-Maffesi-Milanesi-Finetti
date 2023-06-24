package it.polimi.ingsw.Controller;

import it.polimi.ingsw.Network.Server.RMI.ServerRMI_Interface;
import it.polimi.ingsw.Network.Server.Socket.Server_Socket;

public class LobbyInterface {
    private ControllerMain controller;

    private Server_Socket serverSocket;
    private ServerRMI_Interface serverRMI;

    /**
     * Constructor of the class: initialize the parameter
     * @param serverSocket server socket
     * @param serverRMI server rmi
     */
    public LobbyInterface(Server_Socket serverSocket, ServerRMI_Interface serverRMI){
        controller = new ControllerMain(serverSocket, serverRMI);
        this.serverRMI = serverRMI;
        this.serverSocket = serverSocket;
    }

    /**
     *
     * @return the controller of the class
     */
    public ControllerMain getController(){
        return controller;
    }

    /**
     * This method waits for enough players to connect before starting the game.
     * The game is launched by a thread.
     * Infinite new game can be created as long as the server is connected
     * @throws Exception
     */
    public synchronized void waitingGame() throws Exception {
        while(true){
            // waiting for the starting of the game
            controller.waitStart();

            ControllerMain controller2 = new ControllerMain(serverSocket, serverRMI);
            controller2.copy(controller);
            controller.resetController();

            // starting the game
            Runnable task = new startingGame(controller2);
            Thread thread = new Thread(task);
            thread.start();
        }
    }
}

class startingGame implements Runnable{

    private ControllerMain controller;
    /**
     * constructor for the startingGame class
     * @param controller the controllerMain
     */
    public startingGame(ControllerMain controller) {
        this.controller = controller;
    }

    /**
     * start the game
     */
    public void run() {
        try{
            controller.startGame();
        }catch(Exception e){}
    }
}
