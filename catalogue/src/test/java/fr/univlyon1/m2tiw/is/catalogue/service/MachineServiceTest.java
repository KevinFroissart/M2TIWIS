package fr.univlyon1.m2tiw.is.catalogue.service;

import fr.univlyon1.m2tiw.is.catalogue.model.Machine;
import fr.univlyon1.m2tiw.is.catalogue.repository.MachineRepository;
import fr.univlyon1.m2tiw.is.catalogue.service.dto.MachineDTO;
import fr.univlyon1.m2tiw.is.catalogue.utils.MachineDTOMockBuilder;
import fr.univlyon1.m2tiw.is.catalogue.utils.MachineMockBuilder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Collection;
import java.util.Optional;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@RunWith(MockitoJUnitRunner.Silent.class)
public class MachineServiceTest {

    @InjectMocks
    private MachineService machineService;

    @Mock
    private MachineRepository machineRepository;

    @Test
    public void shouldRetournerUneListeDeMachines_whenGetMachines() {
        // Given
        Machine machine1 = new MachineMockBuilder()
                .setId(1L)
                .setModele("Modele 1")
                .build();

        Machine machine2 = new MachineMockBuilder()
                .setId(2L)
                .setModele("Modele 2")
                .build();

        when(machineRepository.findAll()).thenReturn(asList(machine1, machine2));

        // When
        Collection<MachineDTO> resultat = machineService.getMachines();

        // Then
        assertThat(resultat)
                .isNotNull()
                .hasSize(2)
                .extracting(MachineDTO::getId, MachineDTO::getModele)
                .containsOnly(
                        tuple(1L, "Modele 1"),
                        tuple(2L, "Modele 2")
                );
    }

    @Test
    public void shouldRetournerUneMachine_whenGetMachine_withUneMachine() throws NoSuchMachineException {
        // Given
        Machine machine1 = new MachineMockBuilder()
                .setId(1L)
                .setModele("Modele 1")
                .build();

        when(machineRepository.findById(1L)).thenReturn(Optional.ofNullable(machine1));

        // When
        MachineDTO resultat = machineService.getMachine(1L);

        // Then
        assertThat(resultat)
                .isNotNull()
                .extracting(MachineDTO::getId, MachineDTO::getModele)
                .containsOnly(1L, "Modele 1");
    }

    @Test(expected = NullPointerException.class)
    public void shouldThrowNPE_whenGetMachine_withMachineIntrouvable() throws NoSuchMachineException {
        // Given
        when(machineRepository.findById(0L)).thenReturn(null);

        // When
        machineService.getMachine(0L);
    }

    @Test
    public void shouldCreerUneMachine_whenCreateMachine() {
        // Given
        Machine machine = new MachineMockBuilder()
                .setModele("Modele 1")
                .build();

        MachineDTO machineDTO = new MachineDTOMockBuilder()
                .setModele("Modele 1")
                .build();

        when(machineRepository.save(any(Machine.class))).thenReturn(machine);

        // When
        machineService.createMachine(machineDTO);

        // Then
        verify(machineRepository, times(1)).save(any(Machine.class));
    }

    @Test
    public void shouldMettreAJourUneMachine_whenUpdateMachine_withUneMachineExistante() throws NoSuchMachineException {
        // Given
        Machine machine1 = new MachineMockBuilder()
                .setId(1L)
                .setModele("Modele 1")
                .build();

        Machine machine1Update = new MachineMockBuilder()
                .setId(1L)
                .setModele("Modele update")
                .build();

        MachineDTO machineDTOUpdate = new MachineDTOMockBuilder()
                .setId(1L)
                .setModele("Modele update")
                .build();

        when(machineRepository.findById(1L)).thenReturn(Optional.ofNullable(machine1));
        when(machineRepository.save(machine1)).thenReturn(machine1Update);

        // When
        MachineDTO resultat = machineService.updateMachine(machineDTOUpdate);

        // Then
        assertThat(resultat)
                .isNotNull()
                .extracting(MachineDTO::getId, MachineDTO::getModele)
                .containsOnly(1L, "Modele update");
    }

    @Test(expected = NoSuchMachineException.class)
    public void shouldThrowNoSuchMachineException_whenUpdateMachine_withPasDeMachine() throws NoSuchMachineException {
        // Given
        MachineDTO machineDTOUpdate = new MachineDTOMockBuilder()
                .setId(1L)
                .setModele("Modele update")
                .build();

        when(machineRepository.findById(0L)).thenReturn(null);

        // Then
        machineService.updateMachine(machineDTOUpdate);
    }

    @Test
    public void shouldSupprimerUneMachine_whenDeleteMachine_withUneMachine() throws NoSuchMachineException {
        // Given
        Machine machine1 = new MachineMockBuilder()
                .setId(1L)
                .setModele("Modele 1")
                .build();

        when(machineRepository.findById(1L)).thenReturn(Optional.ofNullable(machine1));

        // When
        machineService.deleteMachine(1L);

        // Then
        verify(machineRepository, times(1)).delete(any(Machine.class));
    }

    @Test(expected = NullPointerException.class)
    public void shouldThrowNPE_whenDeleteMachine_withMauvaisId() throws NoSuchMachineException {
        // Given
        when(machineRepository.findById(1L)).thenReturn(null);

        // Then
        machineService.deleteMachine(1L);
    }
}
