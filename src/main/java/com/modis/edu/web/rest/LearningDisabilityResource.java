package com.modis.edu.web.rest;

import com.modis.edu.domain.LearningDisability;
import com.modis.edu.repository.LearningDisabilityRepository;
import com.modis.edu.service.LearningDisabilityService;
import com.modis.edu.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.modis.edu.domain.LearningDisability}.
 */
@RestController
@RequestMapping("/api")
public class LearningDisabilityResource {

    private final Logger log = LoggerFactory.getLogger(LearningDisabilityResource.class);

    private static final String ENTITY_NAME = "learningDisability";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final LearningDisabilityService learningDisabilityService;

    private final LearningDisabilityRepository learningDisabilityRepository;

    public LearningDisabilityResource(
        LearningDisabilityService learningDisabilityService,
        LearningDisabilityRepository learningDisabilityRepository
    ) {
        this.learningDisabilityService = learningDisabilityService;
        this.learningDisabilityRepository = learningDisabilityRepository;
    }

    /**
     * {@code POST  /learning-disabilities} : Create a new learningDisability.
     *
     * @param learningDisability the learningDisability to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new learningDisability, or with status {@code 400 (Bad Request)} if the learningDisability has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/learning-disabilities")
    public ResponseEntity<LearningDisability> createLearningDisability(@RequestBody LearningDisability learningDisability)
        throws URISyntaxException {
        log.debug("REST request to save LearningDisability : {}", learningDisability);
        if (learningDisability.getId() != null) {
            throw new BadRequestAlertException("A new learningDisability cannot already have an ID", ENTITY_NAME, "idexists");
        }
        LearningDisability result = learningDisabilityService.save(learningDisability);
        return ResponseEntity
            .created(new URI("/api/learning-disabilities/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId()))
            .body(result);
    }

    /**
     * {@code PUT  /learning-disabilities/:id} : Updates an existing learningDisability.
     *
     * @param id the id of the learningDisability to save.
     * @param learningDisability the learningDisability to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated learningDisability,
     * or with status {@code 400 (Bad Request)} if the learningDisability is not valid,
     * or with status {@code 500 (Internal Server Error)} if the learningDisability couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/learning-disabilities/{id}")
    public ResponseEntity<LearningDisability> updateLearningDisability(
        @PathVariable(value = "id", required = false) final String id,
        @RequestBody LearningDisability learningDisability
    ) throws URISyntaxException {
        log.debug("REST request to update LearningDisability : {}, {}", id, learningDisability);
        if (learningDisability.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, learningDisability.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!learningDisabilityRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        LearningDisability result = learningDisabilityService.update(learningDisability);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, learningDisability.getId()))
            .body(result);
    }

    /**
     * {@code PATCH  /learning-disabilities/:id} : Partial updates given fields of an existing learningDisability, field will ignore if it is null
     *
     * @param id the id of the learningDisability to save.
     * @param learningDisability the learningDisability to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated learningDisability,
     * or with status {@code 400 (Bad Request)} if the learningDisability is not valid,
     * or with status {@code 404 (Not Found)} if the learningDisability is not found,
     * or with status {@code 500 (Internal Server Error)} if the learningDisability couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/learning-disabilities/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<LearningDisability> partialUpdateLearningDisability(
        @PathVariable(value = "id", required = false) final String id,
        @RequestBody LearningDisability learningDisability
    ) throws URISyntaxException {
        log.debug("REST request to partial update LearningDisability partially : {}, {}", id, learningDisability);
        if (learningDisability.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, learningDisability.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!learningDisabilityRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<LearningDisability> result = learningDisabilityService.partialUpdate(learningDisability);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, learningDisability.getId())
        );
    }

    /**
     * {@code GET  /learning-disabilities} : get all the learningDisabilities.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of learningDisabilities in body.
     */
    @GetMapping("/learning-disabilities")
    public List<LearningDisability> getAllLearningDisabilities() {
        log.debug("REST request to get all LearningDisabilities");
        return learningDisabilityService.findAll();
    }

    /**
     * {@code GET  /learning-disabilities/:id} : get the "id" learningDisability.
     *
     * @param id the id of the learningDisability to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the learningDisability, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/learning-disabilities/{id}")
    public ResponseEntity<LearningDisability> getLearningDisability(@PathVariable String id) {
        log.debug("REST request to get LearningDisability : {}", id);
        Optional<LearningDisability> learningDisability = learningDisabilityService.findOne(id);
        return ResponseUtil.wrapOrNotFound(learningDisability);
    }

    /**
     * {@code DELETE  /learning-disabilities/:id} : delete the "id" learningDisability.
     *
     * @param id the id of the learningDisability to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/learning-disabilities/{id}")
    public ResponseEntity<Void> deleteLearningDisability(@PathVariable String id) {
        log.debug("REST request to delete LearningDisability : {}", id);
        learningDisabilityService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id)).build();
    }
}
