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
  private String idMunicipioAtacante = null;

  @JsonProperty("idMunicipioObjetivo")
  private String idMunicipioObjetivo = null;

  public SimularAtacarMunicipioBody idMunicipioAtacante(String idMunicipioAtacante) {
    this.idMunicipioAtacante = idMunicipioAtacante;
    return this;
  }

  /**
   * Id del municipio que ataca
   * @return idMunicipioAtacante
  **/
  @ApiModelProperty(value = "Id del municipio que ataca")
  
    public String getIdMunicipioAtacante() {
    return idMunicipioAtacante;
  }

  public void setIdMunicipioAtacante(String idMunicipioAtacante) {
    this.idMunicipioAtacante = idMunicipioAtacante;
  }

  public SimularAtacarMunicipioBody idMunicipioObjetivo(String idMunicipioObjetivo) {
    this.idMunicipioObjetivo = idMunicipioObjetivo;
    return this;
  }

  /**
   * Id del municipio atacado
   * @return idMunicipioObjetivo
  **/
  @ApiModelProperty(value = "Id del municipio atacado")
  
    public String getIdMunicipioObjetivo() {
    return idMunicipioObjetivo;
  }

  public void setIdMunicipioObjetivo(String idMunicipioObjetivo) {
    this.idMunicipioObjetivo = idMunicipioObjetivo;
  }


  @Override
  public boolean equals(Object o) {
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
  private String toIndentedString(Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
}
