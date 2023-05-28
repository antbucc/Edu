package com.modis.edu.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.modis.edu.IntegrationTest;
import com.modis.edu.domain.EducatorPreference;
import com.modis.edu.domain.enumeration.Difficulty;
import com.modis.edu.repository.EducatorPreferenceRepository;
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
 * Integration tests for the {@link EducatorPreferenceResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class EducatorPreferenceResourceIT {

    private static final String DEFAULT_SUBJECT = "AAAAAAAAAA";
    private static final String UPDATED_SUBJECT = "BBBBBBBBBB";

    private static final Difficulty DEFAULT_DIFFICULTY = Difficulty.LOW;
    private static final Difficulty UPDATED_DIFFICULTY = Difficulty.MODERATE;

    private static final String ENTITY_API_URL = "/api/educator-preferences";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private EducatorPreferenceRepository educatorPreferenceRepository;

    @Autowired
    private MockMvc restEducatorPreferenceMockMvc;

    private EducatorPreference educatorPreference;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EducatorPreference createEntity() {
        EducatorPreference educatorPreference = new EducatorPreference().subject(DEFAULT_SUBJECT).difficulty(DEFAULT_DIFFICULTY);
        return educatorPreference;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EducatorPreference createUpdatedEntity() {
        EducatorPreference educatorPreference = new EducatorPreference().subject(UPDATED_SUBJECT).difficulty(UPDATED_DIFFICULTY);
        return educatorPreference;
    }

    @BeforeEach
    public void initTest() {
        educatorPreferenceRepository.deleteAll();
        educatorPreference = createEntity();
    }

    @Test
    void createEducatorPreference() throws Exception {
        int databaseSizeBeforeCreate = educatorPreferenceRepository.findAll().size();
        // Create the EducatorPreference
        restEducatorPreferenceMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(educatorPreference))
            )
            .andExpect(status().isCreated());

        // Validate the EducatorPreference in the database
        List<EducatorPreference> educatorPreferenceList = educatorPreferenceRepository.findAll();
        assertThat(educatorPreferenceList).hasSize(databaseSizeBeforeCreate + 1);
        EducatorPreference testEducatorPreference = educatorPreferenceList.get(educatorPreferenceList.size() - 1);
        assertThat(testEducatorPreference.getSubject()).isEqualTo(DEFAULT_SUBJECT);
        assertThat(testEducatorPreference.getDifficulty()).isEqualTo(DEFAULT_DIFFICULTY);
    }

    @Test
    void createEducatorPreferenceWithExistingId() throws Exception {
        // Create the EducatorPreference with an existing ID
        educatorPreference.setId("existing_id");

        int databaseSizeBeforeCreate = educatorPreferenceRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restEducatorPreferenceMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(educatorPreference))
            )
            .andExpect(status().isBadRequest());

        // Validate the EducatorPreference in the database
        List<EducatorPreference> educatorPreferenceList = educatorPreferenceRepository.findAll();
        assertThat(educatorPreferenceList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    void getAllEducatorPreferences() throws Exception {
        // Initialize the database
        educatorPreferenceRepository.save(educatorPreference);

        // Get all the educatorPreferenceList
        restEducatorPreferenceMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(educatorPreference.getId())))
            .andExpect(jsonPath("$.[*].subject").value(hasItem(DEFAULT_SUBJECT)))
            .andExpect(jsonPath("$.[*].difficulty").value(hasItem(DEFAULT_DIFFICULTY.toString())));
    }

    @Test
    void getEducatorPreference() throws Exception {
        // Initialize the database
        educatorPreferenceRepository.save(educatorPreference);

        // Get the educatorPreference
        restEducatorPreferenceMockMvc
            .perform(get(ENTITY_API_URL_ID, educatorPreference.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(educatorPreference.getId()))
            .andExpect(jsonPath("$.subject").value(DEFAULT_SUBJECT))
            .andExpect(jsonPath("$.difficulty").value(DEFAULT_DIFFICULTY.toString()));
    }

    @Test
    void getNonExistingEducatorPreference() throws Exception {
        // Get the educatorPreference
        restEducatorPreferenceMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    void putExistingEducatorPreference() throws Exception {
        // Initialize the database
        educatorPreferenceRepository.save(educatorPreference);

        int databaseSizeBeforeUpdate = educatorPreferenceRepository.findAll().size();

        // Update the educatorPreference
        EducatorPreference updatedEducatorPreference = educatorPreferenceRepository.findById(educatorPreference.getId()).get();
        updatedEducatorPreference.subject(UPDATED_SUBJECT).difficulty(UPDATED_DIFFICULTY);

        restEducatorPreferenceMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedEducatorPreference.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedEducatorPreference))
            )
            .andExpect(status().isOk());

        // Validate the EducatorPreference in the database
        List<EducatorPreference> educatorPreferenceList = educatorPreferenceRepository.findAll();
        assertThat(educatorPreferenceList).hasSize(databaseSizeBeforeUpdate);
        EducatorPreference testEducatorPreference = educatorPreferenceList.get(educatorPreferenceList.size() - 1);
        assertThat(testEducatorPreference.getSubject()).isEqualTo(UPDATED_SUBJECT);
        assertThat(testEducatorPreference.getDifficulty()).isEqualTo(UPDATED_DIFFICULTY);
    }

    @Test
    void putNonExistingEducatorPreference() throws Exception {
        int databaseSizeBeforeUpdate = educatorPreferenceRepository.findAll().size();
        educatorPreference.setId(UUID.randomUUID().toString());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEducatorPreferenceMockMvc
            .perform(
                put(ENTITY_API_URL_ID, educatorPreference.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(educatorPreference))
            )
            .andExpect(status().isBadRequest());

        // Validate the EducatorPreference in the database
        List<EducatorPreference> educatorPreferenceList = educatorPreferenceRepository.findAll();
        assertThat(educatorPreferenceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchEducatorPreference() throws Exception {
        int databaseSizeBeforeUpdate = educatorPreferenceRepository.findAll().size();
        educatorPreference.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEducatorPreferenceMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(educatorPreference))
            )
            .andExpect(status().isBadRequest());

        // Validate the EducatorPreference in the database
        List<EducatorPreference> educatorPreferenceList = educatorPreferenceRepository.findAll();
        assertThat(educatorPreferenceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamEducatorPreference() throws Exception {
        int databaseSizeBeforeUpdate = educatorPreferenceRepository.findAll().size();
        educatorPreference.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEducatorPreferenceMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(educatorPreference))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the EducatorPreference in the database
        List<EducatorPreference> educatorPreferenceList = educatorPreferenceRepository.findAll();
        assertThat(educatorPreferenceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdateEducatorPreferenceWithPatch() throws Exception {
        // Initialize the database
        educatorPreferenceRepository.save(educatorPreference);

        int databaseSizeBeforeUpdate = educatorPreferenceRepository.findAll().size();

        // Update the educatorPreference using partial update
        EducatorPreference partialUpdatedEducatorPreference = new EducatorPreference();
        partialUpdatedEducatorPreference.setId(educatorPreference.getId());

        restEducatorPreferenceMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEducatorPreference.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedEducatorPreference))
            )
            .andExpect(status().isOk());

        // Validate the EducatorPreference in the database
        List<EducatorPreference> educatorPreferenceList = educatorPreferenceRepository.findAll();
        assertThat(educatorPreferenceList).hasSize(databaseSizeBeforeUpdate);
        EducatorPreference testEducatorPreference = educatorPreferenceList.get(educatorPreferenceList.size() - 1);
        assertThat(testEducatorPreference.getSubject()).isEqualTo(DEFAULT_SUBJECT);
        assertThat(testEducatorPreference.getDifficulty()).isEqualTo(DEFAULT_DIFFICULTY);
    }

    @Test
    void fullUpdateEducatorPreferenceWithPatch() throws Exception {
        // Initialize the database
        educatorPreferenceRepository.save(educatorPreference);

        int databaseSizeBeforeUpdate = educatorPreferenceRepository.findAll().size();

        // Update the educatorPreference using partial update
        EducatorPreference partialUpdatedEducatorPreference = new EducatorPreference();
        partialUpdatedEducatorPreference.setId(educatorPreference.getId());

        partialUpdatedEducatorPreference.subject(UPDATED_SUBJECT).difficulty(UPDATED_DIFFICULTY);

        restEducatorPreferenceMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEducatorPreference.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedEducatorPreference))
            )
            .andExpect(status().isOk());

        // Validate the EducatorPreference in the database
        List<EducatorPreference> educatorPreferenceList = educatorPreferenceRepository.findAll();
        assertThat(educatorPreferenceList).hasSize(databaseSizeBeforeUpdate);
        EducatorPreference testEducatorPreference = educatorPreferenceList.get(educatorPreferenceList.size() - 1);
        assertThat(testEducatorPreference.getSubject()).isEqualTo(UPDATED_SUBJECT);
        assertThat(testEducatorPreference.getDifficulty()).isEqualTo(UPDATED_DIFFICULTY);
    }

    @Test
    void patchNonExistingEducatorPreference() throws Exception {
        int databaseSizeBeforeUpdate = educatorPreferenceRepository.findAll().size();
        educatorPreference.setId(UUID.randomUUID().toString());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEducatorPreferenceMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, educatorPreference.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(educatorPreference))
            )
            .andExpect(status().isBadRequest());

        // Validate the EducatorPreference in the database
        List<EducatorPreference> educatorPreferenceList = educatorPreferenceRepository.findAll();
        assertThat(educatorPreferenceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchEducatorPreference() throws Exception {
        int databaseSizeBeforeUpdate = educatorPreferenceRepository.findAll().size();
        educatorPreference.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEducatorPreferenceMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(educatorPreference))
            )
            .andExpect(status().isBadRequest());

        // Validate the EducatorPreference in the database
        List<EducatorPreference> educatorPreferenceList = educatorPreferenceRepository.findAll();
        assertThat(educatorPreferenceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamEducatorPreference() throws Exception {
        int databaseSizeBeforeUpdate = educatorPreferenceRepository.findAll().size();
        educatorPreference.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEducatorPreferenceMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(educatorPreference))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the EducatorPreference in the database
        List<EducatorPreference> educatorPreferenceList = educatorPreferenceRepository.findAll();
        assertThat(educatorPreferenceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void deleteEducatorPreference() throws Exception {
        // Initialize the database
        educatorPreferenceRepository.save(educatorPreference);

        int databaseSizeBeforeDelete = educatorPreferenceRepository.findAll().size();

        // Delete the educatorPreference
        restEducatorPreferenceMockMvc
            .perform(delete(ENTITY_API_URL_ID, educatorPreference.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<EducatorPreference> educatorPreferenceList = educatorPreferenceRepository.findAll();
        assertThat(educatorPreferenceList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
