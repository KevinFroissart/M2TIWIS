package fr.univlyon1.m2tiw.is.commandes.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.sql.SQLException;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import fr.univlyon1.m2tiw.is.commandes.model.Commande;
import fr.univlyon1.m2tiw.is.commandes.model.Option;
import fr.univlyon1.m2tiw.is.commandes.model.Voiture;
import fr.univlyon1.m2tiw.is.commandes.serveur.Serveur;
import fr.univlyon1.m2tiw.is.commandes.serveur.ServeurImpl;

class OptionDAOImplTest {

	private static CommandeDAOImpl commandeDAO;
	private static VoitureDAOImpl voitureDAO;
	private OptionDAOImpl optionDAO;
	private Commande commande;
	private Voiture voiture;
	private static DBAccess dbAccess;

	private static int counter = 0;

	@BeforeAll
	public static void before() throws SQLException, IOException, ClassNotFoundException {
		Serveur serveur = new ServeurImpl();
		dbAccess = serveur.getConnection();
		commandeDAO = new CommandeDAOImpl(dbAccess);
		commandeDAO.init();
		voitureDAO = new VoitureDAOImpl(dbAccess);
		voitureDAO.init();
	}

	@BeforeEach
	public void beforeEach() throws SQLException {
		optionDAO = new OptionDAOImpl(dbAccess);
		optionDAO.init();
		commande = commandeDAO.saveCommande(new Commande(false));
		voiture = voitureDAO.saveVoiture(new Voiture("modele3"), commande.getId());
	}

	@AfterEach
	public void afterEach() throws SQLException, NotFoundException {
		voitureDAO.deleteVoiture(voiture);
		commandeDAO.deleteCommande(commande.getId());
	}

	private Option createOption() throws SQLException {
		Option o = new Option("nom" + counter++, "valeur" + counter++);
		optionDAO.setOptionVoiture(voiture.getId(), o);
		return o;
	}

	@Test
	void shouldGetOption_whenGetOption() throws SQLException, NotFoundException {
		// Given
		Option o = createOption();
		o.setNom(o.getNom());
		o.setValeur(o.getValeur());

		// When
		Option o2 = optionDAO.getOption(voiture.getId(), o.getNom());

		// Then
		assertEquals(o, o2);
		assertEquals(o.getValeur(), o2.getValeur());
		optionDAO.deleteOptionVoiture(voiture.getId(), o.getNom());
	}

	@Test
	void shouldGetAllOptions_whenGetAllOptions() throws SQLException {
		// Given
		Option o = createOption();
		Option o2 = createOption();

		// When
		var options = optionDAO.getAllOptions();

		// Then
		assertTrue(2 <= options.size());
		optionDAO.deleteOptionVoiture(voiture.getId(), o.getNom());
		optionDAO.deleteOptionVoiture(voiture.getId(), o2.getNom());
	}

	@Test
	void shouldGetOptionsForVoiture_whenGetOptionsForVoiture() throws SQLException {
		// Given
		Option o = createOption();
		Option o2 = createOption();

		// When
		var options = optionDAO.getOptionsForVoiture(voiture.getId());

		// Then
		assertEquals(2, options.size());
		optionDAO.deleteOptionVoiture(voiture.getId(), o.getNom());
		optionDAO.deleteOptionVoiture(voiture.getId(), o2.getNom());
		assertEquals(0, optionDAO.getOptionsForVoiture(voiture.getId()).size());
	}

	@Test
	void shouldSetOptionVoiture_whenSetOptionVoiture() throws SQLException, NotFoundException {
		// Given
		Option o = createOption();

		// When
		optionDAO.setOptionVoiture(voiture.getId(), new Option(o.getNom(), "une nouvelle valeur"));

		// Then
		var o2 = optionDAO.getOption(voiture.getId(), o.getNom());
		assertEquals("une nouvelle valeur", o2.getValeur());
		optionDAO.deleteOptionVoiture(voiture.getId(), o.getNom());
	}

	@Test
	void shouldDeleteOptionVoiture_whenDeleteOptionVoiture() throws SQLException {
		// Given
		Option o = createOption();
		optionDAO.deleteOptionVoiture(voiture.getId(), o.getNom());

		// Then
		assertThrows(NotFoundException.class, () -> optionDAO.getOption(voiture.getId(), o.getNom()));
	}
}
