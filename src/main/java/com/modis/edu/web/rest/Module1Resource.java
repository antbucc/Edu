package com.modis.edu.web.rest;

import com.modis.edu.domain.Module1;
import com.modis.edu.repository.Module1Repository;
import com.modis.edu.service.Module1Service;
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
 * REST controller for managing {@link com.modis.edu.domain.Module1}.
 */
@RestController
@RequestMapping("/api")
public class Module1Resource {

    private final Logger log = LoggerFactory.getLogger(Module1Resource.class);

    private static final String ENTITY_NAME = "module1";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final Module1Service module1Service;

    private final Module1Repository module1Repository;

    public Module1Resource(Module1Service module1Service, Module1Repository module1Repository) {
        this.module1Service = module1Service;
        this.module1Repository = module1Repository;
    }

    /**
     * {@code POST  /module-1-s} : Create a new module1.
     *
     * @param module1 the module1 to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new module1, or with status {@code 400 (Bad Request)} if the module1 has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/module-1-s")
    public ResponseEntity<Module1> createModule1(@RequestBody Module1 module1) throws URISyntaxException {
        log.debug("REST request to save Module1 : {}", module1);
        if (module1.getId() != null) {
            throw new BadRequestAlertException("A new module1 cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Module1 result = module1Service.save(module1);
        return ResponseEntity
            .created(new URI("/api/module-1-s/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId()))
            .body(result);
    }

    /**
     * {@code PUT  /module-1-s/:id} : Updates an existing module1.
     *
     * @param id the id of the module1 to save.
     * @param module1 the module1 to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated module1,
     * or with status {@code 400 (Bad Request)} if the module1 is not valid,
     * or with status {@code 500 (Internal Server Error)} if the module1 couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/module-1-s/{id}")
    public ResponseEntity<Module1> updateModule1(
        @PathVariable(value = "id", required = false) final String id,
        @RequestBody Module1 module1
    ) throws URISyntaxException {
        log.debug("REST request to update Module1 : {}, {}", id, module1);
        if (module1.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, module1.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!module1Repository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Module1 result = module1Service.update(module1);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, module1.getId()))
            .body(result);
    }

    /**
     * {@code PATCH  /module-1-s/:id} : Partial updates given fields of an existing module1, field will ignore if it is null
     *
     * @param id the id of the module1 to save.
     * @param module1 the module1 to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated module1,
     * or with status {@code 400 (Bad Request)} if the module1 is not valid,
     * or with status {@code 404 (Not Found)} if the module1 is not found,
     * or with status {@code 500 (Internal Server Error)} if the module1 couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/module-1-s/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Module1> partialUpdateModule1(
        @PathVariable(value = "id", required = false) final String id,
        @RequestBody Module1 module1
    ) throws URISyntaxException {
        log.debug("REST request to partial update Module1 partially : {}, {}", id, module1);
        if (module1.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, module1.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!module1Repository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Module1> result = module1Service.partialUpdate(module1);

        return ResponseUtil.wrapOrNotFound(result, HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, module1.getId()));
    }

    /**
     * {@code GET  /module-1-s} : get all the module1s.
     *
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of module1s in body.
     */
    @GetMapping("/module-1-s")
    public List<Module1> getAllModule1s(@RequestParam(required = false, defaultValue = "false") boolean eagerload) {
        log.debug("REST request to get all Module1s");
        return module1Service.findAll();
    }

    /**
     * {@code GET  /module-1-s/:id} : get the "id" module1.
     *
     * @param id the id of the module1 to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the module1, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/module-1-s/{id}")
    public ResponseEntity<Module1> getModule1(@PathVariable String id) {
        log.debug("REST request to get Module1 : {}", id);
        Optional<Module1> module1 = module1Service.findOne(id);
        return ResponseUtil.wrapOrNotFound(module1);
    }

    /**
     * {@code DELETE  /module-1-s/:id} : delete the "id" module1.
     *
     * @param id the id of the module1 to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/module-1-s/{id}")
    public ResponseEntity<Void> deleteModule1(@PathVariable String id) {
        log.debug("REST request to delete Module1 : {}", id);
        module1Service.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id)).build();
    }
}
