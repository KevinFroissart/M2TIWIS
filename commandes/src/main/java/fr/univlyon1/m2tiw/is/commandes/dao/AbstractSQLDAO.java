package fr.univlyon1.m2tiw.is.commandes.dao;

import fr.univlyon1.m2tiw.is.commandes.services.DBAccess;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Shared init procedure for DAOs based on SQL.
 */
public abstract class AbstractSQLDAO {
    private static final Logger LOG = LoggerFactory.getLogger(AbstractSQLDAO.class);

    private final DBAccess dbAccess = new DBAccess(); // TODO: inject
    private Connection currentConnection = null;

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
}
