package fr.univlyon1.m2tiw.is.commandes.resources;

import fr.univlyon1.m2tiw.is.commandes.dao.NotFoundException;
import fr.univlyon1.m2tiw.is.commandes.model.Commande;
import fr.univlyon1.m2tiw.is.commandes.model.Voiture;
import fr.univlyon1.m2tiw.is.commandes.services.CommandeCouranteService;

import java.sql.SQLException;
import java.util.Collection;

/**
 * Ressource pour la commande courante.
 */
public class CommandeCouranteResource {

    private Commande commandeCourante;

    private final CommandeCouranteService commandeCouranteService;

    private final VoitureResource voitureResource;

    public CommandeCouranteResource(CommandeCouranteService commandeCouranteService, VoitureResource voitureResource) {
        this.commandeCouranteService = commandeCouranteService;
        this.voitureResource = voitureResource;
    }

    private void getCommandeCourante() {
        this.commandeCourante = commandeCouranteService.getCommandeCourante();
    }

    /**
     * Retourne les voitures de la commande courante.
     *
     * @return une {@link Collection<Voiture>}.
     */
    public Collection<Voiture> getAllVoitures() {
        getCommandeCourante();
        return commandeCourante.getVoitures();
    }

    /**
     * Ajoute une voiture à la commande courante.
     *
     * @param voitureId l'id de la {@link Voiture}.
     * @throws SQLException pour une exception SQL.
     * @throws NotFoundException pour une voiture non trouvée.
     */
    public void ajouterVoiture(Long voitureId) throws SQLException, NotFoundException {
        getCommandeCourante();
        this.commandeCourante.addVoiture(voitureResource.getVoiture(voitureId));
    }

    /**
     * Supprime une voiture à la commande courante.
     *
     * @param voitureId l'id de la {@link Voiture}.
     * @throws SQLException pour une exception SQL.
     * @throws NotFoundException pour une voiture non trouvée.
     */
    public void supprimerVoiture(Long voitureId) throws SQLException, NotFoundException {
        getCommandeCourante();
        this.commandeCourante.removeVoiture(voitureResource.getVoiture(voitureId));
        this.voitureResource.supprimerVoiture(voitureId);
    }

}
