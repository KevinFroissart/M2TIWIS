package fr.univlyon1.m2tiw.is.commandes.services;

import fr.univlyon1.m2tiw.is.commandes.dao.NotFoundException;
import fr.univlyon1.m2tiw.is.commandes.model.Option;

import java.sql.SQLException;
import java.util.Collection;

public interface OptionService {
    Collection<Option> getAllOptions() throws SQLException;
}