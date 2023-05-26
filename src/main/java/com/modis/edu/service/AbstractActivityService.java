package com.modis.edu.service;

import com.modis.edu.domain.AbstractActivity;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link AbstractActivity}.
 */
public interface AbstractActivityService {
    /**
     * Save a abstractActivity.
     *
     * @param abstractActivity the entity to save.
     * @return the persisted entity.
     */
    AbstractActivity save(AbstractActivity abstractActivity);

    /**
     * Updates a abstractActivity.
     *
     * @param abstractActivity the entity to update.
     * @return the persisted entity.
     */
    AbstractActivity update(AbstractActivity abstractActivity);

    /**
     * Partially updates a abstractActivity.
     *
     * @param abstractActivity the entity to update partially.
     * @return the persisted entity.
     */
    Optional<AbstractActivity> partialUpdate(AbstractActivity abstractActivity);

    /**
     * Get all the abstractActivities.
     *
     * @return the list of entities.
     */
    List<AbstractActivity> findAll();
    /**
     * Get all the AbstractActivity where Fragment is {@code null}.
     *
     * @return the {@link List} of entities.
     */
    List<AbstractActivity> findAllWhereFragmentIsNull();

    /**
     * Get all the abstractActivities with eager load of many-to-many relationships.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<AbstractActivity> findAllWithEagerRelationships(Pageable pageable);

    /**
     * Get the "id" abstractActivity.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<AbstractActivity> findOne(String id);

    /**
     * Delete the "id" abstractActivity.
     *
     * @param id the id of the entity.
     */
    void delete(String id);
}
