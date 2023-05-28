package com.modis.edu.service.impl;

import com.modis.edu.domain.PreferredLearningStyle;
import com.modis.edu.repository.PreferredLearningStyleRepository;
import com.modis.edu.service.PreferredLearningStyleService;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * Service Implementation for managing {@link PreferredLearningStyle}.
 */
@Service
public class PreferredLearningStyleServiceImpl implements PreferredLearningStyleService {

    private final Logger log = LoggerFactory.getLogger(PreferredLearningStyleServiceImpl.class);

    private final PreferredLearningStyleRepository preferredLearningStyleRepository;

    public PreferredLearningStyleServiceImpl(PreferredLearningStyleRepository preferredLearningStyleRepository) {
        this.preferredLearningStyleRepository = preferredLearningStyleRepository;
    }

    @Override
    public PreferredLearningStyle save(PreferredLearningStyle preferredLearningStyle) {
        log.debug("Request to save PreferredLearningStyle : {}", preferredLearningStyle);
        return preferredLearningStyleRepository.save(preferredLearningStyle);
    }

    @Override
    public PreferredLearningStyle update(PreferredLearningStyle preferredLearningStyle) {
        log.debug("Request to update PreferredLearningStyle : {}", preferredLearningStyle);
        return preferredLearningStyleRepository.save(preferredLearningStyle);
    }

    @Override
    public Optional<PreferredLearningStyle> partialUpdate(PreferredLearningStyle preferredLearningStyle) {
        log.debug("Request to partially update PreferredLearningStyle : {}", preferredLearningStyle);

        return preferredLearningStyleRepository
            .findById(preferredLearningStyle.getId())
            .map(existingPreferredLearningStyle -> {
                if (preferredLearningStyle.getStyle() != null) {
                    existingPreferredLearningStyle.setStyle(preferredLearningStyle.getStyle());
                }

                return existingPreferredLearningStyle;
            })
            .map(preferredLearningStyleRepository::save);
    }

    @Override
    public List<PreferredLearningStyle> findAll() {
        log.debug("Request to get all PreferredLearningStyles");
        return preferredLearningStyleRepository.findAll();
    }

    @Override
    public Optional<PreferredLearningStyle> findOne(String id) {
        log.debug("Request to get PreferredLearningStyle : {}", id);
        return preferredLearningStyleRepository.findById(id);
    }

    @Override
    public void delete(String id) {
        log.debug("Request to delete PreferredLearningStyle : {}", id);
        preferredLearningStyleRepository.deleteById(id);
    }
}
