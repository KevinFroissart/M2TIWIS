package fr.univlyon1.m2tiw.is.commandes.controller;

import java.sql.SQLException;
import java.util.Collection;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.univlyon1.m2tiw.is.commandes.dao.NotFoundException;
import fr.univlyon1.m2tiw.is.commandes.model.Option;
import fr.univlyon1.m2tiw.is.commandes.services.EmptyCommandeException;
import fr.univlyon1.m2tiw.is.commandes.services.OptionService;

public class OptionController extends AbstractController {

	private static final Logger LOG = LoggerFactory.getLogger(OptionController.class);
	private final OptionService optionService;

	public OptionController(OptionService optionService) {
		this.optionService = optionService;
	}

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
	 */
	public Object process(String methode, Map<String, Object> parametres) throws SQLException {
		if (methode.equals("getalloptions")) {
			return getAllOptions();
		}
		return null;
	}

	/**
	 * Retourne toutes les options.
	 *
	 * @return une {@link Collection<Option>}.
	 * @throws SQLException
	 */
	public Collection<Option> getAllOptions() throws SQLException {
		LOG.info("Méthode appelée: getAllOptions");
		return optionService.getAllOptions();
	}

}
