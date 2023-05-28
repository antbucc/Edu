package com.modis.edu.service.impl;

import com.modis.edu.domain.PreferredActivity;
import com.modis.edu.repository.PreferredActivityRepository;
import com.modis.edu.service.PreferredActivityService;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * Service Implementation for managing {@link PreferredActivity}.
 */
@Service
public class PreferredActivityServiceImpl implements PreferredActivityService {

    private final Logger log = LoggerFactory.getLogger(PreferredActivityServiceImpl.class);

    private final PreferredActivityRepository preferredActivityRepository;

    public PreferredActivityServiceImpl(PreferredActivityRepository preferredActivityRepository) {
        this.preferredActivityRepository = preferredActivityRepository;
    }

    @Override
    public PreferredActivity save(PreferredActivity preferredActivity) {
        log.debug("Request to save PreferredActivity : {}", preferredActivity);
        return preferredActivityRepository.save(preferredActivity);
    }

    @Override
    public PreferredActivity update(PreferredActivity preferredActivity) {
        log.debug("Request to update PreferredActivity : {}", preferredActivity);
        return preferredActivityRepository.save(preferredActivity);
    }

    @Override
    public Optional<PreferredActivity> partialUpdate(PreferredActivity preferredActivity) {
        log.debug("Request to partially update PreferredActivity : {}", preferredActivity);

        return preferredActivityRepository
            .findById(preferredActivity.getId())
            .map(existingPreferredActivity -> {
                if (preferredActivity.getTitle() != null) {
                    existingPreferredActivity.setTitle(preferredActivity.getTitle());
                }
                if (preferredActivity.getDescription() != null) {
                    existingPreferredActivity.setDescription(preferredActivity.getDescription());
                }
                if (preferredActivity.getType() != null) {
                    existingPreferredActivity.setType(preferredActivity.getType());
                }
                if (preferredActivity.getTool() != null) {
                    existingPreferredActivity.setTool(preferredActivity.getTool());
                }
                if (preferredActivity.getDifficulty() != null) {
                    existingPreferredActivity.setDifficulty(preferredActivity.getDifficulty());
                }

                return existingPreferredActivity;
            })
            .map(preferredActivityRepository::save);
    }

    @Override
    public List<PreferredActivity> findAll() {
        log.debug("Request to get all PreferredActivities");
        return preferredActivityRepository.findAll();
    }

    @Override
    public Optional<PreferredActivity> findOne(String id) {
        log.debug("Request to get PreferredActivity : {}", id);
        return preferredActivityRepository.findById(id);
    }

    @Override
    public void delete(String id) {
        log.debug("Request to delete PreferredActivity : {}", id);
        preferredActivityRepository.deleteById(id);
    }
}
