package services;

import models.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Dream11QueueService implements IQueueService {

    Map<String, Topic> topicMap;
    List<IPublisher> publishers;

    public Dream11QueueService() {
        topicMap = new HashMap<>();
        publishers = new ArrayList();
    }

    @Override
    public Topic createTopic(String topicId, String topicName) {
        Topic newTopic = new Topic(topicId, topicName);
        topicMap.put(topicId, newTopic);
        return newTopic;
    }

    @Override
    public void subscribe(Subscriber subscriber, Topic topic) {
        topic.addSubscriber(subscriber);
    }

    @Override
    public void publish(Topic topic, Message message) {
        topic.addMessage(message);
        new Thread(() -> topic.publish()).start();
        //topic.publish();
    }

    @Override
    public void resetOffset(String subscriberId, int resetToIndex){
        return; // throw not implemented exception
    }
}
