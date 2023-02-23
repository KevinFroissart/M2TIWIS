package fr.univlyon1.m2tiw.is.chainmanager.models;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class VoitureMachineJobsId implements Serializable {

	@Column(name = "voiture_id")
	private Long voitureId;

	@Column(name = "machine_jobs_key")
	private String machineQueue;

	public VoitureMachineJobsId() {}

	public VoitureMachineJobsId(Long voitureId, String machineQueue) {
		this.voitureId = voitureId;
		this.machineQueue = machineQueue;
	}

	public Long getVoitureId() {
		return voitureId;
	}

	public void setVoitureId(Long voitureId) {
		this.voitureId = voitureId;
	}

	public String getMachineQueue() {
		return machineQueue;
	}

	public void setMachineQueue(String machineQueue) {
		this.machineQueue = machineQueue;
	}
}
