package fr.univlyon1.m2tiw.is.commandes.services;

import fr.univlyon1.m2tiw.is.commandes.dao.*;
import fr.univlyon1.m2tiw.is.commandes.model.Commande;
import fr.univlyon1.m2tiw.is.commandes.model.Option;
import java.sql.SQLException;
import java.util.Collection;

public class GestionCommandeServiceImpl implements GestionCommandeService {

    private OptionService optionService;
    private VoitureService voitureService;
    private CommandeCouranteService commandeCouranteService;
    private CommandeDAO commandeDAO;

    public GestionCommandeServiceImpl() {
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

    public void setOptionService(OptionService optionService) {
        this.optionService = optionService;
    }

    public void setVoitureService(VoitureService voitureService) {
        this.voitureService = voitureService;
    }

    public void setCommandeCouranteService(CommandeCouranteService commandeCouranteService) {
        this.commandeCouranteService = commandeCouranteService;
    }

    public void setCommandeDAO(CommandeDAO commandeDAO) {
        this.commandeDAO = commandeDAO;
    }
}
