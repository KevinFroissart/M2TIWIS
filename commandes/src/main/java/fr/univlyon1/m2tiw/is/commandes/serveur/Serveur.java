package fr.univlyon1.m2tiw.is.commandes.serveur;

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
import org.picocontainer.DefaultPicoContainer;
import org.picocontainer.MutablePicoContainer;
import org.picocontainer.behaviors.Caching;
import org.picocontainer.parameters.ComponentParameter;

import java.sql.SQLException;
import java.util.Collection;

public class Serveur {

    private final VoitureController voitureController;
    private final OptionController optionController;
    private final CommandeController commandeController;


    public Serveur() {
        MutablePicoContainer pico = new DefaultPicoContainer(new Caching());
        pico.addComponent(CommandeDAO.class, CommandeDAOImpl.class);
        pico.addComponent(OptionDAO.class, OptionDAOImpl.class);
        pico.addComponent(VoitureDAO.class, VoitureDAOImpl.class);

        pico.addComponent(VoitureService.class, VoitureServiceImpl.class,
                new ComponentParameter(VoitureDAO.class),
                new ComponentParameter(OptionDAO.class)
        );
        pico.addComponent(OptionService.class, OptionServiceImpl.class,
                new ComponentParameter(OptionDAO.class)
        );
        pico.addComponent(GestionCommandeService.class, GestionCommandeServiceImpl.class,
                new ComponentParameter(OptionService.class),
                new ComponentParameter(VoitureService.class),
                new ComponentParameter(CommandeCouranteService.class),
                new ComponentParameter(CommandeDAO.class));
        pico.addComponent(CommandeCouranteService.class, CommandeCouranteServiceImpl.class,
                new ComponentParameter(VoitureService.class),
                new ComponentParameter(CommandeDAO.class));
        pico.addComponent(DBAccess.class);

        pico.addComponent(VoitureController.class, new ComponentParameter(VoitureService.class));
        pico.addComponent(OptionController.class, new ComponentParameter(OptionService.class));
        pico.addComponent(CommandeController.class,
                new ComponentParameter(GestionCommandeService.class),
                new ComponentParameter(CommandeCouranteService.class)
        );

        voitureController = pico.getComponent(VoitureController.class);
        optionController = pico.getComponent(OptionController.class);
        commandeController = pico.getComponent(CommandeController.class);
    }

    public Collection<Option> getAllOptions() throws SQLException {
        return optionController.getAllOptions();
    }

    public Voiture creerVoiture(String modele) throws SQLException {
        return voitureController.creerVoiture(modele);
    }

    public void ajouterConfiguration(Long voitureId, Option option) throws SQLException, NotFoundException {
        voitureController.ajouterConfiguration(voitureId, option);
    }

    public void supprimerConfiguration(Long voitureId, Option option) throws SQLException, NotFoundException, InvalidConfigurationException {
        voitureController.supprimerConfiguration(voitureId, option);
    }

    public void ajouterVoiture(Long voitureId) throws SQLException, NotFoundException {
        commandeController.ajouterVoiture(voitureId);
    }

    public void supprimerVoiture(Long voitureId) throws SQLException, NotFoundException {
        commandeController.supprimerVoiture(voitureId);
    }

    public long validerCommandeCourante() throws EmptyCommandeException, SQLException, NotFoundException {
        return commandeController.validerCommandeCourante();
    }

    public Commande getCommande(Long id) throws SQLException, NotFoundException {
        return commandeController.getCommande(id);
    }
}
