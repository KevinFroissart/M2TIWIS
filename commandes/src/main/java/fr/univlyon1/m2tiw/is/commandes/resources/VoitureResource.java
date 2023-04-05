package fr.univlyon1.m2tiw.is.commandes.resources;

import fr.univlyon1.m2tiw.is.commandes.dao.NotFoundException;
import fr.univlyon1.m2tiw.is.commandes.dao.OptionDAO;
import fr.univlyon1.m2tiw.is.commandes.dao.VoitureDAO;
import fr.univlyon1.m2tiw.is.commandes.model.Commande;
import fr.univlyon1.m2tiw.is.commandes.model.Option;
import fr.univlyon1.m2tiw.is.commandes.model.Voiture;

import java.sql.SQLException;

/**
 * Ressource pour les voitures.
 */
public class VoitureResource {

    private final VoitureDAO voitureDAO;

    private final OptionDAO optionDAO;

    public VoitureResource(VoitureDAO voitureDAO, OptionDAO optionDAO) {
        this.voitureDAO = voitureDAO;
        this.optionDAO = optionDAO;
    }

    /**
     * Crée une voiture.
     *
     * @param modele le modèle de la voiture.
     * @return la {@link Voiture} créée.
     * @throws SQLException pour une exception SQL.
     */
    public Voiture creerVoiture(String modele) throws SQLException {
        return voitureDAO.saveVoiture(new Voiture(modele));
    }

    /**
     * Récupère une voiture.
     *
     * @param voitureId l'identifiant de la voiture.
     * @return la {@link Voiture} récupérée.
     * @throws SQLException pour une exception SQL.
     * @throws NotFoundException si la voiture n'existe pas.
     */
    public Voiture getVoiture(Long voitureId) throws SQLException, NotFoundException {
        var voiture = voitureDAO.getVoitureById(voitureId);
        for (Option option : optionDAO.getOptionsForVoiture(voitureId)) {
            voiture.addOption(option);
        }
        return voiture;
    }

    /**
     * Remplace la commande associée à une voiture.
     *
     * @param voitureId l'id de la voiture à modifier.
     * @param commande la commande à remplacer.
     * @throws SQLException pour une exception SQL.
     * @throws NotFoundException pour une voiture non trouvée.
     */
    public void sauverVoiture(Long voitureId, Commande commande) throws SQLException, NotFoundException {
        voitureDAO.updateVoitureCommande(voitureId, commande.getId());
    }

    /**
     * Supprime une voiture.
     *
     * @param voitureId l'id de la voiture à supprimer.
     * @throws SQLException pour une exception SQL.
     * @throws NotFoundException pour une voiture non trouvée.
     */
    public void supprimerVoiture(Long voitureId) throws SQLException, NotFoundException {
        voitureDAO.deleteVoiture(voitureDAO.getVoitureById(voitureId));
    }

}
