package fr.univlyon1.m2tiw.is.catalogue.model;

import java.io.Serializable;
import java.util.Objects;

public class MachineOptionKey implements Serializable {
    private Machine machine;
    private Option option;

    public MachineOptionKey(Machine machine, Option option) {
        this.machine = machine;
        this.option = option;
    }

    public MachineOptionKey() {

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MachineOptionKey that = (MachineOptionKey) o;
        return machine.equals(that.machine) && option.equals(that.option);
    }

    @Override
    public int hashCode() {
        return Objects.hash(machine, option);
    }
}
