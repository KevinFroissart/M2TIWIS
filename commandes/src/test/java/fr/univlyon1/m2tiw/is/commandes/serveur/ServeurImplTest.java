package fr.univlyon1.m2tiw.is.commandes.serveur;

import static fr.univlyon1.m2tiw.is.commandes.util.Strings.COMMANDECONTROLLER;
import static fr.univlyon1.m2tiw.is.commandes.util.Strings.VOITURECONTROLLER;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Collection;
import java.util.HashMap;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import fr.univlyon1.m2tiw.is.commandes.dao.CommandeDAO;
import fr.univlyon1.m2tiw.is.commandes.dao.CommandeDAOImpl;
import fr.univlyon1.m2tiw.is.commandes.dao.NotFoundException;
import fr.univlyon1.m2tiw.is.commandes.model.Option;
import fr.univlyon1.m2tiw.is.commandes.model.Voiture;
import fr.univlyon1.m2tiw.is.commandes.services.EmptyCommandeException;
import fr.univlyon1.m2tiw.is.commandes.services.InvalidConfigurationException;

public class ServeurImplTest {

	Serveur serveur;

	@BeforeEach
	void setup() throws SQLException, IOException, ClassNotFoundException {
		this.serveur = new ServeurImpl();
	}

	// Tests des appels vers CommandeCouranteService
	@Test
	void shouldCreerCommandeCourante_whenCreerCommandeCourande() throws SQLException, EmptyCommandeException, NotFoundException, InvalidConfigurationException {
		// When
		var resultat = serveur.processRequest(COMMANDECONTROLLER, "creerCommandeCourante", null);

		// Then
		assertNotNull(resultat);
	}

	@Test
	void shouldGetCommandeCounrate_whenGetCommandeCourande_withoutCommandeCourande() throws SQLException, EmptyCommandeException, NotFoundException, InvalidConfigurationException {
		// When
		var resultat = serveur.processRequest(COMMANDECONTROLLER, "getCommandeCourante", null);

		// Then
		assertNotNull(resultat);
	}

	@Test
	void shouldGetCommandeCourante_whenGetCommandeCourante_withCommandeCourante() throws SQLException, EmptyCommandeException, NotFoundException, InvalidConfigurationException {
		// Given
		var commande = serveur.processRequest(COMMANDECONTROLLER, "creerCommandeCourante", null);

		// When
		var resultat = serveur.processRequest(COMMANDECONTROLLER, "getCommandeCourante", null);

		// Then
		assertNotNull(commande);
		assertNotNull(resultat);
		assertEquals(commande, resultat);
	}

	@Test
	void shouldValiderCommandeCourante_whenValiderCommandeCourante_withVoitures() throws SQLException, EmptyCommandeException, NotFoundException, InvalidConfigurationException {
		// Given
		serveur.processRequest(COMMANDECONTROLLER, "creerCommandeCourante", null);

		HashMap<String, Object> parametres = new HashMap<>();
		parametres.put("modele", "modele1");
		Voiture voiture1 = (Voiture) serveur.processRequest(VOITURECONTROLLER, "creerVoiture", parametres);
		parametres.replace("modele", "modele1", "modele2");
		Voiture voiture2 = (Voiture) serveur.processRequest(VOITURECONTROLLER, "creerVoiture", parametres);

		parametres.put("voitureId", voiture1.getId());
		serveur.processRequest(COMMANDECONTROLLER, "ajouterVoiture", parametres);
		parametres.replace("voitureId", voiture1.getId(), voiture2.getId());
		serveur.processRequest(COMMANDECONTROLLER, "ajouterVoiture", parametres);

		// When
		var resultat = serveur.processRequest(COMMANDECONTROLLER, "validerCommandeCourante", null);

		// Then
		assertNotNull(resultat);
		serveur.processRequest(COMMANDECONTROLLER, "supprimerVoiture", parametres);
		parametres.replace("voitureId", voiture2.getId(), voiture1.getId());
		serveur.processRequest(COMMANDECONTROLLER, "supprimerVoiture", parametres);
	}

	@Test
	void shouldThrowEmptyCommandeException_whenValiderCommandeCourante_withCommandeVide() throws SQLException, EmptyCommandeException, NotFoundException, InvalidConfigurationException {
		// Given
		serveur.processRequest(COMMANDECONTROLLER, "creerCommandeCourante", null);

		// Then
		assertThrows(EmptyCommandeException.class, () -> serveur.processRequest(COMMANDECONTROLLER, "validerCommandeCourante", null));
	}

