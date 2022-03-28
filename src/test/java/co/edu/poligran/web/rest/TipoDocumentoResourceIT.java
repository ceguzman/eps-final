package co.edu.poligran.web.rest;

import co.edu.poligran.PruuebaApp;
import co.edu.poligran.domain.TipoDocumento;
import co.edu.poligran.repository.TipoDocumentoRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import co.edu.poligran.domain.enumeration.State;
/**
 * Integration tests for the {@link TipoDocumentoResource} REST controller.
 */
@SpringBootTest(classes = PruuebaApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class TipoDocumentoResourceIT {

    private static final String DEFAULT_INICIALES = "AAAAAAAAAA";
    private static final String UPDATED_INICIALES = "BBBBBBBBBB";

    private static final String DEFAULT_NOMBRE_DOCUMENTO = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE_DOCUMENTO = "BBBBBBBBBB";

    private static final State DEFAULT_ESTADO_TIPO_DOCUEMNTO = State.ACTIVE;
    private static final State UPDATED_ESTADO_TIPO_DOCUEMNTO = State.INACTIVE;

    @Autowired
    private TipoDocumentoRepository tipoDocumentoRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTipoDocumentoMockMvc;

    private TipoDocumento tipoDocumento;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TipoDocumento createEntity(EntityManager em) {
        TipoDocumento tipoDocumento = new TipoDocumento()
            .iniciales(DEFAULT_INICIALES)
            .nombreDocumento(DEFAULT_NOMBRE_DOCUMENTO)
            .estadoTipoDocuemnto(DEFAULT_ESTADO_TIPO_DOCUEMNTO);
        return tipoDocumento;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TipoDocumento createUpdatedEntity(EntityManager em) {
        TipoDocumento tipoDocumento = new TipoDocumento()
            .iniciales(UPDATED_INICIALES)
            .nombreDocumento(UPDATED_NOMBRE_DOCUMENTO)
            .estadoTipoDocuemnto(UPDATED_ESTADO_TIPO_DOCUEMNTO);
        return tipoDocumento;
    }

    @BeforeEach
    public void initTest() {
        tipoDocumento = createEntity(em);
    }

    @Test
    @Transactional
    public void createTipoDocumento() throws Exception {
        int databaseSizeBeforeCreate = tipoDocumentoRepository.findAll().size();
        // Create the TipoDocumento
        restTipoDocumentoMockMvc.perform(post("/api/tipo-documentos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tipoDocumento)))
            .andExpect(status().isCreated());

        // Validate the TipoDocumento in the database
        List<TipoDocumento> tipoDocumentoList = tipoDocumentoRepository.findAll();
        assertThat(tipoDocumentoList).hasSize(databaseSizeBeforeCreate + 1);
        TipoDocumento testTipoDocumento = tipoDocumentoList.get(tipoDocumentoList.size() - 1);
        assertThat(testTipoDocumento.getIniciales()).isEqualTo(DEFAULT_INICIALES);
        assertThat(testTipoDocumento.getNombreDocumento()).isEqualTo(DEFAULT_NOMBRE_DOCUMENTO);
        assertThat(testTipoDocumento.getEstadoTipoDocuemnto()).isEqualTo(DEFAULT_ESTADO_TIPO_DOCUEMNTO);
    }

    @Test
    @Transactional
    public void createTipoDocumentoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = tipoDocumentoRepository.findAll().size();

        // Create the TipoDocumento with an existing ID
        tipoDocumento.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTipoDocumentoMockMvc.perform(post("/api/tipo-documentos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tipoDocumento)))
            .andExpect(status().isBadRequest());

        // Validate the TipoDocumento in the database
        List<TipoDocumento> tipoDocumentoList = tipoDocumentoRepository.findAll();
        assertThat(tipoDocumentoList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkInicialesIsRequired() throws Exception {
        int databaseSizeBeforeTest = tipoDocumentoRepository.findAll().size();
        // set the field null
        tipoDocumento.setIniciales(null);

        // Create the TipoDocumento, which fails.


        restTipoDocumentoMockMvc.perform(post("/api/tipo-documentos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tipoDocumento)))
            .andExpect(status().isBadRequest());

        List<TipoDocumento> tipoDocumentoList = tipoDocumentoRepository.findAll();
        assertThat(tipoDocumentoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNombreDocumentoIsRequired() throws Exception {
        int databaseSizeBeforeTest = tipoDocumentoRepository.findAll().size();
        // set the field null
        tipoDocumento.setNombreDocumento(null);

        // Create the TipoDocumento, which fails.


        restTipoDocumentoMockMvc.perform(post("/api/tipo-documentos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tipoDocumento)))
            .andExpect(status().isBadRequest());

        List<TipoDocumento> tipoDocumentoList = tipoDocumentoRepository.findAll();
        assertThat(tipoDocumentoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEstadoTipoDocuemntoIsRequired() throws Exception {
        int databaseSizeBeforeTest = tipoDocumentoRepository.findAll().size();
        // set the field null
        tipoDocumento.setEstadoTipoDocuemnto(null);

        // Create the TipoDocumento, which fails.


        restTipoDocumentoMockMvc.perform(post("/api/tipo-documentos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tipoDocumento)))
            .andExpect(status().isBadRequest());

        List<TipoDocumento> tipoDocumentoList = tipoDocumentoRepository.findAll();
        assertThat(tipoDocumentoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTipoDocumentos() throws Exception {
        // Initialize the database
        tipoDocumentoRepository.saveAndFlush(tipoDocumento);

        // Get all the tipoDocumentoList
        restTipoDocumentoMockMvc.perform(get("/api/tipo-documentos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tipoDocumento.getId().intValue())))
            .andExpect(jsonPath("$.[*].iniciales").value(hasItem(DEFAULT_INICIALES)))
            .andExpect(jsonPath("$.[*].nombreDocumento").value(hasItem(DEFAULT_NOMBRE_DOCUMENTO)))
            .andExpect(jsonPath("$.[*].estadoTipoDocuemnto").value(hasItem(DEFAULT_ESTADO_TIPO_DOCUEMNTO.toString())));
    }
    
    @Test
    @Transactional
    public void getTipoDocumento() throws Exception {
        // Initialize the database
        tipoDocumentoRepository.saveAndFlush(tipoDocumento);

        // Get the tipoDocumento
        restTipoDocumentoMockMvc.perform(get("/api/tipo-documentos/{id}", tipoDocumento.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(tipoDocumento.getId().intValue()))
            .andExpect(jsonPath("$.iniciales").value(DEFAULT_INICIALES))
            .andExpect(jsonPath("$.nombreDocumento").value(DEFAULT_NOMBRE_DOCUMENTO))
            .andExpect(jsonPath("$.estadoTipoDocuemnto").value(DEFAULT_ESTADO_TIPO_DOCUEMNTO.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingTipoDocumento() throws Exception {
        // Get the tipoDocumento
        restTipoDocumentoMockMvc.perform(get("/api/tipo-documentos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTipoDocumento() throws Exception {
        // Initialize the database
        tipoDocumentoRepository.saveAndFlush(tipoDocumento);

        int databaseSizeBeforeUpdate = tipoDocumentoRepository.findAll().size();

        // Update the tipoDocumento
        TipoDocumento updatedTipoDocumento = tipoDocumentoRepository.findById(tipoDocumento.getId()).get();
        // Disconnect from session so that the updates on updatedTipoDocumento are not directly saved in db
        em.detach(updatedTipoDocumento);
        updatedTipoDocumento
            .iniciales(UPDATED_INICIALES)
            .nombreDocumento(UPDATED_NOMBRE_DOCUMENTO)
            .estadoTipoDocuemnto(UPDATED_ESTADO_TIPO_DOCUEMNTO);

        restTipoDocumentoMockMvc.perform(put("/api/tipo-documentos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedTipoDocumento)))
            .andExpect(status().isOk());

        // Validate the TipoDocumento in the database
        List<TipoDocumento> tipoDocumentoList = tipoDocumentoRepository.findAll();
        assertThat(tipoDocumentoList).hasSize(databaseSizeBeforeUpdate);
        TipoDocumento testTipoDocumento = tipoDocumentoList.get(tipoDocumentoList.size() - 1);
        assertThat(testTipoDocumento.getIniciales()).isEqualTo(UPDATED_INICIALES);
        assertThat(testTipoDocumento.getNombreDocumento()).isEqualTo(UPDATED_NOMBRE_DOCUMENTO);
        assertThat(testTipoDocumento.getEstadoTipoDocuemnto()).isEqualTo(UPDATED_ESTADO_TIPO_DOCUEMNTO);
    }

    @Test
    @Transactional
    public void updateNonExistingTipoDocumento() throws Exception {
        int databaseSizeBeforeUpdate = tipoDocumentoRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTipoDocumentoMockMvc.perform(put("/api/tipo-documentos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tipoDocumento)))
            .andExpect(status().isBadRequest());

        // Validate the TipoDocumento in the database
        List<TipoDocumento> tipoDocumentoList = tipoDocumentoRepository.findAll();
        assertThat(tipoDocumentoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteTipoDocumento() throws Exception {
        // Initialize the database
        tipoDocumentoRepository.saveAndFlush(tipoDocumento);

        int databaseSizeBeforeDelete = tipoDocumentoRepository.findAll().size();

        // Delete the tipoDocumento
        restTipoDocumentoMockMvc.perform(delete("/api/tipo-documentos/{id}", tipoDocumento.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TipoDocumento> tipoDocumentoList = tipoDocumentoRepository.findAll();
        assertThat(tipoDocumentoList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
