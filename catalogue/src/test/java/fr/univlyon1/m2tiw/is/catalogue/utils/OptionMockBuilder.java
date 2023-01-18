package fr.univlyon1.m2tiw.is.catalogue.utils;

import fr.univlyon1.m2tiw.is.catalogue.model.Option;
import org.mockito.Mockito;

public class OptionMockBuilder {

    private String nom;
    private String description;

    public Option build() {
        final Option mock = Mockito.mock(Option.class);

        Mockito.when(mock.getNom()).thenReturn(nom);
        Mockito.when(mock.getDescription()).thenReturn(description);

        return mock;
    }

    public OptionMockBuilder setNom(String nom) {
        this.nom = nom;
        return this;
    }

    public OptionMockBuilder setDescription(String description) {
        this.description = description;
        return this;
    }
}
