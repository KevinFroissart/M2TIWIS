package fr.univlyon1.m2tiw.is.commandes.services;

import static fr.univlyon1.m2tiw.is.commandes.util.Strings.COMMANDECONTROLLER;
import static fr.univlyon1.m2tiw.is.commandes.util.Strings.VOITURECONTROLLER;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
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

import fr.univlyon1.m2tiw.is.commandes.dao.NotFoundException;
import fr.univlyon1.m2tiw.is.commandes.model.Commande;
import fr.univlyon1.m2tiw.is.commandes.model.Voiture;
import fr.univlyon1.m2tiw.is.commandes.serveur.Serveur;
import fr.univlyon1.m2tiw.is.commandes.serveur.ServeurImpl;

class CommandeCouranteServiceImplTest {

	private static int counter = 0;

	private Serveur serveur;

	@BeforeEach
	public void before() throws SQLException, EmptyCommandeException, NotFoundException, IOException, ClassNotFoundException {
		serveur = new ServeurImpl();
		serveur.processRequest(COMMANDECONTROLLER, "creerCommandeCourante", null);
	}

	private Voiture createVoiture() throws SQLException, EmptyCommandeException, NotFoundException {
		return (Voiture) serveur.processRequest(VOITURECONTROLLER, "creerVoiture", Map.of("modele", "modele" + counter++));
	}

	@Test
	void shouldCreerCommandeCourante_whenCreerCommandeCourante() throws SQLException, EmptyCommandeException, NotFoundException {
		// When
		var c = (Commande) serveur.processRequest(COMMANDECONTROLLER, "creerCommandeCourante", null);

		// Then
		assertNotNull(c);
		assertFalse(c.isFerme());
	}

	@Test
	void shouldAjouterVoiture_whenAjouterVoiture() throws SQLException, NotFoundException, EmptyCommandeException {
		// Given
		Voiture v = createVoiture();

		// When
		serveur.processRequest(COMMANDECONTROLLER, "ajouterVoiture", Map.of("voitureId", v.getId()));

		// Then
		assertEquals(1, ((Commande) serveur.processRequest(COMMANDECONTROLLER, "getCommandeCourante", null)).getVoitures().size());
	}

	@Test
	void shouldSupprimerVoiture_whenSupprimerVoiture() throws SQLException, NotFoundException, EmptyCommandeException {
		// Given
		Voiture v = createVoiture();
		Map<String, Object> parametres = new HashMap<>();
		parametres.put("voitureId", v.getId());
		serveur.processRequest(COMMANDECONTROLLER, "ajouterVoiture", parametres);

		// When
		serveur.processRequest(COMMANDECONTROLLER, "supprimerVoiture", parametres);

		// Then
		assertEquals(0, ((Commande) serveur.processRequest(COMMANDECONTROLLER, "getCommandeCourante", null)).getVoitures().size());
		assertThrows(NotFoundException.class, () -> serveur.processRequest(VOITURECONTROLLER, "getVoiture", parametres));
	}

	@Test
	void shouldGetAllVoitures_whenGetAllVoitures() throws SQLException, NotFoundException, EmptyCommandeException {
		// Given
		Voiture v = createVoiture();
		Map<String, Object> parametres = new HashMap<>();
		parametres.put("voitureId", v.getId());
		serveur.processRequest(COMMANDECONTROLLER, "ajouterVoiture", parametres);

		// When
		Collection<Voiture> voitures = (Collection<Voiture>) serveur.processRequest(COMMANDECONTROLLER, "getAllVoitures", null);

		// Then
		assertTrue(1 >= voitures.size());
		assertTrue(voitures.stream().anyMatch(v2 -> (v2.getId().equals(v.getId()))));
		serveur.processRequest(COMMANDECONTROLLER, "supprimerVoiture", parametres);
	}

	@Test
	void shouldGetCommandeCourante_whenGetCommandeCourante() throws SQLException, EmptyCommandeException, NotFoundException {
		// Given
		Commande c = (Commande) serveur.processRequest(COMMANDECONTROLLER, "creerCommandeCourante", null);

		// Then
		assertEquals(c, serveur.processRequest(COMMANDECONTROLLER, "getCommandeCourante", null));
	}

	@Test
	void shouldValiderCommandeCourante_whenValiderCommandeCourante() throws EmptyCommandeException, SQLException, NotFoundException {
		// Given
		Commande c = (Commande) serveur.processRequest(COMMANDECONTROLLER, "creerCommandeCourante", null);
		Voiture v = createVoiture();
		serveur.processRequest(COMMANDECONTROLLER, "ajouterVoiture", Map.of("voitureId", v.getId()));

		// When
		serveur.processRequest(COMMANDECONTROLLER, "validerCommandeCourante", null);

		// Then
		Commande c2 = (Commande) serveur.processRequest(COMMANDECONTROLLER, "getCommandeCourante", null);
		assertNotEquals(c, c2);
	}
}
