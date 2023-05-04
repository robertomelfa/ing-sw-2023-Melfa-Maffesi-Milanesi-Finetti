package it.polimi.ingsw.Network.Client.Socket;

import it.polimi.ingsw.Model.Player;

import java.net.Socket;

public class ClientClass {
    private Player player; // giocatore

    private Socket socket;  // il client a cui è collegato il giocatore

    /**
     * constructor for the ClientClass
     * @param client the socket the client is connected to
     */
    public ClientClass(Socket client){
        this.socket = client;
    }

    /**
     * initialize a new Player and set it to the local player variable
     * @param name the username of the player in this client
     * @throws Exception
     */
    public void setPlayer(String name) throws Exception{
        player = new Player(name);
    }

    public Player getPlayer() {
        return player;
    }

    public Socket getSocket(){
        return socket;
    }

    public Socket getClient(){
        return this.socket;
    }
}
