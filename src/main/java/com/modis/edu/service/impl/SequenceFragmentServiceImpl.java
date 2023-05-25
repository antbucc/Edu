package com.modis.edu.service.impl;

import com.modis.edu.domain.SequenceFragment;
import com.modis.edu.repository.SequenceFragmentRepository;
import com.modis.edu.service.SequenceFragmentService;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * Service Implementation for managing {@link SequenceFragment}.
 */
@Service
public class SequenceFragmentServiceImpl implements SequenceFragmentService {

    private final Logger log = LoggerFactory.getLogger(SequenceFragmentServiceImpl.class);

    private final SequenceFragmentRepository sequenceFragmentRepository;

    public SequenceFragmentServiceImpl(SequenceFragmentRepository sequenceFragmentRepository) {
        this.sequenceFragmentRepository = sequenceFragmentRepository;
    }

    @Override
    public SequenceFragment save(SequenceFragment sequenceFragment) {
        log.debug("Request to save SequenceFragment : {}", sequenceFragment);
        return sequenceFragmentRepository.save(sequenceFragment);
    }

    @Override
    public SequenceFragment update(SequenceFragment sequenceFragment) {
        log.debug("Request to update SequenceFragment : {}", sequenceFragment);
        return sequenceFragmentRepository.save(sequenceFragment);
    }

    @Override
    public Optional<SequenceFragment> partialUpdate(SequenceFragment sequenceFragment) {
        log.debug("Request to partially update SequenceFragment : {}", sequenceFragment);

        return sequenceFragmentRepository
            .findById(sequenceFragment.getId())
            .map(existingSequenceFragment -> {
                if (sequenceFragment.getOrder() != null) {
                    existingSequenceFragment.setOrder(sequenceFragment.getOrder());
                }

                return existingSequenceFragment;
            })
            .map(sequenceFragmentRepository::save);
    }

    @Override
    public List<SequenceFragment> findAll() {
        log.debug("Request to get all SequenceFragments");
        return sequenceFragmentRepository.findAll();
    }

    public Page<SequenceFragment> findAllWithEagerRelationships(Pageable pageable) {
        return sequenceFragmentRepository.findAllWithEagerRelationships(pageable);
    }

    @Override
    public Optional<SequenceFragment> findOne(String id) {
        log.debug("Request to get SequenceFragment : {}", id);
        return sequenceFragmentRepository.findOneWithEagerRelationships(id);
    }

    @Override
    public void delete(String id) {
        log.debug("Request to delete SequenceFragment : {}", id);
        sequenceFragmentRepository.deleteById(id);
    }
}
