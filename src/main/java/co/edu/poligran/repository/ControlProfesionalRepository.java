package co.edu.poligran.repository;

import co.edu.poligran.domain.ControlProfesional;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the ControlProfesional entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ControlProfesionalRepository extends JpaRepository<ControlProfesional, Long> {
}
