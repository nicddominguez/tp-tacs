package tp.tacs.api.model;

import java.util.Objects;

import org.springframework.validation.annotation.Validated;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModelProperty;

/**
 * EstadisticasDeJuego
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2020-08-29T21:54:39.417-03:00[America/Buenos_Aires]")
public class EstadisticasDeJuegoModel {
  @JsonProperty("partidasCreadas")
  private Integer partidasCreadas = null;

  @JsonProperty("partidasEnCurso")
  private Integer partidasEnCurso = null;

  @JsonProperty("partidesTerminadas")
  private Integer partidesTerminadas = null;

  @JsonProperty("partidasCanceladas")
  private Integer partidasCanceladas = null;

  public EstadisticasDeJuegoModel partidasCreadas(Integer partidasCreadas) {
    this.partidasCreadas = partidasCreadas;
    return this;
  }

  /**
   * Get partidasCreadas
   * @return partidasCreadas
  **/
  @ApiModelProperty(value = "")
  
    public Integer getPartidasCreadas() {
    return partidasCreadas;
  }

  public void setPartidasCreadas(Integer partidasCreadas) {
    this.partidasCreadas = partidasCreadas;
  }

  public EstadisticasDeJuegoModel partidasEnCurso(Integer partidasEnCurso) {
    this.partidasEnCurso = partidasEnCurso;
    return this;
  }

  /**
   * Get partidasEnCurso
   * @return partidasEnCurso
  **/
  @ApiModelProperty(value = "")
  
    public Integer getPartidasEnCurso() {
    return partidasEnCurso;
  }

  public void setPartidasEnCurso(Integer partidasEnCurso) {
    this.partidasEnCurso = partidasEnCurso;
  }

  public EstadisticasDeJuegoModel partidesTerminadas(Integer partidesTerminadas) {
    this.partidesTerminadas = partidesTerminadas;
    return this;
  }

  /**
   * Get partidesTerminadas
   * @return partidesTerminadas
  **/
  @ApiModelProperty(value = "")
  
    public Integer getPartidesTerminadas() {
    return partidesTerminadas;
  }

  public void setPartidesTerminadas(Integer partidesTerminadas) {
    this.partidesTerminadas = partidesTerminadas;
  }

  public EstadisticasDeJuegoModel partidasCanceladas(Integer partidasCanceladas) {
    this.partidasCanceladas = partidasCanceladas;
    return this;
  }

  /**
   * Get partidasCanceladas
   * @return partidasCanceladas
  **/
  @ApiModelProperty(value = "")
  
    public Integer getPartidasCanceladas() {
    return partidasCanceladas;
  }

  public void setPartidasCanceladas(Integer partidasCanceladas) {
    this.partidasCanceladas = partidasCanceladas;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    EstadisticasDeJuegoModel estadisticasDeJuegoModel = (EstadisticasDeJuegoModel) o;
    return Objects.equals(this.partidasCreadas, estadisticasDeJuegoModel.partidasCreadas) &&
        Objects.equals(this.partidasEnCurso, estadisticasDeJuegoModel.partidasEnCurso) &&
        Objects.equals(this.partidesTerminadas, estadisticasDeJuegoModel.partidesTerminadas) &&
        Objects.equals(this.partidasCanceladas, estadisticasDeJuegoModel.partidasCanceladas);
  }

  @Override
  public int hashCode() {
    return Objects.hash(partidasCreadas, partidasEnCurso, partidesTerminadas, partidasCanceladas);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class EstadisticasDeJuego {\n");
    
    sb.append("    partidasCreadas: ").append(toIndentedString(partidasCreadas)).append("\n");
    sb.append("    partidasEnCurso: ").append(toIndentedString(partidasEnCurso)).append("\n");
    sb.append("    partidesTerminadas: ").append(toIndentedString(partidesTerminadas)).append("\n");
    sb.append("    partidasCanceladas: ").append(toIndentedString(partidasCanceladas)).append("\n");
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
