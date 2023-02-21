package fr.univlyon1.m2tiw.is.commandes.dao;

import fr.univlyon1.m2tiw.is.commandes.model.Commande;
import fr.univlyon1.m2tiw.is.commandes.model.Option;
import fr.univlyon1.m2tiw.is.commandes.model.Voiture;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class OptionDAOImplTest {

    private static CommandeDAOImpl commandeDAO;
    private static VoitureDAOImpl voitureDAO;
    private OptionDAOImpl optionDAO;
    private Commande commande;
    private Voiture voiture;

    private static int counter = 0;

    @BeforeAll
    public static void before() throws SQLException {
        commandeDAO = new CommandeDAOImpl();
        commandeDAO.init();
        voitureDAO = new VoitureDAOImpl();
        voitureDAO.init();
    }

    @BeforeEach
    public void beforeEach() throws SQLException {
        optionDAO = new OptionDAOImpl();
        optionDAO.init();
        commande = commandeDAO.saveCommande(new Commande(false));
        voiture = voitureDAO.saveVoiture(new Voiture("modele3"), commande.getId());
    }

    @AfterEach
    public void afterEach() throws SQLException, NotFoundException {
        voitureDAO.deleteVoiture(voiture);
        commandeDAO.deleteCommande(commande.getId());
    }

    private Option createOption() throws SQLException {
        Option o = new Option("nom" + counter++, "valeur" + counter++);
        optionDAO.setOptionVoiture(voiture.getId(), o);
        return o;
    }

    @Test
    void getOption() throws SQLException, NotFoundException {
        Option o = createOption();
        Option o2 = optionDAO.getOption(voiture.getId(), o.getNom());
        assertEquals(o, o2);
        assertEquals(o.getValeur(), o2.getValeur());
        optionDAO.deleteOptionVoiture(voiture.getId(), o.getNom());
    }

    @Test
    void getAllOptions() throws SQLException {
        Option o = createOption();
        Option o2 = createOption();
        var options = optionDAO.getAllOptions();
        assertTrue(2 <= options.size());
        optionDAO.deleteOptionVoiture(voiture.getId(), o.getNom());
        optionDAO.deleteOptionVoiture(voiture.getId(), o2.getNom());
    }

    @Test
    void getOptionsForVoiture() throws SQLException {
        Option o = createOption();
        Option o2 = createOption();
        var options = optionDAO.getOptionsForVoiture(voiture.getId());
        assertEquals(2, options.size());
        optionDAO.deleteOptionVoiture(voiture.getId(), o.getNom());
        optionDAO.deleteOptionVoiture(voiture.getId(), o2.getNom());
        assertEquals(0, optionDAO.getOptionsForVoiture(voiture.getId()).size());
    }

    @Test
    void setOptionVoiture() throws SQLException, NotFoundException {
        Option o = createOption();
        optionDAO.setOptionVoiture(voiture.getId(), new Option(o.getNom(), "une nouvelle valeur"));
        var o2 = optionDAO.getOption(voiture.getId(), o.getNom());
        assertEquals("une nouvelle valeur", o2.getValeur());
        optionDAO.deleteOptionVoiture(voiture.getId(), o.getNom());
    }

    @Test
    void deleteOptionVoiture() throws SQLException {
        Option o = createOption();
        optionDAO.deleteOptionVoiture(voiture.getId(), o.getNom());
        assertThrows(NotFoundException.class, () -> optionDAO.getOption(voiture.getId(), o.getNom()));
    }
}