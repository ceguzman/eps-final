package co.edu.poligran.repository;

import co.edu.poligran.domain.IndicadoresSalud;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the IndicadoresSalud entity.
 */
@SuppressWarnings("unused")
@Repository
public interface IndicadoresSaludRepository extends JpaRepository<IndicadoresSalud, Long> {
}
