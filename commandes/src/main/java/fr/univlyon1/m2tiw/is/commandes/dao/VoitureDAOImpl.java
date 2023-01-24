package fr.univlyon1.m2tiw.is.commandes.dao;

import fr.univlyon1.m2tiw.is.commandes.model.Voiture;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;

public class VoitureDAOImpl extends AbstractSQLDAO implements VoitureDAO {

    private static Logger LOG = LoggerFactory.getLogger(VoitureDAOImpl.class);
    private PreparedStatement insertStatement = null;
    private PreparedStatement getByIdStatement = null;
    private PreparedStatement updateStatement = null;
    private PreparedStatement deleteStatement = null;
    private PreparedStatement getByCommandeStatement = null;
    private PreparedStatement updateCommandeStatement = null;

    @Override
    protected void setupTable(Connection connection) throws SQLException {
        Statement stat = connection.createStatement();
        stat.execute("CREATE TABLE IF NOT EXISTS voiture(" +
                "id SERIAL PRIMARY KEY, " +
                "modele VARCHAR(100) NOT NULL, " +
                "commande INTEGER NULL REFERENCES commande(id)) ");
    }

    @Override
    protected void initStatements(Connection connection) throws SQLException {
        insertStatement = connection.prepareStatement("INSERT INTO voiture(modele, commande) VALUES (?,?) returning id");
        getByIdStatement = connection.prepareStatement("SELECT modele, commande FROM voiture WHERE id = ?");
        getByCommandeStatement = connection.prepareStatement("SELECT id, modele FROM voiture WHERE commande = ?");
        updateStatement = connection.prepareStatement("UPDATE voiture SET modele = ?  WHERE id = ?");
        updateCommandeStatement = connection.prepareStatement("UPDATE voiture SET commande = ?  WHERE id = ?");
        deleteStatement = connection.prepareStatement("DELETE FROM voiture WHERE id = ?");
        LOG.debug("Prepared statements");
    }

    @Override
    public Voiture saveVoiture(Voiture voiture, long commandeId) throws SQLException {
        insertStatement.setString(1, voiture.getModele());
        insertStatement.setLong(2, commandeId);
        ResultSet rs = insertStatement.executeQuery();
        if (rs.next()) {
            voiture.setId(rs.getLong(1));
            return voiture;
        } else {
            throw new SQLException("Failed to create voiture");
        }
    }

    @Override
    public Voiture getVoitureById(long id) throws NotFoundException, SQLException {
        getByIdStatement.setLong(1, id);
        ResultSet rs = getByIdStatement.executeQuery();
        if (rs.next()) {
            return new Voiture(id, rs.getString(1));
        } else {
            throw new NotFoundException();
        }
    }

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

    @Override
    public Voiture updateVoiture(Voiture voiture) throws NotFoundException, SQLException {
        // On ne met pas à jour la commande ici
        updateStatement.setLong(2, voiture.getId());
        updateStatement.setString(1, voiture.getModele());
        int count = updateStatement.executeUpdate();
        if (count < 1) {
            throw new NotFoundException("voiture " + voiture.getId() + " not updated");
        } else if (count > 1) {
            throw new SQLException("Duplicate voiture " + voiture.getId() + " in DB");
        }
        return voiture;
    }

    public void updateVoitureCommande(long voitureId, long newCommandeId) throws SQLException, NotFoundException {
        updateCommandeStatement.setLong(2, voitureId);
        updateCommandeStatement.setLong(1, newCommandeId);
        int count = updateCommandeStatement.executeUpdate();
        if (count < 1) {
            throw new NotFoundException("voiture " + voitureId + " not updated");
        } else if (count > 1) {
            throw new SQLException("Duplicate voiture " + voitureId + " in DB");
        }
    }

    @Override
    public void deleteVoiture(Voiture voiture) throws SQLException, NotFoundException {
        deleteStatement.setLong(1, voiture.getId());
        int count = deleteStatement.executeUpdate();
        if (count < 1) {
            throw new NotFoundException("Voiture " + voiture.getId() + " to delete was not found");
        }
    }
}
