package fr.univlyon1.m2tiw.is.machine.services.dtos;

public class ConfigurationDTO {

	public MachineDTO machine;
	public OptionDTO option;
	public String cfg;

	public MachineDTO getMachine() {
		return machine;
	}

	public OptionDTO getOption() {
		return option;
	}

	public String getCfg() {
		return cfg;
	}
}
