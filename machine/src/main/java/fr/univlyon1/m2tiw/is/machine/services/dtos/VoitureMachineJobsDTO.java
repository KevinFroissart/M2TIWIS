package fr.univlyon1.m2tiw.is.machine.services.dtos;

public class VoitureMachineJobsDTO {

	private Long voitureId;
	private String machineQueue;

	public VoitureMachineJobsDTO() {
	}

	public VoitureMachineJobsDTO(Long voitureId, String machineQueue) {
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
