package com.modis.edu.service.impl;

import com.modis.edu.domain.PreferredModality;
import com.modis.edu.repository.PreferredModalityRepository;
import com.modis.edu.service.PreferredModalityService;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * Service Implementation for managing {@link PreferredModality}.
 */
@Service
public class PreferredModalityServiceImpl implements PreferredModalityService {

    private final Logger log = LoggerFactory.getLogger(PreferredModalityServiceImpl.class);

    private final PreferredModalityRepository preferredModalityRepository;

    public PreferredModalityServiceImpl(PreferredModalityRepository preferredModalityRepository) {
        this.preferredModalityRepository = preferredModalityRepository;
    }

    @Override
    public PreferredModality save(PreferredModality preferredModality) {
        log.debug("Request to save PreferredModality : {}", preferredModality);
        return preferredModalityRepository.save(preferredModality);
    }

    @Override
    public PreferredModality update(PreferredModality preferredModality) {
        log.debug("Request to update PreferredModality : {}", preferredModality);
        return preferredModalityRepository.save(preferredModality);
    }

    @Override
    public Optional<PreferredModality> partialUpdate(PreferredModality preferredModality) {
        log.debug("Request to partially update PreferredModality : {}", preferredModality);

        return preferredModalityRepository
            .findById(preferredModality.getId())
            .map(existingPreferredModality -> {
                if (preferredModality.getModality() != null) {
                    existingPreferredModality.setModality(preferredModality.getModality());
                }

                return existingPreferredModality;
            })
            .map(preferredModalityRepository::save);
    }

    @Override
    public List<PreferredModality> findAll() {
        log.debug("Request to get all PreferredModalities");
        return preferredModalityRepository.findAll();
    }

    @Override
    public Optional<PreferredModality> findOne(String id) {
        log.debug("Request to get PreferredModality : {}", id);
        return preferredModalityRepository.findById(id);
    }

    @Override
    public void delete(String id) {
        log.debug("Request to delete PreferredModality : {}", id);
        preferredModalityRepository.deleteById(id);
    }
}
