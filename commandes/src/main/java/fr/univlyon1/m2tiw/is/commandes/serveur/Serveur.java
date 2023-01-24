package fr.univlyon1.m2tiw.is.commandes.serveur;

import java.sql.SQLException;
import java.util.Collection;

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

public class Serveur {

    private final DBAccess dbAccess;
    private final CommandeDAO commandeDAO;
    private final OptionDAO optionDAO;
    private final VoitureDAO voitureDAO;

    private final CommandeCouranteService commandeCouranteService;
    private final GestionCommandeService gestionCommandeService;
    private final OptionService optionService;
    private final VoitureService voitureService;

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

        VoitureServiceImpl voitureServiceImpl = new VoitureServiceImpl();
        voitureServiceImpl.setVoitureDAO(voitureDAO);
        voitureServiceImpl.setOptionDAO(optionDAO);
        voitureService = voitureServiceImpl;

        CommandeCouranteServiceImpl commandeCouranteServiceImpl = new CommandeCouranteServiceImpl();
        commandeCouranteServiceImpl.setCommandeDAO(commandeDAO);
        commandeCouranteServiceImpl.setVoitureService(voitureService);
        commandeCouranteService = commandeCouranteServiceImpl;

        OptionServiceImpl optionServiceImpl = new OptionServiceImpl();
        optionServiceImpl.setDao(optionDAO);
        optionService = optionServiceImpl;

        GestionCommandeServiceImpl gestionCommandeServiceImpl = new GestionCommandeServiceImpl();
        gestionCommandeServiceImpl.setCommandeCouranteService(commandeCouranteService);
        gestionCommandeServiceImpl.setOptionService(optionService);
        gestionCommandeServiceImpl.setCommandeDAO(commandeDAO);
        gestionCommandeServiceImpl.setVoitureService(voitureService);
        gestionCommandeService = gestionCommandeServiceImpl;
    }

    public Collection<Option> getAllOptions() throws SQLException {
        return optionService.getAllOptions();
    }

    public Voiture creerVoiture(String modele) {
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
