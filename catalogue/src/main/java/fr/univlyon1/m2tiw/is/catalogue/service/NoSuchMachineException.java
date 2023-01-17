package fr.univlyon1.m2tiw.is.catalogue.service;

public class NoSuchMachineException extends Exception {
    private long id;

    public NoSuchMachineException(long id) {
        this.id = id;
    }

    public NoSuchMachineException(String message, long id) {
        super(message);
        this.id = id;
    }

    public NoSuchMachineException(String message, Throwable cause, long id) {
        super(message, cause);
        this.id = id;
    }

    public NoSuchMachineException(Throwable cause, long id) {
        super(cause);
        this.id = id;
    }

    public NoSuchMachineException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, long id) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
