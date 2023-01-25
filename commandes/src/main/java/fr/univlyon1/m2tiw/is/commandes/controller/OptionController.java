package fr.univlyon1.m2tiw.is.commandes.controller;

import java.sql.SQLException;
import java.util.Collection;

import fr.univlyon1.m2tiw.is.commandes.model.Option;
import fr.univlyon1.m2tiw.is.commandes.services.OptionService;

public class OptionController {

	private final OptionService optionService;

	public OptionController(OptionService optionService) {
		this.optionService = optionService;
	}

	public Collection<Option> getAllOptions() throws SQLException {
		return optionService.getAllOptions();
	}

}
