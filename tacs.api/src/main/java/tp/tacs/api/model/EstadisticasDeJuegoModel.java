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
 * EstadisticasDeJuegoModel
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2020-09-14T19:00:38.696940-03:00[America/Buenos_Aires]")


public class EstadisticasDeJuegoModel   {
  @JsonProperty("partidasCreadas")
  private Long partidasCreadas = null;

  @JsonProperty("partidasEnCurso")
  private Long partidasEnCurso = null;

  @JsonProperty("partidasTerminadas")
  private Long partidasTerminadas = null;

  @JsonProperty("partidasCanceladas")
  private Long partidasCanceladas = null;

  public EstadisticasDeJuegoModel partidasCreadas(Long partidasCreadas) {
    this.partidasCreadas = partidasCreadas;
    return this;
  }

  /**
   * Get partidasCreadas
   * @return partidasCreadas
  **/
  @ApiModelProperty(value = "")
  
    public Long getPartidasCreadas() {
    return partidasCreadas;
  }

  public void setPartidasCreadas(Long partidasCreadas) {
    this.partidasCreadas = partidasCreadas;
  }

  public EstadisticasDeJuegoModel partidasEnCurso(Long partidasEnCurso) {
    this.partidasEnCurso = partidasEnCurso;
    return this;
  }

  /**
   * Get partidasEnCurso
   * @return partidasEnCurso
  **/
  @ApiModelProperty(value = "")
  
    public Long getPartidasEnCurso() {
    return partidasEnCurso;
  }

  public void setPartidasEnCurso(Long partidasEnCurso) {
    this.partidasEnCurso = partidasEnCurso;
  }

  public EstadisticasDeJuegoModel partidasTerminadas(Long partidasTerminadas) {
    this.partidasTerminadas = partidasTerminadas;
    return this;
  }

  /**
   * Get partidasTerminadas
   * @return partidasTerminadas
  **/
  @ApiModelProperty(value = "")
  
    public Long getPartidasTerminadas() {
    return partidasTerminadas;
  }

  public void setPartidasTerminadas(Long partidasTerminadas) {
    this.partidasTerminadas = partidasTerminadas;
  }

  public EstadisticasDeJuegoModel partidasCanceladas(Long partidasCanceladas) {
    this.partidasCanceladas = partidasCanceladas;
    return this;
  }

  /**
   * Get partidasCanceladas
   * @return partidasCanceladas
  **/
  @ApiModelProperty(value = "")
  
    public Long getPartidasCanceladas() {
    return partidasCanceladas;
  }

  public void setPartidasCanceladas(Long partidasCanceladas) {
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
        Objects.equals(this.partidasTerminadas, estadisticasDeJuegoModel.partidasTerminadas) &&
        Objects.equals(this.partidasCanceladas, estadisticasDeJuegoModel.partidasCanceladas);
  }

  @Override
  public int hashCode() {
    return Objects.hash(partidasCreadas, partidasEnCurso, partidasTerminadas, partidasCanceladas);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class EstadisticasDeJuegoModel {\n");
    
    sb.append("    partidasCreadas: ").append(toIndentedString(partidasCreadas)).append("\n");
    sb.append("    partidasEnCurso: ").append(toIndentedString(partidasEnCurso)).append("\n");
    sb.append("    partidasTerminadas: ").append(toIndentedString(partidasTerminadas)).append("\n");
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
