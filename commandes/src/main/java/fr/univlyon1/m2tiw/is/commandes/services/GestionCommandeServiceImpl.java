package fr.univlyon1.m2tiw.is.commandes.services;

import java.sql.SQLException;
import java.util.Collection;

import fr.univlyon1.m2tiw.is.commandes.dao.CommandeDAO;
import fr.univlyon1.m2tiw.is.commandes.model.Commande;
import fr.univlyon1.m2tiw.is.commandes.model.Option;

public class GestionCommandeServiceImpl implements GestionCommandeService {

	private final OptionService optionService;
	private final CommandeCouranteService commandeCouranteService;
	private final CommandeDAO commandeDAO;

	public GestionCommandeServiceImpl(OptionService optionService,
									  CommandeCouranteService commandeCouranteService,
									  CommandeDAO commandeDAO) throws SQLException {
		this.optionService = optionService;
		this.commandeCouranteService = commandeCouranteService;
		this.commandeDAO = commandeDAO;
		this.commandeDAO.init();
	}

	@Override
	public Collection<Option> getAllOptions() throws SQLException {
		return this.optionService.getAllOptions();
	}

	@Override
	public Commande getCommandeCourante() {
		return commandeCouranteService.getCommandeCourante();
	}
}
