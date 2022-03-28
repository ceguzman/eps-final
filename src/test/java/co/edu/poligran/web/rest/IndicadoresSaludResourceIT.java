package co.edu.poligran.web.rest;

import co.edu.poligran.PruuebaApp;
import co.edu.poligran.domain.IndicadoresSalud;
import co.edu.poligran.domain.Diagnostico;
import co.edu.poligran.repository.IndicadoresSaludRepository;

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

/**
 * Integration tests for the {@link IndicadoresSaludResource} REST controller.
 */
@SpringBootTest(classes = PruuebaApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class IndicadoresSaludResourceIT {

    private static final String DEFAULT_FRECUENCIA_CARDIACA = "AAAAAAAAAA";
    private static final String UPDATED_FRECUENCIA_CARDIACA = "BBBBBBBBBB";

    private static final String DEFAULT_TENCION_ARTERIAL = "AAAAAAAAAA";
    private static final String UPDATED_TENCION_ARTERIAL = "BBBBBBBBBB";

    private static final String DEFAULT_SATURACION_OXIGENO = "AAAAAAAAAA";
    private static final String UPDATED_SATURACION_OXIGENO = "BBBBBBBBBB";

    private static final String DEFAULT_VACUNAS = "AAAAAAAAAA";
    private static final String UPDATED_VACUNAS = "BBBBBBBBBB";

    private static final String DEFAULT_DISTANCIA_RECORRIDA = "AAAAAAAAAA";
    private static final String UPDATED_DISTANCIA_RECORRIDA = "BBBBBBBBBB";

    @Autowired
    private IndicadoresSaludRepository indicadoresSaludRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restIndicadoresSaludMockMvc;

    private IndicadoresSalud indicadoresSalud;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static IndicadoresSalud createEntity(EntityManager em) {
        IndicadoresSalud indicadoresSalud = new IndicadoresSalud()
            .frecuenciaCardiaca(DEFAULT_FRECUENCIA_CARDIACA)
            .tencionArterial(DEFAULT_TENCION_ARTERIAL)
            .saturacionOxigeno(DEFAULT_SATURACION_OXIGENO)
            .vacunas(DEFAULT_VACUNAS)
            .distanciaRecorrida(DEFAULT_DISTANCIA_RECORRIDA);
        // Add required entity
        Diagnostico diagnostico;
        if (TestUtil.findAll(em, Diagnostico.class).isEmpty()) {
            diagnostico = DiagnosticoResourceIT.createEntity(em);
            em.persist(diagnostico);
            em.flush();
        } else {
            diagnostico = TestUtil.findAll(em, Diagnostico.class).get(0);
        }
        indicadoresSalud.setDiagnostico(diagnostico);
        return indicadoresSalud;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static IndicadoresSalud createUpdatedEntity(EntityManager em) {
        IndicadoresSalud indicadoresSalud = new IndicadoresSalud()
            .frecuenciaCardiaca(UPDATED_FRECUENCIA_CARDIACA)
            .tencionArterial(UPDATED_TENCION_ARTERIAL)
            .saturacionOxigeno(UPDATED_SATURACION_OXIGENO)
            .vacunas(UPDATED_VACUNAS)
            .distanciaRecorrida(UPDATED_DISTANCIA_RECORRIDA);
        // Add required entity
        Diagnostico diagnostico;
        if (TestUtil.findAll(em, Diagnostico.class).isEmpty()) {
            diagnostico = DiagnosticoResourceIT.createUpdatedEntity(em);
            em.persist(diagnostico);
            em.flush();
        } else {
            diagnostico = TestUtil.findAll(em, Diagnostico.class).get(0);
        }
        indicadoresSalud.setDiagnostico(diagnostico);
        return indicadoresSalud;
    }

    @BeforeEach
    public void initTest() {
        indicadoresSalud = createEntity(em);
    }

    @Test
    @Transactional
    public void createIndicadoresSalud() throws Exception {
        int databaseSizeBeforeCreate = indicadoresSaludRepository.findAll().size();
        // Create the IndicadoresSalud
        restIndicadoresSaludMockMvc.perform(post("/api/indicadores-saluds")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(indicadoresSalud)))
            .andExpect(status().isCreated());

        // Validate the IndicadoresSalud in the database
        List<IndicadoresSalud> indicadoresSaludList = indicadoresSaludRepository.findAll();
        assertThat(indicadoresSaludList).hasSize(databaseSizeBeforeCreate + 1);
        IndicadoresSalud testIndicadoresSalud = indicadoresSaludList.get(indicadoresSaludList.size() - 1);
        assertThat(testIndicadoresSalud.getFrecuenciaCardiaca()).isEqualTo(DEFAULT_FRECUENCIA_CARDIACA);
        assertThat(testIndicadoresSalud.getTencionArterial()).isEqualTo(DEFAULT_TENCION_ARTERIAL);
        assertThat(testIndicadoresSalud.getSaturacionOxigeno()).isEqualTo(DEFAULT_SATURACION_OXIGENO);
        assertThat(testIndicadoresSalud.getVacunas()).isEqualTo(DEFAULT_VACUNAS);
        assertThat(testIndicadoresSalud.getDistanciaRecorrida()).isEqualTo(DEFAULT_DISTANCIA_RECORRIDA);
    }

    @Test
    @Transactional
    public void createIndicadoresSaludWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = indicadoresSaludRepository.findAll().size();

        // Create the IndicadoresSalud with an existing ID
        indicadoresSalud.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restIndicadoresSaludMockMvc.perform(post("/api/indicadores-saluds")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(indicadoresSalud)))
            .andExpect(status().isBadRequest());

        // Validate the IndicadoresSalud in the database
        List<IndicadoresSalud> indicadoresSaludList = indicadoresSaludRepository.findAll();
        assertThat(indicadoresSaludList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkFrecuenciaCardiacaIsRequired() throws Exception {
        int databaseSizeBeforeTest = indicadoresSaludRepository.findAll().size();
        // set the field null
        indicadoresSalud.setFrecuenciaCardiaca(null);

        // Create the IndicadoresSalud, which fails.


        restIndicadoresSaludMockMvc.perform(post("/api/indicadores-saluds")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(indicadoresSalud)))
            .andExpect(status().isBadRequest());

        List<IndicadoresSalud> indicadoresSaludList = indicadoresSaludRepository.findAll();
        assertThat(indicadoresSaludList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTencionArterialIsRequired() throws Exception {
        int databaseSizeBeforeTest = indicadoresSaludRepository.findAll().size();
        // set the field null
        indicadoresSalud.setTencionArterial(null);

        // Create the IndicadoresSalud, which fails.


        restIndicadoresSaludMockMvc.perform(post("/api/indicadores-saluds")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(indicadoresSalud)))
            .andExpect(status().isBadRequest());

        List<IndicadoresSalud> indicadoresSaludList = indicadoresSaludRepository.findAll();
        assertThat(indicadoresSaludList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkSaturacionOxigenoIsRequired() throws Exception {
        int databaseSizeBeforeTest = indicadoresSaludRepository.findAll().size();
        // set the field null
        indicadoresSalud.setSaturacionOxigeno(null);

        // Create the IndicadoresSalud, which fails.


        restIndicadoresSaludMockMvc.perform(post("/api/indicadores-saluds")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(indicadoresSalud)))
            .andExpect(status().isBadRequest());

        List<IndicadoresSalud> indicadoresSaludList = indicadoresSaludRepository.findAll();
        assertThat(indicadoresSaludList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkVacunasIsRequired() throws Exception {
        int databaseSizeBeforeTest = indicadoresSaludRepository.findAll().size();
        // set the field null
        indicadoresSalud.setVacunas(null);

        // Create the IndicadoresSalud, which fails.


        restIndicadoresSaludMockMvc.perform(post("/api/indicadores-saluds")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(indicadoresSalud)))
            .andExpect(status().isBadRequest());

        List<IndicadoresSalud> indicadoresSaludList = indicadoresSaludRepository.findAll();
        assertThat(indicadoresSaludList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDistanciaRecorridaIsRequired() throws Exception {
        int databaseSizeBeforeTest = indicadoresSaludRepository.findAll().size();
        // set the field null
        indicadoresSalud.setDistanciaRecorrida(null);

        // Create the IndicadoresSalud, which fails.


        restIndicadoresSaludMockMvc.perform(post("/api/indicadores-saluds")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(indicadoresSalud)))
            .andExpect(status().isBadRequest());

        List<IndicadoresSalud> indicadoresSaludList = indicadoresSaludRepository.findAll();
        assertThat(indicadoresSaludList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllIndicadoresSaluds() throws Exception {
        // Initialize the database
        indicadoresSaludRepository.saveAndFlush(indicadoresSalud);

        // Get all the indicadoresSaludList
        restIndicadoresSaludMockMvc.perform(get("/api/indicadores-saluds?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(indicadoresSalud.getId().intValue())))
            .andExpect(jsonPath("$.[*].frecuenciaCardiaca").value(hasItem(DEFAULT_FRECUENCIA_CARDIACA)))
            .andExpect(jsonPath("$.[*].tencionArterial").value(hasItem(DEFAULT_TENCION_ARTERIAL)))
            .andExpect(jsonPath("$.[*].saturacionOxigeno").value(hasItem(DEFAULT_SATURACION_OXIGENO)))
            .andExpect(jsonPath("$.[*].vacunas").value(hasItem(DEFAULT_VACUNAS)))
            .andExpect(jsonPath("$.[*].distanciaRecorrida").value(hasItem(DEFAULT_DISTANCIA_RECORRIDA)));
    }
    
    @Test
    @Transactional
    public void getIndicadoresSalud() throws Exception {
        // Initialize the database
        indicadoresSaludRepository.saveAndFlush(indicadoresSalud);

        // Get the indicadoresSalud
        restIndicadoresSaludMockMvc.perform(get("/api/indicadores-saluds/{id}", indicadoresSalud.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(indicadoresSalud.getId().intValue()))
            .andExpect(jsonPath("$.frecuenciaCardiaca").value(DEFAULT_FRECUENCIA_CARDIACA))
            .andExpect(jsonPath("$.tencionArterial").value(DEFAULT_TENCION_ARTERIAL))
            .andExpect(jsonPath("$.saturacionOxigeno").value(DEFAULT_SATURACION_OXIGENO))
            .andExpect(jsonPath("$.vacunas").value(DEFAULT_VACUNAS))
            .andExpect(jsonPath("$.distanciaRecorrida").value(DEFAULT_DISTANCIA_RECORRIDA));
    }
    @Test
    @Transactional
    public void getNonExistingIndicadoresSalud() throws Exception {
        // Get the indicadoresSalud
        restIndicadoresSaludMockMvc.perform(get("/api/indicadores-saluds/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateIndicadoresSalud() throws Exception {
        // Initialize the database
        indicadoresSaludRepository.saveAndFlush(indicadoresSalud);

        int databaseSizeBeforeUpdate = indicadoresSaludRepository.findAll().size();

        // Update the indicadoresSalud
        IndicadoresSalud updatedIndicadoresSalud = indicadoresSaludRepository.findById(indicadoresSalud.getId()).get();
        // Disconnect from session so that the updates on updatedIndicadoresSalud are not directly saved in db
        em.detach(updatedIndicadoresSalud);
        updatedIndicadoresSalud
            .frecuenciaCardiaca(UPDATED_FRECUENCIA_CARDIACA)
            .tencionArterial(UPDATED_TENCION_ARTERIAL)
            .saturacionOxigeno(UPDATED_SATURACION_OXIGENO)
            .vacunas(UPDATED_VACUNAS)
            .distanciaRecorrida(UPDATED_DISTANCIA_RECORRIDA);

        restIndicadoresSaludMockMvc.perform(put("/api/indicadores-saluds")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedIndicadoresSalud)))
            .andExpect(status().isOk());

        // Validate the IndicadoresSalud in the database
        List<IndicadoresSalud> indicadoresSaludList = indicadoresSaludRepository.findAll();
        assertThat(indicadoresSaludList).hasSize(databaseSizeBeforeUpdate);
        IndicadoresSalud testIndicadoresSalud = indicadoresSaludList.get(indicadoresSaludList.size() - 1);
        assertThat(testIndicadoresSalud.getFrecuenciaCardiaca()).isEqualTo(UPDATED_FRECUENCIA_CARDIACA);
        assertThat(testIndicadoresSalud.getTencionArterial()).isEqualTo(UPDATED_TENCION_ARTERIAL);
        assertThat(testIndicadoresSalud.getSaturacionOxigeno()).isEqualTo(UPDATED_SATURACION_OXIGENO);
        assertThat(testIndicadoresSalud.getVacunas()).isEqualTo(UPDATED_VACUNAS);
        assertThat(testIndicadoresSalud.getDistanciaRecorrida()).isEqualTo(UPDATED_DISTANCIA_RECORRIDA);
    }

    @Test
    @Transactional
    public void updateNonExistingIndicadoresSalud() throws Exception {
        int databaseSizeBeforeUpdate = indicadoresSaludRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restIndicadoresSaludMockMvc.perform(put("/api/indicadores-saluds")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(indicadoresSalud)))
            .andExpect(status().isBadRequest());

        // Validate the IndicadoresSalud in the database
        List<IndicadoresSalud> indicadoresSaludList = indicadoresSaludRepository.findAll();
        assertThat(indicadoresSaludList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteIndicadoresSalud() throws Exception {
        // Initialize the database
        indicadoresSaludRepository.saveAndFlush(indicadoresSalud);

        int databaseSizeBeforeDelete = indicadoresSaludRepository.findAll().size();

        // Delete the indicadoresSalud
        restIndicadoresSaludMockMvc.perform(delete("/api/indicadores-saluds/{id}", indicadoresSalud.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<IndicadoresSalud> indicadoresSaludList = indicadoresSaludRepository.findAll();
        assertThat(indicadoresSaludList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
