package fr.univlyon1.m2tiw.is.commandes.model;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Modèle d'une voiture.
 */
public class Voiture {
    private Long id;
    private String modele;
    private Map<String, Option> options;

    /**
     * Constructeur d'une voiture.
     *
     * @param id l'id de la voiture.
     * @param modele le modèle de la voiture.
     */
    public Voiture(Long id, String modele) {
        this.id = id;
        this.modele = modele;
        this.options = new HashMap<>();
    }

    /**
     * Constructeur d'une voiture.
     *
     * @param modele le modèle de la voiture.
     */
    public Voiture(String modele) {
        this.modele = modele;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        var voiture = (Voiture) o;
        return Objects.equals(id, voiture.id);
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

    public String getModele() {
        return modele;
    }

    public void setModele(String modele) {
        this.modele = modele;
    }

    public void addOption(Option option) {
        this.options.put(option.getNom(), option);
    }

    public void deleteOption(Option option) {
        this.options.remove(option.getNom());
    }

//    public boolean hasOption(Option option) {
//        return this.options.containsKey(option.getNom());
//    }

    public Collection<Option> getOptions() {
        return options.values();
    }

}
