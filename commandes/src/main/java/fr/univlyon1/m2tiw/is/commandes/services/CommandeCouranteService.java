package fr.univlyon1.m2tiw.is.commandes.services;

import java.sql.SQLException;
import java.util.Collection;

import fr.univlyon1.m2tiw.is.commandes.dao.NotFoundException;
import fr.univlyon1.m2tiw.is.commandes.model.Voiture;

/**
 * Service de gestion de la commande courante.
 */
public interface CommandeCouranteService {

	/**
	 * Retourne les voitures de la commande courante.
	 *
	 * @return une {@link Collection <Voiture>}.
	 */
	Collection<Voiture> getAllVoitures();

	/**
	 * Ajoute une voiture à la commande courante.
	 *
	 * @param voitureId l'id de la {@link Voiture}.
	 * @throws SQLException pour une exception SQL.
	 * @throws NotFoundException pour une voiture non trouvée.
	 */
	void ajouterVoiture(Long voitureId) throws SQLException, NotFoundException;

	/**
	 * Supprime une voiture à la commande courante.
	 *
	 * @param voitureId l'id de la {@link Voiture}.
	 * @throws SQLException pour une exception SQL.
	 * @throws NotFoundException pour une voiture non trouvée.
	 */
	void supprimerVoiture(Long voitureId) throws SQLException, NotFoundException;

}
