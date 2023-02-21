package fr.univlyon1.m2tiw.is.catalogue.service.dto;

import fr.univlyon1.m2tiw.is.catalogue.model.Option;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

public class OptionDTO {
    public String nom;
    public Collection<Long> machines = new ArrayList<>();

    public OptionDTO(String nom) {
        this.nom = nom;
    }

    public OptionDTO() {
    }

    public OptionDTO(Option option) {
        this.nom = option.getNom();
        this.machines = option.getConfigurations().stream().map(cfg -> cfg.getMachine().getId()).toList();
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public Option asOption() {
        Option option = new Option(this.getNom());
        return option;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OptionDTO optionDTO = (OptionDTO) o;
        return Objects.equals(nom, optionDTO.nom);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nom);
    }
}
