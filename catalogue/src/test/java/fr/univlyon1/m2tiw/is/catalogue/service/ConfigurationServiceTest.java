package fr.univlyon1.m2tiw.is.catalogue.service;

import fr.univlyon1.m2tiw.is.catalogue.model.Configuration;
import fr.univlyon1.m2tiw.is.catalogue.model.Machine;
import fr.univlyon1.m2tiw.is.catalogue.model.MachineOptionKey;
import fr.univlyon1.m2tiw.is.catalogue.model.Option;
import fr.univlyon1.m2tiw.is.catalogue.repository.ConfigurationRepository;
import fr.univlyon1.m2tiw.is.catalogue.service.dto.ConfigurationDTO;
import fr.univlyon1.m2tiw.is.catalogue.utils.ConfigurationDTOMockBuilder;
import fr.univlyon1.m2tiw.is.catalogue.utils.ConfigurationMockBuilder;
import fr.univlyon1.m2tiw.is.catalogue.utils.MachineMockBuilder;
import fr.univlyon1.m2tiw.is.catalogue.utils.OptionMockBuilder;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.util.Collection;
import java.util.Optional;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@RunWith(MockitoJUnitRunner.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class ConfigurationServiceTest {

    @InjectMocks
    ConfigurationService configurationService;

    @Mock
    private ConfigurationRepository configurationRepository;

    @Test
    public void shouldRetournerUneListeDeConfigurations_whenGetConfigurations() {
        // Given
        Configuration configuration1 = new ConfigurationMockBuilder()
                .setMachine(new MachineMockBuilder().build())
                .setOption(new OptionMockBuilder().build())
                .setCfg("Configuration 1")
                .build();

        Configuration configuration2 = new ConfigurationMockBuilder()
                .setMachine(new MachineMockBuilder().build())
                .setOption(new OptionMockBuilder().build())
                .setCfg("Configuration 2")
                .build();

        when(configurationRepository.findAll()).thenReturn(asList(configuration1, configuration2));

        // When
        Collection<ConfigurationDTO> resultat = configurationService.getConfigurations();

        // Then
        assertThat(resultat)
                .isNotNull()
                .hasSize(2)
                .extracting(ConfigurationDTO::getCfg)
                .containsOnly("Configuration 1", "Configuration 2");
    }

    @Test
    public void shouldRetournerUneConfiguration_whenGetConfiguration_withUneConfiguration() throws NoSuchConfigurationException {
        // Given
        Machine machine1 = new MachineMockBuilder()
                .setId(1L)
                .setModele("Modele 1")
                .build();

        Option option1 = new OptionMockBuilder()
                .setNom("nom 1")
                .setDescription("Description 1")
                .build();

        MachineOptionKey machineOptionKey = new MachineOptionKey(machine1, option1);

        Configuration configuration1 = new ConfigurationMockBuilder()
                .setMachine(machine1)
                .setOption(option1)
                .setCfg("Configuration 1")
                .build();

        when(configurationRepository.findById(machineOptionKey)).thenReturn(Optional.ofNullable(configuration1));

        // When
        ConfigurationDTO resultat = configurationService.getConfiguration(machineOptionKey);

        // Then
        assertThat(resultat)
                .isNotNull()
                .extracting(ConfigurationDTO::getCfg)
                .isEqualTo("Configuration 1");
    }

    @Test
    public void shouldThrowNPE_whenGetConfiguration_withConfigurationIntrouvable() throws NoSuchConfigurationException {
        // Given
        Machine machine1 = new MachineMockBuilder()
                .setId(1L)
                .setModele("Modele 1")
                .build();

        Option option1 = new OptionMockBuilder()
                .setNom("nom 1")
                .setDescription("Description 1")
                .build();

        MachineOptionKey machineOptionKey = new MachineOptionKey(machine1, option1);

        when(configurationRepository.findById(any(MachineOptionKey.class))).thenReturn(null);

        // When
        assertThrows(NullPointerException.class, () -> configurationService.getConfiguration(machineOptionKey));
    }

    @Test
    public void shouldCreerUneConfiguration_whenCreateConfiguration() {
        // Given
        Machine machine1 = new MachineMockBuilder()
                .setId(1L)
                .setModele("Modele 1")
                .build();

        Option option1 = new OptionMockBuilder()
                .setNom("nom 1")
                .setDescription("Description 1")
                .build();

        Configuration configuration = new ConfigurationMockBuilder()
                .setMachine(machine1)
                .setOption(option1)
                .setCfg("Configuration 1")
                .build();

        ConfigurationDTO configurationDTO = new ConfigurationDTOMockBuilder()
                .setMachine(machine1)
                .setOption(option1)
                .setCfg("Configuration 1")
                .build();

        when(configurationRepository.save(any(Configuration.class))).thenReturn(configuration);

        // When
        configurationService.createConfiguration(configurationDTO);

        // Then
        verify(configurationRepository, times(1)).save(any(Configuration.class));
    }

    @Test
    public void shouldMettreAJourUneConfiguration_whenUpdateConfiguration_withUneConfigurationExistante() throws NoSuchConfigurationException {
        // Given
        Machine machine1 = new MachineMockBuilder()
                .setId(1L)
                .setModele("Modele 1")
                .build();

        Option option1 = new OptionMockBuilder()
                .setNom("nom 1")
                .setDescription("Description 1")
                .build();

        MachineOptionKey machineOptionKey = new MachineOptionKey(machine1, option1);

        Configuration configuration1 = new ConfigurationMockBuilder()
                .setMachine(machine1)
                .setOption(option1)
                .setCfg("Configuration 1")
                .build();

        Configuration configuration1Update = new ConfigurationMockBuilder()
                .setMachine(machine1)
                .setOption(option1)
                .setCfg("Configuration update")
                .build();

        ConfigurationDTO configurationDTOUpdate = new ConfigurationDTOMockBuilder()
                .setMachine(machine1)
                .setOption(option1)
                .setCfg("Configuration update")
                .build();

        when(configurationRepository.findById(machineOptionKey)).thenReturn(Optional.ofNullable(configuration1));
        when(configurationRepository.save(configuration1)).thenReturn(configuration1Update);

        // When
        ConfigurationDTO resultat = configurationService.updateConfiguration(configurationDTOUpdate);

        // Then
        assertThat(resultat)
                .isNotNull()
                .extracting(ConfigurationDTO::getCfg)
                .isEqualTo("Configuration update");
    }

    @Test
    public void shouldThrowNPE_whenUpdateConfiguration_withPasDeConfiguration() {
        // Given
        Machine machine1 = new MachineMockBuilder()
                .setId(1L)
                .setModele("Modele 1")
                .build();

        Option option1 = new OptionMockBuilder()
                .setNom("nom 1")
                .setDescription("Description 1")
                .build();

        ConfigurationDTO configurationDTOUpdate = new ConfigurationDTOMockBuilder()
                .setMachine(machine1)
                .setOption(option1)
                .setCfg("Configuration update")
                .build();

        when(configurationRepository.findById(any(MachineOptionKey.class))).thenReturn(null);

        // Then
        assertThrows(NullPointerException.class, () -> configurationService.updateConfiguration(configurationDTOUpdate));
    }

    @Test
    public void shouldSupprimerUneConfiguration_whenDeleteConfiguration_withUneConfiguration() throws NoSuchConfigurationException {
        // Given
        Machine machine1 = new MachineMockBuilder()
                .setId(1L)
                .setModele("Modele 1")
                .build();

        Option option1 = new OptionMockBuilder()
                .setNom("nom 1")
                .setDescription("Description 1")
                .build();

        MachineOptionKey machineOptionKey = new MachineOptionKey(machine1, option1);

        Configuration configuration1 = new ConfigurationMockBuilder()
                .setMachine(machine1)
                .setOption(option1)
                .setCfg("Configuration 1")
                .build();

        when(configurationRepository.findById(machineOptionKey)).thenReturn(Optional.ofNullable(configuration1));

        // When
        configurationService.deleteConfiguration(machineOptionKey);

        // Then
        verify(configurationRepository, times(1)).delete(any(Configuration.class));
    }

    @Test
    public void shouldThrowNPE_whenDeleteConfiguration_withMauvaisId() {
        // Given
        Machine machine1 = new MachineMockBuilder()
                .setId(1L)
                .setModele("Modele 1")
                .build();

        Option option1 = new OptionMockBuilder()
                .setNom("nom 1")
                .setDescription("Description 1")
                .build();

        MachineOptionKey machineOptionKey = new MachineOptionKey(machine1, option1);

        when(configurationRepository.findById(any(MachineOptionKey.class))).thenReturn(null);

        // Then
        assertThrows(NullPointerException.class, () -> configurationService.deleteConfiguration(machineOptionKey));
    }

}
