package fr.univlyon1.m2tiw.is.catalogue.utils;

import fr.univlyon1.m2tiw.is.catalogue.service.dto.OptionDTO;
import org.mockito.Mockito;

public class OptionDTOMockBuilder {

    private String nom;
    private String description;

    public OptionDTO build() {
        final OptionDTO mock = Mockito.mock(OptionDTO.class);

        Mockito.when(mock.getNom()).thenReturn(nom);
        Mockito.when(mock.getDescription()).thenReturn(description);
        Mockito.when(mock.toOption()).thenCallRealMethod();

        return mock;
    }

    public OptionDTOMockBuilder setNom(String nom) {
        this.nom = nom;
        return this;
    }

    public OptionDTOMockBuilder setDescription(String description) {
        this.description = description;
        return this;
    }
}
