package pubsub.infrastructure.api;

import com.google.cloud.pubsub.v1.Subscriber;
import org.springframework.cloud.gcp.pubsub.PubSubAdmin;
import org.springframework.cloud.gcp.pubsub.core.PubSubTemplate;
import org.springframework.cloud.gcp.pubsub.support.AcknowledgeablePubsubMessage;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@RestController
@RequestMapping("/pubSub")
public class PubSubController {

    private final PubSubTemplate pubSubTemplate;

    private final PubSubAdmin pubSubAdmin;

    private final ArrayList<Subscriber> allSubscribers;

    public PubSubController(PubSubTemplate pubSubTemplate, PubSubAdmin pubSubAdmin) {
        this.pubSubTemplate = pubSubTemplate;
        this.pubSubAdmin = pubSubAdmin;
        this.allSubscribers = new ArrayList<>();
    }

    @PostMapping("/createTopic")
    public String createTopic(@RequestParam("topicName") String topicName) {
        this.pubSubAdmin.createTopic(topicName);

        return "Topic creation successful.";
    }

    @PostMapping("/createSubscription")
    public String createSubscription(@RequestParam("topicName") String topicName,
                                           @RequestParam("subscriptionName") String subscriptionName) {
        this.pubSubAdmin.createSubscription(subscriptionName, topicName);

        return "Subscription creation successful.";
    }

    @GetMapping("/postMessage")
    public String publish(@RequestParam("topicName") String topicName,
                                @RequestParam("message") String message, @RequestParam("count") int messageCount) {
        for (int i = 0; i < messageCount; i++) {
            this.pubSubTemplate.publish(topicName, message);
        }

        return "Messages published asynchronously; status unknown.";
    }

    @GetMapping("/pull")
    public String pull(@RequestParam("subscription1") String subscriptionName) {

        Collection<AcknowledgeablePubsubMessage> messages = this.pubSubTemplate.pull(subscriptionName, 10, true);

        if (messages.isEmpty()) {
            return "No messages available for retrieval.";
        }

        String returnView;
        try {
            ListenableFuture<Void> ackFuture = this.pubSubTemplate.ack(messages);
            ackFuture.get();
            returnView = "Pulled and acked "+messages.size()+" message(s)";
        }
        catch (Exception ex) {
            System.out.println("Acking failed.");
            returnView = "Acking failed";
        }

        return returnView;
    }

    @GetMapping("/multipull")
    public String multipull(
            @RequestParam("subscription1") String subscriptionName1,
            @RequestParam("subscription2") String subscriptionName2) {

        Set<AcknowledgeablePubsubMessage> mixedSubscriptionMessages = new HashSet<>();
        mixedSubscriptionMessages.addAll(this.pubSubTemplate.pull(subscriptionName1, 1000, true));
        mixedSubscriptionMessages.addAll(this.pubSubTemplate.pull(subscriptionName2, 1000, true));

        if (mixedSubscriptionMessages.isEmpty()) {
            return "No messages available for retrieval.";
        }

        String returnView;
        try {
            ListenableFuture<Void> ackFuture = this.pubSubTemplate.ack(mixedSubscriptionMessages);
            ackFuture.get();
            returnView = "Pulled and acked "+mixedSubscriptionMessages.size()+" message(s)";
        }
        catch (Exception ex) {
            returnView = "Acking failed";
        }

        return returnView;
    }

    @GetMapping("/subscribe")
    public String subscribe(@RequestParam("subscription") String subscriptionName) {
        Subscriber subscriber = this.pubSubTemplate.subscribe(subscriptionName, (message) -> {
            System.out.println("Message received from " + subscriptionName + " subscription: "
                    + message.getPubsubMessage().getData().toStringUtf8());
            message.ack();
        });

        this.allSubscribers.add(subscriber);
        return "Subscribed.";
    }

    @PostMapping("/deleteTopic")
    public String deleteTopic(@RequestParam("topic") String topicName) {
        this.pubSubAdmin.deleteTopic(topicName);

        return "Topic deleted successfully.";
    }

    @PostMapping("/deleteSubscription")
    public String deleteSubscription(@RequestParam("subscription") String subscriptionName) {
        this.pubSubAdmin.deleteSubscription(subscriptionName);

        return "Subscription deleted successfully.";
    }

}
