package fr.univlyon1.m2tiw.is.commandes.resource;

import fr.univlyon1.m2tiw.is.commandes.dao.CommandeDAO;
import fr.univlyon1.m2tiw.is.commandes.dao.NotFoundException;
import fr.univlyon1.m2tiw.is.commandes.model.Commande;
import fr.univlyon1.m2tiw.is.commandes.services.VoitureService;

import java.sql.SQLException;

public class CommandeArchiveeResource {

    private final CommandeDAO commandeDAO;

    private final VoitureService voitureService;

    public CommandeArchiveeResource(CommandeDAO commandeDAO, VoitureService voitureService) {
        this.commandeDAO = commandeDAO;
        this.voitureService = voitureService;
    }

    public Commande getCommande(Long id) throws SQLException, NotFoundException {
        Commande commande = commandeDAO.getCommande(id);
        commande.getVoitures().addAll(voitureService.getVoituresByCommande(id));
        return commande;
    }

}
