package com.modis.edu.service.impl;

import com.modis.edu.domain.LearningDisability;
import com.modis.edu.repository.LearningDisabilityRepository;
import com.modis.edu.service.LearningDisabilityService;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * Service Implementation for managing {@link LearningDisability}.
 */
@Service
public class LearningDisabilityServiceImpl implements LearningDisabilityService {

    private final Logger log = LoggerFactory.getLogger(LearningDisabilityServiceImpl.class);

    private final LearningDisabilityRepository learningDisabilityRepository;

    public LearningDisabilityServiceImpl(LearningDisabilityRepository learningDisabilityRepository) {
        this.learningDisabilityRepository = learningDisabilityRepository;
    }

    @Override
    public LearningDisability save(LearningDisability learningDisability) {
        log.debug("Request to save LearningDisability : {}", learningDisability);
        return learningDisabilityRepository.save(learningDisability);
    }

    @Override
    public LearningDisability update(LearningDisability learningDisability) {
        log.debug("Request to update LearningDisability : {}", learningDisability);
        return learningDisabilityRepository.save(learningDisability);
    }

    @Override
    public Optional<LearningDisability> partialUpdate(LearningDisability learningDisability) {
        log.debug("Request to partially update LearningDisability : {}", learningDisability);

        return learningDisabilityRepository
            .findById(learningDisability.getId())
            .map(existingLearningDisability -> {
                if (learningDisability.getName() != null) {
                    existingLearningDisability.setName(learningDisability.getName());
                }
                if (learningDisability.getDescription() != null) {
                    existingLearningDisability.setDescription(learningDisability.getDescription());
                }
                if (learningDisability.getDisabilityType() != null) {
                    existingLearningDisability.setDisabilityType(learningDisability.getDisabilityType());
                }

                return existingLearningDisability;
            })
            .map(learningDisabilityRepository::save);
    }

    @Override
    public List<LearningDisability> findAll() {
        log.debug("Request to get all LearningDisabilities");
        return learningDisabilityRepository.findAll();
    }

    @Override
    public Optional<LearningDisability> findOne(String id) {
        log.debug("Request to get LearningDisability : {}", id);
        return learningDisabilityRepository.findById(id);
    }

    @Override
    public void delete(String id) {
        log.debug("Request to delete LearningDisability : {}", id);
        learningDisabilityRepository.deleteById(id);
    }
}
