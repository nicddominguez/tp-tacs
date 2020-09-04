package tp.tacs.api.model;

import java.util.Objects;

import javax.validation.Valid;

import org.springframework.validation.annotation.Validated;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModelProperty;

/**
 * EstadisticasDeUsuario
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2020-08-29T21:54:39.417-03:00[America/Buenos_Aires]")
public class EstadisticasDeUsuarioModel {
  @JsonProperty("usuario")
  private UsuarioModel usuarioModel = null;

  @JsonProperty("partidasJugadas")
  private Integer partidasJugadas = null;

  @JsonProperty("partidasGanadas")
  private Integer partidasGanadas = null;

  @JsonProperty("rachaActual")
  private Integer rachaActual = null;

  public EstadisticasDeUsuarioModel usuario(UsuarioModel usuarioModel) {
    this.usuarioModel = usuarioModel;
    return this;
  }

  /**
   * Get usuario
   * @return usuario
  **/
  @ApiModelProperty(value = "")
  
    @Valid
    public UsuarioModel getUsuario() {
    return usuarioModel;
  }

  public void setUsuario(UsuarioModel usuarioModel) {
    this.usuarioModel = usuarioModel;
  }

  public EstadisticasDeUsuarioModel partidasJugadas(Integer partidasJugadas) {
    this.partidasJugadas = partidasJugadas;
    return this;
  }

  /**
   * Get partidasJugadas
   * @return partidasJugadas
  **/
  @ApiModelProperty(value = "")
  
    public Integer getPartidasJugadas() {
    return partidasJugadas;
  }

  public void setPartidasJugadas(Integer partidasJugadas) {
    this.partidasJugadas = partidasJugadas;
  }

  public EstadisticasDeUsuarioModel partidasGanadas(Integer partidasGanadas) {
    this.partidasGanadas = partidasGanadas;
    return this;
  }

  /**
   * Get partidasGanadas
   * @return partidasGanadas
  **/
  @ApiModelProperty(value = "")
  
    public Integer getPartidasGanadas() {
    return partidasGanadas;
  }

  public void setPartidasGanadas(Integer partidasGanadas) {
    this.partidasGanadas = partidasGanadas;
  }

  public EstadisticasDeUsuarioModel rachaActual(Integer rachaActual) {
    this.rachaActual = rachaActual;
    return this;
  }

  /**
   * Racha actual de victorias
   * @return rachaActual
  **/
  @ApiModelProperty(value = "Racha actual de victorias")
  
    public Integer getRachaActual() {
    return rachaActual;
  }

  public void setRachaActual(Integer rachaActual) {
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
    return Objects.equals(this.usuarioModel, estadisticasDeUsuarioModel.usuarioModel) &&
        Objects.equals(this.partidasJugadas, estadisticasDeUsuarioModel.partidasJugadas) &&
        Objects.equals(this.partidasGanadas, estadisticasDeUsuarioModel.partidasGanadas) &&
        Objects.equals(this.rachaActual, estadisticasDeUsuarioModel.rachaActual);
  }

  @Override
  public int hashCode() {
    return Objects.hash(usuarioModel, partidasJugadas, partidasGanadas, rachaActual);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class EstadisticasDeUsuario {\n");
    
    sb.append("    usuario: ").append(toIndentedString(usuarioModel)).append("\n");
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
