package fr.univlyon1.m2tiw.is.catalogue.controller;

import fr.univlyon1.m2tiw.is.catalogue.service.NoSuchOptionException;
import fr.univlyon1.m2tiw.is.catalogue.service.OptionService;
import fr.univlyon1.m2tiw.is.catalogue.service.dto.OptionDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@Slf4j
@RestController
@RequestMapping(path = "/option")
public class OptionController {

    private final OptionService optionService;

    public OptionController(OptionService optionService) {
        this.optionService = optionService;
    }

    /**
     * Retourne une collection de {@link OptionDTO}.
     * @return les options.
     */
    @GetMapping()
    public Collection<OptionDTO> getOptions() {
        return optionService.getOptions();
    }


    /**
     * Retourne une {@link OptionDTO} pour un nom donné.
     * @param nom le nom de l'option.
     * @return l'option.
     */
    @GetMapping("/{nom}")
    public ResponseEntity<OptionDTO> getOption(@PathVariable("nom") String nom) {
        try {
            return new ResponseEntity<>(optionService.getOption(nom), HttpStatus.OK);
        } catch (NoSuchOptionException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Crée une {@link OptionDTO}.
     * @param optionDTO l'option à créer.
     * @return l'option créée.
     */
    @PostMapping()
    public ResponseEntity<OptionDTO> createOption(@RequestBody OptionDTO optionDTO) {
        log.info("Creating option with {}", optionDTO);
        return new ResponseEntity<>(optionService.createOption(optionDTO), HttpStatus.CREATED);
    }

    /**
     * Met à jour une {@link OptionDTO}.
     * @param optionDTO l'option à mettre à jour.
     * @return l'option mise à jour.
     */
    @PutMapping
    public ResponseEntity<OptionDTO> updateOption(@RequestBody OptionDTO optionDTO) {
        try {
            log.info("Updating option with {}", optionDTO);
            return new ResponseEntity<>(optionService.updateOption(optionDTO), HttpStatus.NO_CONTENT);
        } catch (NoSuchOptionException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }

    /**
     * Supprime une {@link OptionDTO} pour un nom donné.
     * @param nom le nom de l'option à supprimer.
     * @return 204 si l'option est supprimée ou 404 si aucune option n'est trouvée pour le nom donné.
     */
    @DeleteMapping
    public ResponseEntity<OptionDTO> deleteOption(@PathVariable("nom") String nom) {
        try {
            log.info("Deleting option with nom {}", nom);
            optionService.deleteOption(nom);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (NoSuchOptionException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
