package com.clarauso.demo.pubsub;

import com.google.cloud.spring.pubsub.support.BasicAcknowledgeablePubsubMessage;
import com.google.cloud.spring.pubsub.support.GcpPubSubHeaders;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

/** Test message consumer, only logs payload. */
@Component
public class MessageConsumer {

  private static final Logger log = LoggerFactory.getLogger(MessageConsumer.class);

  @ServiceActivator(inputChannel = ChannelConfig.TEST_SUB_CHANNEL)
  public void consume(
      String payload,
      @Header(GcpPubSubHeaders.ORIGINAL_MESSAGE) BasicAcknowledgeablePubsubMessage message) {
    log.info(
        "Message received headers={} payload={}",
        message.getPubsubMessage().getAttributesMap(),
        payload);
    // manual ack
    message.ack();
  }
}
