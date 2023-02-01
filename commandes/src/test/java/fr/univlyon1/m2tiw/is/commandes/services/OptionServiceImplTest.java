package fr.univlyon1.m2tiw.is.commandes.services;

import fr.univlyon1.m2tiw.is.commandes.dao.CommandeDAOImpl;
import fr.univlyon1.m2tiw.is.commandes.dao.DBAccess;
import fr.univlyon1.m2tiw.is.commandes.dao.NotFoundException;
import fr.univlyon1.m2tiw.is.commandes.dao.OptionDAOImpl;
import fr.univlyon1.m2tiw.is.commandes.dao.VoitureDAOImpl;
import fr.univlyon1.m2tiw.is.commandes.model.Commande;
import fr.univlyon1.m2tiw.is.commandes.model.Option;
import fr.univlyon1.m2tiw.is.commandes.model.Voiture;
import fr.univlyon1.m2tiw.is.commandes.resources.OptionResource;
import fr.univlyon1.m2tiw.is.commandes.serveur.Serveur;
import fr.univlyon1.m2tiw.is.commandes.serveur.ServeurImpl;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class OptionServiceImplTest {


    @Test
    void getAllOptions() throws SQLException, NotFoundException, IOException, ClassNotFoundException {
        Serveur serveur = new ServeurImpl();
        DBAccess dbAccess = serveur.getConnection();
        var optionDAO = new OptionDAOImpl(dbAccess);
        var voitureDAO = new VoitureDAOImpl(dbAccess);
        var commandeDAO = new CommandeDAOImpl(dbAccess);
        optionDAO.init();
        commandeDAO.init();
        voitureDAO.init();
        var optionService = new OptionResource(optionDAO);

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
