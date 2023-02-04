package fr.univlyon1.m2tiw.is.commandes.services;

import java.sql.SQLException;
import java.util.Collection;

import fr.univlyon1.m2tiw.is.commandes.dao.NotFoundException;
import fr.univlyon1.m2tiw.is.commandes.model.Option;
import fr.univlyon1.m2tiw.is.commandes.model.Voiture;

/**
 * Service de gestion des voitures.
 */
public interface VoitureService {

	/**
	 * Ajoute une configuration à une voiture donnée.
	 *
	 * @param voitureId l'id de la voiture.
	 * @param option l'option à ajouter.
	 * @throws SQLException pour une exception SQL.
	 * @throws NotFoundException pour une voiture non trouvée.
	 */
	void ajouterConfiguration(Long voitureId, Option option) throws SQLException, NotFoundException;

	/**
	 * Supprime la configuration d'une voiture.
	 *
	 * @param voitureId l'id de la voiture dont on supprime la configuration.
	 * @param option l'option à supprimer;
	 * @throws SQLException pour une exception SQL.
	 */
	void supprimerConfiguration(Long voitureId, Option option) throws SQLException;

	/**
	 * Retourne les options d'une voiture donnée.
	 *
	 * @param voitureId l'id de la voiture dont on récupère les options.
	 * @return la {@link Collection<Option>} de la voiture.
	 * @throws SQLException pour une exception SQL.
	 * @throws NotFoundException pour une voiture non trouvée.
	 */
	Collection<Option> getOptionsForVoiture(Long voitureId) throws SQLException, NotFoundException;

	/**
	 * Retourne les voitures d'une commande.
	 *
	 * @param id l'id de la commande.
	 * @return la {@link Collection<Voiture>} pour la commande donnée.
	 * @throws SQLException pour une exception SQL.
	 * @throws NotFoundException pour une voiture non trouvée.
	 */
	Collection<Voiture> getVoituresByCommande(Long id) throws SQLException, NotFoundException;

}
