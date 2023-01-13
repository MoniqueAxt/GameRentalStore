package se.oop.messagesystem;

import java.util.ArrayList;

public class MessageBox {
    private final String ownerID;
    private final ArrayList<Message> inbox;
    private final ArrayList<Message> outbox;


    public MessageBox(final String ownerID) {
        this.inbox = new ArrayList<>();
        this.outbox = new ArrayList<>();
        this.ownerID = ownerID;
    }


    public boolean addMessageToInbox(Message message) {
        return inbox.add(message);
    }

    public boolean addMessageToOutbox(Message message) {
        return outbox.add(message);
    }


    /*********************************************************
     * Getters and Setters
     /*********************************************************/
    public String getOwnerID() {
        return ownerID;
    }

    public ArrayList<Message> getInbox() {
        return inbox;
    }

    public ArrayList<Message> getOutbox() {
        return outbox;
    }


}

