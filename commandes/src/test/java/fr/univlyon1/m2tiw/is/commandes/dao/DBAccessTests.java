package fr.univlyon1.m2tiw.is.commandes.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.IOException;
import java.sql.SQLException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import fr.univlyon1.m2tiw.is.commandes.serveur.Serveur;
import fr.univlyon1.m2tiw.is.commandes.serveur.ServeurImpl;

/**
 * Test suite for DBAccess
 */
class DBAccessTests {

	private DBAccess dbAccess;

	@BeforeEach
	public void setup() throws SQLException, IOException, ClassNotFoundException {
		Serveur serveur = new ServeurImpl();
		dbAccess = serveur.getConnection();
	}

	@Test
	void testCanConnect() throws SQLException {
		assertNotNull(dbAccess.getConnection());
	}

	@Test
	void sameConnection() throws SQLException {
		var conn1 = dbAccess.getConnection();
		var conn2 = dbAccess.getConnection();
		assertEquals(conn1, conn2);
	}

}
