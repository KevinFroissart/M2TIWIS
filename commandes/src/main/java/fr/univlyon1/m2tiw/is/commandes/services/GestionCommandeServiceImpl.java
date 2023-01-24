package fr.univlyon1.m2tiw.is.commandes.services;

import fr.univlyon1.m2tiw.is.commandes.dao.*;
import fr.univlyon1.m2tiw.is.commandes.model.Commande;
import fr.univlyon1.m2tiw.is.commandes.model.Option;
import java.sql.SQLException;
import java.util.Collection;

public class GestionCommandeServiceImpl implements GestionCommandeService {

    private final OptionService optionService;
    private final VoitureService voitureService;
    private final CommandeCouranteService commandeCouranteService;
    private final CommandeDAO commandeDAO;

    public GestionCommandeServiceImpl() throws SQLException {
        commandeCouranteService = new CommandeCouranteServiceImpl(); // TODO: Inject
        optionService= new OptionServiceImpl(); // TODO: Inject
        voitureService = new VoitureServiceImpl(); // TODO: Inject
        commandeDAO = new CommandeDAOImpl(); // TODO: Inject
        commandeDAO.init();
    }

    @Override
    public Collection<Option> getAllOptions() throws SQLException {
        return this.optionService.getAllOptions();
    }

    @Override
    public Commande getCommande(Long id) throws SQLException, NotFoundException {
        Commande commande = commandeDAO.getCommande(id);
        commande.getVoitures().addAll(voitureService.getVoituresByCommande(id));
        return commande;
    }

    @Override
    public Commande getCommandeCourante() {
        return commandeCouranteService.getCommandeCourante();
    }
}