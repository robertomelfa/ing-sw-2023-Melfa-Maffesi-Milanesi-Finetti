package it.polimi.ingsw.Network.Server;

import it.polimi.ingsw.Controller.ControllerMain;
import it.polimi.ingsw.Controller.LobbyInterface;
import it.polimi.ingsw.Network.Server.RMI.ServerRMI_Interface;
import it.polimi.ingsw.Network.Server.RMI.Server_RMI;
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

    private static ServerRMI_Interface serverRMI;

    private static LobbyInterface lobby;

    private static Registry registry;

    private static Thread thread1;

    private static Thread thread2;

    /**
     *
     * @return the controllerMain
     */
    public synchronized ControllerMain getController(){
        return this.controller;
    }

    /**
     *
     * @return Server Socket
     */
    public Server_Socket getServerSocket() {return this.serverSocket;}

    /**
     *
     * @return server RMI
     * @throws RemoteException
     */
    public ServerRMI_Interface getServerRMI() throws RemoteException{return  this.serverRMI;}

    /**
     *
     * @return the lobby
     */
    public LobbyInterface getLobby(){
        return this.lobby;
    }

    /**
     *
     * @return the thread1( thread that runs the socket server)
     */
    public Thread getThread1(){return thread1;}

    /**
     *
     * @return the thread2( thread that runs the server RMI)
     */
    public Thread getThread2(){return thread2;}


    /**
     * runs the server socket and the server RMI in 2 different threads and then runs the controller in
     * a third thread
     * @throws RemoteException
     * @throws Exception
     */
    public static void start() throws RemoteException, Exception {
        serverSocket = new Server_Socket();
        serverRMI = new Server_RMI();
        registry = LocateRegistry.createRegistry(1099);
        registry.rebind("GameInterface", serverRMI);
        lobby = new LobbyInterface(serverSocket, serverRMI);
        Runnable task1 = new startSocketServer();
        Runnable task2 = new startRMIServer();
        thread1 = new Thread(task1);
        thread2 = new Thread(task2);
        Runnable task3 = new startController();
        Thread thread3 = new Thread(task3);
        thread1.start();
        thread2.start();
        thread3.start();
    }

    /**
     * shutdown the server
     * @throws RemoteException
     * @throws IOException
     */
    public void close() throws RemoteException, IOException {
        // Unexport the remote object
        UnicastRemoteObject.unexportObject(serverRMI, true);
        // Unexport the registry
        UnicastRemoteObject.unexportObject(registry, true);

        getThread1().interrupt();

        serverSocket.close();


    }

}

class startSocketServer extends Server implements Runnable{

    /**
     * run the socket server
     */
    @Override
    public void run() {
        Server_Socket server = getServerSocket();
        try{
            server.start(getLobby().getController(), getServerRMI());
        }catch(Exception e){}

    }
}

class startRMIServer extends Server implements Runnable{

    /**
     * run the RMI server
     */
    @Override
    public void run(){
        try{
            ServerRMI_Interface server = getServerRMI();
            try{
                server.start(getLobby().getController());
            }catch (Exception e){}
        }catch (RemoteException e){}
    }
}

class startController extends Server implements Runnable{

    /**
     * run the controller
     */
    @Override
    public void run(){
        try{
            // wait all the players before starting the game
            getLobby().waitingGame();
            // stop other thread
            getThread2().interrupt();
            // game is starting
            System.out.println("Starting controller");
            close();
        }catch (Exception e){}
    }
}