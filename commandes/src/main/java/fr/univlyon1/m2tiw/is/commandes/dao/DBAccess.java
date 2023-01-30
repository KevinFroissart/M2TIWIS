package fr.univlyon1.m2tiw.is.commandes.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Encapsulate DB connections setup, management
 */
public class DBAccess {
    private static final Logger LOG = LoggerFactory.getLogger(DBAccess.class);

    /**
     * Database URL.
     * See https://jdbc.postgresql.org/documentation/head/connect.html
     */
    // Par simplicité, on réutilise la base du microservice catalogue-modele, mais on pourrait utiliser une autre base.
    private String dbUrl;

    /**
     * Database username.
     */
    private String dbUsername;

    /**
     * Database password.
     */
    private String dbPassword;

    /**
     * JDBC connection. Initialized the first time a connection is required.
     * Reinitialized if the current connection if closed.
     */
    private static Connection connection = null; // static car sinon trop de connections ouvertes à l'exécution des tests

    public DBAccess(String dbUrl, String dbUsername, String dbPassword) {
        this.dbUrl = dbUrl;
        this.dbUsername = dbUsername;
        this.dbPassword = dbPassword;
    }

    /**
     * Check the status of the connection and reestablish a connection is needed.
     *
     * @throws SQLException If the connection cannot be established.
     */
    private void ensureConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            // See https://jdbc.postgresql.org/documentation/head/connect.html
            connection = DriverManager.getConnection(dbUrl, dbUsername, dbPassword);
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
