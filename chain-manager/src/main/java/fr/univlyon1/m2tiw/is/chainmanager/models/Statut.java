package fr.univlyon1.m2tiw.is.chainmanager.models;

public enum Statut {
    AFAIRE, DEMARRE, TERMINE;

    public static Statut fromString(String s) throws StatutInconnuException {
        try {
            return Statut.valueOf(s.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new StatutInconnuException(s);
        }
    }
}
