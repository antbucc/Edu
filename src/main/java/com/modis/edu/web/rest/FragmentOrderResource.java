package com.modis.edu.web.rest;

import com.modis.edu.domain.FragmentOrder;
import com.modis.edu.repository.FragmentOrderRepository;
import com.modis.edu.service.FragmentOrderService;
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
 * REST controller for managing {@link com.modis.edu.domain.FragmentOrder}.
 */
@RestController
@RequestMapping("/api")
public class FragmentOrderResource {

    private final Logger log = LoggerFactory.getLogger(FragmentOrderResource.class);

    private static final String ENTITY_NAME = "fragmentOrder";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final FragmentOrderService fragmentOrderService;

    private final FragmentOrderRepository fragmentOrderRepository;

    public FragmentOrderResource(FragmentOrderService fragmentOrderService, FragmentOrderRepository fragmentOrderRepository) {
        this.fragmentOrderService = fragmentOrderService;
        this.fragmentOrderRepository = fragmentOrderRepository;
    }

    /**
     * {@code POST  /fragment-orders} : Create a new fragmentOrder.
     *
     * @param fragmentOrder the fragmentOrder to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new fragmentOrder, or with status {@code 400 (Bad Request)} if the fragmentOrder has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/fragment-orders")
    public ResponseEntity<FragmentOrder> createFragmentOrder(@Valid @RequestBody FragmentOrder fragmentOrder) throws URISyntaxException {
        log.debug("REST request to save FragmentOrder : {}", fragmentOrder);
        if (fragmentOrder.getId() != null) {
            throw new BadRequestAlertException("A new fragmentOrder cannot already have an ID", ENTITY_NAME, "idexists");
        }
        FragmentOrder result = fragmentOrderService.save(fragmentOrder);
        return ResponseEntity
            .created(new URI("/api/fragment-orders/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId()))
            .body(result);
    }

    /**
     * {@code PUT  /fragment-orders/:id} : Updates an existing fragmentOrder.
     *
     * @param id the id of the fragmentOrder to save.
     * @param fragmentOrder the fragmentOrder to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated fragmentOrder,
     * or with status {@code 400 (Bad Request)} if the fragmentOrder is not valid,
     * or with status {@code 500 (Internal Server Error)} if the fragmentOrder couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/fragment-orders/{id}")
    public ResponseEntity<FragmentOrder> updateFragmentOrder(
        @PathVariable(value = "id", required = false) final String id,
        @Valid @RequestBody FragmentOrder fragmentOrder
    ) throws URISyntaxException {
        log.debug("REST request to update FragmentOrder : {}, {}", id, fragmentOrder);
        if (fragmentOrder.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, fragmentOrder.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!fragmentOrderRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        FragmentOrder result = fragmentOrderService.update(fragmentOrder);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, fragmentOrder.getId()))
            .body(result);
    }

    /**
     * {@code PATCH  /fragment-orders/:id} : Partial updates given fields of an existing fragmentOrder, field will ignore if it is null
     *
     * @param id the id of the fragmentOrder to save.
     * @param fragmentOrder the fragmentOrder to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated fragmentOrder,
     * or with status {@code 400 (Bad Request)} if the fragmentOrder is not valid,
     * or with status {@code 404 (Not Found)} if the fragmentOrder is not found,
     * or with status {@code 500 (Internal Server Error)} if the fragmentOrder couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/fragment-orders/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<FragmentOrder> partialUpdateFragmentOrder(
        @PathVariable(value = "id", required = false) final String id,
        @NotNull @RequestBody FragmentOrder fragmentOrder
    ) throws URISyntaxException {
        log.debug("REST request to partial update FragmentOrder partially : {}, {}", id, fragmentOrder);
        if (fragmentOrder.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, fragmentOrder.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!fragmentOrderRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<FragmentOrder> result = fragmentOrderService.partialUpdate(fragmentOrder);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, fragmentOrder.getId())
        );
    }

    /**
     * {@code GET  /fragment-orders} : get all the fragmentOrders.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of fragmentOrders in body.
     */
    @GetMapping("/fragment-orders")
    public List<FragmentOrder> getAllFragmentOrders() {
        log.debug("REST request to get all FragmentOrders");
        return fragmentOrderService.findAll();
    }

    /**
     * {@code GET  /fragment-orders/:id} : get the "id" fragmentOrder.
     *
     * @param id the id of the fragmentOrder to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the fragmentOrder, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/fragment-orders/{id}")
    public ResponseEntity<FragmentOrder> getFragmentOrder(@PathVariable String id) {
        log.debug("REST request to get FragmentOrder : {}", id);
        Optional<FragmentOrder> fragmentOrder = fragmentOrderService.findOne(id);
        return ResponseUtil.wrapOrNotFound(fragmentOrder);
    }

    /**
     * {@code DELETE  /fragment-orders/:id} : delete the "id" fragmentOrder.
     *
     * @param id the id of the fragmentOrder to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/fragment-orders/{id}")
    public ResponseEntity<Void> deleteFragmentOrder(@PathVariable String id) {
        log.debug("REST request to delete FragmentOrder : {}", id);
        fragmentOrderService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id)).build();
    }
}
