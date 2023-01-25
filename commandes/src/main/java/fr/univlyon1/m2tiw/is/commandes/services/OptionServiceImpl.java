package fr.univlyon1.m2tiw.is.commandes.services;

import java.sql.SQLException;
import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.univlyon1.m2tiw.is.commandes.dao.OptionDAO;
import fr.univlyon1.m2tiw.is.commandes.model.Option;

public class OptionServiceImpl implements OptionService {

	private static final Logger LOG = LoggerFactory.getLogger(OptionServiceImpl.class);

	private final OptionDAO dao;

	public OptionServiceImpl(OptionDAO dao) {
		this.dao = dao;
	}

	@Override
	public Collection<Option> getAllOptions() throws SQLException {
		return dao.getAllOptions();
	}

}
