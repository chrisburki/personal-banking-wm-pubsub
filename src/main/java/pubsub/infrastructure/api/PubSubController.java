package pubsub.infrastructure.api;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pubsub.domain.outbound.OutboundAdapter;

@RestController
@RequestMapping("/pubSub")
public class PubSubController {

    private final OutboundAdapter.PubSubOutboundGateway messagingGateway;

    public PubSubController(OutboundAdapter.PubSubOutboundGateway messagingGateway) {
        this.messagingGateway = messagingGateway;
    }

    @PostMapping("/publishMessage")
    public String publishMessage(@RequestParam("message") String message) {
        System.out.println("Message received");
        System.out.println("Message Content: " + message);

        this.messagingGateway.sendToPubSub(message);
        return message;
    }
}
