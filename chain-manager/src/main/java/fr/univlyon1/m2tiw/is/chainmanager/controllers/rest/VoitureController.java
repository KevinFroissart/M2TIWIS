package fr.univlyon1.m2tiw.is.chainmanager.controllers.rest;

import java.util.Collection;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import fr.univlyon1.m2tiw.is.chainmanager.services.VoitureService;
import fr.univlyon1.m2tiw.is.chainmanager.services.dtos.VoitureDTO;

@RestController
@RequestMapping(path="/voiture")
public class VoitureController {
    private final VoitureService voitureService;

    public VoitureController(VoitureService voitureService) {
        this.voitureService = voitureService;
    }

    @PostMapping
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public VoitureDTO ajouteVoiture(@RequestBody VoitureDTO voitureDTO) throws JsonProcessingException {
        return voitureService.ajouteVoiture(voitureDTO);
    }

    @GetMapping
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public Collection<VoitureDTO> getAllVoitures() {
        return voitureService.getAllVoitures();
    }
}
