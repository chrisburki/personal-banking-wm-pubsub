package pubsub.domain.subscriber;


import com.google.pubsub.v1.PubsubMessage;
import org.springframework.cloud.gcp.pubsub.support.BasicAcknowledgeablePubsubMessage;
import org.springframework.stereotype.Component;

import java.util.function.Consumer;

@Component
public class Subscriber {

    public String subscription() {
        return "pubsub-subscription";
    }

    protected void consume(BasicAcknowledgeablePubsubMessage acknowledgeablePubsubMessage) {
        // extract wrapped message
        PubsubMessage message = acknowledgeablePubsubMessage.getPubsubMessage();

        // process message
        System.out.println("message received: " + message.getData().toStringUtf8());

        // acknowledge that message was received
        acknowledgeablePubsubMessage.ack();
    }

    public Consumer<BasicAcknowledgeablePubsubMessage> consumer() {
        return basicAcknowledgeablePubsubMessage -> consume(basicAcknowledgeablePubsubMessage);
    }

}
