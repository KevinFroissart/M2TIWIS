package fr.univlyon1.m2tiw.is.machine.services;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import fr.univlyon1.m2tiw.is.machine.services.dtos.MachineDTO;

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

	public void getOrCreateMachine() {
		if (!isMachineInCatalogue(configurationService.getMachineNumber())) {
			createMachine();
		}
	}

	private boolean isMachineInCatalogue(Long machineNumber) {
		try {
			String url = catalogueUrl + "/machine/{machineNumber}";
			return restTemplate.getForEntity(url, MachineDTO.class, machineNumber).getStatusCode() == HttpStatus.OK;
		} catch (RestClientException e) {
			return false;
		}
	}

	private void createMachine() {
		MachineDTO machine = new MachineDTO();
		machine.setQueue(queueName);
		String url = catalogueUrl + "/machine";
		ResponseEntity<MachineDTO> response = restTemplate.postForEntity(url, machine, MachineDTO.class);
		configurationService.setMachineNumber(Objects.requireNonNull(response.getBody()).getId());
	}

}
