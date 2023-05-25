package com.modis.edu.web.rest;

import com.modis.edu.domain.SetOfFragment;
import com.modis.edu.repository.SetOfFragmentRepository;
import com.modis.edu.service.SetOfFragmentService;
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
 * REST controller for managing {@link com.modis.edu.domain.SetOfFragment}.
 */
@RestController
@RequestMapping("/api")
public class SetOfFragmentResource {

    private final Logger log = LoggerFactory.getLogger(SetOfFragmentResource.class);

    private static final String ENTITY_NAME = "setOfFragment";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SetOfFragmentService setOfFragmentService;

    private final SetOfFragmentRepository setOfFragmentRepository;

    public SetOfFragmentResource(SetOfFragmentService setOfFragmentService, SetOfFragmentRepository setOfFragmentRepository) {
        this.setOfFragmentService = setOfFragmentService;
        this.setOfFragmentRepository = setOfFragmentRepository;
    }

    /**
     * {@code POST  /set-of-fragments} : Create a new setOfFragment.
     *
     * @param setOfFragment the setOfFragment to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new setOfFragment, or with status {@code 400 (Bad Request)} if the setOfFragment has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/set-of-fragments")
    public ResponseEntity<SetOfFragment> createSetOfFragment(@Valid @RequestBody SetOfFragment setOfFragment) throws URISyntaxException {
        log.debug("REST request to save SetOfFragment : {}", setOfFragment);
        if (setOfFragment.getId() != null) {
            throw new BadRequestAlertException("A new setOfFragment cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SetOfFragment result = setOfFragmentService.save(setOfFragment);
        return ResponseEntity
            .created(new URI("/api/set-of-fragments/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId()))
            .body(result);
    }

    /**
     * {@code PUT  /set-of-fragments/:id} : Updates an existing setOfFragment.
     *
     * @param id the id of the setOfFragment to save.
     * @param setOfFragment the setOfFragment to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated setOfFragment,
     * or with status {@code 400 (Bad Request)} if the setOfFragment is not valid,
     * or with status {@code 500 (Internal Server Error)} if the setOfFragment couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/set-of-fragments/{id}")
    public ResponseEntity<SetOfFragment> updateSetOfFragment(
        @PathVariable(value = "id", required = false) final String id,
        @Valid @RequestBody SetOfFragment setOfFragment
    ) throws URISyntaxException {
        log.debug("REST request to update SetOfFragment : {}, {}", id, setOfFragment);
        if (setOfFragment.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, setOfFragment.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!setOfFragmentRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        SetOfFragment result = setOfFragmentService.update(setOfFragment);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, setOfFragment.getId()))
            .body(result);
    }

    /**
     * {@code PATCH  /set-of-fragments/:id} : Partial updates given fields of an existing setOfFragment, field will ignore if it is null
     *
     * @param id the id of the setOfFragment to save.
     * @param setOfFragment the setOfFragment to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated setOfFragment,
     * or with status {@code 400 (Bad Request)} if the setOfFragment is not valid,
     * or with status {@code 404 (Not Found)} if the setOfFragment is not found,
     * or with status {@code 500 (Internal Server Error)} if the setOfFragment couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/set-of-fragments/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<SetOfFragment> partialUpdateSetOfFragment(
        @PathVariable(value = "id", required = false) final String id,
        @NotNull @RequestBody SetOfFragment setOfFragment
    ) throws URISyntaxException {
        log.debug("REST request to partial update SetOfFragment partially : {}, {}", id, setOfFragment);
        if (setOfFragment.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, setOfFragment.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!setOfFragmentRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<SetOfFragment> result = setOfFragmentService.partialUpdate(setOfFragment);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, setOfFragment.getId())
        );
    }

    /**
     * {@code GET  /set-of-fragments} : get all the setOfFragments.
     *
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of setOfFragments in body.
     */
    @GetMapping("/set-of-fragments")
    public List<SetOfFragment> getAllSetOfFragments(@RequestParam(required = false, defaultValue = "false") boolean eagerload) {
        log.debug("REST request to get all SetOfFragments");
        return setOfFragmentService.findAll();
    }

    /**
     * {@code GET  /set-of-fragments/:id} : get the "id" setOfFragment.
     *
     * @param id the id of the setOfFragment to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the setOfFragment, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/set-of-fragments/{id}")
    public ResponseEntity<SetOfFragment> getSetOfFragment(@PathVariable String id) {
        log.debug("REST request to get SetOfFragment : {}", id);
        Optional<SetOfFragment> setOfFragment = setOfFragmentService.findOne(id);
        return ResponseUtil.wrapOrNotFound(setOfFragment);
    }

    /**
     * {@code DELETE  /set-of-fragments/:id} : delete the "id" setOfFragment.
     *
     * @param id the id of the setOfFragment to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/set-of-fragments/{id}")
    public ResponseEntity<Void> deleteSetOfFragment(@PathVariable String id) {
        log.debug("REST request to delete SetOfFragment : {}", id);
        setOfFragmentService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id)).build();
    }
}
