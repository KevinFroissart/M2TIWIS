package fr.univlyon1.m2tiw.is.commandes.services;

import java.sql.SQLException;
import java.util.Collection;

import fr.univlyon1.m2tiw.is.commandes.model.Option;

public interface CommandeArchiveeService {

	Collection<Option> getAllOptions() throws SQLException;

}