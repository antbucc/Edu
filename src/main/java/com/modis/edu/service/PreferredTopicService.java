package com.modis.edu.service;

import com.modis.edu.domain.PreferredTopic;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link PreferredTopic}.
 */
public interface PreferredTopicService {
    /**
     * Save a preferredTopic.
     *
     * @param preferredTopic the entity to save.
     * @return the persisted entity.
     */
    PreferredTopic save(PreferredTopic preferredTopic);

    /**
     * Updates a preferredTopic.
     *
     * @param preferredTopic the entity to update.
     * @return the persisted entity.
     */
    PreferredTopic update(PreferredTopic preferredTopic);

    /**
     * Partially updates a preferredTopic.
     *
     * @param preferredTopic the entity to update partially.
     * @return the persisted entity.
     */
    Optional<PreferredTopic> partialUpdate(PreferredTopic preferredTopic);

    /**
     * Get all the preferredTopics.
     *
     * @return the list of entities.
     */
    List<PreferredTopic> findAll();

    /**
     * Get the "id" preferredTopic.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<PreferredTopic> findOne(String id);

    /**
     * Delete the "id" preferredTopic.
     *
     * @param id the id of the entity.
     */
    void delete(String id);
}
