package pubsub.application;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class PublisherService {

    @Value(value = "${message.topic.name}")
    private String topicName;

/*    private final Publisher publisher;

    public PublisherService(Publisher publisher) {
        this.publisher = publisher;
    }

    public void sendMessage(String message) {
       this.publisher.publish(message);
    }*/
}
