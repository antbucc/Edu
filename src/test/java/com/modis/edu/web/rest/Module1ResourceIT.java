package com.modis.edu.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.modis.edu.IntegrationTest;
import com.modis.edu.domain.Module1;
import com.modis.edu.domain.enumeration.Level;
import com.modis.edu.repository.Module1Repository;
import com.modis.edu.service.Module1Service;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
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
 * Integration tests for the {@link Module1Resource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class Module1ResourceIT {

    private static final String DEFAULT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_TITLE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Instant DEFAULT_START_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_START_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_END_DATA = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_END_DATA = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Level DEFAULT_LEVEL = Level.BEGINNER;
    private static final Level UPDATED_LEVEL = Level.INTERMEDIATE;

    private static final String ENTITY_API_URL = "/api/module-1-s";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private Module1Repository module1Repository;

    @Mock
    private Module1Repository module1RepositoryMock;

    @Mock
    private Module1Service module1ServiceMock;

    @Autowired
    private MockMvc restModule1MockMvc;

    private Module1 module1;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Module1 createEntity() {
        Module1 module1 = new Module1()
            .title(DEFAULT_TITLE)
            .description(DEFAULT_DESCRIPTION)
            .startDate(DEFAULT_START_DATE)
            .endData(DEFAULT_END_DATA)
            .level(DEFAULT_LEVEL);
        return module1;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Module1 createUpdatedEntity() {
        Module1 module1 = new Module1()
            .title(UPDATED_TITLE)
            .description(UPDATED_DESCRIPTION)
            .startDate(UPDATED_START_DATE)
            .endData(UPDATED_END_DATA)
            .level(UPDATED_LEVEL);
        return module1;
    }

    @BeforeEach
    public void initTest() {
        module1Repository.deleteAll();
        module1 = createEntity();
    }

    @Test
    void createModule1() throws Exception {
        int databaseSizeBeforeCreate = module1Repository.findAll().size();
        // Create the Module1
        restModule1MockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(module1)))
            .andExpect(status().isCreated());

        // Validate the Module1 in the database
        List<Module1> module1List = module1Repository.findAll();
        assertThat(module1List).hasSize(databaseSizeBeforeCreate + 1);
        Module1 testModule1 = module1List.get(module1List.size() - 1);
        assertThat(testModule1.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testModule1.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testModule1.getStartDate()).isEqualTo(DEFAULT_START_DATE);
        assertThat(testModule1.getEndData()).isEqualTo(DEFAULT_END_DATA);
        assertThat(testModule1.getLevel()).isEqualTo(DEFAULT_LEVEL);
    }

    @Test
    void createModule1WithExistingId() throws Exception {
        // Create the Module1 with an existing ID
        module1.setId("existing_id");

        int databaseSizeBeforeCreate = module1Repository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restModule1MockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(module1)))
            .andExpect(status().isBadRequest());

        // Validate the Module1 in the database
        List<Module1> module1List = module1Repository.findAll();
        assertThat(module1List).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    void getAllModule1s() throws Exception {
        // Initialize the database
        module1Repository.save(module1);

        // Get all the module1List
        restModule1MockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(module1.getId())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].startDate").value(hasItem(DEFAULT_START_DATE.toString())))
            .andExpect(jsonPath("$.[*].endData").value(hasItem(DEFAULT_END_DATA.toString())))
            .andExpect(jsonPath("$.[*].level").value(hasItem(DEFAULT_LEVEL.toString())));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllModule1sWithEagerRelationshipsIsEnabled() throws Exception {
        when(module1ServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restModule1MockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(module1ServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllModule1sWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(module1ServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restModule1MockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(module1RepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    void getModule1() throws Exception {
        // Initialize the database
        module1Repository.save(module1);

        // Get the module1
        restModule1MockMvc
            .perform(get(ENTITY_API_URL_ID, module1.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(module1.getId()))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.startDate").value(DEFAULT_START_DATE.toString()))
            .andExpect(jsonPath("$.endData").value(DEFAULT_END_DATA.toString()))
            .andExpect(jsonPath("$.level").value(DEFAULT_LEVEL.toString()));
    }

    @Test
    void getNonExistingModule1() throws Exception {
        // Get the module1
        restModule1MockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    void putExistingModule1() throws Exception {
        // Initialize the database
        module1Repository.save(module1);

        int databaseSizeBeforeUpdate = module1Repository.findAll().size();

        // Update the module1
        Module1 updatedModule1 = module1Repository.findById(module1.getId()).get();
        updatedModule1
            .title(UPDATED_TITLE)
            .description(UPDATED_DESCRIPTION)
            .startDate(UPDATED_START_DATE)
            .endData(UPDATED_END_DATA)
            .level(UPDATED_LEVEL);

        restModule1MockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedModule1.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedModule1))
            )
            .andExpect(status().isOk());

        // Validate the Module1 in the database
        List<Module1> module1List = module1Repository.findAll();
        assertThat(module1List).hasSize(databaseSizeBeforeUpdate);
        Module1 testModule1 = module1List.get(module1List.size() - 1);
        assertThat(testModule1.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testModule1.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testModule1.getStartDate()).isEqualTo(UPDATED_START_DATE);
        assertThat(testModule1.getEndData()).isEqualTo(UPDATED_END_DATA);
        assertThat(testModule1.getLevel()).isEqualTo(UPDATED_LEVEL);
    }

    @Test
    void putNonExistingModule1() throws Exception {
        int databaseSizeBeforeUpdate = module1Repository.findAll().size();
        module1.setId(UUID.randomUUID().toString());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restModule1MockMvc
            .perform(
                put(ENTITY_API_URL_ID, module1.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(module1))
            )
            .andExpect(status().isBadRequest());

        // Validate the Module1 in the database
        List<Module1> module1List = module1Repository.findAll();
        assertThat(module1List).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchModule1() throws Exception {
        int databaseSizeBeforeUpdate = module1Repository.findAll().size();
        module1.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restModule1MockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(module1))
            )
            .andExpect(status().isBadRequest());

        // Validate the Module1 in the database
        List<Module1> module1List = module1Repository.findAll();
        assertThat(module1List).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamModule1() throws Exception {
        int databaseSizeBeforeUpdate = module1Repository.findAll().size();
        module1.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restModule1MockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(module1)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Module1 in the database
        List<Module1> module1List = module1Repository.findAll();
        assertThat(module1List).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdateModule1WithPatch() throws Exception {
        // Initialize the database
        module1Repository.save(module1);

        int databaseSizeBeforeUpdate = module1Repository.findAll().size();

        // Update the module1 using partial update
        Module1 partialUpdatedModule1 = new Module1();
        partialUpdatedModule1.setId(module1.getId());

        partialUpdatedModule1.title(UPDATED_TITLE).endData(UPDATED_END_DATA).level(UPDATED_LEVEL);

        restModule1MockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedModule1.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedModule1))
            )
            .andExpect(status().isOk());

        // Validate the Module1 in the database
        List<Module1> module1List = module1Repository.findAll();
        assertThat(module1List).hasSize(databaseSizeBeforeUpdate);
        Module1 testModule1 = module1List.get(module1List.size() - 1);
        assertThat(testModule1.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testModule1.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testModule1.getStartDate()).isEqualTo(DEFAULT_START_DATE);
        assertThat(testModule1.getEndData()).isEqualTo(UPDATED_END_DATA);
        assertThat(testModule1.getLevel()).isEqualTo(UPDATED_LEVEL);
    }

    @Test
    void fullUpdateModule1WithPatch() throws Exception {
        // Initialize the database
        module1Repository.save(module1);

        int databaseSizeBeforeUpdate = module1Repository.findAll().size();

        // Update the module1 using partial update
        Module1 partialUpdatedModule1 = new Module1();
        partialUpdatedModule1.setId(module1.getId());

        partialUpdatedModule1
            .title(UPDATED_TITLE)
            .description(UPDATED_DESCRIPTION)
            .startDate(UPDATED_START_DATE)
            .endData(UPDATED_END_DATA)
            .level(UPDATED_LEVEL);

        restModule1MockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedModule1.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedModule1))
            )
            .andExpect(status().isOk());

        // Validate the Module1 in the database
        List<Module1> module1List = module1Repository.findAll();
        assertThat(module1List).hasSize(databaseSizeBeforeUpdate);
        Module1 testModule1 = module1List.get(module1List.size() - 1);
        assertThat(testModule1.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testModule1.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testModule1.getStartDate()).isEqualTo(UPDATED_START_DATE);
        assertThat(testModule1.getEndData()).isEqualTo(UPDATED_END_DATA);
        assertThat(testModule1.getLevel()).isEqualTo(UPDATED_LEVEL);
    }

    @Test
    void patchNonExistingModule1() throws Exception {
        int databaseSizeBeforeUpdate = module1Repository.findAll().size();
        module1.setId(UUID.randomUUID().toString());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restModule1MockMvc
            .perform(
                patch(ENTITY_API_URL_ID, module1.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(module1))
            )
            .andExpect(status().isBadRequest());

        // Validate the Module1 in the database
        List<Module1> module1List = module1Repository.findAll();
        assertThat(module1List).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchModule1() throws Exception {
        int databaseSizeBeforeUpdate = module1Repository.findAll().size();
        module1.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restModule1MockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(module1))
            )
            .andExpect(status().isBadRequest());

        // Validate the Module1 in the database
        List<Module1> module1List = module1Repository.findAll();
        assertThat(module1List).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamModule1() throws Exception {
        int databaseSizeBeforeUpdate = module1Repository.findAll().size();
        module1.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restModule1MockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(module1)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Module1 in the database
        List<Module1> module1List = module1Repository.findAll();
        assertThat(module1List).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void deleteModule1() throws Exception {
        // Initialize the database
        module1Repository.save(module1);

        int databaseSizeBeforeDelete = module1Repository.findAll().size();

        // Delete the module1
        restModule1MockMvc
            .perform(delete(ENTITY_API_URL_ID, module1.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Module1> module1List = module1Repository.findAll();
        assertThat(module1List).hasSize(databaseSizeBeforeDelete - 1);
    }
}
