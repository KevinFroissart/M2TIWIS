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

public class CommandeController implements Startable {

    private static final Logger LOG = LoggerFactory.getLogger(CommandeController.class);

    private final CommandeCouranteService commandeCouranteService;

    private final GestionCommandeService gestionCommandeService;

    public CommandeController(CommandeCouranteService commandeCouranteService, GestionCommandeService gestionCommandeService) {
        this.commandeCouranteService = commandeCouranteService;
        this.gestionCommandeService = gestionCommandeService;
    }

    @Override
    public void start() {
        LOG.info("Composant Controleur démarré : %s".formatted(this.toString()));
    }

    @Override
    public void stop() {
        LOG.info("Composant Controleur arrêté : %s".formatted(this.toString()));
    }

    public Commande creerCommandeCourante() {
        return commandeCouranteService.creerCommandeCourante();
    }

    public void ajouterVoiture(Long voitureId) throws SQLException, NotFoundException {
        commandeCouranteService.ajouterVoiture(voitureId);
    }

    public void supprimerVoiture(Long voitureId) throws SQLException, NotFoundException {
        commandeCouranteService.supprimerVoiture(voitureId);
    }

    public Collection<Voiture> getAllVoitures() {
        return commandeCouranteService.getAllVoitures();
    }

    public Commande getCommandeCourante() {
        return commandeCouranteService.getCommandeCourante();
    }

    public long validerCommandeCourante() throws EmptyCommandeException, SQLException, NotFoundException {
        return commandeCouranteService.validerCommandeCourante();
    }

    public Collection<Option> getAllOptions() throws SQLException {
        return gestionCommandeService.getAllOptions();
    }

    public Commande getCommande(Long id) throws SQLException, NotFoundException {
        return gestionCommandeService.getCommande(id);
    }

}
