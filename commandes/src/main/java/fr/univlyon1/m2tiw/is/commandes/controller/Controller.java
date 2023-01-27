package fr.univlyon1.m2tiw.is.commandes.controller;

import java.sql.SQLException;
import java.util.Map;

import fr.univlyon1.m2tiw.is.commandes.dao.NotFoundException;
import fr.univlyon1.m2tiw.is.commandes.services.EmptyCommandeException;
import fr.univlyon1.m2tiw.is.commandes.services.InvalidConfigurationException;

public interface Controller {

	Object process(String commande, Map<String, Object> parametres) throws SQLException, NotFoundException, EmptyCommandeException, InvalidConfigurationException;

}
