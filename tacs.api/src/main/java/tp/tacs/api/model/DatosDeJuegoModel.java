package tp.tacs.api.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.ArrayList;
import java.util.List;
import tp.tacs.api.model.MunicipioEnJuegoModel;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * Datos de una partida. Incluye su provincia en juego y el usuario que tiene el proximo turno.
 */
@ApiModel(description = "Datos de una partida. Incluye su provincia en juego y el usuario que tiene el proximo turno.")
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2020-09-05T20:37:59.553716700-03:00[America/Buenos_Aires]")


public class DatosDeJuegoModel   {
  @JsonProperty("idUsuarioProximoTurno")
  private Long idUsuarioProximoTurno = null;

  @JsonProperty("municipios")
  @Valid
  private List<MunicipioEnJuegoModel> municipios = null;

  public DatosDeJuegoModel idUsuarioProximoTurno(Long idUsuarioProximoTurno) {
    this.idUsuarioProximoTurno = idUsuarioProximoTurno;
    return this;
  }

  /**
   * Get idUsuarioProximoTurno
   * @return idUsuarioProximoTurno
  **/
  @ApiModelProperty(value = "")
  
    public Long getIdUsuarioProximoTurno() {
    return idUsuarioProximoTurno;
  }

  public void setIdUsuarioProximoTurno(Long idUsuarioProximoTurno) {
    this.idUsuarioProximoTurno = idUsuarioProximoTurno;
  }

  public DatosDeJuegoModel municipios(List<MunicipioEnJuegoModel> municipios) {
    this.municipios = municipios;
    return this;
  }

  public DatosDeJuegoModel addMunicipiosItem(MunicipioEnJuegoModel municipiosItem) {
    if (this.municipios == null) {
      this.municipios = new ArrayList<MunicipioEnJuegoModel>();
    }
    this.municipios.add(municipiosItem);
    return this;
  }

  /**
   * Municipios en juego que contiene la provincia.
   * @return municipios
  **/
  @ApiModelProperty(value = "Municipios en juego que contiene la provincia.")
      @Valid
    public List<MunicipioEnJuegoModel> getMunicipios() {
    return municipios;
  }

  public void setMunicipios(List<MunicipioEnJuegoModel> municipios) {
    this.municipios = municipios;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    DatosDeJuegoModel datosDeJuegoModel = (DatosDeJuegoModel) o;
    return Objects.equals(this.idUsuarioProximoTurno, datosDeJuegoModel.idUsuarioProximoTurno) &&
        Objects.equals(this.municipios, datosDeJuegoModel.municipios);
  }

  @Override
  public int hashCode() {
    return Objects.hash(idUsuarioProximoTurno, municipios);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class DatosDeJuegoModel {\n");
    
    sb.append("    idUsuarioProximoTurno: ").append(toIndentedString(idUsuarioProximoTurno)).append("\n");
    sb.append("    municipios: ").append(toIndentedString(municipios)).append("\n");
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
