package fr.univlyon1.m2tiw.is.chainmanager.models;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class StatutInconnuException extends Exception{
    public String statut;

    public StatutInconnuException(String statut) {
        this.statut = statut;
    }

    public StatutInconnuException(String message, String statut) {
        super(message);
        this.statut = statut;
    }

    public StatutInconnuException(String message, Throwable cause, String statut) {
        super(message, cause);
        this.statut = statut;
    }

    public StatutInconnuException(Throwable cause, String statut) {
        super(cause);
        this.statut = statut;
    }

    public StatutInconnuException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, String statut) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.statut = statut;
    }

    public String getStatut() {
        return statut;
    }

    public void setStatut(String statut) {
        this.statut = statut;
    }
}
