package fr.univlyon1.m2tiw.is.catalogue.service;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class NoSuchOptionException extends Exception {
    private String nom;

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public NoSuchOptionException(String nom) {
        super("Option " + nom + " non trouvée");
        this.nom = nom;
    }

    public NoSuchOptionException(String message, String nom) {
        super(message);
        this.nom = nom;
    }

    public NoSuchOptionException(String message, Throwable cause, String nom) {
        super(message, cause);
        this.nom = nom;
    }

    public NoSuchOptionException(Throwable cause, String nom) {
        super(cause);
        this.nom = nom;
    }

    public NoSuchOptionException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, String nom) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.nom = nom;
    }
}
