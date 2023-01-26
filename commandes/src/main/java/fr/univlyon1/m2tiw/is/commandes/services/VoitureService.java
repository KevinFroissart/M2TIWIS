package fr.univlyon1.m2tiw.is.commandes.services;

import java.sql.SQLException;
import java.util.Collection;

import fr.univlyon1.m2tiw.is.commandes.dao.NotFoundException;
import fr.univlyon1.m2tiw.is.commandes.model.Commande;
import fr.univlyon1.m2tiw.is.commandes.model.Option;
import fr.univlyon1.m2tiw.is.commandes.model.Voiture;

public interface VoitureService {

	Voiture creerVoiture(String modele) throws SQLException;

	void ajouterConfiguration(Long voitureId, Option option) throws SQLException, NotFoundException;

	void supprimerConfiguration(Long voitureId, Option option) throws InvalidConfigurationException, SQLException, NotFoundException;

	Collection<Option> getOptionsForVoiture(Long voitureId) throws SQLException, NotFoundException;

	Voiture getVoiture(Long voitureId) throws SQLException, NotFoundException;

	void sauverVoiture(Long voitureId, Commande commande) throws SQLException, NotFoundException;

	Voiture saveVoiture(Voiture voiture, long commandeId) throws SQLException;

	Collection<Voiture> getVoituresByCommande(Long id) throws SQLException, NotFoundException;

	void supprimerVoiture(Long voitureId) throws SQLException, NotFoundException;

	void deleteVoiture(Voiture voiture) throws SQLException, NotFoundException;
	
}
