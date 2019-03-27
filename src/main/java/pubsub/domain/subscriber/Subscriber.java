package pubsub.domain.subscriber;


import org.springframework.stereotype.Component;

import java.util.function.Consumer;

@Component
public class Subscriber {

    public String subscription() {
        return "pubsub-subscription";
    }

}
