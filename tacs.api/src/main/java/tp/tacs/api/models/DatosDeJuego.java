package tp.tacs.api.models;

import java.util.Objects;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.validation.annotation.Validated;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Datos de una partida. Incluye su provincia en juego y el usuario que tiene el proximo turno.
 */
@ApiModel(description = "Datos de una partida. Incluye su provincia en juego y el usuario que tiene el proximo turno.")
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2020-08-27T18:00:32.244-03:00[America/Buenos_Aires]")
public class DatosDeJuego   {
  @JsonProperty("proximoTurno")
  private Usuario proximoTurno = null;

  @JsonProperty("provincia")
  private ProvinciaEnJuego provincia = null;

  public DatosDeJuego proximoTurno(Usuario proximoTurno) {
    this.proximoTurno = proximoTurno;
    return this;
  }

  /**
   * Get proximoTurno
   * @return proximoTurno
  **/
  @ApiModelProperty(value = "")
  
    @Valid
    public Usuario getProximoTurno() {
    return proximoTurno;
  }

  public void setProximoTurno(Usuario proximoTurno) {
    this.proximoTurno = proximoTurno;
  }

  public DatosDeJuego provincia(ProvinciaEnJuego provincia) {
    this.provincia = provincia;
    return this;
  }

  /**
   * Get provincia
   * @return provincia
  **/
  @ApiModelProperty(required = true, value = "")
      @NotNull

    @Valid
    public ProvinciaEnJuego getProvincia() {
    return provincia;
  }

  public void setProvincia(ProvinciaEnJuego provincia) {
    this.provincia = provincia;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    DatosDeJuego datosDeJuego = (DatosDeJuego) o;
    return Objects.equals(this.proximoTurno, datosDeJuego.proximoTurno) &&
        Objects.equals(this.provincia, datosDeJuego.provincia);
  }

  @Override
  public int hashCode() {
    return Objects.hash(proximoTurno, provincia);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class DatosDeJuego {\n");
    
    sb.append("    proximoTurno: ").append(toIndentedString(proximoTurno)).append("\n");
    sb.append("    provincia: ").append(toIndentedString(provincia)).append("\n");
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
