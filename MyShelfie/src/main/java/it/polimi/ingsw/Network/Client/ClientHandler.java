package it.polimi.ingsw.Network.Client;

import it.polimi.ingsw.Model.Player;
import it.polimi.ingsw.Network.Client.RMI.ClientRMI_Interface;

import java.io.Serializable;
import java.net.Socket;

public class ClientHandler implements Serializable {
    private Player player; // player match to the client

    private transient Socket socket;  // socket client

    private boolean connected = false;

    private boolean gui = false;

    private ClientRMI_Interface RMIclient;  // rmi client

    /**
     * constructor for the ClientClass
     * @param client the socket the client is connected to
     */
    public ClientHandler(Socket client){
        this.socket = client;
        this.RMIclient = null;
        connected = true;
    }

    /**
     * constructor for ClientClass
     * @param client the interface the client is connected to
     */
    public ClientHandler(ClientRMI_Interface client){
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
    public ClientRMI_Interface getClient(){
        return this.RMIclient;
    }

    /**
     * gui is true if the client is using the GUI
     * @return the boolean gui
     */
    public boolean isGui(){ return gui; }

    /**
     * set the boolean gui to true
     */
    public void setGui(){  gui = true; }

}