	// Tests des appels vers CommandeCouranteResource
	@Test
	void shouldRetournerAucuneVoiture_whenGetAllVoitures_withAucuneVoiture() throws SQLException, EmptyCommandeException, NotFoundException, InvalidConfigurationException {
		// Given
		serveur.processRequest(COMMANDECONTROLLER, "creerCommandeCourante", null);

		// When
		Collection<Voiture> resultat = (Collection<Voiture>) serveur.processRequest(COMMANDECONTROLLER, "getAllVoitures", null);

		// Then
		assertEquals(0, resultat.size());
	}

	@Test
	void shouldRetournerDesVoitures_whenGetAllVoitures_withVoitures() throws SQLException, EmptyCommandeException, NotFoundException, InvalidConfigurationException {
		// Given
		serveur.processRequest(COMMANDECONTROLLER, "creerCommandeCourante", null);

		int sizeBefore = ((Collection<Voiture>) serveur.processRequest(COMMANDECONTROLLER, "getAllVoitures", null)).size();

		HashMap<String, Object> parametres = new HashMap<>();
		parametres.put("modele", "modele1");
		Voiture voiture1 = (Voiture) serveur.processRequest(VOITURECONTROLLER, "creerVoiture", parametres);
		parametres.replace("modele", "modele1", "modele2");
		Voiture voiture2 = (Voiture) serveur.processRequest(VOITURECONTROLLER, "creerVoiture", parametres);

		parametres.put("voitureId", voiture1.getId());
		serveur.processRequest(COMMANDECONTROLLER, "ajouterVoiture", parametres);
		parametres.replace("voitureId", voiture1.getId(), voiture2.getId());
		serveur.processRequest(COMMANDECONTROLLER, "ajouterVoiture", parametres);

		// When
		Collection<Voiture> resultat = (Collection<Voiture>) serveur.processRequest(COMMANDECONTROLLER, "getAllVoitures", null);

		// Then
		assertEquals(sizeBefore + 2, resultat.size());
		serveur.processRequest(COMMANDECONTROLLER, "supprimerVoiture", parametres);
		parametres.replace("voitureId", voiture2.getId(), voiture1.getId());
		serveur.processRequest(COMMANDECONTROLLER, "supprimerVoiture", parametres);
	}

	@Test
	void shouldAjouterUneVoiture_whenAjouterVoiture_withUneVoiture() throws SQLException, EmptyCommandeException, NotFoundException, InvalidConfigurationException {
		// Given
		serveur.processRequest(COMMANDECONTROLLER, "creerCommandeCourante", null);

		int sizeBefore = ((Collection<Voiture>) serveur.processRequest(COMMANDECONTROLLER, "getAllVoitures", null)).size();

		HashMap<String, Object> parametres = new HashMap<>();
		parametres.put("modele", "modele1");
		Voiture voiture1 = (Voiture) serveur.processRequest(VOITURECONTROLLER, "creerVoiture", parametres);

		parametres.put("voitureId", voiture1.getId());

		// When
		serveur.processRequest(COMMANDECONTROLLER, "ajouterVoiture", parametres);

		// Then
		assertEquals(sizeBefore + 1, ((Collection<Voiture>) serveur.processRequest(COMMANDECONTROLLER, "getAllVoitures", null)).size());
		serveur.processRequest(COMMANDECONTROLLER, "supprimerVoiture", parametres);
	}

	@Test
	void shouldThrowNotFoundException_whenAjouterVoiture_withMauvaisId() throws SQLException, EmptyCommandeException, NotFoundException, InvalidConfigurationException {
		// Given
		serveur.processRequest(COMMANDECONTROLLER, "creerCommandeCourante", null);

		HashMap<String, Object> parametres = new HashMap<>();
		parametres.put("voitureId", 0L);

		// Then
		assertThrows(NotFoundException.class, () -> serveur.processRequest(COMMANDECONTROLLER, "ajouterVoiture", parametres));
	}

