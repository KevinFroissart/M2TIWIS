package fr.univlyon1.m2tiw.is.catalogue.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

@Entity
public class Option {
    @Id
    private String nom;

    @OneToMany
    private Collection<Configuration> configurations = new ArrayList<>();

    public Option() {
    }

    public Option(String nom) {
        this.nom = nom;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public Collection<Configuration> getConfigurations() {
        return configurations;
    }

    public void setConfigurations(Collection<Configuration> configurations) {
        this.configurations = configurations;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Option option = (Option) o;
        return Objects.equals(nom, option.nom);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nom);
    }
}
