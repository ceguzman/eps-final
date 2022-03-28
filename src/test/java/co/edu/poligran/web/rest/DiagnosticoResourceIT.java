package co.edu.poligran.web.rest;

import co.edu.poligran.PruuebaApp;
import co.edu.poligran.domain.Diagnostico;
import co.edu.poligran.domain.Cliente;
import co.edu.poligran.repository.DiagnosticoRepository;

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
 * Integration tests for the {@link DiagnosticoResource} REST controller.
 */
@SpringBootTest(classes = PruuebaApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class DiagnosticoResourceIT {

    private static final String DEFAULT_RESULTADO_LABORATORIO = "AAAAAAAAAA";
    private static final String UPDATED_RESULTADO_LABORATORIO = "BBBBBBBBBB";

    private static final String DEFAULT_URL_IMAGEN = "AAAAAAAAAA";
    private static final String UPDATED_URL_IMAGEN = "BBBBBBBBBB";

    private static final String DEFAULT_TIPO_CONDICION_SALUD = "AAAAAAAAAA";
    private static final String UPDATED_TIPO_CONDICION_SALUD = "BBBBBBBBBB";

    @Autowired
    private DiagnosticoRepository diagnosticoRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restDiagnosticoMockMvc;

    private Diagnostico diagnostico;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Diagnostico createEntity(EntityManager em) {
        Diagnostico diagnostico = new Diagnostico()
            .resultadoLaboratorio(DEFAULT_RESULTADO_LABORATORIO)
            .urlImagen(DEFAULT_URL_IMAGEN)
            .tipoCondicionSalud(DEFAULT_TIPO_CONDICION_SALUD);
        // Add required entity
        Cliente cliente;
        if (TestUtil.findAll(em, Cliente.class).isEmpty()) {
            cliente = ClienteResourceIT.createEntity(em);
            em.persist(cliente);
            em.flush();
        } else {
            cliente = TestUtil.findAll(em, Cliente.class).get(0);
        }
        diagnostico.setCliente(cliente);
        return diagnostico;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Diagnostico createUpdatedEntity(EntityManager em) {
        Diagnostico diagnostico = new Diagnostico()
            .resultadoLaboratorio(UPDATED_RESULTADO_LABORATORIO)
            .urlImagen(UPDATED_URL_IMAGEN)
            .tipoCondicionSalud(UPDATED_TIPO_CONDICION_SALUD);
        // Add required entity
        Cliente cliente;
        if (TestUtil.findAll(em, Cliente.class).isEmpty()) {
            cliente = ClienteResourceIT.createUpdatedEntity(em);
            em.persist(cliente);
            em.flush();
        } else {
            cliente = TestUtil.findAll(em, Cliente.class).get(0);
        }
        diagnostico.setCliente(cliente);
        return diagnostico;
    }

    @BeforeEach
    public void initTest() {
        diagnostico = createEntity(em);
    }

    @Test
    @Transactional
    public void createDiagnostico() throws Exception {
        int databaseSizeBeforeCreate = diagnosticoRepository.findAll().size();
        // Create the Diagnostico
        restDiagnosticoMockMvc.perform(post("/api/diagnosticos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(diagnostico)))
            .andExpect(status().isCreated());

        // Validate the Diagnostico in the database
        List<Diagnostico> diagnosticoList = diagnosticoRepository.findAll();
        assertThat(diagnosticoList).hasSize(databaseSizeBeforeCreate + 1);
        Diagnostico testDiagnostico = diagnosticoList.get(diagnosticoList.size() - 1);
        assertThat(testDiagnostico.getResultadoLaboratorio()).isEqualTo(DEFAULT_RESULTADO_LABORATORIO);
        assertThat(testDiagnostico.getUrlImagen()).isEqualTo(DEFAULT_URL_IMAGEN);
        assertThat(testDiagnostico.getTipoCondicionSalud()).isEqualTo(DEFAULT_TIPO_CONDICION_SALUD);
    }

    @Test
    @Transactional
    public void createDiagnosticoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = diagnosticoRepository.findAll().size();

        // Create the Diagnostico with an existing ID
        diagnostico.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDiagnosticoMockMvc.perform(post("/api/diagnosticos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(diagnostico)))
            .andExpect(status().isBadRequest());

        // Validate the Diagnostico in the database
        List<Diagnostico> diagnosticoList = diagnosticoRepository.findAll();
        assertThat(diagnosticoList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkResultadoLaboratorioIsRequired() throws Exception {
        int databaseSizeBeforeTest = diagnosticoRepository.findAll().size();
        // set the field null
        diagnostico.setResultadoLaboratorio(null);

        // Create the Diagnostico, which fails.


        restDiagnosticoMockMvc.perform(post("/api/diagnosticos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(diagnostico)))
            .andExpect(status().isBadRequest());

        List<Diagnostico> diagnosticoList = diagnosticoRepository.findAll();
        assertThat(diagnosticoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkUrlImagenIsRequired() throws Exception {
        int databaseSizeBeforeTest = diagnosticoRepository.findAll().size();
        // set the field null
        diagnostico.setUrlImagen(null);

        // Create the Diagnostico, which fails.


        restDiagnosticoMockMvc.perform(post("/api/diagnosticos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(diagnostico)))
            .andExpect(status().isBadRequest());

        List<Diagnostico> diagnosticoList = diagnosticoRepository.findAll();
        assertThat(diagnosticoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTipoCondicionSaludIsRequired() throws Exception {
        int databaseSizeBeforeTest = diagnosticoRepository.findAll().size();
        // set the field null
        diagnostico.setTipoCondicionSalud(null);

        // Create the Diagnostico, which fails.


        restDiagnosticoMockMvc.perform(post("/api/diagnosticos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(diagnostico)))
            .andExpect(status().isBadRequest());

        List<Diagnostico> diagnosticoList = diagnosticoRepository.findAll();
        assertThat(diagnosticoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllDiagnosticos() throws Exception {
        // Initialize the database
        diagnosticoRepository.saveAndFlush(diagnostico);

        // Get all the diagnosticoList
        restDiagnosticoMockMvc.perform(get("/api/diagnosticos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(diagnostico.getId().intValue())))
            .andExpect(jsonPath("$.[*].resultadoLaboratorio").value(hasItem(DEFAULT_RESULTADO_LABORATORIO)))
            .andExpect(jsonPath("$.[*].urlImagen").value(hasItem(DEFAULT_URL_IMAGEN)))
            .andExpect(jsonPath("$.[*].tipoCondicionSalud").value(hasItem(DEFAULT_TIPO_CONDICION_SALUD)));
    }
    
    @Test
    @Transactional
    public void getDiagnostico() throws Exception {
        // Initialize the database
        diagnosticoRepository.saveAndFlush(diagnostico);

        // Get the diagnostico
        restDiagnosticoMockMvc.perform(get("/api/diagnosticos/{id}", diagnostico.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(diagnostico.getId().intValue()))
            .andExpect(jsonPath("$.resultadoLaboratorio").value(DEFAULT_RESULTADO_LABORATORIO))
            .andExpect(jsonPath("$.urlImagen").value(DEFAULT_URL_IMAGEN))
            .andExpect(jsonPath("$.tipoCondicionSalud").value(DEFAULT_TIPO_CONDICION_SALUD));
    }
    @Test
    @Transactional
    public void getNonExistingDiagnostico() throws Exception {
        // Get the diagnostico
        restDiagnosticoMockMvc.perform(get("/api/diagnosticos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDiagnostico() throws Exception {
        // Initialize the database
        diagnosticoRepository.saveAndFlush(diagnostico);

        int databaseSizeBeforeUpdate = diagnosticoRepository.findAll().size();

        // Update the diagnostico
        Diagnostico updatedDiagnostico = diagnosticoRepository.findById(diagnostico.getId()).get();
        // Disconnect from session so that the updates on updatedDiagnostico are not directly saved in db
        em.detach(updatedDiagnostico);
        updatedDiagnostico
            .resultadoLaboratorio(UPDATED_RESULTADO_LABORATORIO)
            .urlImagen(UPDATED_URL_IMAGEN)
            .tipoCondicionSalud(UPDATED_TIPO_CONDICION_SALUD);

        restDiagnosticoMockMvc.perform(put("/api/diagnosticos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedDiagnostico)))
            .andExpect(status().isOk());

        // Validate the Diagnostico in the database
        List<Diagnostico> diagnosticoList = diagnosticoRepository.findAll();
        assertThat(diagnosticoList).hasSize(databaseSizeBeforeUpdate);
        Diagnostico testDiagnostico = diagnosticoList.get(diagnosticoList.size() - 1);
        assertThat(testDiagnostico.getResultadoLaboratorio()).isEqualTo(UPDATED_RESULTADO_LABORATORIO);
        assertThat(testDiagnostico.getUrlImagen()).isEqualTo(UPDATED_URL_IMAGEN);
        assertThat(testDiagnostico.getTipoCondicionSalud()).isEqualTo(UPDATED_TIPO_CONDICION_SALUD);
    }

    @Test
    @Transactional
    public void updateNonExistingDiagnostico() throws Exception {
        int databaseSizeBeforeUpdate = diagnosticoRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDiagnosticoMockMvc.perform(put("/api/diagnosticos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(diagnostico)))
            .andExpect(status().isBadRequest());

        // Validate the Diagnostico in the database
        List<Diagnostico> diagnosticoList = diagnosticoRepository.findAll();
        assertThat(diagnosticoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteDiagnostico() throws Exception {
        // Initialize the database
        diagnosticoRepository.saveAndFlush(diagnostico);

        int databaseSizeBeforeDelete = diagnosticoRepository.findAll().size();

        // Delete the diagnostico
        restDiagnosticoMockMvc.perform(delete("/api/diagnosticos/{id}", diagnostico.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Diagnostico> diagnosticoList = diagnosticoRepository.findAll();
        assertThat(diagnosticoList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
