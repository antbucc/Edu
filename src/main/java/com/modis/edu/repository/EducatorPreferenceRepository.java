package com.modis.edu.repository;

import com.modis.edu.domain.EducatorPreference;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * Spring Data MongoDB repository for the EducatorPreference entity.
 */
@Repository
public interface EducatorPreferenceRepository extends MongoRepository<EducatorPreference, String> {
    @Query("{}")
    Page<EducatorPreference> findAllWithEagerRelationships(Pageable pageable);

    @Query("{}")
    List<EducatorPreference> findAllWithEagerRelationships();

    @Query("{'id': ?0}")
    Optional<EducatorPreference> findOneWithEagerRelationships(String id);
}
