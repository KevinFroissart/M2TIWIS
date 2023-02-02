package fr.univlyon1.m2tiw.is.commandes.services;

import java.sql.SQLException;
import java.util.Collection;

import fr.univlyon1.m2tiw.is.commandes.dao.NotFoundException;
import fr.univlyon1.m2tiw.is.commandes.model.Commande;
import fr.univlyon1.m2tiw.is.commandes.model.Voiture;
import fr.univlyon1.m2tiw.is.commandes.resources.CommandeCouranteResource;
import fr.univlyon1.m2tiw.is.commandes.resources.VoitureResource;

/**
 * Implémentation de {@link CommandeCouranteService}.
 */
public class CommandeCouranteServiceImpl implements CommandeCouranteService {

	private Commande commandeCourante;
	private final CommandeCouranteResource commandeCouranteResource;
	private final VoitureResource voitureResource;

	public CommandeCouranteServiceImpl(CommandeCouranteResource commandeCouranteResource, VoitureResource voitureResource) {
		this.commandeCouranteResource = commandeCouranteResource;
		this.voitureResource = voitureResource;
	}

	private void getCommandeCourante() {
		this.commandeCourante = commandeCouranteResource.getCommandeCourante();
	}

	/**
	 * @InheritDoc
	 * @see CommandeCouranteService#getAllVoitures()
	 */
	@Override
	public Collection<Voiture> getAllVoitures() {
		getCommandeCourante();
		return commandeCourante.getVoitures();
	}

	/**
	 * @InheritDoc
	 * @see CommandeCouranteService#ajouterVoiture(Long)
	 */
	@Override
	public void ajouterVoiture(Long voitureId) throws SQLException, NotFoundException {
		getCommandeCourante();
		this.commandeCourante.addVoiture(voitureResource.getVoiture(voitureId));
	}

	/**
	 * @InheritDoc
	 * @see CommandeCouranteService#supprimerVoiture(Long)
	 */
	@Override
	public void supprimerVoiture(Long voitureId) throws SQLException, NotFoundException {
		getCommandeCourante();
		this.commandeCourante.removeVoiture(voitureResource.getVoiture(voitureId));
		this.voitureResource.supprimerVoiture(voitureId);
	}

}
