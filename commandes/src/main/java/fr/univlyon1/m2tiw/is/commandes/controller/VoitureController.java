package fr.univlyon1.m2tiw.is.commandes.controller;

import java.sql.SQLException;
import java.util.Collection;

import fr.univlyon1.m2tiw.is.commandes.dao.NotFoundException;
import fr.univlyon1.m2tiw.is.commandes.model.Commande;
import fr.univlyon1.m2tiw.is.commandes.model.Option;
import fr.univlyon1.m2tiw.is.commandes.model.Voiture;
import fr.univlyon1.m2tiw.is.commandes.services.InvalidConfigurationException;
import fr.univlyon1.m2tiw.is.commandes.services.VoitureService;

public class VoitureController {

	private final VoitureService voitureService;

	public VoitureController(VoitureService voitureService) {
		this.voitureService = voitureService;
	}

	public Voiture creerVoiture(String modele) throws SQLException {
		return voitureService.creerVoiture(modele);
	}

	public void ajouterConfiguration(Long voitureId, Option option) throws SQLException, NotFoundException {
		voitureService.ajouterConfiguration(voitureId, option);
	}

	public void supprimerConfiguration(Long voitureId, Option option) throws SQLException, NotFoundException, InvalidConfigurationException {
		voitureService.supprimerConfiguration(voitureId, option);
	}

	public Collection<Option> getOptionsForVoiture(Long voitureId) throws SQLException, NotFoundException {
		return voitureService.getOptionsForVoiture(voitureId);
	}

	public Voiture getVoiture(Long voitureId) throws SQLException, NotFoundException {
		return voitureService.getVoiture(voitureId);
	}

	public void sauverVoiture(Long voitureId, Commande commande) throws SQLException, NotFoundException {
		voitureService.sauverVoiture(voitureId, commande);
	}

	public Collection<Voiture> getVoituresByCommande(Long id) throws SQLException, NotFoundException {
		return voitureService.getVoituresByCommande(id);
	}

	public void supprimerVoiture(Long voitureId) throws SQLException, NotFoundException {
		voitureService.supprimerVoiture(voitureId);
	}

}
