package models;

public class Subscriber implements ISubscriber{
    private final String id;
    private final String name;
    private static final int sleepTimeInMillis = 10000;

    public Subscriber(String id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public String getId() {
        return this.id;
    }

    @Override
    public void process(Message message) throws InterruptedException {
        System.out.println("Subscriber: " + this.id + " started processing:" + message.getMessageId());
        Thread.sleep(sleepTimeInMillis);
        System.out.println("Subscriber: " + this.id + " processed message:" + message.getMessageId());
    }
}
