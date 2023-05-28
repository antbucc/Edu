package com.modis.edu.service;

import com.modis.edu.domain.LearningDisability;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link LearningDisability}.
 */
public interface LearningDisabilityService {
    /**
     * Save a learningDisability.
     *
     * @param learningDisability the entity to save.
     * @return the persisted entity.
     */
    LearningDisability save(LearningDisability learningDisability);

    /**
     * Updates a learningDisability.
     *
     * @param learningDisability the entity to update.
     * @return the persisted entity.
     */
    LearningDisability update(LearningDisability learningDisability);

    /**
     * Partially updates a learningDisability.
     *
     * @param learningDisability the entity to update partially.
     * @return the persisted entity.
     */
    Optional<LearningDisability> partialUpdate(LearningDisability learningDisability);

    /**
     * Get all the learningDisabilities.
     *
     * @return the list of entities.
     */
    List<LearningDisability> findAll();

    /**
     * Get the "id" learningDisability.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<LearningDisability> findOne(String id);

    /**
     * Delete the "id" learningDisability.
     *
     * @param id the id of the entity.
     */
    void delete(String id);
}
