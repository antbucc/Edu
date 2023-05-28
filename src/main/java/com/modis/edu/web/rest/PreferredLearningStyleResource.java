package com.modis.edu.web.rest;

import com.modis.edu.domain.PreferredLearningStyle;
import com.modis.edu.repository.PreferredLearningStyleRepository;
import com.modis.edu.service.PreferredLearningStyleService;
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
 * REST controller for managing {@link com.modis.edu.domain.PreferredLearningStyle}.
 */
@RestController
@RequestMapping("/api")
public class PreferredLearningStyleResource {

    private final Logger log = LoggerFactory.getLogger(PreferredLearningStyleResource.class);

    private static final String ENTITY_NAME = "preferredLearningStyle";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PreferredLearningStyleService preferredLearningStyleService;

    private final PreferredLearningStyleRepository preferredLearningStyleRepository;

    public PreferredLearningStyleResource(
        PreferredLearningStyleService preferredLearningStyleService,
        PreferredLearningStyleRepository preferredLearningStyleRepository
    ) {
        this.preferredLearningStyleService = preferredLearningStyleService;
        this.preferredLearningStyleRepository = preferredLearningStyleRepository;
    }

    /**
     * {@code POST  /preferred-learning-styles} : Create a new preferredLearningStyle.
     *
     * @param preferredLearningStyle the preferredLearningStyle to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new preferredLearningStyle, or with status {@code 400 (Bad Request)} if the preferredLearningStyle has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/preferred-learning-styles")
    public ResponseEntity<PreferredLearningStyle> createPreferredLearningStyle(@RequestBody PreferredLearningStyle preferredLearningStyle)
        throws URISyntaxException {
        log.debug("REST request to save PreferredLearningStyle : {}", preferredLearningStyle);
        if (preferredLearningStyle.getId() != null) {
            throw new BadRequestAlertException("A new preferredLearningStyle cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PreferredLearningStyle result = preferredLearningStyleService.save(preferredLearningStyle);
        return ResponseEntity
            .created(new URI("/api/preferred-learning-styles/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId()))
            .body(result);
    }

    /**
     * {@code PUT  /preferred-learning-styles/:id} : Updates an existing preferredLearningStyle.
     *
     * @param id the id of the preferredLearningStyle to save.
     * @param preferredLearningStyle the preferredLearningStyle to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated preferredLearningStyle,
     * or with status {@code 400 (Bad Request)} if the preferredLearningStyle is not valid,
     * or with status {@code 500 (Internal Server Error)} if the preferredLearningStyle couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/preferred-learning-styles/{id}")
    public ResponseEntity<PreferredLearningStyle> updatePreferredLearningStyle(
        @PathVariable(value = "id", required = false) final String id,
        @RequestBody PreferredLearningStyle preferredLearningStyle
    ) throws URISyntaxException {
        log.debug("REST request to update PreferredLearningStyle : {}, {}", id, preferredLearningStyle);
        if (preferredLearningStyle.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, preferredLearningStyle.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!preferredLearningStyleRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        PreferredLearningStyle result = preferredLearningStyleService.update(preferredLearningStyle);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, preferredLearningStyle.getId()))
            .body(result);
    }

    /**
     * {@code PATCH  /preferred-learning-styles/:id} : Partial updates given fields of an existing preferredLearningStyle, field will ignore if it is null
     *
     * @param id the id of the preferredLearningStyle to save.
     * @param preferredLearningStyle the preferredLearningStyle to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated preferredLearningStyle,
     * or with status {@code 400 (Bad Request)} if the preferredLearningStyle is not valid,
     * or with status {@code 404 (Not Found)} if the preferredLearningStyle is not found,
     * or with status {@code 500 (Internal Server Error)} if the preferredLearningStyle couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/preferred-learning-styles/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<PreferredLearningStyle> partialUpdatePreferredLearningStyle(
        @PathVariable(value = "id", required = false) final String id,
        @RequestBody PreferredLearningStyle preferredLearningStyle
    ) throws URISyntaxException {
        log.debug("REST request to partial update PreferredLearningStyle partially : {}, {}", id, preferredLearningStyle);
        if (preferredLearningStyle.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, preferredLearningStyle.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!preferredLearningStyleRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<PreferredLearningStyle> result = preferredLearningStyleService.partialUpdate(preferredLearningStyle);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, preferredLearningStyle.getId())
        );
    }

    /**
     * {@code GET  /preferred-learning-styles} : get all the preferredLearningStyles.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of preferredLearningStyles in body.
     */
    @GetMapping("/preferred-learning-styles")
    public List<PreferredLearningStyle> getAllPreferredLearningStyles() {
        log.debug("REST request to get all PreferredLearningStyles");
        return preferredLearningStyleService.findAll();
    }

    /**
     * {@code GET  /preferred-learning-styles/:id} : get the "id" preferredLearningStyle.
     *
     * @param id the id of the preferredLearningStyle to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the preferredLearningStyle, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/preferred-learning-styles/{id}")
    public ResponseEntity<PreferredLearningStyle> getPreferredLearningStyle(@PathVariable String id) {
        log.debug("REST request to get PreferredLearningStyle : {}", id);
        Optional<PreferredLearningStyle> preferredLearningStyle = preferredLearningStyleService.findOne(id);
        return ResponseUtil.wrapOrNotFound(preferredLearningStyle);
    }

    /**
     * {@code DELETE  /preferred-learning-styles/:id} : delete the "id" preferredLearningStyle.
     *
     * @param id the id of the preferredLearningStyle to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/preferred-learning-styles/{id}")
    public ResponseEntity<Void> deletePreferredLearningStyle(@PathVariable String id) {
        log.debug("REST request to delete PreferredLearningStyle : {}", id);
        preferredLearningStyleService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id)).build();
    }
}
