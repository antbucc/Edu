package com.modis.edu.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.modis.edu.IntegrationTest;
import com.modis.edu.domain.Sequence;
import com.modis.edu.repository.SequenceRepository;
import com.modis.edu.service.SequenceService;
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
 * Integration tests for the {@link SequenceResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class SequenceResourceIT {

    private static final String DEFAULT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_TITLE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/sequences";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private SequenceRepository sequenceRepository;

    @Mock
    private SequenceRepository sequenceRepositoryMock;

    @Mock
    private SequenceService sequenceServiceMock;

    @Autowired
    private MockMvc restSequenceMockMvc;

    private Sequence sequence;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Sequence createEntity() {
        Sequence sequence = new Sequence().title(DEFAULT_TITLE);
        return sequence;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Sequence createUpdatedEntity() {
        Sequence sequence = new Sequence().title(UPDATED_TITLE);
        return sequence;
    }

    @BeforeEach
    public void initTest() {
        sequenceRepository.deleteAll();
        sequence = createEntity();
    }

    @Test
    void createSequence() throws Exception {
        int databaseSizeBeforeCreate = sequenceRepository.findAll().size();
        // Create the Sequence
        restSequenceMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(sequence)))
            .andExpect(status().isCreated());

        // Validate the Sequence in the database
        List<Sequence> sequenceList = sequenceRepository.findAll();
        assertThat(sequenceList).hasSize(databaseSizeBeforeCreate + 1);
        Sequence testSequence = sequenceList.get(sequenceList.size() - 1);
        assertThat(testSequence.getTitle()).isEqualTo(DEFAULT_TITLE);
    }

    @Test
    void createSequenceWithExistingId() throws Exception {
        // Create the Sequence with an existing ID
        sequence.setId("existing_id");

        int databaseSizeBeforeCreate = sequenceRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restSequenceMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(sequence)))
            .andExpect(status().isBadRequest());

        // Validate the Sequence in the database
        List<Sequence> sequenceList = sequenceRepository.findAll();
        assertThat(sequenceList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    void checkTitleIsRequired() throws Exception {
        int databaseSizeBeforeTest = sequenceRepository.findAll().size();
        // set the field null
        sequence.setTitle(null);

        // Create the Sequence, which fails.

        restSequenceMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(sequence)))
            .andExpect(status().isBadRequest());

        List<Sequence> sequenceList = sequenceRepository.findAll();
        assertThat(sequenceList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void getAllSequences() throws Exception {
        // Initialize the database
        sequenceRepository.save(sequence);

        // Get all the sequenceList
        restSequenceMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(sequence.getId())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE)));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllSequencesWithEagerRelationshipsIsEnabled() throws Exception {
        when(sequenceServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restSequenceMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(sequenceServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllSequencesWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(sequenceServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restSequenceMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(sequenceRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    void getSequence() throws Exception {
        // Initialize the database
        sequenceRepository.save(sequence);

        // Get the sequence
        restSequenceMockMvc
            .perform(get(ENTITY_API_URL_ID, sequence.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(sequence.getId()))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE));
    }

    @Test
    void getNonExistingSequence() throws Exception {
        // Get the sequence
        restSequenceMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    void putExistingSequence() throws Exception {
        // Initialize the database
        sequenceRepository.save(sequence);

        int databaseSizeBeforeUpdate = sequenceRepository.findAll().size();

        // Update the sequence
        Sequence updatedSequence = sequenceRepository.findById(sequence.getId()).get();
        updatedSequence.title(UPDATED_TITLE);

        restSequenceMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedSequence.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedSequence))
            )
            .andExpect(status().isOk());

        // Validate the Sequence in the database
        List<Sequence> sequenceList = sequenceRepository.findAll();
        assertThat(sequenceList).hasSize(databaseSizeBeforeUpdate);
        Sequence testSequence = sequenceList.get(sequenceList.size() - 1);
        assertThat(testSequence.getTitle()).isEqualTo(UPDATED_TITLE);
    }

    @Test
    void putNonExistingSequence() throws Exception {
        int databaseSizeBeforeUpdate = sequenceRepository.findAll().size();
        sequence.setId(UUID.randomUUID().toString());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSequenceMockMvc
            .perform(
                put(ENTITY_API_URL_ID, sequence.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(sequence))
            )
            .andExpect(status().isBadRequest());

        // Validate the Sequence in the database
        List<Sequence> sequenceList = sequenceRepository.findAll();
        assertThat(sequenceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchSequence() throws Exception {
        int databaseSizeBeforeUpdate = sequenceRepository.findAll().size();
        sequence.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSequenceMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(sequence))
            )
            .andExpect(status().isBadRequest());

        // Validate the Sequence in the database
        List<Sequence> sequenceList = sequenceRepository.findAll();
        assertThat(sequenceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamSequence() throws Exception {
        int databaseSizeBeforeUpdate = sequenceRepository.findAll().size();
        sequence.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSequenceMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(sequence)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Sequence in the database
        List<Sequence> sequenceList = sequenceRepository.findAll();
        assertThat(sequenceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdateSequenceWithPatch() throws Exception {
        // Initialize the database
        sequenceRepository.save(sequence);

        int databaseSizeBeforeUpdate = sequenceRepository.findAll().size();

        // Update the sequence using partial update
        Sequence partialUpdatedSequence = new Sequence();
        partialUpdatedSequence.setId(sequence.getId());

        partialUpdatedSequence.title(UPDATED_TITLE);

        restSequenceMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSequence.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedSequence))
            )
            .andExpect(status().isOk());

        // Validate the Sequence in the database
        List<Sequence> sequenceList = sequenceRepository.findAll();
        assertThat(sequenceList).hasSize(databaseSizeBeforeUpdate);
        Sequence testSequence = sequenceList.get(sequenceList.size() - 1);
        assertThat(testSequence.getTitle()).isEqualTo(UPDATED_TITLE);
    }

    @Test
    void fullUpdateSequenceWithPatch() throws Exception {
        // Initialize the database
        sequenceRepository.save(sequence);

        int databaseSizeBeforeUpdate = sequenceRepository.findAll().size();

        // Update the sequence using partial update
        Sequence partialUpdatedSequence = new Sequence();
        partialUpdatedSequence.setId(sequence.getId());

        partialUpdatedSequence.title(UPDATED_TITLE);

        restSequenceMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSequence.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedSequence))
            )
            .andExpect(status().isOk());

        // Validate the Sequence in the database
        List<Sequence> sequenceList = sequenceRepository.findAll();
        assertThat(sequenceList).hasSize(databaseSizeBeforeUpdate);
        Sequence testSequence = sequenceList.get(sequenceList.size() - 1);
        assertThat(testSequence.getTitle()).isEqualTo(UPDATED_TITLE);
    }

    @Test
    void patchNonExistingSequence() throws Exception {
        int databaseSizeBeforeUpdate = sequenceRepository.findAll().size();
        sequence.setId(UUID.randomUUID().toString());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSequenceMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, sequence.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(sequence))
            )
            .andExpect(status().isBadRequest());

        // Validate the Sequence in the database
        List<Sequence> sequenceList = sequenceRepository.findAll();
        assertThat(sequenceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchSequence() throws Exception {
        int databaseSizeBeforeUpdate = sequenceRepository.findAll().size();
        sequence.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSequenceMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(sequence))
            )
            .andExpect(status().isBadRequest());

        // Validate the Sequence in the database
        List<Sequence> sequenceList = sequenceRepository.findAll();
        assertThat(sequenceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamSequence() throws Exception {
        int databaseSizeBeforeUpdate = sequenceRepository.findAll().size();
        sequence.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSequenceMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(sequence)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Sequence in the database
        List<Sequence> sequenceList = sequenceRepository.findAll();
        assertThat(sequenceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void deleteSequence() throws Exception {
        // Initialize the database
        sequenceRepository.save(sequence);

        int databaseSizeBeforeDelete = sequenceRepository.findAll().size();

        // Delete the sequence
        restSequenceMockMvc
            .perform(delete(ENTITY_API_URL_ID, sequence.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Sequence> sequenceList = sequenceRepository.findAll();
        assertThat(sequenceList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
