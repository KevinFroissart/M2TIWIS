package fr.univlyon1.m2tiw.is.commandes.dao;

import fr.univlyon1.m2tiw.is.commandes.model.Voiture;

import java.sql.SQLException;
import java.util.Collection;

/**
 * DAO for managing Voiture persistence.
 */
public interface VoitureDAO {

    /**
     * Initilizes statements and tables.
     *
     * @throws SQLException if there is a problem during table or statement creation
     */
    void init() throws SQLException;

    /**
     * Saves a new voiture in base
     *
     * @param voiture    the voiture to save
     * @param commandeId the id of the commande the voiture will be in.
     * @return either the provided voiture or a copy with an updated id.
     * @throws SQLException if there is a persistence problem
     */
    Voiture saveVoiture(Voiture voiture, long commandeId) throws SQLException;

    /**
     * Retreive a voiture using its id.
     *
     * @param id the id of the article
     * @return the article
     * @throws NotFoundException if the id does not match a voiture in the persistence support
     * @throws SQLException      if there is a problem in the underlying queries
     */
    Voiture getVoitureById(long id) throws NotFoundException, SQLException;

    /**
     * Retreive articles related to the given commande.
     *
     * @param commandeId the identifier for the commande
     * @return the collection of matching voitures
     * @throws SQLException      if there is a problem in DB.
     * @throws NotFoundException if a reference voiture cannot be found
     */
    Collection<Voiture> getVoituresByCommande(long commandeId) throws SQLException, NotFoundException;

    /**
     * Updates the data for a voiture
     *
     * @param voiture the voiture to update in the persistence support
     * @return the voiture or an updated copy
     * @throws NotFoundException if the voiture is not found in the persistence support
     * @throws SQLException      if there is a problem in the underlying queries
     */
    Voiture updateVoiture(Voiture voiture) throws NotFoundException, SQLException;

    /**
     * Updates the commande for a voiture
     *
     * @param voitureId the voiture to update in the persistence support
     * @throws NotFoundException if the voiture is not found in the persistence support
     * @throws SQLException      if there is a problem in the underlying queries
     */
    public void updateVoitureCommande(long voitureId, long newCommandeId) throws SQLException, NotFoundException;

    /**
     * Deletes a voiture.
     *
     * @param voiture the voiture to remove from the DB
     * @throws SQLException      if there is a problem in the underlying query
     * @throws NotFoundException if the voiture does not exist in DB
     */
    void deleteVoiture(Voiture voiture) throws SQLException, NotFoundException;
}