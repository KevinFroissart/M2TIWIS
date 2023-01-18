package fr.univlyon1.m2tiw.is.catalogue.utils;

import fr.univlyon1.m2tiw.is.catalogue.model.Machine;
import fr.univlyon1.m2tiw.is.catalogue.service.dto.MachineDTO;
import org.mockito.Mockito;

public class MachineMockBuilder {
    private Long id;
    private String modele;

    public Machine build() {
        final Machine mock = Mockito.mock(Machine.class);

        Mockito.when(mock.getId()).thenReturn(id);
        Mockito.when(mock.getModele()).thenReturn(modele);

        return mock;
    }

    public MachineMockBuilder setId(Long id) {
        this.id = id;
        return this;
    }

    public MachineMockBuilder setModele(String modele) {
        this.modele = modele;
        return this;
    }
}
