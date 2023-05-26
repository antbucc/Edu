package com.modis.edu.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.modis.edu.IntegrationTest;
import com.modis.edu.domain.FragmentSet;
import com.modis.edu.repository.FragmentSetRepository;
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
 * Integration tests for the {@link FragmentSetResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class FragmentSetResourceIT {

    private static final String ENTITY_API_URL = "/api/fragment-sets";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private FragmentSetRepository fragmentSetRepository;

    @Autowired
    private MockMvc restFragmentSetMockMvc;

    private FragmentSet fragmentSet;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FragmentSet createEntity() {
        FragmentSet fragmentSet = new FragmentSet();
        return fragmentSet;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FragmentSet createUpdatedEntity() {
        FragmentSet fragmentSet = new FragmentSet();
        return fragmentSet;
    }

    @BeforeEach
    public void initTest() {
        fragmentSetRepository.deleteAll();
        fragmentSet = createEntity();
    }

    @Test
    void createFragmentSet() throws Exception {
        int databaseSizeBeforeCreate = fragmentSetRepository.findAll().size();
        // Create the FragmentSet
        restFragmentSetMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(fragmentSet)))
            .andExpect(status().isCreated());

        // Validate the FragmentSet in the database
        List<FragmentSet> fragmentSetList = fragmentSetRepository.findAll();
        assertThat(fragmentSetList).hasSize(databaseSizeBeforeCreate + 1);
        FragmentSet testFragmentSet = fragmentSetList.get(fragmentSetList.size() - 1);
    }

    @Test
    void createFragmentSetWithExistingId() throws Exception {
        // Create the FragmentSet with an existing ID
        fragmentSet.setId("existing_id");

        int databaseSizeBeforeCreate = fragmentSetRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restFragmentSetMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(fragmentSet)))
            .andExpect(status().isBadRequest());

        // Validate the FragmentSet in the database
        List<FragmentSet> fragmentSetList = fragmentSetRepository.findAll();
        assertThat(fragmentSetList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    void getAllFragmentSets() throws Exception {
        // Initialize the database
        fragmentSetRepository.save(fragmentSet);

        // Get all the fragmentSetList
        restFragmentSetMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(fragmentSet.getId())));
    }

    @Test
    void getFragmentSet() throws Exception {
        // Initialize the database
        fragmentSetRepository.save(fragmentSet);

        // Get the fragmentSet
        restFragmentSetMockMvc
            .perform(get(ENTITY_API_URL_ID, fragmentSet.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(fragmentSet.getId()));
    }

    @Test
    void getNonExistingFragmentSet() throws Exception {
        // Get the fragmentSet
        restFragmentSetMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    void putExistingFragmentSet() throws Exception {
        // Initialize the database
        fragmentSetRepository.save(fragmentSet);

        int databaseSizeBeforeUpdate = fragmentSetRepository.findAll().size();

        // Update the fragmentSet
        FragmentSet updatedFragmentSet = fragmentSetRepository.findById(fragmentSet.getId()).get();

        restFragmentSetMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedFragmentSet.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedFragmentSet))
            )
            .andExpect(status().isOk());

        // Validate the FragmentSet in the database
        List<FragmentSet> fragmentSetList = fragmentSetRepository.findAll();
        assertThat(fragmentSetList).hasSize(databaseSizeBeforeUpdate);
        FragmentSet testFragmentSet = fragmentSetList.get(fragmentSetList.size() - 1);
    }

    @Test
    void putNonExistingFragmentSet() throws Exception {
        int databaseSizeBeforeUpdate = fragmentSetRepository.findAll().size();
        fragmentSet.setId(UUID.randomUUID().toString());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFragmentSetMockMvc
            .perform(
                put(ENTITY_API_URL_ID, fragmentSet.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(fragmentSet))
            )
            .andExpect(status().isBadRequest());

        // Validate the FragmentSet in the database
        List<FragmentSet> fragmentSetList = fragmentSetRepository.findAll();
        assertThat(fragmentSetList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchFragmentSet() throws Exception {
        int databaseSizeBeforeUpdate = fragmentSetRepository.findAll().size();
        fragmentSet.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFragmentSetMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(fragmentSet))
            )
            .andExpect(status().isBadRequest());

        // Validate the FragmentSet in the database
        List<FragmentSet> fragmentSetList = fragmentSetRepository.findAll();
        assertThat(fragmentSetList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamFragmentSet() throws Exception {
        int databaseSizeBeforeUpdate = fragmentSetRepository.findAll().size();
        fragmentSet.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFragmentSetMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(fragmentSet)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the FragmentSet in the database
        List<FragmentSet> fragmentSetList = fragmentSetRepository.findAll();
        assertThat(fragmentSetList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdateFragmentSetWithPatch() throws Exception {
        // Initialize the database
        fragmentSetRepository.save(fragmentSet);

        int databaseSizeBeforeUpdate = fragmentSetRepository.findAll().size();

        // Update the fragmentSet using partial update
        FragmentSet partialUpdatedFragmentSet = new FragmentSet();
        partialUpdatedFragmentSet.setId(fragmentSet.getId());

        restFragmentSetMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFragmentSet.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedFragmentSet))
            )
            .andExpect(status().isOk());

        // Validate the FragmentSet in the database
        List<FragmentSet> fragmentSetList = fragmentSetRepository.findAll();
        assertThat(fragmentSetList).hasSize(databaseSizeBeforeUpdate);
        FragmentSet testFragmentSet = fragmentSetList.get(fragmentSetList.size() - 1);
    }

    @Test
    void fullUpdateFragmentSetWithPatch() throws Exception {
        // Initialize the database
        fragmentSetRepository.save(fragmentSet);

        int databaseSizeBeforeUpdate = fragmentSetRepository.findAll().size();

        // Update the fragmentSet using partial update
        FragmentSet partialUpdatedFragmentSet = new FragmentSet();
        partialUpdatedFragmentSet.setId(fragmentSet.getId());

        restFragmentSetMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFragmentSet.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedFragmentSet))
            )
            .andExpect(status().isOk());

        // Validate the FragmentSet in the database
        List<FragmentSet> fragmentSetList = fragmentSetRepository.findAll();
        assertThat(fragmentSetList).hasSize(databaseSizeBeforeUpdate);
        FragmentSet testFragmentSet = fragmentSetList.get(fragmentSetList.size() - 1);
    }

    @Test
    void patchNonExistingFragmentSet() throws Exception {
        int databaseSizeBeforeUpdate = fragmentSetRepository.findAll().size();
        fragmentSet.setId(UUID.randomUUID().toString());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFragmentSetMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, fragmentSet.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(fragmentSet))
            )
            .andExpect(status().isBadRequest());

        // Validate the FragmentSet in the database
        List<FragmentSet> fragmentSetList = fragmentSetRepository.findAll();
        assertThat(fragmentSetList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchFragmentSet() throws Exception {
        int databaseSizeBeforeUpdate = fragmentSetRepository.findAll().size();
        fragmentSet.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFragmentSetMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(fragmentSet))
            )
            .andExpect(status().isBadRequest());

        // Validate the FragmentSet in the database
        List<FragmentSet> fragmentSetList = fragmentSetRepository.findAll();
        assertThat(fragmentSetList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamFragmentSet() throws Exception {
        int databaseSizeBeforeUpdate = fragmentSetRepository.findAll().size();
        fragmentSet.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFragmentSetMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(fragmentSet))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the FragmentSet in the database
        List<FragmentSet> fragmentSetList = fragmentSetRepository.findAll();
        assertThat(fragmentSetList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void deleteFragmentSet() throws Exception {
        // Initialize the database
        fragmentSetRepository.save(fragmentSet);

        int databaseSizeBeforeDelete = fragmentSetRepository.findAll().size();

        // Delete the fragmentSet
        restFragmentSetMockMvc
            .perform(delete(ENTITY_API_URL_ID, fragmentSet.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<FragmentSet> fragmentSetList = fragmentSetRepository.findAll();
        assertThat(fragmentSetList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
