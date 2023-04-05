package fr.univlyon1.m2tiw.is.catalogue.repository;

import fr.univlyon1.m2tiw.is.catalogue.model.Machine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MachineRepository extends JpaRepository<Machine,Long> {
}
