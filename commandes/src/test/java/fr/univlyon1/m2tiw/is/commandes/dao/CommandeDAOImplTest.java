package fr.univlyon1.m2tiw.is.commandes.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.IOException;
import java.sql.SQLException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import fr.univlyon1.m2tiw.is.commandes.model.Commande;
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
	void saveCommande() throws SQLException {
		Commande c = new Commande(false);
		c = commandeDAO.saveCommande(c);
		assertNotNull(c);
		assertFalse(c.isFerme());
		commandeDAO.deleteCommande(c.getId());
	}

	@Test
	void getCommande() throws SQLException, NotFoundException {
		Commande c = new Commande(false);
		c = commandeDAO.saveCommande(c);
		Commande c2 = commandeDAO.getCommande(c.getId());
		assertEquals(c, c2);
		assertEquals(c.isFerme(), c2.isFerme());
		commandeDAO.deleteCommande(c.getId());
	}

	@Test
	void updateCommande() throws SQLException, NotFoundException {
		Commande c = new Commande(false);
		c = commandeDAO.saveCommande(c);
		c.setFerme(true);
		commandeDAO.updateCommande(c);
		Commande c2 = commandeDAO.getCommande(c.getId());
		assertEquals(c, c2);
		assertEquals(true, c2.isFerme());
		commandeDAO.deleteCommande(c.getId());
	}
}
