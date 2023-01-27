package fr.univlyon1.m2tiw.is.commandes.resource;

import java.sql.SQLException;
import java.util.Map;

import fr.univlyon1.m2tiw.is.commandes.controller.AbstractController;
import fr.univlyon1.m2tiw.is.commandes.dao.NotFoundException;
import fr.univlyon1.m2tiw.is.commandes.services.EmptyCommandeException;

public class OptionResource extends AbstractController {

	@Override
	public Object process(String commande, Map<String, Object> parametres) throws SQLException, NotFoundException, EmptyCommandeException {
		return null;
	}
}
