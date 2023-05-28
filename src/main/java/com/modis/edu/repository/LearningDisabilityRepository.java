package com.modis.edu.repository;

import com.modis.edu.domain.LearningDisability;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * Spring Data MongoDB repository for the LearningDisability entity.
 */
@SuppressWarnings("unused")
@Repository
public interface LearningDisabilityRepository extends MongoRepository<LearningDisability, String> {}
