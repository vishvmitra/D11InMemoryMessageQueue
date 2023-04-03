package models;

public class TextMessage extends Message {

    public TextMessage(String messageId, Object content) {
        super(messageId, content);
    }

    public TextMessage(String messageId) {
        super(messageId, null);
    }
}

