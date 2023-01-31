package fr.univlyon1.m2tiw.is.commandes.services;

import java.sql.SQLException;

import fr.univlyon1.m2tiw.is.commandes.dao.NotFoundException;
import fr.univlyon1.m2tiw.is.commandes.model.Commande;

/**
 * Service de gestion de la commande courante.
 */
public interface CommandeCouranteService {

	/**
	 * Crée une commande courante.
	 *
	 * @return la {@link Commande} courante.
	 */
	Commande creerCommandeCourante();

	/**
	 * Retourne la commande courante.
	 *
	 * @return la {@link Commande} courante.
	 */
	Commande getCommandeCourante();

	/**
	 * Valide la commande courante.
	 *
	 * @return un l'id de la commande validée.
	 * @throws EmptyCommandeException pour une commande vide.
	 * @throws SQLException pour une exception SQL.
	 * @throws NotFoundException pour une voiture non trouvée.
	 */
	long validerCommandeCourante() throws EmptyCommandeException, SQLException, NotFoundException;

}
