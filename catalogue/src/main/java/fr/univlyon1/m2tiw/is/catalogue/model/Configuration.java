package fr.univlyon1.m2tiw.is.catalogue.model;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.Objects;

@Entity
@IdClass(Configuration.Key.class)
public class Configuration {
    public static class Key implements Serializable {
        public Machine machine;
        public Option option;

        public Key() {
        }

        public Key(Machine machine, Option option) {
            this.machine = machine;
            this.option = option;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Key key = (Key) o;
            return Objects.equals(machine, key.machine) && Objects.equals(option, key.option);
        }

        @Override
        public int hashCode() {
            return Objects.hash(machine, option);
        }
    }

    @Id
    @ManyToOne
    private Machine machine;
    @Id
    @ManyToOne
    private Option option;
    @Lob
    private String cfg;

    public Configuration(Machine machine, Option option, String cfg) {
        this.machine = machine;
        this.option = option;
        this.cfg = cfg;
    }

    public Configuration() {
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
