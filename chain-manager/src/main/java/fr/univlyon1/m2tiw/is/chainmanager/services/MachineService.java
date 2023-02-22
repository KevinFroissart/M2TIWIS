package fr.univlyon1.m2tiw.is.chainmanager.services;

import java.util.Arrays;
import java.util.Collection;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import fr.univlyon1.m2tiw.is.chainmanager.models.Voiture;
import fr.univlyon1.m2tiw.is.chainmanager.services.dtos.MachineDTO;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class MachineService {

	private final RestTemplate restTemplate;
	private final RabbitTemplate rabbitTemplate;

	@Autowired
	public MachineService(RestTemplate restTemplate, RabbitTemplate rabbitTemplate) {
		this.restTemplate = restTemplate;
		this.rabbitTemplate = rabbitTemplate;
	}

	public Collection<MachineDTO> getMachines() {
		ResponseEntity<MachineDTO[]> response = restTemplate.getForEntity("http://localhost:8080/machine", MachineDTO[].class);
		return Arrays.asList(response.getBody());
	}

	public void envoieOptionsVoiture(String queueName, Voiture voiture) throws JsonProcessingException {
		Collection<String> options = voiture.getOptions();
		log.info("Envoi des options '{}' pour la voiture {} sur la queue '{}'", options, voiture.getId(), queueName);
		if (queueName != null) {
			String payload = new ObjectMapper().writeValueAsString(voiture);
			rabbitTemplate.convertAndSend(queueName, payload);
		}
	}
}
