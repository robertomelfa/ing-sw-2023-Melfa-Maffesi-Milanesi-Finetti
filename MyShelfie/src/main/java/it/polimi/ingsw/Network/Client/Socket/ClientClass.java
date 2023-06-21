package it.polimi.ingsw.Network.Client.Socket;

import it.polimi.ingsw.Model.Player;
import it.polimi.ingsw.Network.Client.RMI.GameClientInterface;

import java.io.Serializable;
import java.net.Socket;

public class ClientClass implements Serializable {
    private Player player; // player match to the client

    private transient Socket socket;  // socket client

    private boolean connected = false;

    private boolean gui = false;

    private  GameClientInterface RMIclient;  // rmi client

    /**
     * constructor for the ClientClass
     * @param client the socket the client is connected to
     */
    public ClientClass(Socket client){
        this.socket = client;
        this.RMIclient = null;
        connected = true;
    }

    /**
     * constructor for ClientClass
     * @param client the interface the client is connected to
     */
    public ClientClass(GameClientInterface client){
        this.RMIclient = client;
        this.socket = null;
        connected = true;
    }

    /**
     * initialize a new Player and set it to the local player variable
     * @param name the username of the player in this client
     * @throws Exception Exception
     */
    public void setPlayer(String name) throws Exception{
        player = new Player(name);
    }

    /**
     *
     * @param player the player we want to set in the client
     * @throws Exception Exception
     */
    public void setPlayer(Player player) throws Exception{
        this.player = player;
    }

    /**
     *
     * @return the player of the client
     */
    public Player getPlayer() {
        return player;
    }

    /**
     *
     * @return the Socket client
     */
    public Socket getSocket(){
        return socket;
    }

    /**
     *
     * @return the RMI client
     */
    public GameClientInterface getClient(){
        return this.RMIclient;
    }

    public boolean isGui(){ return gui; }

    public void setGui(){  gui = true; }

}
