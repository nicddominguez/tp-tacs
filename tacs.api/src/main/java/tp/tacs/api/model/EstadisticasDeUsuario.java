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
public class EstadisticasDeUsuario   {
  @JsonProperty("usuario")
  private Usuario usuario = null;

  @JsonProperty("partidasJugadas")
  private Integer partidasJugadas = null;

  @JsonProperty("partidasGanadas")
  private Integer partidasGanadas = null;

  @JsonProperty("rachaActual")
  private Integer rachaActual = null;

  public EstadisticasDeUsuario usuario(Usuario usuario) {
    this.usuario = usuario;
    return this;
  }

  /**
   * Get usuario
   * @return usuario
  **/
  @ApiModelProperty(value = "")
  
    @Valid
    public Usuario getUsuario() {
    return usuario;
  }

  public void setUsuario(Usuario usuario) {
    this.usuario = usuario;
  }

  public EstadisticasDeUsuario partidasJugadas(Integer partidasJugadas) {
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

  public EstadisticasDeUsuario partidasGanadas(Integer partidasGanadas) {
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

  public EstadisticasDeUsuario rachaActual(Integer rachaActual) {
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
    EstadisticasDeUsuario estadisticasDeUsuario = (EstadisticasDeUsuario) o;
    return Objects.equals(this.usuario, estadisticasDeUsuario.usuario) &&
        Objects.equals(this.partidasJugadas, estadisticasDeUsuario.partidasJugadas) &&
        Objects.equals(this.partidasGanadas, estadisticasDeUsuario.partidasGanadas) &&
        Objects.equals(this.rachaActual, estadisticasDeUsuario.rachaActual);
  }

  @Override
  public int hashCode() {
    return Objects.hash(usuario, partidasJugadas, partidasGanadas, rachaActual);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class EstadisticasDeUsuario {\n");
    
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
