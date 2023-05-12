package it.polimi.ingsw.Network;
import static org.junit.Assert.assertEquals;

import it.polimi.ingsw.Controller.ControllerMain;
import it.polimi.ingsw.Network.Client.RMI.Client;
import it.polimi.ingsw.Network.Client.RMI.GameClientInterface;
import it.polimi.ingsw.Network.Client.Socket.Client_Socket;
import it.polimi.ingsw.Network.Server.RMI.GameInterface;
import it.polimi.ingsw.Network.Server.RMI.GameServer;
import it.polimi.ingsw.Network.Server.Server;
import it.polimi.ingsw.Network.Server.Socket.Server_Socket;

import org.junit.Assert;
import org.junit.Before;
        import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.rmi.RemoteException;

public class TestServer {
    private Server server;

    @Before
    public void setUp() throws RemoteException, Exception {
        server = new Server();
    }

    @Test
    public void connectionTest() throws RemoteException, Exception, AssertionError{

        server.start();

        int inputNumber = 4; // valore dell'input che vuoi simulare
        ByteArrayInputStream inputStream = new ByteArrayInputStream(Integer.toString(inputNumber).getBytes());
        System.setIn(inputStream);

        Client_Socket client = new Client_Socket();
        client.start(server.getServerRMI());

    }

}

