package tp.tacs.api.model;

import java.util.Objects;

import org.springframework.validation.annotation.Validated;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModelProperty;

/**
 * AtacarMunicipioBody
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2020-08-29T21:54:39.417-03:00[America/Buenos_Aires]")
public class AtacarMunicipioBody   {
  @JsonProperty("idMunicipioAtacante")
  private Integer idMunicipioAtacante = null;

  @JsonProperty("idMunicipioObjetivo")
  private Integer idMunicipioObjetivo = null;

  public AtacarMunicipioBody idMunicipioAtacante(Integer idMunicipioAtacante) {
    this.idMunicipioAtacante = idMunicipioAtacante;
    return this;
  }

  /**
   * Id del municipio que ataca
   * @return idMunicipioAtacante
  **/
  @ApiModelProperty(value = "Id del municipio que ataca")
  
    public Integer getIdMunicipioAtacante() {
    return idMunicipioAtacante;
  }

  public void setIdMunicipioAtacante(Integer idMunicipioAtacante) {
    this.idMunicipioAtacante = idMunicipioAtacante;
  }

  public AtacarMunicipioBody idMunicipioObjetivo(Integer idMunicipioObjetivo) {
    this.idMunicipioObjetivo = idMunicipioObjetivo;
    return this;
  }

  /**
   * Id del municipio atacado
   * @return idMunicipioObjetivo
  **/
  @ApiModelProperty(value = "Id del municipio atacado")
  
    public Integer getIdMunicipioObjetivo() {
    return idMunicipioObjetivo;
  }

  public void setIdMunicipioObjetivo(Integer idMunicipioObjetivo) {
    this.idMunicipioObjetivo = idMunicipioObjetivo;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    AtacarMunicipioBody atacarMunicipioBody = (AtacarMunicipioBody) o;
    return Objects.equals(this.idMunicipioAtacante, atacarMunicipioBody.idMunicipioAtacante) &&
        Objects.equals(this.idMunicipioObjetivo, atacarMunicipioBody.idMunicipioObjetivo);
  }

  @Override
  public int hashCode() {
    return Objects.hash(idMunicipioAtacante, idMunicipioObjetivo);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class AtacarMunicipioBody {\n");
    
    sb.append("    idMunicipioAtacante: ").append(toIndentedString(idMunicipioAtacante)).append("\n");
    sb.append("    idMunicipioObjetivo: ").append(toIndentedString(idMunicipioObjetivo)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(java.lang.Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
}