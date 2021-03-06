package tp.tacs.api.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import tp.tacs.api.model.ModoDeMunicipioModel;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * ActualizarMunicipio
 */
@Validated


public class ActualizarMunicipio   {
  @JsonProperty("modo")
  private ModoDeMunicipioModel modo = null;

  @JsonProperty("estaBloqueado")
  private Boolean estaBloqueado = null;

  public ActualizarMunicipio modo(ModoDeMunicipioModel modo) {
    this.modo = modo;
    return this;
  }

  /**
   * Get modo
   * @return modo
  **/
  @ApiModelProperty(value = "")
  
    @Valid
    public ModoDeMunicipioModel getModo() {
    return modo;
  }

  public void setModo(ModoDeMunicipioModel modo) {
    this.modo = modo;
  }

  public ActualizarMunicipio estaBloqueado(Boolean estaBloqueado) {
    this.estaBloqueado = estaBloqueado;
    return this;
  }

  /**
   * Get estaBloqueado
   * @return estaBloqueado
  **/
  @ApiModelProperty(value = "")
  
    public Boolean isEstaBloqueado() {
    return estaBloqueado;
  }

  public void setEstaBloqueado(Boolean estaBloqueado) {
    this.estaBloqueado = estaBloqueado;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ActualizarMunicipio actualizarMunicipio = (ActualizarMunicipio) o;
    return Objects.equals(this.modo, actualizarMunicipio.modo) &&
        Objects.equals(this.estaBloqueado, actualizarMunicipio.estaBloqueado);
  }

  @Override
  public int hashCode() {
    return Objects.hash(modo, estaBloqueado);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ActualizarMunicipio {\n");
    
    sb.append("    modo: ").append(toIndentedString(modo)).append("\n");
    sb.append("    estaBloqueado: ").append(toIndentedString(estaBloqueado)).append("\n");
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
