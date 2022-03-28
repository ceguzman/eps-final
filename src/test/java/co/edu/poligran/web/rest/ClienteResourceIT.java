package co.edu.poligran.web.rest;

import co.edu.poligran.PruuebaApp;
import co.edu.poligran.domain.Cliente;
import co.edu.poligran.domain.TipoDocumento;
import co.edu.poligran.repository.ClienteRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Base64Utils;
import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link ClienteResource} REST controller.
 */
@SpringBootTest(classes = PruuebaApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class ClienteResourceIT {

    private static final String DEFAULT_NUMERO_DOCUMENTO = "AAAAAAAAAA";
    private static final String UPDATED_NUMERO_DOCUMENTO = "BBBBBBBBBB";

    private static final String DEFAULT_PRIMER_NOMBRE = "AAAAAAAAAA";
    private static final String UPDATED_PRIMER_NOMBRE = "BBBBBBBBBB";

    private static final String DEFAULT_PRIMER_APELLIDO = "AAAAAAAAAA";
    private static final String UPDATED_PRIMER_APELLIDO = "BBBBBBBBBB";

    private static final String DEFAULT_SEGUNDO_NOMBRE = "AAAAAAAAAA";
    private static final String UPDATED_SEGUNDO_NOMBRE = "BBBBBBBBBB";

    private static final String DEFAULT_SEGUNDO_APELLIDO = "AAAAAAAAAA";
    private static final String UPDATED_SEGUNDO_APELLIDO = "BBBBBBBBBB";

    private static final byte[] DEFAULT_IMG_URL = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_IMG_URL = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_IMG_URL_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_IMG_URL_CONTENT_TYPE = "image/png";

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restClienteMockMvc;

    private Cliente cliente;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Cliente createEntity(EntityManager em) {
        Cliente cliente = new Cliente()
            .numeroDocumento(DEFAULT_NUMERO_DOCUMENTO)
            .primerNombre(DEFAULT_PRIMER_NOMBRE)
            .primerApellido(DEFAULT_PRIMER_APELLIDO)
            .segundoNombre(DEFAULT_SEGUNDO_NOMBRE)
            .segundoApellido(DEFAULT_SEGUNDO_APELLIDO)
            .imgUrl(DEFAULT_IMG_URL)
            .imgUrlContentType(DEFAULT_IMG_URL_CONTENT_TYPE);
        // Add required entity
        TipoDocumento tipoDocumento;
        if (TestUtil.findAll(em, TipoDocumento.class).isEmpty()) {
            tipoDocumento = TipoDocumentoResourceIT.createEntity(em);
            em.persist(tipoDocumento);
            em.flush();
        } else {
            tipoDocumento = TestUtil.findAll(em, TipoDocumento.class).get(0);
        }
        cliente.setTypoDocumento(tipoDocumento);
        return cliente;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Cliente createUpdatedEntity(EntityManager em) {
        Cliente cliente = new Cliente()
            .numeroDocumento(UPDATED_NUMERO_DOCUMENTO)
            .primerNombre(UPDATED_PRIMER_NOMBRE)
            .primerApellido(UPDATED_PRIMER_APELLIDO)
            .segundoNombre(UPDATED_SEGUNDO_NOMBRE)
            .segundoApellido(UPDATED_SEGUNDO_APELLIDO)
            .imgUrl(UPDATED_IMG_URL)
            .imgUrlContentType(UPDATED_IMG_URL_CONTENT_TYPE);
        // Add required entity
        TipoDocumento tipoDocumento;
        if (TestUtil.findAll(em, TipoDocumento.class).isEmpty()) {
            tipoDocumento = TipoDocumentoResourceIT.createUpdatedEntity(em);
            em.persist(tipoDocumento);
            em.flush();
        } else {
            tipoDocumento = TestUtil.findAll(em, TipoDocumento.class).get(0);
        }
        cliente.setTypoDocumento(tipoDocumento);
        return cliente;
    }

    @BeforeEach
    public void initTest() {
        cliente = createEntity(em);
    }

    @Test
    @Transactional
    public void createCliente() throws Exception {
        int databaseSizeBeforeCreate = clienteRepository.findAll().size();
        // Create the Cliente
        restClienteMockMvc.perform(post("/api/clientes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cliente)))
            .andExpect(status().isCreated());

        // Validate the Cliente in the database
        List<Cliente> clienteList = clienteRepository.findAll();
        assertThat(clienteList).hasSize(databaseSizeBeforeCreate + 1);
        Cliente testCliente = clienteList.get(clienteList.size() - 1);
        assertThat(testCliente.getNumeroDocumento()).isEqualTo(DEFAULT_NUMERO_DOCUMENTO);
        assertThat(testCliente.getPrimerNombre()).isEqualTo(DEFAULT_PRIMER_NOMBRE);
        assertThat(testCliente.getPrimerApellido()).isEqualTo(DEFAULT_PRIMER_APELLIDO);
        assertThat(testCliente.getSegundoNombre()).isEqualTo(DEFAULT_SEGUNDO_NOMBRE);
        assertThat(testCliente.getSegundoApellido()).isEqualTo(DEFAULT_SEGUNDO_APELLIDO);
        assertThat(testCliente.getImgUrl()).isEqualTo(DEFAULT_IMG_URL);
        assertThat(testCliente.getImgUrlContentType()).isEqualTo(DEFAULT_IMG_URL_CONTENT_TYPE);
    }

    @Test
    @Transactional
    public void createClienteWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = clienteRepository.findAll().size();

        // Create the Cliente with an existing ID
        cliente.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restClienteMockMvc.perform(post("/api/clientes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cliente)))
            .andExpect(status().isBadRequest());

        // Validate the Cliente in the database
        List<Cliente> clienteList = clienteRepository.findAll();
        assertThat(clienteList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNumeroDocumentoIsRequired() throws Exception {
        int databaseSizeBeforeTest = clienteRepository.findAll().size();
        // set the field null
        cliente.setNumeroDocumento(null);

        // Create the Cliente, which fails.


        restClienteMockMvc.perform(post("/api/clientes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cliente)))
            .andExpect(status().isBadRequest());

        List<Cliente> clienteList = clienteRepository.findAll();
        assertThat(clienteList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPrimerNombreIsRequired() throws Exception {
        int databaseSizeBeforeTest = clienteRepository.findAll().size();
        // set the field null
        cliente.setPrimerNombre(null);

        // Create the Cliente, which fails.


        restClienteMockMvc.perform(post("/api/clientes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cliente)))
            .andExpect(status().isBadRequest());

        List<Cliente> clienteList = clienteRepository.findAll();
        assertThat(clienteList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPrimerApellidoIsRequired() throws Exception {
        int databaseSizeBeforeTest = clienteRepository.findAll().size();
        // set the field null
        cliente.setPrimerApellido(null);

        // Create the Cliente, which fails.


        restClienteMockMvc.perform(post("/api/clientes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cliente)))
            .andExpect(status().isBadRequest());

        List<Cliente> clienteList = clienteRepository.findAll();
        assertThat(clienteList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllClientes() throws Exception {
        // Initialize the database
        clienteRepository.saveAndFlush(cliente);

        // Get all the clienteList
        restClienteMockMvc.perform(get("/api/clientes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cliente.getId().intValue())))
            .andExpect(jsonPath("$.[*].numeroDocumento").value(hasItem(DEFAULT_NUMERO_DOCUMENTO)))
            .andExpect(jsonPath("$.[*].primerNombre").value(hasItem(DEFAULT_PRIMER_NOMBRE)))
            .andExpect(jsonPath("$.[*].primerApellido").value(hasItem(DEFAULT_PRIMER_APELLIDO)))
            .andExpect(jsonPath("$.[*].segundoNombre").value(hasItem(DEFAULT_SEGUNDO_NOMBRE)))
            .andExpect(jsonPath("$.[*].segundoApellido").value(hasItem(DEFAULT_SEGUNDO_APELLIDO)))
            .andExpect(jsonPath("$.[*].imgUrlContentType").value(hasItem(DEFAULT_IMG_URL_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].imgUrl").value(hasItem(Base64Utils.encodeToString(DEFAULT_IMG_URL))));
    }
    
    @Test
    @Transactional
    public void getCliente() throws Exception {
        // Initialize the database
        clienteRepository.saveAndFlush(cliente);

        // Get the cliente
        restClienteMockMvc.perform(get("/api/clientes/{id}", cliente.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(cliente.getId().intValue()))
            .andExpect(jsonPath("$.numeroDocumento").value(DEFAULT_NUMERO_DOCUMENTO))
            .andExpect(jsonPath("$.primerNombre").value(DEFAULT_PRIMER_NOMBRE))
            .andExpect(jsonPath("$.primerApellido").value(DEFAULT_PRIMER_APELLIDO))
            .andExpect(jsonPath("$.segundoNombre").value(DEFAULT_SEGUNDO_NOMBRE))
            .andExpect(jsonPath("$.segundoApellido").value(DEFAULT_SEGUNDO_APELLIDO))
            .andExpect(jsonPath("$.imgUrlContentType").value(DEFAULT_IMG_URL_CONTENT_TYPE))
            .andExpect(jsonPath("$.imgUrl").value(Base64Utils.encodeToString(DEFAULT_IMG_URL)));
    }
    @Test
    @Transactional
    public void getNonExistingCliente() throws Exception {
        // Get the cliente
        restClienteMockMvc.perform(get("/api/clientes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCliente() throws Exception {
        // Initialize the database
        clienteRepository.saveAndFlush(cliente);

        int databaseSizeBeforeUpdate = clienteRepository.findAll().size();

        // Update the cliente
        Cliente updatedCliente = clienteRepository.findById(cliente.getId()).get();
        // Disconnect from session so that the updates on updatedCliente are not directly saved in db
        em.detach(updatedCliente);
        updatedCliente
            .numeroDocumento(UPDATED_NUMERO_DOCUMENTO)
            .primerNombre(UPDATED_PRIMER_NOMBRE)
            .primerApellido(UPDATED_PRIMER_APELLIDO)
            .segundoNombre(UPDATED_SEGUNDO_NOMBRE)
            .segundoApellido(UPDATED_SEGUNDO_APELLIDO)
            .imgUrl(UPDATED_IMG_URL)
            .imgUrlContentType(UPDATED_IMG_URL_CONTENT_TYPE);

        restClienteMockMvc.perform(put("/api/clientes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedCliente)))
            .andExpect(status().isOk());

        // Validate the Cliente in the database
        List<Cliente> clienteList = clienteRepository.findAll();
        assertThat(clienteList).hasSize(databaseSizeBeforeUpdate);
        Cliente testCliente = clienteList.get(clienteList.size() - 1);
        assertThat(testCliente.getNumeroDocumento()).isEqualTo(UPDATED_NUMERO_DOCUMENTO);
        assertThat(testCliente.getPrimerNombre()).isEqualTo(UPDATED_PRIMER_NOMBRE);
        assertThat(testCliente.getPrimerApellido()).isEqualTo(UPDATED_PRIMER_APELLIDO);
        assertThat(testCliente.getSegundoNombre()).isEqualTo(UPDATED_SEGUNDO_NOMBRE);
        assertThat(testCliente.getSegundoApellido()).isEqualTo(UPDATED_SEGUNDO_APELLIDO);
        assertThat(testCliente.getImgUrl()).isEqualTo(UPDATED_IMG_URL);
        assertThat(testCliente.getImgUrlContentType()).isEqualTo(UPDATED_IMG_URL_CONTENT_TYPE);
    }

    @Test
    @Transactional
    public void updateNonExistingCliente() throws Exception {
        int databaseSizeBeforeUpdate = clienteRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restClienteMockMvc.perform(put("/api/clientes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cliente)))
            .andExpect(status().isBadRequest());

        // Validate the Cliente in the database
        List<Cliente> clienteList = clienteRepository.findAll();
        assertThat(clienteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCliente() throws Exception {
        // Initialize the database
        clienteRepository.saveAndFlush(cliente);

        int databaseSizeBeforeDelete = clienteRepository.findAll().size();

        // Delete the cliente
        restClienteMockMvc.perform(delete("/api/clientes/{id}", cliente.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Cliente> clienteList = clienteRepository.findAll();
        assertThat(clienteList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
