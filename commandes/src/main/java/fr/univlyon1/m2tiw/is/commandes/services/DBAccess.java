package fr.univlyon1.m2tiw.is.commandes.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Encapsulate DB connections setup, management
 */
public class DBAccess {
    private final Logger LOG = LoggerFactory.getLogger(DBAccess.class);

    /**
     * Database URL.
     * See https://jdbc.postgresql.org/documentation/head/connect.html
     */
    // Par simplicité, on réutilise la base du microservice catalogue-modele, mais on pourrait utiliser une autre base.
    private static final String DB_URL = "jdbc:postgresql://localhost:5432/mdldb"; // TODO: put in a configuration file

    /**
     * Database username.
     */
    private static final String DB_USERNAME = "mdl"; // TODO: put in a configuration file

    /**
     * Database password.
     */
    private static final String DB_PASSWORD = "mdlpwd"; // TODO: put in a configuration file

    /**
     * JDBC connection. Initialized the first time a connection is required.
     * Reinitialized if the current connection if closed.
     */
    private static Connection connection = null; // static car sinon trop de connections ouvertes à l'exécution des tests

    /**
     * Check the status of the connection and reestablish a connection is needed.
     *
     * @throws SQLException If the connection cannot be established.
     */
    private void ensureConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            // See https://jdbc.postgresql.org/documentation/head/connect.html
            connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
            LOG.info("New connection to DB established");
        }
    }

    /**
     * Provides a connection to the database.
     * This connection will likely be shared by other calls to this method.
     *
     * @return A connection object.
     * @throws SQLException If the connection cannot be established.
     */
    public Connection getConnection() throws SQLException {
        ensureConnection();
        return connection;
    }
}
