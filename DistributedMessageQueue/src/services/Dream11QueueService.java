package services;

import models.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Dream11QueueService implements IQueueService {

    Map<String, TopicHandler> topicHandlerMap;
    List<IPublisher> publishers;

    public Dream11QueueService() {
        topicHandlerMap = new HashMap<>();
        publishers = new ArrayList();
    }

    @Override
    public Topic createTopic(String topicId, String topicName) {
        Topic newTopic = new Topic(topicId, topicName);
        TopicHandler topicHandler = new TopicHandler(newTopic);
        topicHandlerMap.put(topicId, topicHandler);
        return newTopic;
    }

    @Override
    public void subscribe(Subscriber subscriber, Topic topic) {
        this.topicHandlerMap.get(topic.getTopicId()).getTopic().addSubscriber(subscriber);
        //topic.addSubscriber(subscriber);
    }

    @Override
    public void publish(Topic topic, Message message) {
        topic.addMessage(message);
        new Thread(() -> topicHandlerMap.get(topic.getTopicId()).publish()).start();
        topicHandlerMap.get(topic.getTopicId()).publish();
    }

    @Override
    public void resetOffset(String subscriberId, int resetToIndex){
        return; // throw not implemented exception
    }
}
