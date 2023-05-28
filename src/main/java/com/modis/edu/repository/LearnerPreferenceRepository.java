package com.modis.edu.repository;

import com.modis.edu.domain.LearnerPreference;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * Spring Data MongoDB repository for the LearnerPreference entity.
 */
@Repository
public interface LearnerPreferenceRepository extends MongoRepository<LearnerPreference, String> {
    @Query("{}")
    Page<LearnerPreference> findAllWithEagerRelationships(Pageable pageable);

    @Query("{}")
    List<LearnerPreference> findAllWithEagerRelationships();

    @Query("{'id': ?0}")
    Optional<LearnerPreference> findOneWithEagerRelationships(String id);
}
