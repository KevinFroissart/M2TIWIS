package fr.univlyon1.m2tiw.is.commandes.controller;

import java.sql.SQLException;
import java.util.Map;

import org.picocontainer.Startable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.univlyon1.m2tiw.is.commandes.dao.NotFoundException;
import fr.univlyon1.m2tiw.is.commandes.services.EmptyCommandeException;
import fr.univlyon1.m2tiw.is.commandes.services.InvalidConfigurationException;

public abstract class AbstractController implements Controller, Startable {

	private static final Logger LOG = LoggerFactory.getLogger(AbstractController.class);

	@Override
	public void start() {
		LOG.info("Composant Controleur démarré: {}", this);
	}

	@Override
	public void stop() {
		LOG.info("Composant Controleur arrêté: {}", this);
	}

}
