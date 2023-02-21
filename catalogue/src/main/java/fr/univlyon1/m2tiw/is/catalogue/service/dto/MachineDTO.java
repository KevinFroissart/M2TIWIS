package fr.univlyon1.m2tiw.is.catalogue.service.dto;

import fr.univlyon1.m2tiw.is.catalogue.model.Machine;

import java.util.Collection;
import java.util.stream.Collectors;

public class MachineDTO {
    public Long id;
    public String modele;
    public Collection<String> options;

    public MachineDTO() {
    }

    public MachineDTO(Long id, String modele) {
        this.id = id;
        this.modele = modele;
    }

    public MachineDTO(Machine machine) {
        this.id = machine.getId();
        this.modele = machine.getModele();
        this.options = machine.getConfigurations().stream().map(cfg -> cfg.getOption().getNom()).collect(Collectors.toSet());
    }

    public Machine toMachine() {
        return new Machine(id, modele);
    }

    @Override
    public String toString() {
        return "<MachineDTO " + id + " " + modele + ">";
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

    public Collection<String> getOptions() {
        return options;
    }

    public void setOptions(Collection<String> options) {
        this.options = options;
    }
}
