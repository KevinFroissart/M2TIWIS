package fr.univlyon1.m2tiw.is.commandes.dao;

import fr.univlyon1.m2tiw.is.commandes.model.Option;

import java.sql.SQLException;
import java.util.Collection;

/**
 * Manages persistence of modele3d.
 */
public interface OptionDAO {

    /**
     * Initializes table and statements.
     *
     * @throws SQLException if there is a DB problem
     */
    void init() throws SQLException;

    /**
     * Get an option from persistence support
     *
     * @param voitureId the voiture in which this options is searched for
     * @param nom the name of the option
     * @return the option
     * @throws NotFoundException if the option was not in the persistence support.
     * @throw SQLException
     */
    Option getOption(long voitureId, String nom) throws SQLException, NotFoundException;

    /**
     * Get all options
     * @return a collection containing all known options
     */
    Collection<Option> getAllOptions() throws SQLException;

    /**
     * Get options for a voiture
     * @param voitureId id of the voiture
     * @return a collection of options set for this voiture
     * @throws SQLException
     */
    Collection<Option> getOptionsForVoiture(long voitureId) throws SQLException;

    /**
     * Place une option sur une voiture
     * @param voitureId
     * @param option
     */
    void setOptionVoiture(Long voitureId, Option option) throws SQLException;

    /**
     * Supprime une option d'une voiture
     * @param voitureId
     * @param nom
     */
    void deleteOptionVoiture(Long voitureId, String nom) throws SQLException;
}
