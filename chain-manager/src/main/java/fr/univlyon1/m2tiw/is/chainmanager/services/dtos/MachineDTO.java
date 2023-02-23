package fr.univlyon1.m2tiw.is.chainmanager.services.dtos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class MachineDTO {

	private Long id;

	@JsonProperty(value = "modele")
	public String queue;
}
