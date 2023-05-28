package com.modis.edu.repository;

import com.modis.edu.domain.PreferredTopic;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * Spring Data MongoDB repository for the PreferredTopic entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PreferredTopicRepository extends MongoRepository<PreferredTopic, String> {}
