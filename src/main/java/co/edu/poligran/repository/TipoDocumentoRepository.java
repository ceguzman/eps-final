package co.edu.poligran.repository;

import co.edu.poligran.domain.TipoDocumento;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the TipoDocumento entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TipoDocumentoRepository extends JpaRepository<TipoDocumento, Long> {
}
