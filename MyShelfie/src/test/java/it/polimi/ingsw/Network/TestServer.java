package it.polimi.ingsw.Network;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

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

public class TestServer {
    private Server server;

    @Before
    public void setUp() throws RemoteException, Exception {
        server = new Server();
        server.start();
        // wait server starting
        Thread.sleep(500);
    }

    @Test
    public void firstSocketConnectionTest() throws RemoteException, Exception, AssertionError{

        String input = "5 4 bob";
        ByteArrayInputStream inputStream = new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8));
        System.setIn(inputStream);

        Client_Socket client = new Client_Socket();
        client.connect("127.0.0.1", 8080, server.getServerRMI());

        // wait the controller updating
        Thread.sleep(500);

        Assert.assertEquals(4, server.getController().getNumPlayers());
        Assert.assertEquals(server.getController().getClientList().get(0).getPlayer().getNickname(), "bob");

    }

    @Test
    public void firstRMIConnectionTest() throws RemoteException, Exception{

        String input = "5 4 bob";
        ByteArrayInputStream inputStream = new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8));
        System.setIn(inputStream);

        GameClientInterface client = new Client();
        client.connection(server.getServerRMI(), client, server.getController());

        Assert.assertEquals(4, server.getController().getNumPlayers());
        Assert.assertEquals(server.getController().getClientList().get(0).getPlayer().getNickname(), "bob");
    }

    @Test
    public void doubleRMISocketConnectionTest() throws RemoteException, Exception, IOException{
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

        Assert.assertEquals(3, server.getController().getNumPlayers());
        Assert.assertEquals(server.getController().getClientList().get(0).getPlayer().getNickname(), "bob");


        // bob is used, so check marley
        Assert.assertEquals(server.getController().getClientList().get(1).getPlayer().getNickname(), "rob");
    }

    @Test
    public void doubleSocketRMIConnectionTest() throws RemoteException, Exception, IOException{
        String input = "3 mario\n";
        ByteArrayInputStream inputStream = new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8));
        System.setIn(inputStream);


        Client_Socket clientS = new Client_Socket();
        clientS.connect("127.0.0.1", 8080, server.getServerRMI());

        Thread.sleep(500);

        String input1 = "mario\nrossi\n";
        ByteArrayInputStream inputStream1 = new ByteArrayInputStream(input1.getBytes(StandardCharsets.UTF_8));
        System.setIn(inputStream1);


        GameClientInterface client = new Client();
        client.connection(server.getServerRMI(), client, server.getController());

        // wait the controller updating
        Thread.sleep(500);

        Assert.assertEquals(3, server.getController().getNumPlayers());
        Assert.assertEquals(server.getController().getClientList().get(0).getPlayer().getNickname(), "mario");


        // bob is used, so check marley
        Assert.assertEquals(server.getController().getClientList().get(1).getPlayer().getNickname(), "rossi");
    }

    @Test
    public void startGameTest() throws RemoteException, Exception, IOException{
        String input = "2 bob\n";
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

        // game will start
        Assert.assertTrue(server.getController().getStart());
    }

    @After
    public void reset() throws RemoteException, IOException {
        server.close();
    }

}

