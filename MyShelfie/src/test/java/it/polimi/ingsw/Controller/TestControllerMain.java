package it.polimi.ingsw.Controller;

import it.polimi.ingsw.Network.Client.RMI.Client;
import it.polimi.ingsw.Network.Client.RMI.GameClientInterface;
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
        String input = "3 bob\n";
        ByteArrayInputStream inputStream = new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8));
        System.setIn(inputStream);

        GameClientInterface client = new Client();
        client.connection(server.getServerRMI(), client, server.getController());

        Assert.assertTrue(server.getController().checkExistingName("bob"));
        Assert.assertFalse(server.getController().checkExistingName(" bob"));

    }

    @Test
    public void updatePlayerTest() throws RemoteException, Exception{
        String input = "3 bob\n";
        ByteArrayInputStream inputStream = new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8));
        System.setIn(inputStream);

        GameClientInterface client = new Client();
        client.connection(server.getServerRMI(), client, server.getController());

        System.out.println(server.getController().getClientList().get(0).getPlayer().getNickname());

        String input1 = "bob\nrob\n";
        ByteArrayInputStream inputStream1 = new ByteArrayInputStream(input1.getBytes(StandardCharsets.UTF_8));
        System.setIn(inputStream1);

        Client_Socket clientS = new Client_Socket();
        clientS.connect("127.0.0.1", 8080, server.getServerRMI());

        // wait the controller updating
        Thread.sleep(500);
        server.getController().updateCurrentPlayer();
        Assert.assertEquals(server.getController().getCurrentPlayer(), server.getController().getClientList().get(1));
    }

    @After
    public void reset() throws RemoteException, IOException {
        server.close();
    }
}
