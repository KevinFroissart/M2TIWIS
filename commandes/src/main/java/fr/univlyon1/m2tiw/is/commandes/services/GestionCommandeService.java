package fr.univlyon1.m2tiw.is.commandes.services;

import fr.univlyon1.m2tiw.is.commandes.dao.NotFoundException;
import fr.univlyon1.m2tiw.is.commandes.model.Option;
import fr.univlyon1.m2tiw.is.commandes.model.Commande;

import java.sql.SQLException;
import java.util.Collection;

public interface GestionCommandeService {
    public Collection<Option> getAllOptions() throws SQLException;
    public Commande getCommande(Long id) throws SQLException, NotFoundException;
    public Commande getCommandeCourante();
}