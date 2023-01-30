package fr.univlyon1.m2tiw.is.commandes.controller;

import java.sql.SQLException;
import java.util.Collection;
import java.util.Map;

import fr.univlyon1.m2tiw.is.commandes.resources.VoitureResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.univlyon1.m2tiw.is.commandes.dao.NotFoundException;
import fr.univlyon1.m2tiw.is.commandes.model.Commande;
import fr.univlyon1.m2tiw.is.commandes.model.Option;
import fr.univlyon1.m2tiw.is.commandes.model.Voiture;
import fr.univlyon1.m2tiw.is.commandes.services.EmptyCommandeException;
import fr.univlyon1.m2tiw.is.commandes.services.InvalidConfigurationException;
import fr.univlyon1.m2tiw.is.commandes.services.VoitureService;

public class VoitureController extends AbstractController {

	private static final Logger LOG = LoggerFactory.getLogger(VoitureController.class);

	private final VoitureService voitureService;

	private final VoitureResource voitureResource;

	public VoitureController(VoitureService voitureService, VoitureResource voitureResource) {
		this.voitureService = voitureService;
		this.voitureResource = voitureResource;
	}

	/**
	 * Reçoit une méthode et ses paramètres et le délègue à la méthode associée.
	 *
	 * @param methode la méthode envoyée par le serveur.
	 * @param parametres les paramètres associés à la méthode.
	 *
	 * @return un {@link Object} remonté par les services/resources.
	 * @throws SQLException
	 * @throws NotFoundException
	 * @throws EmptyCommandeException
	 */
	public Object process(String methode, Map<String, Object> parametres) throws SQLException, NotFoundException, InvalidConfigurationException {
		switch (methode) {
			case "creervoiture":
				return creerVoiture((String) parametres.get("modele"));
			case "ajouterconfiguration":
				ajouterConfiguration((Long) parametres.get("voitureId"), (Option) parametres.get("option"));
				return null;
			case "supprimerconfiguration":
				supprimerConfiguration((Long) parametres.get("voitureId"), (Option) parametres.get("option"));
				return null;
			case "getoptionsforvoiture":
				return getOptionsForVoiture((Long) parametres.get("voitureId"));
			case "getvoiture":
				return getVoiture((Long) parametres.get("voitureId"));
			case "sauvervoiture":
				sauverVoiture((Long) parametres.get("voitureId"), (Commande) parametres.get("commade"));
				return null;
			case "getvoituresbycommande":
				return getVoituresByCommande((Long) parametres.get("id"));
			case "supprimervoiture":
				supprimerVoiture((Long) parametres.get("voitureId"));
				return null;
			default:
				return null;
		}
	}

	/**
	 * Crée une voiture.
	 *
	 * @param modele le modèle de la voiture.
	 * @return la {@link Voiture} créée.
	 * @throws SQLException
	 */
	public Voiture creerVoiture(String modele) throws SQLException {
		LOG.info("Méthode appelée: creerVoiture, avec comme paramètre(s): {}", modele);
		return voitureResource.creerVoiture(modele);
	}

	/**
	 * Ajoute une configuration à une voiture donnée.
	 *
	 * @param voitureId l'id de la voiture.
	 * @param option l'option à ajouter.
	 * @throws SQLException
	 * @throws NotFoundException
	 */
	public void ajouterConfiguration(Long voitureId, Option option) throws SQLException, NotFoundException {
		LOG.info("Méthode appelée: ajouterConfiguration, avec comme paramètres(s): {}, {}", voitureId, option);
		voitureService.ajouterConfiguration(voitureId, option);
	}

	/**
	 * Supprime la configuration d'une voiture.
	 *
	 * @param voitureId l'id de la voiture dont on supprime la configuration.
	 * @param option l'option à supprimer;
	 * @throws SQLException
	 * @throws NotFoundException
	 * @throws InvalidConfigurationException
	 */
	public void supprimerConfiguration(Long voitureId, Option option) throws SQLException, NotFoundException, InvalidConfigurationException {
		LOG.info("Méthode appelée: supprimerConfiguration, avec comme paramètres(s): {}, {}", voitureId, option);
		voitureService.supprimerConfiguration(voitureId, option);
	}

	/**
	 * Retourne les options d'une voiture donnée.
	 *
	 * @param voitureId l'id de la voiture dont on récupère les options.
	 * @return la {@link Collection<Option>} de la voiture.
	 * @throws SQLException
	 * @throws NotFoundException
	 */
	public Collection<Option> getOptionsForVoiture(Long voitureId) throws SQLException, NotFoundException {
		LOG.info("Méthode appelée: getOptionsForVoiture, avec comme paramètre(s): {}", voitureId);
		return voitureService.getOptionsForVoiture(voitureId);
	}

	/**
	 * Retourne une voiture pour un id donné.
	 *
	 * @param voitureId l'id de la voiture.
	 * @return la {@link Voiture} pour l'id donné.
	 * @throws SQLException
	 * @throws NotFoundException
	 */
	public Voiture getVoiture(Long voitureId) throws SQLException, NotFoundException {
		LOG.info("Méthode appelée: getVoiture, avec comme paramètre(s): {}", voitureId);
		return voitureResource.getVoiture(voitureId);
	}

	/**
	 * Remplace la commande associée à une voiture.
	 * @param voitureId l'id de la voiture à modifier.
	 * @param commande la commande à remplacer.
	 * @throws SQLException
	 * @throws NotFoundException
	 */
	public void sauverVoiture(Long voitureId, Commande commande) throws SQLException, NotFoundException {
		LOG.info("Méthode appelée: sauverVoiture, avec comme paramètres(s): {}, {}", voitureId, commande);
		voitureResource.sauverVoiture(voitureId, commande);
	}

	/**
	 * Retourne les voitures d'une commande.
	 *
	 * @param id l'id de la commande.
	 * @return la {@link Collection<Voiture>} pour la commande donnée.
	 * @throws SQLException
	 * @throws NotFoundException
	 */
	public Collection<Voiture> getVoituresByCommande(Long id) throws SQLException, NotFoundException {
		LOG.info("Méthode appelée: getVoituresByCommande, avec comme paramètre(s): {}", id);
		return voitureService.getVoituresByCommande(id);
	}

	/**
	 * Supprime une voiture.
	 *
	 * @param voitureId l'id de la voiture à supprimer.
	 * @throws SQLException
	 * @throws NotFoundException
	 */
	public void supprimerVoiture(Long voitureId) throws SQLException, NotFoundException {
		LOG.info("Méthode appelée: supprimerVoiture, avec comme paramètre(s): {}", voitureId);
		voitureResource.supprimerVoiture(voitureId);
	}

}
