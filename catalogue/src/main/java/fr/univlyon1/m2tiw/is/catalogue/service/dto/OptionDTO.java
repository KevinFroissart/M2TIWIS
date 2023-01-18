package fr.univlyon1.m2tiw.is.catalogue.service.dto;

import fr.univlyon1.m2tiw.is.catalogue.model.Option;

public class OptionDTO {

    public String nom;

    public String description;

    public OptionDTO() {
    }

    public OptionDTO(String nom, String description) {
        this.nom = nom;
        this.description = description;
    }

    public OptionDTO(Option option) {
        this.nom = option.getNom();
        this.description = option.getDescription();
    }

    public Option toOption() {
        return new Option(nom, description);
    }

    @Override
    public String toString() {
        return "<OptionDTO " + nom + " " + description + ">";
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
