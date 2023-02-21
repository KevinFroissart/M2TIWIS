package fr.univlyon1.m2tiw.is.catalogue.service;

import fr.univlyon1.m2tiw.is.catalogue.model.Configuration;
import fr.univlyon1.m2tiw.is.catalogue.model.Machine;
import fr.univlyon1.m2tiw.is.catalogue.model.Option;
import fr.univlyon1.m2tiw.is.catalogue.repository.ConfigurationRepository;
import fr.univlyon1.m2tiw.is.catalogue.repository.MachineRepository;
import fr.univlyon1.m2tiw.is.catalogue.repository.OptionRepository;
import fr.univlyon1.m2tiw.is.catalogue.service.dto.ConfigurationDTO;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class ConfigurationServiceTest {
    @Autowired
    private MachineRepository machineRepository;
    @Autowired
    private OptionRepository optionRepository;
    @Autowired
    private ConfigurationRepository configurationRepository;
    @Autowired
    private ConfigurationService configurationService;

    private Machine m1;
    private Option o1;
    private static String TN = ConfigurationServiceTest.class.getName() + " test - ";

    @BeforeEach
    public void setup() {
        m1 = new Machine(null, TN + " modele 1");
        machineRepository.save(m1);
        o1 = new Option(TN + " option 1");
        optionRepository.save(o1);
    }

    @AfterEach
    public void teardown() {
        machineRepository.deleteById(m1.getId());
        optionRepository.deleteById(o1.getNom());
    }

    @Test
    public void updateOption() throws NoSuchMachineException, NoSuchOptionException {
        Configuration configuration = new Configuration(m1, o1, TN + " cfg 1");
        var dto = configurationService.updateConfiguration(new ConfigurationDTO(configuration));
        assertEquals(m1.getId(), dto.machineId);
        assertEquals(o1.getNom(), dto.optionId);
        assertEquals(configuration.getCfg(), dto.cfg);
        // do it twice
        dto = configurationService.updateConfiguration(new ConfigurationDTO(configuration));
        assertEquals(m1.getId(), dto.machineId);
        assertEquals(o1.getNom(), dto.optionId);
        assertEquals(configuration.getCfg(), dto.cfg);
        configurationRepository.deleteById(new Configuration.Key(m1, o1));
    }

    @Test
    public void getConfiguration() throws NoSuchMachineException, NoSuchOptionException, NoSuchConfigurationException {
        Configuration configuration = new Configuration(m1, o1, TN + " cfg 2");
        var dto = configurationService.updateConfiguration(new ConfigurationDTO(configuration));
        var dto2 = configurationService.getConfiguration(m1.getId(), o1.getNom());
        assertEquals(m1.getId(), dto2.machineId);
        assertEquals(o1.getNom(), dto2.optionId);
        assertEquals(dto.cfg, dto2.cfg);
        configurationRepository.deleteById(new Configuration.Key(m1, o1));
        assertThrows(NoSuchConfigurationException.class, () -> configurationService.getConfiguration(dto.machineId, dto.optionId));
    }

    @Test
    public void getAllConfigurations() throws NoSuchMachineException, NoSuchOptionException {
        configurationRepository.deleteAll();
        Configuration configuration = new Configuration(m1, o1, TN + " cfg 3");
        var dto = configurationService.updateConfiguration(new ConfigurationDTO(configuration));
        var cfgs = configurationService.getAllConfigurations();
        assertEquals(1, cfgs.size());
        assertEquals(dto.cfg, cfgs.stream().findFirst().get().cfg);
        configurationRepository.deleteById(new Configuration.Key(m1, o1));
    }
}
