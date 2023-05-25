package com.modis.edu.service;

import com.modis.edu.domain.SetOfFragment;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link SetOfFragment}.
 */
public interface SetOfFragmentService {
    /**
     * Save a setOfFragment.
     *
     * @param setOfFragment the entity to save.
     * @return the persisted entity.
     */
    SetOfFragment save(SetOfFragment setOfFragment);

    /**
     * Updates a setOfFragment.
     *
     * @param setOfFragment the entity to update.
     * @return the persisted entity.
     */
    SetOfFragment update(SetOfFragment setOfFragment);

    /**
     * Partially updates a setOfFragment.
     *
     * @param setOfFragment the entity to update partially.
     * @return the persisted entity.
     */
    Optional<SetOfFragment> partialUpdate(SetOfFragment setOfFragment);

    /**
     * Get all the setOfFragments.
     *
     * @return the list of entities.
     */
    List<SetOfFragment> findAll();

    /**
     * Get all the setOfFragments with eager load of many-to-many relationships.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<SetOfFragment> findAllWithEagerRelationships(Pageable pageable);

    /**
     * Get the "id" setOfFragment.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<SetOfFragment> findOne(String id);

    /**
     * Delete the "id" setOfFragment.
     *
     * @param id the id of the entity.
     */
    void delete(String id);
}
