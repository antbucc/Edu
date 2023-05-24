package com.modis.edu.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.modis.edu.IntegrationTest;
import com.modis.edu.domain.Set;
import com.modis.edu.repository.SetRepository;
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
 * Integration tests for the {@link SetResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class SetResourceIT {

    private static final String DEFAULT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_TITLE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/sets";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private SetRepository setRepository;

    @Autowired
    private MockMvc restSetMockMvc;

    private Set set;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Set createEntity() {
        Set set = new Set().title(DEFAULT_TITLE);
        return set;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Set createUpdatedEntity() {
        Set set = new Set().title(UPDATED_TITLE);
        return set;
    }

    @BeforeEach
    public void initTest() {
        setRepository.deleteAll();
        set = createEntity();
    }

    @Test
    void createSet() throws Exception {
        int databaseSizeBeforeCreate = setRepository.findAll().size();
        // Create the Set
        restSetMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(set)))
            .andExpect(status().isCreated());

        // Validate the Set in the database
        List<Set> setList = setRepository.findAll();
        assertThat(setList).hasSize(databaseSizeBeforeCreate + 1);
        Set testSet = setList.get(setList.size() - 1);
        assertThat(testSet.getTitle()).isEqualTo(DEFAULT_TITLE);
    }

    @Test
    void createSetWithExistingId() throws Exception {
        // Create the Set with an existing ID
        set.setId("existing_id");

        int databaseSizeBeforeCreate = setRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restSetMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(set)))
            .andExpect(status().isBadRequest());

        // Validate the Set in the database
        List<Set> setList = setRepository.findAll();
        assertThat(setList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    void getAllSets() throws Exception {
        // Initialize the database
        setRepository.save(set);

        // Get all the setList
        restSetMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(set.getId())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE)));
    }

    @Test
    void getSet() throws Exception {
        // Initialize the database
        setRepository.save(set);

        // Get the set
        restSetMockMvc
            .perform(get(ENTITY_API_URL_ID, set.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(set.getId()))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE));
    }

    @Test
    void getNonExistingSet() throws Exception {
        // Get the set
        restSetMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    void putExistingSet() throws Exception {
        // Initialize the database
        setRepository.save(set);

        int databaseSizeBeforeUpdate = setRepository.findAll().size();

        // Update the set
        Set updatedSet = setRepository.findById(set.getId()).get();
        updatedSet.title(UPDATED_TITLE);

        restSetMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedSet.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedSet))
            )
            .andExpect(status().isOk());

        // Validate the Set in the database
        List<Set> setList = setRepository.findAll();
        assertThat(setList).hasSize(databaseSizeBeforeUpdate);
        Set testSet = setList.get(setList.size() - 1);
        assertThat(testSet.getTitle()).isEqualTo(UPDATED_TITLE);
    }

    @Test
    void putNonExistingSet() throws Exception {
        int databaseSizeBeforeUpdate = setRepository.findAll().size();
        set.setId(UUID.randomUUID().toString());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSetMockMvc
            .perform(
                put(ENTITY_API_URL_ID, set.getId()).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(set))
            )
            .andExpect(status().isBadRequest());

        // Validate the Set in the database
        List<Set> setList = setRepository.findAll();
        assertThat(setList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchSet() throws Exception {
        int databaseSizeBeforeUpdate = setRepository.findAll().size();
        set.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSetMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(set))
            )
            .andExpect(status().isBadRequest());

        // Validate the Set in the database
        List<Set> setList = setRepository.findAll();
        assertThat(setList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamSet() throws Exception {
        int databaseSizeBeforeUpdate = setRepository.findAll().size();
        set.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSetMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(set)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Set in the database
        List<Set> setList = setRepository.findAll();
        assertThat(setList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdateSetWithPatch() throws Exception {
        // Initialize the database
        setRepository.save(set);

        int databaseSizeBeforeUpdate = setRepository.findAll().size();

        // Update the set using partial update
        Set partialUpdatedSet = new Set();
        partialUpdatedSet.setId(set.getId());

        restSetMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSet.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedSet))
            )
            .andExpect(status().isOk());

        // Validate the Set in the database
        List<Set> setList = setRepository.findAll();
        assertThat(setList).hasSize(databaseSizeBeforeUpdate);
        Set testSet = setList.get(setList.size() - 1);
        assertThat(testSet.getTitle()).isEqualTo(DEFAULT_TITLE);
    }

    @Test
    void fullUpdateSetWithPatch() throws Exception {
        // Initialize the database
        setRepository.save(set);

        int databaseSizeBeforeUpdate = setRepository.findAll().size();

        // Update the set using partial update
        Set partialUpdatedSet = new Set();
        partialUpdatedSet.setId(set.getId());

        partialUpdatedSet.title(UPDATED_TITLE);

        restSetMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSet.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedSet))
            )
            .andExpect(status().isOk());

        // Validate the Set in the database
        List<Set> setList = setRepository.findAll();
        assertThat(setList).hasSize(databaseSizeBeforeUpdate);
        Set testSet = setList.get(setList.size() - 1);
        assertThat(testSet.getTitle()).isEqualTo(UPDATED_TITLE);
    }

    @Test
    void patchNonExistingSet() throws Exception {
        int databaseSizeBeforeUpdate = setRepository.findAll().size();
        set.setId(UUID.randomUUID().toString());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSetMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, set.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(set))
            )
            .andExpect(status().isBadRequest());

        // Validate the Set in the database
        List<Set> setList = setRepository.findAll();
        assertThat(setList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchSet() throws Exception {
        int databaseSizeBeforeUpdate = setRepository.findAll().size();
        set.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSetMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(set))
            )
            .andExpect(status().isBadRequest());

        // Validate the Set in the database
        List<Set> setList = setRepository.findAll();
        assertThat(setList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamSet() throws Exception {
        int databaseSizeBeforeUpdate = setRepository.findAll().size();
        set.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSetMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(set)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Set in the database
        List<Set> setList = setRepository.findAll();
        assertThat(setList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void deleteSet() throws Exception {
        // Initialize the database
        setRepository.save(set);

        int databaseSizeBeforeDelete = setRepository.findAll().size();

        // Delete the set
        restSetMockMvc.perform(delete(ENTITY_API_URL_ID, set.getId()).accept(MediaType.APPLICATION_JSON)).andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Set> setList = setRepository.findAll();
        assertThat(setList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
