package fr.univlyon1.m2tiw.is.catalogue.service;

import fr.univlyon1.m2tiw.is.catalogue.model.Machine;
import fr.univlyon1.m2tiw.is.catalogue.repository.MachineRepository;
import fr.univlyon1.m2tiw.is.catalogue.service.dto.MachineDTO;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class MachineService {
    private final MachineRepository machineRepository;

    public MachineService(MachineRepository machineRepository) {
        this.machineRepository = machineRepository;
    }

    /**
     * Retourne une collection de {@link MachineDTO}.
     * @return les machines.
     */
    public Collection<MachineDTO> getMachines() {
        return machineRepository.findAll().stream().map(MachineDTO::new).toList();
    }

    /**
     * Retourne une {@link MachineDTO} pour un id donné.
     * @param id l'id de la machine.
     * @return la machine.
     */
    public MachineDTO getMachine(long id) throws NoSuchMachineException {
        return new MachineDTO(
                machineRepository
                        .findById(id)
                        .orElseThrow(() -> new NoSuchMachineException(id)));
    }

    /**
     * Crée une {@link MachineDTO}.
     * @param machineDTO la machine à créer.
     * @return la machine créée.
     */
    public MachineDTO createMachine(MachineDTO machineDTO) {
        Machine machine = machineDTO.toMachine();
        machine.setId(null); // on s'assure que c'est le support de persistence qui donne l'id car c'est un serial/autoincrement
        machine = machineRepository.save(machine);
        return new MachineDTO(machine);
    }

    /**
     * Met à jour une {@link MachineDTO}.
     * @param machineDTO la machine à mettre à jour.
     * @return la machine mise à jour.
     */
    public MachineDTO updateMachine(MachineDTO machineDTO) throws NoSuchMachineException {
        Machine machine = machineRepository
                .findById(machineDTO.getId())
                .orElseThrow(() -> new NoSuchMachineException(machineDTO.getId()));
        machine.setModele(machineDTO.getModele());
        machine = machineRepository.save(machine);
        return new MachineDTO(machine);
    }

    /**
     * Supprime une {@link MachineDTO} pour un id donné.
     * @param id l'id de la machine à supprimer.
     */
    public void deleteMachine(long id)  throws NoSuchMachineException {
        Machine machine = machineRepository
                .findById(id)
                .orElseThrow(() -> new NoSuchMachineException(id));
        machineRepository.delete(machine);
    }
}
