package com.modis.edu.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.modis.edu.IntegrationTest;
import com.modis.edu.domain.SetOfFragment;
import com.modis.edu.repository.SetOfFragmentRepository;
import com.modis.edu.service.SetOfFragmentService;
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
 * Integration tests for the {@link SetOfFragmentResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class SetOfFragmentResourceIT {

    private static final Integer DEFAULT_ORDER = 1;
    private static final Integer UPDATED_ORDER = 2;

    private static final String ENTITY_API_URL = "/api/set-of-fragments";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private SetOfFragmentRepository setOfFragmentRepository;

    @Mock
    private SetOfFragmentRepository setOfFragmentRepositoryMock;

    @Mock
    private SetOfFragmentService setOfFragmentServiceMock;

    @Autowired
    private MockMvc restSetOfFragmentMockMvc;

    private SetOfFragment setOfFragment;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SetOfFragment createEntity() {
        SetOfFragment setOfFragment = new SetOfFragment().order(DEFAULT_ORDER);
        return setOfFragment;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SetOfFragment createUpdatedEntity() {
        SetOfFragment setOfFragment = new SetOfFragment().order(UPDATED_ORDER);
        return setOfFragment;
    }

    @BeforeEach
    public void initTest() {
        setOfFragmentRepository.deleteAll();
        setOfFragment = createEntity();
    }

    @Test
    void createSetOfFragment() throws Exception {
        int databaseSizeBeforeCreate = setOfFragmentRepository.findAll().size();
        // Create the SetOfFragment
        restSetOfFragmentMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(setOfFragment)))
            .andExpect(status().isCreated());

        // Validate the SetOfFragment in the database
        List<SetOfFragment> setOfFragmentList = setOfFragmentRepository.findAll();
        assertThat(setOfFragmentList).hasSize(databaseSizeBeforeCreate + 1);
        SetOfFragment testSetOfFragment = setOfFragmentList.get(setOfFragmentList.size() - 1);
        assertThat(testSetOfFragment.getOrder()).isEqualTo(DEFAULT_ORDER);
    }

    @Test
    void createSetOfFragmentWithExistingId() throws Exception {
        // Create the SetOfFragment with an existing ID
        setOfFragment.setId("existing_id");

        int databaseSizeBeforeCreate = setOfFragmentRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restSetOfFragmentMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(setOfFragment)))
            .andExpect(status().isBadRequest());

        // Validate the SetOfFragment in the database
        List<SetOfFragment> setOfFragmentList = setOfFragmentRepository.findAll();
        assertThat(setOfFragmentList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    void checkOrderIsRequired() throws Exception {
        int databaseSizeBeforeTest = setOfFragmentRepository.findAll().size();
        // set the field null
        setOfFragment.setOrder(null);

        // Create the SetOfFragment, which fails.

        restSetOfFragmentMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(setOfFragment)))
            .andExpect(status().isBadRequest());

        List<SetOfFragment> setOfFragmentList = setOfFragmentRepository.findAll();
        assertThat(setOfFragmentList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void getAllSetOfFragments() throws Exception {
        // Initialize the database
        setOfFragmentRepository.save(setOfFragment);

        // Get all the setOfFragmentList
        restSetOfFragmentMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(setOfFragment.getId())))
            .andExpect(jsonPath("$.[*].order").value(hasItem(DEFAULT_ORDER)));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllSetOfFragmentsWithEagerRelationshipsIsEnabled() throws Exception {
        when(setOfFragmentServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restSetOfFragmentMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(setOfFragmentServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllSetOfFragmentsWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(setOfFragmentServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restSetOfFragmentMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(setOfFragmentRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    void getSetOfFragment() throws Exception {
        // Initialize the database
        setOfFragmentRepository.save(setOfFragment);

        // Get the setOfFragment
        restSetOfFragmentMockMvc
            .perform(get(ENTITY_API_URL_ID, setOfFragment.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(setOfFragment.getId()))
            .andExpect(jsonPath("$.order").value(DEFAULT_ORDER));
    }

    @Test
    void getNonExistingSetOfFragment() throws Exception {
        // Get the setOfFragment
        restSetOfFragmentMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    void putExistingSetOfFragment() throws Exception {
        // Initialize the database
        setOfFragmentRepository.save(setOfFragment);

        int databaseSizeBeforeUpdate = setOfFragmentRepository.findAll().size();

        // Update the setOfFragment
        SetOfFragment updatedSetOfFragment = setOfFragmentRepository.findById(setOfFragment.getId()).get();
        updatedSetOfFragment.order(UPDATED_ORDER);

        restSetOfFragmentMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedSetOfFragment.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedSetOfFragment))
            )
            .andExpect(status().isOk());

        // Validate the SetOfFragment in the database
        List<SetOfFragment> setOfFragmentList = setOfFragmentRepository.findAll();
        assertThat(setOfFragmentList).hasSize(databaseSizeBeforeUpdate);
        SetOfFragment testSetOfFragment = setOfFragmentList.get(setOfFragmentList.size() - 1);
        assertThat(testSetOfFragment.getOrder()).isEqualTo(UPDATED_ORDER);
    }

    @Test
    void putNonExistingSetOfFragment() throws Exception {
        int databaseSizeBeforeUpdate = setOfFragmentRepository.findAll().size();
        setOfFragment.setId(UUID.randomUUID().toString());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSetOfFragmentMockMvc
            .perform(
                put(ENTITY_API_URL_ID, setOfFragment.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(setOfFragment))
            )
            .andExpect(status().isBadRequest());

        // Validate the SetOfFragment in the database
        List<SetOfFragment> setOfFragmentList = setOfFragmentRepository.findAll();
        assertThat(setOfFragmentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchSetOfFragment() throws Exception {
        int databaseSizeBeforeUpdate = setOfFragmentRepository.findAll().size();
        setOfFragment.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSetOfFragmentMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(setOfFragment))
            )
            .andExpect(status().isBadRequest());

        // Validate the SetOfFragment in the database
        List<SetOfFragment> setOfFragmentList = setOfFragmentRepository.findAll();
        assertThat(setOfFragmentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamSetOfFragment() throws Exception {
        int databaseSizeBeforeUpdate = setOfFragmentRepository.findAll().size();
        setOfFragment.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSetOfFragmentMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(setOfFragment)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the SetOfFragment in the database
        List<SetOfFragment> setOfFragmentList = setOfFragmentRepository.findAll();
        assertThat(setOfFragmentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdateSetOfFragmentWithPatch() throws Exception {
        // Initialize the database
        setOfFragmentRepository.save(setOfFragment);

        int databaseSizeBeforeUpdate = setOfFragmentRepository.findAll().size();

        // Update the setOfFragment using partial update
        SetOfFragment partialUpdatedSetOfFragment = new SetOfFragment();
        partialUpdatedSetOfFragment.setId(setOfFragment.getId());

        partialUpdatedSetOfFragment.order(UPDATED_ORDER);

        restSetOfFragmentMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSetOfFragment.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedSetOfFragment))
            )
            .andExpect(status().isOk());

        // Validate the SetOfFragment in the database
        List<SetOfFragment> setOfFragmentList = setOfFragmentRepository.findAll();
        assertThat(setOfFragmentList).hasSize(databaseSizeBeforeUpdate);
        SetOfFragment testSetOfFragment = setOfFragmentList.get(setOfFragmentList.size() - 1);
        assertThat(testSetOfFragment.getOrder()).isEqualTo(UPDATED_ORDER);
    }

    @Test
    void fullUpdateSetOfFragmentWithPatch() throws Exception {
        // Initialize the database
        setOfFragmentRepository.save(setOfFragment);

        int databaseSizeBeforeUpdate = setOfFragmentRepository.findAll().size();

        // Update the setOfFragment using partial update
        SetOfFragment partialUpdatedSetOfFragment = new SetOfFragment();
        partialUpdatedSetOfFragment.setId(setOfFragment.getId());

        partialUpdatedSetOfFragment.order(UPDATED_ORDER);

        restSetOfFragmentMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSetOfFragment.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedSetOfFragment))
            )
            .andExpect(status().isOk());

        // Validate the SetOfFragment in the database
        List<SetOfFragment> setOfFragmentList = setOfFragmentRepository.findAll();
        assertThat(setOfFragmentList).hasSize(databaseSizeBeforeUpdate);
        SetOfFragment testSetOfFragment = setOfFragmentList.get(setOfFragmentList.size() - 1);
        assertThat(testSetOfFragment.getOrder()).isEqualTo(UPDATED_ORDER);
    }

    @Test
    void patchNonExistingSetOfFragment() throws Exception {
        int databaseSizeBeforeUpdate = setOfFragmentRepository.findAll().size();
        setOfFragment.setId(UUID.randomUUID().toString());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSetOfFragmentMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, setOfFragment.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(setOfFragment))
            )
            .andExpect(status().isBadRequest());

        // Validate the SetOfFragment in the database
        List<SetOfFragment> setOfFragmentList = setOfFragmentRepository.findAll();
        assertThat(setOfFragmentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchSetOfFragment() throws Exception {
        int databaseSizeBeforeUpdate = setOfFragmentRepository.findAll().size();
        setOfFragment.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSetOfFragmentMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(setOfFragment))
            )
            .andExpect(status().isBadRequest());

        // Validate the SetOfFragment in the database
        List<SetOfFragment> setOfFragmentList = setOfFragmentRepository.findAll();
        assertThat(setOfFragmentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamSetOfFragment() throws Exception {
        int databaseSizeBeforeUpdate = setOfFragmentRepository.findAll().size();
        setOfFragment.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSetOfFragmentMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(setOfFragment))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the SetOfFragment in the database
        List<SetOfFragment> setOfFragmentList = setOfFragmentRepository.findAll();
        assertThat(setOfFragmentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void deleteSetOfFragment() throws Exception {
        // Initialize the database
        setOfFragmentRepository.save(setOfFragment);

        int databaseSizeBeforeDelete = setOfFragmentRepository.findAll().size();

        // Delete the setOfFragment
        restSetOfFragmentMockMvc
            .perform(delete(ENTITY_API_URL_ID, setOfFragment.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<SetOfFragment> setOfFragmentList = setOfFragmentRepository.findAll();
        assertThat(setOfFragmentList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
