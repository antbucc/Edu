package com.modis.edu.repository;

import com.modis.edu.domain.PreferredModality;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * Spring Data MongoDB repository for the PreferredModality entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PreferredModalityRepository extends MongoRepository<PreferredModality, String> {}
