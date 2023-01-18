package fr.univlyon1.m2tiw.is.catalogue.service;

import fr.univlyon1.m2tiw.is.catalogue.model.Option;
import fr.univlyon1.m2tiw.is.catalogue.repository.OptionRepository;
import fr.univlyon1.m2tiw.is.catalogue.service.dto.OptionDTO;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class OptionService {

    private final OptionRepository optionRepository;

    public OptionService(OptionRepository optionRepository) {
        this.optionRepository = optionRepository;
    }

    /**
     * Retourne une collection de {@link OptionDTO}.
     * @return les options.
     */
    public Collection<OptionDTO> getOptions() {
        return optionRepository.findAll().stream().map(OptionDTO::new).toList();
    }

    /**
     * Retourne une {@link OptionDTO} pour un id donné.
     * @param nom le nom de l'option.
     * @return l'option.
     */
    public OptionDTO getOption(String nom) throws NoSuchOptionException {
        return new OptionDTO(
                optionRepository
                        .findById(nom)
                        .orElseThrow(() -> new NoSuchOptionException(nom)));
    }

    /**
     * Crée une {@link OptionDTO}.
     * @param optionDTO l'option à créer.
     * @return l"option créée.
     */
    public OptionDTO createOption(OptionDTO optionDTO) {
        Option option = optionDTO.toOption();
        option = optionRepository.save(option);
        return new OptionDTO(option);
    }

    /**
     * Met à jour une {@link OptionDTO}.
     * @param optionDTO l'option à mettre à jour.
     * @return l'option mise à jour.
     */
    public OptionDTO updateOption(OptionDTO optionDTO) throws NoSuchOptionException {
        Option option = optionRepository
                .findById(optionDTO.getNom())
                .orElseThrow(() -> new NoSuchOptionException(optionDTO.getNom()));
        option.setDescription(optionDTO.getDescription());
        option = optionRepository.save(option);
        return new OptionDTO(option);
    }

    /**
     * Supprime une {@link OptionDTO} pour un id donné.
     * @param id l'id de l'option à supprimer.
     */
    public void deleteOption(String nom)  throws NoSuchOptionException {
        Option option = optionRepository
                .findById(nom)
                .orElseThrow(() -> new NoSuchOptionException(nom));
        optionRepository.delete(option);
    }
}
