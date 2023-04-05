package fr.univlyon1.m2tiw.is.commandes.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Collection;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import fr.univlyon1.m2tiw.is.commandes.model.Commande;
import fr.univlyon1.m2tiw.is.commandes.model.Option;
import fr.univlyon1.m2tiw.is.commandes.model.Voiture;
import fr.univlyon1.m2tiw.is.commandes.serveur.Serveur;
import fr.univlyon1.m2tiw.is.commandes.serveur.ServeurImpl;

class VoitureDAOImplTest {

	private static CommandeDAOImpl commandeDAO;
	private VoitureDAOImpl voitureDAO;
	private static DBAccess dbAccess;

	@BeforeAll
	public static void beforeAll() throws SQLException, IOException, ClassNotFoundException {
		Serveur serveur = new ServeurImpl();
		dbAccess = serveur.getConnection();
		commandeDAO = new CommandeDAOImpl(dbAccess);
		commandeDAO.init();
	}

	@BeforeEach
	public void before() throws SQLException {
		voitureDAO = new VoitureDAOImpl(dbAccess);
		voitureDAO.init();
	}

	@Test
	void shouldSaveVoiture_whenSaveVoiture() throws SQLException, NotFoundException {
		// Given
		Voiture voiture = new Voiture("modele1");
		Commande c = commandeDAO.saveCommande(new Commande(false));

		// When
		voiture = voitureDAO.saveVoiture(voiture, c.getId());

		// Then
		assertNotNull(voiture);
		assertEquals("modele1", voiture.getModele());
		voitureDAO.deleteVoiture(voiture);
		commandeDAO.deleteCommande(c.getId());
	}

	@Test
	void shouldGetVoitureById_whenGetVoitureById() throws SQLException, NotFoundException {
		// Given
		Voiture voiture = new Voiture("modele1");
		Commande c = commandeDAO.saveCommande(new Commande(false));
		voiture = voitureDAO.saveVoiture(voiture, c.getId());

		// When
		Voiture voiture2 = voitureDAO.getVoitureById(voiture.getId());

		// Then
		assertEquals(voiture, voiture2);
		assertEquals("modele1", voiture2.getModele());
		voitureDAO.deleteVoiture(voiture);
		commandeDAO.deleteCommande(c.getId());
	}

	@Test
	void shouldGetVoituresByCommande_whenGetVoituresByCommande() throws SQLException, NotFoundException {
		// Given
		Voiture voiture = new Voiture("modele1");
		Commande c = commandeDAO.saveCommande(new Commande(false));
		voiture = voitureDAO.saveVoiture(voiture, c.getId());

		// When
		Collection<Voiture> voitures = voitureDAO.getVoituresByCommande(c.getId());

		// Then
		assertEquals(1, voitures.size());
		for (var v : voitures) {
			assertEquals(v, voiture);
			assertEquals(v.getModele(), voiture.getModele());
		}
		voitureDAO.deleteVoiture(voiture);
		commandeDAO.deleteCommande(c.getId());
	}

	@Test
	void shouldUpdateVoiture_whenUpdateVoiture() throws SQLException, NotFoundException {
		// Given
		Voiture voiture = new Voiture("modele1");
		Commande c = commandeDAO.saveCommande(new Commande(false));
		voiture = voitureDAO.saveVoiture(voiture, c.getId());
		voiture.setModele("modele2");

		// When
		voitureDAO.updateVoiture(voiture);

		// Then
		Voiture voiture2 = voitureDAO.getVoitureById(voiture.getId());
		Option option = new Option("option1", "100");
		voiture2.addOption(option);
		assertEquals(1, voiture2.getOptions().size());
		voiture2.deleteOption(option);
		assertEquals(voiture, voiture2);
		assertEquals("modele2", voiture2.getModele());
		assertEquals(voiture.hashCode(), voiture2.hashCode());
		voitureDAO.deleteVoiture(voiture);
		commandeDAO.deleteCommande(c.getId());
	}

	@Test
	void shouldThrowNotFoundException_whenUpdateVoiture_withIdIntrouvable() {
		// Given
		Voiture voiture = new Voiture(1L, "modele1");

		// Then
		assertThrows(NotFoundException.class, () -> voitureDAO.updateVoiture(voiture));
	}

	@Test
	void shouldUpdateVoitureCommande_whenUpdateVoitureCommande() throws SQLException, NotFoundException {
		// Given
		Voiture voiture = new Voiture("modele1");
		Commande c = commandeDAO.saveCommande(new Commande(false));
		voiture = voitureDAO.saveVoiture(voiture, c.getId());
		Commande c2 = commandeDAO.saveCommande(new Commande(false));

		// When
		voitureDAO.updateVoitureCommande(voiture.getId(), c2.getId());

		// Then
		var voituresC2 = voitureDAO.getVoituresByCommande(c2.getId());
		assertEquals(1, voituresC2.size());
		for (var v : voituresC2) {
			assertEquals(v, voiture);
		}
		voitureDAO.deleteVoiture(voiture);
		commandeDAO.deleteCommande(c.getId());
		commandeDAO.deleteCommande(c2.getId());
	}

	@Test
	void shouldThrowNotFoundException_whenUpdateVoitureCommande() throws SQLException {
		// Given
		Commande c = commandeDAO.saveCommande(new Commande(false));

		// Then
		assertThrows(NotFoundException.class, () -> voitureDAO.updateVoitureCommande(0L, c.getId()));
		commandeDAO.deleteCommande(c.getId());
	}

	@Test
	void shouldDeleteVoiture_whenDeleteVoiture() throws SQLException, NotFoundException {
		// Given
		Voiture voiture = new Voiture("modele1");
		Commande c = commandeDAO.saveCommande(new Commande(false));
		final var voiture2 = voitureDAO.saveVoiture(voiture, c.getId());

		// When
		voitureDAO.deleteVoiture(voiture);

		// Then
		assertEquals(0, voitureDAO.getVoituresByCommande(c.getId()).size());
		assertThrows(NotFoundException.class, () -> {
			voitureDAO.getVoitureById(voiture2.getId());
		});
	}

	@Test
	void shouldThrowNotFoundException_whenDeleteVoiture() {
		// Given
		Voiture voiture = new Voiture(0L, "modele1");

		// Then
		assertThrows(NotFoundException.class, () -> voitureDAO.deleteVoiture(voiture));
	}
}
