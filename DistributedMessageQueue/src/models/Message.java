package models;

public abstract class Message {
    public String getMessageId() {
        return messageId;
    }

    public Object getContent() {
        return content;
    }

    private final String messageId;
    private final Object content;

    public Message(String messageId, Object content) {
        this.messageId = messageId;
        this.content = content;
    }
}
