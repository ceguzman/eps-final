package co.edu.poligran.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Cliente.
 */
@Entity
@Table(name = "cliente")
public class Cliente implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Size(max = 255)
    @Column(name = "numero_documento", length = 255, nullable = false, unique = true)
    private String numeroDocumento;

    @NotNull
    @Size(max = 50)
    @Column(name = "primer_nombre", length = 50, nullable = false)
    private String primerNombre;

    @NotNull
    @Size(max = 50)
    @Column(name = "primer_apellido", length = 50, nullable = false)
    private String primerApellido;

    @Size(max = 50)
    @Column(name = "segundo_nombre", length = 50)
    private String segundoNombre;

    @Size(max = 50)
    @Column(name = "segundo_apellido", length = 50)
    private String segundoApellido;

    @Lob
    @Column(name = "img_url")
    private byte[] imgUrl;

    @Column(name = "img_url_content_type")
    private String imgUrlContentType;

    @OneToMany(mappedBy = "cliente")
    private Set<Afiliado> afiliados = new HashSet<>();

    @OneToMany(mappedBy = "cliente")
    private Set<Diagnostico> diagnosticos = new HashSet<>();

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = "clientes", allowSetters = true)
    private TipoDocumento typoDocumento;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumeroDocumento() {
        return numeroDocumento;
    }

    public Cliente numeroDocumento(String numeroDocumento) {
        this.numeroDocumento = numeroDocumento;
        return this;
    }

    public void setNumeroDocumento(String numeroDocumento) {
        this.numeroDocumento = numeroDocumento;
    }

    public String getPrimerNombre() {
        return primerNombre;
    }

    public Cliente primerNombre(String primerNombre) {
        this.primerNombre = primerNombre;
        return this;
    }

    public void setPrimerNombre(String primerNombre) {
        this.primerNombre = primerNombre;
    }

    public String getPrimerApellido() {
        return primerApellido;
    }

    public Cliente primerApellido(String primerApellido) {
        this.primerApellido = primerApellido;
        return this;
    }

    public void setPrimerApellido(String primerApellido) {
        this.primerApellido = primerApellido;
    }

    public String getSegundoNombre() {
        return segundoNombre;
    }

    public Cliente segundoNombre(String segundoNombre) {
        this.segundoNombre = segundoNombre;
        return this;
    }

    public void setSegundoNombre(String segundoNombre) {
        this.segundoNombre = segundoNombre;
    }

    public String getSegundoApellido() {
        return segundoApellido;
    }

    public Cliente segundoApellido(String segundoApellido) {
        this.segundoApellido = segundoApellido;
        return this;
    }

    public void setSegundoApellido(String segundoApellido) {
        this.segundoApellido = segundoApellido;
    }

    public byte[] getImgUrl() {
        return imgUrl;
    }

    public Cliente imgUrl(byte[] imgUrl) {
        this.imgUrl = imgUrl;
        return this;
    }

    public void setImgUrl(byte[] imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getImgUrlContentType() {
        return imgUrlContentType;
    }

    public Cliente imgUrlContentType(String imgUrlContentType) {
        this.imgUrlContentType = imgUrlContentType;
        return this;
    }

    public void setImgUrlContentType(String imgUrlContentType) {
        this.imgUrlContentType = imgUrlContentType;
    }

    public Set<Afiliado> getAfiliados() {
        return afiliados;
    }

    public Cliente afiliados(Set<Afiliado> afiliados) {
        this.afiliados = afiliados;
        return this;
    }

    public Cliente addAfiliados(Afiliado afiliado) {
        this.afiliados.add(afiliado);
        afiliado.setCliente(this);
        return this;
    }

    public Cliente removeAfiliados(Afiliado afiliado) {
        this.afiliados.remove(afiliado);
        afiliado.setCliente(null);
        return this;
    }

    public void setAfiliados(Set<Afiliado> afiliados) {
        this.afiliados = afiliados;
    }

    public Set<Diagnostico> getDiagnosticos() {
        return diagnosticos;
    }

    public Cliente diagnosticos(Set<Diagnostico> diagnosticos) {
        this.diagnosticos = diagnosticos;
        return this;
    }

    public Cliente addDiagnosticos(Diagnostico diagnostico) {
        this.diagnosticos.add(diagnostico);
        diagnostico.setCliente(this);
        return this;
    }

    public Cliente removeDiagnosticos(Diagnostico diagnostico) {
        this.diagnosticos.remove(diagnostico);
        diagnostico.setCliente(null);
        return this;
    }

    public void setDiagnosticos(Set<Diagnostico> diagnosticos) {
        this.diagnosticos = diagnosticos;
    }

    public TipoDocumento getTypoDocumento() {
        return typoDocumento;
    }

    public Cliente typoDocumento(TipoDocumento tipoDocumento) {
        this.typoDocumento = tipoDocumento;
        return this;
    }

    public void setTypoDocumento(TipoDocumento tipoDocumento) {
        this.typoDocumento = tipoDocumento;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Cliente)) {
            return false;
        }
        return id != null && id.equals(((Cliente) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Cliente{" +
            "id=" + getId() +
            ", numeroDocumento='" + getNumeroDocumento() + "'" +
            ", primerNombre='" + getPrimerNombre() + "'" +
            ", primerApellido='" + getPrimerApellido() + "'" +
            ", segundoNombre='" + getSegundoNombre() + "'" +
            ", segundoApellido='" + getSegundoApellido() + "'" +
            ", imgUrl='" + getImgUrl() + "'" +
            ", imgUrlContentType='" + getImgUrlContentType() + "'" +
            "}";
    }
}
