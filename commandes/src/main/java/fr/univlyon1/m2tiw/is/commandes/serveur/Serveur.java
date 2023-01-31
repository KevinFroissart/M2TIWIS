package fr.univlyon1.m2tiw.is.commandes.serveur;

import fr.univlyon1.m2tiw.is.commandes.dao.DBAccess;
import fr.univlyon1.m2tiw.is.commandes.dao.NotFoundException;
import fr.univlyon1.m2tiw.is.commandes.services.EmptyCommandeException;
import fr.univlyon1.m2tiw.is.commandes.services.InvalidConfigurationException;

import java.sql.SQLException;
import java.util.Map;

/**
 * Interface du serveur.
 */
public interface Serveur {

    /**
     * Reçoit une commande et délègue sa méthode et ses paramètres au controlleur associé.
     *
     * @param commande la controlleur à appeler.
     * @param methode la méthode à déléguer.
     * @param parametres les paramètres à déléguer.
     * @return un {@link Object} remonté par les controlleurs.
     * @throws SQLException pour une exception SQL.
     * @throws EmptyCommandeException pour une commande vide.
     * @throws NotFoundException pour une entité non trouvée.
     * @throws InvalidConfigurationException pour une configuration invalide.
     */
    Object processRequest(String commande, String methode, Map<String, Object> parametres) throws SQLException, EmptyCommandeException, NotFoundException, InvalidConfigurationException;

    /**
     * Retourne la connexion à la base de données.
     *
     * @return une instance de {@link DBAccess}.
     */
    DBAccess getConnection();
}
