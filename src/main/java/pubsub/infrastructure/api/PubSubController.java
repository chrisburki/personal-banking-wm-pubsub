package pubsub.infrastructure.api;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pubsub.application.PublisherService;

@RestController
@RequestMapping("/pubSub")
public class PubSubController {

    private final PublisherService publisherService;

    public PubSubController(PublisherService publisherService) {
        this.publisherService = publisherService;
    }

    @PostMapping("/publishMessage")
    public String publishMessage(@RequestParam("message") String message) {
        System.out.println("Message received");
        System.out.println("Message Content: " + message);

        this.publisherService.sendMessage(message);
        return message;
    }
}
