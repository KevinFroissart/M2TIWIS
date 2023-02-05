package fr.univlyon1.m2tiw.is.commandes.services;

import java.sql.SQLException;
import java.util.Collection;

import fr.univlyon1.m2tiw.is.commandes.model.Option;
import fr.univlyon1.m2tiw.is.commandes.resources.OptionResource;
import fr.univlyon1.m2tiw.tiw1.annotations.Service;

/**
 * Implémentation de {@link CommandeArchiveeService}.
 */
@Service
public class CommandeArchiveeServiceImpl implements CommandeArchiveeService {

	private final OptionResource optionResource;

	public CommandeArchiveeServiceImpl(OptionResource optionResource) {
		this.optionResource = optionResource;
	}

	/**
	 * @inheritDoc
	 * @see OptionResource#getAllOptions()
	 */
	@Override
	public Collection<Option> getAllOptions() throws SQLException {
		return this.optionResource.getAllOptions();
	}

}
