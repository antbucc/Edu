package com.modis.edu.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.modis.edu.IntegrationTest;
import com.modis.edu.domain.SetOf;
import com.modis.edu.repository.SetOfRepository;
import com.modis.edu.service.SetOfService;
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
 * Integration tests for the {@link SetOfResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class SetOfResourceIT {

    private static final String DEFAULT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_TITLE = "BBBBBBBBBB";

    private static final Integer DEFAULT_AT_LEAST = 1;
    private static final Integer UPDATED_AT_LEAST = 2;

    private static final Integer DEFAULT_NO_LESS = 1;
    private static final Integer UPDATED_NO_LESS = 2;

    private static final String ENTITY_API_URL = "/api/set-ofs";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private SetOfRepository setOfRepository;

    @Mock
    private SetOfRepository setOfRepositoryMock;

    @Mock
    private SetOfService setOfServiceMock;

    @Autowired
    private MockMvc restSetOfMockMvc;

    private SetOf setOf;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SetOf createEntity() {
        SetOf setOf = new SetOf().title(DEFAULT_TITLE).atLeast(DEFAULT_AT_LEAST).noLess(DEFAULT_NO_LESS);
        return setOf;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SetOf createUpdatedEntity() {
        SetOf setOf = new SetOf().title(UPDATED_TITLE).atLeast(UPDATED_AT_LEAST).noLess(UPDATED_NO_LESS);
        return setOf;
    }

    @BeforeEach
    public void initTest() {
        setOfRepository.deleteAll();
        setOf = createEntity();
    }

    @Test
    void createSetOf() throws Exception {
        int databaseSizeBeforeCreate = setOfRepository.findAll().size();
        // Create the SetOf
        restSetOfMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(setOf)))
            .andExpect(status().isCreated());

        // Validate the SetOf in the database
        List<SetOf> setOfList = setOfRepository.findAll();
        assertThat(setOfList).hasSize(databaseSizeBeforeCreate + 1);
        SetOf testSetOf = setOfList.get(setOfList.size() - 1);
        assertThat(testSetOf.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testSetOf.getAtLeast()).isEqualTo(DEFAULT_AT_LEAST);
        assertThat(testSetOf.getNoLess()).isEqualTo(DEFAULT_NO_LESS);
    }

    @Test
    void createSetOfWithExistingId() throws Exception {
        // Create the SetOf with an existing ID
        setOf.setId("existing_id");

        int databaseSizeBeforeCreate = setOfRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restSetOfMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(setOf)))
            .andExpect(status().isBadRequest());

        // Validate the SetOf in the database
        List<SetOf> setOfList = setOfRepository.findAll();
        assertThat(setOfList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    void checkTitleIsRequired() throws Exception {
        int databaseSizeBeforeTest = setOfRepository.findAll().size();
        // set the field null
        setOf.setTitle(null);

        // Create the SetOf, which fails.

        restSetOfMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(setOf)))
            .andExpect(status().isBadRequest());

        List<SetOf> setOfList = setOfRepository.findAll();
        assertThat(setOfList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void getAllSetOfs() throws Exception {
        // Initialize the database
        setOfRepository.save(setOf);

        // Get all the setOfList
        restSetOfMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(setOf.getId())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE)))
            .andExpect(jsonPath("$.[*].atLeast").value(hasItem(DEFAULT_AT_LEAST)))
            .andExpect(jsonPath("$.[*].noLess").value(hasItem(DEFAULT_NO_LESS)));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllSetOfsWithEagerRelationshipsIsEnabled() throws Exception {
        when(setOfServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restSetOfMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(setOfServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllSetOfsWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(setOfServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restSetOfMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(setOfRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    void getSetOf() throws Exception {
        // Initialize the database
        setOfRepository.save(setOf);

        // Get the setOf
        restSetOfMockMvc
            .perform(get(ENTITY_API_URL_ID, setOf.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(setOf.getId()))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE))
            .andExpect(jsonPath("$.atLeast").value(DEFAULT_AT_LEAST))
            .andExpect(jsonPath("$.noLess").value(DEFAULT_NO_LESS));
    }

    @Test
    void getNonExistingSetOf() throws Exception {
        // Get the setOf
        restSetOfMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    void putExistingSetOf() throws Exception {
        // Initialize the database
        setOfRepository.save(setOf);

        int databaseSizeBeforeUpdate = setOfRepository.findAll().size();

        // Update the setOf
        SetOf updatedSetOf = setOfRepository.findById(setOf.getId()).get();
        updatedSetOf.title(UPDATED_TITLE).atLeast(UPDATED_AT_LEAST).noLess(UPDATED_NO_LESS);

        restSetOfMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedSetOf.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedSetOf))
            )
            .andExpect(status().isOk());

        // Validate the SetOf in the database
        List<SetOf> setOfList = setOfRepository.findAll();
        assertThat(setOfList).hasSize(databaseSizeBeforeUpdate);
        SetOf testSetOf = setOfList.get(setOfList.size() - 1);
        assertThat(testSetOf.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testSetOf.getAtLeast()).isEqualTo(UPDATED_AT_LEAST);
        assertThat(testSetOf.getNoLess()).isEqualTo(UPDATED_NO_LESS);
    }

    @Test
    void putNonExistingSetOf() throws Exception {
        int databaseSizeBeforeUpdate = setOfRepository.findAll().size();
        setOf.setId(UUID.randomUUID().toString());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSetOfMockMvc
            .perform(
                put(ENTITY_API_URL_ID, setOf.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(setOf))
            )
            .andExpect(status().isBadRequest());

        // Validate the SetOf in the database
        List<SetOf> setOfList = setOfRepository.findAll();
        assertThat(setOfList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchSetOf() throws Exception {
        int databaseSizeBeforeUpdate = setOfRepository.findAll().size();
        setOf.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSetOfMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(setOf))
            )
            .andExpect(status().isBadRequest());

        // Validate the SetOf in the database
        List<SetOf> setOfList = setOfRepository.findAll();
        assertThat(setOfList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamSetOf() throws Exception {
        int databaseSizeBeforeUpdate = setOfRepository.findAll().size();
        setOf.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSetOfMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(setOf)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the SetOf in the database
        List<SetOf> setOfList = setOfRepository.findAll();
        assertThat(setOfList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdateSetOfWithPatch() throws Exception {
        // Initialize the database
        setOfRepository.save(setOf);

        int databaseSizeBeforeUpdate = setOfRepository.findAll().size();

        // Update the setOf using partial update
        SetOf partialUpdatedSetOf = new SetOf();
        partialUpdatedSetOf.setId(setOf.getId());

        partialUpdatedSetOf.title(UPDATED_TITLE).atLeast(UPDATED_AT_LEAST).noLess(UPDATED_NO_LESS);

        restSetOfMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSetOf.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedSetOf))
            )
            .andExpect(status().isOk());

        // Validate the SetOf in the database
        List<SetOf> setOfList = setOfRepository.findAll();
        assertThat(setOfList).hasSize(databaseSizeBeforeUpdate);
        SetOf testSetOf = setOfList.get(setOfList.size() - 1);
        assertThat(testSetOf.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testSetOf.getAtLeast()).isEqualTo(UPDATED_AT_LEAST);
        assertThat(testSetOf.getNoLess()).isEqualTo(UPDATED_NO_LESS);
    }

    @Test
    void fullUpdateSetOfWithPatch() throws Exception {
        // Initialize the database
        setOfRepository.save(setOf);

        int databaseSizeBeforeUpdate = setOfRepository.findAll().size();

        // Update the setOf using partial update
        SetOf partialUpdatedSetOf = new SetOf();
        partialUpdatedSetOf.setId(setOf.getId());

        partialUpdatedSetOf.title(UPDATED_TITLE).atLeast(UPDATED_AT_LEAST).noLess(UPDATED_NO_LESS);

        restSetOfMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSetOf.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedSetOf))
            )
            .andExpect(status().isOk());

        // Validate the SetOf in the database
        List<SetOf> setOfList = setOfRepository.findAll();
        assertThat(setOfList).hasSize(databaseSizeBeforeUpdate);
        SetOf testSetOf = setOfList.get(setOfList.size() - 1);
        assertThat(testSetOf.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testSetOf.getAtLeast()).isEqualTo(UPDATED_AT_LEAST);
        assertThat(testSetOf.getNoLess()).isEqualTo(UPDATED_NO_LESS);
    }

    @Test
    void patchNonExistingSetOf() throws Exception {
        int databaseSizeBeforeUpdate = setOfRepository.findAll().size();
        setOf.setId(UUID.randomUUID().toString());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSetOfMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, setOf.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(setOf))
            )
            .andExpect(status().isBadRequest());

        // Validate the SetOf in the database
        List<SetOf> setOfList = setOfRepository.findAll();
        assertThat(setOfList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchSetOf() throws Exception {
        int databaseSizeBeforeUpdate = setOfRepository.findAll().size();
        setOf.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSetOfMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(setOf))
            )
            .andExpect(status().isBadRequest());

        // Validate the SetOf in the database
        List<SetOf> setOfList = setOfRepository.findAll();
        assertThat(setOfList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamSetOf() throws Exception {
        int databaseSizeBeforeUpdate = setOfRepository.findAll().size();
        setOf.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSetOfMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(setOf)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the SetOf in the database
        List<SetOf> setOfList = setOfRepository.findAll();
        assertThat(setOfList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void deleteSetOf() throws Exception {
        // Initialize the database
        setOfRepository.save(setOf);

        int databaseSizeBeforeDelete = setOfRepository.findAll().size();

        // Delete the setOf
        restSetOfMockMvc
            .perform(delete(ENTITY_API_URL_ID, setOf.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<SetOf> setOfList = setOfRepository.findAll();
        assertThat(setOfList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
