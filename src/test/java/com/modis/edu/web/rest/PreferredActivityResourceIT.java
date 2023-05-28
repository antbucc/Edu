package com.modis.edu.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.modis.edu.IntegrationTest;
import com.modis.edu.domain.PreferredActivity;
import com.modis.edu.domain.enumeration.ActivityType;
import com.modis.edu.domain.enumeration.Difficulty;
import com.modis.edu.domain.enumeration.Tool;
import com.modis.edu.repository.PreferredActivityRepository;
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
 * Integration tests for the {@link PreferredActivityResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class PreferredActivityResourceIT {

    private static final String DEFAULT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_TITLE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final ActivityType DEFAULT_TYPE = ActivityType.INDIVIDUAL;
    private static final ActivityType UPDATED_TYPE = ActivityType.GROUP;

    private static final Tool DEFAULT_TOOL = Tool.COMPUTER;
    private static final Tool UPDATED_TOOL = Tool.MOBILE;

    private static final Difficulty DEFAULT_DIFFICULTY = Difficulty.LOW;
    private static final Difficulty UPDATED_DIFFICULTY = Difficulty.MODERATE;

    private static final String ENTITY_API_URL = "/api/preferred-activities";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private PreferredActivityRepository preferredActivityRepository;

    @Autowired
    private MockMvc restPreferredActivityMockMvc;

    private PreferredActivity preferredActivity;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PreferredActivity createEntity() {
        PreferredActivity preferredActivity = new PreferredActivity()
            .title(DEFAULT_TITLE)
            .description(DEFAULT_DESCRIPTION)
            .type(DEFAULT_TYPE)
            .tool(DEFAULT_TOOL)
            .difficulty(DEFAULT_DIFFICULTY);
        return preferredActivity;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PreferredActivity createUpdatedEntity() {
        PreferredActivity preferredActivity = new PreferredActivity()
            .title(UPDATED_TITLE)
            .description(UPDATED_DESCRIPTION)
            .type(UPDATED_TYPE)
            .tool(UPDATED_TOOL)
            .difficulty(UPDATED_DIFFICULTY);
        return preferredActivity;
    }

    @BeforeEach
    public void initTest() {
        preferredActivityRepository.deleteAll();
        preferredActivity = createEntity();
    }

    @Test
    void createPreferredActivity() throws Exception {
        int databaseSizeBeforeCreate = preferredActivityRepository.findAll().size();
        // Create the PreferredActivity
        restPreferredActivityMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(preferredActivity))
            )
            .andExpect(status().isCreated());

        // Validate the PreferredActivity in the database
        List<PreferredActivity> preferredActivityList = preferredActivityRepository.findAll();
        assertThat(preferredActivityList).hasSize(databaseSizeBeforeCreate + 1);
        PreferredActivity testPreferredActivity = preferredActivityList.get(preferredActivityList.size() - 1);
        assertThat(testPreferredActivity.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testPreferredActivity.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testPreferredActivity.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testPreferredActivity.getTool()).isEqualTo(DEFAULT_TOOL);
        assertThat(testPreferredActivity.getDifficulty()).isEqualTo(DEFAULT_DIFFICULTY);
    }

    @Test
    void createPreferredActivityWithExistingId() throws Exception {
        // Create the PreferredActivity with an existing ID
        preferredActivity.setId("existing_id");

        int databaseSizeBeforeCreate = preferredActivityRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPreferredActivityMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(preferredActivity))
            )
            .andExpect(status().isBadRequest());

        // Validate the PreferredActivity in the database
        List<PreferredActivity> preferredActivityList = preferredActivityRepository.findAll();
        assertThat(preferredActivityList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    void getAllPreferredActivities() throws Exception {
        // Initialize the database
        preferredActivityRepository.save(preferredActivity);

        // Get all the preferredActivityList
        restPreferredActivityMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(preferredActivity.getId())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].tool").value(hasItem(DEFAULT_TOOL.toString())))
            .andExpect(jsonPath("$.[*].difficulty").value(hasItem(DEFAULT_DIFFICULTY.toString())));
    }

    @Test
    void getPreferredActivity() throws Exception {
        // Initialize the database
        preferredActivityRepository.save(preferredActivity);

        // Get the preferredActivity
        restPreferredActivityMockMvc
            .perform(get(ENTITY_API_URL_ID, preferredActivity.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(preferredActivity.getId()))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()))
            .andExpect(jsonPath("$.tool").value(DEFAULT_TOOL.toString()))
            .andExpect(jsonPath("$.difficulty").value(DEFAULT_DIFFICULTY.toString()));
    }

    @Test
    void getNonExistingPreferredActivity() throws Exception {
        // Get the preferredActivity
        restPreferredActivityMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    void putExistingPreferredActivity() throws Exception {
        // Initialize the database
        preferredActivityRepository.save(preferredActivity);

        int databaseSizeBeforeUpdate = preferredActivityRepository.findAll().size();

        // Update the preferredActivity
        PreferredActivity updatedPreferredActivity = preferredActivityRepository.findById(preferredActivity.getId()).get();
        updatedPreferredActivity
            .title(UPDATED_TITLE)
            .description(UPDATED_DESCRIPTION)
            .type(UPDATED_TYPE)
            .tool(UPDATED_TOOL)
            .difficulty(UPDATED_DIFFICULTY);

        restPreferredActivityMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedPreferredActivity.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedPreferredActivity))
            )
            .andExpect(status().isOk());

        // Validate the PreferredActivity in the database
        List<PreferredActivity> preferredActivityList = preferredActivityRepository.findAll();
        assertThat(preferredActivityList).hasSize(databaseSizeBeforeUpdate);
        PreferredActivity testPreferredActivity = preferredActivityList.get(preferredActivityList.size() - 1);
        assertThat(testPreferredActivity.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testPreferredActivity.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testPreferredActivity.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testPreferredActivity.getTool()).isEqualTo(UPDATED_TOOL);
        assertThat(testPreferredActivity.getDifficulty()).isEqualTo(UPDATED_DIFFICULTY);
    }

    @Test
    void putNonExistingPreferredActivity() throws Exception {
        int databaseSizeBeforeUpdate = preferredActivityRepository.findAll().size();
        preferredActivity.setId(UUID.randomUUID().toString());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPreferredActivityMockMvc
            .perform(
                put(ENTITY_API_URL_ID, preferredActivity.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(preferredActivity))
            )
            .andExpect(status().isBadRequest());

        // Validate the PreferredActivity in the database
        List<PreferredActivity> preferredActivityList = preferredActivityRepository.findAll();
        assertThat(preferredActivityList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchPreferredActivity() throws Exception {
        int databaseSizeBeforeUpdate = preferredActivityRepository.findAll().size();
        preferredActivity.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPreferredActivityMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(preferredActivity))
            )
            .andExpect(status().isBadRequest());

        // Validate the PreferredActivity in the database
        List<PreferredActivity> preferredActivityList = preferredActivityRepository.findAll();
        assertThat(preferredActivityList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamPreferredActivity() throws Exception {
        int databaseSizeBeforeUpdate = preferredActivityRepository.findAll().size();
        preferredActivity.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPreferredActivityMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(preferredActivity))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the PreferredActivity in the database
        List<PreferredActivity> preferredActivityList = preferredActivityRepository.findAll();
        assertThat(preferredActivityList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdatePreferredActivityWithPatch() throws Exception {
        // Initialize the database
        preferredActivityRepository.save(preferredActivity);

        int databaseSizeBeforeUpdate = preferredActivityRepository.findAll().size();

        // Update the preferredActivity using partial update
        PreferredActivity partialUpdatedPreferredActivity = new PreferredActivity();
        partialUpdatedPreferredActivity.setId(preferredActivity.getId());

        partialUpdatedPreferredActivity
            .title(UPDATED_TITLE)
            .description(UPDATED_DESCRIPTION)
            .type(UPDATED_TYPE)
            .difficulty(UPDATED_DIFFICULTY);

        restPreferredActivityMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPreferredActivity.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPreferredActivity))
            )
            .andExpect(status().isOk());

        // Validate the PreferredActivity in the database
        List<PreferredActivity> preferredActivityList = preferredActivityRepository.findAll();
        assertThat(preferredActivityList).hasSize(databaseSizeBeforeUpdate);
        PreferredActivity testPreferredActivity = preferredActivityList.get(preferredActivityList.size() - 1);
        assertThat(testPreferredActivity.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testPreferredActivity.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testPreferredActivity.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testPreferredActivity.getTool()).isEqualTo(DEFAULT_TOOL);
        assertThat(testPreferredActivity.getDifficulty()).isEqualTo(UPDATED_DIFFICULTY);
    }

    @Test
    void fullUpdatePreferredActivityWithPatch() throws Exception {
        // Initialize the database
        preferredActivityRepository.save(preferredActivity);

        int databaseSizeBeforeUpdate = preferredActivityRepository.findAll().size();

        // Update the preferredActivity using partial update
        PreferredActivity partialUpdatedPreferredActivity = new PreferredActivity();
        partialUpdatedPreferredActivity.setId(preferredActivity.getId());

        partialUpdatedPreferredActivity
            .title(UPDATED_TITLE)
            .description(UPDATED_DESCRIPTION)
            .type(UPDATED_TYPE)
            .tool(UPDATED_TOOL)
            .difficulty(UPDATED_DIFFICULTY);

        restPreferredActivityMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPreferredActivity.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPreferredActivity))
            )
            .andExpect(status().isOk());

        // Validate the PreferredActivity in the database
        List<PreferredActivity> preferredActivityList = preferredActivityRepository.findAll();
        assertThat(preferredActivityList).hasSize(databaseSizeBeforeUpdate);
        PreferredActivity testPreferredActivity = preferredActivityList.get(preferredActivityList.size() - 1);
        assertThat(testPreferredActivity.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testPreferredActivity.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testPreferredActivity.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testPreferredActivity.getTool()).isEqualTo(UPDATED_TOOL);
        assertThat(testPreferredActivity.getDifficulty()).isEqualTo(UPDATED_DIFFICULTY);
    }

    @Test
    void patchNonExistingPreferredActivity() throws Exception {
        int databaseSizeBeforeUpdate = preferredActivityRepository.findAll().size();
        preferredActivity.setId(UUID.randomUUID().toString());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPreferredActivityMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, preferredActivity.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(preferredActivity))
            )
            .andExpect(status().isBadRequest());

        // Validate the PreferredActivity in the database
        List<PreferredActivity> preferredActivityList = preferredActivityRepository.findAll();
        assertThat(preferredActivityList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchPreferredActivity() throws Exception {
        int databaseSizeBeforeUpdate = preferredActivityRepository.findAll().size();
        preferredActivity.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPreferredActivityMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(preferredActivity))
            )
            .andExpect(status().isBadRequest());

        // Validate the PreferredActivity in the database
        List<PreferredActivity> preferredActivityList = preferredActivityRepository.findAll();
        assertThat(preferredActivityList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamPreferredActivity() throws Exception {
        int databaseSizeBeforeUpdate = preferredActivityRepository.findAll().size();
        preferredActivity.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPreferredActivityMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(preferredActivity))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the PreferredActivity in the database
        List<PreferredActivity> preferredActivityList = preferredActivityRepository.findAll();
        assertThat(preferredActivityList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void deletePreferredActivity() throws Exception {
        // Initialize the database
        preferredActivityRepository.save(preferredActivity);

        int databaseSizeBeforeDelete = preferredActivityRepository.findAll().size();

        // Delete the preferredActivity
        restPreferredActivityMockMvc
            .perform(delete(ENTITY_API_URL_ID, preferredActivity.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PreferredActivity> preferredActivityList = preferredActivityRepository.findAll();
        assertThat(preferredActivityList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
