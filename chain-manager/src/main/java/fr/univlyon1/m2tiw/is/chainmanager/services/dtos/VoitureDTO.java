package fr.univlyon1.m2tiw.is.chainmanager.services.dtos;

import fr.univlyon1.m2tiw.is.chainmanager.models.Statut;
import fr.univlyon1.m2tiw.is.chainmanager.models.Voiture;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

import java.util.Collection;
import java.util.Map;

public class VoitureDTO {
    public Long id;
    public Collection<String> options;
    public String statut;

    public VoitureDTO() {
    }

    public VoitureDTO(Long id, Collection<String> options, String statut) {
        this.id = id;
        this.options = options;
        this.statut = statut;
    }

    public VoitureDTO(Voiture voiture) {
        this(voiture.getId(), voiture.getOptions(), voiture.getStatut().name());
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Collection<String> getOptions() {
        return options;
    }

    public void setOptions(Collection<String> options) {
        this.options = options;
    }

    public String getStatut() {
        return statut;
    }

    public void setStatut(String statut) {
        this.statut = statut;
    }
}
