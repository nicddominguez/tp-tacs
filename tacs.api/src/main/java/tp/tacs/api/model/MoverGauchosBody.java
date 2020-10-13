package tp.tacs.api.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.validation.annotation.Validated;

import java.util.Objects;

/**
 * MoverGauchosBody
 */
@Validated


public class MoverGauchosBody   {
  @JsonProperty("idMunicipioOrigen")
  private String idMunicipioOrigen = null;

  @JsonProperty("idMunicipioDestino")
  private String idMunicipioDestino = null;

  @JsonProperty("cantidad")
  private Long cantidad = null;

  public MoverGauchosBody idMunicipioOrigen(String idMunicipioOrigen) {
    this.idMunicipioOrigen = idMunicipioOrigen;
    return this;
  }

  /**
   * Id del municipio de origen
   * @return idMunicipioOrigen
  **/
  @ApiModelProperty(value = "Id del municipio de origen")
  
    public String getIdMunicipioOrigen() {
    return idMunicipioOrigen;
  }

  public void setIdMunicipioOrigen(String idMunicipioOrigen) {
    this.idMunicipioOrigen = idMunicipioOrigen;
  }

  public MoverGauchosBody idMunicipioDestino(String idMunicipioDestino) {
    this.idMunicipioDestino = idMunicipioDestino;
    return this;
  }

  /**
   * Id del municipio de destino
   * @return idMunicipioDestino
  **/
  @ApiModelProperty(value = "Id del municipio de destino")
  
    public String getIdMunicipioDestino() {
    return idMunicipioDestino;
  }

  public void setIdMunicipioDestino(String idMunicipioDestino) {
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
  public boolean equals(Object o) {
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
  private String toIndentedString(Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
}
