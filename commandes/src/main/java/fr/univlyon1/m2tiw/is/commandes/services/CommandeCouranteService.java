package fr.univlyon1.m2tiw.is.commandes.services;

import fr.univlyon1.m2tiw.is.commandes.dao.NotFoundException;
import fr.univlyon1.m2tiw.is.commandes.model.Commande;
import fr.univlyon1.m2tiw.is.commandes.model.Voiture;

import java.sql.SQLException;
import java.util.Collection;

public interface CommandeCouranteService {
    public Commande creerCommandeCourante();
    public void ajouterVoiture(Long voitureId) throws SQLException, NotFoundException;
    public void supprimerVoiture(Long voitureId) throws NotFoundException, SQLException;
    public Collection<Voiture> getAllVoitures();
    public Commande getCommandeCourante();
    public long validerCommandeCourante() throws EmptyCommandeException, SQLException, NotFoundException;
}