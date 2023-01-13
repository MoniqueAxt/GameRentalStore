package se.oop.messagesystem;

public class Message {
    final private String content;
    final private String senderID;
    final private String receiverID;
    private boolean msgRead = false;

    public Message(final String senderID, final String content, final String receiverID) {
        this.content = content;
        this.receiverID = receiverID;
        this.senderID = senderID;
    }

    @Override
    public String toString() {
        return "Status: " + getReadStatus()
                + "\n Recipient: " + receiverID
                + "\nSender: " + senderID
                + "\nMessage: " + content;

    }

    public String getReadStatus() {
        return msgRead ? "READ" : "UNREAD";
    }

    public void setReadStatus(boolean msgRead) {
        this.msgRead = msgRead;
    }

    public String getContent() {
        return content;
    }

    public String getSenderID() {
        return senderID;
    }

    public String getReceiverID() {
        return receiverID;
    }

    public boolean isMsgRead() {
        return msgRead;
    }

    public void setMsgRead(boolean msgRead) {
        this.msgRead = msgRead;
    }
}
