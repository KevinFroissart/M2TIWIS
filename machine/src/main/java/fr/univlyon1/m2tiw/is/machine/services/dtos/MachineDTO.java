package fr.univlyon1.m2tiw.is.machine.services.dtos;

public class MachineDTO {
	public Long id;
	public String queue;

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
