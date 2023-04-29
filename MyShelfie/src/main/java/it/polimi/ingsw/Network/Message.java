package it.polimi.ingsw.Network;


public class Message{
    private MessageType type;
    private String message;

    public void Message(MessageType type, String message){
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






 enum MessageType {
    sendNickname, requestNickname, notifyBeginTurn, endTurn, ping
}
