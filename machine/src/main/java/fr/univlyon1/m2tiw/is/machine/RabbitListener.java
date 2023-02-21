package fr.univlyon1.m2tiw.is.machine;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import fr.univlyon1.m2tiw.is.machine.services.ConfigurationService;
import fr.univlyon1.m2tiw.is.machine.services.dtos.VehicleDTO;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class RabbitListener {

	private final ConfigurationService configurationService;

	@Autowired
	public RabbitListener(ConfigurationService configurationService) {
		this.configurationService = configurationService;
	}

	@org.springframework.amqp.rabbit.annotation.RabbitListener(queues = "${tiw.is.machine.queue}")
	public void receiveReconfiguration(@Payload String payload) {
		log.info("RabbitListener received: {}", payload);
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			VehicleDTO vehicleDTO = objectMapper.readValue(payload, VehicleDTO.class);
			configurationService.reconfigure(vehicleDTO);
		} catch (Exception e) {
			log.error("Error deserializing VehicleDTO object: ", e);
		}
	}

}
