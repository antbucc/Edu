package com.modis.edu.service;

import com.modis.edu.domain.FragmentOrder;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link FragmentOrder}.
 */
public interface FragmentOrderService {
    /**
     * Save a fragmentOrder.
     *
     * @param fragmentOrder the entity to save.
     * @return the persisted entity.
     */
    FragmentOrder save(FragmentOrder fragmentOrder);

    /**
     * Updates a fragmentOrder.
     *
     * @param fragmentOrder the entity to update.
     * @return the persisted entity.
     */
    FragmentOrder update(FragmentOrder fragmentOrder);

    /**
     * Partially updates a fragmentOrder.
     *
     * @param fragmentOrder the entity to update partially.
     * @return the persisted entity.
     */
    Optional<FragmentOrder> partialUpdate(FragmentOrder fragmentOrder);

    /**
     * Get all the fragmentOrders.
     *
     * @return the list of entities.
     */
    List<FragmentOrder> findAll();

    /**
     * Get the "id" fragmentOrder.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<FragmentOrder> findOne(String id);

    /**
     * Delete the "id" fragmentOrder.
     *
     * @param id the id of the entity.
     */
    void delete(String id);
}
