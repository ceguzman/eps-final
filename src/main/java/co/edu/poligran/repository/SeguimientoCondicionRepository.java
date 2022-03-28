package co.edu.poligran.repository;

import co.edu.poligran.domain.SeguimientoCondicion;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the SeguimientoCondicion entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SeguimientoCondicionRepository extends JpaRepository<SeguimientoCondicion, Long> {
}
