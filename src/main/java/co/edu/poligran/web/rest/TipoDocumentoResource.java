package co.edu.poligran.web.rest;

import co.edu.poligran.domain.TipoDocumento;
import co.edu.poligran.repository.TipoDocumentoRepository;
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
 * REST controller for managing {@link co.edu.poligran.domain.TipoDocumento}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class TipoDocumentoResource {

    private final Logger log = LoggerFactory.getLogger(TipoDocumentoResource.class);

    private static final String ENTITY_NAME = "tipoDocumento";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TipoDocumentoRepository tipoDocumentoRepository;

    public TipoDocumentoResource(TipoDocumentoRepository tipoDocumentoRepository) {
        this.tipoDocumentoRepository = tipoDocumentoRepository;
    }

    /**
     * {@code POST  /tipo-documentos} : Create a new tipoDocumento.
     *
     * @param tipoDocumento the tipoDocumento to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new tipoDocumento, or with status {@code 400 (Bad Request)} if the tipoDocumento has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/tipo-documentos")
    public ResponseEntity<TipoDocumento> createTipoDocumento(@Valid @RequestBody TipoDocumento tipoDocumento) throws URISyntaxException {
        log.debug("REST request to save TipoDocumento : {}", tipoDocumento);
        if (tipoDocumento.getId() != null) {
            throw new BadRequestAlertException("A new tipoDocumento cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TipoDocumento result = tipoDocumentoRepository.save(tipoDocumento);
        return ResponseEntity.created(new URI("/api/tipo-documentos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /tipo-documentos} : Updates an existing tipoDocumento.
     *
     * @param tipoDocumento the tipoDocumento to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated tipoDocumento,
     * or with status {@code 400 (Bad Request)} if the tipoDocumento is not valid,
     * or with status {@code 500 (Internal Server Error)} if the tipoDocumento couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/tipo-documentos")
    public ResponseEntity<TipoDocumento> updateTipoDocumento(@Valid @RequestBody TipoDocumento tipoDocumento) throws URISyntaxException {
        log.debug("REST request to update TipoDocumento : {}", tipoDocumento);
        if (tipoDocumento.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TipoDocumento result = tipoDocumentoRepository.save(tipoDocumento);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, tipoDocumento.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /tipo-documentos} : get all the tipoDocumentos.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of tipoDocumentos in body.
     */
    @GetMapping("/tipo-documentos")
    public List<TipoDocumento> getAllTipoDocumentos() {
        log.debug("REST request to get all TipoDocumentos");
        return tipoDocumentoRepository.findAll();
    }

    /**
     * {@code GET  /tipo-documentos/:id} : get the "id" tipoDocumento.
     *
     * @param id the id of the tipoDocumento to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the tipoDocumento, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/tipo-documentos/{id}")
    public ResponseEntity<TipoDocumento> getTipoDocumento(@PathVariable Long id) {
        log.debug("REST request to get TipoDocumento : {}", id);
        Optional<TipoDocumento> tipoDocumento = tipoDocumentoRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(tipoDocumento);
    }

    /**
     * {@code DELETE  /tipo-documentos/:id} : delete the "id" tipoDocumento.
     *
     * @param id the id of the tipoDocumento to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/tipo-documentos/{id}")
    public ResponseEntity<Void> deleteTipoDocumento(@PathVariable Long id) {
        log.debug("REST request to delete TipoDocumento : {}", id);
        tipoDocumentoRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
