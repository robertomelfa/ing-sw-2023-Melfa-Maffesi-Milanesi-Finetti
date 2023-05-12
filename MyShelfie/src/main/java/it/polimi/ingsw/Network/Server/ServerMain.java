package it.polimi.ingsw.Network.Server;

import java.rmi.RemoteException;

public class ServerMain {

    public static void main(String args[]) throws RemoteException, Exception {
        Server server= new Server();
        server.start();
    }
}
