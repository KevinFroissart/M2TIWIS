package fr.univlyon1.m2tiw.is.commandes.services;

/**
 * Exception levée lorsqu'une commande est vide.
 */
public class EmptyCommandeException extends Exception {

    public EmptyCommandeException(String message) {
        super(message);
    }

}
