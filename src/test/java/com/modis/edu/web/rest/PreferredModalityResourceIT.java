package com.modis.edu.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.modis.edu.IntegrationTest;
import com.modis.edu.domain.PreferredModality;
import com.modis.edu.domain.enumeration.ModalityType;
import com.modis.edu.repository.PreferredModalityRepository;
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
 * Integration tests for the {@link PreferredModalityResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class PreferredModalityResourceIT {

    private static final ModalityType DEFAULT_MODALITY = ModalityType.ONLINE;
    private static final ModalityType UPDATED_MODALITY = ModalityType.IN_PERSON;

    private static final String ENTITY_API_URL = "/api/preferred-modalities";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private PreferredModalityRepository preferredModalityRepository;

    @Autowired
    private MockMvc restPreferredModalityMockMvc;

    private PreferredModality preferredModality;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PreferredModality createEntity() {
        PreferredModality preferredModality = new PreferredModality().modality(DEFAULT_MODALITY);
        return preferredModality;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PreferredModality createUpdatedEntity() {
        PreferredModality preferredModality = new PreferredModality().modality(UPDATED_MODALITY);
        return preferredModality;
    }

    @BeforeEach
    public void initTest() {
        preferredModalityRepository.deleteAll();
        preferredModality = createEntity();
    }

    @Test
    void createPreferredModality() throws Exception {
        int databaseSizeBeforeCreate = preferredModalityRepository.findAll().size();
        // Create the PreferredModality
        restPreferredModalityMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(preferredModality))
            )
            .andExpect(status().isCreated());

        // Validate the PreferredModality in the database
        List<PreferredModality> preferredModalityList = preferredModalityRepository.findAll();
        assertThat(preferredModalityList).hasSize(databaseSizeBeforeCreate + 1);
        PreferredModality testPreferredModality = preferredModalityList.get(preferredModalityList.size() - 1);
        assertThat(testPreferredModality.getModality()).isEqualTo(DEFAULT_MODALITY);
    }

    @Test
    void createPreferredModalityWithExistingId() throws Exception {
        // Create the PreferredModality with an existing ID
        preferredModality.setId("existing_id");

        int databaseSizeBeforeCreate = preferredModalityRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPreferredModalityMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(preferredModality))
            )
            .andExpect(status().isBadRequest());

        // Validate the PreferredModality in the database
        List<PreferredModality> preferredModalityList = preferredModalityRepository.findAll();
        assertThat(preferredModalityList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    void getAllPreferredModalities() throws Exception {
        // Initialize the database
        preferredModalityRepository.save(preferredModality);

        // Get all the preferredModalityList
        restPreferredModalityMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(preferredModality.getId())))
            .andExpect(jsonPath("$.[*].modality").value(hasItem(DEFAULT_MODALITY.toString())));
    }

    @Test
    void getPreferredModality() throws Exception {
        // Initialize the database
        preferredModalityRepository.save(preferredModality);

        // Get the preferredModality
        restPreferredModalityMockMvc
            .perform(get(ENTITY_API_URL_ID, preferredModality.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(preferredModality.getId()))
            .andExpect(jsonPath("$.modality").value(DEFAULT_MODALITY.toString()));
    }

    @Test
    void getNonExistingPreferredModality() throws Exception {
        // Get the preferredModality
        restPreferredModalityMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    void putExistingPreferredModality() throws Exception {
        // Initialize the database
        preferredModalityRepository.save(preferredModality);

        int databaseSizeBeforeUpdate = preferredModalityRepository.findAll().size();

        // Update the preferredModality
        PreferredModality updatedPreferredModality = preferredModalityRepository.findById(preferredModality.getId()).get();
        updatedPreferredModality.modality(UPDATED_MODALITY);

        restPreferredModalityMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedPreferredModality.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedPreferredModality))
            )
            .andExpect(status().isOk());

        // Validate the PreferredModality in the database
        List<PreferredModality> preferredModalityList = preferredModalityRepository.findAll();
        assertThat(preferredModalityList).hasSize(databaseSizeBeforeUpdate);
        PreferredModality testPreferredModality = preferredModalityList.get(preferredModalityList.size() - 1);
        assertThat(testPreferredModality.getModality()).isEqualTo(UPDATED_MODALITY);
    }

    @Test
    void putNonExistingPreferredModality() throws Exception {
        int databaseSizeBeforeUpdate = preferredModalityRepository.findAll().size();
        preferredModality.setId(UUID.randomUUID().toString());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPreferredModalityMockMvc
            .perform(
                put(ENTITY_API_URL_ID, preferredModality.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(preferredModality))
            )
            .andExpect(status().isBadRequest());

        // Validate the PreferredModality in the database
        List<PreferredModality> preferredModalityList = preferredModalityRepository.findAll();
        assertThat(preferredModalityList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchPreferredModality() throws Exception {
        int databaseSizeBeforeUpdate = preferredModalityRepository.findAll().size();
        preferredModality.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPreferredModalityMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(preferredModality))
            )
            .andExpect(status().isBadRequest());

        // Validate the PreferredModality in the database
        List<PreferredModality> preferredModalityList = preferredModalityRepository.findAll();
        assertThat(preferredModalityList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamPreferredModality() throws Exception {
        int databaseSizeBeforeUpdate = preferredModalityRepository.findAll().size();
        preferredModality.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPreferredModalityMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(preferredModality))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the PreferredModality in the database
        List<PreferredModality> preferredModalityList = preferredModalityRepository.findAll();
        assertThat(preferredModalityList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdatePreferredModalityWithPatch() throws Exception {
        // Initialize the database
        preferredModalityRepository.save(preferredModality);

        int databaseSizeBeforeUpdate = preferredModalityRepository.findAll().size();

        // Update the preferredModality using partial update
        PreferredModality partialUpdatedPreferredModality = new PreferredModality();
        partialUpdatedPreferredModality.setId(preferredModality.getId());

        partialUpdatedPreferredModality.modality(UPDATED_MODALITY);

        restPreferredModalityMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPreferredModality.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPreferredModality))
            )
            .andExpect(status().isOk());

        // Validate the PreferredModality in the database
        List<PreferredModality> preferredModalityList = preferredModalityRepository.findAll();
        assertThat(preferredModalityList).hasSize(databaseSizeBeforeUpdate);
        PreferredModality testPreferredModality = preferredModalityList.get(preferredModalityList.size() - 1);
        assertThat(testPreferredModality.getModality()).isEqualTo(UPDATED_MODALITY);
    }

    @Test
    void fullUpdatePreferredModalityWithPatch() throws Exception {
        // Initialize the database
        preferredModalityRepository.save(preferredModality);

        int databaseSizeBeforeUpdate = preferredModalityRepository.findAll().size();

        // Update the preferredModality using partial update
        PreferredModality partialUpdatedPreferredModality = new PreferredModality();
        partialUpdatedPreferredModality.setId(preferredModality.getId());

        partialUpdatedPreferredModality.modality(UPDATED_MODALITY);

        restPreferredModalityMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPreferredModality.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPreferredModality))
            )
            .andExpect(status().isOk());

        // Validate the PreferredModality in the database
        List<PreferredModality> preferredModalityList = preferredModalityRepository.findAll();
        assertThat(preferredModalityList).hasSize(databaseSizeBeforeUpdate);
        PreferredModality testPreferredModality = preferredModalityList.get(preferredModalityList.size() - 1);
        assertThat(testPreferredModality.getModality()).isEqualTo(UPDATED_MODALITY);
    }

    @Test
    void patchNonExistingPreferredModality() throws Exception {
        int databaseSizeBeforeUpdate = preferredModalityRepository.findAll().size();
        preferredModality.setId(UUID.randomUUID().toString());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPreferredModalityMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, preferredModality.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(preferredModality))
            )
            .andExpect(status().isBadRequest());

        // Validate the PreferredModality in the database
        List<PreferredModality> preferredModalityList = preferredModalityRepository.findAll();
        assertThat(preferredModalityList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchPreferredModality() throws Exception {
        int databaseSizeBeforeUpdate = preferredModalityRepository.findAll().size();
        preferredModality.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPreferredModalityMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(preferredModality))
            )
            .andExpect(status().isBadRequest());

        // Validate the PreferredModality in the database
        List<PreferredModality> preferredModalityList = preferredModalityRepository.findAll();
        assertThat(preferredModalityList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamPreferredModality() throws Exception {
        int databaseSizeBeforeUpdate = preferredModalityRepository.findAll().size();
        preferredModality.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPreferredModalityMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(preferredModality))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the PreferredModality in the database
        List<PreferredModality> preferredModalityList = preferredModalityRepository.findAll();
        assertThat(preferredModalityList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void deletePreferredModality() throws Exception {
        // Initialize the database
        preferredModalityRepository.save(preferredModality);

        int databaseSizeBeforeDelete = preferredModalityRepository.findAll().size();

        // Delete the preferredModality
        restPreferredModalityMockMvc
            .perform(delete(ENTITY_API_URL_ID, preferredModality.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PreferredModality> preferredModalityList = preferredModalityRepository.findAll();
        assertThat(preferredModalityList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
