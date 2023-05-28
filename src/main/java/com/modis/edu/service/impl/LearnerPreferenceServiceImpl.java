package com.modis.edu.service.impl;

import com.modis.edu.domain.LearnerPreference;
import com.modis.edu.repository.LearnerPreferenceRepository;
import com.modis.edu.service.LearnerPreferenceService;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * Service Implementation for managing {@link LearnerPreference}.
 */
@Service
public class LearnerPreferenceServiceImpl implements LearnerPreferenceService {

    private final Logger log = LoggerFactory.getLogger(LearnerPreferenceServiceImpl.class);

    private final LearnerPreferenceRepository learnerPreferenceRepository;

    public LearnerPreferenceServiceImpl(LearnerPreferenceRepository learnerPreferenceRepository) {
        this.learnerPreferenceRepository = learnerPreferenceRepository;
    }

    @Override
    public LearnerPreference save(LearnerPreference learnerPreference) {
        log.debug("Request to save LearnerPreference : {}", learnerPreference);
        return learnerPreferenceRepository.save(learnerPreference);
    }

    @Override
    public LearnerPreference update(LearnerPreference learnerPreference) {
        log.debug("Request to update LearnerPreference : {}", learnerPreference);
        return learnerPreferenceRepository.save(learnerPreference);
    }

    @Override
    public Optional<LearnerPreference> partialUpdate(LearnerPreference learnerPreference) {
        log.debug("Request to partially update LearnerPreference : {}", learnerPreference);

        return learnerPreferenceRepository
            .findById(learnerPreference.getId())
            .map(existingLearnerPreference -> {
                if (learnerPreference.getTitle() != null) {
                    existingLearnerPreference.setTitle(learnerPreference.getTitle());
                }
                if (learnerPreference.getStyle() != null) {
                    existingLearnerPreference.setStyle(learnerPreference.getStyle());
                }
                if (learnerPreference.getModality() != null) {
                    existingLearnerPreference.setModality(learnerPreference.getModality());
                }
                if (learnerPreference.getDifficulty() != null) {
                    existingLearnerPreference.setDifficulty(learnerPreference.getDifficulty());
                }
                if (learnerPreference.getDisability() != null) {
                    existingLearnerPreference.setDisability(learnerPreference.getDisability());
                }

                return existingLearnerPreference;
            })
            .map(learnerPreferenceRepository::save);
    }

    @Override
    public List<LearnerPreference> findAll() {
        log.debug("Request to get all LearnerPreferences");
        return learnerPreferenceRepository.findAll();
    }

    public Page<LearnerPreference> findAllWithEagerRelationships(Pageable pageable) {
        return learnerPreferenceRepository.findAllWithEagerRelationships(pageable);
    }

    @Override
    public Optional<LearnerPreference> findOne(String id) {
        log.debug("Request to get LearnerPreference : {}", id);
        return learnerPreferenceRepository.findOneWithEagerRelationships(id);
    }

    @Override
    public void delete(String id) {
        log.debug("Request to delete LearnerPreference : {}", id);
        learnerPreferenceRepository.deleteById(id);
    }
}
