package fr.univlyon1.m2tiw.is.chainmanager.services.dtos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class MachineDTO {
    public Long id;
    public String queue;
}
