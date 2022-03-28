package co.edu.poligran.repository;

import co.edu.poligran.domain.Afiliado;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Afiliado entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AfiliadoRepository extends JpaRepository<Afiliado, Long> {
}
