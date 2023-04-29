package it.polimi.ingsw.Network.Client.RMI;

import it.polimi.ingsw.Model.*;
import java.io.Serializable;

public class Client implements Serializable{
    private Player player;

    Client(String name) throws Exception{
        this.player = new Player(name);
    }

    public Player getPlayer() throws Exception{
        return this.player;
    }
}
