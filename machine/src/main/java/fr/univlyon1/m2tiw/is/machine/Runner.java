package fr.univlyon1.m2tiw.is.machine;

import fr.univlyon1.m2tiw.is.machine.services.ConfigurationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class Runner implements CommandLineRunner {
    private ConfigurationService configurationService;

    public Runner(ConfigurationService configurationService) {
        this.configurationService = configurationService;
    }

    @Override
    public void run(String... args) {
        log.info("Reçoit les messages sur la queue '{}'", configurationService.getQueueName());
    }
}
