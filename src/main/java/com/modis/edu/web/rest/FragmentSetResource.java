package com.modis.edu.web.rest;

import com.modis.edu.domain.FragmentSet;
import com.modis.edu.repository.FragmentSetRepository;
import com.modis.edu.service.FragmentSetService;
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
 * REST controller for managing {@link com.modis.edu.domain.FragmentSet}.
 */
@RestController
@RequestMapping("/api")
public class FragmentSetResource {

    private final Logger log = LoggerFactory.getLogger(FragmentSetResource.class);

    private static final String ENTITY_NAME = "fragmentSet";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final FragmentSetService fragmentSetService;

    private final FragmentSetRepository fragmentSetRepository;

    public FragmentSetResource(FragmentSetService fragmentSetService, FragmentSetRepository fragmentSetRepository) {
        this.fragmentSetService = fragmentSetService;
        this.fragmentSetRepository = fragmentSetRepository;
    }

    /**
     * {@code POST  /fragment-sets} : Create a new fragmentSet.
     *
     * @param fragmentSet the fragmentSet to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new fragmentSet, or with status {@code 400 (Bad Request)} if the fragmentSet has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/fragment-sets")
    public ResponseEntity<FragmentSet> createFragmentSet(@RequestBody FragmentSet fragmentSet) throws URISyntaxException {
        log.debug("REST request to save FragmentSet : {}", fragmentSet);
        if (fragmentSet.getId() != null) {
            throw new BadRequestAlertException("A new fragmentSet cannot already have an ID", ENTITY_NAME, "idexists");
        }
        FragmentSet result = fragmentSetService.save(fragmentSet);
        return ResponseEntity
            .created(new URI("/api/fragment-sets/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId()))
            .body(result);
    }

    /**
     * {@code PUT  /fragment-sets/:id} : Updates an existing fragmentSet.
     *
     * @param id the id of the fragmentSet to save.
     * @param fragmentSet the fragmentSet to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated fragmentSet,
     * or with status {@code 400 (Bad Request)} if the fragmentSet is not valid,
     * or with status {@code 500 (Internal Server Error)} if the fragmentSet couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/fragment-sets/{id}")
    public ResponseEntity<FragmentSet> updateFragmentSet(
        @PathVariable(value = "id", required = false) final String id,
        @RequestBody FragmentSet fragmentSet
    ) throws URISyntaxException {
        log.debug("REST request to update FragmentSet : {}, {}", id, fragmentSet);
        if (fragmentSet.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, fragmentSet.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!fragmentSetRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        FragmentSet result = fragmentSetService.update(fragmentSet);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, fragmentSet.getId()))
            .body(result);
    }

    /**
     * {@code PATCH  /fragment-sets/:id} : Partial updates given fields of an existing fragmentSet, field will ignore if it is null
     *
     * @param id the id of the fragmentSet to save.
     * @param fragmentSet the fragmentSet to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated fragmentSet,
     * or with status {@code 400 (Bad Request)} if the fragmentSet is not valid,
     * or with status {@code 404 (Not Found)} if the fragmentSet is not found,
     * or with status {@code 500 (Internal Server Error)} if the fragmentSet couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/fragment-sets/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<FragmentSet> partialUpdateFragmentSet(
        @PathVariable(value = "id", required = false) final String id,
        @RequestBody FragmentSet fragmentSet
    ) throws URISyntaxException {
        log.debug("REST request to partial update FragmentSet partially : {}, {}", id, fragmentSet);
        if (fragmentSet.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, fragmentSet.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!fragmentSetRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<FragmentSet> result = fragmentSetService.partialUpdate(fragmentSet);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, fragmentSet.getId())
        );
    }

    /**
     * {@code GET  /fragment-sets} : get all the fragmentSets.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of fragmentSets in body.
     */
    @GetMapping("/fragment-sets")
    public List<FragmentSet> getAllFragmentSets() {
        log.debug("REST request to get all FragmentSets");
        return fragmentSetService.findAll();
    }

    /**
     * {@code GET  /fragment-sets/:id} : get the "id" fragmentSet.
     *
     * @param id the id of the fragmentSet to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the fragmentSet, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/fragment-sets/{id}")
    public ResponseEntity<FragmentSet> getFragmentSet(@PathVariable String id) {
        log.debug("REST request to get FragmentSet : {}", id);
        Optional<FragmentSet> fragmentSet = fragmentSetService.findOne(id);
        return ResponseUtil.wrapOrNotFound(fragmentSet);
    }

    /**
     * {@code DELETE  /fragment-sets/:id} : delete the "id" fragmentSet.
     *
     * @param id the id of the fragmentSet to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/fragment-sets/{id}")
    public ResponseEntity<Void> deleteFragmentSet(@PathVariable String id) {
        log.debug("REST request to delete FragmentSet : {}", id);
        fragmentSetService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id)).build();
    }
}
