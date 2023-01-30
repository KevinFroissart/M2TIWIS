package fr.univlyon1.m2tiw.is.commandes.resources;

import fr.univlyon1.m2tiw.is.commandes.dao.CommandeDAO;
import fr.univlyon1.m2tiw.is.commandes.dao.NotFoundException;
import fr.univlyon1.m2tiw.is.commandes.model.Commande;
import fr.univlyon1.m2tiw.is.commandes.services.CommandeCouranteService;
import fr.univlyon1.m2tiw.is.commandes.services.VoitureService;

import java.sql.SQLException;

public class CommandeArchiveeResource {

    private final CommandeDAO commandeDAO;

    private final VoitureService voitureService;

    private final CommandeCouranteService commandeCouranteService;

    public CommandeArchiveeResource(CommandeDAO commandeDAO, VoitureService voitureService, CommandeCouranteService commandeCouranteService) {
        this.commandeDAO = commandeDAO;
        this.voitureService = voitureService;
        this.commandeCouranteService = commandeCouranteService;
    }

    public Commande getCommande(Long id) throws SQLException, NotFoundException {
        var commande = commandeDAO.getCommande(id);
        commande.getVoitures().addAll(voitureService.getVoituresByCommande(id));
        return commande;
    }

    public Commande getCommandeCourante() {
        return commandeCouranteService.getCommandeCourante();
    }

}
