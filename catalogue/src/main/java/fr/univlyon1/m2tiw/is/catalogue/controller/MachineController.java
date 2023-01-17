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
    private MachineService machineService;

    public MachineController(MachineService machineService) {
        this.machineService = machineService;
    }

    @GetMapping()
    public Collection<MachineDTO> getMachines() {
        return machineService.getMachines();
    }


    @GetMapping("/{id}")
    public ResponseEntity<MachineDTO> getMachine(@PathVariable("id") long id) {
        try {
            return new ResponseEntity<>(machineService.getMachine(id), HttpStatus.OK);
        } catch (NoSuchMachineException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping()
    public ResponseEntity<MachineDTO> createMachine(@RequestBody  MachineDTO machineDTO) {
        log.info("Creating machine with {}", machineDTO);
        return new ResponseEntity<>(machineService.createMachine(machineDTO), HttpStatus.CREATED);
    }
}
