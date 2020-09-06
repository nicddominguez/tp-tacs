package tp.tacs.api.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import tp.tacs.api.model.MunicipioEnJuegoModel;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * AtacarMunicipioResponse
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2020-09-05T20:37:59.553716700-03:00[America/Buenos_Aires]")


public class AtacarMunicipioResponse   {
  @JsonProperty("municipioAtacante")
  private MunicipioEnJuegoModel municipioAtacante = null;

  @JsonProperty("municipioAtacado")
  private MunicipioEnJuegoModel municipioAtacado = null;

  public AtacarMunicipioResponse municipioAtacante(MunicipioEnJuegoModel municipioAtacante) {
    this.municipioAtacante = municipioAtacante;
    return this;
  }

  /**
   * Get municipioAtacante
   * @return municipioAtacante
  **/
  @ApiModelProperty(value = "")
  
    @Valid
    public MunicipioEnJuegoModel getMunicipioAtacante() {
    return municipioAtacante;
  }

  public void setMunicipioAtacante(MunicipioEnJuegoModel municipioAtacante) {
    this.municipioAtacante = municipioAtacante;
  }

  public AtacarMunicipioResponse municipioAtacado(MunicipioEnJuegoModel municipioAtacado) {
    this.municipioAtacado = municipioAtacado;
    return this;
  }

  /**
   * Get municipioAtacado
   * @return municipioAtacado
  **/
  @ApiModelProperty(value = "")
  
    @Valid
    public MunicipioEnJuegoModel getMunicipioAtacado() {
    return municipioAtacado;
  }

  public void setMunicipioAtacado(MunicipioEnJuegoModel municipioAtacado) {
    this.municipioAtacado = municipioAtacado;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    AtacarMunicipioResponse atacarMunicipioResponse = (AtacarMunicipioResponse) o;
    return Objects.equals(this.municipioAtacante, atacarMunicipioResponse.municipioAtacante) &&
        Objects.equals(this.municipioAtacado, atacarMunicipioResponse.municipioAtacado);
  }

  @Override
  public int hashCode() {
    return Objects.hash(municipioAtacante, municipioAtacado);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class AtacarMunicipioResponse {\n");
    
    sb.append("    municipioAtacante: ").append(toIndentedString(municipioAtacante)).append("\n");
    sb.append("    municipioAtacado: ").append(toIndentedString(municipioAtacado)).append("\n");
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
