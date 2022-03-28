package co.edu.poligran.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A IndicadoresSalud.
 */
@Entity
@Table(name = "indicadores_salud")
public class IndicadoresSalud implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Size(max = 200)
    @Column(name = "frecuencia_cardiaca", length = 200, nullable = false)
    private String frecuenciaCardiaca;

    @NotNull
    @Size(max = 200)
    @Column(name = "tencion_arterial", length = 200, nullable = false)
    private String tencionArterial;

    @NotNull
    @Size(max = 255)
    @Column(name = "saturacion_oxigeno", length = 255, nullable = false)
    private String saturacionOxigeno;

    @NotNull
    @Size(max = 100)
    @Column(name = "vacunas", length = 100, nullable = false)
    private String vacunas;

    @NotNull
    @Size(max = 30)
    @Column(name = "distancia_recorrida", length = 30, nullable = false)
    private String distanciaRecorrida;

    @OneToMany(mappedBy = "indicadorSalud")
    private Set<ControlProfesional> controlesProfesionales = new HashSet<>();

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = "indicadoresSaluds", allowSetters = true)
    private Diagnostico diagnostico;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFrecuenciaCardiaca() {
        return frecuenciaCardiaca;
    }

    public IndicadoresSalud frecuenciaCardiaca(String frecuenciaCardiaca) {
        this.frecuenciaCardiaca = frecuenciaCardiaca;
        return this;
    }

    public void setFrecuenciaCardiaca(String frecuenciaCardiaca) {
        this.frecuenciaCardiaca = frecuenciaCardiaca;
    }

    public String getTencionArterial() {
        return tencionArterial;
    }

    public IndicadoresSalud tencionArterial(String tencionArterial) {
        this.tencionArterial = tencionArterial;
        return this;
    }

    public void setTencionArterial(String tencionArterial) {
        this.tencionArterial = tencionArterial;
    }

    public String getSaturacionOxigeno() {
        return saturacionOxigeno;
    }

    public IndicadoresSalud saturacionOxigeno(String saturacionOxigeno) {
        this.saturacionOxigeno = saturacionOxigeno;
        return this;
    }

    public void setSaturacionOxigeno(String saturacionOxigeno) {
        this.saturacionOxigeno = saturacionOxigeno;
    }

    public String getVacunas() {
        return vacunas;
    }

    public IndicadoresSalud vacunas(String vacunas) {
        this.vacunas = vacunas;
        return this;
    }

    public void setVacunas(String vacunas) {
        this.vacunas = vacunas;
    }

    public String getDistanciaRecorrida() {
        return distanciaRecorrida;
    }

    public IndicadoresSalud distanciaRecorrida(String distanciaRecorrida) {
        this.distanciaRecorrida = distanciaRecorrida;
        return this;
    }

    public void setDistanciaRecorrida(String distanciaRecorrida) {
        this.distanciaRecorrida = distanciaRecorrida;
    }

    public Set<ControlProfesional> getControlesProfesionales() {
        return controlesProfesionales;
    }

    public IndicadoresSalud controlesProfesionales(Set<ControlProfesional> controlProfesionals) {
        this.controlesProfesionales = controlProfesionals;
        return this;
    }

    public IndicadoresSalud addControlesProfesionales(ControlProfesional controlProfesional) {
        this.controlesProfesionales.add(controlProfesional);
        controlProfesional.setIndicadorSalud(this);
        return this;
    }

    public IndicadoresSalud removeControlesProfesionales(ControlProfesional controlProfesional) {
        this.controlesProfesionales.remove(controlProfesional);
        controlProfesional.setIndicadorSalud(null);
        return this;
    }

    public void setControlesProfesionales(Set<ControlProfesional> controlProfesionals) {
        this.controlesProfesionales = controlProfesionals;
    }

    public Diagnostico getDiagnostico() {
        return diagnostico;
    }

    public IndicadoresSalud diagnostico(Diagnostico diagnostico) {
        this.diagnostico = diagnostico;
        return this;
    }

    public void setDiagnostico(Diagnostico diagnostico) {
        this.diagnostico = diagnostico;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof IndicadoresSalud)) {
            return false;
        }
        return id != null && id.equals(((IndicadoresSalud) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "IndicadoresSalud{" +
            "id=" + getId() +
            ", frecuenciaCardiaca='" + getFrecuenciaCardiaca() + "'" +
            ", tencionArterial='" + getTencionArterial() + "'" +
            ", saturacionOxigeno='" + getSaturacionOxigeno() + "'" +
            ", vacunas='" + getVacunas() + "'" +
            ", distanciaRecorrida='" + getDistanciaRecorrida() + "'" +
            "}";
    }
}
