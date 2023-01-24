package fr.univlyon1.m2tiw.is.commandes.services;

import fr.univlyon1.m2tiw.is.commandes.dao.NotFoundException;
import fr.univlyon1.m2tiw.is.commandes.model.Commande;
import fr.univlyon1.m2tiw.is.commandes.model.Voiture;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class CommandeCouranteServiceImplTest {
    private static Logger log = LoggerFactory.getLogger(CommandeCouranteServiceImplTest.class);
    private static int counter = 0;
    private VoitureService voitureService;
    private CommandeCouranteServiceImpl commandeCouranteService;

    @BeforeEach
    public void before() throws SQLException {
        voitureService = new VoitureServiceImpl();
        commandeCouranteService = new CommandeCouranteServiceImpl();
    }

    private Voiture createVoiture() throws SQLException {
        Voiture v = voitureService.creerVoiture("modele" + counter++);
        return v;
    }

    @Test
    void creerCommandeCourante() {
        Commande c = commandeCouranteService.creerCommandeCourante();
        assertNotNull(c);
        assertFalse(c.isFerme());
    }

    @Test
    void ajouterVoiture() throws SQLException, NotFoundException {
        Commande c = commandeCouranteService.creerCommandeCourante();
        Voiture v = createVoiture();
        commandeCouranteService.ajouterVoiture(v.getId());
        assertEquals(1, commandeCouranteService.getCommandeCourante().getVoitures().size());
    }

    @Test
    void supprimerVoiture() throws SQLException, NotFoundException {
        Commande c = commandeCouranteService.creerCommandeCourante();
        Voiture v = createVoiture();
        commandeCouranteService.ajouterVoiture(v.getId());
        commandeCouranteService.supprimerVoiture(v.getId());
        assertEquals(0, commandeCouranteService.getCommandeCourante().getVoitures().size());
        assertThrows(NotFoundException.class, () -> voitureService.getVoiture(v.getId()));
    }

    @Test
    void getAllVoitures() throws SQLException, NotFoundException {
        Commande c = commandeCouranteService.creerCommandeCourante();
        Voiture v = createVoiture();
        commandeCouranteService.ajouterVoiture(v.getId());
        var voitures = commandeCouranteService.getAllVoitures();
        assertTrue(1 >= voitures.size());
        log.debug("Voitures: {}",voitures);
        log.debug("v: {}",v);
        assertTrue(voitures.stream().anyMatch(v2 -> (v2.getId().equals(v.getId()))));
        commandeCouranteService.supprimerVoiture(v.getId());
    }

    @Test
    void getCommandeCourante() {
        Commande c = commandeCouranteService.creerCommandeCourante();
        assertEquals(c, commandeCouranteService.getCommandeCourante());
    }

    @Test
    void validerCommandeCourante() throws EmptyCommandeException, SQLException, NotFoundException {
        Commande c = commandeCouranteService.creerCommandeCourante();
        Voiture v = createVoiture();
        commandeCouranteService.ajouterVoiture(v.getId());
        commandeCouranteService.validerCommandeCourante();
        Commande c2 = commandeCouranteService.getCommandeCourante();
        assertNotEquals(c, c2);
    }
}