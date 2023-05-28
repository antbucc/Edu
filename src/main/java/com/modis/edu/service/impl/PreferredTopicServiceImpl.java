package com.modis.edu.service.impl;

import com.modis.edu.domain.PreferredTopic;
import com.modis.edu.repository.PreferredTopicRepository;
import com.modis.edu.service.PreferredTopicService;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * Service Implementation for managing {@link PreferredTopic}.
 */
@Service
public class PreferredTopicServiceImpl implements PreferredTopicService {

    private final Logger log = LoggerFactory.getLogger(PreferredTopicServiceImpl.class);

    private final PreferredTopicRepository preferredTopicRepository;

    public PreferredTopicServiceImpl(PreferredTopicRepository preferredTopicRepository) {
        this.preferredTopicRepository = preferredTopicRepository;
    }

    @Override
    public PreferredTopic save(PreferredTopic preferredTopic) {
        log.debug("Request to save PreferredTopic : {}", preferredTopic);
        return preferredTopicRepository.save(preferredTopic);
    }

    @Override
    public PreferredTopic update(PreferredTopic preferredTopic) {
        log.debug("Request to update PreferredTopic : {}", preferredTopic);
        return preferredTopicRepository.save(preferredTopic);
    }

    @Override
    public Optional<PreferredTopic> partialUpdate(PreferredTopic preferredTopic) {
        log.debug("Request to partially update PreferredTopic : {}", preferredTopic);

        return preferredTopicRepository
            .findById(preferredTopic.getId())
            .map(existingPreferredTopic -> {
                if (preferredTopic.getTopicId() != null) {
                    existingPreferredTopic.setTopicId(preferredTopic.getTopicId());
                }
                if (preferredTopic.getTopic() != null) {
                    existingPreferredTopic.setTopic(preferredTopic.getTopic());
                }

                return existingPreferredTopic;
            })
            .map(preferredTopicRepository::save);
    }

    @Override
    public List<PreferredTopic> findAll() {
        log.debug("Request to get all PreferredTopics");
        return preferredTopicRepository.findAll();
    }

    @Override
    public Optional<PreferredTopic> findOne(String id) {
        log.debug("Request to get PreferredTopic : {}", id);
        return preferredTopicRepository.findById(id);
    }

    @Override
    public void delete(String id) {
        log.debug("Request to delete PreferredTopic : {}", id);
        preferredTopicRepository.deleteById(id);
    }
}
