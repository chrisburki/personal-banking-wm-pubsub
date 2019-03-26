package pubsub.domain.subscriber;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.cloud.gcp.pubsub.core.PubSubTemplate;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;

@Configuration
public class SubscriberConfig {

    private final PubSubTemplate pubSubTemplate;

    private final Subscriber subscriber;

    @Autowired
    public SubscriberConfig(PubSubTemplate pubSubTemplate, Subscriber subscriber) {
        this.pubSubTemplate = pubSubTemplate;
        this.subscriber = subscriber;
    }

    /**
     * It's called only when the application is ready to receive requests.
     * Passes a consumer implementation when subscribing to a Pub/Sub topic.
     */
    @EventListener(ApplicationReadyEvent.class)
    public void subscribe() {
        System.out.println("Subscribing "+subscriber.getClass().getSimpleName()+" to "+subscriber.subscription());
        pubSubTemplate.subscribe(subscriber.subscription(), subscriber.consumer());
    }

}
