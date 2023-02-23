package fr.univlyon1.m2tiw.is.machine.services;

import java.util.Arrays;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import fr.univlyon1.m2tiw.is.machine.services.dtos.MachineDTO;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class CatalogueService {

	private final RestTemplate restTemplate;
	private final String catalogueUrl;
	private final String queueName;
	private final ConfigurationService configurationService;

	@Autowired
	public CatalogueService(RestTemplate restTemplate, ConfigurationService configurationService) {
		this.restTemplate = restTemplate;
		this.catalogueUrl = configurationService.getCatalogueUrl();
		this.queueName = configurationService.getQueueName();
		this.configurationService = configurationService;
	}

	/**
	 * Récupère la machine dans le catalogue ou la crée si elle n'existe pas.
	 */
	public void getOrCreateMachine() {
		if (!isMachineInCatalogue()) {
			log.info("Aucune machine trouvée dans le catalogue avec la queue {}, création d'une nouvelle machine", queueName);
			createMachine();
		}
		else {
			log.info("Une machine a été trouvée dans le catalogue avec la queue {}", queueName);
		}
	}

	/**
	 * Vérifie si la machine est présente dans le catalogue.
	 *
	 * @return true si la machine est présente dans le catalogue, false sinon.
	 */
	private boolean isMachineInCatalogue() {
		try {
			String url = catalogueUrl.concat("/machine");
			return Arrays.stream(Objects.requireNonNull(restTemplate.exchange(url, HttpMethod.GET, null, MachineDTO[].class).getBody()))
					.filter(m -> m.getQueue().equals(queueName))
					.findFirst()
					.map(m -> {
						configurationService.setMachineNumber(m.getId());
						return true;
					})
					.orElse(false);
		}
		catch (RestClientException e) {
			return false;
		}
	}

	/**
	 * Crée une machine dans le catalogue.
	 */
	private void createMachine() {
		MachineDTO machine = new MachineDTO();
		machine.setQueue(queueName);
		String url = catalogueUrl.concat("/machine");
		ResponseEntity<MachineDTO> response = restTemplate.postForEntity(url, machine, MachineDTO.class);
		configurationService.setMachineNumber(Objects.requireNonNull(response.getBody()).getId());
	}

}
