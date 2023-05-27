package com.modis.edu.service;

import com.modis.edu.domain.Module1;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link Module1}.
 */
public interface Module1Service {
    /**
     * Save a module1.
     *
     * @param module1 the entity to save.
     * @return the persisted entity.
     */
    Module1 save(Module1 module1);

    /**
     * Updates a module1.
     *
     * @param module1 the entity to update.
     * @return the persisted entity.
     */
    Module1 update(Module1 module1);

    /**
     * Partially updates a module1.
     *
     * @param module1 the entity to update partially.
     * @return the persisted entity.
     */
    Optional<Module1> partialUpdate(Module1 module1);

    /**
     * Get all the module1s.
     *
     * @return the list of entities.
     */
    List<Module1> findAll();

    /**
     * Get all the module1s with eager load of many-to-many relationships.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Module1> findAllWithEagerRelationships(Pageable pageable);

    /**
     * Get the "id" module1.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Module1> findOne(String id);

    /**
     * Delete the "id" module1.
     *
     * @param id the id of the entity.
     */
    void delete(String id);
}
