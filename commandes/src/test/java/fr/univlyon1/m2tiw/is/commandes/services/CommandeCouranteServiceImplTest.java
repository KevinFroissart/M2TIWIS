package fr.univlyon1.m2tiw.is.commandes.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.sql.SQLException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.univlyon1.m2tiw.is.commandes.Strings;
import fr.univlyon1.m2tiw.is.commandes.dao.NotFoundException;
import fr.univlyon1.m2tiw.is.commandes.model.Commande;
import fr.univlyon1.m2tiw.is.commandes.model.Voiture;
import fr.univlyon1.m2tiw.is.commandes.serveur.Serveur;
import fr.univlyon1.m2tiw.is.commandes.serveur.ServeurImpl;

class CommandeCouranteServiceImplTest {

	private static Logger log = LoggerFactory.getLogger(CommandeCouranteServiceImplTest.class);
	private static int counter = 0;

	private Serveur serveur;

	@BeforeEach
	public void before() throws SQLException, EmptyCommandeException, NotFoundException, InvalidConfigurationException {
		serveur = new ServeurImpl();
		serveur.processRequest(Strings.COMMANDECONTROLLER.concat(".creerCommandeCourante"), null);
	}

	private Voiture createVoiture() throws SQLException, EmptyCommandeException, NotFoundException, InvalidConfigurationException {
		Map<String, Object> parametres = new HashMap<>();
		parametres.put("modele", "modele" + counter++);
		return (Voiture) serveur.processRequest(Strings.VOITURECONTROLLER.concat(".creerVoiture"), parametres);
	}

	@Test
	void creerCommandeCourante() throws SQLException, EmptyCommandeException, NotFoundException, InvalidConfigurationException {
		Commande c = (Commande) serveur.processRequest(Strings.COMMANDECONTROLLER.concat(".creerCommandeCourante"), null);
		assertNotNull(c);
		assertFalse(c.isFerme());
	}

	@Test
	void ajouterVoiture() throws SQLException, NotFoundException, EmptyCommandeException, InvalidConfigurationException {
		Voiture v = createVoiture();
		Map<String, Object> parametres = new HashMap<>();
		parametres.put("voitureId", v.getId());
		serveur.processRequest(Strings.COMMANDECONTROLLER.concat(".ajouterVoiture"), parametres);
		assertEquals(1, ((Commande) serveur.processRequest(Strings.COMMANDECONTROLLER.concat(".getCommandeCourante"), null)).getVoitures().size());
	}

	@Test
	void supprimerVoiture() throws SQLException, NotFoundException, EmptyCommandeException, InvalidConfigurationException {
		Voiture v = createVoiture();
		Map<String, Object> parametres = new HashMap<>();
		parametres.put("voitureId", v.getId());
		serveur.processRequest(Strings.COMMANDECONTROLLER.concat(".ajouterVoiture"), parametres);
		serveur.processRequest(Strings.COMMANDECONTROLLER.concat(".supprimerVoiture"), parametres);
		assertEquals(0, ((Commande) serveur.processRequest(Strings.COMMANDECONTROLLER.concat(".getCommandeCourante"), null)).getVoitures().size());
		assertThrows(NotFoundException.class, () -> serveur.processRequest(Strings.VOITURECONTROLLER.concat(".getVoiture"), parametres));
	}

	@Test
	void getAllVoitures() throws SQLException, NotFoundException, EmptyCommandeException, InvalidConfigurationException {
		Voiture v = createVoiture();
		Map<String, Object> parametres = new HashMap<>();
		parametres.put("voitureId", v.getId());
		serveur.processRequest(Strings.COMMANDECONTROLLER.concat(".ajouterVoiture"), parametres);
		Collection<Voiture> voitures = (Collection<Voiture>) serveur.processRequest(Strings.COMMANDECONTROLLER.concat(".getAllVoitures"), null);
		assertTrue(1 >= voitures.size());
		log.debug("Voitures: {}", voitures);
		log.debug("v: {}", v);
		assertTrue(voitures.stream().anyMatch(v2 -> (v2.getId().equals(v.getId()))));
		serveur.processRequest(Strings.COMMANDECONTROLLER.concat(".supprimerVoiture"), parametres);
	}

	@Test
	void getCommandeCourante() throws SQLException, EmptyCommandeException, NotFoundException, InvalidConfigurationException {
		Commande c = (Commande) serveur.processRequest(Strings.COMMANDECONTROLLER.concat(".creerCommandeCourante"), null);
		assertEquals(c, serveur.processRequest(Strings.COMMANDECONTROLLER.concat(".getCommandeCourante"), null));
	}

	@Test
	void validerCommandeCourante() throws EmptyCommandeException, SQLException, NotFoundException, InvalidConfigurationException {
		Commande c = (Commande) serveur.processRequest(Strings.COMMANDECONTROLLER.concat(".creerCommandeCourante"), null);
		Voiture v = createVoiture();
		Map<String, Object> parametres = new HashMap<>();
		parametres.put("voitureId", v.getId());
		serveur.processRequest(Strings.COMMANDECONTROLLER.concat(".ajouterVoiture"), parametres);
		serveur.processRequest(Strings.COMMANDECONTROLLER.concat(".validerCommandeCourante"), null);
		Commande c2 = (Commande) serveur.processRequest(Strings.COMMANDECONTROLLER.concat(".getCommandeCourante"), null);
		assertNotEquals(c, c2);
	}
}
