package fr.univlyon1.m2tiw.is.commandes.services;

import fr.univlyon1.m2tiw.is.commandes.dao.CommandeDAOImpl;
import fr.univlyon1.m2tiw.is.commandes.dao.NotFoundException;
import fr.univlyon1.m2tiw.is.commandes.dao.VoitureDAOImpl;
import fr.univlyon1.m2tiw.is.commandes.model.Commande;
import fr.univlyon1.m2tiw.is.commandes.model.Option;
import fr.univlyon1.m2tiw.is.commandes.model.Voiture;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;

class VoitureServiceImplTest {

    private static int counter;
    private VoitureServiceImpl voitureService;
    private CommandeCouranteServiceImpl commandeCouranteService;
    private VoitureDAOImpl voitureDAO;
    private CommandeDAOImpl commandeDAO;

    @BeforeEach
    void setUp() throws SQLException {
        commandeCouranteService = new CommandeCouranteServiceImpl();
        voitureDAO = new VoitureDAOImpl();
        voitureDAO.init();
        voitureService = new VoitureServiceImpl();
        commandeDAO = new CommandeDAOImpl();
        commandeDAO.init();
    }

    @Test
    void creerVoiture() throws SQLException, NotFoundException {
        Voiture v = voitureService.creerVoiture("modele" + counter++);
        assertNotNull(v);
        Voiture v2 = voitureService.getVoiture(v.getId());
        assertEquals(v,v2);
        voitureService.supprimerVoiture(v.getId());
    }

    @Test
    void ajouterConfiguration() throws SQLException, NotFoundException {
        Voiture v = voitureService.creerVoiture("modele" + counter++);
        Option option = new Option("nom" + counter++, "valeur" + counter++);
        voitureService.ajouterConfiguration(v.getId(), option);
        Collection<Option> options = voitureService.getOptionsForVoiture(v.getId());
        assertTrue(options.stream().anyMatch(o -> o.equals(option)));
        voitureService.supprimerConfiguration(v.getId(), option);
        voitureService.supprimerVoiture(v.getId());
    }

    @Test
    void supprimerConfiguration() throws SQLException, NotFoundException {
        Voiture v = voitureService.creerVoiture("modele" + counter++);
        Option option = new Option("nom" + counter++, "valeur" + counter++);
        voitureService.ajouterConfiguration(v.getId(), option);
        voitureService.supprimerConfiguration(v.getId(), option);
        Collection<Option> options = voitureService.getOptionsForVoiture(v.getId());
        assertTrue(options.stream().noneMatch(o -> o.equals(option)));
        voitureService.supprimerVoiture(v.getId());
    }

    @Test
    void getOptionsForVoiture() throws SQLException, NotFoundException {
        Voiture v = voitureService.creerVoiture("modele" + counter++);
        Option option = new Option("nom" + counter++, "valeur" + counter++);
        Option option2 = new Option("nom" + counter++, "valeur" + counter++);
        voitureService.ajouterConfiguration(v.getId(), option);
        voitureService.ajouterConfiguration(v.getId(), option2);
        Collection<Option> options = voitureService.getOptionsForVoiture(v.getId());
        assertEquals(2, options.size());
        assertTrue(options.stream().anyMatch(o -> o.equals(option)));
        assertTrue(options.stream().anyMatch(o -> o.equals(option2)));
        voitureService.supprimerConfiguration(v.getId(), option);
        voitureService.supprimerConfiguration(v.getId(), option2);
        voitureService.supprimerVoiture(v.getId());
    }

    @Test
    void getVoiture() throws SQLException, NotFoundException {
        Voiture v = voitureService.creerVoiture("modele" + counter++);
        Voiture v2 = voitureService.getVoiture(v.getId());
        assertEquals(v, v2);
        voitureService.supprimerVoiture(v.getId());
    }

    @Test
    void sauverVoiture() throws SQLException, NotFoundException {
        Commande c = commandeCouranteService.creerCommandeCourante();
        Voiture v = voitureService.creerVoiture("modele" + counter++);
        commandeCouranteService.ajouterVoiture(v.getId());
        c = commandeDAO.saveCommande(c); // sinon c n'a pas d'id
        voitureService.sauverVoiture(v.getId(), c);
        var voitures = voitureDAO.getVoituresByCommande(c.getId());
        assertTrue(voitures.stream().anyMatch(v2 -> v2.equals(v)));
        voitureService.supprimerVoiture(v.getId());
        commandeDAO.deleteCommande(c.getId());
    }

    @Test
    void getVoituresByCommande() throws SQLException, NotFoundException {
        Commande c = commandeCouranteService.creerCommandeCourante();
        Voiture v = voitureService.creerVoiture("modele" + counter++);
        commandeCouranteService.ajouterVoiture(v.getId());
        c = commandeDAO.saveCommande(c); // sinon c n'a pas d'id
        voitureService.sauverVoiture(v.getId(), c);
        var voitures = voitureService.getVoituresByCommande(c.getId());
        assertTrue(voitures.stream().anyMatch(v2 -> v2.equals(v)));
        voitureService.supprimerVoiture(v.getId());
        commandeDAO.deleteCommande(c.getId());
    }

    @Test
    void supprimerVoiture() throws SQLException, NotFoundException {
        Voiture v = voitureService.creerVoiture("modele" + counter++);
        voitureService.supprimerVoiture(v.getId());
        assertThrows(NotFoundException.class, () -> voitureService.getVoiture(v.getId()));
    }
}