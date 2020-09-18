package tp.tacs.api.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.validation.annotation.Validated;

import java.util.Objects;

/**
 * SimularAtacarMunicipioBody
 */
@Validated


public class SimularAtacarMunicipioBody   {
  @JsonProperty("idMunicipioAtacante")
  private Long idMunicipioAtacante = null;

  @JsonProperty("idMunicipioObjetivo")
  private Long idMunicipioObjetivo = null;

  public SimularAtacarMunicipioBody idMunicipioAtacante(Long idMunicipioAtacante) {
    this.idMunicipioAtacante = idMunicipioAtacante;
    return this;
  }

  /**
   * Id del municipio que ataca
   * @return idMunicipioAtacante
  **/
  @ApiModelProperty(value = "Id del municipio que ataca")
  
    public Long getIdMunicipioAtacante() {
    return idMunicipioAtacante;
  }

  public void setIdMunicipioAtacante(Long idMunicipioAtacante) {
    this.idMunicipioAtacante = idMunicipioAtacante;
  }

  public SimularAtacarMunicipioBody idMunicipioObjetivo(Long idMunicipioObjetivo) {
    this.idMunicipioObjetivo = idMunicipioObjetivo;
    return this;
  }

  /**
   * Id del municipio atacado
   * @return idMunicipioObjetivo
  **/
  @ApiModelProperty(value = "Id del municipio atacado")
  
    public Long getIdMunicipioObjetivo() {
    return idMunicipioObjetivo;
  }

  public void setIdMunicipioObjetivo(Long idMunicipioObjetivo) {
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
    SimularAtacarMunicipioBody simularAtacarMunicipioBody = (SimularAtacarMunicipioBody) o;
    return Objects.equals(this.idMunicipioAtacante, simularAtacarMunicipioBody.idMunicipioAtacante) &&
        Objects.equals(this.idMunicipioObjetivo, simularAtacarMunicipioBody.idMunicipioObjetivo);
  }

  @Override
  public int hashCode() {
    return Objects.hash(idMunicipioAtacante, idMunicipioObjetivo);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class SimularAtacarMunicipioBody {\n");
    
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
