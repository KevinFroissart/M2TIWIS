package fr.univlyon1.m2tiw.is.commandes.services;

public class EmptyCommandeException extends Exception {
    public EmptyCommandeException() {
        super();
    }

    public EmptyCommandeException(String message) {
        super(message);
    }

    public EmptyCommandeException(String message, Throwable cause) {
        super(message, cause);
    }

    public EmptyCommandeException(Throwable cause) {
        super(cause);
    }

    protected EmptyCommandeException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
