package com.modis.edu.web.rest;

import com.modis.edu.domain.SetOf;
import com.modis.edu.repository.SetRepository;
import com.modis.edu.service.SetService;
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
 * REST controller for managing {@link com.modis.edu.domain.SetOf}.
 */
@RestController
@RequestMapping("/api")
public class SetResource {

    private final Logger log = LoggerFactory.getLogger(SetResource.class);

    private static final String ENTITY_NAME = "set";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SetService setService;

    private final SetRepository setRepository;

    public SetResource(SetService setService, SetRepository setRepository) {
        this.setService = setService;
        this.setRepository = setRepository;
    }

    /**
     * {@code POST  /sets} : Create a new set.
     *
     * @param set the set to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with
     *         body the new set, or with status {@code 400 (Bad Request)} if the set
     *         has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/sets")
    public ResponseEntity<SetOf> createSet(@RequestBody SetOf set) throws URISyntaxException {
        log.debug("REST request to save Set : {}", set);
        if (set.getId() != null) {
            throw new BadRequestAlertException("A new set cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SetOf result = setService.save(set);
        return ResponseEntity
            .created(new URI("/api/sets/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId()))
            .body(result);
    }

    /**
     * {@code PUT  /sets/:id} : Updates an existing set.
     *
     * @param id  the id of the set to save.
     * @param set the set to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
     *         the updated set,
     *         or with status {@code 400 (Bad Request)} if the set is not valid,
     *         or with status {@code 500 (Internal Server Error)} if the set
     *         couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/sets/{id}")
    public ResponseEntity<SetOf> updateSet(@PathVariable(value = "id", required = false) final String id, @RequestBody SetOf set)
        throws URISyntaxException {
        log.debug("REST request to update Set : {}, {}", id, set);
        if (set.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, set.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!setRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        SetOf result = setService.update(set);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, set.getId()))
            .body(result);
    }

    /**
     * {@code PATCH  /sets/:id} : Partial updates given fields of an existing set,
     * field will ignore if it is null
     *
     * @param id  the id of the set to save.
     * @param set the set to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
     *         the updated set,
     *         or with status {@code 400 (Bad Request)} if the set is not valid,
     *         or with status {@code 404 (Not Found)} if the set is not found,
     *         or with status {@code 500 (Internal Server Error)} if the set
     *         couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/sets/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<SetOf> partialUpdateSet(@PathVariable(value = "id", required = false) final String id, @RequestBody SetOf set)
        throws URISyntaxException {
        log.debug("REST request to partial update Set partially : {}, {}", id, set);
        if (set.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, set.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!setRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<SetOf> result = setService.partialUpdate(set);

        return ResponseUtil.wrapOrNotFound(result, HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, set.getId()));
    }

    /**
     * {@code GET  /sets} : get all the sets.
     *
     * @param filter the filter of the request.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list
     *         of sets in body.
     */
    @GetMapping("/sets")
    public List<SetOf> getAllSets(@RequestParam(required = false) String filter) {
        if ("fragment-is-null".equals(filter)) {
            log.debug("REST request to get all Sets where fragment is null");
            return setService.findAllWhereFragmentIsNull();
        }
        log.debug("REST request to get all Sets");
        return setService.findAll();
    }

    /**
     * {@code GET  /sets/:id} : get the "id" set.
     *
     * @param id the id of the set to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
     *         the set, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/sets/{id}")
    public ResponseEntity<SetOf> getSet(@PathVariable String id) {
        log.debug("REST request to get Set : {}", id);
        Optional<SetOf> set = setService.findOne(id);
        return ResponseUtil.wrapOrNotFound(set);
    }

    /**
     * {@code DELETE  /sets/:id} : delete the "id" set.
     *
     * @param id the id of the set to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/sets/{id}")
    public ResponseEntity<Void> deleteSet(@PathVariable String id) {
        log.debug("REST request to delete Set : {}", id);
        setService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id)).build();
    }
}
