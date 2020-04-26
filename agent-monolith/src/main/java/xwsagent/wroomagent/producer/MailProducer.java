package xwsagent.wroomagent.producer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import xwsagent.wroomagent.config.RabbitMQConfiguration;

@Service
public class MailProducer {

    private static final Logger log = LoggerFactory.getLogger(MailProducer.class);

    private RabbitTemplate rabbitTemplate;

    public MailProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void send() {
        MailMessage mailMessage = new MailMessage("testboy@maildrop.cc,", "Hello!", "Yes, my child.");
        rabbitTemplate.convertAndSend(RabbitMQConfiguration.EXCHANGE_NAME, RabbitMQConfiguration.ROUTING_KEY, mailMessage);
        log.info("Sent a message to >"+mailMessage.getRecipient());
    }
}
