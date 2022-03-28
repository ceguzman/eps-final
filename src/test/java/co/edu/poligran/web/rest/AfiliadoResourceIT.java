package co.edu.poligran.web.rest;

import co.edu.poligran.PruuebaApp;
import co.edu.poligran.domain.Afiliado;
import co.edu.poligran.domain.Cliente;
import co.edu.poligran.repository.AfiliadoRepository;

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
 * Integration tests for the {@link AfiliadoResource} REST controller.
 */
@SpringBootTest(classes = PruuebaApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class AfiliadoResourceIT {

    private static final String DEFAULT_PARENTESCO = "AAAAAAAAAA";
    private static final String UPDATED_PARENTESCO = "BBBBBBBBBB";

    private static final String DEFAULT_CORREO_ELECTRONICO = "AAAAAAAAAA";
    private static final String UPDATED_CORREO_ELECTRONICO = "BBBBBBBBBB";

    private static final String DEFAULT_NOMBRE_AFILIADO = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE_AFILIADO = "BBBBBBBBBB";

    @Autowired
    private AfiliadoRepository afiliadoRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAfiliadoMockMvc;

    private Afiliado afiliado;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Afiliado createEntity(EntityManager em) {
        Afiliado afiliado = new Afiliado()
            .parentesco(DEFAULT_PARENTESCO)
            .correoElectronico(DEFAULT_CORREO_ELECTRONICO)
            .nombreAfiliado(DEFAULT_NOMBRE_AFILIADO);
        // Add required entity
        Cliente cliente;
        if (TestUtil.findAll(em, Cliente.class).isEmpty()) {
            cliente = ClienteResourceIT.createEntity(em);
            em.persist(cliente);
            em.flush();
        } else {
            cliente = TestUtil.findAll(em, Cliente.class).get(0);
        }
        afiliado.setCliente(cliente);
        return afiliado;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Afiliado createUpdatedEntity(EntityManager em) {
        Afiliado afiliado = new Afiliado()
            .parentesco(UPDATED_PARENTESCO)
            .correoElectronico(UPDATED_CORREO_ELECTRONICO)
            .nombreAfiliado(UPDATED_NOMBRE_AFILIADO);
        // Add required entity
        Cliente cliente;
        if (TestUtil.findAll(em, Cliente.class).isEmpty()) {
            cliente = ClienteResourceIT.createUpdatedEntity(em);
            em.persist(cliente);
            em.flush();
        } else {
            cliente = TestUtil.findAll(em, Cliente.class).get(0);
        }
        afiliado.setCliente(cliente);
        return afiliado;
    }

    @BeforeEach
    public void initTest() {
        afiliado = createEntity(em);
    }

    @Test
    @Transactional
    public void createAfiliado() throws Exception {
        int databaseSizeBeforeCreate = afiliadoRepository.findAll().size();
        // Create the Afiliado
        restAfiliadoMockMvc.perform(post("/api/afiliados")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(afiliado)))
            .andExpect(status().isCreated());

        // Validate the Afiliado in the database
        List<Afiliado> afiliadoList = afiliadoRepository.findAll();
        assertThat(afiliadoList).hasSize(databaseSizeBeforeCreate + 1);
        Afiliado testAfiliado = afiliadoList.get(afiliadoList.size() - 1);
        assertThat(testAfiliado.getParentesco()).isEqualTo(DEFAULT_PARENTESCO);
        assertThat(testAfiliado.getCorreoElectronico()).isEqualTo(DEFAULT_CORREO_ELECTRONICO);
        assertThat(testAfiliado.getNombreAfiliado()).isEqualTo(DEFAULT_NOMBRE_AFILIADO);
    }

    @Test
    @Transactional
    public void createAfiliadoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = afiliadoRepository.findAll().size();

        // Create the Afiliado with an existing ID
        afiliado.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAfiliadoMockMvc.perform(post("/api/afiliados")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(afiliado)))
            .andExpect(status().isBadRequest());

        // Validate the Afiliado in the database
        List<Afiliado> afiliadoList = afiliadoRepository.findAll();
        assertThat(afiliadoList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkParentescoIsRequired() throws Exception {
        int databaseSizeBeforeTest = afiliadoRepository.findAll().size();
        // set the field null
        afiliado.setParentesco(null);

        // Create the Afiliado, which fails.


        restAfiliadoMockMvc.perform(post("/api/afiliados")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(afiliado)))
            .andExpect(status().isBadRequest());

        List<Afiliado> afiliadoList = afiliadoRepository.findAll();
        assertThat(afiliadoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCorreoElectronicoIsRequired() throws Exception {
        int databaseSizeBeforeTest = afiliadoRepository.findAll().size();
        // set the field null
        afiliado.setCorreoElectronico(null);

        // Create the Afiliado, which fails.


        restAfiliadoMockMvc.perform(post("/api/afiliados")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(afiliado)))
            .andExpect(status().isBadRequest());

        List<Afiliado> afiliadoList = afiliadoRepository.findAll();
        assertThat(afiliadoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNombreAfiliadoIsRequired() throws Exception {
        int databaseSizeBeforeTest = afiliadoRepository.findAll().size();
        // set the field null
        afiliado.setNombreAfiliado(null);

        // Create the Afiliado, which fails.


        restAfiliadoMockMvc.perform(post("/api/afiliados")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(afiliado)))
            .andExpect(status().isBadRequest());

        List<Afiliado> afiliadoList = afiliadoRepository.findAll();
        assertThat(afiliadoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllAfiliados() throws Exception {
        // Initialize the database
        afiliadoRepository.saveAndFlush(afiliado);

        // Get all the afiliadoList
        restAfiliadoMockMvc.perform(get("/api/afiliados?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(afiliado.getId().intValue())))
            .andExpect(jsonPath("$.[*].parentesco").value(hasItem(DEFAULT_PARENTESCO)))
            .andExpect(jsonPath("$.[*].correoElectronico").value(hasItem(DEFAULT_CORREO_ELECTRONICO)))
            .andExpect(jsonPath("$.[*].nombreAfiliado").value(hasItem(DEFAULT_NOMBRE_AFILIADO)));
    }
    
    @Test
    @Transactional
    public void getAfiliado() throws Exception {
        // Initialize the database
        afiliadoRepository.saveAndFlush(afiliado);

        // Get the afiliado
        restAfiliadoMockMvc.perform(get("/api/afiliados/{id}", afiliado.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(afiliado.getId().intValue()))
            .andExpect(jsonPath("$.parentesco").value(DEFAULT_PARENTESCO))
            .andExpect(jsonPath("$.correoElectronico").value(DEFAULT_CORREO_ELECTRONICO))
            .andExpect(jsonPath("$.nombreAfiliado").value(DEFAULT_NOMBRE_AFILIADO));
    }
    @Test
    @Transactional
    public void getNonExistingAfiliado() throws Exception {
        // Get the afiliado
        restAfiliadoMockMvc.perform(get("/api/afiliados/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAfiliado() throws Exception {
        // Initialize the database
        afiliadoRepository.saveAndFlush(afiliado);

        int databaseSizeBeforeUpdate = afiliadoRepository.findAll().size();

        // Update the afiliado
        Afiliado updatedAfiliado = afiliadoRepository.findById(afiliado.getId()).get();
        // Disconnect from session so that the updates on updatedAfiliado are not directly saved in db
        em.detach(updatedAfiliado);
        updatedAfiliado
            .parentesco(UPDATED_PARENTESCO)
            .correoElectronico(UPDATED_CORREO_ELECTRONICO)
            .nombreAfiliado(UPDATED_NOMBRE_AFILIADO);

        restAfiliadoMockMvc.perform(put("/api/afiliados")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedAfiliado)))
            .andExpect(status().isOk());

        // Validate the Afiliado in the database
        List<Afiliado> afiliadoList = afiliadoRepository.findAll();
        assertThat(afiliadoList).hasSize(databaseSizeBeforeUpdate);
        Afiliado testAfiliado = afiliadoList.get(afiliadoList.size() - 1);
        assertThat(testAfiliado.getParentesco()).isEqualTo(UPDATED_PARENTESCO);
        assertThat(testAfiliado.getCorreoElectronico()).isEqualTo(UPDATED_CORREO_ELECTRONICO);
        assertThat(testAfiliado.getNombreAfiliado()).isEqualTo(UPDATED_NOMBRE_AFILIADO);
    }

    @Test
    @Transactional
    public void updateNonExistingAfiliado() throws Exception {
        int databaseSizeBeforeUpdate = afiliadoRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAfiliadoMockMvc.perform(put("/api/afiliados")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(afiliado)))
            .andExpect(status().isBadRequest());

        // Validate the Afiliado in the database
        List<Afiliado> afiliadoList = afiliadoRepository.findAll();
        assertThat(afiliadoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteAfiliado() throws Exception {
        // Initialize the database
        afiliadoRepository.saveAndFlush(afiliado);

        int databaseSizeBeforeDelete = afiliadoRepository.findAll().size();

        // Delete the afiliado
        restAfiliadoMockMvc.perform(delete("/api/afiliados/{id}", afiliado.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Afiliado> afiliadoList = afiliadoRepository.findAll();
        assertThat(afiliadoList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
