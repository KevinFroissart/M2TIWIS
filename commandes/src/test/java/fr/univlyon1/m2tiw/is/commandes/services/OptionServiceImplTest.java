package fr.univlyon1.m2tiw.is.commandes.services;

import static fr.univlyon1.m2tiw.is.commandes.util.Strings.OPTIONCONTROLLER;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Collection;

import org.junit.jupiter.api.Test;

import fr.univlyon1.m2tiw.is.commandes.dao.CommandeDAOImpl;
import fr.univlyon1.m2tiw.is.commandes.dao.DBAccess;
import fr.univlyon1.m2tiw.is.commandes.dao.NotFoundException;
import fr.univlyon1.m2tiw.is.commandes.dao.OptionDAOImpl;
import fr.univlyon1.m2tiw.is.commandes.dao.VoitureDAOImpl;
import fr.univlyon1.m2tiw.is.commandes.model.Commande;
import fr.univlyon1.m2tiw.is.commandes.model.Option;
import fr.univlyon1.m2tiw.is.commandes.model.Voiture;
import fr.univlyon1.m2tiw.is.commandes.serveur.Serveur;
import fr.univlyon1.m2tiw.is.commandes.serveur.ServeurImpl;

class OptionServiceImplTest {

	@Test
	void shouldGetAllOptions_whenGetAllOptions() throws SQLException, NotFoundException, IOException, ClassNotFoundException, EmptyCommandeException {
		// Given
		Serveur serveur = new ServeurImpl();
		DBAccess dbAccess = serveur.getConnection();
		var optionDAO = new OptionDAOImpl(dbAccess);
		var voitureDAO = new VoitureDAOImpl(dbAccess);
		var commandeDAO = new CommandeDAOImpl(dbAccess);
		optionDAO.init();
		commandeDAO.init();
		voitureDAO.init();

		Commande c = commandeDAO.saveCommande(new Commande(false));
		Voiture v = voitureDAO.saveVoiture(new Voiture("modele"), c.getId());
		Option o = new Option("opt", "val");
		optionDAO.setOptionVoiture(v.getId(), o);

		// When
		var options = (Collection<Option>) serveur.processRequest(OPTIONCONTROLLER, "getAllOptions", null);

		// Then
		assertTrue(1 <= options.size());
		optionDAO.deleteOptionVoiture(v.getId(), o.getNom());
		voitureDAO.deleteVoiture(v);
		commandeDAO.deleteCommande(c.getId());
	}
}
