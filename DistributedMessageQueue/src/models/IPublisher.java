package models;

public interface IPublisher {

    void publish(Message message, String topicId);
}
