package fr.univlyon1.m2tiw.is.chainmanager.models;

import jakarta.persistence.*;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

@Entity
public class Voiture {

    @Id
    @GeneratedValue
    private Long id;
    @ElementCollection
    private Collection<String> options = new HashSet<>();
    @Enumerated(EnumType.STRING)
    private Statut statut = Statut.AFAIRE;
    @ElementCollection
    @Enumerated(EnumType.STRING)
    private Map<String, Statut> machineJobs = new HashMap<>();

    public Voiture() {
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

    public Statut getStatut() {
        return statut;
    }

    public void setStatut(Statut statut) {
        this.statut = statut;
    }

    public Map<String, Statut> getMachineJobs() {
        return machineJobs;
    }

    public void setMachineJobs(Map<String, Statut> machineJobs) {
        this.machineJobs = machineJobs;
    }

    public void addOption(String option) {
        options.add(option);
    }

    public void setMachineJobStatus(String machine, Statut statut) {
        machineJobs.put(machine,statut);
    }
}
