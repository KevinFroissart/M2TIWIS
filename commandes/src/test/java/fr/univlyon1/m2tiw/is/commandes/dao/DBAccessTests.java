package fr.univlyon1.m2tiw.is.commandes.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.sql.SQLException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Test suite for DBAccess
 */
public class DBAccessTests {

	private DBAccess dbAccess;

	@BeforeEach
	public void setup() {
		dbAccess = new DBAccess();
	}

	@Test
	public void testCanConnect() throws SQLException {
		assertNotNull(dbAccess.getConnection());
	}

	@Test
	public void sameConnection() throws SQLException {
		var conn1 = dbAccess.getConnection();
		var conn2 = dbAccess.getConnection();
		assertEquals(conn1, conn2);
	}

}
