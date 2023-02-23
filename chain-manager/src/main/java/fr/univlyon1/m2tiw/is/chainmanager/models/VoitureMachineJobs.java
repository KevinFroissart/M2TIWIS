package fr.univlyon1.m2tiw.is.chainmanager.models;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;

@Entity(name = "voiture_machine_jobs")
public class VoitureMachineJobs {

	@EmbeddedId
	private VoitureMachineJobsId id;

	@Column(name = "machine_jobs")
	private Statut statut = Statut.AFAIRE;

	public VoitureMachineJobs() {}

	public VoitureMachineJobs(VoitureMachineJobsId id, Statut statut) {
		this.id = id;
		this.statut = statut;
	}

	public VoitureMachineJobsId getId() {
		return id;
	}

	public void setId(VoitureMachineJobsId id) {
		this.id = id;
	}

	public Statut getStatut() {
		return statut;
	}

	public void setStatut(Statut statut) {
		this.statut = statut;
	}
}
