package com.modis.edu.service;

import com.modis.edu.domain.SequenceFragment;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link SequenceFragment}.
 */
public interface SequenceFragmentService {
    /**
     * Save a sequenceFragment.
     *
     * @param sequenceFragment the entity to save.
     * @return the persisted entity.
     */
    SequenceFragment save(SequenceFragment sequenceFragment);

    /**
     * Updates a sequenceFragment.
     *
     * @param sequenceFragment the entity to update.
     * @return the persisted entity.
     */
    SequenceFragment update(SequenceFragment sequenceFragment);

    /**
     * Partially updates a sequenceFragment.
     *
     * @param sequenceFragment the entity to update partially.
     * @return the persisted entity.
     */
    Optional<SequenceFragment> partialUpdate(SequenceFragment sequenceFragment);

    /**
     * Get all the sequenceFragments.
     *
     * @return the list of entities.
     */
    List<SequenceFragment> findAll();

    /**
     * Get all the sequenceFragments with eager load of many-to-many relationships.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<SequenceFragment> findAllWithEagerRelationships(Pageable pageable);

    /**
     * Get the "id" sequenceFragment.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<SequenceFragment> findOne(String id);

    /**
     * Delete the "id" sequenceFragment.
     *
     * @param id the id of the entity.
     */
    void delete(String id);
}
