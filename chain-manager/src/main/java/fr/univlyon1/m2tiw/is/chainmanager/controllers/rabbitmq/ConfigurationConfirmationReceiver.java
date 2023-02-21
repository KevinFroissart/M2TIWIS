package fr.univlyon1.m2tiw.is.chainmanager.controllers.rabbitmq;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;

@Slf4j
// Rmq: injection du nom de la queue à partir de la configuration définie dans application.properties
@RabbitListener(queues = "${tiw.is.chainmanager.queue}")
@Component
public class ConfigurationConfirmationReceiver {

    @RabbitHandler
    public void receive(String message) {
        log.info("Received <" + message + ">");
    }

    @RabbitHandler
    public void receive(byte[] message) {
        receive(new String(message, StandardCharsets.UTF_8));
    }
}
