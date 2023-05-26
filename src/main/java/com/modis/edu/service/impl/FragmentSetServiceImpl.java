package com.modis.edu.service.impl;

import com.modis.edu.domain.FragmentSet;
import com.modis.edu.repository.FragmentSetRepository;
import com.modis.edu.service.FragmentSetService;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * Service Implementation for managing {@link FragmentSet}.
 */
@Service
public class FragmentSetServiceImpl implements FragmentSetService {

    private final Logger log = LoggerFactory.getLogger(FragmentSetServiceImpl.class);

    private final FragmentSetRepository fragmentSetRepository;

    public FragmentSetServiceImpl(FragmentSetRepository fragmentSetRepository) {
        this.fragmentSetRepository = fragmentSetRepository;
    }

    @Override
    public FragmentSet save(FragmentSet fragmentSet) {
        log.debug("Request to save FragmentSet : {}", fragmentSet);
        return fragmentSetRepository.save(fragmentSet);
    }

    @Override
    public FragmentSet update(FragmentSet fragmentSet) {
        log.debug("Request to update FragmentSet : {}", fragmentSet);
        // no save call needed as we have no fields that can be updated
        return fragmentSet;
    }

    @Override
    public Optional<FragmentSet> partialUpdate(FragmentSet fragmentSet) {
        log.debug("Request to partially update FragmentSet : {}", fragmentSet);

        return fragmentSetRepository
            .findById(fragmentSet.getId())
            .map(existingFragmentSet -> {
                return existingFragmentSet;
            })// .map(fragmentSetRepository::save)
        ;
    }

    @Override
    public List<FragmentSet> findAll() {
        log.debug("Request to get all FragmentSets");
        return fragmentSetRepository.findAll();
    }

    @Override
    public Optional<FragmentSet> findOne(String id) {
        log.debug("Request to get FragmentSet : {}", id);
        return fragmentSetRepository.findById(id);
    }

    @Override
    public void delete(String id) {
        log.debug("Request to delete FragmentSet : {}", id);
        fragmentSetRepository.deleteById(id);
    }
}
