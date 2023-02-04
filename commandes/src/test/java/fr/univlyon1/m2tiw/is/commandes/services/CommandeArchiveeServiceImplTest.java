package fr.univlyon1.m2tiw.is.commandes.services;

import static fr.univlyon1.m2tiw.is.commandes.util.Strings.COMMANDECONTROLLER;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import fr.univlyon1.m2tiw.is.commandes.dao.CommandeDAOImpl;
import fr.univlyon1.m2tiw.is.commandes.dao.DBAccess;
import fr.univlyon1.m2tiw.is.commandes.dao.NotFoundException;
import fr.univlyon1.m2tiw.is.commandes.dao.OptionDAOImpl;
import fr.univlyon1.m2tiw.is.commandes.dao.VoitureDAOImpl;
import fr.univlyon1.m2tiw.is.commandes.model.Commande;
import fr.univlyon1.m2tiw.is.commandes.model.Option;
import fr.univlyon1.m2tiw.is.commandes.model.Voiture;
import fr.univlyon1.m2tiw.is.commandes.resources.CommandeCouranteResource;
import fr.univlyon1.m2tiw.is.commandes.resources.VoitureResource;
import fr.univlyon1.m2tiw.is.commandes.serveur.Serveur;
import fr.univlyon1.m2tiw.is.commandes.serveur.ServeurImpl;

class CommandeArchiveeServiceImplTest {

	private CommandeCouranteServiceImpl commandeCouranteService;
	private CommandeCouranteResource commandeCouranteResource;
	private VoitureResource voitureResource;
	private CommandeDAOImpl commandeDAO;
	private VoitureDAOImpl voitureDAO;
	private OptionDAOImpl optionDAO;
	private Serveur serveur;

	@BeforeEach
	void setUp() throws SQLException, IOException, ClassNotFoundException {
		serveur = new ServeurImpl();
		DBAccess dbAccess = serveur.getConnection();
		optionDAO = new OptionDAOImpl(dbAccess);
		voitureDAO = new VoitureDAOImpl(dbAccess);
		commandeDAO = new CommandeDAOImpl(dbAccess);
		optionDAO.init();
		voitureDAO.init();
		commandeDAO.init();
		voitureResource = new VoitureResource(voitureDAO, optionDAO);
		commandeCouranteResource = new CommandeCouranteResource(commandeDAO, voitureResource);
		commandeCouranteService = new CommandeCouranteServiceImpl(commandeCouranteResource, voitureResource);
	}

	@Test
	void shouldGetAllOptions_whenGetAllOptions() throws SQLException, NotFoundException, EmptyCommandeException, InvalidConfigurationException {
		// Given
		Commande c = commandeDAO.saveCommande(new Commande(false));
		Voiture v = voitureDAO.saveVoiture(new Voiture("modele"), c.getId());
		Option o = new Option("opt", "val");
		optionDAO.setOptionVoiture(v.getId(), o);

		// When
		var options = (Collection<Commande>) serveur.processRequest(COMMANDECONTROLLER, "getAllOptions", null);

		// Then
		assertTrue(1 <= options.size());
		optionDAO.deleteOptionVoiture(v.getId(), o.getNom());
		voitureDAO.deleteVoiture(v);
		commandeDAO.deleteCommande(c.getId());
	}

	@Test
	void shouldGetCommande_whenGetCommande() throws SQLException, NotFoundException, EmptyCommandeException, InvalidConfigurationException {
		// Given
		Voiture v = voitureResource.creerVoiture("modele");
		commandeCouranteService.ajouterVoiture(v.getId());
		long id = commandeCouranteResource.validerCommandeCourante();

		// When
		var c2 = (Commande) serveur.processRequest(COMMANDECONTROLLER, "getCommande", Map.of("id", id));

		// Then
		assertNotNull(c2);
	}

	@Test
	void shouldGetCommandeCourante_whenGetCommandeCourante() throws SQLException, EmptyCommandeException, NotFoundException, InvalidConfigurationException {
		assertNotNull(serveur.processRequest(COMMANDECONTROLLER, "getCommandeCourante", null));
	}
}
