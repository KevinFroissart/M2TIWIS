package fr.univlyon1.m2tiw.is.commandes.serveur;

import fr.univlyon1.m2tiw.is.commandes.dao.NotFoundException;
import fr.univlyon1.m2tiw.is.commandes.services.EmptyCommandeException;
import fr.univlyon1.m2tiw.is.commandes.services.InvalidConfigurationException;

import java.sql.SQLException;
import java.util.Map;

public interface Serveur {

    Object processRequest(String commande, Map<String, Object> parametres) throws SQLException, EmptyCommandeException, NotFoundException, InvalidConfigurationException;

}