	@Test
	void shouldSupprimerVoiture_whenSupprimerVoiture_withUneVoiture() throws SQLException, EmptyCommandeException, NotFoundException, InvalidConfigurationException {
		// Given
		serveur.processRequest(COMMANDECONTROLLER, "creerCommandeCourante", null);

		HashMap<String, Object> parametres = new HashMap<>();
		parametres.put("modele", "modele1");
		Voiture voiture1 = (Voiture) serveur.processRequest(VOITURECONTROLLER, "creerVoiture", parametres);

		parametres.put("voitureId", voiture1.getId());
		serveur.processRequest(COMMANDECONTROLLER, "ajouterVoiture", parametres);

		int sizeBefore = ((Collection<Voiture>) serveur.processRequest(COMMANDECONTROLLER, "getAllVoitures", null)).size();

		// When
		serveur.processRequest(COMMANDECONTROLLER, "supprimerVoiture", parametres);

		// Then
		assertEquals(sizeBefore - 1, ((Collection<Voiture>) serveur.processRequest(COMMANDECONTROLLER, "getAllVoitures", null)).size());
	}

	@Test
	void shouldThrowNotFoundException_whenSupprimerVoiture_withMauvaisId() throws SQLException, EmptyCommandeException, NotFoundException, InvalidConfigurationException {
		// Given
		serveur.processRequest(COMMANDECONTROLLER, "creerCommandeCourante", null);

		HashMap<String, Object> parametres = new HashMap<>();
		parametres.put("voitureId", 0L);

		// Then
		assertThrows(NotFoundException.class, () -> serveur.processRequest(COMMANDECONTROLLER, "supprimerVoiture", parametres));
	}

	// Tests des appels vers CommandeArchiveeService
	@Test
	void shouldGetAlloptions_whenGetAllOptions() throws SQLException, EmptyCommandeException, NotFoundException, InvalidConfigurationException {
		// Given
		Option option = new Option("test", "test");

		int sizeBefore = ((Collection<Option>) serveur.processRequest(COMMANDECONTROLLER, "getAllOptions", null)).size();

		HashMap<String, Object> parametres = new HashMap<>();
		parametres.put("modele", "modele1");
		Voiture voiture1 = (Voiture) serveur.processRequest(VOITURECONTROLLER, "creerVoiture", parametres);
		parametres.put("voitureId", voiture1.getId());
		parametres.put("option", option);
		serveur.processRequest(VOITURECONTROLLER, "ajouterConfiguration", parametres);

		// When
		int resultat = ((Collection<Option>) serveur.processRequest(COMMANDECONTROLLER, "getAllOptions", null)).size();

		// Then
		assertEquals(sizeBefore + 1, resultat);
		serveur.processRequest(VOITURECONTROLLER, "supprimerConfiguration", parametres);
		serveur.processRequest(COMMANDECONTROLLER, "supprimerVoiture", parametres);
	}

	// Tests des appels vers VoitureService
	@Test
	void shouldAjouterConfiguration_whenAjouterConfiguration_withConfiguration() throws SQLException, EmptyCommandeException, NotFoundException, InvalidConfigurationException {
		// Given
		Option option = new Option("test", "test");

		int sizeBefore = ((Collection<Option>) serveur.processRequest(COMMANDECONTROLLER, "getAllOptions", null)).size();

		HashMap<String, Object> parametres = new HashMap<>();
		parametres.put("modele", "modele1");
		Voiture voiture1 = (Voiture) serveur.processRequest(VOITURECONTROLLER, "creerVoiture", parametres);
		parametres.put("voitureId", voiture1.getId());
		parametres.put("option", option);

		// When
		serveur.processRequest(VOITURECONTROLLER, "ajouterConfiguration", parametres);

		// Then
		assertEquals(sizeBefore + 1, ((Collection<Option>) serveur.processRequest(COMMANDECONTROLLER, "getAllOptions", null)).size());
		serveur.processRequest(VOITURECONTROLLER, "supprimerConfiguration", parametres);
		serveur.processRequest(COMMANDECONTROLLER, "supprimerVoiture", parametres);
	}

	@Test
	void shouldThrowNotFoundException_whenAjouterConfiguration_withVoitureIdIntrouvable() {
		// Given
		Option option = new Option("test", "test");

		HashMap<String, Object> parametres = new HashMap<>();
		parametres.put("voitureId", 0L);
		parametres.put("option", option);

		// Then
		assertThrows(NotFoundException.class, () -> serveur.processRequest(VOITURECONTROLLER, "ajouterConfiguration", parametres));
	}

