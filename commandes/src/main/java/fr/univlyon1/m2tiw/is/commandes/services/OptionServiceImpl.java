package fr.univlyon1.m2tiw.is.commandes.services;

import java.sql.SQLException;
import java.util.Collection;

import fr.univlyon1.m2tiw.is.commandes.dao.OptionDAO;
import fr.univlyon1.m2tiw.is.commandes.model.Option;

/**
 * Implémentation de {@link OptionService}.
 */
public class OptionServiceImpl implements OptionService {

	private final OptionDAO optionDAO;

	public OptionServiceImpl(OptionDAO dao) {
		this.optionDAO = dao;
	}

	/**
	 * @inheritDoc
	 * @see OptionService#getAllOptions()
	 */
	@Override
	public Collection<Option> getAllOptions() throws SQLException {
		return optionDAO.getAllOptions();
	}

}
