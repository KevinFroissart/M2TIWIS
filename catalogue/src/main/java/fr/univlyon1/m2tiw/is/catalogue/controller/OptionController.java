package fr.univlyon1.m2tiw.is.catalogue.controller;

import fr.univlyon1.m2tiw.is.catalogue.service.NoSuchOptionException;
import fr.univlyon1.m2tiw.is.catalogue.service.OptionService;
import fr.univlyon1.m2tiw.is.catalogue.service.dto.OptionDTO;
import jakarta.websocket.server.PathParam;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collection;

@RestController
@RequestMapping(path = "/option")
public class OptionController {
    private OptionService optionService;

    public OptionController(OptionService optionService) {
        this.optionService = optionService;
    }

    @PostMapping
    public ResponseEntity<OptionDTO> createOption(@RequestBody OptionDTO optionDTO) {
        optionDTO = optionService.createOption(optionDTO);
        return new ResponseEntity<>(optionDTO, HttpStatus.CREATED);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public Collection<OptionDTO> getOptions() {
        return optionService.getAllOptions();
    }

    @GetMapping("/{nom}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public OptionDTO getOption(@PathParam("nom") String nom) {
        try {
            return optionService.getOption(nom);
        } catch (NoSuchOptionException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Option not found", e);
        }
    }

    @DeleteMapping("/{nom}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteOption(@PathParam("nom") String nom) {
        try {
            optionService.deleteOption(nom);
        } catch (NoSuchOptionException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No such option to delete", e);
        }
    }
}
