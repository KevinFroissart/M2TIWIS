package fr.univlyon1.m2tiw.is.commandes.services;

import fr.univlyon1.m2tiw.is.commandes.dao.*;
import fr.univlyon1.m2tiw.is.commandes.model.Commande;
import fr.univlyon1.m2tiw.is.commandes.model.Option;
import fr.univlyon1.m2tiw.is.commandes.model.Voiture;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class GestionCommandeServiceImplTest {
    private GestionCommandeServiceImpl gestionCommandeService;
    private CommandeCouranteServiceImpl commandeCouranteService;
    private VoitureServiceImpl voitureService;

    CommandeDAOImpl commandeDAO;
    VoitureDAOImpl voitureDAO;
    OptionDAOImpl optionDAO;

    @BeforeEach
    void setUp() throws SQLException {
        gestionCommandeService = new GestionCommandeServiceImpl();
        commandeCouranteService = new CommandeCouranteServiceImpl();
        voitureService = new VoitureServiceImpl();
        commandeDAO = new CommandeDAOImpl();
        commandeDAO.init();
        voitureDAO = new VoitureDAOImpl();
        voitureDAO.init();
        optionDAO = new OptionDAOImpl();
        optionDAO.init();
    }

    @Test
    void getAllOptions() throws SQLException, NotFoundException {
        Commande c = commandeDAO.saveCommande(new Commande(false));
        Voiture v = voitureDAO.saveVoiture(new Voiture("modele"), c.getId());
        Option o = new Option("opt", "val");
        optionDAO.setOptionVoiture(v.getId(), o);
        var options = gestionCommandeService.getAllOptions();
        assertTrue(1 <= options.size());
        optionDAO.deleteOptionVoiture(v.getId(), o.getNom());
        voitureDAO.deleteVoiture(v);
        commandeDAO.deleteCommande(c.getId());
    }

    @Test
    void getCommande() throws SQLException, NotFoundException, EmptyCommandeException {
        Commande c = commandeCouranteService.creerCommandeCourante();
        Voiture v = voitureService.creerVoiture("modele");
        commandeCouranteService.ajouterVoiture(v.getId());
        long id = commandeCouranteService.validerCommandeCourante();
        Commande c2 = gestionCommandeService.getCommande(id);
        assertNotNull(c2);
    }

    @Test
    void getCommandeCourante() {
        assertNotNull(gestionCommandeService.getCommandeCourante());
    }
}