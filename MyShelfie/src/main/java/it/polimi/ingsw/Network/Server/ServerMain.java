package it.polimi.ingsw.Network.Server;

import java.rmi.RemoteException;

public class ServerMain {

    /**
     * main method that starts the server
     * @param args
     * @throws RemoteException
     * @throws Exception
     */
    public static void main(String args[]) throws RemoteException, Exception {
        Server server= new Server();
        server.start();
    }
}
