package com.modis.edu.repository;

import com.modis.edu.domain.SequenceFragment;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * Spring Data MongoDB repository for the SequenceFragment entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SequenceFragmentRepository extends MongoRepository<SequenceFragment, String> {}
