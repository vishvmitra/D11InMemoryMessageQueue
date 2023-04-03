package models;
import java.util.concurrent.atomic.AtomicInteger;

public class TopicSubscriber implements Runnable {
    private ISubscriber subscriber;
    private Topic topic;

    public TopicSubscriber(ISubscriber subscriber, Topic topic) {
        this.subscriber = subscriber;
        this.topic = topic;
    }

    @Override
    public void run() {
        synchronized (subscriber) {
            do {
                System.out.println("Started Consuming");
                //offset handling
                int currentOffset = topic.getOffset(subscriber.getId()).intValue();
                if (currentOffset >= topic.getMessageList().size()) {
                    // do nothing
                    try {
                        subscriber.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                else {
                    Message messageToProcess = topic.getMessage(currentOffset);
                    try {
                        this.subscriber.process(messageToProcess);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    topic.setOffset(subscriber.getId(), currentOffset + 1);
                }

            } while(true);
        }
    }

    synchronized public void wakeUpIfNeeded() {
        synchronized (subscriber) {
            subscriber.notify();
        }
    }
}
