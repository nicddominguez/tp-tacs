package tp.tacs.api.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Partida Liviana
 */
@ApiModel(description = "Partida Liviana")
@Validated

public class PartidaLivianaModel  implements OneOfinlineResponse200 {
  @JsonProperty("id")
  private String id = null;

  @JsonProperty("estado")
  private EstadoDeJuegoModel estado = null;

  @JsonProperty("idGanador")
  private String idGanador = null;

  @JsonProperty("idUsuarioProximoTurno")
  private String idUsuarioProximoTurno = null;

  @JsonProperty("municipiosPorJugador")
  @Valid
  private List<MunicipiosPorJugadorLivianoModel> municipiosPorJugador = null;

  public PartidaLivianaModel id(String id) {
    this.id = id;
    return this;
  }

  /**
   * Get id
   * @return id
  **/
  @ApiModelProperty(value = "")
  
    public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public PartidaLivianaModel estado(EstadoDeJuegoModel estado) {
    this.estado = estado;
    return this;
  }

  /**
   * Get estado
   * @return estado
  **/
  @ApiModelProperty(required = true, value = "")
      @NotNull

    @Valid
    public EstadoDeJuegoModel getEstado() {
    return estado;
  }

  public void setEstado(EstadoDeJuegoModel estado) {
    this.estado = estado;
  }

  public PartidaLivianaModel idGanador(String idGanador) {
    this.idGanador = idGanador;
    return this;
  }

  /**
   * Get idGanador
   * @return idGanador
  **/
  @ApiModelProperty(value = "")
  
    public String getIdGanador() {
    return idGanador;
  }

  public void setIdGanador(String idGanador) {
    this.idGanador = idGanador;
  }

  public PartidaLivianaModel idUsuarioProximoTurno(String idUsuarioProximoTurno) {
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

  public PartidaLivianaModel municipiosPorJugador(List<MunicipiosPorJugadorLivianoModel> municipiosPorJugador) {
    this.municipiosPorJugador = municipiosPorJugador;
    return this;
  }

  public PartidaLivianaModel addMunicipiosPorJugadorItem(MunicipiosPorJugadorLivianoModel municipiosPorJugadorItem) {
    if (this.municipiosPorJugador == null) {
      this.municipiosPorJugador = new ArrayList<MunicipiosPorJugadorLivianoModel>();
    }
    this.municipiosPorJugador.add(municipiosPorJugadorItem);
    return this;
  }

  /**
   * Get municipiosPorJugador
   * @return municipiosPorJugador
  **/
  @ApiModelProperty(value = "")
      @Valid
    public List<MunicipiosPorJugadorLivianoModel> getMunicipiosPorJugador() {
    return municipiosPorJugador;
  }

  public void setMunicipiosPorJugador(List<MunicipiosPorJugadorLivianoModel> municipiosPorJugador) {
    this.municipiosPorJugador = municipiosPorJugador;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    PartidaLivianaModel partidaLivianaModel = (PartidaLivianaModel) o;
    return Objects.equals(this.id, partidaLivianaModel.id) &&
        Objects.equals(this.estado, partidaLivianaModel.estado) &&
        Objects.equals(this.idGanador, partidaLivianaModel.idGanador) &&
        Objects.equals(this.idUsuarioProximoTurno, partidaLivianaModel.idUsuarioProximoTurno) &&
        Objects.equals(this.municipiosPorJugador, partidaLivianaModel.municipiosPorJugador);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, estado, idGanador, idUsuarioProximoTurno, municipiosPorJugador);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class PartidaLivianaModel {\n");
    
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    estado: ").append(toIndentedString(estado)).append("\n");
    sb.append("    idGanador: ").append(toIndentedString(idGanador)).append("\n");
    sb.append("    idUsuarioProximoTurno: ").append(toIndentedString(idUsuarioProximoTurno)).append("\n");
    sb.append("    municipiosPorJugador: ").append(toIndentedString(municipiosPorJugador)).append("\n");
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
