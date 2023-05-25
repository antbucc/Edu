package com.modis.edu.repository;

import com.modis.edu.domain.Sequence;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * Spring Data MongoDB repository for the Sequence entity.
 */
@Repository
public interface SequenceRepository extends MongoRepository<Sequence, String> {
    @Query("{}")
    Page<Sequence> findAllWithEagerRelationships(Pageable pageable);

    @Query("{}")
    List<Sequence> findAllWithEagerRelationships();

    @Query("{'id': ?0}")
    Optional<Sequence> findOneWithEagerRelationships(String id);
}
