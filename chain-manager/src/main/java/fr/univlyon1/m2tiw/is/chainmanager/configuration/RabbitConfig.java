package fr.univlyon1.m2tiw.is.chainmanager.configuration;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import lombok.extern.slf4j.Slf4j;

/**
 * Configuration RabbitMQ, permet de communiquer avec machine.
 */
@Slf4j
@Configuration
public class RabbitConfig {

	@Value("${tiw.is.chainmanager.queue}")
	String queueName;

	@Value("${tiw.is.chainmanager.confirm-queue}")
	String confirmQueueName;

	@Value("${tiw.is.chainmanager.config-queue}")
	String configQueueName;

	@Bean
	public Queue machineQueue() {
		return new Queue(queueName, false);
	}

	@Bean
	public Queue confirmQueue() {
		return new Queue(confirmQueueName, false);
	}

	@Bean
	public Queue configQueue() {
		return new Queue(configQueueName, false);
	}

	@Bean
	public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
		return new RabbitTemplate(connectionFactory);
	}
}
