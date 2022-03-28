package co.edu.poligran.web.rest;

import co.edu.poligran.domain.ControlProfesional;
import co.edu.poligran.repository.ControlProfesionalRepository;
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
 * REST controller for managing {@link co.edu.poligran.domain.ControlProfesional}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class ControlProfesionalResource {

    private final Logger log = LoggerFactory.getLogger(ControlProfesionalResource.class);

    private static final String ENTITY_NAME = "controlProfesional";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ControlProfesionalRepository controlProfesionalRepository;

    public ControlProfesionalResource(ControlProfesionalRepository controlProfesionalRepository) {
        this.controlProfesionalRepository = controlProfesionalRepository;
    }

    /**
     * {@code POST  /control-profesionals} : Create a new controlProfesional.
     *
     * @param controlProfesional the controlProfesional to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new controlProfesional, or with status {@code 400 (Bad Request)} if the controlProfesional has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/control-profesionals")
    public ResponseEntity<ControlProfesional> createControlProfesional(@Valid @RequestBody ControlProfesional controlProfesional) throws URISyntaxException {
        log.debug("REST request to save ControlProfesional : {}", controlProfesional);
        if (controlProfesional.getId() != null) {
            throw new BadRequestAlertException("A new controlProfesional cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ControlProfesional result = controlProfesionalRepository.save(controlProfesional);
        return ResponseEntity.created(new URI("/api/control-profesionals/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /control-profesionals} : Updates an existing controlProfesional.
     *
     * @param controlProfesional the controlProfesional to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated controlProfesional,
     * or with status {@code 400 (Bad Request)} if the controlProfesional is not valid,
     * or with status {@code 500 (Internal Server Error)} if the controlProfesional couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/control-profesionals")
    public ResponseEntity<ControlProfesional> updateControlProfesional(@Valid @RequestBody ControlProfesional controlProfesional) throws URISyntaxException {
        log.debug("REST request to update ControlProfesional : {}", controlProfesional);
        if (controlProfesional.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ControlProfesional result = controlProfesionalRepository.save(controlProfesional);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, controlProfesional.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /control-profesionals} : get all the controlProfesionals.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of controlProfesionals in body.
     */
    @GetMapping("/control-profesionals")
    public List<ControlProfesional> getAllControlProfesionals() {
        log.debug("REST request to get all ControlProfesionals");
        return controlProfesionalRepository.findAll();
    }

    /**
     * {@code GET  /control-profesionals/:id} : get the "id" controlProfesional.
     *
     * @param id the id of the controlProfesional to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the controlProfesional, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/control-profesionals/{id}")
    public ResponseEntity<ControlProfesional> getControlProfesional(@PathVariable Long id) {
        log.debug("REST request to get ControlProfesional : {}", id);
        Optional<ControlProfesional> controlProfesional = controlProfesionalRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(controlProfesional);
    }

    /**
     * {@code DELETE  /control-profesionals/:id} : delete the "id" controlProfesional.
     *
     * @param id the id of the controlProfesional to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/control-profesionals/{id}")
    public ResponseEntity<Void> deleteControlProfesional(@PathVariable Long id) {
        log.debug("REST request to delete ControlProfesional : {}", id);
        controlProfesionalRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
