package com.modis.edu.repository;

import com.modis.edu.domain.PreferredLearningStyle;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * Spring Data MongoDB repository for the PreferredLearningStyle entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PreferredLearningStyleRepository extends MongoRepository<PreferredLearningStyle, String> {}
