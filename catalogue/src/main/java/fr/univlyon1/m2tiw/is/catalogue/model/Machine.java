package fr.univlyon1.m2tiw.is.catalogue.model;

import fr.univlyon1.m2tiw.is.catalogue.service.dto.MachineDTO;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class Machine {
    @Id
    @GeneratedValue
    private Long id;
    private String modele;

    public Machine() {
    }

    public Machine(Long id, String modele) {
        this.id = id;
        this.modele = modele;
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
}
