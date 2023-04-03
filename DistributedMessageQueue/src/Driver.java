import models.*;
import services.Dream11QueueService;
import services.IQueueService;

public class Driver {

    public static void main(String[] args) throws InterruptedException{
            final IQueueService dream11QueueService = new Dream11QueueService();
            Topic topic1 = dream11QueueService.createTopic("T1", "T1");
            Topic topic2 = dream11QueueService.createTopic("T2", "T2");

            Subscriber subscriber1 = new Subscriber("Sub1", "Sub1");
            Subscriber subscriber2 = new Subscriber("Sub2", "Sub2");

            dream11QueueService.subscribe(subscriber1, topic1);
            dream11QueueService.subscribe(subscriber2, topic1);

            Subscriber subscriber3 = new Subscriber("Sub3", "Sub3");
            dream11QueueService.subscribe(subscriber3, topic2);

            dream11QueueService.publish(topic1, new TextMessage("m1"));
            dream11QueueService.publish(topic1, new TextMessage("m2"));
            dream11QueueService.publish(topic2, new TextMessage("m3"));

            //Thread.sleep(15000);

            dream11QueueService.publish(topic2, new TextMessage("m4"));
            dream11QueueService.publish(topic1, new TextMessage("m5"));
    }
}
