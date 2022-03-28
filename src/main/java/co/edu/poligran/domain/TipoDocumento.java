package co.edu.poligran.domain;


import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import co.edu.poligran.domain.enumeration.State;

/**
 * A TipoDocumento.
 */
@Entity
@Table(name = "tipo_documento")
public class TipoDocumento implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Size(max = 20)
    @Column(name = "iniciales", length = 20, nullable = false, unique = true)
    private String iniciales;

    @NotNull
    @Size(max = 20)
    @Column(name = "nombre_documento", length = 20, nullable = false, unique = true)
    private String nombreDocumento;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "estado_tipo_docuemnto", nullable = false)
    private State estadoTipoDocuemnto;

    @OneToMany(mappedBy = "typoDocumento")
    private Set<Cliente> clientes = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIniciales() {
        return iniciales;
    }

    public TipoDocumento iniciales(String iniciales) {
        this.iniciales = iniciales;
        return this;
    }

    public void setIniciales(String iniciales) {
        this.iniciales = iniciales;
    }

    public String getNombreDocumento() {
        return nombreDocumento;
    }

    public TipoDocumento nombreDocumento(String nombreDocumento) {
        this.nombreDocumento = nombreDocumento;
        return this;
    }

    public void setNombreDocumento(String nombreDocumento) {
        this.nombreDocumento = nombreDocumento;
    }

    public State getEstadoTipoDocuemnto() {
        return estadoTipoDocuemnto;
    }

    public TipoDocumento estadoTipoDocuemnto(State estadoTipoDocuemnto) {
        this.estadoTipoDocuemnto = estadoTipoDocuemnto;
        return this;
    }

    public void setEstadoTipoDocuemnto(State estadoTipoDocuemnto) {
        this.estadoTipoDocuemnto = estadoTipoDocuemnto;
    }

    public Set<Cliente> getClientes() {
        return clientes;
    }

    public TipoDocumento clientes(Set<Cliente> clientes) {
        this.clientes = clientes;
        return this;
    }

    public TipoDocumento addClientes(Cliente cliente) {
        this.clientes.add(cliente);
        cliente.setTypoDocumento(this);
        return this;
    }

    public TipoDocumento removeClientes(Cliente cliente) {
        this.clientes.remove(cliente);
        cliente.setTypoDocumento(null);
        return this;
    }

    public void setClientes(Set<Cliente> clientes) {
        this.clientes = clientes;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TipoDocumento)) {
            return false;
        }
        return id != null && id.equals(((TipoDocumento) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TipoDocumento{" +
            "id=" + getId() +
            ", iniciales='" + getIniciales() + "'" +
            ", nombreDocumento='" + getNombreDocumento() + "'" +
            ", estadoTipoDocuemnto='" + getEstadoTipoDocuemnto() + "'" +
            "}";
    }
}
