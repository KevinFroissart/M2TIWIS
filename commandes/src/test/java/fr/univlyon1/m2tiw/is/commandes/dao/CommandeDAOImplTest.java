package fr.univlyon1.m2tiw.is.commandes.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import fr.univlyon1.m2tiw.is.commandes.model.Commande;
import fr.univlyon1.m2tiw.is.commandes.model.Voiture;
import fr.univlyon1.m2tiw.is.commandes.serveur.Serveur;
import fr.univlyon1.m2tiw.is.commandes.serveur.ServeurImpl;

class CommandeDAOImplTest {

	private CommandeDAOImpl commandeDAO;

	@BeforeEach
	public void setup() throws SQLException, IOException, ClassNotFoundException {
		Serveur serveur = new ServeurImpl();
		DBAccess dbAccess = serveur.getConnection();
		commandeDAO = new CommandeDAOImpl(dbAccess);
		commandeDAO.init();
	}

	@Test
	void shouldSaveCommande_whenSaveCommande() throws SQLException {
		// Given
		Commande c = new Commande(false);

		// When
		c = commandeDAO.saveCommande(c);

		// Then
		assertNotNull(c);
		assertFalse(c.isFerme());
		commandeDAO.deleteCommande(c.getId());
	}

	@Test
	void shouldGetCommande_whenGetCommande() throws SQLException, NotFoundException {
		// Given
		Commande c = new Commande(false);
		c = commandeDAO.saveCommande(c);

		// When
		Commande c2 = commandeDAO.getCommande(c.getId());

		// Then
		assertEquals(c, c2);
		assertEquals(c.isFerme(), c2.isFerme());
		commandeDAO.deleteCommande(c.getId());
	}

	@Test
	void shouldThrowNotFoundException_whenGetCommande() {
		assertThrows(NotFoundException.class, () -> commandeDAO.getCommande(0L));
	}

	@Test
	void shouldUpdateCommande_whenUpdateCommande() throws SQLException, NotFoundException {
		// Given
		Commande c = new Commande(false);
		c = commandeDAO.saveCommande(c);
		c.setFerme(true);

		// When
		commandeDAO.updateCommande(c);

		// Then
		Commande c2 = commandeDAO.getCommande(c.getId());
		List<Voiture> voitures = new ArrayList<>();
		voitures.add(new Voiture("modele"));
		c2.setVoitures(voitures);
		assertEquals(c.hashCode(), c2.hashCode());
		assertEquals(c, c2);
		assertTrue(c2.isFerme());
		commandeDAO.deleteCommande(c.getId());
	}

	@Test
	void shouldThrowNotFoundException_whenUpdateCommande() {
		assertThrows(NotFoundException.class, () -> commandeDAO.updateCommande(new Commande(0L, false, null)));
	}
}
