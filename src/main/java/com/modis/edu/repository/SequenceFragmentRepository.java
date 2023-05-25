package com.modis.edu.repository;

import com.modis.edu.domain.SequenceFragment;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * Spring Data MongoDB repository for the SequenceFragment entity.
 */
@Repository
public interface SequenceFragmentRepository extends MongoRepository<SequenceFragment, String> {
    @Query("{}")
    Page<SequenceFragment> findAllWithEagerRelationships(Pageable pageable);

    @Query("{}")
    List<SequenceFragment> findAllWithEagerRelationships();

    @Query("{'id': ?0}")
    Optional<SequenceFragment> findOneWithEagerRelationships(String id);
}
