package com.modis.edu.repository;

import com.modis.edu.domain.Order;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * Spring Data MongoDB repository for the Order entity.
 */
@SuppressWarnings("unused")
@Repository
public interface OrderRepository extends MongoRepository<Order, String> {}
