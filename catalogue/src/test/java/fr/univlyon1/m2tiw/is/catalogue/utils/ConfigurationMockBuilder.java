package fr.univlyon1.m2tiw.is.catalogue.utils;

import fr.univlyon1.m2tiw.is.catalogue.model.Configuration;
import fr.univlyon1.m2tiw.is.catalogue.model.Machine;
import fr.univlyon1.m2tiw.is.catalogue.model.Option;
import org.mockito.Mockito;

public class ConfigurationMockBuilder {

    private Machine machine;
    private Option option;
    private String cfg;

    public Configuration build() {
        final Configuration mock = Mockito.mock(Configuration.class);

        Mockito.when(mock.getMachine()).thenReturn(machine);
        Mockito.when(mock.getOption()).thenReturn(option);
        Mockito.when(mock.getCfg()).thenReturn(cfg);

        return mock;
    }

    public ConfigurationMockBuilder setMachine(Machine machine) {
        this.machine = machine;
        return this;
    }

    public ConfigurationMockBuilder setOption(Option option) {
        this.option = option;
        return this;
    }

    public ConfigurationMockBuilder setCfg(String cfg) {
        this.cfg = cfg;
        return this;
    }
}
