package com.modis.edu.repository;

import com.modis.edu.domain.AbstractActivity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * Spring Data MongoDB repository for the AbstractActivity entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AbstractActivityRepository extends MongoRepository<AbstractActivity, String> {}
