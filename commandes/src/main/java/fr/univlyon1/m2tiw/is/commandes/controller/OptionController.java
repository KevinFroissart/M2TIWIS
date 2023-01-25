package fr.univlyon1.m2tiw.is.commandes.controller;

import fr.univlyon1.m2tiw.is.commandes.model.Option;
import fr.univlyon1.m2tiw.is.commandes.services.OptionService;
import org.picocontainer.Startable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;
import java.util.Collection;
import java.util.Map;

public class OptionController implements Startable {

    private static final Logger LOG = LoggerFactory.getLogger(OptionController.class);
    private final OptionService optionService;

    public OptionController(OptionService optionService) {
        this.optionService = optionService;
    }

    public Object process(String commande, Map<String, Object> parametres) throws SQLException {
        switch (commande) {
            case "getalloptions":
                return getAllOptions();
            default:
                return null;
        }
    }

    public Collection<Option> getAllOptions() throws SQLException {
        LOG.info("Méthode appelée: getAllOptions");
        return optionService.getAllOptions();
    }

    @Override
    public void start() {
        LOG.info("Composant Controleur démarré: {}", this);
    }

    @Override
    public void stop() {
        LOG.info("Composant Controleur arrêté: {}", this);
    }

}
