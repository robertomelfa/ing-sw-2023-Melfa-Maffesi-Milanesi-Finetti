package it.polimi.ingsw.Network.Messages;


import java.io.Serializable;

public class Message implements Serializable {
    private MessageType type;
    private String message;

    /**
     * constructor
     * @param type type message
     * @param message string message
     */
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




