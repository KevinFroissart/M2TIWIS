package fr.univlyon1.m2tiw.is.commandes.dao;

import fr.univlyon1.m2tiw.is.commandes.model.Commande;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class CommandeDAOImplTest {

    private CommandeDAOImpl commandeDAO;

    @BeforeEach
    public void setup() throws SQLException {
        commandeDAO = new CommandeDAOImpl();
        commandeDAO.init();
    }

    @Test
    void saveCommande() throws SQLException {
        Commande c = new Commande(false);
        c = commandeDAO.saveCommande(c);
        assertNotNull(c);
        assertFalse(c.isFerme());
        commandeDAO.deleteCommande(c.getId());
    }

    @Test
    void getCommande() throws SQLException, NotFoundException {
        Commande c = new Commande(false);
        c = commandeDAO.saveCommande(c);
        Commande c2 = commandeDAO.getCommande(c.getId());
        assertEquals(c,c2);
        assertEquals(c.isFerme(),c2.isFerme());
        commandeDAO.deleteCommande(c.getId());
    }

    @Test
    void updateCommande() throws SQLException, NotFoundException {
        Commande c = new Commande(false);
        c = commandeDAO.saveCommande(c);
        c.setFerme(true);
        commandeDAO.updateCommande(c);
        Commande c2 = commandeDAO.getCommande(c.getId());
        assertEquals(c,c2);
        assertEquals(true,c2.isFerme());
        commandeDAO.deleteCommande(c.getId());
    }
}