package fr.univlyon1.m2tiw.is.commandes.services;

import java.sql.SQLException;
import java.util.Collection;

import fr.univlyon1.m2tiw.is.commandes.dao.CommandeDAO;
import fr.univlyon1.m2tiw.is.commandes.dao.NotFoundException;
import fr.univlyon1.m2tiw.is.commandes.model.Commande;
import fr.univlyon1.m2tiw.is.commandes.model.Option;

public class GestionCommandeServiceImpl implements GestionCommandeService {

	private final OptionService optionService;
	private final VoitureService voitureService;
	private final CommandeCouranteService commandeCouranteService;
	private final CommandeDAO commandeDAO;

	public GestionCommandeServiceImpl(OptionService optionService, VoitureService voitureService, CommandeCouranteService commandeCouranteService, CommandeDAO commandeDAO) {
		this.optionService = optionService;
		this.voitureService = voitureService;
		this.commandeCouranteService = commandeCouranteService;
		this.commandeDAO = commandeDAO;
	}

	@Override
	public Collection<Option> getAllOptions() throws SQLException {
		return this.optionService.getAllOptions();
	}

	@Override
	public Commande getCommande(Long id) throws SQLException, NotFoundException {
		Commande commande = commandeDAO.getCommande(id);
		commande.getVoitures().addAll(voitureService.getVoituresByCommande(id));
		return commande;
	}

	@Override
	public Commande getCommandeCourante() {
		return commandeCouranteService.getCommandeCourante();
	}
}
