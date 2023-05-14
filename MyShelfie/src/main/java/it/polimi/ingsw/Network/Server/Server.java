package it.polimi.ingsw.Network.Server;

import it.polimi.ingsw.Controller.ControllerMain;
import it.polimi.ingsw.Network.Server.RMI.GameInterface;
import it.polimi.ingsw.Network.Server.RMI.GameServer;
import it.polimi.ingsw.Network.Server.Socket.Server_Socket;

import java.io.IOException;
import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class Server implements Serializable {

    private static ControllerMain controller;

    private static Server_Socket serverSocket;

    private static GameInterface serverRMI;

    private static Registry registry;

    public synchronized ControllerMain getController(){
        return this.controller;
    }

    public Server_Socket getServerSocket() {return this.serverSocket;}

    public GameInterface getServerRMI() throws RemoteException{return  this.serverRMI;}


    public static void start() throws RemoteException, Exception {
        serverSocket = new Server_Socket();
        serverRMI = new GameServer();
        registry = LocateRegistry.createRegistry(1099);
        registry.rebind("GameInterface", serverRMI);
        controller = new ControllerMain(serverSocket, serverRMI);
        Runnable task1 = new startSocketServer();
        Runnable task2 = new startRMIServer();
        Thread thread1 = new Thread(task1);
        Thread thread2 = new Thread(task2);
        Runnable task3 = new startController();
        Thread thread3 = new Thread(task3);
        thread1.start();
        thread2.start();
        thread3.start();
    }

    public void close() throws RemoteException, IOException {
        // Unexport the remote object
        UnicastRemoteObject.unexportObject(serverRMI, true);
// Unexport the registry
        UnicastRemoteObject.unexportObject(registry, true);

        serverSocket.close();

    }

}

class startSocketServer extends Server implements Runnable{

    @Override
    public void run() {
        Server_Socket server = getServerSocket();
        try{
            server.start(getController());
        }catch(Exception e){}

    }
}

class startRMIServer extends Server implements Runnable{
    @Override
    public void run(){
        try{
            GameInterface server = getServerRMI();
            try{
                server.start(getController());
            }catch (Exception e){}
        }catch (RemoteException e){}
    }
}

class startController extends Server implements Runnable{
    @Override
    public void run(){
        try{
            // wait all the players before starting the game
            getController().waitStart();

            // game is starting
            System.out.println("Starting controller");
            getController().startGame();
        }catch (Exception e){}
    }
}