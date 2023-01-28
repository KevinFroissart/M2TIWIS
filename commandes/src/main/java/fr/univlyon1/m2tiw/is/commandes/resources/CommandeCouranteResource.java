package fr.univlyon1.m2tiw.is.commandes.resources;

import fr.univlyon1.m2tiw.is.commandes.dao.NotFoundException;
import fr.univlyon1.m2tiw.is.commandes.model.Commande;
import fr.univlyon1.m2tiw.is.commandes.model.Voiture;
import fr.univlyon1.m2tiw.is.commandes.services.CommandeCouranteService;

import java.sql.SQLException;
import java.util.Collection;

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

    public Collection<Voiture> getAllVoitures() {
        getCommandeCourante();
        return commandeCourante.getVoitures();
    }

    public void ajouterVoiture(Long voitureId) throws SQLException, NotFoundException {
        this.commandeCourante.addVoiture(voitureResource.getVoiture(voitureId));
    }

    public void supprimerVoiture(Long voitureId) throws SQLException, NotFoundException {
        this.commandeCourante.removeVoiture(voitureResource.getVoiture(voitureId));
        this.voitureResource.supprimerVoiture(voitureId);
    }

}
