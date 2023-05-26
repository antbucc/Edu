package com.modis.edu.web.rest;

import com.modis.edu.domain.AbstractActivity;
import com.modis.edu.repository.AbstractActivityRepository;
import com.modis.edu.service.AbstractActivityService;
import com.modis.edu.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.StreamSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.modis.edu.domain.AbstractActivity}.
 */
@RestController
@RequestMapping("/api")
public class AbstractActivityResource {

    private final Logger log = LoggerFactory.getLogger(AbstractActivityResource.class);

    private static final String ENTITY_NAME = "abstractActivity";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AbstractActivityService abstractActivityService;

    private final AbstractActivityRepository abstractActivityRepository;

    public AbstractActivityResource(
        AbstractActivityService abstractActivityService,
        AbstractActivityRepository abstractActivityRepository
    ) {
        this.abstractActivityService = abstractActivityService;
        this.abstractActivityRepository = abstractActivityRepository;
    }

    /**
     * {@code POST  /abstract-activities} : Create a new abstractActivity.
     *
     * @param abstractActivity the abstractActivity to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new abstractActivity, or with status {@code 400 (Bad Request)} if the abstractActivity has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/abstract-activities")
    public ResponseEntity<AbstractActivity> createAbstractActivity(@RequestBody AbstractActivity abstractActivity)
        throws URISyntaxException {
        log.debug("REST request to save AbstractActivity : {}", abstractActivity);
        if (abstractActivity.getId() != null) {
            throw new BadRequestAlertException("A new abstractActivity cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AbstractActivity result = abstractActivityService.save(abstractActivity);
        return ResponseEntity
            .created(new URI("/api/abstract-activities/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId()))
            .body(result);
    }

    /**
     * {@code PUT  /abstract-activities/:id} : Updates an existing abstractActivity.
     *
     * @param id the id of the abstractActivity to save.
     * @param abstractActivity the abstractActivity to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated abstractActivity,
     * or with status {@code 400 (Bad Request)} if the abstractActivity is not valid,
     * or with status {@code 500 (Internal Server Error)} if the abstractActivity couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/abstract-activities/{id}")
    public ResponseEntity<AbstractActivity> updateAbstractActivity(
        @PathVariable(value = "id", required = false) final String id,
        @RequestBody AbstractActivity abstractActivity
    ) throws URISyntaxException {
        log.debug("REST request to update AbstractActivity : {}, {}", id, abstractActivity);
        if (abstractActivity.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, abstractActivity.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!abstractActivityRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        AbstractActivity result = abstractActivityService.update(abstractActivity);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, abstractActivity.getId()))
            .body(result);
    }

    /**
     * {@code PATCH  /abstract-activities/:id} : Partial updates given fields of an existing abstractActivity, field will ignore if it is null
     *
     * @param id the id of the abstractActivity to save.
     * @param abstractActivity the abstractActivity to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated abstractActivity,
     * or with status {@code 400 (Bad Request)} if the abstractActivity is not valid,
     * or with status {@code 404 (Not Found)} if the abstractActivity is not found,
     * or with status {@code 500 (Internal Server Error)} if the abstractActivity couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/abstract-activities/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<AbstractActivity> partialUpdateAbstractActivity(
        @PathVariable(value = "id", required = false) final String id,
        @RequestBody AbstractActivity abstractActivity
    ) throws URISyntaxException {
        log.debug("REST request to partial update AbstractActivity partially : {}, {}", id, abstractActivity);
        if (abstractActivity.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, abstractActivity.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!abstractActivityRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<AbstractActivity> result = abstractActivityService.partialUpdate(abstractActivity);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, abstractActivity.getId())
        );
    }

    /**
     * {@code GET  /abstract-activities} : get all the abstractActivities.
     *
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @param filter the filter of the request.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of abstractActivities in body.
     */
    @GetMapping("/abstract-activities")
    public List<AbstractActivity> getAllAbstractActivities(
        @RequestParam(required = false) String filter,
        @RequestParam(required = false, defaultValue = "false") boolean eagerload
    ) {
        if ("fragment-is-null".equals(filter)) {
            log.debug("REST request to get all AbstractActivitys where fragment is null");
            return abstractActivityService.findAllWhereFragmentIsNull();
        }
        log.debug("REST request to get all AbstractActivities");
        return abstractActivityService.findAll();
    }

    /**
     * {@code GET  /abstract-activities/:id} : get the "id" abstractActivity.
     *
     * @param id the id of the abstractActivity to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the abstractActivity, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/abstract-activities/{id}")
    public ResponseEntity<AbstractActivity> getAbstractActivity(@PathVariable String id) {
        log.debug("REST request to get AbstractActivity : {}", id);
        Optional<AbstractActivity> abstractActivity = abstractActivityService.findOne(id);
        return ResponseUtil.wrapOrNotFound(abstractActivity);
    }

    /**
     * {@code DELETE  /abstract-activities/:id} : delete the "id" abstractActivity.
     *
     * @param id the id of the abstractActivity to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/abstract-activities/{id}")
    public ResponseEntity<Void> deleteAbstractActivity(@PathVariable String id) {
        log.debug("REST request to delete AbstractActivity : {}", id);
        abstractActivityService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id)).build();
    }
}
