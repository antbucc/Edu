package com.modis.edu.service;

import com.modis.edu.domain.Goal;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link Goal}.
 */
public interface GoalService {
    /**
     * Save a goal.
     *
     * @param goal the entity to save.
     * @return the persisted entity.
     */
    Goal save(Goal goal);

    /**
     * Updates a goal.
     *
     * @param goal the entity to update.
     * @return the persisted entity.
     */
    Goal update(Goal goal);

    /**
     * Partially updates a goal.
     *
     * @param goal the entity to update partially.
     * @return the persisted entity.
     */
    Optional<Goal> partialUpdate(Goal goal);

    /**
     * Get all the goals.
     *
     * @return the list of entities.
     */
    List<Goal> findAll();

    /**
     * Get the "id" goal.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Goal> findOne(String id);

    /**
     * Delete the "id" goal.
     *
     * @param id the id of the entity.
     */
    void delete(String id);
}
