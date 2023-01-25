package fr.univlyon1.m2tiw.is.commandes.controller;

import fr.univlyon1.m2tiw.is.commandes.model.Option;
import fr.univlyon1.m2tiw.is.commandes.services.OptionService;
import org.picocontainer.Startable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;
import java.util.Collection;

public class OptionController implements Startable {

    private static final Logger LOG = LoggerFactory.getLogger(OptionController.class);
    private final OptionService optionService;

    public OptionController(OptionService optionService) {
        this.optionService = optionService;
    }

    @Override
    public void start() {
        LOG.info("Composant Controleur démarré : %s".formatted(this.toString()));
    }

    @Override
    public void stop() {
        LOG.info("Composant Controleur arrêté : %s".formatted(this.toString()));
    }

    public Collection<Option> getAllOptions() throws SQLException {
        return optionService.getAllOptions();
    }

}
