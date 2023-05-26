package com.modis.edu.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.modis.edu.IntegrationTest;
import com.modis.edu.domain.AbstractActivity;
import com.modis.edu.repository.AbstractActivityRepository;
import com.modis.edu.service.AbstractActivityService;
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
 * Integration tests for the {@link AbstractActivityResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class AbstractActivityResourceIT {

    private static final String DEFAULT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_TITLE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/abstract-activities";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private AbstractActivityRepository abstractActivityRepository;

    @Mock
    private AbstractActivityRepository abstractActivityRepositoryMock;

    @Mock
    private AbstractActivityService abstractActivityServiceMock;

    @Autowired
    private MockMvc restAbstractActivityMockMvc;

    private AbstractActivity abstractActivity;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AbstractActivity createEntity() {
        AbstractActivity abstractActivity = new AbstractActivity().title(DEFAULT_TITLE);
        return abstractActivity;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AbstractActivity createUpdatedEntity() {
        AbstractActivity abstractActivity = new AbstractActivity().title(UPDATED_TITLE);
        return abstractActivity;
    }

    @BeforeEach
    public void initTest() {
        abstractActivityRepository.deleteAll();
        abstractActivity = createEntity();
    }

    @Test
    void createAbstractActivity() throws Exception {
        int databaseSizeBeforeCreate = abstractActivityRepository.findAll().size();
        // Create the AbstractActivity
        restAbstractActivityMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(abstractActivity))
            )
            .andExpect(status().isCreated());

        // Validate the AbstractActivity in the database
        List<AbstractActivity> abstractActivityList = abstractActivityRepository.findAll();
        assertThat(abstractActivityList).hasSize(databaseSizeBeforeCreate + 1);
        AbstractActivity testAbstractActivity = abstractActivityList.get(abstractActivityList.size() - 1);
        assertThat(testAbstractActivity.getTitle()).isEqualTo(DEFAULT_TITLE);
    }

    @Test
    void createAbstractActivityWithExistingId() throws Exception {
        // Create the AbstractActivity with an existing ID
        abstractActivity.setId("existing_id");

        int databaseSizeBeforeCreate = abstractActivityRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restAbstractActivityMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(abstractActivity))
            )
            .andExpect(status().isBadRequest());

        // Validate the AbstractActivity in the database
        List<AbstractActivity> abstractActivityList = abstractActivityRepository.findAll();
        assertThat(abstractActivityList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    void checkTitleIsRequired() throws Exception {
        int databaseSizeBeforeTest = abstractActivityRepository.findAll().size();
        // set the field null
        abstractActivity.setTitle(null);

        // Create the AbstractActivity, which fails.

        restAbstractActivityMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(abstractActivity))
            )
            .andExpect(status().isBadRequest());

        List<AbstractActivity> abstractActivityList = abstractActivityRepository.findAll();
        assertThat(abstractActivityList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void getAllAbstractActivities() throws Exception {
        // Initialize the database
        abstractActivityRepository.save(abstractActivity);

        // Get all the abstractActivityList
        restAbstractActivityMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(abstractActivity.getId())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE)));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllAbstractActivitiesWithEagerRelationshipsIsEnabled() throws Exception {
        when(abstractActivityServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restAbstractActivityMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(abstractActivityServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllAbstractActivitiesWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(abstractActivityServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restAbstractActivityMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(abstractActivityRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    void getAbstractActivity() throws Exception {
        // Initialize the database
        abstractActivityRepository.save(abstractActivity);

        // Get the abstractActivity
        restAbstractActivityMockMvc
            .perform(get(ENTITY_API_URL_ID, abstractActivity.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(abstractActivity.getId()))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE));
    }

    @Test
    void getNonExistingAbstractActivity() throws Exception {
        // Get the abstractActivity
        restAbstractActivityMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    void putExistingAbstractActivity() throws Exception {
        // Initialize the database
        abstractActivityRepository.save(abstractActivity);

        int databaseSizeBeforeUpdate = abstractActivityRepository.findAll().size();

        // Update the abstractActivity
        AbstractActivity updatedAbstractActivity = abstractActivityRepository.findById(abstractActivity.getId()).get();
        updatedAbstractActivity.title(UPDATED_TITLE);

        restAbstractActivityMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedAbstractActivity.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedAbstractActivity))
            )
            .andExpect(status().isOk());

        // Validate the AbstractActivity in the database
        List<AbstractActivity> abstractActivityList = abstractActivityRepository.findAll();
        assertThat(abstractActivityList).hasSize(databaseSizeBeforeUpdate);
        AbstractActivity testAbstractActivity = abstractActivityList.get(abstractActivityList.size() - 1);
        assertThat(testAbstractActivity.getTitle()).isEqualTo(UPDATED_TITLE);
    }

    @Test
    void putNonExistingAbstractActivity() throws Exception {
        int databaseSizeBeforeUpdate = abstractActivityRepository.findAll().size();
        abstractActivity.setId(UUID.randomUUID().toString());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAbstractActivityMockMvc
            .perform(
                put(ENTITY_API_URL_ID, abstractActivity.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(abstractActivity))
            )
            .andExpect(status().isBadRequest());

        // Validate the AbstractActivity in the database
        List<AbstractActivity> abstractActivityList = abstractActivityRepository.findAll();
        assertThat(abstractActivityList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchAbstractActivity() throws Exception {
        int databaseSizeBeforeUpdate = abstractActivityRepository.findAll().size();
        abstractActivity.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAbstractActivityMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(abstractActivity))
            )
            .andExpect(status().isBadRequest());

        // Validate the AbstractActivity in the database
        List<AbstractActivity> abstractActivityList = abstractActivityRepository.findAll();
        assertThat(abstractActivityList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamAbstractActivity() throws Exception {
        int databaseSizeBeforeUpdate = abstractActivityRepository.findAll().size();
        abstractActivity.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAbstractActivityMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(abstractActivity))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the AbstractActivity in the database
        List<AbstractActivity> abstractActivityList = abstractActivityRepository.findAll();
        assertThat(abstractActivityList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdateAbstractActivityWithPatch() throws Exception {
        // Initialize the database
        abstractActivityRepository.save(abstractActivity);

        int databaseSizeBeforeUpdate = abstractActivityRepository.findAll().size();

        // Update the abstractActivity using partial update
        AbstractActivity partialUpdatedAbstractActivity = new AbstractActivity();
        partialUpdatedAbstractActivity.setId(abstractActivity.getId());

        partialUpdatedAbstractActivity.title(UPDATED_TITLE);

        restAbstractActivityMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAbstractActivity.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAbstractActivity))
            )
            .andExpect(status().isOk());

        // Validate the AbstractActivity in the database
        List<AbstractActivity> abstractActivityList = abstractActivityRepository.findAll();
        assertThat(abstractActivityList).hasSize(databaseSizeBeforeUpdate);
        AbstractActivity testAbstractActivity = abstractActivityList.get(abstractActivityList.size() - 1);
        assertThat(testAbstractActivity.getTitle()).isEqualTo(UPDATED_TITLE);
    }

    @Test
    void fullUpdateAbstractActivityWithPatch() throws Exception {
        // Initialize the database
        abstractActivityRepository.save(abstractActivity);

        int databaseSizeBeforeUpdate = abstractActivityRepository.findAll().size();

        // Update the abstractActivity using partial update
        AbstractActivity partialUpdatedAbstractActivity = new AbstractActivity();
        partialUpdatedAbstractActivity.setId(abstractActivity.getId());

        partialUpdatedAbstractActivity.title(UPDATED_TITLE);

        restAbstractActivityMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAbstractActivity.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAbstractActivity))
            )
            .andExpect(status().isOk());

        // Validate the AbstractActivity in the database
        List<AbstractActivity> abstractActivityList = abstractActivityRepository.findAll();
        assertThat(abstractActivityList).hasSize(databaseSizeBeforeUpdate);
        AbstractActivity testAbstractActivity = abstractActivityList.get(abstractActivityList.size() - 1);
        assertThat(testAbstractActivity.getTitle()).isEqualTo(UPDATED_TITLE);
    }

    @Test
    void patchNonExistingAbstractActivity() throws Exception {
        int databaseSizeBeforeUpdate = abstractActivityRepository.findAll().size();
        abstractActivity.setId(UUID.randomUUID().toString());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAbstractActivityMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, abstractActivity.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(abstractActivity))
            )
            .andExpect(status().isBadRequest());

        // Validate the AbstractActivity in the database
        List<AbstractActivity> abstractActivityList = abstractActivityRepository.findAll();
        assertThat(abstractActivityList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchAbstractActivity() throws Exception {
        int databaseSizeBeforeUpdate = abstractActivityRepository.findAll().size();
        abstractActivity.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAbstractActivityMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(abstractActivity))
            )
            .andExpect(status().isBadRequest());

        // Validate the AbstractActivity in the database
        List<AbstractActivity> abstractActivityList = abstractActivityRepository.findAll();
        assertThat(abstractActivityList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamAbstractActivity() throws Exception {
        int databaseSizeBeforeUpdate = abstractActivityRepository.findAll().size();
        abstractActivity.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAbstractActivityMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(abstractActivity))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the AbstractActivity in the database
        List<AbstractActivity> abstractActivityList = abstractActivityRepository.findAll();
        assertThat(abstractActivityList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void deleteAbstractActivity() throws Exception {
        // Initialize the database
        abstractActivityRepository.save(abstractActivity);

        int databaseSizeBeforeDelete = abstractActivityRepository.findAll().size();

        // Delete the abstractActivity
        restAbstractActivityMockMvc
            .perform(delete(ENTITY_API_URL_ID, abstractActivity.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<AbstractActivity> abstractActivityList = abstractActivityRepository.findAll();
        assertThat(abstractActivityList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
