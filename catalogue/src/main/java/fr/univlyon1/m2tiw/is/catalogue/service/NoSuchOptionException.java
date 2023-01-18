package fr.univlyon1.m2tiw.is.catalogue.service;

public class NoSuchOptionException extends Exception {

    private String nom;

    public NoSuchOptionException(String nom) {
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

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }
}
