package models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class Topic {
    private String topicId;
    private String topicName;
    private List<Message> messageList;
    private List<ISubscriber> subscribers;
    private Map<String, TopicSubscriber> topicSubscribers;
    private Map<String, AtomicInteger> subscriberOffsetMap;

    public Topic(String topicId, String topicName) {
        this.topicId = topicId;
        this.topicName = topicName;
        this.subscriberOffsetMap = new HashMap<>();
        this.subscribers = new ArrayList<ISubscriber>();
        this.topicSubscribers = new HashMap<>();
        this.messageList = new ArrayList<>();
    }

    public String getTopicId() {
        return topicId;
    }

    public String getTopicName() {
        return topicName;
    }

    public List<Message> getMessageList() {
        return messageList;
    }

    public void setMessageList(List<Message> messageList) {
        this.messageList = messageList;
    }

    public List<ISubscriber> getSubscribers() {
        return subscribers;
    }

    public void setSubscribers(List<ISubscriber> subscribers) {
        this.subscribers = subscribers;
    }

    public AtomicInteger getOffset(String subscriberId) {
        return subscriberOffsetMap.get(subscriberId);
    }

    public void setOffset(String subscriberId, int offset) {
        subscriberOffsetMap.put(subscriberId, new AtomicInteger(offset));
    }

    public Message getMessage(int index) {
        return messageList.get(index);
    }

    public void addMessage(Message message) {
        messageList.add(message);
    }

    public int getMessageListSize() {
        return messageList.size();
    }

    public void addSubscriber(Subscriber subscriber){
        subscribers.add(subscriber);
        subscriberOffsetMap.put(subscriber.getId(), new AtomicInteger(0));
        TopicSubscriber topicSubscriber = new TopicSubscriber(subscriber, this);
        this.topicSubscribers.put(subscriber.getId(), topicSubscriber);
    }

    public void publish() {
        System.out.println("Start Publishing");
        for (ISubscriber subscriber : this.getSubscribers()) {
            startSubcriberWorkers(subscriber);
        }
    }

    private void startSubcriberWorkers(ISubscriber subscriber) {
        final String subscriberId = subscriber.getId();
        TopicSubscriber topicSubscriber = null;
        if (!topicSubscribers.containsKey(subscriberId)) {
            topicSubscriber = new TopicSubscriber(subscriber, this);
        }
        else {
            topicSubscriber = topicSubscribers.get(subscriberId);
        }

        topicSubscriber.wakeUpIfNeeded();
    }
}
