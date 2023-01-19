package fr.univlyon1.m2tiw.is.catalogue.model;

import jakarta.persistence.Basic;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Lob;
import jakarta.persistence.OneToOne;

@Entity
@IdClass(MachineOptionKey.class)
public class Configuration {

    @Id
    @OneToOne
    private Machine machine;

    @Id
    @OneToOne
    private Option option;

    @Lob
    @Basic(fetch = FetchType.LAZY)
    private String cfg;

    public Configuration() {
    }

    public Configuration(Machine machine, Option option, String cfg) {
        this.machine = machine;
        this.option = option;
        this.cfg = cfg;
    }

    public Machine getMachine() {
        return machine;
    }

    public void setMachine(Machine machine) {
        this.machine = machine;
    }

    public Option getOption() {
        return option;
    }

    public void setOption(Option option) {
        this.option = option;
    }

    public String getCfg() {
        return cfg;
    }

    public void setCfg(String cfg) {
        this.cfg = cfg;
    }
}
