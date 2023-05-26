package com.modis.edu.service;

import com.modis.edu.domain.Sequence;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link Sequence}.
 */
public interface SequenceService {
    /**
     * Save a sequence.
     *
     * @param sequence the entity to save.
     * @return the persisted entity.
     */
    Sequence save(Sequence sequence);

    /**
     * Updates a sequence.
     *
     * @param sequence the entity to update.
     * @return the persisted entity.
     */
    Sequence update(Sequence sequence);

    /**
     * Partially updates a sequence.
     *
     * @param sequence the entity to update partially.
     * @return the persisted entity.
     */
    Optional<Sequence> partialUpdate(Sequence sequence);

    /**
     * Get all the sequences.
     *
     * @return the list of entities.
     */
    List<Sequence> findAll();
    /**
     * Get all the Sequence where Fragment is {@code null}.
     *
     * @return the {@link List} of entities.
     */
    List<Sequence> findAllWhereFragmentIsNull();

    /**
     * Get the "id" sequence.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Sequence> findOne(String id);

    /**
     * Delete the "id" sequence.
     *
     * @param id the id of the entity.
     */
    void delete(String id);
}
