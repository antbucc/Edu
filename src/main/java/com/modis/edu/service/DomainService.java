package com.modis.edu.service;

import com.modis.edu.domain.Domain;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link Domain}.
 */
public interface DomainService {
    /**
     * Save a domain.
     *
     * @param domain the entity to save.
     * @return the persisted entity.
     */
    Domain save(Domain domain);

    /**
     * Updates a domain.
     *
     * @param domain the entity to update.
     * @return the persisted entity.
     */
    Domain update(Domain domain);

    /**
     * Partially updates a domain.
     *
     * @param domain the entity to update partially.
     * @return the persisted entity.
     */
    Optional<Domain> partialUpdate(Domain domain);

    /**
     * Get all the domains.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Domain> findAll(Pageable pageable);

    /**
     * Get the "id" domain.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Domain> findOne(String id);

    /**
     * Delete the "id" domain.
     *
     * @param id the id of the entity.
     */
    void delete(String id);
}
