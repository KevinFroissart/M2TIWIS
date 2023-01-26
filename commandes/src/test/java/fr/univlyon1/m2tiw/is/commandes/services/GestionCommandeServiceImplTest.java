package fr.univlyon1.m2tiw.is.commandes.services;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.sql.SQLException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import fr.univlyon1.m2tiw.is.commandes.dao.CommandeDAOImpl;
import fr.univlyon1.m2tiw.is.commandes.dao.NotFoundException;
import fr.univlyon1.m2tiw.is.commandes.dao.OptionDAOImpl;
import fr.univlyon1.m2tiw.is.commandes.dao.VoitureDAOImpl;
import fr.univlyon1.m2tiw.is.commandes.model.Commande;
import fr.univlyon1.m2tiw.is.commandes.model.Option;
import fr.univlyon1.m2tiw.is.commandes.model.Voiture;

class GestionCommandeServiceImplTest {

	private GestionCommandeServiceImpl gestionCommandeService;
	private CommandeCouranteServiceImpl commandeCouranteService;
	private VoitureServiceImpl voitureService;

	CommandeDAOImpl commandeDAO;
	VoitureDAOImpl voitureDAO;
	OptionDAOImpl optionDAO;

	@BeforeEach
	void setUp() throws SQLException {
		optionDAO = new OptionDAOImpl();
		voitureDAO = new VoitureDAOImpl();
		commandeDAO = new CommandeDAOImpl();
		optionDAO.init();
		voitureDAO.init();
		commandeDAO.init();
		OptionService optionService = new OptionServiceImpl(optionDAO);
		voitureService = new VoitureServiceImpl(voitureDAO, optionDAO);
		commandeCouranteService = new CommandeCouranteServiceImpl(commandeDAO, voitureService);
		gestionCommandeService = new GestionCommandeServiceImpl(optionService, voitureService, commandeCouranteService, commandeDAO);
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
