package pubsub.application;

import org.springframework.integration.annotation.MessagingGateway;

public class OutboundService {

    @MessagingGateway(defaultRequestChannel = "pubsubOutputChannel")
    public interface PubsubOutboundGateway {

        void sendToPubsub(String text);
    }
}
