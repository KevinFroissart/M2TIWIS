package fr.univlyon1.m2tiw.is.catalogue.service;

import fr.univlyon1.m2tiw.is.catalogue.model.MachineOptionKey;

public class NoSuchConfigurationException extends Exception {
    private MachineOptionKey machineOptionKey;

    public NoSuchConfigurationException (MachineOptionKey machineOptionKey) {
        this.machineOptionKey = machineOptionKey;
    }

    public NoSuchConfigurationException (String message, MachineOptionKey machineOptionKey) {
        super(message);
        this.machineOptionKey = machineOptionKey;
    }

    public NoSuchConfigurationException (String message, Throwable cause, MachineOptionKey machineOptionKey) {
        super(message, cause);
        this.machineOptionKey = machineOptionKey;
    }

    public NoSuchConfigurationException (Throwable cause, MachineOptionKey machineOptionKey) {
        super(cause);
        this.machineOptionKey = machineOptionKey;
    }

    public NoSuchConfigurationException (String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, MachineOptionKey machineOptionKey) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.machineOptionKey = machineOptionKey;
    }

    public MachineOptionKey getMachineOptionKey() {
        return machineOptionKey;
    }

    public void setMachineOptionKey(MachineOptionKey machineOptionKey) {
        this.machineOptionKey = machineOptionKey;
    }
}
