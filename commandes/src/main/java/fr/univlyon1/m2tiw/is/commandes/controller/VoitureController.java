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
import fr.univlyon1.m2tiw.is.commandes.services.InvalidConfigurationException;
import fr.univlyon1.m2tiw.is.commandes.services.VoitureService;
import fr.univlyon1.m2tiw.tiw1.annotations.Controller;

/**
 * Classe de contrôle des voitures.
 */
@Controller
public class VoitureController extends AbstractController {

	private static final Logger LOG = LoggerFactory.getLogger(VoitureController.class);

	private final VoitureService voitureService;

	private final VoitureResource voitureResource;

	public VoitureController(VoitureService voitureService, VoitureResource voitureResource) {
		this.voitureService = voitureService;
		this.voitureResource = voitureResource;
	}

	/**
	 * @inheritDoc
	 * @see AbstractController#process(String, Map)
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
				sauverVoiture((Long) parametres.get("voitureId"), (Commande) parametres.get("commande"));
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
	 * @inheritDoc
	 * @see VoitureResource#creerVoiture(String)
	 */
	public Voiture creerVoiture(String modele) throws SQLException {
		LOG.info("Méthode appelée: creerVoiture, avec comme paramètre(s): {}", modele);
		return voitureResource.creerVoiture(modele);
	}

	/**
	 * @inheritDoc
	 * @see VoitureService#ajouterConfiguration(Long, Option)
	 */
	public void ajouterConfiguration(Long voitureId, Option option) throws SQLException, NotFoundException {
		LOG.info("Méthode appelée: ajouterConfiguration, avec comme paramètres(s): {}, {}", voitureId, option);
		voitureService.ajouterConfiguration(voitureId, option);
	}

	/**
	 * @inheritDoc
	 * @see VoitureService#supprimerConfiguration(Long, Option)
	 */
	public void supprimerConfiguration(Long voitureId, Option option) throws SQLException, NotFoundException, InvalidConfigurationException {
		LOG.info("Méthode appelée: supprimerConfiguration, avec comme paramètres(s): {}, {}", voitureId, option);
		voitureService.supprimerConfiguration(voitureId, option);
	}

	/**
	 * @inheritDoc
	 * @see VoitureService#getOptionsForVoiture(Long)
	 */
	public Collection<Option> getOptionsForVoiture(Long voitureId) throws SQLException, NotFoundException {
		LOG.info("Méthode appelée: getOptionsForVoiture, avec comme paramètre(s): {}", voitureId);
		return voitureService.getOptionsForVoiture(voitureId);
	}

	/**
	 * @inheritDoc
	 * @see VoitureResource#getVoiture(Long)
	 */
	public Voiture getVoiture(Long voitureId) throws SQLException, NotFoundException {
		LOG.info("Méthode appelée: getVoiture, avec comme paramètre(s): {}", voitureId);
		return voitureResource.getVoiture(voitureId);
	}

	/**
	 * @inheritDoc
	 * @see VoitureResource#sauverVoiture(Long, Commande)
	 */
	public void sauverVoiture(Long voitureId, Commande commande) throws SQLException, NotFoundException {
		LOG.info("Méthode appelée: sauverVoiture, avec comme paramètres(s): {}, {}", voitureId, commande);
		voitureResource.sauverVoiture(voitureId, commande);
	}

	/**
	 * @inheritDoc
	 * @see VoitureService#getVoituresByCommande(Long)
	 */
	public Collection<Voiture> getVoituresByCommande(Long id) throws SQLException, NotFoundException {
		LOG.info("Méthode appelée: getVoituresByCommande, avec comme paramètre(s): {}", id);
		return voitureService.getVoituresByCommande(id);
	}

	/**
	 * @inheritDoc
	 * @see VoitureResource#supprimerVoiture(Long)
	 */
	public void supprimerVoiture(Long voitureId) throws SQLException, NotFoundException {
		LOG.info("Méthode appelée: supprimerVoiture, avec comme paramètre(s): {}", voitureId);
		voitureResource.supprimerVoiture(voitureId);
	}

}
