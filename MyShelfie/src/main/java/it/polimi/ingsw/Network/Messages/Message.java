package it.polimi.ingsw.Network.Messages;


import it.polimi.ingsw.Model.GameTable;
import it.polimi.ingsw.Network.Messages.MessageType;

import java.io.Serializable;

public class Message implements Serializable {
    private MessageType type;
    private String message;

    public  Message(MessageType type, String message){
        this.type=type;
        this.message=message;
    }

    public MessageType getType() {
        return type;
    }

    public String getMessage() {
        return message;
    }
}




