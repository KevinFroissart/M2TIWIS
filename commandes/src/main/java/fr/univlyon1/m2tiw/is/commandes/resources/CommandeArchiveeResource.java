package fr.univlyon1.m2tiw.is.commandes.resources;

import fr.univlyon1.m2tiw.is.commandes.dao.CommandeDAO;
import fr.univlyon1.m2tiw.is.commandes.dao.NotFoundException;
import fr.univlyon1.m2tiw.is.commandes.model.Commande;
import fr.univlyon1.m2tiw.is.commandes.services.VoitureService;

import java.sql.SQLException;

/**
 * Ressource pour les commandes archivées.
 */
public class CommandeArchiveeResource {

    private final CommandeDAO commandeDAO;

    private final VoitureService voitureService;

    private final CommandeCouranteResource commandeCouranteResource;

    public CommandeArchiveeResource(CommandeDAO commandeDAO, VoitureService voitureService, CommandeCouranteResource commandeCouranteResource) {
        this.commandeDAO = commandeDAO;
        this.voitureService = voitureService;
        this.commandeCouranteResource = commandeCouranteResource;
    }

    /**
     * Retourne une commande pour un id donné.
     *
     * @param id l'id de la commande.
     * @return la {@link Commande}
     * @throws SQLException pour une exception SQL.
     * @throws NotFoundException pour une commande non trouvée.
     */
    public Commande getCommande(Long id) throws SQLException, NotFoundException {
        var commande = commandeDAO.getCommande(id);
        commande.getVoitures().addAll(voitureService.getVoituresByCommande(id));
        return commande;
    }

    /**
     * @InheritDoc
     * @see CommandeCouranteResource#getCommandeCourante()
     */
    public Commande getCommandeCourante() {
        return commandeCouranteResource.getCommandeCourante();
    }

}
