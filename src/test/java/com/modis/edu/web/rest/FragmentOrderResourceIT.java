package com.modis.edu.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.modis.edu.IntegrationTest;
import com.modis.edu.domain.FragmentOrder;
import com.modis.edu.repository.FragmentOrderRepository;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

/**
 * Integration tests for the {@link FragmentOrderResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class FragmentOrderResourceIT {

    private static final Integer DEFAULT_ORDER = 1;
    private static final Integer UPDATED_ORDER = 2;

    private static final String ENTITY_API_URL = "/api/fragment-orders";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private FragmentOrderRepository fragmentOrderRepository;

    @Autowired
    private MockMvc restFragmentOrderMockMvc;

    private FragmentOrder fragmentOrder;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FragmentOrder createEntity() {
        FragmentOrder fragmentOrder = new FragmentOrder().order(DEFAULT_ORDER);
        return fragmentOrder;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FragmentOrder createUpdatedEntity() {
        FragmentOrder fragmentOrder = new FragmentOrder().order(UPDATED_ORDER);
        return fragmentOrder;
    }

    @BeforeEach
    public void initTest() {
        fragmentOrderRepository.deleteAll();
        fragmentOrder = createEntity();
    }

    @Test
    void createFragmentOrder() throws Exception {
        int databaseSizeBeforeCreate = fragmentOrderRepository.findAll().size();
        // Create the FragmentOrder
        restFragmentOrderMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(fragmentOrder)))
            .andExpect(status().isCreated());

        // Validate the FragmentOrder in the database
        List<FragmentOrder> fragmentOrderList = fragmentOrderRepository.findAll();
        assertThat(fragmentOrderList).hasSize(databaseSizeBeforeCreate + 1);
        FragmentOrder testFragmentOrder = fragmentOrderList.get(fragmentOrderList.size() - 1);
        assertThat(testFragmentOrder.getOrder()).isEqualTo(DEFAULT_ORDER);
    }

    @Test
    void createFragmentOrderWithExistingId() throws Exception {
        // Create the FragmentOrder with an existing ID
        fragmentOrder.setId("existing_id");

        int databaseSizeBeforeCreate = fragmentOrderRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restFragmentOrderMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(fragmentOrder)))
            .andExpect(status().isBadRequest());

        // Validate the FragmentOrder in the database
        List<FragmentOrder> fragmentOrderList = fragmentOrderRepository.findAll();
        assertThat(fragmentOrderList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    void checkOrderIsRequired() throws Exception {
        int databaseSizeBeforeTest = fragmentOrderRepository.findAll().size();
        // set the field null
        fragmentOrder.setOrder(null);

        // Create the FragmentOrder, which fails.

        restFragmentOrderMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(fragmentOrder)))
            .andExpect(status().isBadRequest());

        List<FragmentOrder> fragmentOrderList = fragmentOrderRepository.findAll();
        assertThat(fragmentOrderList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void getAllFragmentOrders() throws Exception {
        // Initialize the database
        fragmentOrderRepository.save(fragmentOrder);

        // Get all the fragmentOrderList
        restFragmentOrderMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(fragmentOrder.getId())))
            .andExpect(jsonPath("$.[*].order").value(hasItem(DEFAULT_ORDER)));
    }

    @Test
    void getFragmentOrder() throws Exception {
        // Initialize the database
        fragmentOrderRepository.save(fragmentOrder);

        // Get the fragmentOrder
        restFragmentOrderMockMvc
            .perform(get(ENTITY_API_URL_ID, fragmentOrder.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(fragmentOrder.getId()))
            .andExpect(jsonPath("$.order").value(DEFAULT_ORDER));
    }

    @Test
    void getNonExistingFragmentOrder() throws Exception {
        // Get the fragmentOrder
        restFragmentOrderMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    void putExistingFragmentOrder() throws Exception {
        // Initialize the database
        fragmentOrderRepository.save(fragmentOrder);

        int databaseSizeBeforeUpdate = fragmentOrderRepository.findAll().size();

        // Update the fragmentOrder
        FragmentOrder updatedFragmentOrder = fragmentOrderRepository.findById(fragmentOrder.getId()).get();
        updatedFragmentOrder.order(UPDATED_ORDER);

        restFragmentOrderMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedFragmentOrder.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedFragmentOrder))
            )
            .andExpect(status().isOk());

        // Validate the FragmentOrder in the database
        List<FragmentOrder> fragmentOrderList = fragmentOrderRepository.findAll();
        assertThat(fragmentOrderList).hasSize(databaseSizeBeforeUpdate);
        FragmentOrder testFragmentOrder = fragmentOrderList.get(fragmentOrderList.size() - 1);
        assertThat(testFragmentOrder.getOrder()).isEqualTo(UPDATED_ORDER);
    }

    @Test
    void putNonExistingFragmentOrder() throws Exception {
        int databaseSizeBeforeUpdate = fragmentOrderRepository.findAll().size();
        fragmentOrder.setId(UUID.randomUUID().toString());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFragmentOrderMockMvc
            .perform(
                put(ENTITY_API_URL_ID, fragmentOrder.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(fragmentOrder))
            )
            .andExpect(status().isBadRequest());

        // Validate the FragmentOrder in the database
        List<FragmentOrder> fragmentOrderList = fragmentOrderRepository.findAll();
        assertThat(fragmentOrderList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchFragmentOrder() throws Exception {
        int databaseSizeBeforeUpdate = fragmentOrderRepository.findAll().size();
        fragmentOrder.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFragmentOrderMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(fragmentOrder))
            )
            .andExpect(status().isBadRequest());

        // Validate the FragmentOrder in the database
        List<FragmentOrder> fragmentOrderList = fragmentOrderRepository.findAll();
        assertThat(fragmentOrderList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamFragmentOrder() throws Exception {
        int databaseSizeBeforeUpdate = fragmentOrderRepository.findAll().size();
        fragmentOrder.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFragmentOrderMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(fragmentOrder)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the FragmentOrder in the database
        List<FragmentOrder> fragmentOrderList = fragmentOrderRepository.findAll();
        assertThat(fragmentOrderList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdateFragmentOrderWithPatch() throws Exception {
        // Initialize the database
        fragmentOrderRepository.save(fragmentOrder);

        int databaseSizeBeforeUpdate = fragmentOrderRepository.findAll().size();

        // Update the fragmentOrder using partial update
        FragmentOrder partialUpdatedFragmentOrder = new FragmentOrder();
        partialUpdatedFragmentOrder.setId(fragmentOrder.getId());

        restFragmentOrderMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFragmentOrder.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedFragmentOrder))
            )
            .andExpect(status().isOk());

        // Validate the FragmentOrder in the database
        List<FragmentOrder> fragmentOrderList = fragmentOrderRepository.findAll();
        assertThat(fragmentOrderList).hasSize(databaseSizeBeforeUpdate);
        FragmentOrder testFragmentOrder = fragmentOrderList.get(fragmentOrderList.size() - 1);
        assertThat(testFragmentOrder.getOrder()).isEqualTo(DEFAULT_ORDER);
    }

    @Test
    void fullUpdateFragmentOrderWithPatch() throws Exception {
        // Initialize the database
        fragmentOrderRepository.save(fragmentOrder);

        int databaseSizeBeforeUpdate = fragmentOrderRepository.findAll().size();

        // Update the fragmentOrder using partial update
        FragmentOrder partialUpdatedFragmentOrder = new FragmentOrder();
        partialUpdatedFragmentOrder.setId(fragmentOrder.getId());

        partialUpdatedFragmentOrder.order(UPDATED_ORDER);

        restFragmentOrderMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFragmentOrder.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedFragmentOrder))
            )
            .andExpect(status().isOk());

        // Validate the FragmentOrder in the database
        List<FragmentOrder> fragmentOrderList = fragmentOrderRepository.findAll();
        assertThat(fragmentOrderList).hasSize(databaseSizeBeforeUpdate);
        FragmentOrder testFragmentOrder = fragmentOrderList.get(fragmentOrderList.size() - 1);
        assertThat(testFragmentOrder.getOrder()).isEqualTo(UPDATED_ORDER);
    }

    @Test
    void patchNonExistingFragmentOrder() throws Exception {
        int databaseSizeBeforeUpdate = fragmentOrderRepository.findAll().size();
        fragmentOrder.setId(UUID.randomUUID().toString());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFragmentOrderMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, fragmentOrder.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(fragmentOrder))
            )
            .andExpect(status().isBadRequest());

        // Validate the FragmentOrder in the database
        List<FragmentOrder> fragmentOrderList = fragmentOrderRepository.findAll();
        assertThat(fragmentOrderList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchFragmentOrder() throws Exception {
        int databaseSizeBeforeUpdate = fragmentOrderRepository.findAll().size();
        fragmentOrder.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFragmentOrderMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(fragmentOrder))
            )
            .andExpect(status().isBadRequest());

        // Validate the FragmentOrder in the database
        List<FragmentOrder> fragmentOrderList = fragmentOrderRepository.findAll();
        assertThat(fragmentOrderList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamFragmentOrder() throws Exception {
        int databaseSizeBeforeUpdate = fragmentOrderRepository.findAll().size();
        fragmentOrder.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFragmentOrderMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(fragmentOrder))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the FragmentOrder in the database
        List<FragmentOrder> fragmentOrderList = fragmentOrderRepository.findAll();
        assertThat(fragmentOrderList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void deleteFragmentOrder() throws Exception {
        // Initialize the database
        fragmentOrderRepository.save(fragmentOrder);

        int databaseSizeBeforeDelete = fragmentOrderRepository.findAll().size();

        // Delete the fragmentOrder
        restFragmentOrderMockMvc
            .perform(delete(ENTITY_API_URL_ID, fragmentOrder.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<FragmentOrder> fragmentOrderList = fragmentOrderRepository.findAll();
        assertThat(fragmentOrderList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
