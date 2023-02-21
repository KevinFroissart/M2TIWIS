package fr.univlyon1.m2tiw.is.chainmanager.controllers.rest;

import fr.univlyon1.m2tiw.is.chainmanager.models.StatutInconnuException;
import fr.univlyon1.m2tiw.is.chainmanager.services.VoitureService;
import fr.univlyon1.m2tiw.is.chainmanager.services.dtos.VoitureDTO;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping(path="/voiture")
public class VoitureController {
    private VoitureService voitureService;

    public VoitureController(VoitureService voitureService) {
        this.voitureService = voitureService;
    }

    @PostMapping
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public VoitureDTO ajouteVoiture(VoitureDTO voitureDTO) throws StatutInconnuException {
        return voitureService.ajouteVoiture(voitureDTO);
    }

    @GetMapping
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public Collection<VoitureDTO> getAllVoitures() {
        return voitureService.getAllVoitures();
    }
}
