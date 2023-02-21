package fr.univlyon1.m2tiw.is.catalogue.service.dto;

import fr.univlyon1.m2tiw.is.catalogue.model.Configuration;

public class ConfigurationDTO {
    public Long machineId;
    public String optionId;
    public String cfg;

    public ConfigurationDTO(Long machineId, String optionId, String cfg) {
        this.machineId = machineId;
        this.optionId = optionId;
        this.cfg = cfg;
    }

    public ConfigurationDTO() {
    }

    public ConfigurationDTO(Configuration configuration) {
        this(configuration.getMachine().getId(), configuration.getOption().getNom(), configuration.getCfg());
    }

    public Long getMachineId() {
        return machineId;
    }

    public void setMachineId(Long machineId) {
        this.machineId = machineId;
    }

    public String getOptionId() {
        return optionId;
    }

    public void setOptionId(String optionId) {
        this.optionId = optionId;
    }

    public String getCfg() {
        return cfg;
    }

    public void setCfg(String cfg) {
        this.cfg = cfg;
    }
}
