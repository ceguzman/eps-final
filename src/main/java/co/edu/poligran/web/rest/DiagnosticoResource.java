package co.edu.poligran.web.rest;

import co.edu.poligran.domain.Diagnostico;
import co.edu.poligran.repository.DiagnosticoRepository;
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
 * REST controller for managing {@link co.edu.poligran.domain.Diagnostico}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class DiagnosticoResource {

    private final Logger log = LoggerFactory.getLogger(DiagnosticoResource.class);

    private static final String ENTITY_NAME = "diagnostico";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DiagnosticoRepository diagnosticoRepository;

    public DiagnosticoResource(DiagnosticoRepository diagnosticoRepository) {
        this.diagnosticoRepository = diagnosticoRepository;
    }

    /**
     * {@code POST  /diagnosticos} : Create a new diagnostico.
     *
     * @param diagnostico the diagnostico to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new diagnostico, or with status {@code 400 (Bad Request)} if the diagnostico has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/diagnosticos")
    public ResponseEntity<Diagnostico> createDiagnostico(@Valid @RequestBody Diagnostico diagnostico) throws URISyntaxException {
        log.debug("REST request to save Diagnostico : {}", diagnostico);
        if (diagnostico.getId() != null) {
            throw new BadRequestAlertException("A new diagnostico cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Diagnostico result = diagnosticoRepository.save(diagnostico);
        return ResponseEntity.created(new URI("/api/diagnosticos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /diagnosticos} : Updates an existing diagnostico.
     *
     * @param diagnostico the diagnostico to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated diagnostico,
     * or with status {@code 400 (Bad Request)} if the diagnostico is not valid,
     * or with status {@code 500 (Internal Server Error)} if the diagnostico couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/diagnosticos")
    public ResponseEntity<Diagnostico> updateDiagnostico(@Valid @RequestBody Diagnostico diagnostico) throws URISyntaxException {
        log.debug("REST request to update Diagnostico : {}", diagnostico);
        if (diagnostico.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Diagnostico result = diagnosticoRepository.save(diagnostico);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, diagnostico.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /diagnosticos} : get all the diagnosticos.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of diagnosticos in body.
     */
    @GetMapping("/diagnosticos")
    public List<Diagnostico> getAllDiagnosticos() {
        log.debug("REST request to get all Diagnosticos");
        return diagnosticoRepository.findAll();
    }

    /**
     * {@code GET  /diagnosticos/:id} : get the "id" diagnostico.
     *
     * @param id the id of the diagnostico to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the diagnostico, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/diagnosticos/{id}")
    public ResponseEntity<Diagnostico> getDiagnostico(@PathVariable Long id) {
        log.debug("REST request to get Diagnostico : {}", id);
        Optional<Diagnostico> diagnostico = diagnosticoRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(diagnostico);
    }

    /**
     * {@code DELETE  /diagnosticos/:id} : delete the "id" diagnostico.
     *
     * @param id the id of the diagnostico to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/diagnosticos/{id}")
    public ResponseEntity<Void> deleteDiagnostico(@PathVariable Long id) {
        log.debug("REST request to delete Diagnostico : {}", id);
        diagnosticoRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
