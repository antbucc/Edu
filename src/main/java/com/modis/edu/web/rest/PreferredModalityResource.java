package com.modis.edu.web.rest;

import com.modis.edu.domain.PreferredModality;
import com.modis.edu.repository.PreferredModalityRepository;
import com.modis.edu.service.PreferredModalityService;
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
 * REST controller for managing {@link com.modis.edu.domain.PreferredModality}.
 */
@RestController
@RequestMapping("/api")
public class PreferredModalityResource {

    private final Logger log = LoggerFactory.getLogger(PreferredModalityResource.class);

    private static final String ENTITY_NAME = "preferredModality";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PreferredModalityService preferredModalityService;

    private final PreferredModalityRepository preferredModalityRepository;

    public PreferredModalityResource(
        PreferredModalityService preferredModalityService,
        PreferredModalityRepository preferredModalityRepository
    ) {
        this.preferredModalityService = preferredModalityService;
        this.preferredModalityRepository = preferredModalityRepository;
    }

    /**
     * {@code POST  /preferred-modalities} : Create a new preferredModality.
     *
     * @param preferredModality the preferredModality to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new preferredModality, or with status {@code 400 (Bad Request)} if the preferredModality has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/preferred-modalities")
    public ResponseEntity<PreferredModality> createPreferredModality(@RequestBody PreferredModality preferredModality)
        throws URISyntaxException {
        log.debug("REST request to save PreferredModality : {}", preferredModality);
        if (preferredModality.getId() != null) {
            throw new BadRequestAlertException("A new preferredModality cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PreferredModality result = preferredModalityService.save(preferredModality);
        return ResponseEntity
            .created(new URI("/api/preferred-modalities/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId()))
            .body(result);
    }

    /**
     * {@code PUT  /preferred-modalities/:id} : Updates an existing preferredModality.
     *
     * @param id the id of the preferredModality to save.
     * @param preferredModality the preferredModality to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated preferredModality,
     * or with status {@code 400 (Bad Request)} if the preferredModality is not valid,
     * or with status {@code 500 (Internal Server Error)} if the preferredModality couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/preferred-modalities/{id}")
    public ResponseEntity<PreferredModality> updatePreferredModality(
        @PathVariable(value = "id", required = false) final String id,
        @RequestBody PreferredModality preferredModality
    ) throws URISyntaxException {
        log.debug("REST request to update PreferredModality : {}, {}", id, preferredModality);
        if (preferredModality.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, preferredModality.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!preferredModalityRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        PreferredModality result = preferredModalityService.update(preferredModality);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, preferredModality.getId()))
            .body(result);
    }

    /**
     * {@code PATCH  /preferred-modalities/:id} : Partial updates given fields of an existing preferredModality, field will ignore if it is null
     *
     * @param id the id of the preferredModality to save.
     * @param preferredModality the preferredModality to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated preferredModality,
     * or with status {@code 400 (Bad Request)} if the preferredModality is not valid,
     * or with status {@code 404 (Not Found)} if the preferredModality is not found,
     * or with status {@code 500 (Internal Server Error)} if the preferredModality couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/preferred-modalities/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<PreferredModality> partialUpdatePreferredModality(
        @PathVariable(value = "id", required = false) final String id,
        @RequestBody PreferredModality preferredModality
    ) throws URISyntaxException {
        log.debug("REST request to partial update PreferredModality partially : {}, {}", id, preferredModality);
        if (preferredModality.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, preferredModality.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!preferredModalityRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<PreferredModality> result = preferredModalityService.partialUpdate(preferredModality);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, preferredModality.getId())
        );
    }

    /**
     * {@code GET  /preferred-modalities} : get all the preferredModalities.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of preferredModalities in body.
     */
    @GetMapping("/preferred-modalities")
    public List<PreferredModality> getAllPreferredModalities() {
        log.debug("REST request to get all PreferredModalities");
        return preferredModalityService.findAll();
    }

    /**
     * {@code GET  /preferred-modalities/:id} : get the "id" preferredModality.
     *
     * @param id the id of the preferredModality to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the preferredModality, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/preferred-modalities/{id}")
    public ResponseEntity<PreferredModality> getPreferredModality(@PathVariable String id) {
        log.debug("REST request to get PreferredModality : {}", id);
        Optional<PreferredModality> preferredModality = preferredModalityService.findOne(id);
        return ResponseUtil.wrapOrNotFound(preferredModality);
    }

    /**
     * {@code DELETE  /preferred-modalities/:id} : delete the "id" preferredModality.
     *
     * @param id the id of the preferredModality to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/preferred-modalities/{id}")
    public ResponseEntity<Void> deletePreferredModality(@PathVariable String id) {
        log.debug("REST request to delete PreferredModality : {}", id);
        preferredModalityService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id)).build();
    }
}
