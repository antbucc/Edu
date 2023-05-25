package com.modis.edu.repository;

import com.modis.edu.domain.SetOfFragment;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * Spring Data MongoDB repository for the SetOfFragment entity.
 */
@Repository
public interface SetOfFragmentRepository extends MongoRepository<SetOfFragment, String> {
    @Query("{}")
    Page<SetOfFragment> findAllWithEagerRelationships(Pageable pageable);

    @Query("{}")
    List<SetOfFragment> findAllWithEagerRelationships();

    @Query("{'id': ?0}")
    Optional<SetOfFragment> findOneWithEagerRelationships(String id);
}
