package fr.univlyon1.m2tiw.is.commandes.controller;

import java.sql.SQLException;
import java.util.Collection;
import java.util.Map;

import fr.univlyon1.m2tiw.is.commandes.resources.CommandeArchiveeResource;
import fr.univlyon1.m2tiw.is.commandes.resources.CommandeCouranteResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.univlyon1.m2tiw.is.commandes.dao.NotFoundException;
import fr.univlyon1.m2tiw.is.commandes.model.Commande;
import fr.univlyon1.m2tiw.is.commandes.model.Option;
import fr.univlyon1.m2tiw.is.commandes.model.Voiture;
import fr.univlyon1.m2tiw.is.commandes.services.CommandeCouranteService;
import fr.univlyon1.m2tiw.is.commandes.services.EmptyCommandeException;
import fr.univlyon1.m2tiw.is.commandes.services.CommandeArchiveeService;

/**
 * Classe de contrôle des commandes.
 */
public class CommandeController extends AbstractController {

	private static final Logger LOG = LoggerFactory.getLogger(CommandeController.class);

	private final CommandeCouranteService commandeCouranteService;

	private final CommandeArchiveeService gestionCommandeService;

	private final CommandeArchiveeResource commandeArchiveeResource;

	private final CommandeCouranteResource commandeCouranteResource;

	public CommandeController(CommandeCouranteService commandeCouranteService,
							  CommandeArchiveeService gestionCommandeService,

							  CommandeArchiveeResource commandeArchiveeResource,
							  CommandeCouranteResource commandeCouranteResource
							  ) {
		this.commandeCouranteService = commandeCouranteService;
		this.gestionCommandeService = gestionCommandeService;

		this.commandeArchiveeResource = commandeArchiveeResource;
		this.commandeCouranteResource = commandeCouranteResource;
	}

	/**
	 * @inheritDoc
	 * @see AbstractController#process(String, Map)
	 */
	public Object process(String methode, Map<String, Object> parametres) throws SQLException, NotFoundException, EmptyCommandeException {
		switch (methode) {
			case "creercommandecourante":
				return creerCommandeCourante();
			case "ajoutervoiture":
				ajouterVoiture((Long) parametres.get("voitureId"));
				return null;
			case "supprimervoiture":
				supprimerVoiture((Long) parametres.get("voitureId"));
				return null;
			case "getallvoitures":
				return getAllVoitures();
			case "getcommandecourante":
				return getCommandeCourante();
			case "validercommandecourante":
				return validerCommandeCourante();
			case "getalloptions":
				return getAllOptions();
			case "getcommande":
				return getCommande((Long) parametres.get("id"));
			default:
				return null;
		}
	}

	/**
	 * @inheritDoc
	 * @see CommandeCouranteResource#creerCommandeCourante()
	 */
	public Commande creerCommandeCourante() {
		LOG.info("Méthode appelée: creerCommandeCourante");
		return commandeCouranteResource.creerCommandeCourante();
	}

	/**
	 * @inheritDoc
	 * @see CommandeCouranteService#ajouterVoiture(Long)
	 */
	private void ajouterVoiture(Long voitureId) throws SQLException, NotFoundException {
		LOG.info("Méthode appelée: ajouterVoiture, avec paramètre: {}", voitureId);
		commandeCouranteService.ajouterVoiture(voitureId);
	}

	/**
	 * @inheritDoc
	 * @see CommandeCouranteService#supprimerVoiture(Long)
	 */
	private void supprimerVoiture(Long voitureId) throws SQLException, NotFoundException {
		LOG.info("Méthode appelée: supprimerVoiture, avec paramètre: {}", voitureId);
		commandeCouranteService.supprimerVoiture(voitureId);
	}

	/**
	 * @inheritDoc
	 * @see CommandeCouranteService#getAllVoitures()
	 */
	private Collection<Voiture> getAllVoitures() {
		LOG.info("Méthode appelée: getAllVoitures");
		return commandeCouranteService.getAllVoitures();
	}

	/**
	 * @inheritDoc
	 * @see CommandeCouranteResource#getCommandeCourante()
	 */
	private Commande getCommandeCourante() {
		LOG.info("Méthode appelée: getCommandeCourante");
		return commandeCouranteResource.getCommandeCourante();
	}

	/**
	 * @inheritDoc
	 * @see CommandeCouranteResource#validerCommandeCourante()
	 */
	private long validerCommandeCourante() throws EmptyCommandeException, SQLException, NotFoundException {
		LOG.info("Méthode appelée: validerCommandeCourante");
		return commandeCouranteResource.validerCommandeCourante();
	}

	/**
	 * @inheritDoc
	 * @see CommandeArchiveeService#getAllOptions()
	 */
	private Collection<Option> getAllOptions() throws SQLException {
		LOG.info("Méthode appelée: getAllOptions");
		return gestionCommandeService.getAllOptions();
	}

	/**
	 * @inheritDoc
	 * @see CommandeArchiveeResource#getCommande(Long)
	 */
	private Commande getCommande(Long id) throws SQLException, NotFoundException {
		LOG.info("Méthode appelée: getCommande, avec paramètre: {}", id);
		return commandeArchiveeResource.getCommande(id);
	}

}
