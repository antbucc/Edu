package com.modis.edu.repository;

import com.modis.edu.domain.SetOf;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * Spring Data MongoDB repository for the SetOf entity.
 */
@Repository
public interface SetOfRepository extends MongoRepository<SetOf, String> {
    @Query("{}")
    Page<SetOf> findAllWithEagerRelationships(Pageable pageable);

    @Query("{}")
    List<SetOf> findAllWithEagerRelationships();

    @Query("{'id': ?0}")
    Optional<SetOf> findOneWithEagerRelationships(String id);
}
