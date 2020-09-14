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
 * MoverGauchosResponse
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2020-09-14T19:00:38.696940-03:00[America/Buenos_Aires]")


public class MoverGauchosResponse   {
  @JsonProperty("municipioOrigen")
  private MunicipioEnJuegoModel municipioOrigen = null;

  @JsonProperty("municipioDestino")
  private MunicipioEnJuegoModel municipioDestino = null;

  public MoverGauchosResponse municipioOrigen(MunicipioEnJuegoModel municipioOrigen) {
    this.municipioOrigen = municipioOrigen;
    return this;
  }

  /**
   * Get municipioOrigen
   * @return municipioOrigen
  **/
  @ApiModelProperty(value = "")
  
    @Valid
    public MunicipioEnJuegoModel getMunicipioOrigen() {
    return municipioOrigen;
  }

  public void setMunicipioOrigen(MunicipioEnJuegoModel municipioOrigen) {
    this.municipioOrigen = municipioOrigen;
  }

  public MoverGauchosResponse municipioDestino(MunicipioEnJuegoModel municipioDestino) {
    this.municipioDestino = municipioDestino;
    return this;
  }

  /**
   * Get municipioDestino
   * @return municipioDestino
  **/
  @ApiModelProperty(value = "")
  
    @Valid
    public MunicipioEnJuegoModel getMunicipioDestino() {
    return municipioDestino;
  }

  public void setMunicipioDestino(MunicipioEnJuegoModel municipioDestino) {
    this.municipioDestino = municipioDestino;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    MoverGauchosResponse moverGauchosResponse = (MoverGauchosResponse) o;
    return Objects.equals(this.municipioOrigen, moverGauchosResponse.municipioOrigen) &&
        Objects.equals(this.municipioDestino, moverGauchosResponse.municipioDestino);
  }

  @Override
  public int hashCode() {
    return Objects.hash(municipioOrigen, municipioDestino);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class MoverGauchosResponse {\n");
    
    sb.append("    municipioOrigen: ").append(toIndentedString(municipioOrigen)).append("\n");
    sb.append("    municipioDestino: ").append(toIndentedString(municipioDestino)).append("\n");
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
