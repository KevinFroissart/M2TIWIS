package fr.univlyon1.m2tiw.is.machine.services.dtos;

public class ConfigurationDTO {

	private Long machineId;
	private String optionId;
	private String cfg;

	public Long getMachineId() {
		return machineId;
	}

	public String getOptionId() {
		return optionId;
	}

	public String getCfg() {
		return cfg;
	}

	public void setMachineId(Long machineId) {
		this.machineId = machineId;
	}

	public void setOptionId(String optionId) {
		this.optionId = optionId;
	}

	public void setCfg(String cfg) {
		this.cfg = cfg;
	}
}
