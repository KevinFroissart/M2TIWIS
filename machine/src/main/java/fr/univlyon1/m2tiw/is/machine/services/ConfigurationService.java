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
import fr.univlyon1.m2tiw.is.machine.services.dtos.VoitureMachineJobsDTO;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ConfigurationService {

	@Value("${tiw.is.machine.queue}")
	private String queueName;

	@Value("${tiw.is.machine.confirm-queue}")
	private String confirmQueueName;

	@Value("${tiw.is.machine.config-queue}")
	private String configQueueName;

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
		VoitureMachineJobsDTO voitureMachineJobsDTO = new VoitureMachineJobsDTO(voiture.getId(), queueName);
		ObjectMapper objectMapper = new ObjectMapper();

		log.info("Envoi du signal de début de reconfiguration de la machine {} avec les options: {}", getMachineNumber(), voiture.getOptions());
		String payload = objectMapper.writeValueAsString(voitureMachineJobsDTO);
		rabbitTemplate.convertAndSend(configQueueName, payload);

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		int maxLen = 0;

		for (String optionName : voiture.getOptions()) {
			HttpEntity<String> requestEntity = new HttpEntity<>("{\"cfg\":\"" + getMachineNumber() + "cfg\"}", headers);
			restTemplate.exchange(catalogueUrl + "/configuration/" + getMachineNumber() + "/" + optionName, HttpMethod.PUT, requestEntity, String.class);

			ConfigurationDTO configurationDTO = getConfigurationByMachineNumberAndOptionName(getMachineNumber(), optionName);
			log.info("Vérification pour la machine: {} avec les options: {}, et la configuration: {}", configurationDTO.getMachineId(), configurationDTO.getOptionId(), configurationDTO.getCfg());

			maxLen = Math.max(maxLen, optionName.length());
		}

		try {
			Thread.sleep(maxLen * 1000L);
		}
		catch (InterruptedException e) {
			log.error("Erreur survenue au cours du sleep(): {}", e.getMessage());
			Thread.currentThread().interrupt();
		}

		log.info("Envoi du signal de fin de reconfiguration de la machine {} avec les options: {}", machineNumber, voiture.getOptions());
		rabbitTemplate.convertAndSend(confirmQueueName, payload);
	}

	private ConfigurationDTO getConfigurationByMachineNumberAndOptionName(Long machineNumber, String optionName) {
		try {
			String url = catalogueUrl + "/configuration/{machineNumber}/{optionName}";
			return restTemplate.getForEntity(url, ConfigurationDTO.class, machineNumber, optionName).getBody();
		}
		catch (RestClientException e) {
			log.error("Erreur lors de la récupération de la configuration pour la machine {} et l'option {}", machineNumber, optionName);
			throw e;
		}
	}

}
