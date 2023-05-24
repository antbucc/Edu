package com.modis.edu.web.rest;

import com.modis.edu.domain.SetOf;
import com.modis.edu.repository.SetOfRepository;
import com.modis.edu.service.SetOfService;
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
public class SetOfResource {

    private final Logger log = LoggerFactory.getLogger(SetOfResource.class);

    private static final String ENTITY_NAME = "setOf";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SetOfService setOfService;

    private final SetOfRepository setOfRepository;

    public SetOfResource(SetOfService setOfService, SetOfRepository setOfRepository) {
        this.setOfService = setOfService;
        this.setOfRepository = setOfRepository;
    }

    /**
     * {@code POST  /set-ofs} : Create a new setOf.
     *
     * @param setOf the setOf to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new setOf, or with status {@code 400 (Bad Request)} if the setOf has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/set-ofs")
    public ResponseEntity<SetOf> createSetOf(@RequestBody SetOf setOf) throws URISyntaxException {
        log.debug("REST request to save SetOf : {}", setOf);
        if (setOf.getId() != null) {
            throw new BadRequestAlertException("A new setOf cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SetOf result = setOfService.save(setOf);
        return ResponseEntity
            .created(new URI("/api/set-ofs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId()))
            .body(result);
    }

    /**
     * {@code PUT  /set-ofs/:id} : Updates an existing setOf.
     *
     * @param id the id of the setOf to save.
     * @param setOf the setOf to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated setOf,
     * or with status {@code 400 (Bad Request)} if the setOf is not valid,
     * or with status {@code 500 (Internal Server Error)} if the setOf couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/set-ofs/{id}")
    public ResponseEntity<SetOf> updateSetOf(@PathVariable(value = "id", required = false) final String id, @RequestBody SetOf setOf)
        throws URISyntaxException {
        log.debug("REST request to update SetOf : {}, {}", id, setOf);
        if (setOf.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, setOf.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!setOfRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        SetOf result = setOfService.update(setOf);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, setOf.getId()))
            .body(result);
    }

    /**
     * {@code PATCH  /set-ofs/:id} : Partial updates given fields of an existing setOf, field will ignore if it is null
     *
     * @param id the id of the setOf to save.
     * @param setOf the setOf to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated setOf,
     * or with status {@code 400 (Bad Request)} if the setOf is not valid,
     * or with status {@code 404 (Not Found)} if the setOf is not found,
     * or with status {@code 500 (Internal Server Error)} if the setOf couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/set-ofs/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<SetOf> partialUpdateSetOf(
        @PathVariable(value = "id", required = false) final String id,
        @RequestBody SetOf setOf
    ) throws URISyntaxException {
        log.debug("REST request to partial update SetOf partially : {}, {}", id, setOf);
        if (setOf.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, setOf.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!setOfRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<SetOf> result = setOfService.partialUpdate(setOf);

        return ResponseUtil.wrapOrNotFound(result, HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, setOf.getId()));
    }

    /**
     * {@code GET  /set-ofs} : get all the setOfs.
     *
     * @param filter the filter of the request.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of setOfs in body.
     */
    @GetMapping("/set-ofs")
    public List<SetOf> getAllSetOfs(@RequestParam(required = false) String filter) {
        if ("fragment-is-null".equals(filter)) {
            log.debug("REST request to get all SetOfs where fragment is null");
            return setOfService.findAllWhereFragmentIsNull();
        }
        log.debug("REST request to get all SetOfs");
        return setOfService.findAll();
    }

    /**
     * {@code GET  /set-ofs/:id} : get the "id" setOf.
     *
     * @param id the id of the setOf to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the setOf, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/set-ofs/{id}")
    public ResponseEntity<SetOf> getSetOf(@PathVariable String id) {
        log.debug("REST request to get SetOf : {}", id);
        Optional<SetOf> setOf = setOfService.findOne(id);
        return ResponseUtil.wrapOrNotFound(setOf);
    }

    /**
     * {@code DELETE  /set-ofs/:id} : delete the "id" setOf.
     *
     * @param id the id of the setOf to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/set-ofs/{id}")
    public ResponseEntity<Void> deleteSetOf(@PathVariable String id) {
        log.debug("REST request to delete SetOf : {}", id);
        setOfService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id)).build();
    }
}