	@Test
	void shouldSupprimerConfiguration_whenSupprimerConfiguration_withConfiguration() throws SQLException, EmptyCommandeException, NotFoundException, InvalidConfigurationException {
		// Given
		Option option = new Option("test", "test");

		HashMap<String, Object> parametres = new HashMap<>();
		parametres.put("modele", "modele1");
		Voiture voiture1 = (Voiture) serveur.processRequest(VOITURECONTROLLER, "creerVoiture", parametres);
		parametres.put("voitureId", voiture1.getId());
		parametres.put("option", option);
		serveur.processRequest(VOITURECONTROLLER, "ajouterConfiguration", parametres);
		int sizeBefore = ((Collection<Option>) serveur.processRequest(COMMANDECONTROLLER, "getAllOptions", null)).size();

		// When
		serveur.processRequest(VOITURECONTROLLER, "supprimerConfiguration", parametres);

		// Then
		assertEquals(sizeBefore - 1, ((Collection<Option>) serveur.processRequest(COMMANDECONTROLLER, "getAllOptions", null)).size());
		serveur.processRequest(VOITURECONTROLLER, "supprimerConfiguration", parametres);
		serveur.processRequest(COMMANDECONTROLLER, "supprimerVoiture", parametres);
	}

	@Test
	void shouldThrowNotFoundException_whenSupprimerConfiguration_withVoitureIdIntrouvable() {
		// Given
		Option option = new Option("test", "test");

		HashMap<String, Object> parametres = new HashMap<>();
		parametres.put("voitureId", 0L);
		parametres.put("option", option);

		// When
		assertThrows(NotFoundException.class, () -> serveur.processRequest(VOITURECONTROLLER, "supprimerConfiguration", parametres));
	}

	@Test
	void shouldGetOptions_whenGetOptionsForVoiture() throws SQLException, EmptyCommandeException, NotFoundException, InvalidConfigurationException {
		// Given
		Option option1 = new Option("test", "test");
		Option option2 = new Option("test2", "test2");

		HashMap<String, Object> parametres = new HashMap<>();
		parametres.put("modele", "modele1");
		Voiture voiture1 = (Voiture) serveur.processRequest(VOITURECONTROLLER, "creerVoiture", parametres);
		parametres.put("voitureId", voiture1.getId());

		int sizeBefore = ((Collection<Option>) serveur.processRequest(VOITURECONTROLLER, "getOptionsForVoiture", parametres)).size();

		parametres.put("option", option1);
		serveur.processRequest(VOITURECONTROLLER, "ajouterConfiguration", parametres);
		parametres.replace("option", option1, option2);
		serveur.processRequest(VOITURECONTROLLER, "ajouterConfiguration", parametres);

		// When
		Collection<Option> resultat = (Collection<Option>) serveur.processRequest(VOITURECONTROLLER, "getOptionsForVoiture", parametres);

		// Then
		assertNotNull(resultat);
		assertEquals(sizeBefore + 2, resultat.size());
		serveur.processRequest(VOITURECONTROLLER, "supprimerConfiguration", parametres);
		parametres.replace("option", option2, option1);
		serveur.processRequest(VOITURECONTROLLER, "supprimerConfiguration", parametres);
		serveur.processRequest(COMMANDECONTROLLER, "supprimerVoiture", parametres);
	}

	@Test
	void shouldNeRienRetourner_whenGetOptionsForVoiture_withAucuneOptions() throws SQLException, EmptyCommandeException, NotFoundException, InvalidConfigurationException {
		// Given
		HashMap<String, Object> parametres = new HashMap<>();
		parametres.put("modele", "modele1");
		parametres.put("voitureId", 0L);

		// Then
		assertEquals(0, ((Collection<Option>) serveur.processRequest(VOITURECONTROLLER, "getOptionsForVoiture", parametres)).size());
	}

	@Test
	void shouldGetVoitures_whenGetVoituresByCommande() throws SQLException, EmptyCommandeException, NotFoundException, InvalidConfigurationException {
		// Given
		CommandeDAO commandeDAO = new CommandeDAOImpl(serveur.getConnection());
		commandeDAO.init();
		serveur.processRequest(COMMANDECONTROLLER, "creerCommandeCourante", null);

		HashMap<String, Object> parametres = new HashMap<>();
		parametres.put("modele", "modele1");
		Voiture voiture1 = (Voiture) serveur.processRequest(VOITURECONTROLLER, "creerVoiture", parametres);

		parametres.put("voitureId", voiture1.getId());
		serveur.processRequest(COMMANDECONTROLLER, "ajouterVoiture", parametres);

		long commandeId = (long) serveur.processRequest(COMMANDECONTROLLER, "validerCommandeCourante", null);
		parametres.put("id", commandeId);

		// When
		Collection<Voiture> resultat = (Collection<Voiture>) serveur.processRequest(VOITURECONTROLLER, "getVoituresByCommande", parametres);

		// Then
		assertNotNull(resultat);
		assertEquals(1, resultat.size());
		serveur.processRequest(COMMANDECONTROLLER, "supprimerVoiture", parametres);
		commandeDAO.deleteCommande(commandeId);
	}

