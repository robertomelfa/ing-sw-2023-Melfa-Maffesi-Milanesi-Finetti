package it.polimi.ingsw.Controller;

import it.polimi.ingsw.Network.Client.RMI.Client_RMI;
import it.polimi.ingsw.Network.Client.RMI.ClientRMI_Interface;
import it.polimi.ingsw.Network.Client.Socket.Client_Socket;
import it.polimi.ingsw.Network.Server.Server;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.rmi.RemoteException;

public class TestControllerMain {
    private Server server;

    @Before
    public void setUp() throws RemoteException, Exception {
        server = new Server();
        server.start();
        // wait server starting
        Thread.sleep(500);
    }

    @Test
    public void nameTest() throws RemoteException, Exception{
        String input = "3\nbob\n";
        ByteArrayInputStream inputStream = new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8));
        System.setIn(inputStream);

        ClientRMI_Interface client = new Client_RMI();
        client.connection(server.getServerRMI(), client, server.getLobby().getController());

        Assert.assertTrue(server.getLobby().getController().checkExistingName("bob"));
        Assert.assertFalse(server.getLobby().getController().checkExistingName(" bob"));

    }

    @Test
    public void updatePlayerTest() throws RemoteException, Exception{
        String input = "3\nbob\n";
        ByteArrayInputStream inputStream = new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8));
        System.setIn(inputStream);

        ClientRMI_Interface client = new Client_RMI();
        client.connection(server.getServerRMI(), client, server.getLobby().getController());


        String input1 = "bob\nrob\n";
        ByteArrayInputStream inputStream1 = new ByteArrayInputStream(input1.getBytes(StandardCharsets.UTF_8));
        System.setIn(inputStream1);

        Client_Socket clientS = new Client_Socket();
        clientS.setView();
        clientS.connect("127.0.0.1", 8080, server.getServerRMI());


        // wait the controller updating
        Thread.sleep(500);
        server.getLobby().getController().updateCurrentPlayer();
        Assert.assertEquals(server.getLobby().getController().getCurrentPlayer(), server.getLobby().getController().getClientList().get(1));
    }

    @After
    public void reset() throws RemoteException, IOException {
        server.close();
    }
}
