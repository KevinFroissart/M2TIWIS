package fr.univlyon1.m2tiw.is.chainmanager.repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;

import fr.univlyon1.m2tiw.is.chainmanager.models.VoitureMachineJobs;
import fr.univlyon1.m2tiw.is.chainmanager.models.VoitureMachineJobsId;

public interface VoitureMachineJobsRepository extends JpaRepository<VoitureMachineJobs, VoitureMachineJobsId> {

	Collection<VoitureMachineJobs> findAllById_VoitureId(Long voitureId);
}
