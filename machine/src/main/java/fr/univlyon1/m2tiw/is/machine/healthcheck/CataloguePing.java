package fr.univlyon1.m2tiw.is.machine.healthcheck;

import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import fr.univlyon1.m2tiw.is.machine.services.ConfigurationService;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class CataloguePing {

	private final ConfigurationService configurationService;

	private RestTemplate restTemplate;

	private int pingCount = 0;

	@Autowired
	public CataloguePing(RestTemplate restTemplate, ConfigurationService configurationService) {
		this.restTemplate = restTemplate;
		this.configurationService = configurationService;
	}

	@PostConstruct
	private void init() {
		restTemplate = new RestTemplate();
		Executors.newSingleThreadScheduledExecutor()
				.scheduleWithFixedDelay(this::pingCatalogue, 0, configurationService.getPingInterval(), TimeUnit.MILLISECONDS);
	}

	private void pingCatalogue() {
		try {
			restTemplate.getForObject(configurationService.getCatalogueUrl() + "/machine", Void.class);
			pingCount++;
		} catch (RestClientException e) {
			log.error("Catalogue is not responding, ping count: {}, interval is {}", pingCount, configurationService.getPingInterval());
		}
	}
}

