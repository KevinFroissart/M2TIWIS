package fr.univlyon1.m2tiw.is.catalogue.utils;

import fr.univlyon1.m2tiw.is.catalogue.model.Machine;
import fr.univlyon1.m2tiw.is.catalogue.model.Option;
import fr.univlyon1.m2tiw.is.catalogue.service.dto.ConfigurationDTO;
import org.mockito.Mockito;

public class ConfigurationDTOMockBuilder {

    private Machine machine;
    private Option option;
    private String cfg;

    public ConfigurationDTO build() {
        final ConfigurationDTO mock = Mockito.mock(ConfigurationDTO.class);

        Mockito.when(mock.getMachine()).thenReturn(machine);
        Mockito.when(mock.getOption()).thenReturn(option);
        Mockito.when(mock.getCfg()).thenReturn(cfg);
        Mockito.when(mock.toConfiguration()).thenCallRealMethod();

        return mock;
    }

    public ConfigurationDTOMockBuilder setMachine(Machine machine) {
        this.machine = machine;
        return this;
    }

    public ConfigurationDTOMockBuilder setOption(Option option) {
        this.option = option;
        return this;
    }

    public ConfigurationDTOMockBuilder setCfg(String cfg) {
        this.cfg = cfg;
        return this;
    }
}
