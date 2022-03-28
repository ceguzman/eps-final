package co.edu.poligran.web.rest;

import co.edu.poligran.domain.Afiliado;
import co.edu.poligran.repository.AfiliadoRepository;
import co.edu.poligran.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link co.edu.poligran.domain.Afiliado}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class AfiliadoResource {

    private final Logger log = LoggerFactory.getLogger(AfiliadoResource.class);

    private static final String ENTITY_NAME = "afiliado";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AfiliadoRepository afiliadoRepository;

    public AfiliadoResource(AfiliadoRepository afiliadoRepository) {
        this.afiliadoRepository = afiliadoRepository;
    }

    /**
     * {@code POST  /afiliados} : Create a new afiliado.
     *
     * @param afiliado the afiliado to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new afiliado, or with status {@code 400 (Bad Request)} if the afiliado has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/afiliados")
    public ResponseEntity<Afiliado> createAfiliado(@Valid @RequestBody Afiliado afiliado) throws URISyntaxException {
        log.debug("REST request to save Afiliado : {}", afiliado);
        if (afiliado.getId() != null) {
            throw new BadRequestAlertException("A new afiliado cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Afiliado result = afiliadoRepository.save(afiliado);
        return ResponseEntity.created(new URI("/api/afiliados/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /afiliados} : Updates an existing afiliado.
     *
     * @param afiliado the afiliado to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated afiliado,
     * or with status {@code 400 (Bad Request)} if the afiliado is not valid,
     * or with status {@code 500 (Internal Server Error)} if the afiliado couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/afiliados")
    public ResponseEntity<Afiliado> updateAfiliado(@Valid @RequestBody Afiliado afiliado) throws URISyntaxException {
        log.debug("REST request to update Afiliado : {}", afiliado);
        if (afiliado.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Afiliado result = afiliadoRepository.save(afiliado);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, afiliado.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /afiliados} : get all the afiliados.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of afiliados in body.
     */
    @GetMapping("/afiliados")
    public List<Afiliado> getAllAfiliados() {
        log.debug("REST request to get all Afiliados");
        return afiliadoRepository.findAll();
    }

    /**
     * {@code GET  /afiliados/:id} : get the "id" afiliado.
     *
     * @param id the id of the afiliado to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the afiliado, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/afiliados/{id}")
    public ResponseEntity<Afiliado> getAfiliado(@PathVariable Long id) {
        log.debug("REST request to get Afiliado : {}", id);
        Optional<Afiliado> afiliado = afiliadoRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(afiliado);
    }

    /**
     * {@code DELETE  /afiliados/:id} : delete the "id" afiliado.
     *
     * @param id the id of the afiliado to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/afiliados/{id}")
    public ResponseEntity<Void> deleteAfiliado(@PathVariable Long id) {
        log.debug("REST request to delete Afiliado : {}", id);
        afiliadoRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
