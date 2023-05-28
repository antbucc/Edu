package com.modis.edu.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.modis.edu.IntegrationTest;
import com.modis.edu.domain.PreferredLearningStyle;
import com.modis.edu.domain.enumeration.LearningStyleType;
import com.modis.edu.repository.PreferredLearningStyleRepository;
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
 * Integration tests for the {@link PreferredLearningStyleResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class PreferredLearningStyleResourceIT {

    private static final LearningStyleType DEFAULT_STYLE = LearningStyleType.VISUAL;
    private static final LearningStyleType UPDATED_STYLE = LearningStyleType.AUDITORY;

    private static final String ENTITY_API_URL = "/api/preferred-learning-styles";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private PreferredLearningStyleRepository preferredLearningStyleRepository;

    @Autowired
    private MockMvc restPreferredLearningStyleMockMvc;

    private PreferredLearningStyle preferredLearningStyle;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PreferredLearningStyle createEntity() {
        PreferredLearningStyle preferredLearningStyle = new PreferredLearningStyle().style(DEFAULT_STYLE);
        return preferredLearningStyle;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PreferredLearningStyle createUpdatedEntity() {
        PreferredLearningStyle preferredLearningStyle = new PreferredLearningStyle().style(UPDATED_STYLE);
        return preferredLearningStyle;
    }

    @BeforeEach
    public void initTest() {
        preferredLearningStyleRepository.deleteAll();
        preferredLearningStyle = createEntity();
    }

    @Test
    void createPreferredLearningStyle() throws Exception {
        int databaseSizeBeforeCreate = preferredLearningStyleRepository.findAll().size();
        // Create the PreferredLearningStyle
        restPreferredLearningStyleMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(preferredLearningStyle))
            )
            .andExpect(status().isCreated());

        // Validate the PreferredLearningStyle in the database
        List<PreferredLearningStyle> preferredLearningStyleList = preferredLearningStyleRepository.findAll();
        assertThat(preferredLearningStyleList).hasSize(databaseSizeBeforeCreate + 1);
        PreferredLearningStyle testPreferredLearningStyle = preferredLearningStyleList.get(preferredLearningStyleList.size() - 1);
        assertThat(testPreferredLearningStyle.getStyle()).isEqualTo(DEFAULT_STYLE);
    }

    @Test
    void createPreferredLearningStyleWithExistingId() throws Exception {
        // Create the PreferredLearningStyle with an existing ID
        preferredLearningStyle.setId("existing_id");

        int databaseSizeBeforeCreate = preferredLearningStyleRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPreferredLearningStyleMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(preferredLearningStyle))
            )
            .andExpect(status().isBadRequest());

        // Validate the PreferredLearningStyle in the database
        List<PreferredLearningStyle> preferredLearningStyleList = preferredLearningStyleRepository.findAll();
        assertThat(preferredLearningStyleList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    void getAllPreferredLearningStyles() throws Exception {
        // Initialize the database
        preferredLearningStyleRepository.save(preferredLearningStyle);

        // Get all the preferredLearningStyleList
        restPreferredLearningStyleMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(preferredLearningStyle.getId())))
            .andExpect(jsonPath("$.[*].style").value(hasItem(DEFAULT_STYLE.toString())));
    }

    @Test
    void getPreferredLearningStyle() throws Exception {
        // Initialize the database
        preferredLearningStyleRepository.save(preferredLearningStyle);

        // Get the preferredLearningStyle
        restPreferredLearningStyleMockMvc
            .perform(get(ENTITY_API_URL_ID, preferredLearningStyle.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(preferredLearningStyle.getId()))
            .andExpect(jsonPath("$.style").value(DEFAULT_STYLE.toString()));
    }

    @Test
    void getNonExistingPreferredLearningStyle() throws Exception {
        // Get the preferredLearningStyle
        restPreferredLearningStyleMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    void putExistingPreferredLearningStyle() throws Exception {
        // Initialize the database
        preferredLearningStyleRepository.save(preferredLearningStyle);

        int databaseSizeBeforeUpdate = preferredLearningStyleRepository.findAll().size();

        // Update the preferredLearningStyle
        PreferredLearningStyle updatedPreferredLearningStyle = preferredLearningStyleRepository
            .findById(preferredLearningStyle.getId())
            .get();
        updatedPreferredLearningStyle.style(UPDATED_STYLE);

        restPreferredLearningStyleMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedPreferredLearningStyle.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedPreferredLearningStyle))
            )
            .andExpect(status().isOk());

        // Validate the PreferredLearningStyle in the database
        List<PreferredLearningStyle> preferredLearningStyleList = preferredLearningStyleRepository.findAll();
        assertThat(preferredLearningStyleList).hasSize(databaseSizeBeforeUpdate);
        PreferredLearningStyle testPreferredLearningStyle = preferredLearningStyleList.get(preferredLearningStyleList.size() - 1);
        assertThat(testPreferredLearningStyle.getStyle()).isEqualTo(UPDATED_STYLE);
    }

    @Test
    void putNonExistingPreferredLearningStyle() throws Exception {
        int databaseSizeBeforeUpdate = preferredLearningStyleRepository.findAll().size();
        preferredLearningStyle.setId(UUID.randomUUID().toString());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPreferredLearningStyleMockMvc
            .perform(
                put(ENTITY_API_URL_ID, preferredLearningStyle.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(preferredLearningStyle))
            )
            .andExpect(status().isBadRequest());

        // Validate the PreferredLearningStyle in the database
        List<PreferredLearningStyle> preferredLearningStyleList = preferredLearningStyleRepository.findAll();
        assertThat(preferredLearningStyleList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchPreferredLearningStyle() throws Exception {
        int databaseSizeBeforeUpdate = preferredLearningStyleRepository.findAll().size();
        preferredLearningStyle.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPreferredLearningStyleMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(preferredLearningStyle))
            )
            .andExpect(status().isBadRequest());

        // Validate the PreferredLearningStyle in the database
        List<PreferredLearningStyle> preferredLearningStyleList = preferredLearningStyleRepository.findAll();
        assertThat(preferredLearningStyleList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamPreferredLearningStyle() throws Exception {
        int databaseSizeBeforeUpdate = preferredLearningStyleRepository.findAll().size();
        preferredLearningStyle.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPreferredLearningStyleMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(preferredLearningStyle))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the PreferredLearningStyle in the database
        List<PreferredLearningStyle> preferredLearningStyleList = preferredLearningStyleRepository.findAll();
        assertThat(preferredLearningStyleList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdatePreferredLearningStyleWithPatch() throws Exception {
        // Initialize the database
        preferredLearningStyleRepository.save(preferredLearningStyle);

        int databaseSizeBeforeUpdate = preferredLearningStyleRepository.findAll().size();

        // Update the preferredLearningStyle using partial update
        PreferredLearningStyle partialUpdatedPreferredLearningStyle = new PreferredLearningStyle();
        partialUpdatedPreferredLearningStyle.setId(preferredLearningStyle.getId());

        restPreferredLearningStyleMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPreferredLearningStyle.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPreferredLearningStyle))
            )
            .andExpect(status().isOk());

        // Validate the PreferredLearningStyle in the database
        List<PreferredLearningStyle> preferredLearningStyleList = preferredLearningStyleRepository.findAll();
        assertThat(preferredLearningStyleList).hasSize(databaseSizeBeforeUpdate);
        PreferredLearningStyle testPreferredLearningStyle = preferredLearningStyleList.get(preferredLearningStyleList.size() - 1);
        assertThat(testPreferredLearningStyle.getStyle()).isEqualTo(DEFAULT_STYLE);
    }

    @Test
    void fullUpdatePreferredLearningStyleWithPatch() throws Exception {
        // Initialize the database
        preferredLearningStyleRepository.save(preferredLearningStyle);

        int databaseSizeBeforeUpdate = preferredLearningStyleRepository.findAll().size();

        // Update the preferredLearningStyle using partial update
        PreferredLearningStyle partialUpdatedPreferredLearningStyle = new PreferredLearningStyle();
        partialUpdatedPreferredLearningStyle.setId(preferredLearningStyle.getId());

        partialUpdatedPreferredLearningStyle.style(UPDATED_STYLE);

        restPreferredLearningStyleMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPreferredLearningStyle.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPreferredLearningStyle))
            )
            .andExpect(status().isOk());

        // Validate the PreferredLearningStyle in the database
        List<PreferredLearningStyle> preferredLearningStyleList = preferredLearningStyleRepository.findAll();
        assertThat(preferredLearningStyleList).hasSize(databaseSizeBeforeUpdate);
        PreferredLearningStyle testPreferredLearningStyle = preferredLearningStyleList.get(preferredLearningStyleList.size() - 1);
        assertThat(testPreferredLearningStyle.getStyle()).isEqualTo(UPDATED_STYLE);
    }

    @Test
    void patchNonExistingPreferredLearningStyle() throws Exception {
        int databaseSizeBeforeUpdate = preferredLearningStyleRepository.findAll().size();
        preferredLearningStyle.setId(UUID.randomUUID().toString());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPreferredLearningStyleMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, preferredLearningStyle.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(preferredLearningStyle))
            )
            .andExpect(status().isBadRequest());

        // Validate the PreferredLearningStyle in the database
        List<PreferredLearningStyle> preferredLearningStyleList = preferredLearningStyleRepository.findAll();
        assertThat(preferredLearningStyleList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchPreferredLearningStyle() throws Exception {
        int databaseSizeBeforeUpdate = preferredLearningStyleRepository.findAll().size();
        preferredLearningStyle.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPreferredLearningStyleMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(preferredLearningStyle))
            )
            .andExpect(status().isBadRequest());

        // Validate the PreferredLearningStyle in the database
        List<PreferredLearningStyle> preferredLearningStyleList = preferredLearningStyleRepository.findAll();
        assertThat(preferredLearningStyleList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamPreferredLearningStyle() throws Exception {
        int databaseSizeBeforeUpdate = preferredLearningStyleRepository.findAll().size();
        preferredLearningStyle.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPreferredLearningStyleMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(preferredLearningStyle))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the PreferredLearningStyle in the database
        List<PreferredLearningStyle> preferredLearningStyleList = preferredLearningStyleRepository.findAll();
        assertThat(preferredLearningStyleList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void deletePreferredLearningStyle() throws Exception {
        // Initialize the database
        preferredLearningStyleRepository.save(preferredLearningStyle);

        int databaseSizeBeforeDelete = preferredLearningStyleRepository.findAll().size();

        // Delete the preferredLearningStyle
        restPreferredLearningStyleMockMvc
            .perform(delete(ENTITY_API_URL_ID, preferredLearningStyle.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PreferredLearningStyle> preferredLearningStyleList = preferredLearningStyleRepository.findAll();
        assertThat(preferredLearningStyleList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
