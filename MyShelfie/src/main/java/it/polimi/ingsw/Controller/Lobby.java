package it.polimi.ingsw.Controller;

import it.polimi.ingsw.Network.Messages.Message;
import it.polimi.ingsw.Network.Messages.MessageType;
import it.polimi.ingsw.Network.Server.RMI.GameInterface;
import it.polimi.ingsw.Network.Server.Socket.Server_Socket;

import java.util.List;

public class Lobby {
    private ControllerMain controller;

    private Server_Socket serverSocket;
    private GameInterface serverRMI;

    private boolean newLobby = true;


    public Lobby(Server_Socket serverSocket, GameInterface serverRMI){
        controller = new ControllerMain(serverSocket, serverRMI);
        this.serverRMI = serverRMI;
        this.serverSocket = serverSocket;
    }

    public ControllerMain getController(){
        return controller;
    }

    public synchronized void waitingGame() throws Exception {
        while(true){
            controller.waitStart();

            ControllerMain controller2 = new ControllerMain(serverSocket, serverRMI);
            controller2.copy(controller);
            controller.resetController();

            Runnable task = new startingGame(controller2);
            Thread thread = new Thread(task);
            thread.start();
        }
    }
}

class startingGame implements Runnable{

    private ControllerMain controller;
    public startingGame(ControllerMain controller) {
        this.controller = controller;
    }

    public void run() {
        try{
            System.out.println("run");
            controller.startGame();
        }catch(Exception e){}
    }
}
