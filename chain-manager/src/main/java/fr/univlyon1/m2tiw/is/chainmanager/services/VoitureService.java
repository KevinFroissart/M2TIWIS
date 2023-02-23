package fr.univlyon1.m2tiw.is.chainmanager.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import fr.univlyon1.m2tiw.is.chainmanager.models.Statut;
import fr.univlyon1.m2tiw.is.chainmanager.models.Voiture;
import fr.univlyon1.m2tiw.is.chainmanager.models.VoitureMachineJobs;
import fr.univlyon1.m2tiw.is.chainmanager.models.VoitureMachineJobsId;
import fr.univlyon1.m2tiw.is.chainmanager.repositories.VoitureMachineJobsRepository;
import fr.univlyon1.m2tiw.is.chainmanager.repositories.VoitureRepository;
import fr.univlyon1.m2tiw.is.chainmanager.services.dtos.MachineDTO;
import fr.univlyon1.m2tiw.is.chainmanager.services.dtos.VoitureDTO;
import lombok.extern.slf4j.Slf4j;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.NoSuchElementException;
import java.util.Optional;

@Slf4j
@Service
@Transactional
public class VoitureService {
    private final VoitureRepository voitureRepository;
    private final VoitureMachineJobsRepository voitureMachineJobsRepository;
    private final MachineService machineService;

    public VoitureService(VoitureRepository voitureRepository, VoitureMachineJobsRepository voitureMachineJobsRepository , MachineService machineService) {
        this.voitureRepository = voitureRepository;
        this.voitureMachineJobsRepository = voitureMachineJobsRepository;
        this.machineService = machineService;
    }

    public VoitureDTO ajouteVoiture(VoitureDTO voitureDTO) throws JsonProcessingException {
        Voiture voiture = new Voiture();
        voiture.setStatut(Statut.AFAIRE);
        voiture.setOptions(new ArrayList<>(voitureDTO.getOptions()));
        voiture = voitureRepository.save(voiture);
        demarreConfigurationVoiture();
        return new VoitureDTO(voiture);
    }

    public Collection<VoitureDTO> getAllVoitures() {
        return voitureRepository.findAll().stream().map(VoitureDTO::new).toList();
    }

    /**
     * Envoie les options d'une voiture à toutes les machines.
     *
     * @param voiture la voiture et ses options.
     */
    public void envoieOptions(Voiture voiture) throws JsonProcessingException {
        for (MachineDTO m : machineService.getMachines()) {
            machineService.envoieOptionsVoiture(m.queue, voiture);
        }
    }

    /**
     * Lance la reconfiguration des machines pour une voiture si aucune reconfiguration n'est en cours.
     */
    public void demarreConfigurationVoiture() throws JsonProcessingException {
        if (!reconfigurationEnCours()) {
            var voituresAFaire = voitureRepository.findVoituresByStatut(Statut.AFAIRE);
            var voitureOpt = voituresAFaire.stream().findFirst();
            if (voitureOpt.isPresent()) {
                var voiture = voitureOpt.get();
                voiture.setStatut(Statut.DEMARRE);
                voitureRepository.save(voiture);
                envoieOptions(voiture);
            } else {
                log.info("Pas de nouveau lancement de reconfiguration à effectuer");
            }
        } else {
            log.info("Reconfiguration en cours, pas de nouveau lancement de reconfiguration");
        }
    }

    /**
     * Teste si une reconfiguration est en cours.
     *
     * @return true si une reconfiguration est en cours.
     */
    public boolean reconfigurationEnCours() {
        return voitureRepository.existsByStatut(Statut.DEMARRE);
    }

    /**
     * Reçoit la confirmation d'une machine que la reconfiguration est terminée.
     *
     * @param payload l'id de la voiture et de la machine reconfigurée.
     */
    @RabbitListener(queues = "${tiw.is.chainmanager.confirm-queue}")
    public void receiveConfirmation(String payload) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            VoitureMachineJobsId voitureMachineJobsId = objectMapper.readValue(payload, VoitureMachineJobsId.class);
            log.info("Voiture {} reçue dans la queue de confirmation", voitureMachineJobsId.getVoitureId());

            VoitureMachineJobs voitureMachineJobs = voitureMachineJobsRepository.findById(voitureMachineJobsId).orElseThrow(NoSuchElementException::new);
            voitureMachineJobs.setStatut(Statut.TERMINE);
            voitureMachineJobsRepository.save(voitureMachineJobs);

            terminerConfigurationVoiture(voitureMachineJobsId.getVoitureId());
        } catch (IOException e) {
            log.error("Erreur de désérialisation du message de confirmation: ", e);
        }
    }

    /**
     * Termine la configuration d'une voiture si toutes les machines ont terminé.
     *
     * @param voitureId l'id de la voiture à terminer.
     */
    public void terminerConfigurationVoiture(Long voitureId) {
        if (isConfigurationOver(voitureId)) {
            log.info("Toutes les machines ont terminé la reconfiguration de la voiture {}", voitureId);
            Voiture voiture = voitureRepository.findById(voitureId).orElseThrow(NoSuchElementException::new);
            voiture.setStatut(Statut.TERMINE);
            voitureRepository.save(voiture);
        }
    }

    /**
     * Teste si toutes les machines ont terminé la configuration d'une voiture donnée.
     *
     * @param voitureId l'id de la voiture à tester.
     * @return true si toutes les machines ont terminé la configuration.
     */
    private boolean isConfigurationOver(Long voitureId) {
        Collection<VoitureMachineJobs> voitureMachineJobs = voitureMachineJobsRepository.findAllById_VoitureId(voitureId);
        return voitureMachineJobs.stream().allMatch(vmj -> vmj.getStatut() == Statut.TERMINE);
    }

    /**
     * Permet de marquer une clé voiture/machine comme en cours de reconfiguration.
     */
    @RabbitListener(queues = "${tiw.is.chainmanager.config-queue}")
    public void reconfigureInProgress(String payload) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        VoitureMachineJobsId voitureMachineJobsId = objectMapper.readValue(payload, VoitureMachineJobsId.class);

        Long voitureId = voitureMachineJobsId.getVoitureId();
        String machineQueue = voitureMachineJobsId.getMachineQueue();

        // Vérifier si une entrée est déjà présente pour les paramètres donnés
        Optional<VoitureMachineJobs> voitureMachineJobsOptional = voitureMachineJobsRepository.findById(new VoitureMachineJobsId(voitureId, machineQueue));
        if (voitureMachineJobsOptional.isPresent()) {
            // Une entrée existe déjà, ne rien faire
            return;
        }

        log.info("Reconfiguration en cours pour la voiture {} et la machine qui a pour queue : {}", voitureId, machineQueue);

        // Ajouter une entrée en base avec le statut DEMARRE
        VoitureMachineJobs voitureMachineJobs = new VoitureMachineJobs(voitureMachineJobsId, Statut.DEMARRE);
        voitureMachineJobsRepository.save(voitureMachineJobs);
    }
}
