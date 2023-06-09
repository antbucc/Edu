package com.modis.edu.service;

import com.modis.edu.domain.EducatorPreference;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link EducatorPreference}.
 */
public interface EducatorPreferenceService {
    /**
     * Save a educatorPreference.
     *
     * @param educatorPreference the entity to save.
     * @return the persisted entity.
     */
    EducatorPreference save(EducatorPreference educatorPreference);

    /**
     * Updates a educatorPreference.
     *
     * @param educatorPreference the entity to update.
     * @return the persisted entity.
     */
    EducatorPreference update(EducatorPreference educatorPreference);

    /**
     * Partially updates a educatorPreference.
     *
     * @param educatorPreference the entity to update partially.
     * @return the persisted entity.
     */
    Optional<EducatorPreference> partialUpdate(EducatorPreference educatorPreference);

    /**
     * Get all the educatorPreferences.
     *
     * @return the list of entities.
     */
    List<EducatorPreference> findAll();

    /**
     * Get all the educatorPreferences with eager load of many-to-many relationships.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<EducatorPreference> findAllWithEagerRelationships(Pageable pageable);

    /**
     * Get the "id" educatorPreference.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<EducatorPreference> findOne(String id);

    /**
     * Delete the "id" educatorPreference.
     *
     * @param id the id of the entity.
     */
    void delete(String id);
}
