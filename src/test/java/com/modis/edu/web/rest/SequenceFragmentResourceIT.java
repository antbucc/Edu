package com.modis.edu.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.modis.edu.IntegrationTest;
import com.modis.edu.domain.SequenceFragment;
import com.modis.edu.repository.SequenceFragmentRepository;
import com.modis.edu.service.SequenceFragmentService;
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
 * Integration tests for the {@link SequenceFragmentResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class SequenceFragmentResourceIT {

    private static final Integer DEFAULT_ORDER = 1;
    private static final Integer UPDATED_ORDER = 2;

    private static final String ENTITY_API_URL = "/api/sequence-fragments";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private SequenceFragmentRepository sequenceFragmentRepository;

    @Mock
    private SequenceFragmentRepository sequenceFragmentRepositoryMock;

    @Mock
    private SequenceFragmentService sequenceFragmentServiceMock;

    @Autowired
    private MockMvc restSequenceFragmentMockMvc;

    private SequenceFragment sequenceFragment;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SequenceFragment createEntity() {
        SequenceFragment sequenceFragment = new SequenceFragment().order(DEFAULT_ORDER);
        return sequenceFragment;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SequenceFragment createUpdatedEntity() {
        SequenceFragment sequenceFragment = new SequenceFragment().order(UPDATED_ORDER);
        return sequenceFragment;
    }

    @BeforeEach
    public void initTest() {
        sequenceFragmentRepository.deleteAll();
        sequenceFragment = createEntity();
    }

    @Test
    void createSequenceFragment() throws Exception {
        int databaseSizeBeforeCreate = sequenceFragmentRepository.findAll().size();
        // Create the SequenceFragment
        restSequenceFragmentMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(sequenceFragment))
            )
            .andExpect(status().isCreated());

        // Validate the SequenceFragment in the database
        List<SequenceFragment> sequenceFragmentList = sequenceFragmentRepository.findAll();
        assertThat(sequenceFragmentList).hasSize(databaseSizeBeforeCreate + 1);
        SequenceFragment testSequenceFragment = sequenceFragmentList.get(sequenceFragmentList.size() - 1);
        assertThat(testSequenceFragment.getOrder()).isEqualTo(DEFAULT_ORDER);
    }

    @Test
    void createSequenceFragmentWithExistingId() throws Exception {
        // Create the SequenceFragment with an existing ID
        sequenceFragment.setId("existing_id");

        int databaseSizeBeforeCreate = sequenceFragmentRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restSequenceFragmentMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(sequenceFragment))
            )
            .andExpect(status().isBadRequest());

        // Validate the SequenceFragment in the database
        List<SequenceFragment> sequenceFragmentList = sequenceFragmentRepository.findAll();
        assertThat(sequenceFragmentList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    void checkOrderIsRequired() throws Exception {
        int databaseSizeBeforeTest = sequenceFragmentRepository.findAll().size();
        // set the field null
        sequenceFragment.setOrder(null);

        // Create the SequenceFragment, which fails.

        restSequenceFragmentMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(sequenceFragment))
            )
            .andExpect(status().isBadRequest());

        List<SequenceFragment> sequenceFragmentList = sequenceFragmentRepository.findAll();
        assertThat(sequenceFragmentList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void getAllSequenceFragments() throws Exception {
        // Initialize the database
        sequenceFragmentRepository.save(sequenceFragment);

        // Get all the sequenceFragmentList
        restSequenceFragmentMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(sequenceFragment.getId())))
            .andExpect(jsonPath("$.[*].order").value(hasItem(DEFAULT_ORDER)));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllSequenceFragmentsWithEagerRelationshipsIsEnabled() throws Exception {
        when(sequenceFragmentServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restSequenceFragmentMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(sequenceFragmentServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllSequenceFragmentsWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(sequenceFragmentServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restSequenceFragmentMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(sequenceFragmentRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    void getSequenceFragment() throws Exception {
        // Initialize the database
        sequenceFragmentRepository.save(sequenceFragment);

        // Get the sequenceFragment
        restSequenceFragmentMockMvc
            .perform(get(ENTITY_API_URL_ID, sequenceFragment.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(sequenceFragment.getId()))
            .andExpect(jsonPath("$.order").value(DEFAULT_ORDER));
    }

    @Test
    void getNonExistingSequenceFragment() throws Exception {
        // Get the sequenceFragment
        restSequenceFragmentMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    void putExistingSequenceFragment() throws Exception {
        // Initialize the database
        sequenceFragmentRepository.save(sequenceFragment);

        int databaseSizeBeforeUpdate = sequenceFragmentRepository.findAll().size();

        // Update the sequenceFragment
        SequenceFragment updatedSequenceFragment = sequenceFragmentRepository.findById(sequenceFragment.getId()).get();
        updatedSequenceFragment.order(UPDATED_ORDER);

        restSequenceFragmentMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedSequenceFragment.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedSequenceFragment))
            )
            .andExpect(status().isOk());

        // Validate the SequenceFragment in the database
        List<SequenceFragment> sequenceFragmentList = sequenceFragmentRepository.findAll();
        assertThat(sequenceFragmentList).hasSize(databaseSizeBeforeUpdate);
        SequenceFragment testSequenceFragment = sequenceFragmentList.get(sequenceFragmentList.size() - 1);
        assertThat(testSequenceFragment.getOrder()).isEqualTo(UPDATED_ORDER);
    }

    @Test
    void putNonExistingSequenceFragment() throws Exception {
        int databaseSizeBeforeUpdate = sequenceFragmentRepository.findAll().size();
        sequenceFragment.setId(UUID.randomUUID().toString());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSequenceFragmentMockMvc
            .perform(
                put(ENTITY_API_URL_ID, sequenceFragment.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(sequenceFragment))
            )
            .andExpect(status().isBadRequest());

        // Validate the SequenceFragment in the database
        List<SequenceFragment> sequenceFragmentList = sequenceFragmentRepository.findAll();
        assertThat(sequenceFragmentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchSequenceFragment() throws Exception {
        int databaseSizeBeforeUpdate = sequenceFragmentRepository.findAll().size();
        sequenceFragment.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSequenceFragmentMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(sequenceFragment))
            )
            .andExpect(status().isBadRequest());

        // Validate the SequenceFragment in the database
        List<SequenceFragment> sequenceFragmentList = sequenceFragmentRepository.findAll();
        assertThat(sequenceFragmentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamSequenceFragment() throws Exception {
        int databaseSizeBeforeUpdate = sequenceFragmentRepository.findAll().size();
        sequenceFragment.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSequenceFragmentMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(sequenceFragment))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the SequenceFragment in the database
        List<SequenceFragment> sequenceFragmentList = sequenceFragmentRepository.findAll();
        assertThat(sequenceFragmentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdateSequenceFragmentWithPatch() throws Exception {
        // Initialize the database
        sequenceFragmentRepository.save(sequenceFragment);

        int databaseSizeBeforeUpdate = sequenceFragmentRepository.findAll().size();

        // Update the sequenceFragment using partial update
        SequenceFragment partialUpdatedSequenceFragment = new SequenceFragment();
        partialUpdatedSequenceFragment.setId(sequenceFragment.getId());

        restSequenceFragmentMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSequenceFragment.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedSequenceFragment))
            )
            .andExpect(status().isOk());

        // Validate the SequenceFragment in the database
        List<SequenceFragment> sequenceFragmentList = sequenceFragmentRepository.findAll();
        assertThat(sequenceFragmentList).hasSize(databaseSizeBeforeUpdate);
        SequenceFragment testSequenceFragment = sequenceFragmentList.get(sequenceFragmentList.size() - 1);
        assertThat(testSequenceFragment.getOrder()).isEqualTo(DEFAULT_ORDER);
    }

    @Test
    void fullUpdateSequenceFragmentWithPatch() throws Exception {
        // Initialize the database
        sequenceFragmentRepository.save(sequenceFragment);

        int databaseSizeBeforeUpdate = sequenceFragmentRepository.findAll().size();

        // Update the sequenceFragment using partial update
        SequenceFragment partialUpdatedSequenceFragment = new SequenceFragment();
        partialUpdatedSequenceFragment.setId(sequenceFragment.getId());

        partialUpdatedSequenceFragment.order(UPDATED_ORDER);

        restSequenceFragmentMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSequenceFragment.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedSequenceFragment))
            )
            .andExpect(status().isOk());

        // Validate the SequenceFragment in the database
        List<SequenceFragment> sequenceFragmentList = sequenceFragmentRepository.findAll();
        assertThat(sequenceFragmentList).hasSize(databaseSizeBeforeUpdate);
        SequenceFragment testSequenceFragment = sequenceFragmentList.get(sequenceFragmentList.size() - 1);
        assertThat(testSequenceFragment.getOrder()).isEqualTo(UPDATED_ORDER);
    }

    @Test
    void patchNonExistingSequenceFragment() throws Exception {
        int databaseSizeBeforeUpdate = sequenceFragmentRepository.findAll().size();
        sequenceFragment.setId(UUID.randomUUID().toString());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSequenceFragmentMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, sequenceFragment.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(sequenceFragment))
            )
            .andExpect(status().isBadRequest());

        // Validate the SequenceFragment in the database
        List<SequenceFragment> sequenceFragmentList = sequenceFragmentRepository.findAll();
        assertThat(sequenceFragmentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchSequenceFragment() throws Exception {
        int databaseSizeBeforeUpdate = sequenceFragmentRepository.findAll().size();
        sequenceFragment.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSequenceFragmentMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(sequenceFragment))
            )
            .andExpect(status().isBadRequest());

        // Validate the SequenceFragment in the database
        List<SequenceFragment> sequenceFragmentList = sequenceFragmentRepository.findAll();
        assertThat(sequenceFragmentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamSequenceFragment() throws Exception {
        int databaseSizeBeforeUpdate = sequenceFragmentRepository.findAll().size();
        sequenceFragment.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSequenceFragmentMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(sequenceFragment))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the SequenceFragment in the database
        List<SequenceFragment> sequenceFragmentList = sequenceFragmentRepository.findAll();
        assertThat(sequenceFragmentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void deleteSequenceFragment() throws Exception {
        // Initialize the database
        sequenceFragmentRepository.save(sequenceFragment);

        int databaseSizeBeforeDelete = sequenceFragmentRepository.findAll().size();

        // Delete the sequenceFragment
        restSequenceFragmentMockMvc
            .perform(delete(ENTITY_API_URL_ID, sequenceFragment.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<SequenceFragment> sequenceFragmentList = sequenceFragmentRepository.findAll();
        assertThat(sequenceFragmentList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
