package fr.univlyon1.m2tiw.is.catalogue.controller;

import fr.univlyon1.m2tiw.is.catalogue.service.MachineService;
import fr.univlyon1.m2tiw.is.catalogue.service.NoSuchMachineException;
import fr.univlyon1.m2tiw.is.catalogue.service.dto.MachineDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@Slf4j
@RestController
@RequestMapping(path = "/machine")
public class MachineController {
    private final MachineService machineService;

    public MachineController(MachineService machineService) {
        this.machineService = machineService;
    }

    /**
     * Retourne une collection de {@link MachineDTO}.
     * @return les machines.
     */
    @GetMapping()
    public Collection<MachineDTO> getMachines() {
        return machineService.getMachines();
    }


    /**
     * Retourne une {@link MachineDTO} pour un id donné.
     * @param id l'id de la machine.
     * @return la machine.
     */
    @GetMapping("/{id}")
    public ResponseEntity<MachineDTO> getMachine(@PathVariable("id") long id) {
        try {
            return new ResponseEntity<>(machineService.getMachine(id), HttpStatus.OK);
        } catch (NoSuchMachineException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Crée une {@link MachineDTO}.
     * @param machineDTO la machine à créer.
     * @return la machine créée.
     */
    @PostMapping()
    public ResponseEntity<MachineDTO> createMachine(@RequestBody  MachineDTO machineDTO) {
        log.info("Creating machine with {}", machineDTO);
        return new ResponseEntity<>(machineService.createMachine(machineDTO), HttpStatus.CREATED);
    }

    /**
     * Met à jour une {@link MachineDTO}.
     * @param machineDTO la machine à mettre à jour.
     * @return la machine mise à jour.
     */
    @PutMapping
    public ResponseEntity<MachineDTO> updateMachine(@RequestBody MachineDTO machineDTO) {
        try {
            log.info("Updating machine with {}", machineDTO);
            return new ResponseEntity<>(machineService.updateMachine(machineDTO), HttpStatus.NO_CONTENT);
        } catch (NoSuchMachineException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }

    /**
     * Supprime une {@link MachineDTO} pour un id donné.
     * @param id l'id de la machine à supprimer.
     * @return 204 si la machine est supprimée ou 404 si aucune machine n'est trouvée pour l'id donné.
     */
    @DeleteMapping
    public ResponseEntity<MachineDTO> deleteMachine(@PathVariable("id") long id) {
        try {
            log.info("Deleting machine with id {}", id);
            machineService.deleteMachine(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (NoSuchMachineException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
