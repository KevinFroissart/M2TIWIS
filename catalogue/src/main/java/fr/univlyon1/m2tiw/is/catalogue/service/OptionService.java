package fr.univlyon1.m2tiw.is.catalogue.service;

import fr.univlyon1.m2tiw.is.catalogue.model.Option;
import fr.univlyon1.m2tiw.is.catalogue.repository.OptionRepository;
import fr.univlyon1.m2tiw.is.catalogue.service.dto.OptionDTO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

@Service
@Transactional
public class OptionService {
    private OptionRepository optionRepository;

    public OptionService(OptionRepository optionRepository) {
        this.optionRepository = optionRepository;
    }

    public OptionDTO createOption(OptionDTO optionDTO) {
        Option option = optionDTO.asOption();
        option = optionRepository.save(option);
        return new OptionDTO(option);
    }

    public OptionDTO getOption(String nom) throws NoSuchOptionException {
        return new OptionDTO(optionRepository
                .findById(nom)
                .orElseThrow(() -> new NoSuchOptionException(nom)));
    }

    public void deleteOption(String nom) throws NoSuchOptionException {
        if (!optionRepository.existsById(nom)) {
            throw new NoSuchOptionException(nom);
        }
        optionRepository.deleteById(nom);
    }

    public Collection<OptionDTO> getAllOptions() {
        return optionRepository.findAll().stream().map(OptionDTO::new).toList();
    }
}
