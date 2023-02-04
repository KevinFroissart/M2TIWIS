package fr.univlyon1.m2tiw.is.commandes.services;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.sql.SQLException;

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
import fr.univlyon1.m2tiw.is.commandes.resources.CommandeArchiveeResource;
import fr.univlyon1.m2tiw.is.commandes.resources.CommandeCouranteResource;
import fr.univlyon1.m2tiw.is.commandes.resources.OptionResource;
import fr.univlyon1.m2tiw.is.commandes.resources.VoitureResource;
import fr.univlyon1.m2tiw.is.commandes.serveur.Serveur;
import fr.univlyon1.m2tiw.is.commandes.serveur.ServeurImpl;

class CommandeArchiveeServiceImplTest {

	private CommandeArchiveeServiceImpl commandeArchiveeService;
	private CommandeCouranteServiceImpl commandeCouranteService;
	private CommandeArchiveeResource commandeArchiveeResource;
	private CommandeCouranteResource commandeCouranteResource;
	private VoitureResource voitureResource;
	private CommandeDAOImpl commandeDAO;
	private VoitureDAOImpl voitureDAO;
	private OptionDAOImpl optionDAO;

	@BeforeEach
	void setUp() throws SQLException, IOException, ClassNotFoundException {
		Serveur serveur = new ServeurImpl();
		DBAccess dbAccess = serveur.getConnection();
		optionDAO = new OptionDAOImpl(dbAccess);
		voitureDAO = new VoitureDAOImpl(dbAccess);
		commandeDAO = new CommandeDAOImpl(dbAccess);
		optionDAO.init();
		voitureDAO.init();
		commandeDAO.init();
		OptionResource optionResource = new OptionResource(optionDAO);
		VoitureServiceImpl voitureService = new VoitureServiceImpl(voitureDAO, optionDAO);
		voitureResource = new VoitureResource(voitureDAO, optionDAO);
		commandeCouranteResource = new CommandeCouranteResource(commandeDAO, voitureResource);
		commandeCouranteService = new CommandeCouranteServiceImpl(commandeCouranteResource, voitureResource);
		commandeArchiveeService = new CommandeArchiveeServiceImpl(optionResource);
		commandeArchiveeResource = new CommandeArchiveeResource(commandeDAO, voitureService, commandeCouranteResource);
	}

	@Test
	void getAllOptions() throws SQLException, NotFoundException {
		Commande c = commandeDAO.saveCommande(new Commande(false));
		Voiture v = voitureDAO.saveVoiture(new Voiture("modele"), c.getId());
		Option o = new Option("opt", "val");
		optionDAO.setOptionVoiture(v.getId(), o);
		var options = commandeArchiveeService.getAllOptions();
		assertTrue(1 <= options.size());
		optionDAO.deleteOptionVoiture(v.getId(), o.getNom());
		voitureDAO.deleteVoiture(v);
		commandeDAO.deleteCommande(c.getId());
	}

	@Test
	void getCommande() throws SQLException, NotFoundException, EmptyCommandeException {
		Commande c = commandeCouranteResource.creerCommandeCourante();
		Voiture v = voitureResource.creerVoiture("modele");
		commandeCouranteService.ajouterVoiture(v.getId());
		long id = commandeCouranteResource.validerCommandeCourante();
		Commande c2 = commandeArchiveeResource.getCommande(id);
		assertNotNull(c2);
	}

	@Test
	void getCommandeCourante() {
		assertNotNull(commandeArchiveeResource.getCommandeCourante());
	}
}
