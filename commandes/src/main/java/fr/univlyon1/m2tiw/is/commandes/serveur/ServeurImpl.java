package fr.univlyon1.m2tiw.is.commandes.serveur;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Map;
import java.util.Objects;

import com.fasterxml.jackson.databind.ObjectMapper;
import fr.univlyon1.m2tiw.is.commandes.config.ApplicationConfiguration;
import org.picocontainer.DefaultPicoContainer;
import org.picocontainer.MutablePicoContainer;
import org.picocontainer.behaviors.Caching;
import org.picocontainer.parameters.ConstantParameter;

import fr.univlyon1.m2tiw.is.commandes.controller.CommandeController;
import fr.univlyon1.m2tiw.is.commandes.controller.OptionController;
import fr.univlyon1.m2tiw.is.commandes.controller.VoitureController;
import fr.univlyon1.m2tiw.is.commandes.dao.CommandeDAO;
import fr.univlyon1.m2tiw.is.commandes.dao.DBAccess;
import fr.univlyon1.m2tiw.is.commandes.dao.NotFoundException;
import fr.univlyon1.m2tiw.is.commandes.dao.OptionDAO;
import fr.univlyon1.m2tiw.is.commandes.dao.VoitureDAO;
import fr.univlyon1.m2tiw.is.commandes.services.EmptyCommandeException;
import fr.univlyon1.m2tiw.is.commandes.services.InvalidConfigurationException;

public class ServeurImpl implements Serveur {

	private final VoitureController voitureController;
	private final OptionController optionController;
	private final CommandeController commandeController;

	private final DBAccess dbAccess;

	public ServeurImpl() throws SQLException, IOException, ClassNotFoundException {
		MutablePicoContainer pico = new DefaultPicoContainer(new Caching());

		var mapper = new ObjectMapper();
		ApplicationConfiguration configuration = mapper.readValue(new File(
				Objects.requireNonNull(ServeurImpl.class.getResource("/configuration.json"))
				.getPath()), ApplicationConfiguration.class);

		for (ApplicationConfiguration.Configuration.Component component : configuration.getConfiguration().getAllComponents()) {
			Class<?> componentClass = Class.forName(component.getClassName());
			if (!component.getParameters().isEmpty()) {
				Map<String, String> params = component.getParameters();
				pico.addComponent(componentClass, componentClass,
						new ConstantParameter(params.get("url")),
						new ConstantParameter(params.get("user")),
						new ConstantParameter(params.get("password"))
				);
			} else if (component.getClassName().contains("Service") || component.getClassName().contains("DAO")) {
				Class<?> componentImplClass = Class.forName(component.getClassName().concat("Impl"));
				pico.addComponent(componentClass, componentImplClass);
			} else {
				pico.addComponent(componentClass);
			}
		}

		voitureController = pico.getComponent(VoitureController.class);
		optionController = pico.getComponent(OptionController.class);
		commandeController = pico.getComponent(CommandeController.class);
		dbAccess = pico.getComponent(DBAccess.class);

		pico.getComponent(CommandeDAO.class).init();
		pico.getComponent(OptionDAO.class).init();
		pico.getComponent(VoitureDAO.class).init();

		pico.start();
	}

	public Object processRequest(String commande, String methode, Map<String, Object> parametres) throws SQLException, EmptyCommandeException, NotFoundException, InvalidConfigurationException {
		switch (commande.toLowerCase()) {
			case "commandecontroller":
				return commandeController.process(methode.toLowerCase(), parametres);
			case "optioncontroller":
				return optionController.process(methode.toLowerCase(), parametres);
			case "voiturecontroller":
				return voitureController.process(methode.toLowerCase(), parametres);
			default:
				return null;
		}
	}

	public DBAccess getConnection() {
		return dbAccess;
	}

	public static void main(String[] args) throws SQLException, IOException, ClassNotFoundException {
		new ServeurImpl();
	}

}
