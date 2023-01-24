package fr.univlyon1.m2tiw.is.commandes.services;

import fr.univlyon1.m2tiw.is.commandes.dao.CommandeDAOImpl;
import fr.univlyon1.m2tiw.is.commandes.dao.NotFoundException;
import fr.univlyon1.m2tiw.is.commandes.dao.OptionDAOImpl;
import fr.univlyon1.m2tiw.is.commandes.dao.VoitureDAOImpl;
import fr.univlyon1.m2tiw.is.commandes.model.Commande;
import fr.univlyon1.m2tiw.is.commandes.model.Option;
import fr.univlyon1.m2tiw.is.commandes.model.Voiture;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class OptionServiceImplTest {


    @Test
    void getAllOptions() throws SQLException, NotFoundException {
        var optionService = new OptionServiceImpl();
        var commandeDAO = new CommandeDAOImpl();
        commandeDAO.init();
        var voitureDAO = new VoitureDAOImpl();
        voitureDAO.init();
        var optionDAO = new OptionDAOImpl();
        optionDAO.init();
        Commande c = commandeDAO.saveCommande(new Commande(false));
        Voiture v = voitureDAO.saveVoiture(new Voiture("modele"), c.getId());
        Option o = new Option("opt", "val");
        optionDAO.setOptionVoiture(v.getId(), o);
        var options = optionService.getAllOptions();
        assertTrue(1 <= options.size());
        optionDAO.deleteOptionVoiture(v.getId(), o.getNom());
        voitureDAO.deleteVoiture(v);
        commandeDAO.deleteCommande(c.getId());
    }
}