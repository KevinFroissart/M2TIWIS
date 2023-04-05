package fr.univlyon1.m2tiw.is.catalogue.service;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Collection;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import fr.univlyon1.m2tiw.is.catalogue.model.Option;
import fr.univlyon1.m2tiw.is.catalogue.repository.OptionRepository;
import fr.univlyon1.m2tiw.is.catalogue.service.dto.OptionDTO;
import fr.univlyon1.m2tiw.is.catalogue.utils.OptionDTOMockBuilder;
import fr.univlyon1.m2tiw.is.catalogue.utils.OptionMockBuilder;

@ExtendWith(MockitoExtension.class)
@RunWith(MockitoJUnitRunner.Silent.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class OptionServiceTest {

    @InjectMocks
    private OptionService optionService;

    @Mock
    private OptionRepository optionRepository;

    @Test
    public void shouldRetournerUneListeDOptions_whenGetOptions() {
        // Given
        Option option1 = new OptionMockBuilder()
                .setNom("nom 1")
                .setDescription("Description 1")
                .build();

        Option option2 = new OptionMockBuilder()
                .setNom("nom 2")
                .setDescription("Description 2")
                .build();

        when(optionRepository.findAll()).thenReturn(asList(option1, option2));

        // When
        Collection<OptionDTO> resultat = optionService.getOptions();

        // Then
        assertThat(resultat)
                .isNotNull()
                .hasSize(2)
                .extracting(OptionDTO::getNom, OptionDTO::getDescription)
                .containsOnly(
                        tuple("nom 1", "Description 1"),
                        tuple("nom 2", "Description 2")
                );
    }

    @Test
    public void shouldRetournerUneOption_whenGetOption_withUneOption() throws NoSuchOptionException {
        // Given
        Option option1 = new OptionMockBuilder()
                .setNom("nom 1")
                .setDescription("Description 1")
                .build();

        when(optionRepository.findById("nom 1")).thenReturn(Optional.ofNullable(option1));

        // When
        OptionDTO resultat = optionService.getOption("nom 1");

        // Then
        assertThat(resultat)
                .isNotNull()
                .extracting(OptionDTO::getNom, OptionDTO::getDescription)
                .containsOnly("nom 1", "Description 1");
    }

    @Test
    public void shouldThrowNPE_whenGetOption_withOptionIntrouvable() throws NoSuchOptionException {
        // Given
        when(optionRepository.findById("nom 0")).thenReturn(null);

        // When
        assertThrows(NullPointerException.class, () -> optionService.getOption("nom 0"));
    }

    @Test
    public void shouldCreerUneOption_whenCreateOption() {
        // Given
        Option option = new OptionMockBuilder()
                .setNom("nom 1")
                .setDescription("Description 1")
                .build();

        OptionDTO optionDTO = new OptionDTOMockBuilder()
                .setNom("nom 1")
                .setDescription("Description 1")
                .build();

        when(optionRepository.save(any(Option.class))).thenReturn(option);

        // When
        optionService.createOption(optionDTO);

        // Then
        verify(optionRepository, times(1)).save(any(Option.class));
    }

    @Test
    public void shouldMettreAJourUneOption_whenUpdateOption_withUneOptionExistante() throws NoSuchOptionException {
        // Given
        Option option1 = new OptionMockBuilder()
                .setNom("nom 1")
                .setDescription("Description 1")
                .build();

        Option option1Update = new OptionMockBuilder()
                .setNom("nom 1")
                .setDescription("Description update")
                .build();

        OptionDTO optionDTO1Update = new OptionDTOMockBuilder()
                .setNom("nom 1")
                .setDescription("Description update")
                .build();

        when(optionRepository.findById("nom 1")).thenReturn(Optional.ofNullable(option1));
        when(optionRepository.save(option1)).thenReturn(option1Update);

        // When
        OptionDTO resultat = optionService.updateOption(optionDTO1Update);

        // Then
        assertThat(resultat)
                .isNotNull()
                .extracting(OptionDTO::getNom, OptionDTO::getDescription)
                .containsOnly("nom 1", "Description update");
    }

    @Test()
    public void shouldThrowNoSuchMachineException_whenUpdateMachine_withPasDeMachine() {
        // Given
        OptionDTO optionDTO1Update = new OptionDTOMockBuilder()
                .setNom("nom 1")
                .setDescription("Description update")
                .build();

        when(optionRepository.findById("nom 0")).thenReturn(null);

        // Then
        assertThrows(NoSuchOptionException.class, () -> optionService.updateOption(optionDTO1Update));
    }

    @Test
    public void shouldSupprimerUneMachine_whendeleteOption_withUneOption() throws NoSuchOptionException {
        // Given
        Option option1 = new OptionMockBuilder()
                .setNom("nom 1")
                .setDescription("Description 1")
                .build();

        when(optionRepository.findById("nom 1")).thenReturn(Optional.ofNullable(option1));

        // When
        optionService.deleteOption("nom 1");

        // Then
        verify(optionRepository, times(1)).delete(any(Option.class));
    }

    @Test
    public void shouldThrowNPE_whendeleteOption_withMauvaisId() {
        // Given
        when(optionRepository.findById("nom 1")).thenReturn(null);

        // Then
        assertThrows(NullPointerException.class, () -> optionService.deleteOption("nom 1"));
    }

}
