package fr.univlyon1.m2tiw.is.commandes.controller;

import java.sql.SQLException;
import java.util.Collection;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.univlyon1.m2tiw.is.commandes.model.Option;
import fr.univlyon1.m2tiw.is.commandes.services.OptionService;

/**
 * Classe de contrôle des options.
 */
public class OptionController extends AbstractController {

	private static final Logger LOG = LoggerFactory.getLogger(OptionController.class);
	private final OptionService optionService;

	public OptionController(OptionService optionService) {
		this.optionService = optionService;
	}

	/**
	 * @inheritDoc
	 * @see AbstractController#process(String, Map)
	 */
	public Object process(String methode, Map<String, Object> parametres) throws SQLException {
		if (methode.equals("getalloptions")) {
			return getAllOptions();
		}
		return null;
	}

	/**
	 * @inheritDoc
	 * @see OptionService#getAllOptions()
	 */
	public Collection<Option> getAllOptions() throws SQLException {
		LOG.info("Méthode appelée: getAllOptions");
		return optionService.getAllOptions();
	}

}
