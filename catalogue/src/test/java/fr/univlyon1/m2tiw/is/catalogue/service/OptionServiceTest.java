package fr.univlyon1.m2tiw.is.catalogue.service;

import fr.univlyon1.m2tiw.is.catalogue.repository.OptionRepository;
import fr.univlyon1.m2tiw.is.catalogue.service.dto.OptionDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class OptionServiceTest {

    @Autowired
    private OptionService optionService;
    @Autowired
    private OptionRepository optionRepository;

    @Test
    void createOption() {
        String nom = "JUnit test - option 1";
        var od1 = new OptionDTO(nom);
        od1 = optionService.createOption(od1);
        assertNotNull(od1);
        assertEquals(nom, od1.nom);
        optionRepository.deleteById(nom);
    }

    @Test
    void getOption() throws NoSuchOptionException {
        String nom = "JUnit test - option 2";
        var od1 = new OptionDTO(nom);
        optionService.createOption(od1);
        od1 = optionService.getOption(nom);
        assertNotNull(od1);
        assertEquals(nom, od1.nom);
        optionRepository.deleteById(nom);
    }

    @Test
    void deleteOption() throws NoSuchOptionException {
        String nom = "JUnit test - option 3";
        var od1 = new OptionDTO(nom);
        od1 = optionService.createOption(od1);
        optionService.deleteOption(nom);
        assertFalse(optionRepository.existsById(nom));
        assertThrows(NoSuchOptionException.class, () -> {
            optionService.getOption(nom);
        });
    }

    @Test
    void getAllOptions() {
        optionRepository.deleteAll();
        String nom1 = "JUnit test - option 4";
        var od1 = new OptionDTO(nom1);
        od1 = optionService.createOption(od1);
        String nom2 = "JUnit test - option 5";
        var od2 = new OptionDTO(nom2);
        od2 = optionService.createOption(od2);
        assertEquals(2, optionService.getAllOptions().size());
        optionRepository.deleteAll();
    }
}