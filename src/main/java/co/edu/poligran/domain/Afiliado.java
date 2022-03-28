package co.edu.poligran.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

/**
 * A Afiliado.
 */
@Entity
@Table(name = "afiliado")
public class Afiliado implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Size(max = 100)
    @Column(name = "parentesco", length = 100, nullable = false)
    private String parentesco;

    @NotNull
    @Size(max = 180)
    @Column(name = "correo_electronico", length = 180, nullable = false, unique = true)
    private String correoElectronico;

    @NotNull
    @Size(max = 250)
    @Column(name = "nombre_afiliado", length = 250, nullable = false)
    private String nombreAfiliado;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = "afiliados", allowSetters = true)
    private Cliente cliente;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getParentesco() {
        return parentesco;
    }

    public Afiliado parentesco(String parentesco) {
        this.parentesco = parentesco;
        return this;
    }

    public void setParentesco(String parentesco) {
        this.parentesco = parentesco;
    }

    public String getCorreoElectronico() {
        return correoElectronico;
    }

    public Afiliado correoElectronico(String correoElectronico) {
        this.correoElectronico = correoElectronico;
        return this;
    }

    public void setCorreoElectronico(String correoElectronico) {
        this.correoElectronico = correoElectronico;
    }

    public String getNombreAfiliado() {
        return nombreAfiliado;
    }

    public Afiliado nombreAfiliado(String nombreAfiliado) {
        this.nombreAfiliado = nombreAfiliado;
        return this;
    }

    public void setNombreAfiliado(String nombreAfiliado) {
        this.nombreAfiliado = nombreAfiliado;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public Afiliado cliente(Cliente cliente) {
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
        if (!(o instanceof Afiliado)) {
            return false;
        }
        return id != null && id.equals(((Afiliado) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Afiliado{" +
            "id=" + getId() +
            ", parentesco='" + getParentesco() + "'" +
            ", correoElectronico='" + getCorreoElectronico() + "'" +
            ", nombreAfiliado='" + getNombreAfiliado() + "'" +
            "}";
    }
}
