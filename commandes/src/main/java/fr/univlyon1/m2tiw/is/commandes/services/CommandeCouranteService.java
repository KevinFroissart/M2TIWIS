package fr.univlyon1.m2tiw.is.commandes.services;

import java.sql.SQLException;

import fr.univlyon1.m2tiw.is.commandes.dao.NotFoundException;
import fr.univlyon1.m2tiw.is.commandes.model.Commande;

public interface CommandeCouranteService {

	Commande creerCommandeCourante();

	Commande getCommandeCourante();

	long validerCommandeCourante() throws EmptyCommandeException, SQLException, NotFoundException;

}
