package com.modis.edu.repository;

import com.modis.edu.domain.FragmentSet;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * Spring Data MongoDB repository for the FragmentSet entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FragmentSetRepository extends MongoRepository<FragmentSet, String> {}
