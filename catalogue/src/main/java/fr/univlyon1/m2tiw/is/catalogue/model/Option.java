package fr.univlyon1.m2tiw.is.catalogue.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Option {

    @Id
    private String nom;

    private String description;

    public Option() {
    }

    public Option(String nom, String description) {
        this.nom = nom;
        this.description = description;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
