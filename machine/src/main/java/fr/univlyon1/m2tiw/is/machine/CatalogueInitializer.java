package fr.univlyon1.m2tiw.is.machine;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import fr.univlyon1.m2tiw.is.machine.services.CatalogueService;

@Component
public class CatalogueInitializer implements CommandLineRunner {

	private final CatalogueService catalogueService;

	@Autowired
	public CatalogueInitializer(CatalogueService catalogueService) {
		this.catalogueService = catalogueService;
	}

	@Override
	public void run(String... args) {
		catalogueService.getOrCreateMachine();
	}
}
