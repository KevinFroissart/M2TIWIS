package fr.univlyon1.m2tiw.is.chainmanager.services;

import java.util.Arrays;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import fr.univlyon1.m2tiw.is.chainmanager.models.Voiture;
import fr.univlyon1.m2tiw.is.chainmanager.services.dtos.MachineDTO;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class MachineService {

	private final RestTemplate restTemplate;

	@Autowired
	public MachineService(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}

	public Collection<MachineDTO> getMachines() {
		ResponseEntity<MachineDTO[]> response = restTemplate.getForEntity("http://localhost:8080/machine", MachineDTO[].class);
		return Arrays.asList(response.getBody());
	}

	public void envoieOptionsVoiture(String queueName, Voiture voiture) {
		Collection<String> options = voiture.getOptions();
		log.info("Envoi de des {} options '{}' pour la voiture {} sur la queue '{}'",
				options.size(), options, voiture.getId(), queueName);
		// TODO: TP3 utiliser RabbitTemplate pour envoyer une demande
		//  de reconfiguration sur la queue indiquée
	}
}
