package tp.tacs.api.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import tp.tacs.api.model.UsuarioModel;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * EstadisticasDeUsuarioModel
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2020-09-10T19:10:01.693073800-03:00[America/Buenos_Aires]")


public class EstadisticasDeUsuarioModel   {
  @JsonProperty("usuario")
  private UsuarioModel usuario = null;

  @JsonProperty("partidasJugadas")
  private Long partidasJugadas = null;

  @JsonProperty("partidasGanadas")
  private Long partidasGanadas = null;

  @JsonProperty("rachaActual")
  private Long rachaActual = null;

  public EstadisticasDeUsuarioModel usuario(UsuarioModel usuario) {
    this.usuario = usuario;
    return this;
  }

  /**
   * Get usuario
   * @return usuario
  **/
  @ApiModelProperty(value = "")
  
    @Valid
    public UsuarioModel getUsuario() {
    return usuario;
  }

  public void setUsuario(UsuarioModel usuario) {
    this.usuario = usuario;
  }

  public EstadisticasDeUsuarioModel partidasJugadas(Long partidasJugadas) {
    this.partidasJugadas = partidasJugadas;
    return this;
  }

  /**
   * Get partidasJugadas
   * @return partidasJugadas
  **/
  @ApiModelProperty(value = "")
  
    public Long getPartidasJugadas() {
    return partidasJugadas;
  }

  public void setPartidasJugadas(Long partidasJugadas) {
    this.partidasJugadas = partidasJugadas;
  }

  public EstadisticasDeUsuarioModel partidasGanadas(Long partidasGanadas) {
    this.partidasGanadas = partidasGanadas;
    return this;
  }

  /**
   * Get partidasGanadas
   * @return partidasGanadas
  **/
  @ApiModelProperty(value = "")
  
    public Long getPartidasGanadas() {
    return partidasGanadas;
  }

  public void setPartidasGanadas(Long partidasGanadas) {
    this.partidasGanadas = partidasGanadas;
  }

  public EstadisticasDeUsuarioModel rachaActual(Long rachaActual) {
    this.rachaActual = rachaActual;
    return this;
  }

  /**
   * Racha actual de victorias
   * @return rachaActual
  **/
  @ApiModelProperty(value = "Racha actual de victorias")
  
    public Long getRachaActual() {
    return rachaActual;
  }

  public void setRachaActual(Long rachaActual) {
    this.rachaActual = rachaActual;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    EstadisticasDeUsuarioModel estadisticasDeUsuarioModel = (EstadisticasDeUsuarioModel) o;
    return Objects.equals(this.usuario, estadisticasDeUsuarioModel.usuario) &&
        Objects.equals(this.partidasJugadas, estadisticasDeUsuarioModel.partidasJugadas) &&
        Objects.equals(this.partidasGanadas, estadisticasDeUsuarioModel.partidasGanadas) &&
        Objects.equals(this.rachaActual, estadisticasDeUsuarioModel.rachaActual);
  }

  @Override
  public int hashCode() {
    return Objects.hash(usuario, partidasJugadas, partidasGanadas, rachaActual);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class EstadisticasDeUsuarioModel {\n");
    
    sb.append("    usuario: ").append(toIndentedString(usuario)).append("\n");
    sb.append("    partidasJugadas: ").append(toIndentedString(partidasJugadas)).append("\n");
    sb.append("    partidasGanadas: ").append(toIndentedString(partidasGanadas)).append("\n");
    sb.append("    rachaActual: ").append(toIndentedString(rachaActual)).append("\n");
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
