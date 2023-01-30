package fr.univlyon1.m2tiw.is.commandes.dao;

import java.sql.Connection;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Shared init procedure for DAOs based on SQL.
 */
public abstract class AbstractSQLDAO {

	private static final Logger LOG = LoggerFactory.getLogger(AbstractSQLDAO.class);

	private DBAccess dbAccess;
	private Connection currentConnection = null;

	public AbstractSQLDAO(DBAccess dbAccess) {
		this.dbAccess = dbAccess;
	}

	/**
	 * Initialize table and statements.
	 *
	 * @throws SQLException If there is an error while creating statements or tables.
	 */
	public void init() throws SQLException {
		Connection connection = dbAccess.getConnection();
		if (currentConnection != connection) {
			LOG.debug("Initializing table and statements");
			setupTable(connection);
			initStatements(connection);
			currentConnection = connection;
		}
		LOG.debug("End of init");
	}

	/**
	 * Initializes statements used for SQL queries.
	 *
	 * @param connection SQL connection to the DB
	 * @throws SQLException if there is an error while creating statements
	 */
	protected abstract void initStatements(Connection connection) throws SQLException;

	/**
	 * Creates tables for storing DAO target entity.
	 *
	 * @param connection SQL connection to the DB
	 * @throws SQLException if there is an error while creating tables
	 */
	protected abstract void setupTable(Connection connection) throws SQLException;

	public void setDbAccess(DBAccess dbAccess) {
		this.dbAccess = dbAccess;
	}
}
