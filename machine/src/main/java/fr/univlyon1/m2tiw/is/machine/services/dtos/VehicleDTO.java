package fr.univlyon1.m2tiw.is.machine.services.dtos;

import java.util.Collection;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@JsonDeserialize
public class VehicleDTO {

	private Long id;
	private Collection<String> options;

	public Long getId() {
		return id;
	}

	public Collection<String> getOptions() {
		return options;
	}
}
