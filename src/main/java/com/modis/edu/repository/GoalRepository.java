package com.modis.edu.repository;

import com.modis.edu.domain.Goal;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * Spring Data MongoDB repository for the Goal entity.
 */
@SuppressWarnings("unused")
@Repository
public interface GoalRepository extends MongoRepository<Goal, String> {}
