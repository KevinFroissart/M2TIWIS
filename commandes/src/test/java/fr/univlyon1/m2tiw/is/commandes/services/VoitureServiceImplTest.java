package fr.univlyon1.m2tiw.is.commandes.services;

import fr.univlyon1.m2tiw.is.commandes.dao.CommandeDAOImpl;
import fr.univlyon1.m2tiw.is.commandes.dao.DBAccess;
import fr.univlyon1.m2tiw.is.commandes.dao.NotFoundException;
import fr.univlyon1.m2tiw.is.commandes.dao.OptionDAO;
import fr.univlyon1.m2tiw.is.commandes.dao.OptionDAOImpl;
import fr.univlyon1.m2tiw.is.commandes.dao.VoitureDAOImpl;
import fr.univlyon1.m2tiw.is.commandes.model.Commande;
import fr.univlyon1.m2tiw.is.commandes.model.Option;
import fr.univlyon1.m2tiw.is.commandes.model.Voiture;
import fr.univlyon1.m2tiw.is.commandes.resources.CommandeCouranteResource;
import fr.univlyon1.m2tiw.is.commandes.resources.VoitureResource;
import fr.univlyon1.m2tiw.is.commandes.serveur.Serveur;
import fr.univlyon1.m2tiw.is.commandes.serveur.ServeurImpl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;

class VoitureServiceImplTest {

    private static int counter;
    private VoitureServiceImpl voitureService;
    private CommandeCouranteServiceImpl commandeCouranteService;
    private VoitureResource voitureResource;
    private CommandeCouranteResource commandeCouranteResource;
    private VoitureDAOImpl voitureDAO;
    private CommandeDAOImpl commandeDAO;

    @BeforeEach
    void setUp() throws SQLException, IOException, ClassNotFoundException {
        Serveur serveur = new ServeurImpl();
        DBAccess dbAccess = serveur.getConnection();
        OptionDAO optionDAO = new OptionDAOImpl(dbAccess);
        voitureDAO = new VoitureDAOImpl(dbAccess);
        commandeDAO = new CommandeDAOImpl(dbAccess);

        optionDAO.init();
        commandeDAO.init();
        voitureDAO.init();

        voitureService = new VoitureServiceImpl(voitureDAO, optionDAO);
        voitureResource = new VoitureResource(voitureDAO, optionDAO);
        commandeCouranteService = new CommandeCouranteServiceImpl(commandeDAO, voitureResource);
        commandeCouranteResource = new CommandeCouranteResource(commandeCouranteService, voitureResource);
    }

    @Test
    void creerVoiture() throws SQLException, NotFoundException {
        Voiture v = voitureResource.creerVoiture("modele" + counter++);
        assertNotNull(v);
        Voiture v2 = voitureResource.getVoiture(v.getId());
        assertEquals(v,v2);
        voitureResource.supprimerVoiture(v.getId());
    }

    @Test
    void ajouterConfiguration() throws SQLException, NotFoundException {
        Voiture v = voitureResource.creerVoiture("modele" + counter++);
        Option option = new Option("nom" + counter++, "valeur" + counter++);
        voitureService.ajouterConfiguration(v.getId(), option);
        Collection<Option> options = voitureService.getOptionsForVoiture(v.getId());
        assertTrue(options.stream().anyMatch(o -> o.equals(option)));
        voitureService.supprimerConfiguration(v.getId(), option);
        voitureResource.supprimerVoiture(v.getId());
    }

    @Test
    void supprimerConfiguration() throws SQLException, NotFoundException {
        Voiture v = voitureResource.creerVoiture("modele" + counter++);
        Option option = new Option("nom" + counter++, "valeur" + counter++);
        voitureService.ajouterConfiguration(v.getId(), option);
        voitureService.supprimerConfiguration(v.getId(), option);
        Collection<Option> options = voitureService.getOptionsForVoiture(v.getId());
        assertTrue(options.stream().noneMatch(o -> o.equals(option)));
        voitureResource.supprimerVoiture(v.getId());
    }

    @Test
    void getOptionsForVoiture() throws SQLException, NotFoundException {
        Voiture v = voitureResource.creerVoiture("modele" + counter++);
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
        voitureResource.supprimerVoiture(v.getId());
    }

    @Test
    void getVoiture() throws SQLException, NotFoundException {
        Voiture v = voitureResource.creerVoiture("modele" + counter++);
        Voiture v2 = voitureResource.getVoiture(v.getId());
        assertEquals(v, v2);
        voitureResource.supprimerVoiture(v.getId());
    }

    @Test
    void sauverVoiture() throws SQLException, NotFoundException {
        Commande c = commandeCouranteService.creerCommandeCourante();
        Voiture v = voitureResource.creerVoiture("modele" + counter++);
        commandeCouranteResource.ajouterVoiture(v.getId());
        c = commandeDAO.saveCommande(c); // sinon c n'a pas d'id
        voitureResource.sauverVoiture(v.getId(), c);
        var voitures = voitureDAO.getVoituresByCommande(c.getId());
        assertTrue(voitures.stream().anyMatch(v2 -> v2.equals(v)));
        voitureResource.supprimerVoiture(v.getId());
        commandeDAO.deleteCommande(c.getId());
    }

    @Test
    void getVoituresByCommande() throws SQLException, NotFoundException {
        Commande c = commandeCouranteService.creerCommandeCourante();
        Voiture v = voitureResource.creerVoiture("modele" + counter++);
        commandeCouranteResource.ajouterVoiture(v.getId());
        c = commandeDAO.saveCommande(c); // sinon c n'a pas d'id
        voitureResource.sauverVoiture(v.getId(), c);
        var voitures = voitureService.getVoituresByCommande(c.getId());
        assertTrue(voitures.stream().anyMatch(v2 -> v2.equals(v)));
        voitureResource.supprimerVoiture(v.getId());
        commandeDAO.deleteCommande(c.getId());
    }

    @Test
    void supprimerVoiture() throws SQLException, NotFoundException {
        Voiture v = voitureResource.creerVoiture("modele" + counter++);
        voitureResource.supprimerVoiture(v.getId());
        assertThrows(NotFoundException.class, () -> voitureResource.getVoiture(v.getId()));
    }
}
