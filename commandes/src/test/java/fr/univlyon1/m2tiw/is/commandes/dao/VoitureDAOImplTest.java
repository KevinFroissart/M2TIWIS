package fr.univlyon1.m2tiw.is.commandes.dao;

import fr.univlyon1.m2tiw.is.commandes.model.Commande;
import fr.univlyon1.m2tiw.is.commandes.model.Voiture;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;

class VoitureDAOImplTest {

    private static CommandeDAOImpl commandeDAO;
    private VoitureDAOImpl voitureDAO;

    @BeforeAll
    public static void beforeAll() throws SQLException {
        commandeDAO = new CommandeDAOImpl();
        commandeDAO.init();
    }

    @BeforeEach
    public void before() throws SQLException {
        voitureDAO = new VoitureDAOImpl();
        voitureDAO.init();
    }

    @Test
    void saveVoiture() throws SQLException, NotFoundException {
        Voiture voiture = new Voiture("modele1");
        Commande c = commandeDAO.saveCommande(new Commande(false));
        voiture = voitureDAO.saveVoiture(voiture, c.getId());
        assertNotNull(voiture);
        assertEquals("modele1", voiture.getModele());
        voitureDAO.deleteVoiture(voiture);
        commandeDAO.deleteCommande(c.getId());
    }

    @Test
    void getVoitureById() throws SQLException, NotFoundException {
        Voiture voiture = new Voiture("modele1");
        Commande c = commandeDAO.saveCommande(new Commande(false));
        voiture = voitureDAO.saveVoiture(voiture, c.getId());
        Voiture voiture2 = voitureDAO.getVoitureById(voiture.getId());
        assertEquals(voiture, voiture2);
        assertEquals("modele1", voiture2.getModele());
        voitureDAO.deleteVoiture(voiture);
        commandeDAO.deleteCommande(c.getId());
    }

    @Test
    void getVoituresByCommande() throws SQLException, NotFoundException {
        Voiture voiture = new Voiture("modele1");
        Commande c = commandeDAO.saveCommande(new Commande(false));
        voiture = voitureDAO.saveVoiture(voiture, c.getId());
        Collection<Voiture> voitures = voitureDAO.getVoituresByCommande(c.getId());
        assertEquals(1, voitures.size());
        for (var v : voitures) {
            assertEquals(v, voiture);
            assertEquals(v.getModele(), voiture.getModele());
        }
        voitureDAO.deleteVoiture(voiture);
        commandeDAO.deleteCommande(c.getId());
    }

    @Test
    void updateVoiture() throws SQLException, NotFoundException {
        Voiture voiture = new Voiture("modele1");
        Commande c = commandeDAO.saveCommande(new Commande(false));
        voiture = voitureDAO.saveVoiture(voiture, c.getId());
        voiture.setModele("modele2");
        voitureDAO.updateVoiture(voiture);
        Voiture voiture2 = voitureDAO.getVoitureById(voiture.getId());
        assertEquals(voiture, voiture2);
        assertEquals("modele2", voiture2.getModele());
        voitureDAO.deleteVoiture(voiture);
        commandeDAO.deleteCommande(c.getId());
    }

    @Test
    void updateVoitureCommande() throws SQLException, NotFoundException {
        Voiture voiture = new Voiture("modele1");
        Commande c = commandeDAO.saveCommande(new Commande(false));
        voiture = voitureDAO.saveVoiture(voiture, c.getId());
        Commande c2 = commandeDAO.saveCommande(new Commande(false));
        voitureDAO.updateVoitureCommande(voiture.getId(), c2.getId());
        var voituresC2 = voitureDAO.getVoituresByCommande(c2.getId());
        assertEquals(1, voituresC2.size());
        for (var v : voituresC2) {
            assertEquals(v, voiture);
        }
        voitureDAO.deleteVoiture(voiture);
        commandeDAO.deleteCommande(c.getId());
        commandeDAO.deleteCommande(c2.getId());
    }

    @Test
    void deleteVoiture() throws SQLException, NotFoundException {
        Voiture voiture = new Voiture("modele1");
        Commande c = commandeDAO.saveCommande(new Commande(false));
        final var voiture2 = voitureDAO.saveVoiture(voiture, c.getId());
        voitureDAO.deleteVoiture(voiture);
        assertEquals(0, voitureDAO.getVoituresByCommande(c.getId()).size());
        assertThrows(NotFoundException.class, () -> {
            voitureDAO.getVoitureById(voiture2.getId());
        });
    }
}