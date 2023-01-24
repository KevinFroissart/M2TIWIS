package fr.univlyon1.m2tiw.is.commandes.services;

import fr.univlyon1.m2tiw.is.commandes.dao.NotFoundException;
import fr.univlyon1.m2tiw.is.commandes.model.Commande;
import fr.univlyon1.m2tiw.is.commandes.model.Option;
import fr.univlyon1.m2tiw.is.commandes.model.Voiture;

import java.sql.SQLException;
import java.util.Collection;

public interface VoitureService {
    public Voiture creerVoiture(String modele);

    public void ajouterConfiguration(Long voitureId, Option option) throws SQLException, NotFoundException;
    public void supprimerConfiguration(Long voitureId, Option option) throws InvalidConfigurationException, SQLException, NotFoundException;
    public Collection<Option> getOptionsForVoiture(Long voitureId) throws SQLException, NotFoundException;

    public Voiture getVoiture(Long voitureId) throws SQLException, NotFoundException;

    public void sauverVoiture(Long voitureId, Commande commande) throws SQLException, NotFoundException;

    Collection<Voiture> getVoituresByCommande(Long id) throws SQLException, NotFoundException;
}
