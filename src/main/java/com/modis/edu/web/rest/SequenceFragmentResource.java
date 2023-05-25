package com.modis.edu.web.rest;

import com.modis.edu.domain.SequenceFragment;
import com.modis.edu.repository.SequenceFragmentRepository;
import com.modis.edu.service.SequenceFragmentService;
import com.modis.edu.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.modis.edu.domain.SequenceFragment}.
 */
@RestController
@RequestMapping("/api")
public class SequenceFragmentResource {

    private final Logger log = LoggerFactory.getLogger(SequenceFragmentResource.class);

    private static final String ENTITY_NAME = "sequenceFragment";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SequenceFragmentService sequenceFragmentService;

    private final SequenceFragmentRepository sequenceFragmentRepository;

    public SequenceFragmentResource(
        SequenceFragmentService sequenceFragmentService,
        SequenceFragmentRepository sequenceFragmentRepository
    ) {
        this.sequenceFragmentService = sequenceFragmentService;
        this.sequenceFragmentRepository = sequenceFragmentRepository;
    }

    /**
     * {@code POST  /sequence-fragments} : Create a new sequenceFragment.
     *
     * @param sequenceFragment the sequenceFragment to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new sequenceFragment, or with status {@code 400 (Bad Request)} if the sequenceFragment has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/sequence-fragments")
    public ResponseEntity<SequenceFragment> createSequenceFragment(@Valid @RequestBody SequenceFragment sequenceFragment)
        throws URISyntaxException {
        log.debug("REST request to save SequenceFragment : {}", sequenceFragment);
        if (sequenceFragment.getId() != null) {
            throw new BadRequestAlertException("A new sequenceFragment cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SequenceFragment result = sequenceFragmentService.save(sequenceFragment);
        return ResponseEntity
            .created(new URI("/api/sequence-fragments/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId()))
            .body(result);
    }

    /**
     * {@code PUT  /sequence-fragments/:id} : Updates an existing sequenceFragment.
     *
     * @param id the id of the sequenceFragment to save.
     * @param sequenceFragment the sequenceFragment to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated sequenceFragment,
     * or with status {@code 400 (Bad Request)} if the sequenceFragment is not valid,
     * or with status {@code 500 (Internal Server Error)} if the sequenceFragment couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/sequence-fragments/{id}")
    public ResponseEntity<SequenceFragment> updateSequenceFragment(
        @PathVariable(value = "id", required = false) final String id,
        @Valid @RequestBody SequenceFragment sequenceFragment
    ) throws URISyntaxException {
        log.debug("REST request to update SequenceFragment : {}, {}", id, sequenceFragment);
        if (sequenceFragment.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, sequenceFragment.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!sequenceFragmentRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        SequenceFragment result = sequenceFragmentService.update(sequenceFragment);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, sequenceFragment.getId()))
            .body(result);
    }

    /**
     * {@code PATCH  /sequence-fragments/:id} : Partial updates given fields of an existing sequenceFragment, field will ignore if it is null
     *
     * @param id the id of the sequenceFragment to save.
     * @param sequenceFragment the sequenceFragment to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated sequenceFragment,
     * or with status {@code 400 (Bad Request)} if the sequenceFragment is not valid,
     * or with status {@code 404 (Not Found)} if the sequenceFragment is not found,
     * or with status {@code 500 (Internal Server Error)} if the sequenceFragment couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/sequence-fragments/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<SequenceFragment> partialUpdateSequenceFragment(
        @PathVariable(value = "id", required = false) final String id,
        @NotNull @RequestBody SequenceFragment sequenceFragment
    ) throws URISyntaxException {
        log.debug("REST request to partial update SequenceFragment partially : {}, {}", id, sequenceFragment);
        if (sequenceFragment.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, sequenceFragment.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!sequenceFragmentRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<SequenceFragment> result = sequenceFragmentService.partialUpdate(sequenceFragment);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, sequenceFragment.getId())
        );
    }

    /**
     * {@code GET  /sequence-fragments} : get all the sequenceFragments.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of sequenceFragments in body.
     */
    @GetMapping("/sequence-fragments")
    public List<SequenceFragment> getAllSequenceFragments() {
        log.debug("REST request to get all SequenceFragments");
        return sequenceFragmentService.findAll();
    }

    /**
     * {@code GET  /sequence-fragments/:id} : get the "id" sequenceFragment.
     *
     * @param id the id of the sequenceFragment to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the sequenceFragment, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/sequence-fragments/{id}")
    public ResponseEntity<SequenceFragment> getSequenceFragment(@PathVariable String id) {
        log.debug("REST request to get SequenceFragment : {}", id);
        Optional<SequenceFragment> sequenceFragment = sequenceFragmentService.findOne(id);
        return ResponseUtil.wrapOrNotFound(sequenceFragment);
    }

    /**
     * {@code DELETE  /sequence-fragments/:id} : delete the "id" sequenceFragment.
     *
     * @param id the id of the sequenceFragment to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/sequence-fragments/{id}")
    public ResponseEntity<Void> deleteSequenceFragment(@PathVariable String id) {
        log.debug("REST request to delete SequenceFragment : {}", id);
        sequenceFragmentService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id)).build();
    }
}
