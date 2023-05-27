package com.modis.edu.repository;

import com.modis.edu.domain.Module1;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * Spring Data MongoDB repository for the Module1 entity.
 */
@Repository
public interface Module1Repository extends MongoRepository<Module1, String> {
    @Query("{}")
    Page<Module1> findAllWithEagerRelationships(Pageable pageable);

    @Query("{}")
    List<Module1> findAllWithEagerRelationships();

    @Query("{'id': ?0}")
    Optional<Module1> findOneWithEagerRelationships(String id);
}
