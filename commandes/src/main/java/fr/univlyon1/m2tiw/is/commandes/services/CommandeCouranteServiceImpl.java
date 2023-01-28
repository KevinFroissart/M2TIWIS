package fr.univlyon1.m2tiw.is.commandes.services;

import java.sql.SQLException;

import fr.univlyon1.m2tiw.is.commandes.dao.CommandeDAO;
import fr.univlyon1.m2tiw.is.commandes.dao.NotFoundException;
import fr.univlyon1.m2tiw.is.commandes.model.Commande;
import fr.univlyon1.m2tiw.is.commandes.model.Voiture;
import fr.univlyon1.m2tiw.is.commandes.resource.VoitureResource;

public class CommandeCouranteServiceImpl implements CommandeCouranteService {

	private Commande commandeCourante;
	private final CommandeDAO commandeDAO;
	private final VoitureResource voitureResource;


	public CommandeCouranteServiceImpl(CommandeDAO commandeDAO, VoitureResource voitureResource) throws SQLException {
		this.commandeDAO = commandeDAO;
		this.voitureResource = voitureResource;
	}

	@Override
	public Commande creerCommandeCourante() {
		this.commandeCourante = new Commande(false);
		return commandeCourante;
	}

	@Override
	public Commande getCommandeCourante() {
		if (commandeCourante == null) {
			creerCommandeCourante();
		}
		return commandeCourante;
	}

	@Override
	public long validerCommandeCourante() throws EmptyCommandeException, SQLException, NotFoundException {
		if (commandeCourante.getVoitures().isEmpty()) {
			throw new EmptyCommandeException("Commande vide");
		}
		commandeCourante.setFerme(true);
		commandeCourante = commandeDAO.saveCommande(commandeCourante);
		for (Voiture voiture : commandeCourante.getVoitures()) {
			voitureResource.sauverVoiture(voiture.getId(), commandeCourante);
		}
		long id = commandeCourante.getId();
		creerCommandeCourante(); // On repart avec un nouveau panier vide
		return id;
	}

}
