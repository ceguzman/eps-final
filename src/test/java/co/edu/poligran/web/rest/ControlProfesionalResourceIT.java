package co.edu.poligran.web.rest;

import co.edu.poligran.PruuebaApp;
import co.edu.poligran.domain.ControlProfesional;
import co.edu.poligran.domain.IndicadoresSalud;
import co.edu.poligran.repository.ControlProfesionalRepository;

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
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link ControlProfesionalResource} REST controller.
 */
@SpringBootTest(classes = PruuebaApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class ControlProfesionalResourceIT {

    private static final String DEFAULT_TIPO_MEDICO = "AAAAAAAAAA";
    private static final String UPDATED_TIPO_MEDICO = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_FECHA = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FECHA = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_NOMBRE_MEDICO = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE_MEDICO = "BBBBBBBBBB";

    private static final String DEFAULT_OBSERVACIONES = "AAAAAAAAAA";
    private static final String UPDATED_OBSERVACIONES = "BBBBBBBBBB";

    @Autowired
    private ControlProfesionalRepository controlProfesionalRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restControlProfesionalMockMvc;

    private ControlProfesional controlProfesional;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ControlProfesional createEntity(EntityManager em) {
        ControlProfesional controlProfesional = new ControlProfesional()
            .tipoMedico(DEFAULT_TIPO_MEDICO)
            .fecha(DEFAULT_FECHA)
            .nombreMedico(DEFAULT_NOMBRE_MEDICO)
            .observaciones(DEFAULT_OBSERVACIONES);
        // Add required entity
        IndicadoresSalud indicadoresSalud;
        if (TestUtil.findAll(em, IndicadoresSalud.class).isEmpty()) {
            indicadoresSalud = IndicadoresSaludResourceIT.createEntity(em);
            em.persist(indicadoresSalud);
            em.flush();
        } else {
            indicadoresSalud = TestUtil.findAll(em, IndicadoresSalud.class).get(0);
        }
        controlProfesional.setIndicadorSalud(indicadoresSalud);
        return controlProfesional;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ControlProfesional createUpdatedEntity(EntityManager em) {
        ControlProfesional controlProfesional = new ControlProfesional()
            .tipoMedico(UPDATED_TIPO_MEDICO)
            .fecha(UPDATED_FECHA)
            .nombreMedico(UPDATED_NOMBRE_MEDICO)
            .observaciones(UPDATED_OBSERVACIONES);
        // Add required entity
        IndicadoresSalud indicadoresSalud;
        if (TestUtil.findAll(em, IndicadoresSalud.class).isEmpty()) {
            indicadoresSalud = IndicadoresSaludResourceIT.createUpdatedEntity(em);
            em.persist(indicadoresSalud);
            em.flush();
        } else {
            indicadoresSalud = TestUtil.findAll(em, IndicadoresSalud.class).get(0);
        }
        controlProfesional.setIndicadorSalud(indicadoresSalud);
        return controlProfesional;
    }

    @BeforeEach
    public void initTest() {
        controlProfesional = createEntity(em);
    }

    @Test
    @Transactional
    public void createControlProfesional() throws Exception {
        int databaseSizeBeforeCreate = controlProfesionalRepository.findAll().size();
        // Create the ControlProfesional
        restControlProfesionalMockMvc.perform(post("/api/control-profesionals")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(controlProfesional)))
            .andExpect(status().isCreated());

        // Validate the ControlProfesional in the database
        List<ControlProfesional> controlProfesionalList = controlProfesionalRepository.findAll();
        assertThat(controlProfesionalList).hasSize(databaseSizeBeforeCreate + 1);
        ControlProfesional testControlProfesional = controlProfesionalList.get(controlProfesionalList.size() - 1);
        assertThat(testControlProfesional.getTipoMedico()).isEqualTo(DEFAULT_TIPO_MEDICO);
        assertThat(testControlProfesional.getFecha()).isEqualTo(DEFAULT_FECHA);
        assertThat(testControlProfesional.getNombreMedico()).isEqualTo(DEFAULT_NOMBRE_MEDICO);
        assertThat(testControlProfesional.getObservaciones()).isEqualTo(DEFAULT_OBSERVACIONES);
    }

    @Test
    @Transactional
    public void createControlProfesionalWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = controlProfesionalRepository.findAll().size();

        // Create the ControlProfesional with an existing ID
        controlProfesional.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restControlProfesionalMockMvc.perform(post("/api/control-profesionals")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(controlProfesional)))
            .andExpect(status().isBadRequest());

        // Validate the ControlProfesional in the database
        List<ControlProfesional> controlProfesionalList = controlProfesionalRepository.findAll();
        assertThat(controlProfesionalList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkTipoMedicoIsRequired() throws Exception {
        int databaseSizeBeforeTest = controlProfesionalRepository.findAll().size();
        // set the field null
        controlProfesional.setTipoMedico(null);

        // Create the ControlProfesional, which fails.


        restControlProfesionalMockMvc.perform(post("/api/control-profesionals")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(controlProfesional)))
            .andExpect(status().isBadRequest());

        List<ControlProfesional> controlProfesionalList = controlProfesionalRepository.findAll();
        assertThat(controlProfesionalList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkFechaIsRequired() throws Exception {
        int databaseSizeBeforeTest = controlProfesionalRepository.findAll().size();
        // set the field null
        controlProfesional.setFecha(null);

        // Create the ControlProfesional, which fails.


        restControlProfesionalMockMvc.perform(post("/api/control-profesionals")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(controlProfesional)))
            .andExpect(status().isBadRequest());

        List<ControlProfesional> controlProfesionalList = controlProfesionalRepository.findAll();
        assertThat(controlProfesionalList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNombreMedicoIsRequired() throws Exception {
        int databaseSizeBeforeTest = controlProfesionalRepository.findAll().size();
        // set the field null
        controlProfesional.setNombreMedico(null);

        // Create the ControlProfesional, which fails.


        restControlProfesionalMockMvc.perform(post("/api/control-profesionals")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(controlProfesional)))
            .andExpect(status().isBadRequest());

        List<ControlProfesional> controlProfesionalList = controlProfesionalRepository.findAll();
        assertThat(controlProfesionalList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkObservacionesIsRequired() throws Exception {
        int databaseSizeBeforeTest = controlProfesionalRepository.findAll().size();
        // set the field null
        controlProfesional.setObservaciones(null);

        // Create the ControlProfesional, which fails.


        restControlProfesionalMockMvc.perform(post("/api/control-profesionals")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(controlProfesional)))
            .andExpect(status().isBadRequest());

        List<ControlProfesional> controlProfesionalList = controlProfesionalRepository.findAll();
        assertThat(controlProfesionalList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllControlProfesionals() throws Exception {
        // Initialize the database
        controlProfesionalRepository.saveAndFlush(controlProfesional);

        // Get all the controlProfesionalList
        restControlProfesionalMockMvc.perform(get("/api/control-profesionals?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(controlProfesional.getId().intValue())))
            .andExpect(jsonPath("$.[*].tipoMedico").value(hasItem(DEFAULT_TIPO_MEDICO)))
            .andExpect(jsonPath("$.[*].fecha").value(hasItem(DEFAULT_FECHA.toString())))
            .andExpect(jsonPath("$.[*].nombreMedico").value(hasItem(DEFAULT_NOMBRE_MEDICO)))
            .andExpect(jsonPath("$.[*].observaciones").value(hasItem(DEFAULT_OBSERVACIONES)));
    }
    
    @Test
    @Transactional
    public void getControlProfesional() throws Exception {
        // Initialize the database
        controlProfesionalRepository.saveAndFlush(controlProfesional);

        // Get the controlProfesional
        restControlProfesionalMockMvc.perform(get("/api/control-profesionals/{id}", controlProfesional.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(controlProfesional.getId().intValue()))
            .andExpect(jsonPath("$.tipoMedico").value(DEFAULT_TIPO_MEDICO))
            .andExpect(jsonPath("$.fecha").value(DEFAULT_FECHA.toString()))
            .andExpect(jsonPath("$.nombreMedico").value(DEFAULT_NOMBRE_MEDICO))
            .andExpect(jsonPath("$.observaciones").value(DEFAULT_OBSERVACIONES));
    }
    @Test
    @Transactional
    public void getNonExistingControlProfesional() throws Exception {
        // Get the controlProfesional
        restControlProfesionalMockMvc.perform(get("/api/control-profesionals/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateControlProfesional() throws Exception {
        // Initialize the database
        controlProfesionalRepository.saveAndFlush(controlProfesional);

        int databaseSizeBeforeUpdate = controlProfesionalRepository.findAll().size();

        // Update the controlProfesional
        ControlProfesional updatedControlProfesional = controlProfesionalRepository.findById(controlProfesional.getId()).get();
        // Disconnect from session so that the updates on updatedControlProfesional are not directly saved in db
        em.detach(updatedControlProfesional);
        updatedControlProfesional
            .tipoMedico(UPDATED_TIPO_MEDICO)
            .fecha(UPDATED_FECHA)
            .nombreMedico(UPDATED_NOMBRE_MEDICO)
            .observaciones(UPDATED_OBSERVACIONES);

        restControlProfesionalMockMvc.perform(put("/api/control-profesionals")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedControlProfesional)))
            .andExpect(status().isOk());

        // Validate the ControlProfesional in the database
        List<ControlProfesional> controlProfesionalList = controlProfesionalRepository.findAll();
        assertThat(controlProfesionalList).hasSize(databaseSizeBeforeUpdate);
        ControlProfesional testControlProfesional = controlProfesionalList.get(controlProfesionalList.size() - 1);
        assertThat(testControlProfesional.getTipoMedico()).isEqualTo(UPDATED_TIPO_MEDICO);
        assertThat(testControlProfesional.getFecha()).isEqualTo(UPDATED_FECHA);
        assertThat(testControlProfesional.getNombreMedico()).isEqualTo(UPDATED_NOMBRE_MEDICO);
        assertThat(testControlProfesional.getObservaciones()).isEqualTo(UPDATED_OBSERVACIONES);
    }

    @Test
    @Transactional
    public void updateNonExistingControlProfesional() throws Exception {
        int databaseSizeBeforeUpdate = controlProfesionalRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restControlProfesionalMockMvc.perform(put("/api/control-profesionals")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(controlProfesional)))
            .andExpect(status().isBadRequest());

        // Validate the ControlProfesional in the database
        List<ControlProfesional> controlProfesionalList = controlProfesionalRepository.findAll();
        assertThat(controlProfesionalList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteControlProfesional() throws Exception {
        // Initialize the database
        controlProfesionalRepository.saveAndFlush(controlProfesional);

        int databaseSizeBeforeDelete = controlProfesionalRepository.findAll().size();

        // Delete the controlProfesional
        restControlProfesionalMockMvc.perform(delete("/api/control-profesionals/{id}", controlProfesional.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ControlProfesional> controlProfesionalList = controlProfesionalRepository.findAll();
        assertThat(controlProfesionalList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
