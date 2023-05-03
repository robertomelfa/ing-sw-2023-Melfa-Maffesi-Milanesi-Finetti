package it.polimi.ingsw.Network.Client.Socket;

import it.polimi.ingsw.Model.Player;

import java.net.Socket;

public class ClientClass {
    private Player player; // giocatore

    private Socket socket;  // il client a cui è collegato il giocatore


    public ClientClass(Socket client){
        this.socket = socket;
    }

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
