package it.polimi.ingsw.Network.Client.Socket;

import it.polimi.ingsw.Model.Player;
import it.polimi.ingsw.Network.Client.RMI.Client;
import it.polimi.ingsw.Network.Client.RMI.GameClientInterface;

import java.io.Serializable;
import java.net.Socket;

public class ClientClass implements Serializable {
    private Player player; // giocatore

    private transient Socket socket;  // il client a cui è collegato il giocatore

    private GameClientInterface RMIclient;

    /**
     * constructor for the ClientClass
     * @param client the socket the client is connected to
     */
    public ClientClass(Socket client){
        this.socket = client;
        this.RMIclient = null;
    }

    public ClientClass(GameClientInterface client){
        this.RMIclient = client;
        this.socket = null;
    }

    /**
     * initialize a new Player and set it to the local player variable
     * @param name the username of the player in this client
     * @throws Exception
     */
    public void setPlayer(String name) throws Exception{
        player = new Player(name);
    }

    public void setPlayer(Player player) throws Exception{
        this.player = player;
    }

    public Player getPlayer() {
        return player;
    }

    public Socket getSocket(){
        return socket;
    }

    public GameClientInterface getClient(){
        return this.RMIclient;
    }
}
