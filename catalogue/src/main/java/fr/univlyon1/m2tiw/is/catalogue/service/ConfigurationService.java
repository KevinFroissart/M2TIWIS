package fr.univlyon1.m2tiw.is.catalogue.service;

import fr.univlyon1.m2tiw.is.catalogue.model.Configuration;
import fr.univlyon1.m2tiw.is.catalogue.model.Machine;
import fr.univlyon1.m2tiw.is.catalogue.model.Option;
import fr.univlyon1.m2tiw.is.catalogue.repository.ConfigurationRepository;
import fr.univlyon1.m2tiw.is.catalogue.repository.MachineRepository;
import fr.univlyon1.m2tiw.is.catalogue.repository.OptionRepository;
import fr.univlyon1.m2tiw.is.catalogue.service.dto.ConfigurationDTO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

@Service
@Transactional
public class ConfigurationService {
    private ConfigurationRepository configurationRepository;
    private MachineRepository machineRepository;
    private OptionRepository optionRepository;

    public ConfigurationService(ConfigurationRepository configurationRepository, MachineRepository machineRepository, OptionRepository optionRepository) {
        this.configurationRepository = configurationRepository;
        this.machineRepository = machineRepository;
        this.optionRepository = optionRepository;
    }

    @Transactional
    public ConfigurationDTO updateConfiguration(ConfigurationDTO configurationDTO)
            throws NoSuchMachineException, NoSuchOptionException {
        Machine machine = machineRepository
                .findById(configurationDTO.getMachineId())
                .orElseThrow(() -> new NoSuchMachineException(configurationDTO.getMachineId()));
        Option option = optionRepository
                .findById(configurationDTO.getOptionId())
                .orElseThrow(() -> new NoSuchOptionException(configurationDTO.getOptionId()));
        Configuration configuration = new Configuration(machine, option, configurationDTO.getCfg());
        return new ConfigurationDTO(configurationRepository.save(configuration));
    }

    public ConfigurationDTO getConfiguration(long mId, String optNom)
            throws NoSuchMachineException, NoSuchOptionException, NoSuchConfigurationException {
        Machine machine = machineRepository
                .findById(mId)
                .orElseThrow(() -> new NoSuchMachineException(mId));
        Option option = optionRepository
                .findById(optNom)
                .orElseThrow(() -> new NoSuchOptionException(optNom));
        var key = new Configuration.Key(machine, option);
        return new ConfigurationDTO(configurationRepository
                .findById(key)
                .orElseThrow(() -> new NoSuchConfigurationException(mId, optNom)));
    }

    public Collection<ConfigurationDTO> getAllConfigurations() {
        return configurationRepository.findAll().stream().map(ConfigurationDTO::new).toList();
    }
}
