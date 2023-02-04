package fr.univlyon1.m2tiw.is.commandes.services;

import static fr.univlyon1.m2tiw.is.commandes.util.Strings.VOITURECONTROLLER;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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

class VoitureServiceImplTest {

	private static int counter;
	private VoitureServiceImpl voitureService;
	private CommandeCouranteServiceImpl commandeCouranteService;
	private VoitureResource voitureResource;
	private CommandeCouranteResource commandeCouranteResource;
	private VoitureDAOImpl voitureDAO;
	private CommandeDAOImpl commandeDAO;
	private OptionDAO optionDAO;
	private Serveur serveur;

	@BeforeEach
	void setUp() throws SQLException, IOException, ClassNotFoundException {
		serveur = new ServeurImpl();
		DBAccess dbAccess = serveur.getConnection();
		optionDAO = new OptionDAOImpl(dbAccess);
		voitureDAO = new VoitureDAOImpl(dbAccess);
		commandeDAO = new CommandeDAOImpl(dbAccess);

		optionDAO.init();
		commandeDAO.init();
		voitureDAO.init();

		voitureService = new VoitureServiceImpl(voitureDAO, optionDAO);
		voitureResource = new VoitureResource(voitureDAO, optionDAO);
		commandeCouranteResource = new CommandeCouranteResource(commandeDAO, voitureResource);
		commandeCouranteService = new CommandeCouranteServiceImpl(commandeCouranteResource, voitureResource);
	}

	@Test
	void shouldCreerVoiture_whenCreerVoiture() throws SQLException, NotFoundException, EmptyCommandeException {
		// Given
		Map<String, Object> parametres = new HashMap<>();
		parametres.put("modele", "modele" + counter++);

		// When
		var v = (Voiture) serveur.processRequest(VOITURECONTROLLER, "creerVoiture", parametres);

		// Then
		assertNotNull(v);
		Voiture v2 = voitureResource.getVoiture(v.getId());
		assertEquals(v, v2);
		voitureResource.supprimerVoiture(v.getId());
	}

	@Test
	void shouldAjouterConfiguration_whenAjouterConfiguration() throws SQLException, NotFoundException, EmptyCommandeException {
		// Given
		Voiture v = voitureResource.creerVoiture("modele" + counter++);
		Option option = new Option("nom" + counter++, "valeur" + counter++);

		// When
		serveur.processRequest(VOITURECONTROLLER, "ajouterConfiguration", Map.of("voitureId", v.getId(), "option", option));

		// Then
		Collection<Option> options = voitureService.getOptionsForVoiture(v.getId());
		assertTrue(options.stream().anyMatch(o -> o.equals(option)));
		voitureService.supprimerConfiguration(v.getId(), option);
		voitureResource.supprimerVoiture(v.getId());
	}

	@Test
	void shouldSupprimerConfiguration_whenSupprimerConfiguration() throws SQLException, NotFoundException, EmptyCommandeException {
		// Given
		Voiture v = voitureResource.creerVoiture("modele" + counter++);
		Option option = new Option("nom" + counter++, "valeur" + counter++);
		voitureService.ajouterConfiguration(v.getId(), option);

		// When
		serveur.processRequest(VOITURECONTROLLER, "supprimerConfiguration", Map.of("voitureId", v.getId(), "option", option));

		// Then
		Collection<Option> options = voitureService.getOptionsForVoiture(v.getId());
		assertTrue(options.stream().noneMatch(o -> o.equals(option)));
		voitureResource.supprimerVoiture(v.getId());
	}

	@Test
	void shouldGetOptionsForVoiture_whenGetOptionsForVoiture() throws SQLException, NotFoundException, EmptyCommandeException {
		// Given
		Voiture v = voitureResource.creerVoiture("modele" + counter++);
		Option option = new Option("nom" + counter++, "valeur" + counter++);
		Option option2 = new Option("nom" + counter++, "valeur" + counter++);
		voitureService.ajouterConfiguration(v.getId(), option);
		voitureService.ajouterConfiguration(v.getId(), option2);

		// When
		var options = (Collection<Option>) serveur.processRequest(VOITURECONTROLLER, "getOptionsForVoiture", Map.of("voitureId", v.getId()));

		// Then
		assertEquals(2, options.size());
		assertTrue(options.stream().anyMatch(o -> o.equals(option)));
		assertTrue(options.stream().anyMatch(o -> o.equals(option2)));
		voitureService.supprimerConfiguration(v.getId(), option);
		voitureService.supprimerConfiguration(v.getId(), option2);
		voitureResource.supprimerVoiture(v.getId());
	}

	@Test
	void shouldGetVoiture_whenGetVoiture() throws SQLException, NotFoundException, EmptyCommandeException {
		// Given
		Voiture v = voitureResource.creerVoiture("modele" + counter++);
		optionDAO.setOptionVoiture(v.getId(), new Option("nom0", "valeur0"));

		// When
		var v2 = (Voiture) serveur.processRequest(VOITURECONTROLLER, "getVoiture", Map.of("voitureId", v.getId()));

		// Then
		assertEquals(v, v2);
		assertNotNull(v2.getOptions());
		optionDAO.deleteOptionVoiture(v.getId(),"nom0");
		voitureResource.supprimerVoiture(v.getId());
	}

	@Test
	void shouldSauverVoiture_whenSauverVoiture() throws SQLException, NotFoundException, EmptyCommandeException {
		// Given
		Commande c = commandeCouranteResource.creerCommandeCourante();
		Voiture v = voitureResource.creerVoiture("modele" + counter++);
		commandeCouranteService.ajouterVoiture(v.getId());
		c = commandeDAO.saveCommande(c); // sinon c n'a pas d'id

		// When
		serveur.processRequest(VOITURECONTROLLER, "sauverVoiture", Map.of("voitureId", v.getId(), "commande", c));

		// Then
		var voitures = voitureDAO.getVoituresByCommande(c.getId());
		assertTrue(voitures.stream().anyMatch(v2 -> v2.equals(v)));
		voitureResource.supprimerVoiture(v.getId());
		commandeDAO.deleteCommande(c.getId());
	}

	@Test
	void shouldGetVoituresByCommande_whenGetVoituresByCommande() throws SQLException, NotFoundException, EmptyCommandeException {
		// Given
		Commande c = commandeCouranteResource.creerCommandeCourante();
		Voiture v = voitureResource.creerVoiture("modele" + counter++);
		commandeCouranteService.ajouterVoiture(v.getId());
		c = commandeDAO.saveCommande(c); // sinon c n'a pas d'id
		voitureResource.sauverVoiture(v.getId(), c);

		// When
		var voitures = (Collection<Voiture>) serveur.processRequest(VOITURECONTROLLER, "getVoituresByCommande", Map.of("id", c.getId()));

		// Then
		assertTrue(voitures.stream().anyMatch(v2 -> v2.equals(v)));
		voitureResource.supprimerVoiture(v.getId());
		commandeDAO.deleteCommande(c.getId());
	}

	@Test
	void shouldSupprimerVoiture_whenSupprimerVoiture() throws SQLException, NotFoundException, EmptyCommandeException {
		// Given
		Voiture v = voitureResource.creerVoiture("modele" + counter++);

		// When
		serveur.processRequest(VOITURECONTROLLER, "supprimerVoiture", Map.of("voitureId", v.getId()));

		// Then
		assertThrows(NotFoundException.class, () -> voitureResource.getVoiture(v.getId()));
	}
}
