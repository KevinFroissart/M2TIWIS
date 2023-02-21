package fr.univlyon1.m2tiw.is.machine.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

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
}
