package fr.univlyon1.m2tiw.is.machine.controller;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import fr.univlyon1.m2tiw.is.machine.models.Voiture;
import fr.univlyon1.m2tiw.is.machine.services.ConfigurationService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class RabbitMQListener {

	private final ConfigurationService configurationService;

	@Autowired
	public RabbitMQListener(ConfigurationService configurationService) {
		this.configurationService = configurationService;
	}

	/**
	 * Reçoit un message de la queue RabbitMQ et lance la reconfiguration de la machine.
	 *
	 * @param payload le message reçu.
	 */
	@RabbitListener(queues = "${tiw.is.machine.queue}")
	public void receiveReconfiguration(@Payload String payload) {
		log.info("RabbitListener a reçu: {}", payload);
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			Voiture voiture = objectMapper.readValue(payload, Voiture.class);
			configurationService.reconfigure(voiture);
		}
		catch (Exception e) {
			log.error("Erreur de désérialisation de l'objet Voiture: ", e);
		}
	}
}
