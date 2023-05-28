package com.modis.edu.service.impl;

import com.modis.edu.domain.EducatorPreference;
import com.modis.edu.repository.EducatorPreferenceRepository;
import com.modis.edu.service.EducatorPreferenceService;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * Service Implementation for managing {@link EducatorPreference}.
 */
@Service
public class EducatorPreferenceServiceImpl implements EducatorPreferenceService {

    private final Logger log = LoggerFactory.getLogger(EducatorPreferenceServiceImpl.class);

    private final EducatorPreferenceRepository educatorPreferenceRepository;

    public EducatorPreferenceServiceImpl(EducatorPreferenceRepository educatorPreferenceRepository) {
        this.educatorPreferenceRepository = educatorPreferenceRepository;
    }

    @Override
    public EducatorPreference save(EducatorPreference educatorPreference) {
        log.debug("Request to save EducatorPreference : {}", educatorPreference);
        return educatorPreferenceRepository.save(educatorPreference);
    }

    @Override
    public EducatorPreference update(EducatorPreference educatorPreference) {
        log.debug("Request to update EducatorPreference : {}", educatorPreference);
        return educatorPreferenceRepository.save(educatorPreference);
    }

    @Override
    public Optional<EducatorPreference> partialUpdate(EducatorPreference educatorPreference) {
        log.debug("Request to partially update EducatorPreference : {}", educatorPreference);

        return educatorPreferenceRepository
            .findById(educatorPreference.getId())
            .map(existingEducatorPreference -> {
                if (educatorPreference.getTitle() != null) {
                    existingEducatorPreference.setTitle(educatorPreference.getTitle());
                }
                if (educatorPreference.getStyle() != null) {
                    existingEducatorPreference.setStyle(educatorPreference.getStyle());
                }
                if (educatorPreference.getModality() != null) {
                    existingEducatorPreference.setModality(educatorPreference.getModality());
                }
                if (educatorPreference.getDifficulty() != null) {
                    existingEducatorPreference.setDifficulty(educatorPreference.getDifficulty());
                }

                return existingEducatorPreference;
            })
            .map(educatorPreferenceRepository::save);
    }

    @Override
    public List<EducatorPreference> findAll() {
        log.debug("Request to get all EducatorPreferences");
        return educatorPreferenceRepository.findAll();
    }

    public Page<EducatorPreference> findAllWithEagerRelationships(Pageable pageable) {
        return educatorPreferenceRepository.findAllWithEagerRelationships(pageable);
    }

    @Override
    public Optional<EducatorPreference> findOne(String id) {
        log.debug("Request to get EducatorPreference : {}", id);
        return educatorPreferenceRepository.findOneWithEagerRelationships(id);
    }

    @Override
    public void delete(String id) {
        log.debug("Request to delete EducatorPreference : {}", id);
        educatorPreferenceRepository.deleteById(id);
    }
}
