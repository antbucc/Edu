package com.modis.edu.service;

import com.modis.edu.domain.PreferredModality;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link PreferredModality}.
 */
public interface PreferredModalityService {
    /**
     * Save a preferredModality.
     *
     * @param preferredModality the entity to save.
     * @return the persisted entity.
     */
    PreferredModality save(PreferredModality preferredModality);

    /**
     * Updates a preferredModality.
     *
     * @param preferredModality the entity to update.
     * @return the persisted entity.
     */
    PreferredModality update(PreferredModality preferredModality);

    /**
     * Partially updates a preferredModality.
     *
     * @param preferredModality the entity to update partially.
     * @return the persisted entity.
     */
    Optional<PreferredModality> partialUpdate(PreferredModality preferredModality);

    /**
     * Get all the preferredModalities.
     *
     * @return the list of entities.
     */
    List<PreferredModality> findAll();

    /**
     * Get the "id" preferredModality.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<PreferredModality> findOne(String id);

    /**
     * Delete the "id" preferredModality.
     *
     * @param id the id of the entity.
     */
    void delete(String id);
}
