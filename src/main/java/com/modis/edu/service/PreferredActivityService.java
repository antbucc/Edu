package com.modis.edu.service;

import com.modis.edu.domain.PreferredActivity;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link PreferredActivity}.
 */
public interface PreferredActivityService {
    /**
     * Save a preferredActivity.
     *
     * @param preferredActivity the entity to save.
     * @return the persisted entity.
     */
    PreferredActivity save(PreferredActivity preferredActivity);

    /**
     * Updates a preferredActivity.
     *
     * @param preferredActivity the entity to update.
     * @return the persisted entity.
     */
    PreferredActivity update(PreferredActivity preferredActivity);

    /**
     * Partially updates a preferredActivity.
     *
     * @param preferredActivity the entity to update partially.
     * @return the persisted entity.
     */
    Optional<PreferredActivity> partialUpdate(PreferredActivity preferredActivity);

    /**
     * Get all the preferredActivities.
     *
     * @return the list of entities.
     */
    List<PreferredActivity> findAll();

    /**
     * Get the "id" preferredActivity.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<PreferredActivity> findOne(String id);

    /**
     * Delete the "id" preferredActivity.
     *
     * @param id the id of the entity.
     */
    void delete(String id);
}
