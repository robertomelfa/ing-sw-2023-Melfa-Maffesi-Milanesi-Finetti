package it.polimi.ingsw.Network.Server;

import it.polimi.ingsw.Controller.Socket.SocketController;
import it.polimi.ingsw.Network.Server.RMI.GameInterface;
import it.polimi.ingsw.Network.Server.RMI.GameServer;
import it.polimi.ingsw.Network.Server.Socket.Server_Socket;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;




public class Server{
    public static void main(String[] args) throws RemoteException, Exception {

        Runnable task1 = new startSocketServer();
        Runnable task2 = new startRMIServer();
        Thread thread1 = new Thread(task1);
        Thread thread2 = new Thread(task2);
        thread1.start();
        thread2.start();
    }
}

class startSocketServer implements Runnable{

    @Override
    public void run() {
        System.out.println("New Thread created");
        Server_Socket server=new Server_Socket();
        try{
            server.start();
            SocketController controller = new SocketController(server);
            while(true){
                controller.takeTurn();
            }
        }catch(Exception e){}

    }
}

class startRMIServer implements Runnable{
    @Override
    public void run(){
        try{
            GameInterface server = new GameServer();

            Registry registry = LocateRegistry.createRegistry(1099);
            registry.rebind("GameInterface", server);

            try{
                server.start();
            }catch (Exception e){}
        }catch (RemoteException e){}
    }
}
