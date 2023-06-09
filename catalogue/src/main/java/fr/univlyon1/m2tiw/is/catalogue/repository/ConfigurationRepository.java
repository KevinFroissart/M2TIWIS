package fr.univlyon1.m2tiw.is.catalogue.repository;

import fr.univlyon1.m2tiw.is.catalogue.model.Configuration;
import fr.univlyon1.m2tiw.is.catalogue.model.MachineOptionKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConfigurationRepository extends JpaRepository<Configuration, MachineOptionKey> {
}
