package fr.univlyon1.m2tiw.is.catalogue.service;

import fr.univlyon1.m2tiw.is.catalogue.model.Machine;
import fr.univlyon1.m2tiw.is.catalogue.repository.MachineRepository;
import fr.univlyon1.m2tiw.is.catalogue.service.dto.MachineDTO;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class MachineService {
    private MachineRepository machineRepository;

    public MachineService(MachineRepository machineRepository) {
        this.machineRepository = machineRepository;
    }

    public Collection<MachineDTO> getMachines() {
        return machineRepository.findAll().stream().map(MachineDTO::new).toList();
    }

    public MachineDTO getMachine(long id) throws NoSuchMachineException {
        return new MachineDTO(
                machineRepository
                        .findById(id)
                        .orElseThrow(() -> new NoSuchMachineException(id)));
    }

    public MachineDTO createMachine(MachineDTO machineDTO) {
        Machine machine = machineDTO.toMachine();
        machine.setId(null); // on s'assure que c'est le support de persistence qui donne l'id car c'est un serial/autoincrement
        machine = machineRepository.save(machine);
        return new MachineDTO(machine);
    }
}
