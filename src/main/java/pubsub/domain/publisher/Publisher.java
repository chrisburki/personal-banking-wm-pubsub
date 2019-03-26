package pubsub.domain.publisher;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gcp.pubsub.core.PubSubTemplate;
import org.springframework.stereotype.Component;

@Component
public class Publisher {

    @Value(value = "${message.topic.name}")
    private String topicName;

    private final PubSubTemplate pubSubTemplate;

    protected Publisher(PubSubTemplate pubSubTemplate) {
        this.pubSubTemplate = pubSubTemplate;
    }

    public void publish(String message) {
        System.out.println("publishing to topic ["+topicName+"], message: ["+message+"]");
        pubSubTemplate.publish(topicName, message);
    }

}
