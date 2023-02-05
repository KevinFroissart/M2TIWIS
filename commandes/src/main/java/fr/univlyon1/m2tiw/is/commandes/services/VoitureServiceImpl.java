package fr.univlyon1.m2tiw.is.commandes.services;

import java.sql.SQLException;
import java.util.Collection;

import fr.univlyon1.m2tiw.is.commandes.dao.NotFoundException;
import fr.univlyon1.m2tiw.is.commandes.dao.OptionDAO;
import fr.univlyon1.m2tiw.is.commandes.dao.VoitureDAO;
import fr.univlyon1.m2tiw.is.commandes.model.Option;
import fr.univlyon1.m2tiw.is.commandes.model.Voiture;
//import fr.univlyon1.m2tiw.tiw1.annotations.Service;

/**
 * Implémentation de {@link VoitureService}.
 */
//@Service
public class VoitureServiceImpl implements VoitureService {

	private final VoitureDAO voitureDAO;
	private final OptionDAO optionDAO;

	public VoitureServiceImpl(VoitureDAO voitureDAO, OptionDAO optionDAO) {
		this.voitureDAO = voitureDAO;
		this.optionDAO = optionDAO;
	}

	/**
	 * @inheritDoc
	 * @see VoitureService#ajouterConfiguration(Long, Option)
	 */
	@Override
	public void ajouterConfiguration(Long voitureId, Option option) throws SQLException, NotFoundException {
		var voiture = voitureDAO.getVoitureById(voitureId);
		voiture.addOption(option);
		optionDAO.setOptionVoiture(voitureId, option);
	}

	/**
	 * @inheritDoc
	 * @see VoitureService#supprimerConfiguration(Long, Option)
	 */
	@Override
	public void supprimerConfiguration(Long voitureId, Option option) throws SQLException {
		//Actuellement `voitureDAO.getVoitureById` ne charge pas les options de la voiture, donc on ne peut pas vérifier si l'option est présente ou non.
		optionDAO.deleteOptionVoiture(voitureId, option.getNom());
	}

	/**
	 * @inheritDoc
	 * @see VoitureService#getOptionsForVoiture(Long)
	 */
	@Override
	public Collection<Option> getOptionsForVoiture(Long voitureId) throws SQLException {
		return optionDAO.getOptionsForVoiture(voitureId);
	}

	/**
	 * @inheritDoc
	 * @see VoitureService#getVoituresByCommande(Long)
	 */
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
