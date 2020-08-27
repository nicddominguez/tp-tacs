package tp.tacs.api.models;

import java.util.Objects;

import org.springframework.validation.annotation.Validated;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModelProperty;

/**
 * EjecutarAccionEntreMunicipiosBody
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2020-08-27T18:00:32.244-03:00[America/Buenos_Aires]")
public class EjecutarAccionEntreMunicipiosBody   {
  @JsonProperty("idMunicipioOrigen")
  private Integer idMunicipioOrigen = null;

  @JsonProperty("idMunicipioObjetivo")
  private Integer idMunicipioObjetivo = null;

  @JsonProperty("action")
  private Object action = null;

  public EjecutarAccionEntreMunicipiosBody idMunicipioOrigen(Integer idMunicipioOrigen) {
    this.idMunicipioOrigen = idMunicipioOrigen;
    return this;
  }

  /**
   * Id del municipio de origen
   * @return idMunicipioOrigen
  **/
  @ApiModelProperty(value = "Id del municipio de origen")
  
    public Integer getIdMunicipioOrigen() {
    return idMunicipioOrigen;
  }

  public void setIdMunicipioOrigen(Integer idMunicipioOrigen) {
    this.idMunicipioOrigen = idMunicipioOrigen;
  }

  public EjecutarAccionEntreMunicipiosBody idMunicipioObjetivo(Integer idMunicipioObjetivo) {
    this.idMunicipioObjetivo = idMunicipioObjetivo;
    return this;
  }

  /**
   * Id del municipio objetivo
   * @return idMunicipioObjetivo
  **/
  @ApiModelProperty(value = "Id del municipio objetivo")
  
    public Integer getIdMunicipioObjetivo() {
    return idMunicipioObjetivo;
  }

  public void setIdMunicipioObjetivo(Integer idMunicipioObjetivo) {
    this.idMunicipioObjetivo = idMunicipioObjetivo;
  }

  public EjecutarAccionEntreMunicipiosBody action(Object action) {
    this.action = action;
    return this;
  }

  /**
   * Get action
   * @return action
  **/
  @ApiModelProperty(value = "")
  
    public Object getAction() {
    return action;
  }

  public void setAction(Object action) {
    this.action = action;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    EjecutarAccionEntreMunicipiosBody ejecutarAccionEntreMunicipiosBody = (EjecutarAccionEntreMunicipiosBody) o;
    return Objects.equals(this.idMunicipioOrigen, ejecutarAccionEntreMunicipiosBody.idMunicipioOrigen) &&
        Objects.equals(this.idMunicipioObjetivo, ejecutarAccionEntreMunicipiosBody.idMunicipioObjetivo) &&
        Objects.equals(this.action, ejecutarAccionEntreMunicipiosBody.action);
  }

  @Override
  public int hashCode() {
    return Objects.hash(idMunicipioOrigen, idMunicipioObjetivo, action);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class EjecutarAccionEntreMunicipiosBody {\n");
    
    sb.append("    idMunicipioOrigen: ").append(toIndentedString(idMunicipioOrigen)).append("\n");
    sb.append("    idMunicipioObjetivo: ").append(toIndentedString(idMunicipioObjetivo)).append("\n");
    sb.append("    action: ").append(toIndentedString(action)).append("\n");
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