	@Test
	void shouldNeRienRetourner_whenGetVoituresByCommande_withCommandeIdIntrouvable() throws SQLException, EmptyCommandeException, NotFoundException, InvalidConfigurationException {
		// Given
		serveur.processRequest(COMMANDECONTROLLER, "creerCommandeCourante", null);

		HashMap<String, Object> parametres = new HashMap<>();
		parametres.put("id", 0L);

		// When
		Collection<Voiture> resultat = (Collection<Voiture>) serveur.processRequest(VOITURECONTROLLER, "getVoituresByCommande", parametres);

		// Then
		assertEquals(0, resultat.size());
	}

	// Tests des appels vers VoitureResource
	@Test
	void shouldCreerVoiture_whenCreerVoiture() throws SQLException, EmptyCommandeException, NotFoundException, InvalidConfigurationException {
		// Given
		HashMap<String, Object> parametres = new HashMap<>();
		parametres.put("modele", "modele1");

		// When
		Voiture voiture = (Voiture) serveur.processRequest(VOITURECONTROLLER, "creerVoiture", parametres);

		// Then
		assertNotNull(voiture);
		parametres.put("voitureId", voiture.getId());
		serveur.processRequest(VOITURECONTROLLER, "supprimerVoiture", parametres);
	}

	@Test
	void shouldGetVoiture_whenGetVoiture() throws SQLException, EmptyCommandeException, NotFoundException, InvalidConfigurationException {
		// Given
		HashMap<String, Object> parametres = new HashMap<>();
		parametres.put("modele", "modele1");

		Voiture voiture = (Voiture) serveur.processRequest(VOITURECONTROLLER, "creerVoiture", parametres);
		parametres.put("voitureId", voiture.getId());

		// When
		Voiture resultat = (Voiture) serveur.processRequest(VOITURECONTROLLER, "getVoiture", parametres);

		// Then
		assertNotNull(voiture);
		assertNotNull(resultat);
		assertEquals(voiture, resultat);
		serveur.processRequest(VOITURECONTROLLER, "supprimerVoiture", parametres);
	}

	@Test
	void shouldThrowNotFoundException_whenGetVoiture_withVoitureIdIntrouvable() throws SQLException, EmptyCommandeException, NotFoundException, InvalidConfigurationException {
		// Given
		HashMap<String, Object> parametres = new HashMap<>();
		parametres.put("voitureId", 0L);

		// Then
		assertThrows(NotFoundException.class, () -> serveur.processRequest(VOITURECONTROLLER, "getVoiture", parametres));
	}

	@Test
	void shouldSupprimerVoiture_whenDeleteVoiture() throws SQLException, EmptyCommandeException, NotFoundException, InvalidConfigurationException {
		// Given
		HashMap<String, Object> parametres = new HashMap<>();
		parametres.put("modele", "modele1");

		Voiture voiture = (Voiture) serveur.processRequest(VOITURECONTROLLER, "creerVoiture", parametres);
		parametres.put("voitureId", voiture.getId());

		int sizeBefore = ((Collection<Voiture>) serveur.processRequest(COMMANDECONTROLLER, "getAllVoitures", null)).size();

		// When
		serveur.processRequest(VOITURECONTROLLER, "supprimerVoiture", parametres);

		// Then
		assertEquals(sizeBefore - 1, ((Collection<Voiture>) serveur.processRequest(COMMANDECONTROLLER, "getAllVoitures", null)).size());

	}

	@Test
	void shouldThrowNotFoundException_whenDeleteVoiture_withVoitureIdIntrouvable() {
		// Given
		HashMap<String, Object> parametres = new HashMap<>();
		parametres.put("voitureId", 0L);

		// Then
		assertThrows(NotFoundException.class, () -> serveur.processRequest(VOITURECONTROLLER, "supprimerVoiture", parametres));
	}
}
