package fr.univlyon1.m2tiw.is.catalogue.utils;

import fr.univlyon1.m2tiw.is.catalogue.service.dto.MachineDTO;
import org.mockito.Mockito;

public class MachineDTOMockBuilder {

    private Long id;
    private String modele;

    public MachineDTO build() {
        final MachineDTO mock = Mockito.mock(MachineDTO.class);

        Mockito.when(mock.getId()).thenReturn(id);
        Mockito.when(mock.getModele()).thenReturn(modele);
        Mockito.when(mock.toMachine()).thenCallRealMethod();

        return mock;
    }

    public MachineDTOMockBuilder setId(Long id) {
        this.id = id;
        return this;
    }

    public MachineDTOMockBuilder setModele(String modele) {
        this.modele = modele;
        return this;
    }
}
