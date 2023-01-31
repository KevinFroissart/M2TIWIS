package fr.univlyon1.m2tiw.is.commandes.services;

import java.sql.SQLException;
import java.util.Collection;

import fr.univlyon1.m2tiw.is.commandes.model.Option;

/**
 * Implémentation de {@link CommandeArchiveeService}.
 */
public class CommandeArchiveeServiceImpl implements CommandeArchiveeService {

	private final OptionService optionService;

	public CommandeArchiveeServiceImpl(OptionService optionService) {
		this.optionService = optionService;
	}

	/**
	 * @inheritDoc
	 * @see OptionService#getAllOptions()
	 */
	@Override
	public Collection<Option> getAllOptions() throws SQLException {
		return this.optionService.getAllOptions();
	}

}
