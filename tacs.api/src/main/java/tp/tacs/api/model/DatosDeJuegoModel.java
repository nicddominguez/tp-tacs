package tp.tacs.api.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Datos de una partida. Incluye su provincia en juego y el usuario que tiene el proximo turno.
 */
@ApiModel(description = "Datos de una partida. Incluye su provincia en juego y el usuario que tiene el proximo turno.")
@Validated


public class DatosDeJuegoModel   {
  @JsonProperty("idUsuarioProximoTurno")
  private String idUsuarioProximoTurno = null;

  @JsonProperty("municipios")
  @Valid
  private List<MunicipioEnJuegoModel> municipios = null;

  public DatosDeJuegoModel idUsuarioProximoTurno(String idUsuarioProximoTurno) {
    this.idUsuarioProximoTurno = idUsuarioProximoTurno;
    return this;
  }

  /**
   * Get idUsuarioProximoTurno
   * @return idUsuarioProximoTurno
  **/
  @ApiModelProperty(value = "")
  
    public String getIdUsuarioProximoTurno() {
    return idUsuarioProximoTurno;
  }

  public void setIdUsuarioProximoTurno(String idUsuarioProximoTurno) {
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
  public boolean equals(Object o) {
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
  private String toIndentedString(Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
}
