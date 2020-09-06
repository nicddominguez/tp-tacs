package tp.tacs.api.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * MoverGauchosBody
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2020-09-05T20:37:59.553716700-03:00[America/Buenos_Aires]")


public class MoverGauchosBody   {
  @JsonProperty("idMunicipioOrigen")
  private Long idMunicipioOrigen = null;

  @JsonProperty("idMunicipioDestino")
  private Long idMunicipioDestino = null;

  @JsonProperty("cantidad")
  private Long cantidad = null;

  public MoverGauchosBody idMunicipioOrigen(Long idMunicipioOrigen) {
    this.idMunicipioOrigen = idMunicipioOrigen;
    return this;
  }

  /**
   * Id del municipio de origen
   * @return idMunicipioOrigen
  **/
  @ApiModelProperty(value = "Id del municipio de origen")
  
    public Long getIdMunicipioOrigen() {
    return idMunicipioOrigen;
  }

  public void setIdMunicipioOrigen(Long idMunicipioOrigen) {
    this.idMunicipioOrigen = idMunicipioOrigen;
  }

  public MoverGauchosBody idMunicipioDestino(Long idMunicipioDestino) {
    this.idMunicipioDestino = idMunicipioDestino;
    return this;
  }

  /**
   * Id del municipio de destino
   * @return idMunicipioDestino
  **/
  @ApiModelProperty(value = "Id del municipio de destino")
  
    public Long getIdMunicipioDestino() {
    return idMunicipioDestino;
  }

  public void setIdMunicipioDestino(Long idMunicipioDestino) {
    this.idMunicipioDestino = idMunicipioDestino;
  }

  public MoverGauchosBody cantidad(Long cantidad) {
    this.cantidad = cantidad;
    return this;
  }

  /**
   * Cantidad de gauchos a mover
   * @return cantidad
  **/
  @ApiModelProperty(value = "Cantidad de gauchos a mover")
  
    public Long getCantidad() {
    return cantidad;
  }

  public void setCantidad(Long cantidad) {
    this.cantidad = cantidad;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    MoverGauchosBody moverGauchosBody = (MoverGauchosBody) o;
    return Objects.equals(this.idMunicipioOrigen, moverGauchosBody.idMunicipioOrigen) &&
        Objects.equals(this.idMunicipioDestino, moverGauchosBody.idMunicipioDestino) &&
        Objects.equals(this.cantidad, moverGauchosBody.cantidad);
  }

  @Override
  public int hashCode() {
    return Objects.hash(idMunicipioOrigen, idMunicipioDestino, cantidad);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class MoverGauchosBody {\n");
    
    sb.append("    idMunicipioOrigen: ").append(toIndentedString(idMunicipioOrigen)).append("\n");
    sb.append("    idMunicipioDestino: ").append(toIndentedString(idMunicipioDestino)).append("\n");
    sb.append("    cantidad: ").append(toIndentedString(cantidad)).append("\n");
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
