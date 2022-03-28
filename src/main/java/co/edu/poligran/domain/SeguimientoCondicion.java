package co.edu.poligran.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * A SeguimientoCondicion.
 */
@Entity
@Table(name = "seguimiento_condicion")
public class SeguimientoCondicion implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Size(max = 100)
    @Column(name = "estado_condicion", length = 100, nullable = false)
    private String estadoCondicion;

    @NotNull
    @Column(name = "fecha", nullable = false)
    private LocalDate fecha;

    @NotNull
    @Size(max = 255)
    @Column(name = "diagnostico", length = 255, nullable = false)
    private String diagnostico;

    @NotNull
    @Size(max = 255)
    @Column(name = "evolucion_tratamiento", length = 255, nullable = false)
    private String evolucionTratamiento;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = "seguimientoCondiciones", allowSetters = true)
    private Diagnostico diagnosticos;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEstadoCondicion() {
        return estadoCondicion;
    }

    public SeguimientoCondicion estadoCondicion(String estadoCondicion) {
        this.estadoCondicion = estadoCondicion;
        return this;
    }

    public void setEstadoCondicion(String estadoCondicion) {
        this.estadoCondicion = estadoCondicion;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public SeguimientoCondicion fecha(LocalDate fecha) {
        this.fecha = fecha;
        return this;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public String getDiagnostico() {
        return diagnostico;
    }

    public SeguimientoCondicion diagnostico(String diagnostico) {
        this.diagnostico = diagnostico;
        return this;
    }

    public void setDiagnostico(String diagnostico) {
        this.diagnostico = diagnostico;
    }

    public String getEvolucionTratamiento() {
        return evolucionTratamiento;
    }

    public SeguimientoCondicion evolucionTratamiento(String evolucionTratamiento) {
        this.evolucionTratamiento = evolucionTratamiento;
        return this;
    }

    public void setEvolucionTratamiento(String evolucionTratamiento) {
        this.evolucionTratamiento = evolucionTratamiento;
    }

    public Diagnostico getDiagnosticos() {
        return diagnosticos;
    }

    public SeguimientoCondicion diagnosticos(Diagnostico diagnostico) {
        this.diagnosticos = diagnostico;
        return this;
    }

    public void setDiagnosticos(Diagnostico diagnostico) {
        this.diagnosticos = diagnostico;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SeguimientoCondicion)) {
            return false;
        }
        return id != null && id.equals(((SeguimientoCondicion) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SeguimientoCondicion{" +
            "id=" + getId() +
            ", estadoCondicion='" + getEstadoCondicion() + "'" +
            ", fecha='" + getFecha() + "'" +
            ", diagnostico='" + getDiagnostico() + "'" +
            ", evolucionTratamiento='" + getEvolucionTratamiento() + "'" +
            "}";
    }
}
