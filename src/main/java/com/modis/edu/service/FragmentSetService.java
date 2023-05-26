package com.modis.edu.service;

import com.modis.edu.domain.FragmentSet;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link FragmentSet}.
 */
public interface FragmentSetService {
    /**
     * Save a fragmentSet.
     *
     * @param fragmentSet the entity to save.
     * @return the persisted entity.
     */
    FragmentSet save(FragmentSet fragmentSet);

    /**
     * Updates a fragmentSet.
     *
     * @param fragmentSet the entity to update.
     * @return the persisted entity.
     */
    FragmentSet update(FragmentSet fragmentSet);

    /**
     * Partially updates a fragmentSet.
     *
     * @param fragmentSet the entity to update partially.
     * @return the persisted entity.
     */
    Optional<FragmentSet> partialUpdate(FragmentSet fragmentSet);

    /**
     * Get all the fragmentSets.
     *
     * @return the list of entities.
     */
    List<FragmentSet> findAll();

    /**
     * Get the "id" fragmentSet.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<FragmentSet> findOne(String id);

    /**
     * Delete the "id" fragmentSet.
     *
     * @param id the id of the entity.
     */
    void delete(String id);
}
