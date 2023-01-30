package fr.univlyon1.m2tiw.is.commandes.controller;

import org.picocontainer.Startable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
