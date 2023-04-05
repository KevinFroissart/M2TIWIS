package fr.univlyon1.m2tiw.is.commandes.services;

import java.sql.SQLException;
import java.util.Collection;

import fr.univlyon1.m2tiw.is.commandes.model.Option;

/**
 * Service de gestion des commandes archivées.
 */
public interface CommandeArchiveeService {

	/**
	 * Retourne les options de la commande courante.
	 *
	 * @return une {@link Collection<Option>} d'options.
	 * @throws SQLException pour une exception SQL.
	 */
	Collection<Option> getAllOptions() throws SQLException;

}
