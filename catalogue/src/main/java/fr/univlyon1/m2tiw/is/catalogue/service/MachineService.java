package fr.univlyon1.m2tiw.is.catalogue.service;

import fr.univlyon1.m2tiw.is.catalogue.model.Machine;
import fr.univlyon1.m2tiw.is.catalogue.repository.MachineRepository;
import fr.univlyon1.m2tiw.is.catalogue.service.dto.MachineDTO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Optional;

@Service
@Transactional
public class MachineService {
    private MachineRepository machineRepository;

    public MachineService(MachineRepository machineRepository) {
        this.machineRepository = machineRepository;
    }

    @Transactional
    public Collection<MachineDTO> getMachines() {
        return machineRepository.findAll().stream().map(MachineDTO::new).toList();
    }

    @Transactional
    public MachineDTO getMachine(long id) throws NoSuchMachineException {
        return new MachineDTO(
                machineRepository
                        .findById(id)
                        .orElseThrow(() -> new NoSuchMachineException(id)));
    }

    @Transactional
    public MachineDTO createMachine(MachineDTO machineDTO) {
        Machine machine = machineDTO.toMachine();
        machine.setId(null); // on s'assure que c'est le support de persistence qui donne l'id car c'est un serial/autoincrement
        machine = machineRepository.save(machine);
        return new MachineDTO(machine);
    }

    @Transactional
    public void deleteMachine(long id) {
        machineRepository.deleteById(id);
    }

    @Transactional
    public MachineDTO updateMachine(MachineDTO machineDTO) throws NoSuchMachineException {
        Optional<Machine> machineOpt = machineRepository.findById(machineDTO.id);
        if (machineOpt.isPresent()) {
            Machine machine = machineOpt.get();
            if (machineDTO.modele != null) {
                machine.setModele(machineDTO.modele);
                // on ne mets volontairement pas à jour les configurations ici
            }
            // machineRepository.save(machine);
            return new MachineDTO(machine);
        } else {
            throw new NoSuchMachineException(machineDTO.id);
        }
    }
}
