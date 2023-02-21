package fr.univlyon1.m2tiw.is.catalogue.controller;

import fr.univlyon1.m2tiw.is.catalogue.service.ConfigurationService;
import fr.univlyon1.m2tiw.is.catalogue.service.NoSuchConfigurationException;
import fr.univlyon1.m2tiw.is.catalogue.service.NoSuchMachineException;
import fr.univlyon1.m2tiw.is.catalogue.service.NoSuchOptionException;
import fr.univlyon1.m2tiw.is.catalogue.service.dto.ConfigurationDTO;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping(path="/configuration")
public class ConfigurationController {

    private ConfigurationService configurationService;

    public ConfigurationController(ConfigurationService configurationService) {
        this.configurationService = configurationService;
    }

    @PutMapping("/{mId}/{optNom}")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public ConfigurationDTO updateConfiguration(@PathVariable("mId") long mId,
                                                @PathVariable("optNom") String optNom,
                                                @RequestBody ConfigurationDTO configurationDTO)
            throws NoSuchMachineException, NoSuchOptionException {
        configurationDTO.setMachineId(mId);
        configurationDTO.setOptionId(optNom);
        return configurationService.updateConfiguration(configurationDTO);
    }

    @GetMapping("/{mId}/{optNom}")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public ConfigurationDTO getConfiguration(@PathVariable("mId") long mId,
                                             @PathVariable("optNom") String optNom)
            throws NoSuchMachineException, NoSuchOptionException, NoSuchConfigurationException {
        return configurationService.getConfiguration(mId, optNom);
    }

    @GetMapping()
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public Collection<ConfigurationDTO> getAllConfigurations() {
        return configurationService.getAllConfigurations();
    }

}
