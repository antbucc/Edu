package com.modis.edu.repository;

import com.modis.edu.domain.AbstractActivity;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * Spring Data MongoDB repository for the AbstractActivity entity.
 */
@Repository
public interface AbstractActivityRepository extends MongoRepository<AbstractActivity, String> {
    @Query("{}")
    Page<AbstractActivity> findAllWithEagerRelationships(Pageable pageable);

    @Query("{}")
    List<AbstractActivity> findAllWithEagerRelationships();

    @Query("{'id': ?0}")
    Optional<AbstractActivity> findOneWithEagerRelationships(String id);
}
