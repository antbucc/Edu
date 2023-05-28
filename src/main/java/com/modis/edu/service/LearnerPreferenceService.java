package com.modis.edu.service;

import com.modis.edu.domain.LearnerPreference;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link LearnerPreference}.
 */
public interface LearnerPreferenceService {
    /**
     * Save a learnerPreference.
     *
     * @param learnerPreference the entity to save.
     * @return the persisted entity.
     */
    LearnerPreference save(LearnerPreference learnerPreference);

    /**
     * Updates a learnerPreference.
     *
     * @param learnerPreference the entity to update.
     * @return the persisted entity.
     */
    LearnerPreference update(LearnerPreference learnerPreference);

    /**
     * Partially updates a learnerPreference.
     *
     * @param learnerPreference the entity to update partially.
     * @return the persisted entity.
     */
    Optional<LearnerPreference> partialUpdate(LearnerPreference learnerPreference);

    /**
     * Get all the learnerPreferences.
     *
     * @return the list of entities.
     */
    List<LearnerPreference> findAll();

    /**
     * Get all the learnerPreferences with eager load of many-to-many relationships.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<LearnerPreference> findAllWithEagerRelationships(Pageable pageable);

    /**
     * Get the "id" learnerPreference.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<LearnerPreference> findOne(String id);

    /**
     * Delete the "id" learnerPreference.
     *
     * @param id the id of the entity.
     */
    void delete(String id);
}
