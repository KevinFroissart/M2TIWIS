package fr.univlyon1.m2tiw.is.machine.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import fr.univlyon1.m2tiw.is.machine.services.dtos.ConfigurationDTO;
import fr.univlyon1.m2tiw.is.machine.services.dtos.VehicleDTO;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ConfigurationService {

	@Value("${tiw.is.machine.queue}")
	private String queueName;

	@Value("${tiw.is.machine.number}")
	private Long machineNumber;

	@Value("${tiw.is.catalogue.url}")
	private String catalogueUrl;

	@Value("${tiw.is.catalogue.ping-interval}")
	private int pingInterval;

	private final RestTemplate restTemplate;

	@Autowired
	public ConfigurationService(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
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

	public void reconfigure(VehicleDTO vehicleDTO) {
		log.info("Reconfiguring machine {} with options: {}", machineNumber, vehicleDTO.getOptions());

		for (String optionName : vehicleDTO.getOptions()) {
			ConfigurationDTO configurationDTO = getConfigurationByMachineNumberAndOptionName(machineNumber, optionName);
			log.info("Machine: {}, Option: {}, Configuration: {}", configurationDTO.getMachine().getId(), configurationDTO.getOption(), configurationDTO.getCfg());
		}
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
