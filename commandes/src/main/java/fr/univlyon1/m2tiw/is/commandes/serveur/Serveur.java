package fr.univlyon1.m2tiw.is.commandes.serveur;

import java.sql.SQLException;
import java.util.Collection;

import org.picocontainer.DefaultPicoContainer;
import org.picocontainer.MutablePicoContainer;
import org.picocontainer.PicoContainer;

import fr.univlyon1.m2tiw.is.commandes.controller.CommandeController;
import fr.univlyon1.m2tiw.is.commandes.controller.OptionController;
import fr.univlyon1.m2tiw.is.commandes.controller.VoitureController;
import fr.univlyon1.m2tiw.is.commandes.dao.CommandeDAO;
import fr.univlyon1.m2tiw.is.commandes.dao.CommandeDAOImpl;
import fr.univlyon1.m2tiw.is.commandes.dao.NotFoundException;
import fr.univlyon1.m2tiw.is.commandes.dao.OptionDAO;
import fr.univlyon1.m2tiw.is.commandes.dao.OptionDAOImpl;
import fr.univlyon1.m2tiw.is.commandes.dao.VoitureDAO;
import fr.univlyon1.m2tiw.is.commandes.dao.VoitureDAOImpl;
import fr.univlyon1.m2tiw.is.commandes.model.Commande;
import fr.univlyon1.m2tiw.is.commandes.model.Option;
import fr.univlyon1.m2tiw.is.commandes.model.Voiture;
import fr.univlyon1.m2tiw.is.commandes.services.CommandeCouranteService;
import fr.univlyon1.m2tiw.is.commandes.services.DBAccess;
import fr.univlyon1.m2tiw.is.commandes.services.EmptyCommandeException;
import fr.univlyon1.m2tiw.is.commandes.services.GestionCommandeService;
import fr.univlyon1.m2tiw.is.commandes.services.InvalidConfigurationException;
import fr.univlyon1.m2tiw.is.commandes.services.OptionService;
import fr.univlyon1.m2tiw.is.commandes.services.VoitureService;

public class Serveur {

	private final DBAccess dbAccess;
	private final CommandeDAO commandeDAO;
	private final OptionDAO optionDAO;
	private final VoitureDAO voitureDAO;

	private static MutablePicoContainer pico;

	public Serveur() throws SQLException {
		dbAccess = new DBAccess();

		CommandeDAOImpl commandeDaoImpl = new CommandeDAOImpl();
		commandeDaoImpl.setDbAccess(dbAccess);
		commandeDaoImpl.init();
		commandeDAO = commandeDaoImpl;

		OptionDAOImpl optionDaoImpl = new OptionDAOImpl();
		optionDaoImpl.setDbAccess(dbAccess);
		optionDaoImpl.init();
		optionDAO = optionDaoImpl;

		VoitureDAOImpl voitureDaoImpl = new VoitureDAOImpl();
		voitureDaoImpl.setDbAccess(dbAccess);
		voitureDaoImpl.init();
		voitureDAO = voitureDaoImpl;
	}

	public static PicoContainer context() {
		if (pico == null) initialize();
		return pico;
	}

	private static void initialize() {
		pico = new DefaultPicoContainer();
		pico.addComponent(VoitureDAO.class);
		pico.addComponent(OptionDAO.class);
		pico.addComponent(CommandeDAO.class);
		pico.addComponent(VoitureService.class);
		pico.addComponent(OptionService.class);
		pico.addComponent(GestionCommandeService.class);
		pico.addComponent(CommandeCouranteService.class);
		pico.addComponent(VoitureController.class);
		pico.addComponent(OptionController.class);
		pico.addComponent(CommandeController.class);
	}

	public Collection<Option> getAllOptions() throws SQLException {
		return optionService.getAllOptions();
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

	public void ajouterVoiture(Long voitureId) throws SQLException, NotFoundException {
		commandeCouranteService.ajouterVoiture(voitureId);
	}

	public void supprimerVoiture(Long voitureId) throws SQLException, NotFoundException {
		commandeCouranteService.supprimerVoiture(voitureId);
	}

	public long validerCommandeCourante() throws EmptyCommandeException, SQLException, NotFoundException {
		return commandeCouranteService.validerCommandeCourante();
	}

	public Commande getCommande(Long id) throws SQLException, NotFoundException {
		return gestionCommandeService.getCommande(id);
	}
}
