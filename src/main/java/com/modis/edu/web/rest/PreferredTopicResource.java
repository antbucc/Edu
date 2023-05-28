package com.modis.edu.web.rest;

import com.modis.edu.domain.PreferredTopic;
import com.modis.edu.repository.PreferredTopicRepository;
import com.modis.edu.service.PreferredTopicService;
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
 * REST controller for managing {@link com.modis.edu.domain.PreferredTopic}.
 */
@RestController
@RequestMapping("/api")
public class PreferredTopicResource {

    private final Logger log = LoggerFactory.getLogger(PreferredTopicResource.class);

    private static final String ENTITY_NAME = "preferredTopic";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PreferredTopicService preferredTopicService;

    private final PreferredTopicRepository preferredTopicRepository;

    public PreferredTopicResource(PreferredTopicService preferredTopicService, PreferredTopicRepository preferredTopicRepository) {
        this.preferredTopicService = preferredTopicService;
        this.preferredTopicRepository = preferredTopicRepository;
    }

    /**
     * {@code POST  /preferred-topics} : Create a new preferredTopic.
     *
     * @param preferredTopic the preferredTopic to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new preferredTopic, or with status {@code 400 (Bad Request)} if the preferredTopic has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/preferred-topics")
    public ResponseEntity<PreferredTopic> createPreferredTopic(@RequestBody PreferredTopic preferredTopic) throws URISyntaxException {
        log.debug("REST request to save PreferredTopic : {}", preferredTopic);
        if (preferredTopic.getId() != null) {
            throw new BadRequestAlertException("A new preferredTopic cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PreferredTopic result = preferredTopicService.save(preferredTopic);
        return ResponseEntity
            .created(new URI("/api/preferred-topics/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId()))
            .body(result);
    }

    /**
     * {@code PUT  /preferred-topics/:id} : Updates an existing preferredTopic.
     *
     * @param id the id of the preferredTopic to save.
     * @param preferredTopic the preferredTopic to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated preferredTopic,
     * or with status {@code 400 (Bad Request)} if the preferredTopic is not valid,
     * or with status {@code 500 (Internal Server Error)} if the preferredTopic couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/preferred-topics/{id}")
    public ResponseEntity<PreferredTopic> updatePreferredTopic(
        @PathVariable(value = "id", required = false) final String id,
        @RequestBody PreferredTopic preferredTopic
    ) throws URISyntaxException {
        log.debug("REST request to update PreferredTopic : {}, {}", id, preferredTopic);
        if (preferredTopic.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, preferredTopic.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!preferredTopicRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        PreferredTopic result = preferredTopicService.update(preferredTopic);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, preferredTopic.getId()))
            .body(result);
    }

    /**
     * {@code PATCH  /preferred-topics/:id} : Partial updates given fields of an existing preferredTopic, field will ignore if it is null
     *
     * @param id the id of the preferredTopic to save.
     * @param preferredTopic the preferredTopic to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated preferredTopic,
     * or with status {@code 400 (Bad Request)} if the preferredTopic is not valid,
     * or with status {@code 404 (Not Found)} if the preferredTopic is not found,
     * or with status {@code 500 (Internal Server Error)} if the preferredTopic couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/preferred-topics/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<PreferredTopic> partialUpdatePreferredTopic(
        @PathVariable(value = "id", required = false) final String id,
        @RequestBody PreferredTopic preferredTopic
    ) throws URISyntaxException {
        log.debug("REST request to partial update PreferredTopic partially : {}, {}", id, preferredTopic);
        if (preferredTopic.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, preferredTopic.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!preferredTopicRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<PreferredTopic> result = preferredTopicService.partialUpdate(preferredTopic);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, preferredTopic.getId())
        );
    }

    /**
     * {@code GET  /preferred-topics} : get all the preferredTopics.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of preferredTopics in body.
     */
    @GetMapping("/preferred-topics")
    public List<PreferredTopic> getAllPreferredTopics() {
        log.debug("REST request to get all PreferredTopics");
        return preferredTopicService.findAll();
    }

    /**
     * {@code GET  /preferred-topics/:id} : get the "id" preferredTopic.
     *
     * @param id the id of the preferredTopic to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the preferredTopic, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/preferred-topics/{id}")
    public ResponseEntity<PreferredTopic> getPreferredTopic(@PathVariable String id) {
        log.debug("REST request to get PreferredTopic : {}", id);
        Optional<PreferredTopic> preferredTopic = preferredTopicService.findOne(id);
        return ResponseUtil.wrapOrNotFound(preferredTopic);
    }

    /**
     * {@code DELETE  /preferred-topics/:id} : delete the "id" preferredTopic.
     *
     * @param id the id of the preferredTopic to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/preferred-topics/{id}")
    public ResponseEntity<Void> deletePreferredTopic(@PathVariable String id) {
        log.debug("REST request to delete PreferredTopic : {}", id);
        preferredTopicService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id)).build();
    }
}
