package fr.univlyon1.m2tiw.is.catalogue.service;

import fr.univlyon1.m2tiw.is.catalogue.model.Machine;
import fr.univlyon1.m2tiw.is.catalogue.repository.MachineRepository;
import fr.univlyon1.m2tiw.is.catalogue.service.dto.MachineDTO;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MachineServiceTest {

    @Autowired
    private MachineService machineService;

    @Autowired
    private MachineRepository machineRepository;

    private Machine m1;
    private Machine m2;

    @BeforeEach
    public void setup() {
        machineRepository.deleteAll();
        m1 = new Machine();
        m1.setModele("JUnit test - modele 1");
        m2 = new Machine();
        m2.setModele("JUnit test - modele 2");
        machineRepository.save(m1);
        machineRepository.save(m2);
    }

    @AfterEach
    public void tearDown() {
        try {
            machineRepository.deleteById(m1.getId());
        } catch (Exception e) {
            // ignore
        }
        try {
            machineRepository.deleteById(m2.getId());
        } catch (Exception e) {
            // ignore
        }
    }

    @Test
    void getMachines() {
        var machines = machineService.getMachines();
        assertEquals(2, machines.size());
        assertTrue(machines.stream().anyMatch(m -> m.id == m1.getId().longValue()));
        assertTrue(machines.stream().anyMatch(m -> m.id == m2.getId().longValue()));
    }

    @Test
    void getMachine() throws NoSuchMachineException {
        var machine = machineService.getMachine(m1.getId());
        assertNotNull(machine);
        assertEquals(machine.id, m1.getId());
        assertEquals(machine.modele, m1.getModele());
    }

    @Test
    void createMachine() {
        var md3 = new MachineDTO();
        md3.modele = "JUnit test - modele 3";
        md3 = machineService.createMachine(md3);
        assertTrue(machineRepository.existsById(md3.id));
        assertEquals("JUnit test - modele 3", machineRepository.findById(md3.id).get().getModele());
    }

    @Test
    void deleteMachine() {
        machineService.deleteMachine(m1.getId());
        var machines = machineRepository.findAll();
        assertFalse(machines.contains(m1));
    }

    @Test
    void updateMachine() throws NoSuchMachineException {
        var md1 = new MachineDTO(m1);
        String modele = "JUnit test - modele 4";
        md1.modele = modele;
        var md1b = machineService.updateMachine(md1);
        assertEquals(modele, machineRepository.findById(md1.id).get().getModele());
        assertEquals(modele, md1b.modele);
        assertEquals(m1.getId(), md1b.id);
    }
}