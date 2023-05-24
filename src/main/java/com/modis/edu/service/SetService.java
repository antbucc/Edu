package com.modis.edu.service;

import com.modis.edu.domain.Set;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link Set}.
 */
public interface SetService {
    /**
     * Save a set.
     *
     * @param set the entity to save.
     * @return the persisted entity.
     */
    Set save(Set set);

    /**
     * Updates a set.
     *
     * @param set the entity to update.
     * @return the persisted entity.
     */
    Set update(Set set);

    /**
     * Partially updates a set.
     *
     * @param set the entity to update partially.
     * @return the persisted entity.
     */
    Optional<Set> partialUpdate(Set set);

    /**
     * Get all the sets.
     *
     * @return the list of entities.
     */
    List<Set> findAll();

    /**
     * Get the "id" set.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Set> findOne(String id);

    /**
     * Delete the "id" set.
     *
     * @param id the id of the entity.
     */
    void delete(String id);
}
