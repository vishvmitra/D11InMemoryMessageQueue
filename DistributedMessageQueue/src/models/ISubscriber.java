package models;

public interface ISubscriber {

    String getId();

    void process(Message message) throws InterruptedException;
}
