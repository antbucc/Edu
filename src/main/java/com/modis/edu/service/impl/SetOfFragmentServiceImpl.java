package com.modis.edu.service.impl;

import com.modis.edu.domain.SetOfFragment;
import com.modis.edu.repository.SetOfFragmentRepository;
import com.modis.edu.service.SetOfFragmentService;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * Service Implementation for managing {@link SetOfFragment}.
 */
@Service
public class SetOfFragmentServiceImpl implements SetOfFragmentService {

    private final Logger log = LoggerFactory.getLogger(SetOfFragmentServiceImpl.class);

    private final SetOfFragmentRepository setOfFragmentRepository;

    public SetOfFragmentServiceImpl(SetOfFragmentRepository setOfFragmentRepository) {
        this.setOfFragmentRepository = setOfFragmentRepository;
    }

    @Override
    public SetOfFragment save(SetOfFragment setOfFragment) {
        log.debug("Request to save SetOfFragment : {}", setOfFragment);
        return setOfFragmentRepository.save(setOfFragment);
    }

    @Override
    public SetOfFragment update(SetOfFragment setOfFragment) {
        log.debug("Request to update SetOfFragment : {}", setOfFragment);
        return setOfFragmentRepository.save(setOfFragment);
    }

    @Override
    public Optional<SetOfFragment> partialUpdate(SetOfFragment setOfFragment) {
        log.debug("Request to partially update SetOfFragment : {}", setOfFragment);

        return setOfFragmentRepository
            .findById(setOfFragment.getId())
            .map(existingSetOfFragment -> {
                if (setOfFragment.getOrder() != null) {
                    existingSetOfFragment.setOrder(setOfFragment.getOrder());
                }

                return existingSetOfFragment;
            })
            .map(setOfFragmentRepository::save);
    }

    @Override
    public List<SetOfFragment> findAll() {
        log.debug("Request to get all SetOfFragments");
        return setOfFragmentRepository.findAll();
    }

    public Page<SetOfFragment> findAllWithEagerRelationships(Pageable pageable) {
        return setOfFragmentRepository.findAllWithEagerRelationships(pageable);
    }

    @Override
    public Optional<SetOfFragment> findOne(String id) {
        log.debug("Request to get SetOfFragment : {}", id);
        return setOfFragmentRepository.findOneWithEagerRelationships(id);
    }

    @Override
    public void delete(String id) {
        log.debug("Request to delete SetOfFragment : {}", id);
        setOfFragmentRepository.deleteById(id);
    }
}
