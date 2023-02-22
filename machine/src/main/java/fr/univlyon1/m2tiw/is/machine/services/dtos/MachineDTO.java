package fr.univlyon1.m2tiw.is.machine.services.dtos;

import java.util.Collection;

import com.fasterxml.jackson.annotation.JsonProperty;

public class MachineDTO {

	public Long id;

	@JsonProperty(value = "modele")
	public String queue;

	public Collection<String> options;

	public Long getId() {
		return id;
	}

	public String getQueue() {
		return queue;
	}

	public void setQueue(String queue) {
		this.queue = queue;
	}

}
