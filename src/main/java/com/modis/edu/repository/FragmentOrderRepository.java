package com.modis.edu.repository;

import com.modis.edu.domain.FragmentOrder;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * Spring Data MongoDB repository for the FragmentOrder entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FragmentOrderRepository extends MongoRepository<FragmentOrder, String> {}
