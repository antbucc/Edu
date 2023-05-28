package com.modis.edu.repository;

import com.modis.edu.domain.PreferredActivity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * Spring Data MongoDB repository for the PreferredActivity entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PreferredActivityRepository extends MongoRepository<PreferredActivity, String> {}
