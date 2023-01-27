package fr.univlyon1.m2tiw.is.commandes.resource;

import fr.univlyon1.m2tiw.is.commandes.dao.NotFoundException;
import fr.univlyon1.m2tiw.is.commandes.dao.OptionDAO;
import fr.univlyon1.m2tiw.is.commandes.dao.VoitureDAO;
import fr.univlyon1.m2tiw.is.commandes.model.Commande;
import fr.univlyon1.m2tiw.is.commandes.model.Option;
import fr.univlyon1.m2tiw.is.commandes.model.Voiture;

import java.sql.SQLException;

public class VoitureResource {

    private final VoitureDAO voitureDAO;

    private final OptionDAO optionDAO;

    public VoitureResource(VoitureDAO voitureDAO, OptionDAO optionDAO) {
        this.voitureDAO = voitureDAO;
        this.optionDAO = optionDAO;
    }

    public Voiture creerVoiture(String modele) throws SQLException {
        return voitureDAO.saveVoiture(new Voiture(modele));
    }

    public Voiture getVoiture(Long voitureId) throws SQLException, NotFoundException {
        Voiture voiture = voitureDAO.getVoitureById(voitureId);
        for (Option option : optionDAO.getOptionsForVoiture(voitureId)) {
            voiture.addOption(option);
        }
        return voiture;
    }

    public void sauverVoiture(Long voitureId, Commande commande) throws SQLException, NotFoundException {
        voitureDAO.updateVoitureCommande(voitureId, commande.getId());
    }

    public void supprimerVoiture(Long voitureId) throws SQLException, NotFoundException {
        voitureDAO.deleteVoiture(voitureDAO.getVoitureById(voitureId));
    }

}
