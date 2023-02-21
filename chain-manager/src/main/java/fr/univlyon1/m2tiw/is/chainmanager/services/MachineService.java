package fr.univlyon1.m2tiw.is.chainmanager.services;

import fr.univlyon1.m2tiw.is.chainmanager.models.Voiture;
import fr.univlyon1.m2tiw.is.chainmanager.services.dtos.MachineDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;

@Slf4j
@Service
public class MachineService {

    public Collection<MachineDTO> getMachines() {
        // TODO: TP3 requêter le catalogue à partir du RestTemplate pour récupérer les machines et leur file rabbitmq
        return new ArrayList<>();
    }

    public void envoieOptionsVoiture(String queueName, Voiture voiture) {
        Collection<String> options = voiture.getOptions();
        log.info("Envoi de des {} options '{}' pour la voiture {} sur la queue '{}'",
                options.size(), options, voiture.getId(), queueName);
        // TODO: TP3 utiliser RabbitTemplate pour envoyer une demande
        //  de reconfiguration sur la queue indiquée
    }
}
