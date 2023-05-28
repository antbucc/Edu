package com.modis.edu.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.modis.edu.IntegrationTest;
import com.modis.edu.domain.LearningDisability;
import com.modis.edu.domain.enumeration.DisabilityType;
import com.modis.edu.repository.LearningDisabilityRepository;
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
 * Integration tests for the {@link LearningDisabilityResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class LearningDisabilityResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final DisabilityType DEFAULT_DISABILITY_TYPE = DisabilityType.DYSLEXIA;
    private static final DisabilityType UPDATED_DISABILITY_TYPE = DisabilityType.DYSCALCULIA;

    private static final String ENTITY_API_URL = "/api/learning-disabilities";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private LearningDisabilityRepository learningDisabilityRepository;

    @Autowired
    private MockMvc restLearningDisabilityMockMvc;

    private LearningDisability learningDisability;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static LearningDisability createEntity() {
        LearningDisability learningDisability = new LearningDisability()
            .name(DEFAULT_NAME)
            .description(DEFAULT_DESCRIPTION)
            .disabilityType(DEFAULT_DISABILITY_TYPE);
        return learningDisability;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static LearningDisability createUpdatedEntity() {
        LearningDisability learningDisability = new LearningDisability()
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .disabilityType(UPDATED_DISABILITY_TYPE);
        return learningDisability;
    }

    @BeforeEach
    public void initTest() {
        learningDisabilityRepository.deleteAll();
        learningDisability = createEntity();
    }

    @Test
    void createLearningDisability() throws Exception {
        int databaseSizeBeforeCreate = learningDisabilityRepository.findAll().size();
        // Create the LearningDisability
        restLearningDisabilityMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(learningDisability))
            )
            .andExpect(status().isCreated());

        // Validate the LearningDisability in the database
        List<LearningDisability> learningDisabilityList = learningDisabilityRepository.findAll();
        assertThat(learningDisabilityList).hasSize(databaseSizeBeforeCreate + 1);
        LearningDisability testLearningDisability = learningDisabilityList.get(learningDisabilityList.size() - 1);
        assertThat(testLearningDisability.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testLearningDisability.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testLearningDisability.getDisabilityType()).isEqualTo(DEFAULT_DISABILITY_TYPE);
    }

    @Test
    void createLearningDisabilityWithExistingId() throws Exception {
        // Create the LearningDisability with an existing ID
        learningDisability.setId("existing_id");

        int databaseSizeBeforeCreate = learningDisabilityRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restLearningDisabilityMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(learningDisability))
            )
            .andExpect(status().isBadRequest());

        // Validate the LearningDisability in the database
        List<LearningDisability> learningDisabilityList = learningDisabilityRepository.findAll();
        assertThat(learningDisabilityList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    void getAllLearningDisabilities() throws Exception {
        // Initialize the database
        learningDisabilityRepository.save(learningDisability);

        // Get all the learningDisabilityList
        restLearningDisabilityMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(learningDisability.getId())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].disabilityType").value(hasItem(DEFAULT_DISABILITY_TYPE.toString())));
    }

    @Test
    void getLearningDisability() throws Exception {
        // Initialize the database
        learningDisabilityRepository.save(learningDisability);

        // Get the learningDisability
        restLearningDisabilityMockMvc
            .perform(get(ENTITY_API_URL_ID, learningDisability.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(learningDisability.getId()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.disabilityType").value(DEFAULT_DISABILITY_TYPE.toString()));
    }

    @Test
    void getNonExistingLearningDisability() throws Exception {
        // Get the learningDisability
        restLearningDisabilityMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    void putExistingLearningDisability() throws Exception {
        // Initialize the database
        learningDisabilityRepository.save(learningDisability);

        int databaseSizeBeforeUpdate = learningDisabilityRepository.findAll().size();

        // Update the learningDisability
        LearningDisability updatedLearningDisability = learningDisabilityRepository.findById(learningDisability.getId()).get();
        updatedLearningDisability.name(UPDATED_NAME).description(UPDATED_DESCRIPTION).disabilityType(UPDATED_DISABILITY_TYPE);

        restLearningDisabilityMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedLearningDisability.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedLearningDisability))
            )
            .andExpect(status().isOk());

        // Validate the LearningDisability in the database
        List<LearningDisability> learningDisabilityList = learningDisabilityRepository.findAll();
        assertThat(learningDisabilityList).hasSize(databaseSizeBeforeUpdate);
        LearningDisability testLearningDisability = learningDisabilityList.get(learningDisabilityList.size() - 1);
        assertThat(testLearningDisability.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testLearningDisability.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testLearningDisability.getDisabilityType()).isEqualTo(UPDATED_DISABILITY_TYPE);
    }

    @Test
    void putNonExistingLearningDisability() throws Exception {
        int databaseSizeBeforeUpdate = learningDisabilityRepository.findAll().size();
        learningDisability.setId(UUID.randomUUID().toString());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLearningDisabilityMockMvc
            .perform(
                put(ENTITY_API_URL_ID, learningDisability.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(learningDisability))
            )
            .andExpect(status().isBadRequest());

        // Validate the LearningDisability in the database
        List<LearningDisability> learningDisabilityList = learningDisabilityRepository.findAll();
        assertThat(learningDisabilityList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchLearningDisability() throws Exception {
        int databaseSizeBeforeUpdate = learningDisabilityRepository.findAll().size();
        learningDisability.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLearningDisabilityMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(learningDisability))
            )
            .andExpect(status().isBadRequest());

        // Validate the LearningDisability in the database
        List<LearningDisability> learningDisabilityList = learningDisabilityRepository.findAll();
        assertThat(learningDisabilityList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamLearningDisability() throws Exception {
        int databaseSizeBeforeUpdate = learningDisabilityRepository.findAll().size();
        learningDisability.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLearningDisabilityMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(learningDisability))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the LearningDisability in the database
        List<LearningDisability> learningDisabilityList = learningDisabilityRepository.findAll();
        assertThat(learningDisabilityList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdateLearningDisabilityWithPatch() throws Exception {
        // Initialize the database
        learningDisabilityRepository.save(learningDisability);

        int databaseSizeBeforeUpdate = learningDisabilityRepository.findAll().size();

        // Update the learningDisability using partial update
        LearningDisability partialUpdatedLearningDisability = new LearningDisability();
        partialUpdatedLearningDisability.setId(learningDisability.getId());

        partialUpdatedLearningDisability.name(UPDATED_NAME).description(UPDATED_DESCRIPTION);

        restLearningDisabilityMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedLearningDisability.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedLearningDisability))
            )
            .andExpect(status().isOk());

        // Validate the LearningDisability in the database
        List<LearningDisability> learningDisabilityList = learningDisabilityRepository.findAll();
        assertThat(learningDisabilityList).hasSize(databaseSizeBeforeUpdate);
        LearningDisability testLearningDisability = learningDisabilityList.get(learningDisabilityList.size() - 1);
        assertThat(testLearningDisability.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testLearningDisability.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testLearningDisability.getDisabilityType()).isEqualTo(DEFAULT_DISABILITY_TYPE);
    }

    @Test
    void fullUpdateLearningDisabilityWithPatch() throws Exception {
        // Initialize the database
        learningDisabilityRepository.save(learningDisability);

        int databaseSizeBeforeUpdate = learningDisabilityRepository.findAll().size();

        // Update the learningDisability using partial update
        LearningDisability partialUpdatedLearningDisability = new LearningDisability();
        partialUpdatedLearningDisability.setId(learningDisability.getId());

        partialUpdatedLearningDisability.name(UPDATED_NAME).description(UPDATED_DESCRIPTION).disabilityType(UPDATED_DISABILITY_TYPE);

        restLearningDisabilityMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedLearningDisability.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedLearningDisability))
            )
            .andExpect(status().isOk());

        // Validate the LearningDisability in the database
        List<LearningDisability> learningDisabilityList = learningDisabilityRepository.findAll();
        assertThat(learningDisabilityList).hasSize(databaseSizeBeforeUpdate);
        LearningDisability testLearningDisability = learningDisabilityList.get(learningDisabilityList.size() - 1);
        assertThat(testLearningDisability.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testLearningDisability.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testLearningDisability.getDisabilityType()).isEqualTo(UPDATED_DISABILITY_TYPE);
    }

    @Test
    void patchNonExistingLearningDisability() throws Exception {
        int databaseSizeBeforeUpdate = learningDisabilityRepository.findAll().size();
        learningDisability.setId(UUID.randomUUID().toString());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLearningDisabilityMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, learningDisability.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(learningDisability))
            )
            .andExpect(status().isBadRequest());

        // Validate the LearningDisability in the database
        List<LearningDisability> learningDisabilityList = learningDisabilityRepository.findAll();
        assertThat(learningDisabilityList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchLearningDisability() throws Exception {
        int databaseSizeBeforeUpdate = learningDisabilityRepository.findAll().size();
        learningDisability.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLearningDisabilityMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(learningDisability))
            )
            .andExpect(status().isBadRequest());

        // Validate the LearningDisability in the database
        List<LearningDisability> learningDisabilityList = learningDisabilityRepository.findAll();
        assertThat(learningDisabilityList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamLearningDisability() throws Exception {
        int databaseSizeBeforeUpdate = learningDisabilityRepository.findAll().size();
        learningDisability.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLearningDisabilityMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(learningDisability))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the LearningDisability in the database
        List<LearningDisability> learningDisabilityList = learningDisabilityRepository.findAll();
        assertThat(learningDisabilityList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void deleteLearningDisability() throws Exception {
        // Initialize the database
        learningDisabilityRepository.save(learningDisability);

        int databaseSizeBeforeDelete = learningDisabilityRepository.findAll().size();

        // Delete the learningDisability
        restLearningDisabilityMockMvc
            .perform(delete(ENTITY_API_URL_ID, learningDisability.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<LearningDisability> learningDisabilityList = learningDisabilityRepository.findAll();
        assertThat(learningDisabilityList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
