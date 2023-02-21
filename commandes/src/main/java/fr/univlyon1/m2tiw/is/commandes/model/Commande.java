package fr.univlyon1.m2tiw.is.commandes.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

public class Commande {
    private Long id;
    private boolean ferme;
    private Collection<Voiture> voitures = new ArrayList<>();

    public Commande(boolean ferme) {
        this.ferme = ferme;
    }

    /*public Commande(Long id) {
        this.id = id;
    }*/

    public Commande(Long id, boolean ferme, Collection<Voiture> voitures) {
        this.id = id;
        this.ferme = ferme;
        this.voitures = voitures;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Commande commande = (Commande) o;
        return Objects.equals(id, commande.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean isFerme() {
        return ferme;
    }

    public void setFerme(boolean ferme) {
        this.ferme = ferme;
    }

    public Collection<Voiture> getVoitures() {
        return voitures;
    }

    public void addVoiture(Voiture voiture) {
        this.voitures.add(voiture);
    }

    public void removeVoiture(Voiture voiture) {
        this.voitures.remove(voiture);
    }

    public void setVoitures(Collection<Voiture> voitures) {
        this.voitures = voitures;
    }
}
