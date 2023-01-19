package fr.univlyon1.m2tiw.is.catalogue.service.dto;

import fr.univlyon1.m2tiw.is.catalogue.model.Configuration;
import fr.univlyon1.m2tiw.is.catalogue.model.Machine;
import fr.univlyon1.m2tiw.is.catalogue.model.Option;

import java.lang.String;

public class ConfigurationDTO {

    public Machine machine;
    public Option option;
    public String cfg;

    public ConfigurationDTO() {
    }

    public ConfigurationDTO(Machine machine, Option option, String cfg) {
        this.machine = machine;
        this.option = option;
        this.cfg = cfg;
    }

    public ConfigurationDTO(Configuration configuration) {
        this.machine = configuration.getMachine();
        this.option = configuration.getOption();
        this.cfg = configuration.getCfg();
    }

    public Configuration toConfiguration() {
        return new Configuration(machine, option, cfg);
    }

    @Override
    public String toString() {
        return "<ConfigurationDTO" + machine + " " + option + " " + cfg + ">";
    }

    public Machine getMachine() {
        return machine;
    }

    public void setMachine(Machine machine) {
        this.machine = machine;
    }

    public Option getOption() {
        return option;
    }

    public void setOption(Option option) {
        this.option = option;
    }

    public String getCfg() {
        return cfg;
    }

    public void setCfg(String cfg) {
        this.cfg = cfg;
    }
}
