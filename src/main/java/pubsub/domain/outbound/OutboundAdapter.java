package pubsub.domain.outbound;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gcp.pubsub.core.PubSubTemplate;
import org.springframework.cloud.gcp.pubsub.integration.outbound.PubSubMessageHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.integration.annotation.MessagingGateway;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.messaging.MessageHandler;

public class OutboundAdapter {

    @Value(value = "${message.topic.name}")
    private String topicName;

    @Bean
    @ServiceActivator(inputChannel = "pubSubOutputChannel")
    public MessageHandler messageSender(PubSubTemplate pubsubTemplate) {
        return new PubSubMessageHandler(pubsubTemplate, topicName);
    }

    @MessagingGateway(defaultRequestChannel = "pubSubOutputChannel")
    public interface PubSubOutboundGateway {

        void sendToPubSub(String text);
    }
}
