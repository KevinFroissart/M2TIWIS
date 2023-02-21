package fr.univlyon1.m2tiw.is.chainmanager.services;

import fr.univlyon1.m2tiw.is.chainmanager.models.Statut;
import fr.univlyon1.m2tiw.is.chainmanager.models.Voiture;
import fr.univlyon1.m2tiw.is.chainmanager.repositories.VoitureRepository;
import fr.univlyon1.m2tiw.is.chainmanager.services.dtos.MachineDTO;
import fr.univlyon1.m2tiw.is.chainmanager.services.dtos.VoitureDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;

@Slf4j
@Service
@Transactional
public class VoitureService {
    private VoitureRepository voitureRepository;
    private MachineService machineService;

    public VoitureService(VoitureRepository voitureRepository, MachineService machineService) {
        this.voitureRepository = voitureRepository;
        this.machineService = machineService;
    }

    public VoitureDTO ajouteVoiture(VoitureDTO voitureDTO) {
        Voiture voiture = new Voiture();
        voiture.setStatut(Statut.AFAIRE);
        voiture.setOptions(new ArrayList<>(voitureDTO.options));
        voiture = voitureRepository.save(voiture);
        demarreConfigurationVoiture(); // TODO TP3: expliquer la présence de cette ligne
        return new VoitureDTO(voiture);
    }

    public Collection<VoitureDTO> getAllVoitures() {
        return voitureRepository.findAll().stream().map(VoitureDTO::new).toList();
    }

    public void envoieOptions(Voiture voiture) {
        for (MachineDTO m : machineService.getMachines()) {
            machineService.envoieOptionsVoiture(m.queue, voiture);
        }
    }

    /**
     * Lance la reconfiguration des machines pour une voiture si aucune reconfiguration n'est en cours
     */
    public void demarreConfigurationVoiture() {
        if (!reconfigurationEnCours()) { // TODO TP3: expliquer la présence de cette ligne
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
     * Teste si une reconfiguration est en cours
     *
     * @return true si une reconfiguration est en cours
     */
    public boolean reconfigurationEnCours() {
        return voitureRepository.existsByStatut(Statut.DEMARRE);
    }
}
