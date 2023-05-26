package com.modis.edu.repository;

import com.modis.edu.domain.Order;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * Spring Data MongoDB repository for the Order entity.
 */
@Repository
public interface OrderRepository extends MongoRepository<Order, String> {
    @Query("{}")
    Page<Order> findAllWithEagerRelationships(Pageable pageable);

    @Query("{}")
    List<Order> findAllWithEagerRelationships();

    @Query("{'id': ?0}")
    Optional<Order> findOneWithEagerRelationships(String id);
}
