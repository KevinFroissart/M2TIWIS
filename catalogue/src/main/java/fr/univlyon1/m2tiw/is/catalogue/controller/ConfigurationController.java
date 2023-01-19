package fr.univlyon1.m2tiw.is.catalogue.controller;

import fr.univlyon1.m2tiw.is.catalogue.model.MachineOptionKey;
import fr.univlyon1.m2tiw.is.catalogue.service.ConfigurationService;
import fr.univlyon1.m2tiw.is.catalogue.service.NoSuchConfigurationException;
import fr.univlyon1.m2tiw.is.catalogue.service.dto.ConfigurationDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@Slf4j
@RestController
@RequestMapping(path = "/configuration")
public class ConfigurationController {

    private final ConfigurationService configurationService;

    public ConfigurationController(ConfigurationService configurationService) {
        this.configurationService = configurationService;
    }

    /**
     * Retourne une collection de {@link ConfigurationDTO}.
     *
     * @return les configurations.
     */
    @GetMapping
    public Collection<ConfigurationDTO> getConfigurations() {
        return configurationService.getConfigurations();
    }


    /**
     * Retourne une {@link ConfigurationDTO} pour une paire {@link MachineOptionKey} donnée.
     *
     * @param machineOptionKey l'id de la configuration.
     * @return la configuration.
     */
    @GetMapping
    public ResponseEntity<ConfigurationDTO> getConfiguration(@RequestBody MachineOptionKey machineOptionKey) {
        try {
            return new ResponseEntity<>(configurationService.getConfiguration(machineOptionKey), HttpStatus.OK);
        } catch (NoSuchConfigurationException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Crée une {@link ConfigurationDTO}.
     *
     * @param configurationDTO la configuration à créer.
     * @return la configuration créée.
     */
    @PostMapping
    public ResponseEntity<ConfigurationDTO> createConfiguration(@RequestBody ConfigurationDTO configurationDTO) {
        log.info("Creating configuration with {}", configurationDTO);
        return new ResponseEntity<>(configurationService.createConfiguration(configurationDTO), HttpStatus.CREATED);
    }

    /**
     * Met à jour une {@link ConfigurationDTO}.
     *
     * @param configurationDTO la configuration à mettre à jour.
     * @return la configuration mise à jour.
     */
    @PutMapping
    public ResponseEntity<ConfigurationDTO> updateConfiguration(@RequestBody ConfigurationDTO configurationDTO) {
        try {
            log.info("Updating configuration with {}", configurationDTO);
            return new ResponseEntity<>(configurationService.updateConfiguration(configurationDTO), HttpStatus.NO_CONTENT);
        } catch (NoSuchConfigurationException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }

    /**
     * Supprime une {@link ConfigurationDTO} pour une paire {@link MachineOptionKey} donnée.
     *
     * @param machineOptionKey l'id de la configuration à supprimer.
     * @return 204 si la configuration est supprimée ou 404 si aucune configuration n'est trouvée pour l'id donné.
     */
    @DeleteMapping
    public ResponseEntity<ConfigurationDTO> deleteConfiguration(@RequestBody MachineOptionKey machineOptionKey) {
        try {
            log.info("Deleting configuration with id {}", machineOptionKey);
            configurationService.deleteConfiguration(machineOptionKey);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (NoSuchConfigurationException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
