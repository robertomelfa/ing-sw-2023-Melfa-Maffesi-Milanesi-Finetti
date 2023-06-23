package it.polimi.ingsw.Network;

import static it.polimi.ingsw.Model.Card.BLUE1;
import static it.polimi.ingsw.Model.Card.YELLOW1;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import it.polimi.ingsw.Model.Game;
import it.polimi.ingsw.Model.GameLogic;
import it.polimi.ingsw.Model.GameTable;
import it.polimi.ingsw.Model.PlayerObj;
import it.polimi.ingsw.Network.Client.Socket.Client_Socket;
import it.polimi.ingsw.Network.Messages.Message;
import it.polimi.ingsw.Network.Messages.MessageType;
import it.polimi.ingsw.Network.Server.Server;

import it.polimi.ingsw.View.CLI.CLIView;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.rmi.RemoteException;

public class TestSocketMessages {

    private Server server;

    private CLIView view = new CLIView();

    @Before
    public void setUp() throws RemoteException, Exception {
        server = new Server();
        server.start();
        // wait server starting
        Thread.sleep(500);
    }

    @Test
    public void TestSendPlayerObj() throws Exception{
        String input = "5\n4\nbob";
        ByteArrayInputStream inputStream = new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8));
        System.setIn(inputStream);

        Client_Socket client = new Client_Socket();
        client.setView();
        client.connect("127.0.0.1", 8080, server.getServerRMI());

        // wait the controller updating
        Thread.sleep(500);

        PlayerObj obj = new PlayerObj(3);



        server.getServerSocket().sendPlayerObj(server.getLobby().getController().getClientList().get(0).getSocket(), obj);
        Message msg=client.receiveMessage();

        if (msg.getType()== MessageType.receivePlayerObj) {
            PlayerObj obj2 = client.receivePlayerObj();
            Assert.assertEquals(obj.getNum(), obj2.getNum());
        }

    }

    @Test
    public void TestSendGameTable() throws Exception{
        String input = "5\n4\nbob";
        ByteArrayInputStream inputStream = new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8));
        System.setIn(inputStream);

        Client_Socket client = new Client_Socket();
        client.setView();
        client.connect("127.0.0.1", 8080, server.getServerRMI());

        // wait the controller updating
        Thread.sleep(500);

        GameTable gameTable = new GameTable(2);
        gameTable.setCardfromBoard(5,5,BLUE1);
        gameTable.setCardfromBoard(7,4,YELLOW1);

        server.getServerSocket().sendGameTable(server.getLobby().getController().getClientList().get(0).getSocket(), gameTable);
        Message msg=client.receiveMessage();

        if (msg.getType()== MessageType.receiveGameTable) {
            GameTable gameTable1 = client.receiveGameTable();
            Assert.assertTrue(gameTable.equals(gameTable1));
        }

    }

    @Test
    public void TestClientLogic() throws Exception{
        String input = "5\n4\nbob";
        ByteArrayInputStream inputStream = new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8));
        System.setIn(inputStream);

        Client_Socket client = new Client_Socket();
        client.setView();
        client.connect("127.0.0.1", 8080, server.getServerRMI());
        Game game = new Game(2);
        GameTable gameTable = new GameTable(2);
        PlayerObj obj = new PlayerObj(4);
        GameLogic gameLogic = new GameLogic(game);
        Thread.sleep(500);
        Message msg = new Message(MessageType.printMessage, "Game is starting");
        server.getServerSocket().sendMessage(msg, server.getLobby().getController().getClientList().get(0).getSocket());
        msg = new Message(MessageType.objectiveCompleted, "completed");
        server.getServerSocket().sendMessage(msg, server.getLobby().getController().getClientList().get(0).getSocket());
        server.getServerSocket().sendGameTable(server.getLobby().getController().getClientList().get(0).getSocket(), gameTable);
        server.getServerSocket().sendPlayerObj(server.getLobby().getController().getClientList().get(0).getSocket(), obj);
        server.getServerSocket().sendLibrary(server.getLobby().getController().getClientList().get(0).getSocket(), server.getLobby().getController().getClientList().get(0).getPlayer().getLibrary());
        msg = new Message(MessageType.endGame, null);
        server.getServerSocket().sendMessage(msg, server.getLobby().getController().getClientList().get(0).getSocket());
        client.clientlogic(server.getServerRMI());

    }

    @After
    public void reset() throws RemoteException, IOException {
        server.close();
    }
}
