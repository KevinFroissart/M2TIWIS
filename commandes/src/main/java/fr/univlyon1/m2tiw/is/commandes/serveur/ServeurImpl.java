package fr.univlyon1.m2tiw.is.commandes.serveur;

import java.sql.SQLException;
import java.util.Map;

import fr.univlyon1.m2tiw.is.commandes.resource.CommandeArchiveeResource;
import fr.univlyon1.m2tiw.is.commandes.resource.CommandeCouranteResource;
import fr.univlyon1.m2tiw.is.commandes.resource.VoitureResource;
import org.picocontainer.DefaultPicoContainer;
import org.picocontainer.MutablePicoContainer;
import org.picocontainer.behaviors.Caching;
import org.picocontainer.parameters.ComponentParameter;

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
import fr.univlyon1.m2tiw.is.commandes.services.CommandeCouranteService;
import fr.univlyon1.m2tiw.is.commandes.services.CommandeCouranteServiceImpl;
import fr.univlyon1.m2tiw.is.commandes.services.DBAccess;
import fr.univlyon1.m2tiw.is.commandes.services.EmptyCommandeException;
import fr.univlyon1.m2tiw.is.commandes.services.GestionCommandeService;
import fr.univlyon1.m2tiw.is.commandes.services.GestionCommandeServiceImpl;
import fr.univlyon1.m2tiw.is.commandes.services.InvalidConfigurationException;
import fr.univlyon1.m2tiw.is.commandes.services.OptionService;
import fr.univlyon1.m2tiw.is.commandes.services.OptionServiceImpl;
import fr.univlyon1.m2tiw.is.commandes.services.VoitureService;
import fr.univlyon1.m2tiw.is.commandes.services.VoitureServiceImpl;

public class ServeurImpl implements Serveur {

	private final VoitureController voitureController;
	private final OptionController optionController;
	private final CommandeController commandeController;

	public ServeurImpl() throws SQLException {
		MutablePicoContainer pico = new DefaultPicoContainer(new Caching());
		pico.addComponent(DBAccess.class);
		pico.addComponent(CommandeDAO.class, CommandeDAOImpl.class);
		pico.addComponent(OptionDAO.class, OptionDAOImpl.class);
		pico.addComponent(VoitureDAO.class, VoitureDAOImpl.class);

		pico.addComponent(VoitureResource.class,
				new ComponentParameter(VoitureDAO.class),
				new ComponentParameter(OptionDAO.class)
		);
		pico.addComponent(VoitureService.class, VoitureServiceImpl.class,
				new ComponentParameter(VoitureDAO.class),
				new ComponentParameter(OptionDAO.class)
		);

		pico.addComponent(OptionService.class, OptionServiceImpl.class,
				new ComponentParameter(OptionDAO.class)
		);

		pico.addComponent(GestionCommandeService.class, GestionCommandeServiceImpl.class,
				new ComponentParameter(OptionService.class)
		);
		pico.addComponent(CommandeCouranteService.class, CommandeCouranteServiceImpl.class,
				new ComponentParameter(CommandeDAO.class),
				new ComponentParameter(VoitureResource.class)
		);
		pico.addComponent(CommandeArchiveeResource.class,
				new ComponentParameter(CommandeDAO.class),
				new ComponentParameter(VoitureService.class),
				new ComponentParameter(CommandeCouranteService.class)
		);
		pico.addComponent(CommandeCouranteResource.class,
				new ComponentParameter(CommandeCouranteService.class),
				new ComponentParameter(VoitureResource.class)
		);

		pico.addComponent(VoitureController.class);
		pico.addComponent(OptionController.class);
		pico.addComponent(CommandeController.class);

		voitureController = pico.getComponent(VoitureController.class);
		optionController = pico.getComponent(OptionController.class);
		commandeController = pico.getComponent(CommandeController.class);

		pico.getComponent(CommandeDAO.class).init();
		pico.getComponent(OptionDAO.class).init();
		pico.getComponent(VoitureDAO.class).init();

		pico.start();
	}

	public Object processRequest(String commande, String methode, Map<String, Object> parametres) throws SQLException, EmptyCommandeException, NotFoundException, InvalidConfigurationException {
		switch (commande) {
			case "commandecontroller":
				return commandeController.process(methode, parametres);
			case "optioncontroller":
				return optionController.process(methode, parametres);
			case "voiturecontroller":
				return voitureController.process(methode, parametres);
			default:
				return null;
		}
	}

	public static void main(String[] args) throws SQLException {
		new ServeurImpl();
	}

}

