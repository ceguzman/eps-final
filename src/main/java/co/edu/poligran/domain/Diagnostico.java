package co.edu.poligran.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Diagnostico.
 */
@Entity
@Table(name = "diagnostico")
public class Diagnostico implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Size(max = 100)
    @Column(name = "resultado_laboratorio", length = 100, nullable = false)
    private String resultadoLaboratorio;

    @NotNull
    @Size(max = 500)
    @Column(name = "url_imagen", length = 500, nullable = false)
    private String urlImagen;

    @NotNull
    @Size(max = 240)
    @Column(name = "tipo_condicion_salud", length = 240, nullable = false)
    private String tipoCondicionSalud;

    @OneToMany(mappedBy = "diagnosticos")
    private Set<SeguimientoCondicion> seguimientoCondiciones = new HashSet<>();

    @OneToMany(mappedBy = "diagnostico")
    private Set<IndicadoresSalud> indicadoresSaluds = new HashSet<>();

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = "diagnosticos", allowSetters = true)
    private Cliente cliente;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getResultadoLaboratorio() {
        return resultadoLaboratorio;
    }

    public Diagnostico resultadoLaboratorio(String resultadoLaboratorio) {
        this.resultadoLaboratorio = resultadoLaboratorio;
        return this;
    }

    public void setResultadoLaboratorio(String resultadoLaboratorio) {
        this.resultadoLaboratorio = resultadoLaboratorio;
    }

    public String getUrlImagen() {
        return urlImagen;
    }

    public Diagnostico urlImagen(String urlImagen) {
        this.urlImagen = urlImagen;
        return this;
    }

    public void setUrlImagen(String urlImagen) {
        this.urlImagen = urlImagen;
    }

    public String getTipoCondicionSalud() {
        return tipoCondicionSalud;
    }

    public Diagnostico tipoCondicionSalud(String tipoCondicionSalud) {
        this.tipoCondicionSalud = tipoCondicionSalud;
        return this;
    }

    public void setTipoCondicionSalud(String tipoCondicionSalud) {
        this.tipoCondicionSalud = tipoCondicionSalud;
    }

    public Set<SeguimientoCondicion> getSeguimientoCondiciones() {
        return seguimientoCondiciones;
    }

    public Diagnostico seguimientoCondiciones(Set<SeguimientoCondicion> seguimientoCondicions) {
        this.seguimientoCondiciones = seguimientoCondicions;
        return this;
    }

    public Diagnostico addSeguimientoCondiciones(SeguimientoCondicion seguimientoCondicion) {
        this.seguimientoCondiciones.add(seguimientoCondicion);
        seguimientoCondicion.setDiagnosticos(this);
        return this;
    }

    public Diagnostico removeSeguimientoCondiciones(SeguimientoCondicion seguimientoCondicion) {
        this.seguimientoCondiciones.remove(seguimientoCondicion);
        seguimientoCondicion.setDiagnosticos(null);
        return this;
    }

    public void setSeguimientoCondiciones(Set<SeguimientoCondicion> seguimientoCondicions) {
        this.seguimientoCondiciones = seguimientoCondicions;
    }

    public Set<IndicadoresSalud> getIndicadoresSaluds() {
        return indicadoresSaluds;
    }

    public Diagnostico indicadoresSaluds(Set<IndicadoresSalud> indicadoresSaluds) {
        this.indicadoresSaluds = indicadoresSaluds;
        return this;
    }

    public Diagnostico addIndicadoresSalud(IndicadoresSalud indicadoresSalud) {
        this.indicadoresSaluds.add(indicadoresSalud);
        indicadoresSalud.setDiagnostico(this);
        return this;
    }

    public Diagnostico removeIndicadoresSalud(IndicadoresSalud indicadoresSalud) {
        this.indicadoresSaluds.remove(indicadoresSalud);
        indicadoresSalud.setDiagnostico(null);
        return this;
    }

    public void setIndicadoresSaluds(Set<IndicadoresSalud> indicadoresSaluds) {
        this.indicadoresSaluds = indicadoresSaluds;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public Diagnostico cliente(Cliente cliente) {
        this.cliente = cliente;
        return this;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Diagnostico)) {
            return false;
        }
        return id != null && id.equals(((Diagnostico) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Diagnostico{" +
            "id=" + getId() +
            ", resultadoLaboratorio='" + getResultadoLaboratorio() + "'" +
            ", urlImagen='" + getUrlImagen() + "'" +
            ", tipoCondicionSalud='" + getTipoCondicionSalud() + "'" +
            "}";
    }
}
