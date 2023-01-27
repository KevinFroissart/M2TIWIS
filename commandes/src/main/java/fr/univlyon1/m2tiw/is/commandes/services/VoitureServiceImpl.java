package fr.univlyon1.m2tiw.is.commandes.services;

import java.sql.SQLException;
import java.util.Collection;

import fr.univlyon1.m2tiw.is.commandes.dao.NotFoundException;
import fr.univlyon1.m2tiw.is.commandes.dao.OptionDAO;
import fr.univlyon1.m2tiw.is.commandes.dao.VoitureDAO;
import fr.univlyon1.m2tiw.is.commandes.model.Commande;
import fr.univlyon1.m2tiw.is.commandes.model.Option;
import fr.univlyon1.m2tiw.is.commandes.model.Voiture;

public class VoitureServiceImpl implements VoitureService {

	private final VoitureDAO voitureDAO;
	private final OptionDAO optionDAO;

	public VoitureServiceImpl(VoitureDAO voitureDAO, OptionDAO optionDAO) throws SQLException {
		this.voitureDAO = voitureDAO;
		this.optionDAO = optionDAO;
		this.voitureDAO.init();
		this.optionDAO.init();
	}

	@Override
	public void ajouterConfiguration(Long voitureId, Option option) throws SQLException, NotFoundException {
		Voiture voiture = voitureDAO.getVoitureById(voitureId);
		voiture.addOption(option);
		optionDAO.setOptionVoiture(voitureId, option);
	}

	@Override
	public void supprimerConfiguration(Long voitureId, Option option) throws SQLException, NotFoundException {
		Voiture voiture = voitureDAO.getVoitureById(voitureId);
		if (voiture.hasOption(option)) {
			voiture.deleteOption(option);
		}
		optionDAO.deleteOptionVoiture(voitureId, option.getNom());
	}

	@Override
	public Collection<Option> getOptionsForVoiture(Long voitureId) throws SQLException, NotFoundException {
		return optionDAO.getOptionsForVoiture(voitureId);
	}

	@Override
	public Collection<Voiture> getVoituresByCommande(Long id) throws SQLException, NotFoundException {
		Collection<Voiture> voitures = voitureDAO.getVoituresByCommande(id);
		for (Voiture voiture : voitures) {
			for (Option option : optionDAO.getOptionsForVoiture(voiture.getId())) {
				voiture.addOption(option);
			}
		}
		return voitures;
	}

}
