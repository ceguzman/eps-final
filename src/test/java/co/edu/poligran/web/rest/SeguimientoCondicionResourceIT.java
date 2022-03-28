package co.edu.poligran.web.rest;

import co.edu.poligran.PruuebaApp;
import co.edu.poligran.domain.SeguimientoCondicion;
import co.edu.poligran.domain.Diagnostico;
import co.edu.poligran.repository.SeguimientoCondicionRepository;

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
 * Integration tests for the {@link SeguimientoCondicionResource} REST controller.
 */
@SpringBootTest(classes = PruuebaApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class SeguimientoCondicionResourceIT {

    private static final String DEFAULT_ESTADO_CONDICION = "AAAAAAAAAA";
    private static final String UPDATED_ESTADO_CONDICION = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_FECHA = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FECHA = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_DIAGNOSTICO = "AAAAAAAAAA";
    private static final String UPDATED_DIAGNOSTICO = "BBBBBBBBBB";

    private static final String DEFAULT_EVOLUCION_TRATAMIENTO = "AAAAAAAAAA";
    private static final String UPDATED_EVOLUCION_TRATAMIENTO = "BBBBBBBBBB";

    @Autowired
    private SeguimientoCondicionRepository seguimientoCondicionRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSeguimientoCondicionMockMvc;

    private SeguimientoCondicion seguimientoCondicion;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SeguimientoCondicion createEntity(EntityManager em) {
        SeguimientoCondicion seguimientoCondicion = new SeguimientoCondicion()
            .estadoCondicion(DEFAULT_ESTADO_CONDICION)
            .fecha(DEFAULT_FECHA)
            .diagnostico(DEFAULT_DIAGNOSTICO)
            .evolucionTratamiento(DEFAULT_EVOLUCION_TRATAMIENTO);
        // Add required entity
        Diagnostico diagnostico;
        if (TestUtil.findAll(em, Diagnostico.class).isEmpty()) {
            diagnostico = DiagnosticoResourceIT.createEntity(em);
            em.persist(diagnostico);
            em.flush();
        } else {
            diagnostico = TestUtil.findAll(em, Diagnostico.class).get(0);
        }
        seguimientoCondicion.setDiagnosticos(diagnostico);
        return seguimientoCondicion;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SeguimientoCondicion createUpdatedEntity(EntityManager em) {
        SeguimientoCondicion seguimientoCondicion = new SeguimientoCondicion()
            .estadoCondicion(UPDATED_ESTADO_CONDICION)
            .fecha(UPDATED_FECHA)
            .diagnostico(UPDATED_DIAGNOSTICO)
            .evolucionTratamiento(UPDATED_EVOLUCION_TRATAMIENTO);
        // Add required entity
        Diagnostico diagnostico;
        if (TestUtil.findAll(em, Diagnostico.class).isEmpty()) {
            diagnostico = DiagnosticoResourceIT.createUpdatedEntity(em);
            em.persist(diagnostico);
            em.flush();
        } else {
            diagnostico = TestUtil.findAll(em, Diagnostico.class).get(0);
        }
        seguimientoCondicion.setDiagnosticos(diagnostico);
        return seguimientoCondicion;
    }

    @BeforeEach
    public void initTest() {
        seguimientoCondicion = createEntity(em);
    }

    @Test
    @Transactional
    public void createSeguimientoCondicion() throws Exception {
        int databaseSizeBeforeCreate = seguimientoCondicionRepository.findAll().size();
        // Create the SeguimientoCondicion
        restSeguimientoCondicionMockMvc.perform(post("/api/seguimiento-condicions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(seguimientoCondicion)))
            .andExpect(status().isCreated());

        // Validate the SeguimientoCondicion in the database
        List<SeguimientoCondicion> seguimientoCondicionList = seguimientoCondicionRepository.findAll();
        assertThat(seguimientoCondicionList).hasSize(databaseSizeBeforeCreate + 1);
        SeguimientoCondicion testSeguimientoCondicion = seguimientoCondicionList.get(seguimientoCondicionList.size() - 1);
        assertThat(testSeguimientoCondicion.getEstadoCondicion()).isEqualTo(DEFAULT_ESTADO_CONDICION);
        assertThat(testSeguimientoCondicion.getFecha()).isEqualTo(DEFAULT_FECHA);
        assertThat(testSeguimientoCondicion.getDiagnostico()).isEqualTo(DEFAULT_DIAGNOSTICO);
        assertThat(testSeguimientoCondicion.getEvolucionTratamiento()).isEqualTo(DEFAULT_EVOLUCION_TRATAMIENTO);
    }

    @Test
    @Transactional
    public void createSeguimientoCondicionWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = seguimientoCondicionRepository.findAll().size();

        // Create the SeguimientoCondicion with an existing ID
        seguimientoCondicion.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSeguimientoCondicionMockMvc.perform(post("/api/seguimiento-condicions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(seguimientoCondicion)))
            .andExpect(status().isBadRequest());

        // Validate the SeguimientoCondicion in the database
        List<SeguimientoCondicion> seguimientoCondicionList = seguimientoCondicionRepository.findAll();
        assertThat(seguimientoCondicionList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkEstadoCondicionIsRequired() throws Exception {
        int databaseSizeBeforeTest = seguimientoCondicionRepository.findAll().size();
        // set the field null
        seguimientoCondicion.setEstadoCondicion(null);

        // Create the SeguimientoCondicion, which fails.


        restSeguimientoCondicionMockMvc.perform(post("/api/seguimiento-condicions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(seguimientoCondicion)))
            .andExpect(status().isBadRequest());

        List<SeguimientoCondicion> seguimientoCondicionList = seguimientoCondicionRepository.findAll();
        assertThat(seguimientoCondicionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkFechaIsRequired() throws Exception {
        int databaseSizeBeforeTest = seguimientoCondicionRepository.findAll().size();
        // set the field null
        seguimientoCondicion.setFecha(null);

        // Create the SeguimientoCondicion, which fails.


        restSeguimientoCondicionMockMvc.perform(post("/api/seguimiento-condicions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(seguimientoCondicion)))
            .andExpect(status().isBadRequest());

        List<SeguimientoCondicion> seguimientoCondicionList = seguimientoCondicionRepository.findAll();
        assertThat(seguimientoCondicionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDiagnosticoIsRequired() throws Exception {
        int databaseSizeBeforeTest = seguimientoCondicionRepository.findAll().size();
        // set the field null
        seguimientoCondicion.setDiagnostico(null);

        // Create the SeguimientoCondicion, which fails.


        restSeguimientoCondicionMockMvc.perform(post("/api/seguimiento-condicions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(seguimientoCondicion)))
            .andExpect(status().isBadRequest());

        List<SeguimientoCondicion> seguimientoCondicionList = seguimientoCondicionRepository.findAll();
        assertThat(seguimientoCondicionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEvolucionTratamientoIsRequired() throws Exception {
        int databaseSizeBeforeTest = seguimientoCondicionRepository.findAll().size();
        // set the field null
        seguimientoCondicion.setEvolucionTratamiento(null);

        // Create the SeguimientoCondicion, which fails.


        restSeguimientoCondicionMockMvc.perform(post("/api/seguimiento-condicions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(seguimientoCondicion)))
            .andExpect(status().isBadRequest());

        List<SeguimientoCondicion> seguimientoCondicionList = seguimientoCondicionRepository.findAll();
        assertThat(seguimientoCondicionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllSeguimientoCondicions() throws Exception {
        // Initialize the database
        seguimientoCondicionRepository.saveAndFlush(seguimientoCondicion);

        // Get all the seguimientoCondicionList
        restSeguimientoCondicionMockMvc.perform(get("/api/seguimiento-condicions?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(seguimientoCondicion.getId().intValue())))
            .andExpect(jsonPath("$.[*].estadoCondicion").value(hasItem(DEFAULT_ESTADO_CONDICION)))
            .andExpect(jsonPath("$.[*].fecha").value(hasItem(DEFAULT_FECHA.toString())))
            .andExpect(jsonPath("$.[*].diagnostico").value(hasItem(DEFAULT_DIAGNOSTICO)))
            .andExpect(jsonPath("$.[*].evolucionTratamiento").value(hasItem(DEFAULT_EVOLUCION_TRATAMIENTO)));
    }
    
    @Test
    @Transactional
    public void getSeguimientoCondicion() throws Exception {
        // Initialize the database
        seguimientoCondicionRepository.saveAndFlush(seguimientoCondicion);

        // Get the seguimientoCondicion
        restSeguimientoCondicionMockMvc.perform(get("/api/seguimiento-condicions/{id}", seguimientoCondicion.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(seguimientoCondicion.getId().intValue()))
            .andExpect(jsonPath("$.estadoCondicion").value(DEFAULT_ESTADO_CONDICION))
            .andExpect(jsonPath("$.fecha").value(DEFAULT_FECHA.toString()))
            .andExpect(jsonPath("$.diagnostico").value(DEFAULT_DIAGNOSTICO))
            .andExpect(jsonPath("$.evolucionTratamiento").value(DEFAULT_EVOLUCION_TRATAMIENTO));
    }
    @Test
    @Transactional
    public void getNonExistingSeguimientoCondicion() throws Exception {
        // Get the seguimientoCondicion
        restSeguimientoCondicionMockMvc.perform(get("/api/seguimiento-condicions/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSeguimientoCondicion() throws Exception {
        // Initialize the database
        seguimientoCondicionRepository.saveAndFlush(seguimientoCondicion);

        int databaseSizeBeforeUpdate = seguimientoCondicionRepository.findAll().size();

        // Update the seguimientoCondicion
        SeguimientoCondicion updatedSeguimientoCondicion = seguimientoCondicionRepository.findById(seguimientoCondicion.getId()).get();
        // Disconnect from session so that the updates on updatedSeguimientoCondicion are not directly saved in db
        em.detach(updatedSeguimientoCondicion);
        updatedSeguimientoCondicion
            .estadoCondicion(UPDATED_ESTADO_CONDICION)
            .fecha(UPDATED_FECHA)
            .diagnostico(UPDATED_DIAGNOSTICO)
            .evolucionTratamiento(UPDATED_EVOLUCION_TRATAMIENTO);

        restSeguimientoCondicionMockMvc.perform(put("/api/seguimiento-condicions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedSeguimientoCondicion)))
            .andExpect(status().isOk());

        // Validate the SeguimientoCondicion in the database
        List<SeguimientoCondicion> seguimientoCondicionList = seguimientoCondicionRepository.findAll();
        assertThat(seguimientoCondicionList).hasSize(databaseSizeBeforeUpdate);
        SeguimientoCondicion testSeguimientoCondicion = seguimientoCondicionList.get(seguimientoCondicionList.size() - 1);
        assertThat(testSeguimientoCondicion.getEstadoCondicion()).isEqualTo(UPDATED_ESTADO_CONDICION);
        assertThat(testSeguimientoCondicion.getFecha()).isEqualTo(UPDATED_FECHA);
        assertThat(testSeguimientoCondicion.getDiagnostico()).isEqualTo(UPDATED_DIAGNOSTICO);
        assertThat(testSeguimientoCondicion.getEvolucionTratamiento()).isEqualTo(UPDATED_EVOLUCION_TRATAMIENTO);
    }

    @Test
    @Transactional
    public void updateNonExistingSeguimientoCondicion() throws Exception {
        int databaseSizeBeforeUpdate = seguimientoCondicionRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSeguimientoCondicionMockMvc.perform(put("/api/seguimiento-condicions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(seguimientoCondicion)))
            .andExpect(status().isBadRequest());

        // Validate the SeguimientoCondicion in the database
        List<SeguimientoCondicion> seguimientoCondicionList = seguimientoCondicionRepository.findAll();
        assertThat(seguimientoCondicionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteSeguimientoCondicion() throws Exception {
        // Initialize the database
        seguimientoCondicionRepository.saveAndFlush(seguimientoCondicion);

        int databaseSizeBeforeDelete = seguimientoCondicionRepository.findAll().size();

        // Delete the seguimientoCondicion
        restSeguimientoCondicionMockMvc.perform(delete("/api/seguimiento-condicions/{id}", seguimientoCondicion.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<SeguimientoCondicion> seguimientoCondicionList = seguimientoCondicionRepository.findAll();
        assertThat(seguimientoCondicionList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
