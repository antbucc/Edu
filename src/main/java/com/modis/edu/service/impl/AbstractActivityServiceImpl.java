package com.modis.edu.service.impl;

import com.modis.edu.domain.AbstractActivity;
import com.modis.edu.repository.AbstractActivityRepository;
import com.modis.edu.service.AbstractActivityService;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * Service Implementation for managing {@link AbstractActivity}.
 */
@Service
public class AbstractActivityServiceImpl implements AbstractActivityService {

    private final Logger log = LoggerFactory.getLogger(AbstractActivityServiceImpl.class);

    private final AbstractActivityRepository abstractActivityRepository;

    public AbstractActivityServiceImpl(AbstractActivityRepository abstractActivityRepository) {
        this.abstractActivityRepository = abstractActivityRepository;
    }

    @Override
    public AbstractActivity save(AbstractActivity abstractActivity) {
        log.debug("Request to save AbstractActivity : {}", abstractActivity);
        return abstractActivityRepository.save(abstractActivity);
    }

    @Override
    public AbstractActivity update(AbstractActivity abstractActivity) {
        log.debug("Request to update AbstractActivity : {}", abstractActivity);
        return abstractActivityRepository.save(abstractActivity);
    }

    @Override
    public Optional<AbstractActivity> partialUpdate(AbstractActivity abstractActivity) {
        log.debug("Request to partially update AbstractActivity : {}", abstractActivity);

        return abstractActivityRepository
            .findById(abstractActivity.getId())
            .map(existingAbstractActivity -> {
                if (abstractActivity.getTitle() != null) {
                    existingAbstractActivity.setTitle(abstractActivity.getTitle());
                }

                return existingAbstractActivity;
            })
            .map(abstractActivityRepository::save);
    }

    @Override
    public List<AbstractActivity> findAll() {
        log.debug("Request to get all AbstractActivities");
        return abstractActivityRepository.findAll();
    }

    /**
     *  Get all the abstractActivities where Fragment is {@code null}.
     *  @return the list of entities.
     */

    public List<AbstractActivity> findAllWhereFragmentIsNull() {
        log.debug("Request to get all abstractActivities where Fragment is null");
        return StreamSupport
            .stream(abstractActivityRepository.findAll().spliterator(), false)
            .filter(abstractActivity -> abstractActivity.getFragment() == null)
            .collect(Collectors.toList());
    }

    @Override
    public Optional<AbstractActivity> findOne(String id) {
        log.debug("Request to get AbstractActivity : {}", id);
        return abstractActivityRepository.findById(id);
    }

    @Override
    public void delete(String id) {
        log.debug("Request to delete AbstractActivity : {}", id);
        abstractActivityRepository.deleteById(id);
    }
}
