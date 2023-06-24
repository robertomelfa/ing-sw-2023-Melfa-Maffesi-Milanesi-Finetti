package it.polimi.ingsw.Network.Messages;


import java.io.Serializable;

public class Message implements Serializable {
    private MessageType type;
    private String message;

    /**
     * constructor for the message
     * @param type the type of the message
     * @param message the string containing the message we want to send
     */
    public  Message(MessageType type, String message){
        this.type=type;
        this.message=message;
    }

    /**
     *
     * @return the type of the message
     */
    public MessageType getType() {
        return type;
    }

    /**
     *
     * @return the message string
     */
    public String getMessage() {
        return message;
    }
}




