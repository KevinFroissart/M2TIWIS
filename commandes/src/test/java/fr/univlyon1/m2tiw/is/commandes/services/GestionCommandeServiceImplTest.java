package fr.univlyon1.m2tiw.is.commandes.services;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.sql.SQLException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import fr.univlyon1.m2tiw.is.commandes.Strings;
import fr.univlyon1.m2tiw.is.commandes.dao.CommandeDAOImpl;
import fr.univlyon1.m2tiw.is.commandes.dao.NotFoundException;
import fr.univlyon1.m2tiw.is.commandes.dao.OptionDAOImpl;
import fr.univlyon1.m2tiw.is.commandes.dao.VoitureDAOImpl;
import fr.univlyon1.m2tiw.is.commandes.model.Commande;
import fr.univlyon1.m2tiw.is.commandes.model.Option;
import fr.univlyon1.m2tiw.is.commandes.model.Voiture;
import fr.univlyon1.m2tiw.is.commandes.serveur.Serveur;
import fr.univlyon1.m2tiw.is.commandes.serveur.ServeurImpl;

class GestionCommandeServiceImplTest {

	private GestionCommandeServiceImpl gestionCommandeService;
	private CommandeCouranteServiceImpl commandeCouranteService;
	private VoitureServiceImpl voitureService;

	CommandeDAOImpl commandeDAO;
	VoitureDAOImpl voitureDAO;
	OptionDAOImpl optionDAO;

	private Serveur serveur;

	@BeforeEach
	void setUp() throws SQLException, EmptyCommandeException, NotFoundException, InvalidConfigurationException {
		serveur = new ServeurImpl();
		serveur.processRequest(Strings.COMMANDECONTROLLER.concat(".creerCommandeCourante"), null);
	}

	@Test
	void getAllOptions() throws SQLException, NotFoundException, EmptyCommandeException, InvalidConfigurationException {
		Map<String, Object> parametres = new HashMap<>();
		parametres.put("commande", new Commande(false));
		Commande c = (Commande) serveur.processRequest(Strings.COMMANDECONTROLLER.concat(".saveCommande"), parametres);
		parametres.put("voiture", new Voiture("modele"));
		parametres.put("commandeId", c.getId());
		Voiture v = (Voiture) serveur.processRequest(Strings.VOITURECONTROLLER.concat(".saveVoiture"), parametres);
		Option o = new Option("opt", "val");

		parametres.put("voitureId", v.getId());
		parametres.put("option", o);
		serveur.processRequest(Strings.OPTIONCONTROLLER.concat(".setOptionVoiture"), parametres);
		assertTrue(1 <= ((Collection<Option>) serveur.processRequest(Strings.COMMANDECONTROLLER.concat(".getAllOptions"), null)).size());

		parametres.put("nom", o.getNom());
		serveur.processRequest(Strings.OPTIONCONTROLLER.concat("deleteOptionVoiture"), parametres);

		parametres.remove("voiture");
		parametres.put("voiture", v);
		serveur.processRequest(Strings.VOITURECONTROLLER.concat("deletevoiture"), parametres);

		serveur.processRequest(Strings.COMMANDECONTROLLER.concat("deleteCommande"), parametres);
	}

	@Test
	void getCommande() throws SQLException, NotFoundException, EmptyCommandeException, InvalidConfigurationException {
		Map<String, Object> parametres = new HashMap<>();
		parametres.put("modele", "modele");
		Voiture v = (Voiture) serveur.processRequest(Strings.VOITURECONTROLLER.concat(".creervoiture"), parametres);
		parametres.put("voitureId", v.getId());
		serveur.processRequest(Strings.COMMANDECONTROLLER.concat(".ajouterVoiture"), parametres);
		long id = (long) serveur.processRequest(Strings.COMMANDECONTROLLER.concat(".validerCommandeCourante"), null);
		parametres.put("id", id);
		assertNotNull(serveur.processRequest(Strings.COMMANDECONTROLLER.concat(".getCommande"), parametres));
	}

	@Test
	void getCommandeCourante() throws SQLException, EmptyCommandeException, NotFoundException, InvalidConfigurationException {
		assertNotNull(serveur.processRequest(Strings.COMMANDECONTROLLER.concat(".getCommandeCourante"), null));
	}
}
