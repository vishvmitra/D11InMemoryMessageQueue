package services;

import models.Message;
import models.Subscriber;
import models.Topic;

public interface IQueueService {

    Topic createTopic(String topicId, String topicName);

    void subscribe(Subscriber subscriber, Topic topic);

    void publish(Topic topic,Message message);

    // offset handling
    void resetOffset(String subscriberId, int resetToIndex);
}
