package com.modis.edu.web.rest;

import com.modis.edu.domain.EducatorPreference;
import com.modis.edu.repository.EducatorPreferenceRepository;
import com.modis.edu.service.EducatorPreferenceService;
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
 * REST controller for managing {@link com.modis.edu.domain.EducatorPreference}.
 */
@RestController
@RequestMapping("/api")
public class EducatorPreferenceResource {

    private final Logger log = LoggerFactory.getLogger(EducatorPreferenceResource.class);

    private static final String ENTITY_NAME = "educatorPreference";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EducatorPreferenceService educatorPreferenceService;

    private final EducatorPreferenceRepository educatorPreferenceRepository;

    public EducatorPreferenceResource(
        EducatorPreferenceService educatorPreferenceService,
        EducatorPreferenceRepository educatorPreferenceRepository
    ) {
        this.educatorPreferenceService = educatorPreferenceService;
        this.educatorPreferenceRepository = educatorPreferenceRepository;
    }

    /**
     * {@code POST  /educator-preferences} : Create a new educatorPreference.
     *
     * @param educatorPreference the educatorPreference to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new educatorPreference, or with status {@code 400 (Bad Request)} if the educatorPreference has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/educator-preferences")
    public ResponseEntity<EducatorPreference> createEducatorPreference(@RequestBody EducatorPreference educatorPreference)
        throws URISyntaxException {
        log.debug("REST request to save EducatorPreference : {}", educatorPreference);
        if (educatorPreference.getId() != null) {
            throw new BadRequestAlertException("A new educatorPreference cannot already have an ID", ENTITY_NAME, "idexists");
        }
        EducatorPreference result = educatorPreferenceService.save(educatorPreference);
        return ResponseEntity
            .created(new URI("/api/educator-preferences/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId()))
            .body(result);
    }

    /**
     * {@code PUT  /educator-preferences/:id} : Updates an existing educatorPreference.
     *
     * @param id the id of the educatorPreference to save.
     * @param educatorPreference the educatorPreference to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated educatorPreference,
     * or with status {@code 400 (Bad Request)} if the educatorPreference is not valid,
     * or with status {@code 500 (Internal Server Error)} if the educatorPreference couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/educator-preferences/{id}")
    public ResponseEntity<EducatorPreference> updateEducatorPreference(
        @PathVariable(value = "id", required = false) final String id,
        @RequestBody EducatorPreference educatorPreference
    ) throws URISyntaxException {
        log.debug("REST request to update EducatorPreference : {}, {}", id, educatorPreference);
        if (educatorPreference.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, educatorPreference.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!educatorPreferenceRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        EducatorPreference result = educatorPreferenceService.update(educatorPreference);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, educatorPreference.getId()))
            .body(result);
    }

    /**
     * {@code PATCH  /educator-preferences/:id} : Partial updates given fields of an existing educatorPreference, field will ignore if it is null
     *
     * @param id the id of the educatorPreference to save.
     * @param educatorPreference the educatorPreference to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated educatorPreference,
     * or with status {@code 400 (Bad Request)} if the educatorPreference is not valid,
     * or with status {@code 404 (Not Found)} if the educatorPreference is not found,
     * or with status {@code 500 (Internal Server Error)} if the educatorPreference couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/educator-preferences/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<EducatorPreference> partialUpdateEducatorPreference(
        @PathVariable(value = "id", required = false) final String id,
        @RequestBody EducatorPreference educatorPreference
    ) throws URISyntaxException {
        log.debug("REST request to partial update EducatorPreference partially : {}, {}", id, educatorPreference);
        if (educatorPreference.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, educatorPreference.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!educatorPreferenceRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<EducatorPreference> result = educatorPreferenceService.partialUpdate(educatorPreference);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, educatorPreference.getId())
        );
    }

    /**
     * {@code GET  /educator-preferences} : get all the educatorPreferences.
     *
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of educatorPreferences in body.
     */
    @GetMapping("/educator-preferences")
    public List<EducatorPreference> getAllEducatorPreferences(@RequestParam(required = false, defaultValue = "false") boolean eagerload) {
        log.debug("REST request to get all EducatorPreferences");
        return educatorPreferenceService.findAll();
    }

    /**
     * {@code GET  /educator-preferences/:id} : get the "id" educatorPreference.
     *
     * @param id the id of the educatorPreference to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the educatorPreference, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/educator-preferences/{id}")
    public ResponseEntity<EducatorPreference> getEducatorPreference(@PathVariable String id) {
        log.debug("REST request to get EducatorPreference : {}", id);
        Optional<EducatorPreference> educatorPreference = educatorPreferenceService.findOne(id);
        return ResponseUtil.wrapOrNotFound(educatorPreference);
    }

    /**
     * {@code DELETE  /educator-preferences/:id} : delete the "id" educatorPreference.
     *
     * @param id the id of the educatorPreference to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/educator-preferences/{id}")
    public ResponseEntity<Void> deleteEducatorPreference(@PathVariable String id) {
        log.debug("REST request to delete EducatorPreference : {}", id);
        educatorPreferenceService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id)).build();
    }
}
