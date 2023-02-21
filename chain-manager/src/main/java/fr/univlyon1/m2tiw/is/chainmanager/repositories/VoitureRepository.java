package fr.univlyon1.m2tiw.is.chainmanager.repositories;

import fr.univlyon1.m2tiw.is.chainmanager.models.Statut;
import fr.univlyon1.m2tiw.is.chainmanager.models.Voiture;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface VoitureRepository extends JpaRepository<Voiture,Long> {
    boolean existsByStatut(Statut statut);
    Collection<Voiture> findVoituresByStatut(Statut statut);
}
