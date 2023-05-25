package com.modis.edu.service.impl;

import com.modis.edu.domain.SetOf;
import com.modis.edu.repository.SetOfRepository;
import com.modis.edu.service.SetOfService;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * Service Implementation for managing {@link SetOf}.
 */
@Service
public class SetOfServiceImpl implements SetOfService {

    private final Logger log = LoggerFactory.getLogger(SetOfServiceImpl.class);

    private final SetOfRepository setOfRepository;

    public SetOfServiceImpl(SetOfRepository setOfRepository) {
        this.setOfRepository = setOfRepository;
    }

    @Override
    public SetOf save(SetOf setOf) {
        log.debug("Request to save SetOf : {}", setOf);
        return setOfRepository.save(setOf);
    }

    @Override
    public SetOf update(SetOf setOf) {
        log.debug("Request to update SetOf : {}", setOf);
        return setOfRepository.save(setOf);
    }

    @Override
    public Optional<SetOf> partialUpdate(SetOf setOf) {
        log.debug("Request to partially update SetOf : {}", setOf);

        return setOfRepository
            .findById(setOf.getId())
            .map(existingSetOf -> {
                if (setOf.getTitle() != null) {
                    existingSetOf.setTitle(setOf.getTitle());
                }

                return existingSetOf;
            })
            .map(setOfRepository::save);
    }

    @Override
    public List<SetOf> findAll() {
        log.debug("Request to get all SetOfs");
        return setOfRepository.findAll();
    }

    public Page<SetOf> findAllWithEagerRelationships(Pageable pageable) {
        return setOfRepository.findAllWithEagerRelationships(pageable);
    }

    @Override
    public Optional<SetOf> findOne(String id) {
        log.debug("Request to get SetOf : {}", id);
        return setOfRepository.findOneWithEagerRelationships(id);
    }

    @Override
    public void delete(String id) {
        log.debug("Request to delete SetOf : {}", id);
        setOfRepository.deleteById(id);
    }
}
