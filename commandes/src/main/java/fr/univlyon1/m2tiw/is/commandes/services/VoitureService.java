package fr.univlyon1.m2tiw.is.commandes.services;

import java.sql.SQLException;
import java.util.Collection;

import fr.univlyon1.m2tiw.is.commandes.dao.NotFoundException;
import fr.univlyon1.m2tiw.is.commandes.model.Option;
import fr.univlyon1.m2tiw.is.commandes.model.Voiture;

public interface VoitureService {

	void ajouterConfiguration(Long voitureId, Option option) throws SQLException, NotFoundException;

	void supprimerConfiguration(Long voitureId, Option option) throws InvalidConfigurationException, SQLException, NotFoundException;

	Collection<Option> getOptionsForVoiture(Long voitureId) throws SQLException, NotFoundException;

	Collection<Voiture> getVoituresByCommande(Long id) throws SQLException, NotFoundException;

}
