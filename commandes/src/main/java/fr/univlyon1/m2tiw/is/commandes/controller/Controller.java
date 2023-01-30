package fr.univlyon1.m2tiw.is.commandes.controller;

import java.sql.SQLException;
import java.util.Map;

import fr.univlyon1.m2tiw.is.commandes.dao.NotFoundException;
import fr.univlyon1.m2tiw.is.commandes.services.EmptyCommandeException;
import fr.univlyon1.m2tiw.is.commandes.services.InvalidConfigurationException;

public interface Controller {

	/**
	 * Reçoit une méthode et ses paramètres et le délègue à la méthode associée.
	 *
	 * @param methode la méthode envoyée par le serveur.
	 * @param parametres les paramètres associés à la méthode.
	 *
	 * @return un {@link Object} remonté par les services/resources.
	 * @throws SQLException
	 * @throws NotFoundException
	 * @throws EmptyCommandeException
	 * @throws InvalidConfigurationException
	 */
	Object process(String methode, Map<String, Object> parametres) throws SQLException, NotFoundException, EmptyCommandeException, InvalidConfigurationException;

}
