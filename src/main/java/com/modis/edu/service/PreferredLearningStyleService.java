package com.modis.edu.service;

import com.modis.edu.domain.PreferredLearningStyle;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link PreferredLearningStyle}.
 */
public interface PreferredLearningStyleService {
    /**
     * Save a preferredLearningStyle.
     *
     * @param preferredLearningStyle the entity to save.
     * @return the persisted entity.
     */
    PreferredLearningStyle save(PreferredLearningStyle preferredLearningStyle);

    /**
     * Updates a preferredLearningStyle.
     *
     * @param preferredLearningStyle the entity to update.
     * @return the persisted entity.
     */
    PreferredLearningStyle update(PreferredLearningStyle preferredLearningStyle);

    /**
     * Partially updates a preferredLearningStyle.
     *
     * @param preferredLearningStyle the entity to update partially.
     * @return the persisted entity.
     */
    Optional<PreferredLearningStyle> partialUpdate(PreferredLearningStyle preferredLearningStyle);

    /**
     * Get all the preferredLearningStyles.
     *
     * @return the list of entities.
     */
    List<PreferredLearningStyle> findAll();

    /**
     * Get the "id" preferredLearningStyle.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<PreferredLearningStyle> findOne(String id);

    /**
     * Delete the "id" preferredLearningStyle.
     *
     * @param id the id of the entity.
     */
    void delete(String id);
}
