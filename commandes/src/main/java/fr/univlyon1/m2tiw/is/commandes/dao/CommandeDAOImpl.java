package fr.univlyon1.m2tiw.is.commandes.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.univlyon1.m2tiw.is.commandes.model.Commande;
//import fr.univlyon1.m2tiw.tiw1.annotations.Persistence;

/**
 * Implémente les méthodes de {@link CommandeDAO}.
 */
//@Persistence
public class CommandeDAOImpl extends AbstractSQLDAO implements CommandeDAO {

	private static final Logger LOG = LoggerFactory.getLogger(CommandeDAOImpl.class);

	private PreparedStatement insertStatement;
	private PreparedStatement getStatement;
	private PreparedStatement updateStatement;
	private PreparedStatement deleteStatement;

	public CommandeDAOImpl(DBAccess dbAccess) {
		super(dbAccess);
	}

	/**
	 * @InheritDoc
	 * @see AbstractSQLDAO#initStatements(Connection)
	 *
	 * Prépare les requêtes SQL pour les commandes.
	 */
	@Override
	protected void initStatements(Connection connection) throws SQLException {
		insertStatement = connection.prepareStatement(
				"INSERT INTO commande(ferme) VALUES(?) RETURNING id");
		getStatement = connection.prepareStatement(
				"SELECT ferme FROM commande WHERE id = ?");
		updateStatement = connection.prepareStatement(
				"UPDATE commande set ferme = ? WHERE id = ?");
		deleteStatement = connection.prepareStatement("DELETE from commande WHERE id = ?");
		LOG.debug("Statements prepared");
	}

	/**
	 * @InheritDoc
	 * @see AbstractSQLDAO#setupTable(Connection)
	 *
	 * Crée la table commande.
	 */
	@Override
	protected void setupTable(Connection connection) throws SQLException {
		var stat = connection.createStatement();
		stat.execute("CREATE TABLE IF NOT EXISTS commande(" +
				"id SERIAL PRIMARY KEY," +
				"ferme BOOLEAN) ");
	}

	/**
	 * @InheritDoc
	 * @see CommandeDAO#saveCommande(Commande)
	 */
	@Override
	public Commande saveCommande(Commande commande) throws SQLException {
		insertStatement.setBoolean(1, commande.isFerme());
		ResultSet rs = insertStatement.executeQuery();
		if (rs.next()) {
			commande.setId(rs.getLong(1));
			return commande;
		}
		else {
			throw new SQLException("Failed to create commande");
		}
	}

	/**
	 * @InheritDoc
	 * @see CommandeDAO#getCommande(long)
	 */
	@Override
	public Commande getCommande(long commandeId) throws SQLException, NotFoundException {
		getStatement.setLong(1, commandeId);
		ResultSet rs = getStatement.executeQuery();
		if (rs.next()) {
			return new Commande(commandeId, rs.getBoolean(1), new ArrayList<>());
		}
		else {
			throw new NotFoundException("Commande " + commandeId + " not found in DB");
		}
	}

	/**
	 * @InheritDoc
	 * @see CommandeDAO#updateCommande(Commande)
	 */
	@Override
	public void updateCommande(Commande commande) throws SQLException, NotFoundException {
		updateStatement.setBoolean(1, commande.isFerme());
		updateStatement.setLong(2, commande.getId());
		int count = updateStatement.executeUpdate();
		if (count < 1) {
			throw new NotFoundException("Commande " + commande.getId() + " cannot be found to be updated");
		}
	}

	/**
	 * @InheritDoc
	 * @see CommandeDAO#deleteCommande(long)
	 */
	@Override
	public void deleteCommande(long commandeId) throws SQLException {
		deleteStatement.setLong(1, commandeId);
		deleteStatement.executeUpdate();
	}

}
