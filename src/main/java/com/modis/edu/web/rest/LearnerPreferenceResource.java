package com.modis.edu.web.rest;

import com.modis.edu.domain.LearnerPreference;
import com.modis.edu.repository.LearnerPreferenceRepository;
import com.modis.edu.service.LearnerPreferenceService;
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
 * REST controller for managing {@link com.modis.edu.domain.LearnerPreference}.
 */
@RestController
@RequestMapping("/api")
public class LearnerPreferenceResource {

    private final Logger log = LoggerFactory.getLogger(LearnerPreferenceResource.class);

    private static final String ENTITY_NAME = "learnerPreference";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final LearnerPreferenceService learnerPreferenceService;

    private final LearnerPreferenceRepository learnerPreferenceRepository;

    public LearnerPreferenceResource(
        LearnerPreferenceService learnerPreferenceService,
        LearnerPreferenceRepository learnerPreferenceRepository
    ) {
        this.learnerPreferenceService = learnerPreferenceService;
        this.learnerPreferenceRepository = learnerPreferenceRepository;
    }

    /**
     * {@code POST  /learner-preferences} : Create a new learnerPreference.
     *
     * @param learnerPreference the learnerPreference to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new learnerPreference, or with status {@code 400 (Bad Request)} if the learnerPreference has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/learner-preferences")
    public ResponseEntity<LearnerPreference> createLearnerPreference(@RequestBody LearnerPreference learnerPreference)
        throws URISyntaxException {
        log.debug("REST request to save LearnerPreference : {}", learnerPreference);
        if (learnerPreference.getId() != null) {
            throw new BadRequestAlertException("A new learnerPreference cannot already have an ID", ENTITY_NAME, "idexists");
        }
        LearnerPreference result = learnerPreferenceService.save(learnerPreference);
        return ResponseEntity
            .created(new URI("/api/learner-preferences/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId()))
            .body(result);
    }

    /**
     * {@code PUT  /learner-preferences/:id} : Updates an existing learnerPreference.
     *
     * @param id the id of the learnerPreference to save.
     * @param learnerPreference the learnerPreference to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated learnerPreference,
     * or with status {@code 400 (Bad Request)} if the learnerPreference is not valid,
     * or with status {@code 500 (Internal Server Error)} if the learnerPreference couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/learner-preferences/{id}")
    public ResponseEntity<LearnerPreference> updateLearnerPreference(
        @PathVariable(value = "id", required = false) final String id,
        @RequestBody LearnerPreference learnerPreference
    ) throws URISyntaxException {
        log.debug("REST request to update LearnerPreference : {}, {}", id, learnerPreference);
        if (learnerPreference.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, learnerPreference.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!learnerPreferenceRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        LearnerPreference result = learnerPreferenceService.update(learnerPreference);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, learnerPreference.getId()))
            .body(result);
    }

    /**
     * {@code PATCH  /learner-preferences/:id} : Partial updates given fields of an existing learnerPreference, field will ignore if it is null
     *
     * @param id the id of the learnerPreference to save.
     * @param learnerPreference the learnerPreference to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated learnerPreference,
     * or with status {@code 400 (Bad Request)} if the learnerPreference is not valid,
     * or with status {@code 404 (Not Found)} if the learnerPreference is not found,
     * or with status {@code 500 (Internal Server Error)} if the learnerPreference couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/learner-preferences/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<LearnerPreference> partialUpdateLearnerPreference(
        @PathVariable(value = "id", required = false) final String id,
        @RequestBody LearnerPreference learnerPreference
    ) throws URISyntaxException {
        log.debug("REST request to partial update LearnerPreference partially : {}, {}", id, learnerPreference);
        if (learnerPreference.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, learnerPreference.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!learnerPreferenceRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<LearnerPreference> result = learnerPreferenceService.partialUpdate(learnerPreference);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, learnerPreference.getId())
        );
    }

    /**
     * {@code GET  /learner-preferences} : get all the learnerPreferences.
     *
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of learnerPreferences in body.
     */
    @GetMapping("/learner-preferences")
    public List<LearnerPreference> getAllLearnerPreferences(@RequestParam(required = false, defaultValue = "false") boolean eagerload) {
        log.debug("REST request to get all LearnerPreferences");
        return learnerPreferenceService.findAll();
    }

    /**
     * {@code GET  /learner-preferences/:id} : get the "id" learnerPreference.
     *
     * @param id the id of the learnerPreference to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the learnerPreference, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/learner-preferences/{id}")
    public ResponseEntity<LearnerPreference> getLearnerPreference(@PathVariable String id) {
        log.debug("REST request to get LearnerPreference : {}", id);
        Optional<LearnerPreference> learnerPreference = learnerPreferenceService.findOne(id);
        return ResponseUtil.wrapOrNotFound(learnerPreference);
    }

    /**
     * {@code DELETE  /learner-preferences/:id} : delete the "id" learnerPreference.
     *
     * @param id the id of the learnerPreference to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/learner-preferences/{id}")
    public ResponseEntity<Void> deleteLearnerPreference(@PathVariable String id) {
        log.debug("REST request to delete LearnerPreference : {}", id);
        learnerPreferenceService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id)).build();
    }
}
