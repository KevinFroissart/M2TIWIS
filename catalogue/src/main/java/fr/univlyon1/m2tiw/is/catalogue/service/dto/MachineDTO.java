package fr.univlyon1.m2tiw.is.catalogue.service.dto;

import fr.univlyon1.m2tiw.is.catalogue.model.Machine;

public class MachineDTO {
    public Long id;
    public String modele;

    public MachineDTO() {
    }

    public MachineDTO(Long id, String modele) {
        this.id = id;
        this.modele = modele;
    }

    public MachineDTO(Machine machine) {
        this.id = machine.getId();
        this.modele = machine.getModele();
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
}
