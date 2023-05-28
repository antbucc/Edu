package com.modis.edu.web.rest;

import com.modis.edu.domain.PreferredActivity;
import com.modis.edu.repository.PreferredActivityRepository;
import com.modis.edu.service.PreferredActivityService;
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
 * REST controller for managing {@link com.modis.edu.domain.PreferredActivity}.
 */
@RestController
@RequestMapping("/api")
public class PreferredActivityResource {

    private final Logger log = LoggerFactory.getLogger(PreferredActivityResource.class);

    private static final String ENTITY_NAME = "preferredActivity";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PreferredActivityService preferredActivityService;

    private final PreferredActivityRepository preferredActivityRepository;

    public PreferredActivityResource(
        PreferredActivityService preferredActivityService,
        PreferredActivityRepository preferredActivityRepository
    ) {
        this.preferredActivityService = preferredActivityService;
        this.preferredActivityRepository = preferredActivityRepository;
    }

    /**
     * {@code POST  /preferred-activities} : Create a new preferredActivity.
     *
     * @param preferredActivity the preferredActivity to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new preferredActivity, or with status {@code 400 (Bad Request)} if the preferredActivity has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/preferred-activities")
    public ResponseEntity<PreferredActivity> createPreferredActivity(@RequestBody PreferredActivity preferredActivity)
        throws URISyntaxException {
        log.debug("REST request to save PreferredActivity : {}", preferredActivity);
        if (preferredActivity.getId() != null) {
            throw new BadRequestAlertException("A new preferredActivity cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PreferredActivity result = preferredActivityService.save(preferredActivity);
        return ResponseEntity
            .created(new URI("/api/preferred-activities/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId()))
            .body(result);
    }

    /**
     * {@code PUT  /preferred-activities/:id} : Updates an existing preferredActivity.
     *
     * @param id the id of the preferredActivity to save.
     * @param preferredActivity the preferredActivity to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated preferredActivity,
     * or with status {@code 400 (Bad Request)} if the preferredActivity is not valid,
     * or with status {@code 500 (Internal Server Error)} if the preferredActivity couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/preferred-activities/{id}")
    public ResponseEntity<PreferredActivity> updatePreferredActivity(
        @PathVariable(value = "id", required = false) final String id,
        @RequestBody PreferredActivity preferredActivity
    ) throws URISyntaxException {
        log.debug("REST request to update PreferredActivity : {}, {}", id, preferredActivity);
        if (preferredActivity.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, preferredActivity.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!preferredActivityRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        PreferredActivity result = preferredActivityService.update(preferredActivity);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, preferredActivity.getId()))
            .body(result);
    }

    /**
     * {@code PATCH  /preferred-activities/:id} : Partial updates given fields of an existing preferredActivity, field will ignore if it is null
     *
     * @param id the id of the preferredActivity to save.
     * @param preferredActivity the preferredActivity to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated preferredActivity,
     * or with status {@code 400 (Bad Request)} if the preferredActivity is not valid,
     * or with status {@code 404 (Not Found)} if the preferredActivity is not found,
     * or with status {@code 500 (Internal Server Error)} if the preferredActivity couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/preferred-activities/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<PreferredActivity> partialUpdatePreferredActivity(
        @PathVariable(value = "id", required = false) final String id,
        @RequestBody PreferredActivity preferredActivity
    ) throws URISyntaxException {
        log.debug("REST request to partial update PreferredActivity partially : {}, {}", id, preferredActivity);
        if (preferredActivity.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, preferredActivity.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!preferredActivityRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<PreferredActivity> result = preferredActivityService.partialUpdate(preferredActivity);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, preferredActivity.getId())
        );
    }

    /**
     * {@code GET  /preferred-activities} : get all the preferredActivities.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of preferredActivities in body.
     */
    @GetMapping("/preferred-activities")
    public List<PreferredActivity> getAllPreferredActivities() {
        log.debug("REST request to get all PreferredActivities");
        return preferredActivityService.findAll();
    }

    /**
     * {@code GET  /preferred-activities/:id} : get the "id" preferredActivity.
     *
     * @param id the id of the preferredActivity to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the preferredActivity, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/preferred-activities/{id}")
    public ResponseEntity<PreferredActivity> getPreferredActivity(@PathVariable String id) {
        log.debug("REST request to get PreferredActivity : {}", id);
        Optional<PreferredActivity> preferredActivity = preferredActivityService.findOne(id);
        return ResponseUtil.wrapOrNotFound(preferredActivity);
    }

    /**
     * {@code DELETE  /preferred-activities/:id} : delete the "id" preferredActivity.
     *
     * @param id the id of the preferredActivity to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/preferred-activities/{id}")
    public ResponseEntity<Void> deletePreferredActivity(@PathVariable String id) {
        log.debug("REST request to delete PreferredActivity : {}", id);
        preferredActivityService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id)).build();
    }
}
