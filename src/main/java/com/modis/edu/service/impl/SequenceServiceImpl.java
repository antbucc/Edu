package com.modis.edu.service.impl;

import com.modis.edu.domain.Sequence;
import com.modis.edu.repository.SequenceRepository;
import com.modis.edu.service.SequenceService;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * Service Implementation for managing {@link Sequence}.
 */
@Service
public class SequenceServiceImpl implements SequenceService {

    private final Logger log = LoggerFactory.getLogger(SequenceServiceImpl.class);

    private final SequenceRepository sequenceRepository;

    public SequenceServiceImpl(SequenceRepository sequenceRepository) {
        this.sequenceRepository = sequenceRepository;
    }

    @Override
    public Sequence save(Sequence sequence) {
        log.debug("Request to save Sequence : {}", sequence);
        return sequenceRepository.save(sequence);
    }

    @Override
    public Sequence update(Sequence sequence) {
        log.debug("Request to update Sequence : {}", sequence);
        return sequenceRepository.save(sequence);
    }

    @Override
    public Optional<Sequence> partialUpdate(Sequence sequence) {
        log.debug("Request to partially update Sequence : {}", sequence);

        return sequenceRepository
            .findById(sequence.getId())
            .map(existingSequence -> {
                if (sequence.getTitle() != null) {
                    existingSequence.setTitle(sequence.getTitle());
                }

                return existingSequence;
            })
            .map(sequenceRepository::save);
    }

    @Override
    public List<Sequence> findAll() {
        log.debug("Request to get all Sequences");
        return sequenceRepository.findAll();
    }

    @Override
    public Optional<Sequence> findOne(String id) {
        log.debug("Request to get Sequence : {}", id);
        return sequenceRepository.findById(id);
    }

    @Override
    public void delete(String id) {
        log.debug("Request to delete Sequence : {}", id);
        sequenceRepository.deleteById(id);
    }
}
