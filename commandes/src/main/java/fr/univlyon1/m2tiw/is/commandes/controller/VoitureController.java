package fr.univlyon1.m2tiw.is.commandes.controller;

import fr.univlyon1.m2tiw.is.commandes.dao.NotFoundException;
import fr.univlyon1.m2tiw.is.commandes.model.Commande;
import fr.univlyon1.m2tiw.is.commandes.model.Option;
import fr.univlyon1.m2tiw.is.commandes.model.Voiture;
import fr.univlyon1.m2tiw.is.commandes.services.InvalidConfigurationException;
import fr.univlyon1.m2tiw.is.commandes.services.VoitureService;
import org.picocontainer.Startable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;
import java.util.Collection;
import java.util.Map;

public class VoitureController implements Startable {

    private static final Logger LOG = LoggerFactory.getLogger(VoitureController.class);

    private final VoitureService voitureService;

    public VoitureController(VoitureService voitureService) {
        this.voitureService = voitureService;
    }

    public Object process(String commande, Map<String, Object> parametres) throws SQLException, NotFoundException, InvalidConfigurationException {
        switch (commande) {
            case "creervoiture":
                return creerVoiture((String) parametres.get("modele"));
            case "ajouterconfiguration":
                ajouterConfiguration((Long) parametres.get("voitureId"), (Option) parametres.get("option"));
                return null;
            case "supprimerconfiguration":
                supprimerConfiguration((Long) parametres.get("voitureId"), (Option) parametres.get("option"));
                return null;
            case "getoptionsforvoiture":
                return getOptionsForVoiture((Long) parametres.get("voitureId"));
            case "getvoiture":
                return getVoiture((Long) parametres.get("voitureId"));
            case "sauvervoiture":
                sauverVoiture((Long) parametres.get("voitureId"), (Commande) parametres.get("commade"));
                return null;
            case "getvoituresbycommande":
                return getVoituresByCommande((Long) parametres.get("id"));
            case "supprimervoiture":
                supprimerVoiture((Long) parametres.get("voitureId"));
                return null;
            default:
                return null;
        }
    }

    public Voiture creerVoiture(String modele) throws SQLException {
        LOG.info("Méthode appelée: creerVoiture, avec comme paramètre(s): {}", modele);
        return voitureService.creerVoiture(modele);
    }

    public void ajouterConfiguration(Long voitureId, Option option) throws SQLException, NotFoundException {
        LOG.info("Méthode appelée: ajouterConfiguration, avec comme paramètres(s): {}, {}", voitureId, option);
        voitureService.ajouterConfiguration(voitureId, option);
    }

    public void supprimerConfiguration(Long voitureId, Option option) throws SQLException, NotFoundException, InvalidConfigurationException {
        LOG.info("Méthode appelée: supprimerConfiguration, avec comme paramètres(s): {}, {}", voitureId, option);
        voitureService.supprimerConfiguration(voitureId, option);
    }

    public Collection<Option> getOptionsForVoiture(Long voitureId) throws SQLException, NotFoundException {
        LOG.info("Méthode appelée: getOptionsForVoiture, avec comme paramètre(s): {}", voitureId);
        return voitureService.getOptionsForVoiture(voitureId);
    }

    public Voiture getVoiture(Long voitureId) throws SQLException, NotFoundException {
        LOG.info("Méthode appelée: getVoiture, avec comme paramètre(s): {}", voitureId);
        return voitureService.getVoiture(voitureId);
    }

    public void sauverVoiture(Long voitureId, Commande commande) throws SQLException, NotFoundException {
        LOG.info("Méthode appelée: sauverVoiture, avec comme paramètres(s): {}, {}", voitureId, commande);
        voitureService.sauverVoiture(voitureId, commande);
    }

    public Collection<Voiture> getVoituresByCommande(Long id) throws SQLException, NotFoundException {
        LOG.info("Méthode appelée: getVoituresByCommande, avec comme paramètre(s): {}", id);
        return voitureService.getVoituresByCommande(id);
    }

    public void supprimerVoiture(Long voitureId) throws SQLException, NotFoundException {
        LOG.info("Méthode appelée: supprimerVoiture, avec comme paramètre(s): {}", voitureId);
        voitureService.supprimerVoiture(voitureId);
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
