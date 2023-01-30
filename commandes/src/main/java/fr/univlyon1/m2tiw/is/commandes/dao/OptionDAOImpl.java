package fr.univlyon1.m2tiw.is.commandes.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;

import fr.univlyon1.m2tiw.is.commandes.model.Option;

public class OptionDAOImpl extends AbstractSQLDAO implements OptionDAO {

	private PreparedStatement getOptionStatement = null;
	private PreparedStatement getAllOptionsStatement = null;
	private PreparedStatement getOptionsForVoitureStatement = null;
	private PreparedStatement deleteOptionForVoitureStatement;
	private PreparedStatement addOptionForVoitureStatement;

	public OptionDAOImpl(DBAccess dbAccess) {
		super(dbAccess);
	}

	@Override
	protected void initStatements(Connection connection) throws SQLException {
		getOptionStatement = connection.prepareStatement("SELECT valeur FROM option2 WHERE voiture = ? AND  nom = ?");
		getAllOptionsStatement = connection.prepareStatement("SELECT DISTINCT nom, valeur FROM option2");
		getOptionsForVoitureStatement = connection.prepareStatement("SELECT nom, valeur FROM option2 WHERE voiture = ?");
		deleteOptionForVoitureStatement = connection.prepareStatement("DELETE FROM option2 WHERE voiture = ? AND nom = ?");
		addOptionForVoitureStatement = connection.prepareStatement("INSERT INTO option2(voiture,nom,valeur) VALUES( ?,?,?)");
	}

	@Override
	protected void setupTable(Connection connection) throws SQLException {
		Statement stat = connection.createStatement();
		stat.execute("CREATE TABLE IF NOT EXISTS option2(" +
				"voiture integer references voiture(id), " +
				"nom varchar(100), " +
				"valeur varchar(100) NOT NULL," +
				"primary key (voiture, nom)) ");
	}

	@Override
	public Option getOption(long voitureId, String nom) throws SQLException, NotFoundException {
		getOptionStatement.setLong(1, voitureId);
		getOptionStatement.setString(2, nom);
		ResultSet rs = getOptionStatement.executeQuery();
		if (rs.next()) {
			Option option = new Option(nom, rs.getString(1));
			return option;
		}
		else {
			throw new NotFoundException("Option " + nom + " pour voiture " + voitureId + " nom trouvée");
		}
	}

	@Override
	public Collection<Option> getAllOptions() throws SQLException {
		ResultSet rs = getAllOptionsStatement.executeQuery();
		Collection<Option> options = new ArrayList<>();
		while (rs.next()) {
			options.add(new Option(rs.getString(1), rs.getString(2)));
		}
		return options;
	}

	@Override
	public Collection<Option> getOptionsForVoiture(long voitureId) throws SQLException {
		getOptionsForVoitureStatement.setLong(1, voitureId);
		ResultSet rs = getOptionsForVoitureStatement.executeQuery();
		Collection<Option> options = new ArrayList<>();
		while (rs.next()) {
			options.add(new Option(rs.getString(1), rs.getString(2)));
		}
		return options;
	}

	@Override
	public void setOptionVoiture(Long voitureId, Option option) throws SQLException {
		deleteOptionVoiture(voitureId, option.getNom());
		addOptionForVoitureStatement.setLong(1, voitureId);
		addOptionForVoitureStatement.setString(2, option.getNom());
		addOptionForVoitureStatement.setString(3, option.getValeur());
		addOptionForVoitureStatement.executeUpdate();
	}

	@Override
	public void deleteOptionVoiture(Long voitureId, String nom) throws SQLException {
		deleteOptionForVoitureStatement.setLong(1, voitureId);
		deleteOptionForVoitureStatement.setString(2, nom);
		deleteOptionForVoitureStatement.executeUpdate();
	}
}
