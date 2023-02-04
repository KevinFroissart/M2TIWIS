package fr.univlyon1.m2tiw.is.commandes.dao;

/**
 * Exception levée lorsqu'une entité n'est pas trouvée.
 */
public class NotFoundException extends Exception {
    public NotFoundException() {
        super();
    }

    public NotFoundException(String message) {
        super(message);
    }

}
