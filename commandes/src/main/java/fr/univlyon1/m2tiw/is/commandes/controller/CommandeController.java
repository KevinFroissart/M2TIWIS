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
	 * Crée une commande courante.
	 *
	 * @return la {@link Commande} courante.
	 */
	public Commande creerCommandeCourante() {
		LOG.info("Méthode appelée: creerCommandeCourante");
		return commandeCouranteService.creerCommandeCourante();
	}

	/**
	 * Ajoute une voiture à la commande courante.
	 *
	 * @param voitureId l'id de la {@link Voiture}.
	 * @throws SQLException
	 * @throws NotFoundException
	 */
	private void ajouterVoiture(Long voitureId) throws SQLException, NotFoundException {
		LOG.info("Méthode appelée: ajouterVoiture, avec paramètre: {}", voitureId);
		commandeCouranteResource.ajouterVoiture(voitureId);
	}

	/**
	 * Supprime une voiture à la commande courante.
	 *
	 * @param voitureId l'id de la {@link Voiture}.
	 * @throws SQLException
	 * @throws NotFoundException
	 */
	private void supprimerVoiture(Long voitureId) throws SQLException, NotFoundException {
		LOG.info("Méthode appelée: supprimerVoiture, avec paramètre: {}", voitureId);
		commandeCouranteResource.supprimerVoiture(voitureId);
	}

	/**
	 * Retourne les voitures de la commande courante.
	 *
	 * @return une {@link Collection<Voiture>}.
	 */
	private Collection<Voiture> getAllVoitures() {
		LOG.info("Méthode appelée: getAllVoitures");
		return commandeCouranteResource.getAllVoitures();
	}

	/**
	 * Retourne la commande courante.
	 *
	 * @return la {@link Commande} courante.
	 */
	private Commande getCommandeCourante() {
		LOG.info("Méthode appelée: getCommandeCourante");
		return commandeCouranteService.getCommandeCourante();
	}

	/**
	 * Valide la commande courante.
	 *
	 * @return un l'id de la commande validée.
	 * @throws EmptyCommandeException
	 * @throws SQLException
	 * @throws NotFoundException
	 */
	private long validerCommandeCourante() throws EmptyCommandeException, SQLException, NotFoundException {
		LOG.info("Méthode appelée: validerCommandeCourante");
		return commandeCouranteService.validerCommandeCourante();
	}

	/**
	 * Retourne les options de la commande courante.
	 *
	 * @return une {@link Collection<Option>} d'options.
	 * @throws SQLException
	 */
	private Collection<Option> getAllOptions() throws SQLException {
		LOG.info("Méthode appelée: getAllOptions");
		return gestionCommandeService.getAllOptions();
	}

	/**
	 * Retourne une commande pour un id donné.
	 *
	 * @param id l'id de la commande.
	 * @return la {@link Commande}
	 * @throws SQLException
	 * @throws NotFoundException
	 */
	private Commande getCommande(Long id) throws SQLException, NotFoundException {
		LOG.info("Méthode appelée: getCommande, avec paramètre: {}", id);
		return commandeArchiveeResource.getCommande(id);
	}

}
