package fr.univlyon1.m2tiw.is.machine.models;

import java.io.Serializable;
import java.util.Collection;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Copie de la classe Voiture de chain-manager, permet de serialiser les objets voiture.
 */
public class Voiture implements Serializable {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("options")
    private Collection<String> options ;

    @JsonProperty("statut")
    private Statut statut;

    @JsonProperty("machineJobs")
    private Map<String, Statut> machineJobs;

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
