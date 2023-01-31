package fr.univlyon1.m2tiw.is.commandes.services;

import java.sql.SQLException;
import java.util.Collection;

import fr.univlyon1.m2tiw.is.commandes.model.Option;

/**
 * Service de gestion des options.
 */
public interface OptionService {

	/**
	 * Retourne toutes les options.
	 *
	 * @return une {@link Collection<Option>}.
	 * @throws SQLException pour une exception SQL.
	 */
	Collection<Option> getAllOptions() throws SQLException;
}
