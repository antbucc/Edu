package com.modis.edu.repository;

import com.modis.edu.domain.EducatorPreference;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * Spring Data MongoDB repository for the EducatorPreference entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EducatorPreferenceRepository extends MongoRepository<EducatorPreference, String> {}
