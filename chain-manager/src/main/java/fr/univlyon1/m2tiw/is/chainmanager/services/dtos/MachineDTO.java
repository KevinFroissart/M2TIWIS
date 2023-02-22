package fr.univlyon1.m2tiw.is.chainmanager.services.dtos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class MachineDTO {

	public Long id;

	@JsonProperty(value = "modele")
	public String queue;
}
