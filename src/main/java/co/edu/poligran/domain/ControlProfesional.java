package co.edu.poligran.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * A ControlProfesional.
 */
@Entity
@Table(name = "control_profesional")
public class ControlProfesional implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Size(max = 200)
    @Column(name = "tipo_medico", length = 200, nullable = false)
    private String tipoMedico;

    @NotNull
    @Column(name = "fecha", nullable = false)
    private LocalDate fecha;

    @NotNull
    @Size(max = 250)
    @Column(name = "nombre_medico", length = 250, nullable = false)
    private String nombreMedico;

    @NotNull
    @Size(max = 200)
    @Column(name = "observaciones", length = 200, nullable = false)
    private String observaciones;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = "controlesProfesionales", allowSetters = true)
    private IndicadoresSalud indicadorSalud;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTipoMedico() {
        return tipoMedico;
    }

    public ControlProfesional tipoMedico(String tipoMedico) {
        this.tipoMedico = tipoMedico;
        return this;
    }

    public void setTipoMedico(String tipoMedico) {
        this.tipoMedico = tipoMedico;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public ControlProfesional fecha(LocalDate fecha) {
        this.fecha = fecha;
        return this;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public String getNombreMedico() {
        return nombreMedico;
    }

    public ControlProfesional nombreMedico(String nombreMedico) {
        this.nombreMedico = nombreMedico;
        return this;
    }

    public void setNombreMedico(String nombreMedico) {
        this.nombreMedico = nombreMedico;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public ControlProfesional observaciones(String observaciones) {
        this.observaciones = observaciones;
        return this;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public IndicadoresSalud getIndicadorSalud() {
        return indicadorSalud;
    }

    public ControlProfesional indicadorSalud(IndicadoresSalud indicadoresSalud) {
        this.indicadorSalud = indicadoresSalud;
        return this;
    }

    public void setIndicadorSalud(IndicadoresSalud indicadoresSalud) {
        this.indicadorSalud = indicadoresSalud;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ControlProfesional)) {
            return false;
        }
        return id != null && id.equals(((ControlProfesional) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ControlProfesional{" +
            "id=" + getId() +
            ", tipoMedico='" + getTipoMedico() + "'" +
            ", fecha='" + getFecha() + "'" +
            ", nombreMedico='" + getNombreMedico() + "'" +
            ", observaciones='" + getObservaciones() + "'" +
            "}";
    }
}
