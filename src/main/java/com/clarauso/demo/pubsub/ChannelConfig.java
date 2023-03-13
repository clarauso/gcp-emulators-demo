package com.clarauso.demo.pubsub;

import com.google.cloud.spring.pubsub.core.PubSubTemplate;
import com.google.cloud.spring.pubsub.integration.AckMode;
import com.google.cloud.spring.pubsub.integration.inbound.PubSubInboundChannelAdapter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.channel.PublishSubscribeChannel;
import org.springframework.messaging.MessageChannel;

/** Spring Integration configuration to handle Pub/Sub. */
@Configuration
public class ChannelConfig {

  public static final String TEST_SUB_CHANNEL = "testSubscriptionChannel";

  @Value("${config.pubsub.test-subscription}")
  private String testSubscription;

  @Bean
  public MessageChannel testSubscriptionChannel() {
    return new PublishSubscribeChannel();
  }

  @Bean
  public PubSubInboundChannelAdapter inboundChannelAdapter(
      @Qualifier(TEST_SUB_CHANNEL) MessageChannel messageChannel, PubSubTemplate pubSubTemplate) {
    PubSubInboundChannelAdapter adapter =
        new PubSubInboundChannelAdapter(pubSubTemplate, testSubscription);
    adapter.setOutputChannel(messageChannel);
    adapter.setAckMode(AckMode.MANUAL);
    adapter.setPayloadType(String.class);
    return adapter;
  }
}
