package tp.tacs.api.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.util.Objects;

/**
 * ActualizarEstadoPartida
 */
@Validated


public class ActualizarEstadoPartida   {
  @JsonProperty("estado")
  private EstadoDeJuegoModel estado = null;

  public ActualizarEstadoPartida estado(EstadoDeJuegoModel estado) {
    this.estado = estado;
    return this;
  }

  /**
   * Get estado
   * @return estado
  **/
  @ApiModelProperty(value = "")
  
    @Valid
    public EstadoDeJuegoModel getEstado() {
    return estado;
  }

  public void setEstado(EstadoDeJuegoModel estado) {
    this.estado = estado;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ActualizarEstadoPartida actualizarEstadoPartida = (ActualizarEstadoPartida) o;
    return Objects.equals(this.estado, actualizarEstadoPartida.estado);
  }

  @Override
  public int hashCode() {
    return Objects.hash(estado);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ActualizarEstadoPartida {\n");
    
    sb.append("    estado: ").append(toIndentedString(estado)).append("\n");
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
