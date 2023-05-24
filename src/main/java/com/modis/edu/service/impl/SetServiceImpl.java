package com.modis.edu.service.impl;

import com.modis.edu.domain.SetOf;
import com.modis.edu.repository.SetRepository;
import com.modis.edu.service.SetService;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * Service Implementation for managing {@link SetOf}.
 */
@Service
public class SetServiceImpl implements SetService {

    private final Logger log = LoggerFactory.getLogger(SetServiceImpl.class);

    private final SetRepository setRepository;

    public SetServiceImpl(SetRepository setRepository) {
        this.setRepository = setRepository;
    }

    @Override
    public SetOf save(SetOf set) {
        log.debug("Request to save Set : {}", set);
        return setRepository.save(set);
    }

    @Override
    public SetOf update(SetOf set) {
        log.debug("Request to update Set : {}", set);
        return setRepository.save(set);
    }

    @Override
    public Optional<SetOf> partialUpdate(SetOf set) {
        log.debug("Request to partially update Set : {}", set);

        return setRepository
            .findById(set.getId())
            .map(existingSet -> {
                if (set.getTitle() != null) {
                    existingSet.setTitle(set.getTitle());
                }

                return existingSet;
            })
            .map(setRepository::save);
    }

    @Override
    public List<SetOf> findAll() {
        log.debug("Request to get all Sets");
        return setRepository.findAll();
    }

    /**
     * Get all the sets where Fragment is {@code null}.
     *
     * @return the list of entities.
     */

    public List<SetOf> findAllWhereFragmentIsNull() {
        log.debug("Request to get all sets where Fragment is null");
        return StreamSupport
            .stream(setRepository.findAll().spliterator(), false)
            .filter(set -> set.getFragment() == null)
            .collect(Collectors.toList());
    }

    @Override
    public Optional<SetOf> findOne(String id) {
        log.debug("Request to get Set : {}", id);
        return setRepository.findById(id);
    }

    @Override
    public void delete(String id) {
        log.debug("Request to delete Set : {}", id);
        setRepository.deleteById(id);
    }
}
