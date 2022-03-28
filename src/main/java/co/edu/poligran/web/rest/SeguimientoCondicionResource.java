package co.edu.poligran.web.rest;

import co.edu.poligran.domain.SeguimientoCondicion;
import co.edu.poligran.repository.SeguimientoCondicionRepository;
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
 * REST controller for managing {@link co.edu.poligran.domain.SeguimientoCondicion}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class SeguimientoCondicionResource {

    private final Logger log = LoggerFactory.getLogger(SeguimientoCondicionResource.class);

    private static final String ENTITY_NAME = "seguimientoCondicion";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SeguimientoCondicionRepository seguimientoCondicionRepository;

    public SeguimientoCondicionResource(SeguimientoCondicionRepository seguimientoCondicionRepository) {
        this.seguimientoCondicionRepository = seguimientoCondicionRepository;
    }

    /**
     * {@code POST  /seguimiento-condicions} : Create a new seguimientoCondicion.
     *
     * @param seguimientoCondicion the seguimientoCondicion to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new seguimientoCondicion, or with status {@code 400 (Bad Request)} if the seguimientoCondicion has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/seguimiento-condicions")
    public ResponseEntity<SeguimientoCondicion> createSeguimientoCondicion(@Valid @RequestBody SeguimientoCondicion seguimientoCondicion) throws URISyntaxException {
        log.debug("REST request to save SeguimientoCondicion : {}", seguimientoCondicion);
        if (seguimientoCondicion.getId() != null) {
            throw new BadRequestAlertException("A new seguimientoCondicion cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SeguimientoCondicion result = seguimientoCondicionRepository.save(seguimientoCondicion);
        return ResponseEntity.created(new URI("/api/seguimiento-condicions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /seguimiento-condicions} : Updates an existing seguimientoCondicion.
     *
     * @param seguimientoCondicion the seguimientoCondicion to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated seguimientoCondicion,
     * or with status {@code 400 (Bad Request)} if the seguimientoCondicion is not valid,
     * or with status {@code 500 (Internal Server Error)} if the seguimientoCondicion couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/seguimiento-condicions")
    public ResponseEntity<SeguimientoCondicion> updateSeguimientoCondicion(@Valid @RequestBody SeguimientoCondicion seguimientoCondicion) throws URISyntaxException {
        log.debug("REST request to update SeguimientoCondicion : {}", seguimientoCondicion);
        if (seguimientoCondicion.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        SeguimientoCondicion result = seguimientoCondicionRepository.save(seguimientoCondicion);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, seguimientoCondicion.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /seguimiento-condicions} : get all the seguimientoCondicions.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of seguimientoCondicions in body.
     */
    @GetMapping("/seguimiento-condicions")
    public List<SeguimientoCondicion> getAllSeguimientoCondicions() {
        log.debug("REST request to get all SeguimientoCondicions");
        return seguimientoCondicionRepository.findAll();
    }

    /**
     * {@code GET  /seguimiento-condicions/:id} : get the "id" seguimientoCondicion.
     *
     * @param id the id of the seguimientoCondicion to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the seguimientoCondicion, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/seguimiento-condicions/{id}")
    public ResponseEntity<SeguimientoCondicion> getSeguimientoCondicion(@PathVariable Long id) {
        log.debug("REST request to get SeguimientoCondicion : {}", id);
        Optional<SeguimientoCondicion> seguimientoCondicion = seguimientoCondicionRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(seguimientoCondicion);
    }

    /**
     * {@code DELETE  /seguimiento-condicions/:id} : delete the "id" seguimientoCondicion.
     *
     * @param id the id of the seguimientoCondicion to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/seguimiento-condicions/{id}")
    public ResponseEntity<Void> deleteSeguimientoCondicion(@PathVariable Long id) {
        log.debug("REST request to delete SeguimientoCondicion : {}", id);
        seguimientoCondicionRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
