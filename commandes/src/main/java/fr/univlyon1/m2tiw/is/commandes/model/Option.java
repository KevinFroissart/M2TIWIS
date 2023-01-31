package fr.univlyon1.m2tiw.is.commandes.model;

import java.util.Objects;

/**
 * Modèle d'une option.
 */
public class Option {

    private String nom;
    private String valeur;

    /**
     * Constructeur d'une option.
     *
     * @param nom le nom de l'option.
     * @param valeur la valeur de l'option.
     */
    public Option(String nom, String valeur) {
        this.nom = nom;
        this.valeur = valeur;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        var option = (Option) o;
        return Objects.equals(nom, option.nom) && Objects.equals(valeur, option.valeur);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nom, valeur);
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getValeur() {
        return valeur;
    }

    public void setValeur(String valeur) {
        this.valeur = valeur;
    }

}
