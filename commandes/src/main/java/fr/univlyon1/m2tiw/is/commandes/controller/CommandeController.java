package fr.univlyon1.m2tiw.is.commandes.controller;

import java.sql.SQLException;
import java.util.Collection;
import java.util.Map;

import fr.univlyon1.m2tiw.is.commandes.resource.CommandeArchiveeResource;
import fr.univlyon1.m2tiw.is.commandes.resource.CommandeCouranteResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.univlyon1.m2tiw.is.commandes.dao.NotFoundException;
import fr.univlyon1.m2tiw.is.commandes.model.Commande;
import fr.univlyon1.m2tiw.is.commandes.model.Option;
import fr.univlyon1.m2tiw.is.commandes.model.Voiture;
import fr.univlyon1.m2tiw.is.commandes.services.CommandeCouranteService;
import fr.univlyon1.m2tiw.is.commandes.services.EmptyCommandeException;
import fr.univlyon1.m2tiw.is.commandes.services.GestionCommandeService;

public class CommandeController extends AbstractController {

	private static final Logger LOG = LoggerFactory.getLogger(CommandeController.class);

	private final CommandeCouranteService commandeCouranteService;

	private final GestionCommandeService gestionCommandeService;

	private final CommandeArchiveeResource commandeArchiveeResource;

	private final CommandeCouranteResource commandeCouranteResource;

	public CommandeController(CommandeCouranteService commandeCouranteService,
							  GestionCommandeService gestionCommandeService,

							  CommandeArchiveeResource commandeArchiveeResource,
							  CommandeCouranteResource commandeCouranteResource
							  ) {
		this.commandeCouranteService = commandeCouranteService;
		this.gestionCommandeService = gestionCommandeService;

		this.commandeArchiveeResource = commandeArchiveeResource;
		this.commandeCouranteResource = commandeCouranteResource;
	}

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

	public Commande creerCommandeCourante() {
		LOG.info("Méthode appelée: creerCommandeCourante");
		return commandeCouranteService.creerCommandeCourante();
	}

	private void ajouterVoiture(Long voitureId) throws SQLException, NotFoundException {
		LOG.info("Méthode appelée: ajouterVoiture, avec paramètre: {}", voitureId);
		commandeCouranteResource.ajouterVoiture(voitureId);
	}

	private void supprimerVoiture(Long voitureId) throws SQLException, NotFoundException {
		LOG.info("Méthode appelée: supprimerVoiture, avec paramètre: {}", voitureId);
		commandeCouranteResource.supprimerVoiture(voitureId);
	}

	private Collection<Voiture> getAllVoitures() {
		LOG.info("Méthode appelée: getAllVoitures");
		return commandeCouranteResource.getAllVoitures();
	}

	private Commande getCommandeCourante() {
		LOG.info("Méthode appelée: getCommandeCourante");
		return commandeCouranteService.getCommandeCourante();
	}

	private long validerCommandeCourante() throws EmptyCommandeException, SQLException, NotFoundException {
		LOG.info("Méthode appelée: validerCommandeCourante");
		return commandeCouranteService.validerCommandeCourante();
	}

	private Collection<Option> getAllOptions() throws SQLException {
		LOG.info("Méthode appelée: getAllOptions");
		return gestionCommandeService.getAllOptions();
	}

	private Commande getCommande(Long id) throws SQLException, NotFoundException {
		LOG.info("Méthode appelée: getCommande, avec paramètre: {}", id);
		return commandeArchiveeResource.getCommande(id);
	}

}
