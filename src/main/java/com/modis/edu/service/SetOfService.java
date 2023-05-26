package com.modis.edu.service;

import com.modis.edu.domain.SetOf;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link SetOf}.
 */
public interface SetOfService {
    /**
     * Save a setOf.
     *
     * @param setOf the entity to save.
     * @return the persisted entity.
     */
    SetOf save(SetOf setOf);

    /**
     * Updates a setOf.
     *
     * @param setOf the entity to update.
     * @return the persisted entity.
     */
    SetOf update(SetOf setOf);

    /**
     * Partially updates a setOf.
     *
     * @param setOf the entity to update partially.
     * @return the persisted entity.
     */
    Optional<SetOf> partialUpdate(SetOf setOf);

    /**
     * Get all the setOfs.
     *
     * @return the list of entities.
     */
    List<SetOf> findAll();
    /**
     * Get all the SetOf where Fragment1 is {@code null}.
     *
     * @return the {@link List} of entities.
     */
    List<SetOf> findAllWhereFragment1IsNull();

    /**
     * Get all the setOfs with eager load of many-to-many relationships.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<SetOf> findAllWithEagerRelationships(Pageable pageable);

    /**
     * Get the "id" setOf.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<SetOf> findOne(String id);

    /**
     * Delete the "id" setOf.
     *
     * @param id the id of the entity.
     */
    void delete(String id);
}
