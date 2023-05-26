package com.modis.edu.repository;

import com.modis.edu.domain.SetOf;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * Spring Data MongoDB repository for the SetOf entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SetOfRepository extends MongoRepository<SetOf, String> {}
