package tp.tacs.api.models;

import java.util.Objects;

import org.springframework.validation.annotation.Validated;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModelProperty;

/**
 * ResultadoAtaque
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2020-08-27T18:00:32.244-03:00[America/Buenos_Aires]")
public class ResultadoAtaque   {
  @JsonProperty("exitoso")
  private Boolean exitoso = null;

  @JsonProperty("gauchosDefensoresFinal")
  private Integer gauchosDefensoresFinal = null;

  @JsonProperty("gauchosAtacantesFinal")
  private Integer gauchosAtacantesFinal = null;

  public ResultadoAtaque exitoso(Boolean exitoso) {
    this.exitoso = exitoso;
    return this;
  }

  /**
   * Get exitoso
   * @return exitoso
  **/
  @ApiModelProperty(value = "")
  
    public Boolean isExitoso() {
    return exitoso;
  }

  public void setExitoso(Boolean exitoso) {
    this.exitoso = exitoso;
  }

  public ResultadoAtaque gauchosDefensoresFinal(Integer gauchosDefensoresFinal) {
    this.gauchosDefensoresFinal = gauchosDefensoresFinal;
    return this;
  }

  /**
   * Get gauchosDefensoresFinal
   * @return gauchosDefensoresFinal
  **/
  @ApiModelProperty(value = "")
  
    public Integer getGauchosDefensoresFinal() {
    return gauchosDefensoresFinal;
  }

  public void setGauchosDefensoresFinal(Integer gauchosDefensoresFinal) {
    this.gauchosDefensoresFinal = gauchosDefensoresFinal;
  }

  public ResultadoAtaque gauchosAtacantesFinal(Integer gauchosAtacantesFinal) {
    this.gauchosAtacantesFinal = gauchosAtacantesFinal;
    return this;
  }

  /**
   * Get gauchosAtacantesFinal
   * @return gauchosAtacantesFinal
  **/
  @ApiModelProperty(value = "")
  
    public Integer getGauchosAtacantesFinal() {
    return gauchosAtacantesFinal;
  }

  public void setGauchosAtacantesFinal(Integer gauchosAtacantesFinal) {
    this.gauchosAtacantesFinal = gauchosAtacantesFinal;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ResultadoAtaque resultadoAtaque = (ResultadoAtaque) o;
    return Objects.equals(this.exitoso, resultadoAtaque.exitoso) &&
        Objects.equals(this.gauchosDefensoresFinal, resultadoAtaque.gauchosDefensoresFinal) &&
        Objects.equals(this.gauchosAtacantesFinal, resultadoAtaque.gauchosAtacantesFinal);
  }

  @Override
  public int hashCode() {
    return Objects.hash(exitoso, gauchosDefensoresFinal, gauchosAtacantesFinal);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ResultadoAtaque {\n");
    
    sb.append("    exitoso: ").append(toIndentedString(exitoso)).append("\n");
    sb.append("    gauchosDefensoresFinal: ").append(toIndentedString(gauchosDefensoresFinal)).append("\n");
    sb.append("    gauchosAtacantesFinal: ").append(toIndentedString(gauchosAtacantesFinal)).append("\n");
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
