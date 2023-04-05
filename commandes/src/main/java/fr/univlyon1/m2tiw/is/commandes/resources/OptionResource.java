package fr.univlyon1.m2tiw.is.commandes.resources;

import java.sql.SQLException;
import java.util.Collection;

import fr.univlyon1.m2tiw.is.commandes.dao.OptionDAO;
import fr.univlyon1.m2tiw.is.commandes.model.Option;

/**
 * Resource pour les options.
 */
public class OptionResource {

	private final OptionDAO optionDAO;

	public OptionResource(OptionDAO dao) {
		this.optionDAO = dao;
	}

	/**
	 * Retourne toutes les options.
	 *
	 * @return une {@link Collection<Option>}.
	 * @throws SQLException pour une exception SQL.
	 */
	public Collection<Option> getAllOptions() throws SQLException {
		return optionDAO.getAllOptions();
	}

}
