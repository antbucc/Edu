package com.modis.edu.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.modis.edu.IntegrationTest;
import com.modis.edu.domain.PreferredTopic;
import com.modis.edu.repository.PreferredTopicRepository;
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
 * Integration tests for the {@link PreferredTopicResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class PreferredTopicResourceIT {

    private static final Long DEFAULT_TOPIC_ID = 1L;
    private static final Long UPDATED_TOPIC_ID = 2L;

    private static final String DEFAULT_TOPIC = "AAAAAAAAAA";
    private static final String UPDATED_TOPIC = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/preferred-topics";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private PreferredTopicRepository preferredTopicRepository;

    @Autowired
    private MockMvc restPreferredTopicMockMvc;

    private PreferredTopic preferredTopic;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PreferredTopic createEntity() {
        PreferredTopic preferredTopic = new PreferredTopic().topicId(DEFAULT_TOPIC_ID).topic(DEFAULT_TOPIC);
        return preferredTopic;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PreferredTopic createUpdatedEntity() {
        PreferredTopic preferredTopic = new PreferredTopic().topicId(UPDATED_TOPIC_ID).topic(UPDATED_TOPIC);
        return preferredTopic;
    }

    @BeforeEach
    public void initTest() {
        preferredTopicRepository.deleteAll();
        preferredTopic = createEntity();
    }

    @Test
    void createPreferredTopic() throws Exception {
        int databaseSizeBeforeCreate = preferredTopicRepository.findAll().size();
        // Create the PreferredTopic
        restPreferredTopicMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(preferredTopic))
            )
            .andExpect(status().isCreated());

        // Validate the PreferredTopic in the database
        List<PreferredTopic> preferredTopicList = preferredTopicRepository.findAll();
        assertThat(preferredTopicList).hasSize(databaseSizeBeforeCreate + 1);
        PreferredTopic testPreferredTopic = preferredTopicList.get(preferredTopicList.size() - 1);
        assertThat(testPreferredTopic.getTopicId()).isEqualTo(DEFAULT_TOPIC_ID);
        assertThat(testPreferredTopic.getTopic()).isEqualTo(DEFAULT_TOPIC);
    }

    @Test
    void createPreferredTopicWithExistingId() throws Exception {
        // Create the PreferredTopic with an existing ID
        preferredTopic.setId("existing_id");

        int databaseSizeBeforeCreate = preferredTopicRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPreferredTopicMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(preferredTopic))
            )
            .andExpect(status().isBadRequest());

        // Validate the PreferredTopic in the database
        List<PreferredTopic> preferredTopicList = preferredTopicRepository.findAll();
        assertThat(preferredTopicList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    void getAllPreferredTopics() throws Exception {
        // Initialize the database
        preferredTopicRepository.save(preferredTopic);

        // Get all the preferredTopicList
        restPreferredTopicMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(preferredTopic.getId())))
            .andExpect(jsonPath("$.[*].topicId").value(hasItem(DEFAULT_TOPIC_ID.intValue())))
            .andExpect(jsonPath("$.[*].topic").value(hasItem(DEFAULT_TOPIC)));
    }

    @Test
    void getPreferredTopic() throws Exception {
        // Initialize the database
        preferredTopicRepository.save(preferredTopic);

        // Get the preferredTopic
        restPreferredTopicMockMvc
            .perform(get(ENTITY_API_URL_ID, preferredTopic.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(preferredTopic.getId()))
            .andExpect(jsonPath("$.topicId").value(DEFAULT_TOPIC_ID.intValue()))
            .andExpect(jsonPath("$.topic").value(DEFAULT_TOPIC));
    }

    @Test
    void getNonExistingPreferredTopic() throws Exception {
        // Get the preferredTopic
        restPreferredTopicMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    void putExistingPreferredTopic() throws Exception {
        // Initialize the database
        preferredTopicRepository.save(preferredTopic);

        int databaseSizeBeforeUpdate = preferredTopicRepository.findAll().size();

        // Update the preferredTopic
        PreferredTopic updatedPreferredTopic = preferredTopicRepository.findById(preferredTopic.getId()).get();
        updatedPreferredTopic.topicId(UPDATED_TOPIC_ID).topic(UPDATED_TOPIC);

        restPreferredTopicMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedPreferredTopic.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedPreferredTopic))
            )
            .andExpect(status().isOk());

        // Validate the PreferredTopic in the database
        List<PreferredTopic> preferredTopicList = preferredTopicRepository.findAll();
        assertThat(preferredTopicList).hasSize(databaseSizeBeforeUpdate);
        PreferredTopic testPreferredTopic = preferredTopicList.get(preferredTopicList.size() - 1);
        assertThat(testPreferredTopic.getTopicId()).isEqualTo(UPDATED_TOPIC_ID);
        assertThat(testPreferredTopic.getTopic()).isEqualTo(UPDATED_TOPIC);
    }

    @Test
    void putNonExistingPreferredTopic() throws Exception {
        int databaseSizeBeforeUpdate = preferredTopicRepository.findAll().size();
        preferredTopic.setId(UUID.randomUUID().toString());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPreferredTopicMockMvc
            .perform(
                put(ENTITY_API_URL_ID, preferredTopic.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(preferredTopic))
            )
            .andExpect(status().isBadRequest());

        // Validate the PreferredTopic in the database
        List<PreferredTopic> preferredTopicList = preferredTopicRepository.findAll();
        assertThat(preferredTopicList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchPreferredTopic() throws Exception {
        int databaseSizeBeforeUpdate = preferredTopicRepository.findAll().size();
        preferredTopic.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPreferredTopicMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(preferredTopic))
            )
            .andExpect(status().isBadRequest());

        // Validate the PreferredTopic in the database
        List<PreferredTopic> preferredTopicList = preferredTopicRepository.findAll();
        assertThat(preferredTopicList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamPreferredTopic() throws Exception {
        int databaseSizeBeforeUpdate = preferredTopicRepository.findAll().size();
        preferredTopic.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPreferredTopicMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(preferredTopic)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the PreferredTopic in the database
        List<PreferredTopic> preferredTopicList = preferredTopicRepository.findAll();
        assertThat(preferredTopicList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdatePreferredTopicWithPatch() throws Exception {
        // Initialize the database
        preferredTopicRepository.save(preferredTopic);

        int databaseSizeBeforeUpdate = preferredTopicRepository.findAll().size();

        // Update the preferredTopic using partial update
        PreferredTopic partialUpdatedPreferredTopic = new PreferredTopic();
        partialUpdatedPreferredTopic.setId(preferredTopic.getId());

        partialUpdatedPreferredTopic.topic(UPDATED_TOPIC);

        restPreferredTopicMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPreferredTopic.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPreferredTopic))
            )
            .andExpect(status().isOk());

        // Validate the PreferredTopic in the database
        List<PreferredTopic> preferredTopicList = preferredTopicRepository.findAll();
        assertThat(preferredTopicList).hasSize(databaseSizeBeforeUpdate);
        PreferredTopic testPreferredTopic = preferredTopicList.get(preferredTopicList.size() - 1);
        assertThat(testPreferredTopic.getTopicId()).isEqualTo(DEFAULT_TOPIC_ID);
        assertThat(testPreferredTopic.getTopic()).isEqualTo(UPDATED_TOPIC);
    }

    @Test
    void fullUpdatePreferredTopicWithPatch() throws Exception {
        // Initialize the database
        preferredTopicRepository.save(preferredTopic);

        int databaseSizeBeforeUpdate = preferredTopicRepository.findAll().size();

        // Update the preferredTopic using partial update
        PreferredTopic partialUpdatedPreferredTopic = new PreferredTopic();
        partialUpdatedPreferredTopic.setId(preferredTopic.getId());

        partialUpdatedPreferredTopic.topicId(UPDATED_TOPIC_ID).topic(UPDATED_TOPIC);

        restPreferredTopicMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPreferredTopic.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPreferredTopic))
            )
            .andExpect(status().isOk());

        // Validate the PreferredTopic in the database
        List<PreferredTopic> preferredTopicList = preferredTopicRepository.findAll();
        assertThat(preferredTopicList).hasSize(databaseSizeBeforeUpdate);
        PreferredTopic testPreferredTopic = preferredTopicList.get(preferredTopicList.size() - 1);
        assertThat(testPreferredTopic.getTopicId()).isEqualTo(UPDATED_TOPIC_ID);
        assertThat(testPreferredTopic.getTopic()).isEqualTo(UPDATED_TOPIC);
    }

    @Test
    void patchNonExistingPreferredTopic() throws Exception {
        int databaseSizeBeforeUpdate = preferredTopicRepository.findAll().size();
        preferredTopic.setId(UUID.randomUUID().toString());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPreferredTopicMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, preferredTopic.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(preferredTopic))
            )
            .andExpect(status().isBadRequest());

        // Validate the PreferredTopic in the database
        List<PreferredTopic> preferredTopicList = preferredTopicRepository.findAll();
        assertThat(preferredTopicList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchPreferredTopic() throws Exception {
        int databaseSizeBeforeUpdate = preferredTopicRepository.findAll().size();
        preferredTopic.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPreferredTopicMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(preferredTopic))
            )
            .andExpect(status().isBadRequest());

        // Validate the PreferredTopic in the database
        List<PreferredTopic> preferredTopicList = preferredTopicRepository.findAll();
        assertThat(preferredTopicList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamPreferredTopic() throws Exception {
        int databaseSizeBeforeUpdate = preferredTopicRepository.findAll().size();
        preferredTopic.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPreferredTopicMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(preferredTopic))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the PreferredTopic in the database
        List<PreferredTopic> preferredTopicList = preferredTopicRepository.findAll();
        assertThat(preferredTopicList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void deletePreferredTopic() throws Exception {
        // Initialize the database
        preferredTopicRepository.save(preferredTopic);

        int databaseSizeBeforeDelete = preferredTopicRepository.findAll().size();

        // Delete the preferredTopic
        restPreferredTopicMockMvc
            .perform(delete(ENTITY_API_URL_ID, preferredTopic.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PreferredTopic> preferredTopicList = preferredTopicRepository.findAll();
        assertThat(preferredTopicList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
