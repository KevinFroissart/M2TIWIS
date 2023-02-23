package fr.univlyon1.m2tiw.is.machine.configuration;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;

/**
 * Configuration RabbitMQ, permet de communiquer avec chain-manager.
 */
public class RabbitConfig {

	@Value("${tiw.is.machine.queue}")
	String queueName;

	@Value("${tiw.is.machine.confirm-queue}")
	String confirmQueueName;

	@Value("${tiw.is.machine.config-queue}")
	String configQueueName;

	@Bean
	public Queue machineQueue() {
		return new Queue(queueName, false);
	}

	@Bean
	public Queue confirmMachineQueue() {
		return new Queue(confirmQueueName, false);
	}

	@Bean
	public Queue configMachineQueue() {
		return new Queue(configQueueName, false);
	}

	@Bean
	public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
		return new RabbitTemplate(connectionFactory);
	}

}
