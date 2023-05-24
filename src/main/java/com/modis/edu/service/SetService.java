package com.modis.edu.service;

import com.modis.edu.domain.SetOf;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link SetOf}.
 */
public interface SetService {
    /**
     * Save a set.
     *
     * @param set the entity to save.
     * @return the persisted entity.
     */
    SetOf save(SetOf set);

    /**
     * Updates a set.
     *
     * @param set the entity to update.
     * @return the persisted entity.
     */
    SetOf update(SetOf set);

    /**
     * Partially updates a set.
     *
     * @param set the entity to update partially.
     * @return the persisted entity.
     */
    Optional<SetOf> partialUpdate(SetOf set);

    /**
     * Get all the sets.
     *
     * @return the list of entities.
     */
    List<SetOf> findAll();

    /**
     * Get all the Set where Fragment is {@code null}.
     *
     * @return the {@link List} of entities.
     */
    List<SetOf> findAllWhereFragmentIsNull();

    /**
     * Get the "id" set.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<SetOf> findOne(String id);

    /**
     * Delete the "id" set.
     *
     * @param id the id of the entity.
     */
    void delete(String id);
}
