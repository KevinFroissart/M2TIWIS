package fr.univlyon1.m2tiw.is.commandes.dao;

import fr.univlyon1.m2tiw.is.commandes.model.Commande;

import java.sql.SQLException;

/**
 * Manages persistence of panier
 */
public interface CommandeDAO {

    /**
     * Initializes table and statements.
     *
     * @throws SQLException if there is a DB problem
     */
    void init() throws SQLException;

    /**
     * Saves a commande in the persistence support.
     *
     * @param commande the commande to save
     * @return the commande or an updated copy
     * @throws SQLException if the is a problem in DB
     */
    Commande saveCommande(Commande commande) throws SQLException;

    /**
     * Retreives a panier from its id.
     *
     * @param commandeId the id of the commande to retreive
     * @return the panier
     * @throws SQLException      if there is a problem in the DB
     * @throws NotFoundException if the panier is not in DB
     */
    Commande getCommande(long commandeId) throws SQLException, NotFoundException;

    /**
     * Updates a commande in DB
     *
     * @param commande the commande to update
     * @throws SQLException      if there is a problem in DB
     * @throws NotFoundException if the commande is not in DB
     */
    void updateCommande(Commande commande) throws SQLException, NotFoundException;

    void deleteCommande(long commandeId) throws SQLException;
}
