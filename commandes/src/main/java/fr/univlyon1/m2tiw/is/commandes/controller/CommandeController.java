package fr.univlyon1.m2tiw.is.commandes.controller;

import fr.univlyon1.m2tiw.is.commandes.dao.NotFoundException;
import fr.univlyon1.m2tiw.is.commandes.model.Commande;
import fr.univlyon1.m2tiw.is.commandes.model.Option;
import fr.univlyon1.m2tiw.is.commandes.model.Voiture;
import fr.univlyon1.m2tiw.is.commandes.services.CommandeCouranteService;
import fr.univlyon1.m2tiw.is.commandes.services.EmptyCommandeException;
import fr.univlyon1.m2tiw.is.commandes.services.GestionCommandeService;
import org.picocontainer.Startable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;
import java.util.Collection;
import java.util.Map;

public class CommandeController implements Startable {

    private static final Logger LOG = LoggerFactory.getLogger(CommandeController.class);

    private final CommandeCouranteService commandeCouranteService;

    private final GestionCommandeService gestionCommandeService;

    public CommandeController(CommandeCouranteService commandeCouranteService, GestionCommandeService gestionCommandeService) {
        this.commandeCouranteService = commandeCouranteService;
        this.gestionCommandeService = gestionCommandeService;
    }

    public Object process(String commande, Map<String, Object> parametres) throws SQLException, NotFoundException, EmptyCommandeException {
        switch (commande) {
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
        commandeCouranteService.ajouterVoiture(voitureId);
    }

    private void supprimerVoiture(Long voitureId) throws SQLException, NotFoundException {
        LOG.info("Méthode appelée: supprimerVoiture, avec paramètre: {}", voitureId);
        commandeCouranteService.supprimerVoiture(voitureId);
    }

    private Collection<Voiture> getAllVoitures() {
        LOG.info("Méthode appelée: getAllVoitures");
        return commandeCouranteService.getAllVoitures();
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
        return gestionCommandeService.getCommande(id);
    }

    @Override
    public void start() {
        LOG.info("Composant Controleur démarré: {}", this);
    }

    @Override
    public void stop() {
        LOG.info("Composant Controleur arrêté: {}", this);
    }

}