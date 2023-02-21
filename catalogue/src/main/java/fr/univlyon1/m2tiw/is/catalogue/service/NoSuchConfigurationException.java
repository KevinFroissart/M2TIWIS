package fr.univlyon1.m2tiw.is.catalogue.service;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code= HttpStatus.NOT_FOUND)
public class NoSuchConfigurationException extends Exception {
    public long machineId;
    public String optionNom;

    public NoSuchConfigurationException(long machineId, String optionNom) {
        this.machineId = machineId;
        this.optionNom = optionNom;
    }

    public NoSuchConfigurationException(String message, long machineId, String optionNom) {
        super(message);
        this.machineId = machineId;
        this.optionNom = optionNom;
    }

    public NoSuchConfigurationException(String message, Throwable cause, long machineId, String optionNom) {
        super(message, cause);
        this.machineId = machineId;
        this.optionNom = optionNom;
    }

    public NoSuchConfigurationException(Throwable cause, long machineId, String optionNom) {
        super(cause);
        this.machineId = machineId;
        this.optionNom = optionNom;
    }

    public NoSuchConfigurationException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, long machineId, String optionNom) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.machineId = machineId;
        this.optionNom = optionNom;
    }

    public long getMachineId() {
        return machineId;
    }

    public void setMachineId(long machineId) {
        this.machineId = machineId;
    }

    public String getOptionNom() {
        return optionNom;
    }

    public void setOptionNom(String optionNom) {
        this.optionNom = optionNom;
    }
}
