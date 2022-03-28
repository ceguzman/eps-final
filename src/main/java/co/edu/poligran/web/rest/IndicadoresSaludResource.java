package co.edu.poligran.web.rest;

import co.edu.poligran.domain.IndicadoresSalud;
import co.edu.poligran.repository.IndicadoresSaludRepository;
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
 * REST controller for managing {@link co.edu.poligran.domain.IndicadoresSalud}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class IndicadoresSaludResource {

    private final Logger log = LoggerFactory.getLogger(IndicadoresSaludResource.class);

    private static final String ENTITY_NAME = "indicadoresSalud";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final IndicadoresSaludRepository indicadoresSaludRepository;

    public IndicadoresSaludResource(IndicadoresSaludRepository indicadoresSaludRepository) {
        this.indicadoresSaludRepository = indicadoresSaludRepository;
    }

    /**
     * {@code POST  /indicadores-saluds} : Create a new indicadoresSalud.
     *
     * @param indicadoresSalud the indicadoresSalud to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new indicadoresSalud, or with status {@code 400 (Bad Request)} if the indicadoresSalud has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/indicadores-saluds")
    public ResponseEntity<IndicadoresSalud> createIndicadoresSalud(@Valid @RequestBody IndicadoresSalud indicadoresSalud) throws URISyntaxException {
        log.debug("REST request to save IndicadoresSalud : {}", indicadoresSalud);
        if (indicadoresSalud.getId() != null) {
            throw new BadRequestAlertException("A new indicadoresSalud cannot already have an ID", ENTITY_NAME, "idexists");
        }
        IndicadoresSalud result = indicadoresSaludRepository.save(indicadoresSalud);
        return ResponseEntity.created(new URI("/api/indicadores-saluds/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /indicadores-saluds} : Updates an existing indicadoresSalud.
     *
     * @param indicadoresSalud the indicadoresSalud to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated indicadoresSalud,
     * or with status {@code 400 (Bad Request)} if the indicadoresSalud is not valid,
     * or with status {@code 500 (Internal Server Error)} if the indicadoresSalud couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/indicadores-saluds")
    public ResponseEntity<IndicadoresSalud> updateIndicadoresSalud(@Valid @RequestBody IndicadoresSalud indicadoresSalud) throws URISyntaxException {
        log.debug("REST request to update IndicadoresSalud : {}", indicadoresSalud);
        if (indicadoresSalud.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        IndicadoresSalud result = indicadoresSaludRepository.save(indicadoresSalud);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, indicadoresSalud.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /indicadores-saluds} : get all the indicadoresSaluds.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of indicadoresSaluds in body.
     */
    @GetMapping("/indicadores-saluds")
    public List<IndicadoresSalud> getAllIndicadoresSaluds() {
        log.debug("REST request to get all IndicadoresSaluds");
        return indicadoresSaludRepository.findAll();
    }

    /**
     * {@code GET  /indicadores-saluds/:id} : get the "id" indicadoresSalud.
     *
     * @param id the id of the indicadoresSalud to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the indicadoresSalud, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/indicadores-saluds/{id}")
    public ResponseEntity<IndicadoresSalud> getIndicadoresSalud(@PathVariable Long id) {
        log.debug("REST request to get IndicadoresSalud : {}", id);
        Optional<IndicadoresSalud> indicadoresSalud = indicadoresSaludRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(indicadoresSalud);
    }

    /**
     * {@code DELETE  /indicadores-saluds/:id} : delete the "id" indicadoresSalud.
     *
     * @param id the id of the indicadoresSalud to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/indicadores-saluds/{id}")
    public ResponseEntity<Void> deleteIndicadoresSalud(@PathVariable Long id) {
        log.debug("REST request to delete IndicadoresSalud : {}", id);
        indicadoresSaludRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
