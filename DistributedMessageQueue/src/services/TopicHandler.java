package services;

import models.ISubscriber;
import models.Topic;
import models.TopicSubscriber;

import java.util.HashMap;
import java.util.Map;

public class TopicHandler {
    private final Topic topic;
    private Map<String, TopicSubscriber> subscriberWorkersMap;

    public Topic getTopic() {
        return topic;
    }

    public Map<String, TopicSubscriber> getSubscriberWorkersMap() {
        return subscriberWorkersMap;
    }

    public void setSubscriberWorkersMap(Map<String, TopicSubscriber> subscriberWorkersMap) {
        this.subscriberWorkersMap = subscriberWorkersMap;
    }

    public TopicHandler(Topic topic) {
        this.topic = topic;
        this.subscriberWorkersMap = new HashMap<>();
    }

    public void publish() {
        System.out.println("Start Publishing");
        for (ISubscriber subscriber : this.topic.getSubscribers()) {
            startSubcriberWorkers(subscriber);
        }
    }

    private void startSubcriberWorkers(ISubscriber subscriber) {
        final String subscriberId = subscriber.getId();
        TopicSubscriber topicSubscriber = null;
        if (!subscriberWorkersMap.containsKey(subscriberId)) {
            topicSubscriber = new TopicSubscriber(subscriber, this.topic);
        }
        else {
            topicSubscriber = subscriberWorkersMap.get(subscriberId);
        }

        topicSubscriber.wakeUpIfNeeded();
    }
}
