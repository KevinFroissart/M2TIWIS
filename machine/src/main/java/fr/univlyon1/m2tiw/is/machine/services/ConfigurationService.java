package fr.univlyon1.m2tiw.is.machine.services;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import fr.univlyon1.m2tiw.is.machine.models.Voiture;
import fr.univlyon1.m2tiw.is.machine.services.dtos.ConfigurationDTO;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ConfigurationService {

	@Value("${tiw.is.machine.queue}")
	private String queueName;

	@Value("${tiw.is.machine.confirm-queue}")
	private String confirmQueueName;

	private Long machineNumber;

	@Value("${tiw.is.catalogue.url}")
	private String catalogueUrl;

	@Value("${tiw.is.catalogue.ping-interval}")
	private int pingInterval;

	private final RestTemplate restTemplate;
	private final RabbitTemplate rabbitTemplate;

	@Autowired
	public ConfigurationService(RestTemplate restTemplate, RabbitTemplate rabbitTemplate) {
		this.restTemplate = restTemplate;
		this.rabbitTemplate = rabbitTemplate;
	}

	public String getQueueName() {
		return queueName;
	}

	public Long getMachineNumber() {
		return machineNumber;
	}

	public String getCatalogueUrl() {
		return catalogueUrl;
	}

	public int getPingInterval() {
		return pingInterval;
	}

	public void setMachineNumber(Long machineNumber) {
		this.machineNumber = machineNumber;
	}

	public void reconfigure(Voiture voiture) throws JsonProcessingException {
		log.info("Reconfiguring machine {} with options: {}", machineNumber, voiture.getOptions());

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		for (String optionName : voiture.getOptions()) {
			HttpEntity<String> requestEntity = new HttpEntity<>("{\"cfg\":\"" + machineNumber + "cfg\"}", headers);
			restTemplate.exchange(catalogueUrl + "/configuration/" + machineNumber + "/" + optionName, HttpMethod.PUT, requestEntity, String.class);

			ConfigurationDTO configurationDTO = getConfigurationByMachineNumberAndOptionName(machineNumber, optionName);
			log.info("Verification for Machine: {} with Option: {}, Configuration: {}", configurationDTO.getMachineId(), configurationDTO.getOptionId(), configurationDTO.getCfg());
		}

		ObjectMapper objectMapper = new ObjectMapper();
		String payload = objectMapper.writeValueAsString(voiture);
		rabbitTemplate.convertAndSend(confirmQueueName, payload);
	}

	private ConfigurationDTO getConfigurationByMachineNumberAndOptionName(Long machineNumber, String optionName) {
		try {
			String url = catalogueUrl + "/configuration/{machineNumber}/{optionName}";
			return restTemplate.getForEntity(url, ConfigurationDTO.class, machineNumber, optionName).getBody();
		}
		catch (RestClientException e) {
			log.error("Error while getting configuration for machine {} and option {}", machineNumber, optionName);
			throw e;
		}
	}

}
