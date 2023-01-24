package fr.univlyon1.m2tiw.is.commandes.services;

import fr.univlyon1.m2tiw.is.commandes.dao.*;
import fr.univlyon1.m2tiw.is.commandes.model.Commande;
import fr.univlyon1.m2tiw.is.commandes.model.Option;
import fr.univlyon1.m2tiw.is.commandes.model.Voiture;

import java.sql.SQLException;
import java.util.Collection;

public class VoitureServiceImpl implements VoitureService {
    private static Long currentId = 0L;
    private VoitureDAO voitureDAO;
    private OptionDAO optionDAO;

    public VoitureServiceImpl() {
    }

    @Override
    public Voiture creerVoiture(String modele) {
        return new Voiture(currentId++, modele);
    }

    @Override
    public void ajouterConfiguration(Long voitureId, Option option) throws SQLException, NotFoundException {
        Voiture voiture = voitureDAO.getVoitureById(voitureId);
        voiture.addOption(option);
        optionDAO.setOptionVoiture( voitureId,  option);
    }

    @Override
    public void supprimerConfiguration(Long voitureId, Option option) throws SQLException, NotFoundException {
        Voiture voiture = voitureDAO.getVoitureById(voitureId);
        if(voiture.hasOption(option)) {
            voiture.deleteOption(option);
        }
        optionDAO.deleteOptionVoiture(voitureId, option.getNom());
    }

    @Override
    public Collection<Option> getOptionsForVoiture(Long voitureId) throws SQLException, NotFoundException {
        return optionDAO.getOptionsForVoiture(voitureId);
    }

    @Override
    public Voiture getVoiture(Long voitureId) throws SQLException, NotFoundException {
        Voiture voiture = voitureDAO.getVoitureById(voitureId);
        for(Option option : optionDAO.getOptionsForVoiture(voitureId)) {
            voiture.addOption(option);
        }
        return voiture;
    }

    @Override
    public void sauverVoiture(Long voitureId, Commande commande) throws SQLException, NotFoundException {
        voitureDAO.updateVoitureCommande(voitureId, commande.getId());
    }

    @Override
    public Collection<Voiture> getVoituresByCommande(Long id) throws SQLException, NotFoundException {
        Collection<Voiture> voitures = voitureDAO.getVoituresByCommande(id);
        for (Voiture voiture: voitures) {
            for(Option option : optionDAO.getOptionsForVoiture(voiture.getId())) {
                voiture.addOption(option);
            }
        }
        return voitures;
    }

    public void setVoitureDAO(VoitureDAO voitureDAO) {
        this.voitureDAO = voitureDAO;
    }

    public void setOptionDAO(OptionDAO optionDAO) {
        this.optionDAO = optionDAO;
    }
}
