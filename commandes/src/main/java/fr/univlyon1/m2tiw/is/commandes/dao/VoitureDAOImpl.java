package fr.univlyon1.m2tiw.is.commandes.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.univlyon1.m2tiw.is.commandes.model.Voiture;
import fr.univlyon1.m2tiw.tiw1.annotations.Persistence;

/**
 * Implémente les méthodes de {@link VoitureDAO}.
 */
@Persistence
public class VoitureDAOImpl extends AbstractSQLDAO implements VoitureDAO {

	private static final Logger LOG = LoggerFactory.getLogger(VoitureDAOImpl.class);
	private PreparedStatement insertStatement = null;
	private PreparedStatement getByIdStatement = null;
	private PreparedStatement updateStatement = null;
	private PreparedStatement deleteStatement = null;
	private PreparedStatement getByCommandeStatement = null;
	private PreparedStatement updateCommandeStatement = null;
	private PreparedStatement insertStatementNoCmd;

	public VoitureDAOImpl(DBAccess dbAccess) {
		super(dbAccess);
	}

	/**
	 * @InheritDoc
	 * @see AbstractSQLDAO#setupTable(Connection)
	 * <p>
	 * Crée la table voiture.
	 */
	@Override
	protected void setupTable(Connection connection) throws SQLException {
		var stat = connection.createStatement();
		stat.execute("CREATE TABLE IF NOT EXISTS voiture(" +
				"id SERIAL PRIMARY KEY, " +
				"modele VARCHAR(100) NOT NULL, " +
				"commande INTEGER NULL REFERENCES commande(id)) ");
	}

	/**
	 * @InheritDoc
	 * @see AbstractSQLDAO#initStatements(Connection)
	 * <p>
	 * Prépare les requêtes SQL pour les voitures.
	 */
	@Override
	protected void initStatements(Connection connection) throws SQLException {
		insertStatement = connection.prepareStatement("INSERT INTO voiture(modele, commande) VALUES (?,?) returning id");
		insertStatementNoCmd = connection.prepareStatement("INSERT INTO voiture(modele, commande) VALUES (?,NULL) returning id");
		getByIdStatement = connection.prepareStatement("SELECT modele, commande FROM voiture WHERE id = ?");
		getByCommandeStatement = connection.prepareStatement("SELECT id, modele FROM voiture WHERE commande = ?");
		updateStatement = connection.prepareStatement("UPDATE voiture SET modele = ?  WHERE id = ?");
		updateCommandeStatement = connection.prepareStatement("UPDATE voiture SET commande = ?  WHERE id = ?");
		deleteStatement = connection.prepareStatement("DELETE FROM voiture WHERE id = ?");
		LOG.debug("Prepared statements");
	}

	/**
	 * @InheritDoc
	 * @see VoitureDAO#saveVoiture(Voiture)
	 */
	@Override
	public Voiture saveVoiture(Voiture voiture) throws SQLException {
		insertStatementNoCmd.setString(1, voiture.getModele());
		ResultSet rs = insertStatementNoCmd.executeQuery();
		if (rs.next()) {
			voiture.setId(rs.getLong(1));
			return voiture;
		}
		else {
			throw new SQLException("Failed to create voiture");
		}
	}

	/**
	 * @InheritDoc
	 * @see VoitureDAO#saveVoiture(Voiture, long)
	 */
	@Override
	public Voiture saveVoiture(Voiture voiture, long commandeId) throws SQLException {
		insertStatement.setString(1, voiture.getModele());
		insertStatement.setLong(2, commandeId);
		ResultSet rs = insertStatement.executeQuery();
		if (rs.next()) {
			voiture.setId(rs.getLong(1));
			return voiture;
		}
		else {
			throw new SQLException("Failed to create voiture");
		}
	}

	/**
	 * @InheritDoc
	 * @see VoitureDAO#getVoitureById(long)
	 */
	@Override
	public Voiture getVoitureById(long id) throws NotFoundException, SQLException {
		getByIdStatement.setLong(1, id);
		ResultSet rs = getByIdStatement.executeQuery();
		if (rs.next()) {
			return new Voiture(id, rs.getString(1));
		}
		else {
			throw new NotFoundException();
		}
	}

	/**
	 * @InheritDoc
	 * @see VoitureDAO#getVoituresByCommande(long)
	 */
	@Override
	public Collection<Voiture> getVoituresByCommande(long commandeId) throws SQLException, NotFoundException {
		Collection<Voiture> result = new ArrayList<>();
		getByCommandeStatement.setLong(1, commandeId);
		ResultSet rs = getByCommandeStatement.executeQuery();
		while (rs.next()) {
			result.add(new Voiture(rs.getLong(1), rs.getString(2)));
		}
		return result;
	}

	/**
	 * @InheritDoc
	 * @see VoitureDAO#updateVoiture(Voiture)
	 */
	@Override
	public Voiture updateVoiture(Voiture voiture) throws NotFoundException, SQLException {
		// On ne met pas à jour la commande ici
		updateStatement.setLong(2, voiture.getId());
		updateStatement.setString(1, voiture.getModele());
		int count = updateStatement.executeUpdate();
		if (count < 1) {
			throw new NotFoundException("voiture " + voiture.getId() + " not updated");
		}
		else if (count > 1) {
			throw new SQLException("Duplicate voiture " + voiture.getId() + " in DB");
		}
		return voiture;
	}

	/**
	 * @InheritDoc
	 * @see VoitureDAO#updateVoitureCommande(long, long)
	 */
	public void updateVoitureCommande(long voitureId, long newCommandeId) throws SQLException, NotFoundException {
		updateCommandeStatement.setLong(2, voitureId);
		updateCommandeStatement.setLong(1, newCommandeId);
		int count = updateCommandeStatement.executeUpdate();
		if (count < 1) {
			throw new NotFoundException("voiture " + voitureId + " not updated");
		}
		else if (count > 1) {
			throw new SQLException("Duplicate voiture " + voitureId + " in DB");
		}
	}

	/**
	 * @InheritDoc
	 * @see VoitureDAO#deleteVoiture(Voiture)
	 */
	@Override
	public void deleteVoiture(Voiture voiture) throws SQLException, NotFoundException {
		deleteStatement.setLong(1, voiture.getId());
		int count = deleteStatement.executeUpdate();
		if (count < 1) {
			throw new NotFoundException("Voiture " + voiture.getId() + " to delete was not found");
		}
	}
}
