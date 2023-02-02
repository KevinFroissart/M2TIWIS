package fr.univlyon1.m2tiw.is.commandes.resources;

import java.sql.SQLException;

import fr.univlyon1.m2tiw.is.commandes.dao.CommandeDAO;
import fr.univlyon1.m2tiw.is.commandes.dao.NotFoundException;
import fr.univlyon1.m2tiw.is.commandes.model.Commande;
import fr.univlyon1.m2tiw.is.commandes.model.Voiture;
import fr.univlyon1.m2tiw.is.commandes.services.EmptyCommandeException;

/**
 * Ressource pour la commande courante.
 */
public class CommandeCouranteResource {

    private Commande commandeCourante;

    private final CommandeDAO commandeDAO;

    private final VoitureResource voitureResource;

    public CommandeCouranteResource(CommandeDAO commandeDAO, VoitureResource voitureResource) {
        this.commandeDAO = commandeDAO;
        this.voitureResource = voitureResource;
    }

    /**
     * Crée une commande courante.
     *
     * @return la {@link Commande} courante.
     */
    public Commande creerCommandeCourante() {
        this.commandeCourante = new Commande(false);
        return commandeCourante;
    }

    /**
     * Retourne la commande courante.
     *
     * @return la {@link Commande} courante.
     */
    public Commande getCommandeCourante() {
        if (commandeCourante == null) {
            creerCommandeCourante();
        }
        return commandeCourante;
    }

    /**
     * Valide la commande courante.
     *
     * @return un l'id de la commande validée.
     * @throws EmptyCommandeException pour une commande vide.
     * @throws SQLException pour une exception SQL.
     * @throws NotFoundException pour une voiture non trouvée.
     */
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
