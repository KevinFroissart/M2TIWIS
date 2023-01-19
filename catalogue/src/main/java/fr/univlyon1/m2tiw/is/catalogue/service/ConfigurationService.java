package fr.univlyon1.m2tiw.is.catalogue.service;

import fr.univlyon1.m2tiw.is.catalogue.model.Configuration;
import fr.univlyon1.m2tiw.is.catalogue.model.MachineOptionKey;
import fr.univlyon1.m2tiw.is.catalogue.repository.ConfigurationRepository;
import fr.univlyon1.m2tiw.is.catalogue.service.dto.ConfigurationDTO;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class ConfigurationService {

    private final ConfigurationRepository configurationRepository;

    public ConfigurationService(ConfigurationRepository configurationRepository) {
        this.configurationRepository = configurationRepository;
    }

    /**
     * Retourne une collection de {@link ConfigurationDTO}.
     *
     * @return les configurations.
     */
    public Collection<ConfigurationDTO> getConfigurations() {
        return configurationRepository.findAll().stream().map(ConfigurationDTO::new).toList();
    }

    /**
     * Retourne une {@link ConfigurationDTO} pour une paire {@link MachineOptionKey} donnée.
     *
     * @param id l'id de la configuration.
     * @return la configuration.
     */
    public ConfigurationDTO getConfiguration(MachineOptionKey machineOptionKey) throws NoSuchConfigurationException {
        return new ConfigurationDTO(
                configurationRepository
                        .findById(machineOptionKey)
                        .orElseThrow(() -> new NoSuchConfigurationException(machineOptionKey)));
    }

    /**
     * Crée une {@link ConfigurationDTO}.
     *
     * @param configurationDTO la configuration à créer.
     * @return la configuration créée.
     */
    public ConfigurationDTO createConfiguration(ConfigurationDTO configurationDTO) {
        Configuration configuration = configurationDTO.toConfiguration();
        configuration = configurationRepository.save(configuration);
        return new ConfigurationDTO(configuration);
    }

    /**
     * Met à jour une {@link ConfigurationDTO}.
     *
     * @param configurationDTO la configuration à mettre à jour.
     * @return la configuration mise à jour.
     */
    public ConfigurationDTO updateConfiguration(ConfigurationDTO configurationDTO) throws NoSuchConfigurationException {
        MachineOptionKey machineOptionKey = new MachineOptionKey(configurationDTO.getMachine(), configurationDTO.getOption());
        Configuration configuration = configurationRepository
                .findById(machineOptionKey)
                .orElseThrow(() -> new NoSuchConfigurationException(machineOptionKey));
        configuration.setCfg(configurationDTO.getCfg());
        configuration = configurationRepository.save(configuration);
        return new ConfigurationDTO(configuration);
    }

    /**
     * Supprime une {@link ConfigurationDTO} pour une paire {@link MachineOptionKey} donnée.
     *
     * @param machineOptionKey l'id de la configuration à supprimer.
     */
    public void deleteConfiguration(MachineOptionKey machineOptionKey) throws NoSuchConfigurationException {
        Configuration configuration = configurationRepository
                .findById(machineOptionKey)
                .orElseThrow(() -> new NoSuchConfigurationException(machineOptionKey));
        configurationRepository.delete(configuration);
    }

}
