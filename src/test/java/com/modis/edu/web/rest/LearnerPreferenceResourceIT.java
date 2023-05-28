package com.modis.edu.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.modis.edu.IntegrationTest;
import com.modis.edu.domain.LearnerPreference;
import com.modis.edu.domain.enumeration.Difficulty;
import com.modis.edu.domain.enumeration.DisabilityType;
import com.modis.edu.domain.enumeration.LearningStyleType;
import com.modis.edu.domain.enumeration.ModalityType;
import com.modis.edu.repository.LearnerPreferenceRepository;
import com.modis.edu.service.LearnerPreferenceService;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

/**
 * Integration tests for the {@link LearnerPreferenceResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class LearnerPreferenceResourceIT {

    private static final String DEFAULT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_TITLE = "BBBBBBBBBB";

    private static final LearningStyleType DEFAULT_STYLE = LearningStyleType.VISUAL;
    private static final LearningStyleType UPDATED_STYLE = LearningStyleType.AUDITORY;

    private static final ModalityType DEFAULT_MODALITY = ModalityType.ONLINE;
    private static final ModalityType UPDATED_MODALITY = ModalityType.IN_PERSON;

    private static final Difficulty DEFAULT_DIFFICULTY = Difficulty.LOW;
    private static final Difficulty UPDATED_DIFFICULTY = Difficulty.MODERATE;

    private static final DisabilityType DEFAULT_DISABILITY = DisabilityType.DYSLEXIA;
    private static final DisabilityType UPDATED_DISABILITY = DisabilityType.DYSCALCULIA;

    private static final String ENTITY_API_URL = "/api/learner-preferences";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private LearnerPreferenceRepository learnerPreferenceRepository;

    @Mock
    private LearnerPreferenceRepository learnerPreferenceRepositoryMock;

    @Mock
    private LearnerPreferenceService learnerPreferenceServiceMock;

    @Autowired
    private MockMvc restLearnerPreferenceMockMvc;

    private LearnerPreference learnerPreference;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static LearnerPreference createEntity() {
        LearnerPreference learnerPreference = new LearnerPreference()
            .title(DEFAULT_TITLE)
            .style(DEFAULT_STYLE)
            .modality(DEFAULT_MODALITY)
            .difficulty(DEFAULT_DIFFICULTY)
            .disability(DEFAULT_DISABILITY);
        return learnerPreference;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static LearnerPreference createUpdatedEntity() {
        LearnerPreference learnerPreference = new LearnerPreference()
            .title(UPDATED_TITLE)
            .style(UPDATED_STYLE)
            .modality(UPDATED_MODALITY)
            .difficulty(UPDATED_DIFFICULTY)
            .disability(UPDATED_DISABILITY);
        return learnerPreference;
    }

    @BeforeEach
    public void initTest() {
        learnerPreferenceRepository.deleteAll();
        learnerPreference = createEntity();
    }

    @Test
    void createLearnerPreference() throws Exception {
        int databaseSizeBeforeCreate = learnerPreferenceRepository.findAll().size();
        // Create the LearnerPreference
        restLearnerPreferenceMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(learnerPreference))
            )
            .andExpect(status().isCreated());

        // Validate the LearnerPreference in the database
        List<LearnerPreference> learnerPreferenceList = learnerPreferenceRepository.findAll();
        assertThat(learnerPreferenceList).hasSize(databaseSizeBeforeCreate + 1);
        LearnerPreference testLearnerPreference = learnerPreferenceList.get(learnerPreferenceList.size() - 1);
        assertThat(testLearnerPreference.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testLearnerPreference.getStyle()).isEqualTo(DEFAULT_STYLE);
        assertThat(testLearnerPreference.getModality()).isEqualTo(DEFAULT_MODALITY);
        assertThat(testLearnerPreference.getDifficulty()).isEqualTo(DEFAULT_DIFFICULTY);
        assertThat(testLearnerPreference.getDisability()).isEqualTo(DEFAULT_DISABILITY);
    }

    @Test
    void createLearnerPreferenceWithExistingId() throws Exception {
        // Create the LearnerPreference with an existing ID
        learnerPreference.setId("existing_id");

        int databaseSizeBeforeCreate = learnerPreferenceRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restLearnerPreferenceMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(learnerPreference))
            )
            .andExpect(status().isBadRequest());

        // Validate the LearnerPreference in the database
        List<LearnerPreference> learnerPreferenceList = learnerPreferenceRepository.findAll();
        assertThat(learnerPreferenceList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    void getAllLearnerPreferences() throws Exception {
        // Initialize the database
        learnerPreferenceRepository.save(learnerPreference);

        // Get all the learnerPreferenceList
        restLearnerPreferenceMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(learnerPreference.getId())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE)))
            .andExpect(jsonPath("$.[*].style").value(hasItem(DEFAULT_STYLE.toString())))
            .andExpect(jsonPath("$.[*].modality").value(hasItem(DEFAULT_MODALITY.toString())))
            .andExpect(jsonPath("$.[*].difficulty").value(hasItem(DEFAULT_DIFFICULTY.toString())))
            .andExpect(jsonPath("$.[*].disability").value(hasItem(DEFAULT_DISABILITY.toString())));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllLearnerPreferencesWithEagerRelationshipsIsEnabled() throws Exception {
        when(learnerPreferenceServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restLearnerPreferenceMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(learnerPreferenceServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllLearnerPreferencesWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(learnerPreferenceServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restLearnerPreferenceMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(learnerPreferenceRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    void getLearnerPreference() throws Exception {
        // Initialize the database
        learnerPreferenceRepository.save(learnerPreference);

        // Get the learnerPreference
        restLearnerPreferenceMockMvc
            .perform(get(ENTITY_API_URL_ID, learnerPreference.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(learnerPreference.getId()))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE))
            .andExpect(jsonPath("$.style").value(DEFAULT_STYLE.toString()))
            .andExpect(jsonPath("$.modality").value(DEFAULT_MODALITY.toString()))
            .andExpect(jsonPath("$.difficulty").value(DEFAULT_DIFFICULTY.toString()))
            .andExpect(jsonPath("$.disability").value(DEFAULT_DISABILITY.toString()));
    }

    @Test
    void getNonExistingLearnerPreference() throws Exception {
        // Get the learnerPreference
        restLearnerPreferenceMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    void putExistingLearnerPreference() throws Exception {
        // Initialize the database
        learnerPreferenceRepository.save(learnerPreference);

        int databaseSizeBeforeUpdate = learnerPreferenceRepository.findAll().size();

        // Update the learnerPreference
        LearnerPreference updatedLearnerPreference = learnerPreferenceRepository.findById(learnerPreference.getId()).get();
        updatedLearnerPreference
            .title(UPDATED_TITLE)
            .style(UPDATED_STYLE)
            .modality(UPDATED_MODALITY)
            .difficulty(UPDATED_DIFFICULTY)
            .disability(UPDATED_DISABILITY);

        restLearnerPreferenceMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedLearnerPreference.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedLearnerPreference))
            )
            .andExpect(status().isOk());

        // Validate the LearnerPreference in the database
        List<LearnerPreference> learnerPreferenceList = learnerPreferenceRepository.findAll();
        assertThat(learnerPreferenceList).hasSize(databaseSizeBeforeUpdate);
        LearnerPreference testLearnerPreference = learnerPreferenceList.get(learnerPreferenceList.size() - 1);
        assertThat(testLearnerPreference.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testLearnerPreference.getStyle()).isEqualTo(UPDATED_STYLE);
        assertThat(testLearnerPreference.getModality()).isEqualTo(UPDATED_MODALITY);
        assertThat(testLearnerPreference.getDifficulty()).isEqualTo(UPDATED_DIFFICULTY);
        assertThat(testLearnerPreference.getDisability()).isEqualTo(UPDATED_DISABILITY);
    }

    @Test
    void putNonExistingLearnerPreference() throws Exception {
        int databaseSizeBeforeUpdate = learnerPreferenceRepository.findAll().size();
        learnerPreference.setId(UUID.randomUUID().toString());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLearnerPreferenceMockMvc
            .perform(
                put(ENTITY_API_URL_ID, learnerPreference.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(learnerPreference))
            )
            .andExpect(status().isBadRequest());

        // Validate the LearnerPreference in the database
        List<LearnerPreference> learnerPreferenceList = learnerPreferenceRepository.findAll();
        assertThat(learnerPreferenceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchLearnerPreference() throws Exception {
        int databaseSizeBeforeUpdate = learnerPreferenceRepository.findAll().size();
        learnerPreference.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLearnerPreferenceMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(learnerPreference))
            )
            .andExpect(status().isBadRequest());

        // Validate the LearnerPreference in the database
        List<LearnerPreference> learnerPreferenceList = learnerPreferenceRepository.findAll();
        assertThat(learnerPreferenceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamLearnerPreference() throws Exception {
        int databaseSizeBeforeUpdate = learnerPreferenceRepository.findAll().size();
        learnerPreference.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLearnerPreferenceMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(learnerPreference))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the LearnerPreference in the database
        List<LearnerPreference> learnerPreferenceList = learnerPreferenceRepository.findAll();
        assertThat(learnerPreferenceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdateLearnerPreferenceWithPatch() throws Exception {
        // Initialize the database
        learnerPreferenceRepository.save(learnerPreference);

        int databaseSizeBeforeUpdate = learnerPreferenceRepository.findAll().size();

        // Update the learnerPreference using partial update
        LearnerPreference partialUpdatedLearnerPreference = new LearnerPreference();
        partialUpdatedLearnerPreference.setId(learnerPreference.getId());

        partialUpdatedLearnerPreference
            .title(UPDATED_TITLE)
            .modality(UPDATED_MODALITY)
            .difficulty(UPDATED_DIFFICULTY)
            .disability(UPDATED_DISABILITY);

        restLearnerPreferenceMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedLearnerPreference.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedLearnerPreference))
            )
            .andExpect(status().isOk());

        // Validate the LearnerPreference in the database
        List<LearnerPreference> learnerPreferenceList = learnerPreferenceRepository.findAll();
        assertThat(learnerPreferenceList).hasSize(databaseSizeBeforeUpdate);
        LearnerPreference testLearnerPreference = learnerPreferenceList.get(learnerPreferenceList.size() - 1);
        assertThat(testLearnerPreference.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testLearnerPreference.getStyle()).isEqualTo(DEFAULT_STYLE);
        assertThat(testLearnerPreference.getModality()).isEqualTo(UPDATED_MODALITY);
        assertThat(testLearnerPreference.getDifficulty()).isEqualTo(UPDATED_DIFFICULTY);
        assertThat(testLearnerPreference.getDisability()).isEqualTo(UPDATED_DISABILITY);
    }

    @Test
    void fullUpdateLearnerPreferenceWithPatch() throws Exception {
        // Initialize the database
        learnerPreferenceRepository.save(learnerPreference);

        int databaseSizeBeforeUpdate = learnerPreferenceRepository.findAll().size();

        // Update the learnerPreference using partial update
        LearnerPreference partialUpdatedLearnerPreference = new LearnerPreference();
        partialUpdatedLearnerPreference.setId(learnerPreference.getId());

        partialUpdatedLearnerPreference
            .title(UPDATED_TITLE)
            .style(UPDATED_STYLE)
            .modality(UPDATED_MODALITY)
            .difficulty(UPDATED_DIFFICULTY)
            .disability(UPDATED_DISABILITY);

        restLearnerPreferenceMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedLearnerPreference.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedLearnerPreference))
            )
            .andExpect(status().isOk());

        // Validate the LearnerPreference in the database
        List<LearnerPreference> learnerPreferenceList = learnerPreferenceRepository.findAll();
        assertThat(learnerPreferenceList).hasSize(databaseSizeBeforeUpdate);
        LearnerPreference testLearnerPreference = learnerPreferenceList.get(learnerPreferenceList.size() - 1);
        assertThat(testLearnerPreference.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testLearnerPreference.getStyle()).isEqualTo(UPDATED_STYLE);
        assertThat(testLearnerPreference.getModality()).isEqualTo(UPDATED_MODALITY);
        assertThat(testLearnerPreference.getDifficulty()).isEqualTo(UPDATED_DIFFICULTY);
        assertThat(testLearnerPreference.getDisability()).isEqualTo(UPDATED_DISABILITY);
    }

    @Test
    void patchNonExistingLearnerPreference() throws Exception {
        int databaseSizeBeforeUpdate = learnerPreferenceRepository.findAll().size();
        learnerPreference.setId(UUID.randomUUID().toString());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLearnerPreferenceMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, learnerPreference.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(learnerPreference))
            )
            .andExpect(status().isBadRequest());

        // Validate the LearnerPreference in the database
        List<LearnerPreference> learnerPreferenceList = learnerPreferenceRepository.findAll();
        assertThat(learnerPreferenceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchLearnerPreference() throws Exception {
        int databaseSizeBeforeUpdate = learnerPreferenceRepository.findAll().size();
        learnerPreference.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLearnerPreferenceMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(learnerPreference))
            )
            .andExpect(status().isBadRequest());

        // Validate the LearnerPreference in the database
        List<LearnerPreference> learnerPreferenceList = learnerPreferenceRepository.findAll();
        assertThat(learnerPreferenceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamLearnerPreference() throws Exception {
        int databaseSizeBeforeUpdate = learnerPreferenceRepository.findAll().size();
        learnerPreference.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLearnerPreferenceMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(learnerPreference))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the LearnerPreference in the database
        List<LearnerPreference> learnerPreferenceList = learnerPreferenceRepository.findAll();
        assertThat(learnerPreferenceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void deleteLearnerPreference() throws Exception {
        // Initialize the database
        learnerPreferenceRepository.save(learnerPreference);

        int databaseSizeBeforeDelete = learnerPreferenceRepository.findAll().size();

        // Delete the learnerPreference
        restLearnerPreferenceMockMvc
            .perform(delete(ENTITY_API_URL_ID, learnerPreference.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<LearnerPreference> learnerPreferenceList = learnerPreferenceRepository.findAll();
        assertThat(learnerPreferenceList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
