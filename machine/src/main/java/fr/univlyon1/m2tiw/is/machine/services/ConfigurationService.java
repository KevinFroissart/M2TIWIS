package fr.univlyon1.m2tiw.is.machine.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class ConfigurationService {
    @Value("${tiw.is.machine.queue}")
    private String queueName;

    public String getQueueName() {
        return queueName;
    }
}
